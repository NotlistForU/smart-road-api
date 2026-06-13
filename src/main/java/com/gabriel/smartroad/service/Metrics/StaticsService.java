package com.gabriel.smartroad.service.Metrics;

import com.gabriel.smartroad.repository.FuelRecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaticsService {
    private final FuelRecordRepository fuelRecordRepository;

    public StaticsService(FuelRecordRepository fuelRecordRepository) {
        this.fuelRecordRepository = fuelRecordRepository;
    }

    public Double getAverageKmlForModelAndVersion(String model, String version) {
        List<Double> kmls = fuelRecordRepository.findTrustedKmlByVehicleModel(model, version);

        if(kmls == null || kmls.isEmpty()){
            return 0.0;
        }

        double soma = 0.0;
        for (Double k: kmls){
            soma += k;
        }

        return soma / kmls.size();
    }
}
