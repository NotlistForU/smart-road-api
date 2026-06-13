package com.gabriel.smartroad.service.Metrics;

import com.gabriel.smartroad.domain.FuelRecord;
import com.gabriel.smartroad.domain.Vehicle;
import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

    public Double calculateKmL(Vehicle vehicle, FuelRecord fuelRecord) {
        if (vehicle.getCurrentMileage() == null) return null;
        if (fuelRecord.getLiters() == null || fuelRecord.getLiters() <= 0) return null;
        if (fuelRecord.getMileageAtRefueling() < vehicle.getCurrentMileage()
                || fuelRecord.getMileageAtRefueling() <= 0) return null;
        double mileage = fuelRecord.getMileageAtRefueling() - vehicle.getCurrentMileage();
        return mileage / fuelRecord.getLiters();
    }
}
