package calamadevs.dto.PublicDashboard;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record GetRequestDashboardByVersion(
        @NotNull
        Long versionId,
        LocalDateTime startDate,
        LocalDateTime endDate
) {
}
