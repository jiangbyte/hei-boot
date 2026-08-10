package github.jiangbyte.io.user.modules.admin.profile.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import github.jiangbyte.io.common.core.exception.BizException;
import github.jiangbyte.io.common.mybatis.datasource.ReadDataSource;
import github.jiangbyte.io.common.satoken.model.LoginUser;
import github.jiangbyte.io.common.satoken.utils.LoginHelper;
import github.jiangbyte.io.iam.account.AccountIdentityApi;
import github.jiangbyte.io.sys.file.FileApi;
import github.jiangbyte.io.sys.file.FileInfo;
import github.jiangbyte.io.user.admin.profile.AdminUserProfileInfo;
import github.jiangbyte.io.user.modules.admin.profile.convert.AdminUserProfileConvert;
import github.jiangbyte.io.user.modules.admin.profile.entity.AdminUserProfile;
import github.jiangbyte.io.user.modules.admin.profile.mapper.AdminUserProfileMapper;
import github.jiangbyte.io.user.modules.admin.profile.param.ProfileUpdateParam;
import github.jiangbyte.io.user.modules.admin.profile.result.AvatarUpdateResult;
import github.jiangbyte.io.user.modules.admin.profile.result.DeptIdNameResult;
import github.jiangbyte.io.user.modules.admin.profile.result.GroupIdNameResult;
import github.jiangbyte.io.user.modules.admin.profile.result.MeResult;
import github.jiangbyte.io.user.modules.admin.profile.result.OrgInfoResult;
import github.jiangbyte.io.user.modules.admin.profile.result.RoleIdNameResult;
import github.jiangbyte.io.user.modules.admin.profile.result.UserProfileResult;
import github.jiangbyte.io.user.modules.admin.profile.service.AdminUserProfileService;
import lombok.RequiredArgsConstructor;
import org.dromara.trans.service.impl.TransService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * {@link AdminUserProfileService} 实现：资料持久化、头像文件处理、组织名称翻译与跨模块资料读写。
 *
 * Author: Charlie
 */
@Service
@RequiredArgsConstructor
public class AdminUserProfileServiceImpl implements AdminUserProfileService {

    private static final long AVATAR_MAX_SIZE = 2 * 1024 * 1024;
    private static final Set<String> AVATAR_TYPES = Set.of("image/jpeg", "image/png", "image/webp");

    private final AdminUserProfileMapper adminProfileMapper;
    private final FileApi fileApi;
    private final AccountIdentityApi accountIdentityApi;
    private final TransService transService;
    private final AdminUserProfileConvert adminUserProfileConvert;

    @Override
    @ReadDataSource
    public MeResult currentMe() {
        // 读取会话与资料，组装 me 并填充组织名称
        LoginUser loginUser = LoginHelper.requireUser();
        UserProfileResult profile = currentProfile();
        MeResult response = adminUserProfileConvert.toMe(loginUser, profile);
        fillOrgIdNames(response, loginUser);
        response.setAvatar(profile.getAvatar());
        return response;
    }

    @Override
    @ReadDataSource
    public UserProfileResult currentProfile() {
        // 确保资料存在后补全登录开关与头像 URL
        LoginUser loginUser = LoginHelper.requireUser();
        return withResolvedAvatar(enrichLoginFlags(adminUserProfileConvert.toDto(ensureProfile(loginUser.getAccountId()))));
    }

    @Override
    @Transactional
    public void updateProfile(ProfileUpdateParam request) {
        LoginUser loginUser = LoginHelper.requireUser();
        AdminUserProfile profile = ensureProfile(loginUser.getAccountId());
        // 映射可编辑字段；头像单独规范化对象名
        adminUserProfileConvert.update(request, profile);
        if (StringUtils.hasText(request.getAvatar())) {
            profile.setAvatar(fileApi.normalizeObjectName(request.getAvatar()));
        }
        adminProfileMapper.updateById(profile);
    }

    @Override
    @Transactional
    public AvatarUpdateResult uploadAvatar(MultipartFile file) {
        LoginUser loginUser = LoginHelper.requireUser();
        // 校验文件存在、大小与 MIME
        if (file == null || file.isEmpty()) {
            throw new BizException("File is required");
        }
        if (file.getSize() > AVATAR_MAX_SIZE) {
            throw new BizException("Avatar exceeds 2MB");
        }
        String contentType = file.getContentType() == null ? "" : file.getContentType().toLowerCase();
        if (!AVATAR_TYPES.contains(contentType)) {
            throw new BizException("Avatar must be jpeg/png/webp");
        }
        AdminUserProfile current = ensureProfile(loginUser.getAccountId());
        String previousAvatar = current.getAvatar();
        // 上传新头像并落库
        FileInfo uploaded = fileApi.upload(file, null);
        current.setAvatar(uploaded.getObjectName());
        adminProfileMapper.updateById(current);
        // 尽力删除旧对象
        if (StringUtils.hasText(previousAvatar) && !previousAvatar.equals(uploaded.getObjectName())) {
            try {
                fileApi.deleteByObjectName(previousAvatar);
            } catch (Exception ignored) {
                // 尽力清理
            }
        }
        String resolved = fileApi.resolveUrl(uploaded.getObjectName());
        if (!StringUtils.hasText(resolved)) {
            resolved = uploaded.getUrl();
        }
        AvatarUpdateResult response = new AvatarUpdateResult();
        BeanUtil.copyProperties(uploaded, response);
        response.setFileId(uploaded.getId());
        response.setAvatar(resolved);
        response.setUrl(resolved);
        return response;
    }

    @Override
    @ReadDataSource
    public OrgInfoResult orgInfo() {
        return resolveOrgIdNames(LoginHelper.requireUser());
    }

    @Override
    @ReadDataSource
    public AdminUserProfileInfo getProfile(String accountId) {
        if (!StringUtils.hasText(accountId)) {
            return null;
        }
        return adminUserProfileConvert.toInfo(adminProfileMapper.selectById(accountId));
    }

    @Override
    @ReadDataSource
    public Map<String, AdminUserProfileInfo> getProfiles(Collection<String> accountIds) {
        Map<String, AdminUserProfileInfo> map = new HashMap<>();
        if (accountIds == null || accountIds.isEmpty()) {
            return map;
        }
        // 批量查询并按 accountId 建索引
        adminProfileMapper.selectByIds(accountIds)
                .forEach(profile -> map.put(profile.getAccountId(), adminUserProfileConvert.toInfo(profile)));
        return map;
    }

    @Override
    @ReadDataSource
    public Map<String, String> getDisplayNames(Collection<String> accountIds) {
        // 批量查资料，优先姓名否则昵称
        Map<String, String> map = new HashMap<>();
        if (accountIds == null || accountIds.isEmpty()) {
            return map;
        }
        adminProfileMapper.selectByIds(accountIds).forEach(profile ->
                map.put(profile.getAccountId(),
                        Objects.requireNonNullElse(profile.getName(), profile.getNickname())));
        return map;
    }

    @Override
    @Transactional
    public void upsertProfile(AdminUserProfileInfo info) {
        if (info == null || !StringUtils.hasText(info.getAccountId())) {
            return;
        }
        AdminUserProfile profile = adminProfileMapper.selectById(info.getAccountId());
        if (profile == null) {
            // 不存在则新建
            profile = new AdminUserProfile();
            profile.setAccountId(info.getAccountId());
            adminUserProfileConvert.updateInfo(info, profile);
            adminProfileMapper.insert(profile);
            return;
        }
        adminUserProfileConvert.updateInfo(info, profile);
        adminProfileMapper.updateById(profile);
    }

    @Override
    @Transactional
    public void updatePhoneByAccount(String accountId, String phone) {
        String value = StringUtils.hasText(phone) ? phone.trim() : null;
        AdminUserProfile profile = adminProfileMapper.selectById(accountId);
        if (profile == null) {
            profile = new AdminUserProfile();
            profile.setAccountId(accountId);
            profile.setPhone(value);
            adminProfileMapper.insert(profile);
            return;
        }
        profile.setPhone(value);
        adminProfileMapper.updateById(profile);
    }

    @Override
    @Transactional
    public void updateEmailByAccount(String accountId, String email) {
        // 邮箱统一小写存储
        String value = StringUtils.hasText(email) ? email.trim().toLowerCase(Locale.ROOT) : null;
        AdminUserProfile profile = adminProfileMapper.selectById(accountId);
        if (profile == null) {
            profile = new AdminUserProfile();
            profile.setAccountId(accountId);
            profile.setEmail(value);
            adminProfileMapper.insert(profile);
            return;
        }
        profile.setEmail(value);
        adminProfileMapper.updateById(profile);
    }

    @Override
    @Transactional
    public void createProfile(String accountId, String name, String nickname, String email) {
        if (!StringUtils.hasText(accountId)) {
            return;
        }
        AdminUserProfileInfo info = new AdminUserProfileInfo();
        info.setAccountId(accountId);
        info.setName(name);
        info.setNickname(nickname);
        info.setEmail(email);
        upsertProfile(info);
    }

    @Override
    @Transactional
    public void deleteProfiles(Collection<String> accountIds) {
        if (accountIds == null || accountIds.isEmpty()) {
            return;
        }
        adminProfileMapper.deleteByIds(accountIds);
    }

    @Override
    @ReadDataSource
    public Set<String> findAccountIdsByProfileFilters(String name, String phone, String email) {
        boolean hasName = StringUtils.hasText(name);
        boolean hasPhone = StringUtils.hasText(phone);
        boolean hasEmail = StringUtils.hasText(email);
        // 无条件时返回 null，表示不过滤
        if (!hasName && !hasPhone && !hasEmail) {
            return null;
        }
        // 多条件取交集
        Set<String> matched = null;
        if (hasName) {
            matched = intersectOrInit(matched, accountIdsByName(name.trim()));
        }
        if (hasPhone) {
            matched = intersectOrInit(matched, accountIdsByPhone(phone.trim()));
        }
        if (hasEmail) {
            matched = intersectOrInit(matched, accountIdsByEmail(email.trim()));
        }
        return matched == null ? Set.of() : matched;
    }

    private Set<String> accountIdsByName(String name) {
        return adminProfileMapper.selectList(Wrappers.<AdminUserProfile>lambdaQuery()
                        .like(AdminUserProfile::getName, name)
                        .select(AdminUserProfile::getAccountId))
                .stream()
                .map(AdminUserProfile::getAccountId)
                .collect(Collectors.toCollection(HashSet::new));
    }

    private Set<String> accountIdsByPhone(String phone) {
        return adminProfileMapper.selectList(Wrappers.<AdminUserProfile>lambdaQuery()
                        .like(AdminUserProfile::getPhone, phone)
                        .select(AdminUserProfile::getAccountId))
                .stream()
                .map(AdminUserProfile::getAccountId)
                .collect(Collectors.toCollection(HashSet::new));
    }

    private Set<String> accountIdsByEmail(String email) {
        return adminProfileMapper.selectList(Wrappers.<AdminUserProfile>lambdaQuery()
                        .like(AdminUserProfile::getEmail, email)
                        .select(AdminUserProfile::getAccountId))
                .stream()
                .map(AdminUserProfile::getAccountId)
                .collect(Collectors.toCollection(HashSet::new));
    }

    private static Set<String> intersectOrInit(Set<String> current, Set<String> next) {
        if (current == null) {
            return next;
        }
        return current.stream().filter(next::contains).collect(Collectors.toCollection(HashSet::new));
    }

    private AdminUserProfile ensureProfile(String accountId) {
        // 首次访问时懒创建空资料行
        AdminUserProfile profile = adminProfileMapper.selectById(accountId);
        if (profile != null) {
            return profile;
        }
        profile = new AdminUserProfile();
        profile.setAccountId(accountId);
        adminProfileMapper.insert(profile);
        return profile;
    }

    private UserProfileResult enrichLoginFlags(UserProfileResult dto) {
        dto.setPhoneLoginEnabled(accountIdentityApi.hasIdentity(dto.getAccountId(), "PHONE"));
        dto.setEmailLoginEnabled(accountIdentityApi.hasIdentity(dto.getAccountId(), "EMAIL"));
        return dto;
    }

    private UserProfileResult withResolvedAvatar(UserProfileResult dto) {
        if (dto != null) {
            dto.setAvatar(fileApi.resolveUrl(dto.getAvatar()));
        }
        return dto;
    }

    private void fillOrgIdNames(MeResult response, LoginUser loginUser) {
        // 解析组织 ID-名称后写入 me
        OrgInfoResult org = resolveOrgIdNames(loginUser);
        response.setRoleIdNames(org.getRoleIdNames());
        response.setDeptIdNames(org.getDeptIdNames());
        response.setGroupIdNames(org.getGroupIdNames());
    }

    private OrgInfoResult resolveOrgIdNames(LoginUser loginUser) {
        // 会话 ID 列表 → 可翻译对象 → RPC 批量补名称
        OrgInfoResult org = new OrgInfoResult();
        List<RoleIdNameResult> roles = toRoleIdNames(loginUser.getRoleIds());
        List<DeptIdNameResult> depts = toDeptIdNames(loginUser.getDeptIds());
        List<GroupIdNameResult> groups = toGroupIdNames(loginUser.getGroupIds());
        if (!roles.isEmpty()) {
            transService.transBatch(roles);
        }
        if (!depts.isEmpty()) {
            transService.transBatch(depts);
        }
        if (!groups.isEmpty()) {
            transService.transBatch(groups);
        }
        org.setRoleIdNames(roles);
        org.setDeptIdNames(depts);
        org.setGroupIdNames(groups);
        return org;
    }

    private static List<RoleIdNameResult> toRoleIdNames(Collection<String> ids) {
        List<RoleIdNameResult> result = new ArrayList<>();
        if (CollectionUtils.isEmpty(ids)) {
            return result;
        }
        for (String id : ids) {
            if (!StringUtils.hasText(id)) {
                continue;
            }
            RoleIdNameResult item = new RoleIdNameResult();
            item.setId(id);
            result.add(item);
        }
        return result;
    }

    private static List<DeptIdNameResult> toDeptIdNames(Collection<String> ids) {
        List<DeptIdNameResult> result = new ArrayList<>();
        if (CollectionUtils.isEmpty(ids)) {
            return result;
        }
        for (String id : ids) {
            if (!StringUtils.hasText(id)) {
                continue;
            }
            DeptIdNameResult item = new DeptIdNameResult();
            item.setId(id);
            result.add(item);
        }
        return result;
    }

    private static List<GroupIdNameResult> toGroupIdNames(Collection<String> ids) {
        List<GroupIdNameResult> result = new ArrayList<>();
        if (CollectionUtils.isEmpty(ids)) {
            return result;
        }
        for (String id : ids) {
            if (!StringUtils.hasText(id)) {
                continue;
            }
            GroupIdNameResult item = new GroupIdNameResult();
            item.setId(id);
            result.add(item);
        }
        return result;
    }
}
