package github.jiangbyte.io.user.modules.portal.profile.convert;

import github.jiangbyte.io.common.satoken.model.LoginUser;
import github.jiangbyte.io.user.portal.profile.PortalUserProfileInfo;
import github.jiangbyte.io.user.modules.portal.profile.entity.PortalUserProfile;
import github.jiangbyte.io.user.modules.portal.profile.param.ProfileUpdateParam;
import github.jiangbyte.io.user.modules.portal.profile.result.MeResult;
import github.jiangbyte.io.user.modules.portal.profile.result.PublicProfileResult;
import github.jiangbyte.io.user.modules.portal.profile.result.UserProfileResult;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

/**
 * 门户端用户资料对象转换：实体 / Info / 请求参数与 me、公开资料、资料 DTO 之间的 MapStruct 映射。
 *
 * Author: Charlie
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PortalUserProfileConvert {

    /** 将会话用户与资料 DTO 组装为 me 响应（组织名称由调用方补齐）。 */
    @Mapping(target = "accountId", source = "loginUser.accountId")
    @Mapping(target = "account", source = "loginUser.account")
    @Mapping(target = "accountType", source = "loginUser.accountType")
    @Mapping(target = "roleIds", source = "loginUser.roleIds")
    @Mapping(target = "deptIds", source = "loginUser.deptIds")
    @Mapping(target = "groupIds", source = "loginUser.groupIds")
    @Mapping(target = "permissionKeys", source = "loginUser.permissions")
    @Mapping(target = "name", source = "profile.name")
    @Mapping(target = "nickname", source = "profile.nickname")
    @Mapping(target = "avatar", source = "profile.avatar")
    @Mapping(target = "profile", source = "profile")
    @Mapping(target = "roleIdNames", ignore = true)
    @Mapping(target = "deptIdNames", ignore = true)
    @Mapping(target = "groupIdNames", ignore = true)
    MeResult toMe(LoginUser loginUser, UserProfileResult profile);

    /** 实体转为用户中心资料 DTO。 */
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    UserProfileResult toDto(PortalUserProfile profile);

    /** 实体转为门户公开资料响应。 */
    PublicProfileResult toPublic(PortalUserProfile profile);

    /** 实体转为跨模块 Info。 */
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    PortalUserProfileInfo toInfo(PortalUserProfile profile);

    /** 用 Info 覆盖实体可变字段（保留主键与审计字段）。 */
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    @Mapping(target = "accountId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void updateInfo(PortalUserProfileInfo info, @MappingTarget PortalUserProfile profile);

    /** 用资料更新请求覆盖实体（头像/手机/邮箱由调用方单独处理）。 */
    @BeanMapping(
            ignoreUnmappedSourceProperties = {"remark", "avatar"},
            unmappedTargetPolicy = ReportingPolicy.IGNORE)
    @Mapping(target = "accountId", ignore = true)
    @Mapping(target = "avatar", ignore = true)
    @Mapping(target = "phone", ignore = true)
    @Mapping(target = "email", ignore = true)
    void update(ProfileUpdateParam request, @MappingTarget PortalUserProfile profile);
}
