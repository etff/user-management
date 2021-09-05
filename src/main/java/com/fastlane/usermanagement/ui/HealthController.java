package com.fastlane.usermanagement.ui;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "API HEALTH CHECK")
@RestController
public class HealthController {

    @GetMapping("/api/health")
    @ApiOperation(value = "API 헬스체크")
    public Long healthCheck() {
        return System.currentTimeMillis();
    }
}
