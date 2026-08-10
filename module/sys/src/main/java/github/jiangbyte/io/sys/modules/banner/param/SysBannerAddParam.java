package github.jiangbyte.io.sys.modules.banner.param;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建 Banner 入参。
 *
 * Author: Charlie
 */
@Data
public class SysBannerAddParam {

    @NotBlank
    private String title;
    @NotBlank
    private String image;
    private String url;
    private String linkType = "URL";
    private String summary;
    private String description;
    @NotBlank
    private String category;
    @NotBlank
    private String type;
    @NotBlank
    private String position;
    private List<String> targetAccountTypes = new ArrayList<>();
    private Integer sort = 0;
    private String status = "ENABLED";
    private OffsetDateTime startAt;
    private OffsetDateTime endAt;
}
