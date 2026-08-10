package github.jiangbyte.io.user.modules.admin.profile.result;

import lombok.Data;

/**
 * 管理端头像上传结果：可访问 URL、文件 ID 与对象存储路径。
 *
 * Author: Charlie
 */
@Data
public class AvatarUpdateResult {
    private String avatar;
    private String fileId;
    private String objectName;
    private String url;
}
