package calamadevs.controller;

import calamadevs.domain.User;
import calamadevs.dto.SupplyRecord.*;
import calamadevs.service.SupplyRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/supply-records")
public class SupplyRecordController {

    private final SupplyRecordService supplyRecordService;

    @PostMapping
    public ResponseEntity<ResponseSupplyRecord> postSupplyRecord(
            @RequestBody @Valid PostRequestSupplyRecord dto,
            @AuthenticationPrincipal User user) {
        ResponseSupplyRecord response = supplyRecordService.postSupplyRecordRequest(dto, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<ResponseSupplyRecord>> getSupplyRecords(
            @RequestParam("vehicle-id") Long vehicleId,
            @AuthenticationPrincipal User user, @PageableDefault(size = 20) Pageable pageable) {
        Page<ResponseSupplyRecord> response = supplyRecordService.getSupplyRecords(vehicleId, user, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping()
    public ResponseEntity<ResponseSupplyRecord> putSupplyVehicle(
            @RequestBody @Valid PutRequestSupplyRecord dto,
            @AuthenticationPrincipal User user) {
        ResponseSupplyRecord response = supplyRecordService.putSupplyRecord(dto, user);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{supply-id}")
    public ResponseEntity<Void> deleteSupplyVehicleRecord(
            @PathVariable("supply-id") Long supplyId,
            @AuthenticationPrincipal User user) {
        supplyRecordService.deleteSupplyRecord(supplyId, user);
        return ResponseEntity.noContent().build();
    }

}
