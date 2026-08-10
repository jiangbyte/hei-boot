package github.jiangbyte.io.sys.modules.banner.job;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import github.jiangbyte.io.sys.modules.banner.entity.SysBanner;
import github.jiangbyte.io.sys.modules.banner.mapper.SysBannerMapper;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;

/**
 * Banner 状态定时任务：按 start_at / end_at 激活或过期。
 *
 * Author: Charlie
 */
@Component
@RequiredArgsConstructor
public class BannerStatusJob {

    private final SysBannerMapper bannerMapper;

    @XxlJob("bannerStatusJob")
    public void execute() {
        OffsetDateTime now = OffsetDateTime.now();
        int expired = bannerMapper.update(null, Wrappers.<SysBanner>lambdaUpdate()
                .set(SysBanner::getStatus, "DISABLED")
                .set(SysBanner::getUpdatedAt, now)
                .eq(SysBanner::getStatus, "ENABLED")
                .isNotNull(SysBanner::getEndAt)
                .lt(SysBanner::getEndAt, now));

        // 组装查询条件
        int activated = bannerMapper.update(null, Wrappers.<SysBanner>lambdaUpdate()
                .set(SysBanner::getStatus, "ENABLED")
                .set(SysBanner::getUpdatedAt, now)
                .eq(SysBanner::getStatus, "DISABLED")
                .isNotNull(SysBanner::getStartAt)
                .le(SysBanner::getStartAt, now)
                .and(w -> w.isNull(SysBanner::getEndAt).or().ge(SysBanner::getEndAt, now)));

        XxlJobHelper.log("Banner status sync: expired={}, activated={}", expired, activated);
        XxlJobHelper.handleSuccess("expired=" + expired + ",activated=" + activated);
    }
}
