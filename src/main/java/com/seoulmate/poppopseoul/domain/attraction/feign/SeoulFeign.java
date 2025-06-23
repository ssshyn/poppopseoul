package com.seoulmate.poppopseoul.domain.attraction.feign;

import com.seoulmate.poppopseoul.config.OpenFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "seoulOpenApiClient", url = "${seoul.url}", configuration = OpenFeignConfig.class)
public interface SeoulFeign {
    @GetMapping("/${seoul.key}/json/{apiCode}/{startIndex}/{endIndex}")
    SeoulFeignResponse<?> getSeoulData(@PathVariable("apiCode") String apiCode,
                                      @PathVariable("startIndex") Integer startIndex,
                                      @PathVariable("endIndex") Integer endIndex);

    @GetMapping("/${seoul.key}/json/{apiCode}/{startIndex}/{endIndex}/{param}")
    SeoulFeignResponse<?> getSeoulData(@PathVariable("apiCode") String apiCode,
                                      @PathVariable("startIndex") Integer startIndex,
                                      @PathVariable("endIndex") Integer endIndex,
                                      @PathVariable("param") String param);
}


