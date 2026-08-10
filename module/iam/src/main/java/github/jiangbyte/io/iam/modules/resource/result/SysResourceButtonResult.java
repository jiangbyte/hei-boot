package github.jiangbyte.io.iam.modules.resource.result;

import lombok.Data;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 管理端按钮资源结果 DTO。
 *
 * Author: Charlie
 */
@Data
public class SysResourceButtonResult {

    private String id;
    private String parentId;
    private String parentIdName;
    private String code;
    private String name;
    private String resourceType;
    private String moduleId;
    private String moduleIdName;
    private String moduleClient;
    private String path;
    private String component;
    private String redirect;
    private String icon;
    private String color;
    private String href;
    private Integer sort;
    private Boolean isVisible;
    private Boolean isCache;
    private Boolean isAffix;
    private String status;
    private String description;
    private String layout;
    private Map<String, Object> extra;
    private OffsetDateTime createdAt;
    private String createdBy;
    private OffsetDateTime updatedAt;
    private String updatedBy;

    private String permissionRelId;
    private String permissionKey;
    private String dataScope;
    private List<String> customScopeDeptIds = new ArrayList<>();
    private String permissionDescription;
}
