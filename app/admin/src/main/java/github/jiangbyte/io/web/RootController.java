package github.jiangbyte.io.web;

/** Author: Charlie **/

import github.jiangbyte.io.common.core.domain.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class RootController {

    @GetMapping("/")
    public ApiResponse<Map<String, String>> root() {
        Map<String, String> data = new LinkedHashMap<>();
        data.put("name", "hei-boot-admin");
        return ApiResponse.ok(data);
    }
}
