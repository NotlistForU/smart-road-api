package calamadevs.controller;

import calamadevs.dto.PublicDashboard.GetRequestDashboardByVersion;
import calamadevs.dto.PublicDashboard.ResponsePublicDashboard;
import calamadevs.service.PublicDashboardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public")
public class PublicDashboardController {

    private final PublicDashboardService publicDashboardService;

    @PostMapping("/get-dashboard")
    public ResponseEntity<ResponsePublicDashboard>  getPublicDashboardByVersion(
            @RequestBody @Valid GetRequestDashboardByVersion dto
    ) {
        log.info("Thread: {}", Thread.currentThread().getName());
        return ResponseEntity.status(HttpStatus.OK).body(publicDashboardService.getSuppliesByVersion(dto));
    }
}
