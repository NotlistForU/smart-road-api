package com.gabriel.smartroad.service.Alerts;

import com.gabriel.smartroad.domain.Maintenance;
import com.gabriel.smartroad.domain.MaintenanceRule;
import com.gabriel.smartroad.domain.Notification;
import com.gabriel.smartroad.domain.Vehicle;
import com.gabriel.smartroad.repository.MaintenanceRepository;
import com.gabriel.smartroad.repository.MaintenanceRuleRepository;
import com.gabriel.smartroad.repository.NotificationRepository;
import com.gabriel.smartroad.repository.VehicleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class MaintenanceAlertService {
    private final VehicleRepository vehicleRepository;
    private final MaintenanceRuleRepository maintenanceRuleRepository;
    private final MaintenanceRepository maintenanceRepository;
    private final NotificationRepository notificationRepository;

    public MaintenanceAlertService(
            VehicleRepository vehicleRepository,
            MaintenanceRuleRepository maintenanceRuleRepository,
            MaintenanceRepository maintenanceRepository,
            NotificationRepository notificationRepository
    ) {
        this.vehicleRepository = vehicleRepository;
        this.maintenanceRuleRepository = maintenanceRuleRepository;
        this.maintenanceRepository = maintenanceRepository;
        this.notificationRepository = notificationRepository;
    }

    // 30 segundos para teste.
    @Scheduled(fixedRate = 300000000)
    public void scanAndAlert(){
        log.info("Iniciando varredura de Manutenção Preditiva...");

        List<Vehicle> activeVehicles =  vehicleRepository.findAll();
        for (Vehicle vehicle : activeVehicles){
            if (vehicle.getCurrentMileage() == null) continue;

            List<MaintenanceRule> rules = maintenanceRuleRepository.findByVehicleId(vehicle.getId());
            for (MaintenanceRule rule : rules){
                Optional<Maintenance> lastMaintenance = maintenanceRepository
                        .findTopByVehicleIdAndItem_IdOrderByMileageAtMaintenanceDesc(vehicle.getId(),rule.getItem().getId());

                if (lastMaintenance.isPresent()){
                    // Lifespan
                    int nextReplacementKm = lastMaintenance.get().getMileageAtMaintenance() + rule.getIntervalKm();
                    // Lifespan - 1000km
                    int warningThreshold = nextReplacementKm - 1000; // Alert when have 1000km left

                    if (vehicle.getCurrentMileage() >= warningThreshold && vehicle.getCurrentMileage() < nextReplacementKm){
                        log.warn("ALERTA GERADO: a peça {} está quase atigindo a vida util recomendada do veículo ID {}", rule.getItem().getName(), vehicle.getId());
                        createNotification(vehicle, rule.getItem().getName(), nextReplacementKm);
                    }
                }
            }
        }
    }

    private void createNotification(Vehicle vehicle, String itemName, int targetKm){
        Notification notification = new Notification();
        notification.setUser(vehicle.getUser());
        notification.setVehicle(vehicle);
        notification.setMessage("Atenção: A peça '" + itemName + " está se aproximando do limite estimado de troca (" + targetKm + " km).");
        notification.setRead(false);
        notificationRepository.save(notification);
    }
}
