package github.jiangbyte.io.sys.modules.banner.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import github.jiangbyte.io.common.core.domain.BaseEntity;
import github.jiangbyte.io.common.mybatis.handler.PostgresJacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Banner 实体，对应表 sys_banner。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_banner", autoResultMap = true)
public class SysBanner extends BaseEntity {
    private String title;
    private String image;

    @TableField(exist = false)
    private String imageUrl;
    private String url;
    private String linkType;
    private String summary;
    private String description;
    private String category;
    private String type;
    private String position;
    @TableField(typeHandler = PostgresJacksonTypeHandler.class)
    private List<String> targetAccountTypes;
    private Integer sort;
    private Long interactionCount;
    private String status;
    private OffsetDateTime startAt;
    private OffsetDateTime endAt;
}
