INSERT INTO brand (name)
SELECT v.name
FROM (VALUES
          ('BYD'),
          ('BMW'),
          ('Caoa Chery'),
          ('Chevrolet'),
          ('Citroen'),
          ('Fiat'),
          ('Ford'),
          ('GWM'),
          ('Honda'),
          ('Hyundai'),
          ('Jeep'),
          ('Kia'),
          ('Mercedes-Benz'),
          ('Nissan')
     ) AS v(name)
WHERE NOT EXISTS (
    SELECT 1 FROM brand b WHERE b.name = v.name
);

INSERT INTO models (name, model_year, brand_id)
SELECT v.name, v.model_year, b.id
FROM (VALUES
          ( 'X1', 2024, 'BMW'),
          ( 'X1', 2025, 'BMW'),
          ( 'iX1',2024, 'BMW'),
          ( 'iX1',2025, 'BMW'),
          ( 'X3', 2024, 'BMW'),
          ( 'X3', 2025, 'BMW'),
          ( 'X5', 2024, 'BMW'),
          ( 'X5', 2025, 'BMW'),
          ( '330e',2024,'BMW'),
          ( '330e',2025,'BMW'),
          ( 'i4', 2024, 'BMW'),
          ( 'i4', 2025, 'BMW'),
          ('Série 3',2025,'BMW'),
          ('X1', 2025, 'BMW'),
          ('iX1', 2025, 'BMW'),
          ('Song Plus', 2024, 'BYD'),
          ('Song Plus', 2025, 'BYD'),
          ('Dolphin', 2024, 'BYD'),
          ('Dolphin', 2025, 'BYD'),
          ('Dolphin Mini', 2025, 'BYD'),
          ('Yuan Plus', 2024, 'BYD'),
          ('Yuan Plus', 2025, 'BYD'),
          ('King', 2025, 'BYD'),
          ('Tiggo 5X', 2024, 'Caoa Chery'),
          ('Tiggo 7', 2024, 'Caoa Chery'),
          ('Tiggo 7', 2025, 'Caoa Chery'),
          ('Tiggo 8', 2024, 'Caoa Chery'),
          ('Tiggo 8', 2025, 'Caoa Chery'),
          ('Onix', 2024, 'Chevrolet'),
          ('Onix', 2025, 'Chevrolet'),
          ('Tracker', 2024, 'Chevrolet'),
          ('Tracker', 2025, 'Chevrolet'),
          ('Spin', 2024, 'Chevrolet'),
          ('Spin', 2025, 'Chevrolet'),
          ('Montana', 2024, 'Chevrolet'),
          ('Montana', 2025, 'Chevrolet'),
          ('C3', 2024, 'Citroen'),
          ('C3', 2025, 'Citroen'),
          ('Basalt', 2025, 'Citroen'),
          ('Aircross', 2024, 'Citroen'),
          ('Aircross', 2025, 'Citroen'),
          ('Argo', 2024, 'Fiat'),
          ('Argo', 2025, 'Fiat'),
          ('Cronos', 2024, 'Fiat'),
          ('Cronos', 2025, 'Fiat'),
          ('Pulse', 2024, 'Fiat'),
          ('Pulse', 2025, 'Fiat'),
          ('Fastback', 2024, 'Fiat'),
          ('Fastback', 2025, 'Fiat'),
          ('Maverick', 2024, 'Ford'),
          ('Maverick', 2025, 'Ford'),
          ('Ranger', 2024, 'Ford'),
          ('Ranger', 2025, 'Ford'),
          ('Territory', 2024, 'Ford'),
          ('Territory', 2025, 'Ford'),
          ('Haval H6', 2024, 'GWM'),
          ('Haval H6', 2025, 'GWM'),
          ('Ora 03', 2024, 'GWM'),
          ('Ora 03', 2025, 'GWM'),
          ('Civic', 2024, 'Honda'),
          ('Civic', 2025, 'Honda'),
          ('HR-V', 2024, 'Honda'),
          ('HR-V', 2025, 'Honda'),
          ('City', 2024, 'Honda'),
          ('City', 2025, 'Honda'),
          ('Creta', 2024, 'Hyundai'),
          ('Creta', 2025, 'Hyundai'),
          ('HB20', 2024, 'Hyundai'),
          ('HB20', 2025, 'Hyundai'),
          ('Tucson', 2024, 'Hyundai'),
          ('Compass', 2024, 'Jeep'),
          ('Compass', 2025, 'Jeep'),
          ('Renegade', 2024, 'Jeep'),
          ('Renegade', 2025, 'Jeep'),
          ('Commander', 2024, 'Jeep'),
          ('Commander', 2025, 'Jeep'),
          ('Sportage', 2024, 'Kia'),
          ('Sportage', 2025, 'Kia'),
          ('Niro', 2024, 'Kia'),
          ('Niro', 2025, 'Kia'),
          ('C 200', 2024, 'Mercedes-Benz'),
          ('GLC', 2024, 'Mercedes-Benz'),
          ('GLC', 2025, 'Mercedes-Benz'),
          ('Kicks', 2024, 'Nissan'),
          ('Kicks', 2025, 'Nissan'),
          ('Versa', 2024, 'Nissan'),
          ('Versa', 2025, 'Nissan'),
          ('Sentra', 2024, 'Nissan'),
          ('Sentra', 2025, 'Nissan')
     ) AS v(name, model_year, brand_name)
         JOIN brand b ON b.name = v.brand_name
WHERE NOT EXISTS (
    SELECT 1
    FROM models existing
    WHERE existing.name = v.name
      AND existing.model_year = v.model_year
      AND existing.brand_id = b.id
);

INSERT INTO versions (name, vehicle_type, model_id, tank_capacity, battery_capacity)
SELECT v.version_name, v.vehicle_type, m.id, v.tank_capacity, v.battery_capacity
FROM (VALUES
          ('BMW', 'X1', 2024, 'sDrive20i X-Line', 'COMBUSTION', 54.0, NULL),
          ('BMW', 'X1', 2025, 'sDrive20i X-Line', 'COMBUSTION', 54.0, NULL),
          ('BMW', 'iX1', 2024, 'xDrive30', 'ELECTRIC', NULL, 64.7),
          ('BMW', 'iX1', 2025, 'xDrive30', 'ELECTRIC', NULL, 64.7),
          ('BMW', 'X3', 2024, 'xDrive30i M Sport', 'COMBUSTION', 65.0, NULL),
          ('BMW', 'X3', 2025, 'xDrive30 M Sport', 'COMBUSTION', 65.0, NULL),
          ('BMW', 'X5', 2024, 'xDrive50e M Sport', 'PLUGIN_HYBRID', 69.0, 25.7),
          ('BMW', 'X5', 2025, 'xDrive50e M Sport', 'PLUGIN_HYBRID', 69.0, 25.7),
          ('BMW', '330e', 2024, 'M Sport', 'PLUGIN_HYBRID', 59.0, 19.5),
          ('BMW', '330e', 2025, 'M Sport', 'PLUGIN_HYBRID', 59.0, 19.5),
          ('BMW', 'i4', 2024, 'eDrive40', 'ELECTRIC', NULL, 83.9),
          ('BMW', 'i4', 2025, 'eDrive40', 'ELECTRIC', NULL, 83.9),
          ('BYD', 'Song Plus', 2024, 'GS DM-i', 'PLUGIN_HYBRID', 52.0, 18.3),
          ('BYD', 'Song Plus', 2024, 'GL DM-i', 'PLUGIN_HYBRID', 52.0, 18.3),
          ('BYD', 'Song Plus', 2025, 'Premium DM-i', 'PLUGIN_HYBRID', 52.0, 18.3),
          ('BYD', 'Dolphin', 2024, 'GS', 'ELECTRIC', NULL, 44.9),
          ('BYD', 'Dolphin', 2024, 'Plus', 'ELECTRIC', NULL, 60.5),
          ('BYD', 'Dolphin', 2025, 'GS', 'ELECTRIC', NULL, 44.9),
          ('BYD', 'Dolphin Mini', 2025, 'GL', 'ELECTRIC', NULL, 38.0),
          ('BYD', 'Dolphin Mini', 2025, 'GS', 'ELECTRIC', NULL, 38.0),
          ('BYD', 'Yuan Plus', 2024, 'GS', 'ELECTRIC', NULL, 60.5),
          ('BYD', 'Yuan Plus', 2025, 'GS', 'ELECTRIC', NULL, 60.5),
          ('BYD', 'King', 2025, 'GL DM-i', 'PLUGIN_HYBRID', 48.0, 8.3),
          ('BYD', 'King', 2025, 'GS DM-i', 'PLUGIN_HYBRID', 48.0, 18.3),
          ('Caoa Chery', 'Tiggo 5X', 2024, 'Sport 1.5 Turbo', 'COMBUSTION', 51.0, NULL),
          ('Caoa Chery', 'Tiggo 5X', 2024, 'Pro 1.5 Turbo', 'COMBUSTION', 51.0, NULL),
          ('Caoa Chery', 'Tiggo 7', 2024, 'Sport 1.5 Turbo', 'COMBUSTION', 51.0, NULL),
          ('Caoa Chery', 'Tiggo 7', 2024, 'Pro Max Drive', 'COMBUSTION', 51.0, NULL),
          ('Caoa Chery', 'Tiggo 7', 2025, 'Pro Hybrid', 'PLUGIN_HYBRID', 51.0, 1.8),
          ('Caoa Chery', 'Tiggo 8', 2024, 'Pro 1.6 Turbo', 'COMBUSTION', 51.0, NULL),
          ('Caoa Chery', 'Tiggo 8', 2025, 'Pro Plug-in Hybrid', 'PLUGIN_HYBRID', 45.0, 19.3),
          ('Chevrolet', 'Onix', 2024, '1.0 MT', 'COMBUSTION', 44.0, NULL),
          ('Chevrolet', 'Onix', 2024, '1.0 Turbo AT', 'COMBUSTION', 44.0, NULL),
          ('Chevrolet', 'Onix', 2025, '1.0 MT', 'COMBUSTION', 44.0, NULL),
          ('Chevrolet', 'Onix', 2025, '1.0 Turbo LTZ AT', 'COMBUSTION', 44.0, NULL),
          ('Chevrolet', 'Tracker', 2024, '1.0 Turbo AT', 'COMBUSTION', 44.0, NULL),
          ('Chevrolet', 'Tracker', 2024, '1.2 Turbo Premier', 'COMBUSTION', 44.0, NULL),
          ('Chevrolet', 'Tracker', 2025, '1.0 Turbo LT', 'COMBUSTION', 44.0, NULL),
          ('Chevrolet', 'Tracker', 2025, '1.2 Turbo Premier', 'COMBUSTION', 44.0, NULL),
          ('Chevrolet', 'Spin', 2024, 'LT 1.8 AT', 'COMBUSTION', 53.0, NULL),
          ('Chevrolet', 'Spin', 2025, 'Premier 1.8 AT', 'COMBUSTION', 53.0, NULL),
          ('Chevrolet', 'Montana', 2024, 'LT 1.2 Turbo', 'COMBUSTION', 44.0, NULL),
          ('Chevrolet', 'Montana', 2025, 'Premier 1.2 Turbo', 'COMBUSTION', 44.0, NULL),
          ('Citroen', 'C3', 2024, 'Live 1.0', 'COMBUSTION', 47.0, NULL),
          ('Citroen', 'C3', 2024, 'Feel 1.0', 'COMBUSTION', 47.0, NULL),
          ('Citroen', 'C3', 2025, 'Live Pack 1.0', 'COMBUSTION', 47.0, NULL),
          ('Citroen', 'Basalt', 2025, 'Feel 1.0', 'COMBUSTION', 47.0, NULL),
          ('Citroen', 'Basalt', 2025, 'Shine 1.0 Turbo', 'COMBUSTION', 47.0, NULL),
          ('Citroen', 'Aircross', 2024, 'Feel 1.0', 'COMBUSTION', 47.0, NULL),
          ('Citroen', 'Aircross', 2025, 'Shine 1.0 Turbo', 'COMBUSTION', 47.0, NULL),
          ('Fiat', 'Argo', 2024, '1.0', 'COMBUSTION', 47.0, NULL),
          ('Fiat', 'Argo', 2024, 'Drive 1.3 CVT', 'COMBUSTION', 47.0, NULL),
          ('Fiat', 'Argo', 2025, '1.0', 'COMBUSTION', 47.0, NULL),
          ('Fiat', 'Cronos', 2024, 'Drive 1.3', 'COMBUSTION', 48.0, NULL),
          ('Fiat', 'Cronos', 2025, 'Precision 1.3 CVT', 'COMBUSTION', 48.0, NULL),
          ('Fiat', 'Pulse', 2024, 'Drive 1.3', 'COMBUSTION', 47.0, NULL),
          ('Fiat', 'Pulse', 2024, 'Audace Turbo 200 Hybrid', 'PLUGIN_HYBRID', 47.0, 0.13),
          ('Fiat', 'Pulse', 2025, 'Impetus Turbo 200 Hybrid', 'PLUGIN_HYBRID', 47.0, 0.13),
          ('Fiat', 'Fastback', 2024, 'Audace Turbo 200', 'COMBUSTION', 47.0, NULL),
          ('Fiat', 'Fastback', 2025, 'Limited Edition Turbo 270', 'COMBUSTION', 47.0, NULL),
          ('Ford', 'Maverick', 2024, 'Lariat FX4 2.0 EcoBoost', 'COMBUSTION', 62.0, NULL),
          ('Ford', 'Maverick', 2025, 'Lariat Hybrid', 'PLUGIN_HYBRID', 52.0, 1.1),
          ('Ford', 'Ranger', 2024, 'XL 2.0 Diesel', 'COMBUSTION', 80.0, NULL),
          ('Ford', 'Ranger', 2024, 'XLT 3.0 V6 Diesel', 'COMBUSTION', 80.0, NULL),
          ('Ford', 'Ranger', 2025, 'Limited 3.0 V6 Diesel', 'COMBUSTION', 80.0, NULL),
          ('Ford', 'Territory', 2024, 'Titanium 1.5 Turbo', 'COMBUSTION', 60.0, NULL),
          ('Ford', 'Territory', 2025, 'Titanium 1.5 Turbo', 'COMBUSTION', 60.0, NULL),
          ('GWM', 'Haval H6', 2024, 'HEV2', 'PLUGIN_HYBRID', 61.0, 1.7),
          ('GWM', 'Haval H6', 2024, 'PHEV34', 'PLUGIN_HYBRID', 55.0, 34.0),
          ('GWM', 'Haval H6', 2025, 'PHEV34', 'PLUGIN_HYBRID', 55.0, 34.0),
          ('GWM', 'Haval H6', 2025, 'PHEV19', 'PLUGIN_HYBRID', 55.0, 19.0),
          ('GWM', 'Ora 03', 2024, 'Skin', 'ELECTRIC', NULL, 48.0),
          ('GWM', 'Ora 03', 2024, 'GT', 'ELECTRIC', NULL, 63.0),
          ('GWM', 'Ora 03', 2025, 'Skin', 'ELECTRIC', NULL, 48.0),
          ('Honda', 'Civic', 2024, 'Advanced Hybrid', 'PLUGIN_HYBRID', 40.0, 1.05),
          ('Honda', 'Civic', 2025, 'Advanced Hybrid', 'PLUGIN_HYBRID', 40.0, 1.05),
          ('Honda', 'HR-V', 2024, 'EX 1.5', 'COMBUSTION', 50.0, NULL),
          ('Honda', 'HR-V', 2024, 'Touring 1.5 Turbo', 'COMBUSTION', 50.0, NULL),
          ('Honda', 'HR-V', 2025, 'EXL 1.5', 'COMBUSTION', 50.0, NULL),
          ('Honda', 'City', 2024, 'Sedan LX 1.5', 'COMBUSTION', 39.5, NULL),
          ('Honda', 'City', 2025, 'Hatchback Touring 1.5', 'COMBUSTION', 39.5, NULL),
          ('Hyundai', 'Creta', 2024, 'Comfort 1.0 Turbo', 'COMBUSTION', 50.0, NULL),
          ('Hyundai', 'Creta', 2024, 'Ultimate 2.0', 'COMBUSTION', 50.0, NULL),
          ('Hyundai', 'Creta', 2025, 'Platinum 1.0 Turbo', 'COMBUSTION', 50.0, NULL),
          ('Hyundai', 'HB20', 2024, 'Comfort 1.0', 'COMBUSTION', 50.0, NULL),
          ('Hyundai', 'HB20', 2024, 'Platinum 1.0 Turbo AT', 'COMBUSTION', 50.0, NULL),
          ('Hyundai', 'HB20', 2025, 'Comfort Plus 1.0', 'COMBUSTION', 50.0, NULL),
          ('Hyundai', 'Tucson', 2024, 'Limited 1.6 Turbo', 'COMBUSTION', 54.0, NULL),
          ('Jeep', 'Compass', 2024, 'Longitude T270', 'COMBUSTION', 60.0, NULL),
          ('Jeep', 'Compass', 2024, 'Limited T270', 'COMBUSTION', 60.0, NULL),
          ('Jeep', 'Compass', 2025, 'Blackhawk Hurricane', 'COMBUSTION', 60.0, NULL),
          ('Jeep', 'Renegade', 2024, 'Sport T270', 'COMBUSTION', 55.0, NULL),
          ('Jeep', 'Renegade', 2025, 'Longitude T270', 'COMBUSTION', 55.0, NULL),
          ('Jeep', 'Commander', 2024, 'Limited T270', 'COMBUSTION', 60.0, NULL),
          ('Jeep', 'Commander', 2025, 'Overland Hurricane', 'COMBUSTION', 60.0, NULL),
          ('Kia', 'Sportage', 2024, 'EX 1.6 Turbo MHEV', 'PLUGIN_HYBRID', 54.0, 0.44),
          ('Kia', 'Sportage', 2025, 'EX Prestige 1.6 Turbo', 'COMBUSTION', 54.0, NULL),
          ('Kia', 'Niro', 2024, 'SX Prestige Hybrid', 'PLUGIN_HYBRID', 42.0, 1.32),
          ('Kia', 'Niro', 2025, 'SX Prestige Hybrid', 'PLUGIN_HYBRID', 42.0, 1.32),
          ('Mercedes-Benz', 'C 200', 2024, 'Avantgarde 1.5 EQ Boost', 'COMBUSTION', 66.0, NULL),
          ('Mercedes-Benz', 'GLC', 2024, '300 4MATIC', 'COMBUSTION', 62.0, NULL),
          ('Mercedes-Benz', 'GLC', 2025, '300 4MATIC', 'COMBUSTION', 62.0, NULL),
          ('Nissan', 'Kicks', 2024, 'Active 1.6 CVT', 'COMBUSTION', 41.0, NULL),
          ('Nissan', 'Kicks', 2024, 'Exclusive 1.6 CVT', 'COMBUSTION', 41.0, NULL),
          ('Nissan', 'Kicks', 2025, 'Sense 1.6 CVT', 'COMBUSTION', 41.0, NULL),
          ('Nissan', 'Versa', 2024, 'Sense 1.6 CVT', 'COMBUSTION', 41.0, NULL),
          ('Nissan', 'Versa', 2025, 'Advance 1.6 CVT', 'COMBUSTION', 41.0, NULL),
          ('Nissan', 'Sentra', 2024, 'Advance 2.0 CVT', 'COMBUSTION', 47.0, NULL),
          ('Nissan', 'Sentra', 2025, 'Exclusive 2.0 CVT', 'COMBUSTION', 47.0, NULL)
     ) AS v(brand_name, model_name, model_year, version_name, vehicle_type, tank_capacity, battery_capacity)
         JOIN brand b ON b.name = v.brand_name
         JOIN models m ON m.brand_id = b.id
    AND m.name = v.model_name
    AND m.model_year = v.model_year
WHERE NOT EXISTS (
    SELECT 1
    FROM versions existing
    WHERE existing.model_id = m.id
      AND existing.name = v.version_name
);

INSERT INTO brand (name)
SELECT v.name
FROM (VALUES
          ('Peugeot'),
          ('Renault'),
          ('Toyota'),
          ('Volkswagen'),
          ('Volvo')
     ) AS v(name)
WHERE NOT EXISTS (
    SELECT 1 FROM brand b WHERE b.name = v.name
);

INSERT INTO models (name, model_year, brand_id)
SELECT v.name, v.model_year, b.id
FROM (VALUES
          ('208', 2024, 'Peugeot'),
          ('208', 2025, 'Peugeot'),
          ('2008', 2024, 'Peugeot'),
          ('2008', 2025, 'Peugeot'),
          ('Kwid', 2024, 'Renault'),
          ('Kwid', 2025, 'Renault'),
          ('Duster', 2024, 'Renault'),
          ('Duster', 2025, 'Renault'),
          ('Kardian', 2024, 'Renault'),
          ('Kardian', 2025, 'Renault'),
          ('Corolla', 2024, 'Toyota'),
          ('Corolla', 2025, 'Toyota'),
          ('Corolla Cross', 2024, 'Toyota'),
          ('Corolla Cross', 2025, 'Toyota'),
          ('Hilux', 2024, 'Toyota'),
          ('Hilux', 2025, 'Toyota'),
          ('Yaris Cross', 2026, 'Toyota'),
          ('Polo', 2024, 'Volkswagen'),
          ('Polo', 2025, 'Volkswagen'),
          ('T-Cross', 2024, 'Volkswagen'),
          ('T-Cross', 2025, 'Volkswagen'),
          ('Nivus', 2024, 'Volkswagen'),
          ('Nivus', 2025, 'Volkswagen'),
          ('Tiguan', 2024, 'Volkswagen'),
          ('Tiguan', 2025, 'Volkswagen'),
          ('EX30', 2024, 'Volvo'),
          ('EX30', 2025, 'Volvo'),
          ('XC60', 2024, 'Volvo'),
          ('XC60', 2025, 'Volvo')
     ) AS v(name, model_year, brand_name)
         JOIN brand b ON b.name = v.brand_name
WHERE NOT EXISTS (
    SELECT 1
    FROM models existing
    WHERE existing.name = v.name
      AND existing.model_year = v.model_year
      AND existing.brand_id = b.id
);

INSERT INTO versions (
    name,
    vehicle_type,
    model_id,
    tank_capacity,
    battery_capacity
)
SELECT
    v.version_name,
    v.vehicle_type,
    m.id,
    v.tank_capacity,
    v.battery_capacity
FROM (VALUES
          ('Peugeot', '208', 2024, 'Like 1.0', 'COMBUSTION', 47.0, NULL::NUMERIC),
          ('Peugeot', '208', 2024, 'Style 1.0', 'COMBUSTION', 47.0, NULL::NUMERIC),
          ('Peugeot', '208', 2025, 'Allure 1.0 Turbo', 'COMBUSTION', 47.0, NULL::NUMERIC),
          ('Peugeot', '2008', 2024, 'Allure 1.0 Turbo', 'COMBUSTION', 47.0, NULL::NUMERIC),
          ('Peugeot', '2008', 2024, 'GT 1.0 Turbo', 'COMBUSTION', 47.0, NULL::NUMERIC),
          ('Peugeot', '2008', 2025, 'GT 1.0 Turbo', 'COMBUSTION', 47.0, NULL::NUMERIC),
          ('Renault', 'Kwid', 2024, 'Zen 1.0', 'COMBUSTION', 38.0, NULL::NUMERIC),
          ('Renault', 'Kwid', 2024, 'Intense 1.0', 'COMBUSTION', 38.0, NULL::NUMERIC),
          ('Renault', 'Kwid', 2025, 'Iconic 1.0', 'COMBUSTION', 38.0, NULL::NUMERIC),
          ('Renault', 'Duster', 2024, 'Intense 1.6 CVT', 'COMBUSTION', 50.0, NULL::NUMERIC),
          ('Renault', 'Duster', 2024, 'Iconic Plus 1.3 Turbo', 'COMBUSTION', 50.0, NULL::NUMERIC),
          ('Renault', 'Duster', 2025, 'Iconic Plus 1.3 Turbo', 'COMBUSTION', 50.0, NULL::NUMERIC),
          ('Renault', 'Kardian', 2024, 'Evolution 1.0 Turbo', 'COMBUSTION', 50.0, NULL::NUMERIC),
          ('Renault', 'Kardian', 2024, 'Techno 1.0 Turbo', 'COMBUSTION', 50.0, NULL::NUMERIC),
          ('Renault', 'Kardian', 2025, 'Premiere Edition 1.0 Turbo', 'COMBUSTION', 50.0, NULL::NUMERIC),
          ('Toyota', 'Corolla', 2024, 'GLi 2.0', 'COMBUSTION', 50.0, NULL::NUMERIC),
          ('Toyota', 'Corolla', 2024, 'XEi 2.0', 'COMBUSTION', 50.0, NULL::NUMERIC),
          ('Toyota', 'Corolla', 2025, 'Altis Premium Hybrid', 'COMBUSTION', 43.0, NULL::NUMERIC),
          ('Toyota', 'Corolla', 2025, 'XEi 2.0', 'COMBUSTION', 50.0, NULL::NUMERIC),
          ('Toyota', 'Corolla Cross', 2024, 'XR 2.0', 'COMBUSTION', 47.0, NULL::NUMERIC),
          ('Toyota', 'Corolla Cross', 2024, 'XRE 2.0', 'COMBUSTION', 47.0, NULL::NUMERIC),
          ('Toyota', 'Corolla Cross', 2025, 'XRX Hybrid', 'COMBUSTION', 36.0, NULL::NUMERIC),
          ('Toyota', 'Hilux', 2024, 'SR 2.8 Diesel', 'COMBUSTION', 80.0, NULL::NUMERIC),
          ('Toyota', 'Hilux', 2024, 'SRX 2.8 Diesel', 'COMBUSTION', 80.0, NULL::NUMERIC),
          ('Toyota', 'Hilux', 2025, 'SRV 2.8 Diesel', 'COMBUSTION', 80.0, NULL::NUMERIC),
          ('Toyota', 'Yaris Cross', 2026, 'XR 1.5', 'COMBUSTION', 36.0, NULL::NUMERIC),
          ('Toyota', 'Yaris Cross', 2026, 'XRX Hybrid', 'COMBUSTION', 36.0, NULL::NUMERIC),
          ('Volkswagen', 'Polo', 2024, 'Track 1.0 MPI', 'COMBUSTION', 49.0, NULL::NUMERIC),
          ('Volkswagen', 'Polo', 2024, 'Comfortline 1.0 TSI', 'COMBUSTION', 49.0, NULL::NUMERIC),
          ('Volkswagen', 'Polo', 2025, 'Highline 1.0 TSI', 'COMBUSTION', 49.0, NULL::NUMERIC),
          ('Volkswagen', 'T-Cross', 2024, 'Sense 1.0 TSI', 'COMBUSTION', 49.0, NULL::NUMERIC),
          ('Volkswagen', 'T-Cross', 2024, 'Highline 1.4 TSI', 'COMBUSTION', 49.0, NULL::NUMERIC),
          ('Volkswagen', 'T-Cross', 2025, 'Comfortline 1.0 TSI', 'COMBUSTION', 49.0, NULL::NUMERIC),
          ('Volkswagen', 'Nivus', 2024, 'Comfortline 1.0 TSI', 'COMBUSTION', 49.0, NULL::NUMERIC),
          ('Volkswagen', 'Nivus', 2024, 'Highline 1.0 TSI', 'COMBUSTION', 49.0, NULL::NUMERIC),
          ('Volkswagen', 'Nivus', 2025, 'GTS 1.4 TSI', 'COMBUSTION', 49.0, NULL::NUMERIC),
          ('Volkswagen', 'Tiguan', 2024, 'Allspace R-Line 2.0 TSI', 'COMBUSTION', 58.0, NULL::NUMERIC),
          ('Volkswagen', 'Tiguan', 2025, 'R-Line 2.0 TSI', 'COMBUSTION', 58.0, NULL::NUMERIC),
          ('Volvo', 'EX30', 2024, 'Single Motor Extended Range', 'ELECTRIC', NULL, 69.0),
          ('Volvo', 'EX30', 2024, 'Twin Motor Performance', 'ELECTRIC', NULL, 69.0),
          ('Volvo', 'EX30', 2025, 'Single Motor Extended Range', 'ELECTRIC', NULL, 69.0),
          ('Volvo', 'EX30', 2025, 'Twin Motor Performance', 'ELECTRIC', NULL, 69.0),
          ('Volvo', 'XC60', 2024, 'B5 Plus', 'COMBUSTION', 71.0, NULL::NUMERIC),
          ('Volvo', 'XC60', 2024, 'B5 Ultimate', 'COMBUSTION', 71.0, NULL::NUMERIC),
          ('Volvo', 'XC60', 2025, 'T8 Plug-in Hybrid', 'PLUGIN_HYBRID', 71.0, 18.8)
     ) AS v(
            brand_name,
            model_name,
            model_year,
            version_name,
            vehicle_type,
            tank_capacity,
            battery_capacity
    )
         JOIN brand b ON b.name = v.brand_name
         JOIN models m
              ON m.brand_id = b.id
                  AND m.name = v.model_name
                  AND m.model_year = v.model_year
WHERE NOT EXISTS (
    SELECT 1
    FROM versions existing
    WHERE existing.model_id = m.id
      AND existing.name = v.version_name
);

UPDATE versions
SET vehicle_type = 'COMBUSTION', battery_capacity = NULL
WHERE vehicle_type = 'PLUGIN_HYBRID' AND battery_capacity < 5;