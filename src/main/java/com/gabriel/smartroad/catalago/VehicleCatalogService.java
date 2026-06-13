package com.gabriel.smartroad.catalago;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class VehicleCatalogService {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private JsonNode catalog;

    @PostConstruct
    public void loadCatalog() throws IOException {
        InputStream is = getClass()
                .getResourceAsStream("/catalog/catalog.json");
        System.out.println("IS = " + is);
        catalog = objectMapper.readTree(is);
    }

    public List<String> getBrands() {
        List<String> brands = new ArrayList<>();
        catalog.fieldNames().forEachRemaining(brands::add);

        return brands;
    }

    public List<String> getModels(String brand) {
        JsonNode node = catalog.path(brand);
        if (node.isMissingNode()) return Collections.emptyList();
        List<String> models = new ArrayList<>();
        node.fieldNames().forEachRemaining(models::add);
        return models;
    }

    public List<String> getVersions(String brand, String model) {
        JsonNode node = catalog.path(brand).path(model);
        if (node.isMissingNode()) return Collections.emptyList();
        List<String> versions = new ArrayList<>();
        node.fieldNames().forEachRemaining(versions::add);
        return versions;
    }

    public List<Integer> getYears(String brand, String model, String version) {
        JsonNode node = catalog.path(brand).path(model).path(version);
        if (node.isMissingNode() || !node.isArray()) return Collections.emptyList();
        List<Integer> years = new ArrayList<>();
        for (JsonNode y : node) {
            JsonNode yearNode = y.get("year");
            System.out.println(yearNode);
            if (yearNode == null) continue;
            years.add(yearNode.asInt());
        }
        return years;
    }

    public Integer getTankCapacity(String brand, String model, String version, int year) {
        JsonNode node = catalog.path(brand).path(model).path(version);
        if (node.isMissingNode() || !node.isArray()) return null;
        for (JsonNode y : node) {
            if (y.path("year").asInt() != year) continue;
            return y.path("tankCapacity").asInt();

        }
        return null;
    }
}
