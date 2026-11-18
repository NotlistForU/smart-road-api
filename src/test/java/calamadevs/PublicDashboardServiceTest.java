package calamadevs;

import calamadevs.domain.Supply.FuelType;
import calamadevs.domain.User;
import calamadevs.domain.Vehicle.Version;
import calamadevs.dto.PublicDashboard.GetRequestDashboardByVersion;
import calamadevs.dto.PublicDashboard.ResponsePublicDashboard;
import calamadevs.dto.SupplyRecord.PostRequestSupplyRecord;
import calamadevs.dto.Vehicle.PostRequestVehicle;
import calamadevs.dto.Vehicle.ResponseVehicle;
import calamadevs.service.PublicDashboardService;
import calamadevs.service.SupplyRecordService;
import calamadevs.service.VehicleService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Sql("/versions-catalog.sql")
public class PublicDashboardServiceTest {

    @Autowired
    PublicDashboardService dashboardService;
    @Autowired
    SupplyRecordService supplyRecordService;
    @Autowired
    VehicleService vehicleService;
    @Autowired
    MockMvc mockMvc;
    @PersistenceContext
    EntityManager em;
    @MockitoBean
    JavaMailSender javaMailSender;

    private User user;
    private Version version;

    @BeforeEach
    void setup() {
        user = new User();
        user.setName("Teste01");
        user.setEmail("teste01@gmail.com");
        user.setPassword("Teste01@#$%");
        em.persist(user);

        version = em.createQuery(
                "SELECT v FROM Version v WHERE v.name = 'XEi 2.0' AND v.model.name = 'Corolla' AND v.model.modelYear = 2024",
                Version.class).getSingleResult();
    }

    // cria um veículo e 1 abastecimento de combustão: 40 L x R$ 5,00 = R$ 200
    private void createVehicleWithSupply(double km) {
        ResponseVehicle vehicle = vehicleService.postVehicle(
                new PostRequestVehicle(version.getId(), null, 0.0), user);

        BigDecimal pricePerLiter = new BigDecimal("5.00");

        supplyRecordService.postSupplyRecordRequest(
                new PostRequestSupplyRecord(
                        vehicle.vehicleId(),
                        LocalDateTime.now().minusDays(1),
                        km,
                        FuelType.GASOLINE,
                        40.0,
                        null,
                        pricePerLiter,
                        null),
                user);
    }

    @Test
    void shouldReturnCombustionSuppliesMetricsByVersionAndPeriod() {
        createVehicleWithSupply(400.0);
        GetRequestDashboardByVersion request = new GetRequestDashboardByVersion(
                version.getId(),
                LocalDateTime.now().minusDays(2),
                LocalDateTime.now());
        ResponsePublicDashboard response = dashboardService.getSuppliesByVersion(request);
        assertEquals(1, response.supplies().size());
        ResponsePublicDashboard.SupplyMetrics metrics = response.supplies().get(0);
        assertEquals(0, new BigDecimal("0.5000").compareTo(metrics.costPerKm()));
        assertEquals(0, new BigDecimal("10.0000").compareTo(metrics.kmPerLiter()));
        assertNull(metrics.kmPerKwh());
        assertNull(metrics.kmPerMj());
        assertNull(metrics.key());
    }

    @Test
    void shouldReturnPluginHybridSuppliesMetricsWithMjAndKey() {
        Version hybridVersion = em.createQuery(
                "SELECT v FROM Version v WHERE v.name = 'GS DM-i' AND v.model.name = 'Song Plus' AND v.model.modelYear = 2024",
                Version.class).getSingleResult();
        ResponseVehicle hybridVehicle = vehicleService.postVehicle(
                new PostRequestVehicle(hybridVersion.getId(), null, 0.0), user);
        supplyRecordService.postSupplyRecordRequest(
                new PostRequestSupplyRecord(
                        hybridVehicle.vehicleId(),
                        LocalDateTime.now().minusDays(1),
                        500.0,
                        FuelType.GASOLINE,
                        10.0, 20.0,
                        new BigDecimal("5.00"), new BigDecimal("1.00")),
                user);
        GetRequestDashboardByVersion request = new GetRequestDashboardByVersion(
                hybridVersion.getId(),
                LocalDateTime.now().minusDays(2),
                LocalDateTime.now());
        ResponsePublicDashboard response = dashboardService.getSuppliesByVersion(request);
        assertEquals(1, response.supplies().size());
        ResponsePublicDashboard.SupplyMetrics metrics = response.supplies().get(0);
        assertEquals(0, new BigDecimal("0.1400").compareTo(metrics.costPerKm()));
        assertNull(metrics.kmPerLiter());
        assertNull(metrics.kmPerKwh());
        assertNotNull(metrics.kmPerMj());
        assertNotNull(metrics.key());
    }

    @Test
    void shouldCallControllerEndpointPostSuppliesByVersion() throws Exception {
        createVehicleWithSupply(400.0);
        String jsonBody = String.format("""
                {
                    "versionId": %d,
                    "startDate": "%s",
                    "endDate": "%s"
                }
                """,
                version.getId(),
                LocalDateTime.now().minusDays(2),
                LocalDateTime.now()
        );
        mockMvc.perform(post("/api/public/get-supplies-by-version")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.supplies.length()").value(1))
                .andExpect(jsonPath("$.supplies[0].costPerKm").value(0.5))
                .andExpect(jsonPath("$.supplies[0].kmPerLiter").value(10.0));
    }

}
