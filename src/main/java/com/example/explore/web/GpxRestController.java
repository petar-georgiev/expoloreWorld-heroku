package com.example.explore.web;

import com.example.explore.service.RouteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GpxRestController {
    private final RouteService routeService;

    public GpxRestController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping("api/{routeId}/gpxCoordinates")
    public ResponseEntity<String> getGpxCoordinates(
            @PathVariable Long routeId
    ){
        return ResponseEntity.ok(routeService.getGpxCoordinates(routeId));
    }

}
