CREATE TABLE "vessel_type"(
                              "vessel_type_id" INT NOT NULL,
                              "name" VARCHAR(255),
                              CONSTRAINT "PK_vessel_type" PRIMARY KEY ("vessel_type_id")
);

CREATE TABLE "ship"(
                       "mmsi" INT ,
                       "call_sign" VARCHAR(60) NOT NULL ,
                       "ship_imo_id" INT NOT NULL,
                       "ship_name" VARCHAR(60) NOT NULL,
                       "nr_energy_gens" INT NOT NULL,
                       "gen_po" FLOAT NOT NULL,
                       "length" FLOAT NOT NULL,
                       "width" FLOAT NOT NULL,
                       "capacity" FLOAT NOT NULL,
                       "draft" FLOAT NOT NULL,
                       "vessel_type" int NOT NULL,
                       CONSTRAINT PK_ship  PRIMARY KEY("mmsi"),
                       CONSTRAINT "FK_ship.vessel" FOREIGN KEY ("vessel_type")
                           REFERENCES "vessel_type"("vessel_type_id"),
                       CONSTRAINT PK_ship_unique UNIQUE ("call_sign", "ship_imo_id"),
                       CONSTRAINT CK_ship_mmsi CHECK ("mmsi"<1000000000 AND "mmsi">99999999),
                       CONSTRAINT CK_ship_ship_imo_id CHECK  ("ship_imo_id"<10000000 AND "ship_imo_id">999999),
                       CONSTRAINT CK_ship_nr_energy_gens CHECK  ("nr_energy_gens">0),
                       CONSTRAINT CK_ship_gen_po CHECK  ("gen_po">0),
                       CONSTRAINT CK_ship_length CHECK  ("length">0),
                       CONSTRAINT CK_ship_width CHECK  ("width">0),
                       CONSTRAINT CK_ship_capacity CHECK  ("capacity">0),
                       CONSTRAINT CK_ship_draft CHECK  ("draft">0),
                       CONSTRAINT CK_ship_vessel_type CHECK  ("vessel_type"<100)

);

-- FAZER O TRIGGER DO FROM PORTWAREHOUSE ---------------------------
CREATE TABLE "port_warehouse" (
                                  "port_warehouse_id" INT,
                                  "name" VARCHAR(60) NOT NULL,
                                  "continent" VARCHAR(60) NOT NULL,
                                  "country" VARCHAR(60) NOT NULL,
                                  "type" VARCHAR(60) NOT NULL,
                                  "lat" FLOAT NOT NULL,
                                  "log" FLOAT NOT NULL,
                                  "capacity" INT NOT NULL,
                                  "from_port" INT,
                                  CONSTRAINT PK_port_warehouse  PRIMARY KEY ("port_warehouse_id"),
                                  CONSTRAINT "FK_port_warehouse.from" FOREIGN KEY ("from_port")
                                      REFERENCES "port_warehouse"("port_warehouse_id"),
                                  CONSTRAINT CK_port_warehouse_lat CHECK ("lat">=-90 AND "lat"<=90),
                                  CONSTRAINT CK_port_warehouse_log CHECK ("log" >=-180 AND "log"<=180)
);

--FAZER AUTO_INCREMENT------------------------------------------DONE
CREATE TABLE "container" (
                             "container_id" int,
                             "ISOCode" varchar(4) NOT NULL,
                             "checkDigit" int NOT NULL,
                             "maxWeightC" FLOAT NOT NULL,
                             "weightC" FLOAT NOT NULL,
                             "maxVol" FLOAT NOT NULL,
                             "repairRecomendation" NUMBER(1,0) NOT NULL,
                             "refrigeration" FLOAT,
                             CONSTRAINT "PK_container"
                                 PRIMARY KEY ("container_id"),
                             CONSTRAINT "CK_container_ISOCode" CHECK (length("ISOCode")=4),
                             CONSTRAINT "CK_container_maxWeightC" CHECK ("maxWeightC">0),
                             CONSTRAINT "CK_container_weightC" CHECK ("weightC">0),
                             CONSTRAINT "CK_container_maxVol" CHECK ("maxVol">0)
);

--FAZER AUTO_INCREMENT------------------------------------------DONE
CREATE TABLE "certificate" (
                               "certificate_id" int,
                               "name" VARCHAR(60) NOT NULL,
                               CONSTRAINT "PK_certificate"
                                   PRIMARY KEY ("certificate_id")
);

--FAZER AUTO_INCREMENT------------------------------------------DONE
CREATE TABLE "role" (
                        "role_id" INT,
                        "name" VARCHAR(60) NOT NULL,
                        CONSTRAINT "PK_roles"
                            PRIMARY KEY ("role_id")
);

CREATE TABLE "truck" (
                         "plate" VARCHAR(8),
                         "fuelCapacity" FLOAT,
                         CONSTRAINT "PK_truck"
                             PRIMARY KEY ("plate"),
                         CONSTRAINT "CK_truck_plate" CHECK (REGEXP_LIKE("plate",'^(([A-Z]{2}:\d{2}:(\d{2}|[A-Z]{2}))|(\d{2}:(\d{2}:[A-Z]{2}|[A-Z]{2}:\d{2})))$$')) ,
                         CONSTRAINT "CK_truck_fuelCapacity" CHECK ("fuelCapacity">0)
);
--FAZER AUTO_INCREMENT------------------------------------------DONE
CREATE TABLE "vehicle" (
                           "vehicle_id" INT NOT NULL,
                           "ship_id" INT,
                           "plate" VARCHAR(255),
                           CONSTRAINT "PK_vehicle" PRIMARY KEY ("vehicle_id"),
                           CONSTRAINT "FK_vehicle.ship_id"
                               FOREIGN KEY ("ship_id")
                                   REFERENCES "ship"("mmsi"),
                           CONSTRAINT "FK_vehicle.plate"
                               FOREIGN KEY ("plate")
                                   REFERENCES "truck"("plate")
);
--FAZER AUTO_INCREMENT------------------------------------------DONE
CREATE TABLE "cargo_manifest" (
                                  "cargo_manifesto_id" int ,
                                  "vehicle_id" int ,
                                  "operation_type" varchar(30) NOT NULL,
                                  "entry_date" DATE NOT NULL,
                                  "destiny" int NOT NULL,
                                  CONSTRAINT "FK_cargo_manifest.ship_id"
                                      FOREIGN KEY ("vehicle_id")
                                          REFERENCES "vehicle"("vehicle_id"),
                                  CONSTRAINT PK_cargo_manifesto
                                      PRIMARY KEY ("cargo_manifesto_id"),
                                  CONSTRAINT "FK_cargo_manifest.destiny"
                                      FOREIGN KEY ("destiny")
                                          REFERENCES "port_warehouse"("port_warehouse_id"),
                                  CONSTRAINT CK_operation_type CHECK ("operation_type" = 'LOAD' OR "operation_type" = 'UNLOAD' OR "operation_type" = 'load' OR "operation_type" = 'unload')

);



CREATE TABLE "container_certificate" (
                                         "container_id" int,
                                         "certificate_id" int,
                                         CONSTRAINT "FK_container_certificate.container_id"
                                             FOREIGN KEY ("container_id")
                                                 REFERENCES "container"("container_id"),
                                         CONSTRAINT "FK_container_certificate.certificate_id"
                                             FOREIGN KEY ("certificate_id")
                                                 REFERENCES "certificate"("certificate_id"),
                                         PRIMARY KEY ("container_id", "certificate_id")
);


CREATE TABLE "ship_data" (
                             "ship_id" INT,
                             "Data" DATE,
                             "latitude" FLOAT NOT NULL,
                             "longitude" FLOAT NOT NULL,
                             "SOG" FLOAT NOT NULL,
                             "COG" FLOAT NOT NULL,
                             "heading" FLOAT NOT NULL,
                             "cargo" VARCHAR(60) NOT NULL,
                             "transceiver_class" VARCHAR(60) NOT NULL,
                             CONSTRAINT "PK_ship_data"  PRIMARY KEY ("ship_id", "Data"),
                             CONSTRAINT "FK_ ship_data.ship_id"
                                 FOREIGN KEY ("ship_id")
                                     REFERENCES "ship"("mmsi"),
                             CONSTRAINT "CK_latitude_menor" CHECK ("latitude"<=90),
                             CONSTRAINT "CK_latitude_maior" CHECK ("latitude">=-90),
                             CONSTRAINT "CK_Longitude_menor" CHECK ("longitude"<=180),
                             CONSTRAINT "CK_Longitude_maior" CHECK ("longitude">=-180),
                             CONSTRAINT "CK_SOG" CHECK ("SOG">=0),
                             CONSTRAINT "CK_COG_menor" CHECK ("COG">=0),
                             CONSTRAINT "CK_COG_maior" CHECK ("COG"<=359)
);


CREATE TABLE "truck_data" (
                              "plate" VARCHAR(8),
                              "date" DATE NOT NULL,
                              "lat" FLOAT NOT NULL,
                              "log" FLOAT NOT NULL,
                              CONSTRAINT "PK_truck_data"
                                  PRIMARY KEY ("plate", "date"),
                              CONSTRAINT "FK_truck_data_plate.plate"
                                  FOREIGN KEY ("plate")
                                      REFERENCES "truck"("plate"),
                              CONSTRAINT "CK_truck_data_lat_menor" CHECK ("lat"<=90),
                              CONSTRAINT "CK_truck_data_lat_maior" CHECK ("lat">=-90),
                              CONSTRAINT "CK_truck_data_log_menor" CHECK ("log"<=90),
                              CONSTRAINT "CK_truck_data_log_maior" CHECK ("log">=-90),
                              CONSTRAINT "CK_truck_data_plate" CHECK (REGEXP_LIKE("plate",'^(([A-Z]{2}:\d{2}:(\d{2}|[A-Z]{2}))|(\d{2}:(\d{2}:[A-Z]{2}|[A-Z]{2}:\d{2})))$$'))
);


--FAZER AUTO_INCREMENT------------------------------------------DONE
CREATE TABLE "trip" (
                        "trip_id" INT,
                        "vehicle_id" INT,
                        "source" VARCHAR(60) NOT NULL,
                        "destiny" VARCHAR(60) NOT NULL,
                        "start_date" DATE NOT NULL,
                        "end_date" DATE,
                        "estimated_date" DATE NOT NULL,
                        CONSTRAINT "PK_trip_id"
                            PRIMARY KEY ("trip_id"),
                        CONSTRAINT "FK_trip.vehicle_id" FOREIGN KEY ("vehicle_id")
                            REFERENCES "vehicle"("vehicle_id")
);

CREATE TABLE "trip_stop" (
                             "trip_id" INT,
                             "port_wharehouse_id" INT,
                             "data" DATE ,
                             "estimate_date" DATE NOT NULL,
                             "cargo_manifest_id" INT NOT NULL,
                             CONSTRAINT "PK_trip_stops_id"
                                 PRIMARY KEY ("trip_id", "cargo_manifest_id"),
                             CONSTRAINT "FK_trip_id.trip_id"
                                 FOREIGN KEY ("trip_id")
                                     REFERENCES "trip"("trip_id"),
                             CONSTRAINT "FK_trip_id.port_wharehouse_id"
                                 FOREIGN KEY ("port_wharehouse_id")
                                     REFERENCES "port_warehouse"("port_warehouse_id"),
                             CONSTRAINT "FK_trip_id.cargo_manifest_id"
                                 FOREIGN KEY ("cargo_manifest_id")
                                     REFERENCES "cargo_manifest"("cargo_manifesto_id")

);

CREATE TABLE "user" (
                        "user_id" INT,
                        "role_id" INT,
                        "name" VARCHAR(100),
                        "email" VARCHAR(100),
                        "pass" VARCHAR(60),
                        CONSTRAINT "PK_user_id"
                            PRIMARY KEY ("user_id"),
                        CONSTRAINT "FK_user_id.role_id"
                            FOREIGN KEY ("role_id")
                                REFERENCES "role"("role_id")
);

CREATE TABLE "employe_schedule" (
                                    "employe_id" INT,
                                    "port_wharehouse_id" INT,
                                    "day_of_the_week" VARCHAR(60) NOT NULL,
                                    hour VARCHAR(20) NOT NULL,
                                    CONSTRAINT "PK_employe_schedule"
                                        PRIMARY KEY ("employe_id", "port_wharehouse_id"),
                                    CONSTRAINT "FK_employe_schedule.employe_id"
                                        FOREIGN KEY ("employe_id")
                                            REFERENCES "user"("user_id"),
                                    CONSTRAINT "FK_employe_schedule.port_wharehouse_id"
                                        FOREIGN KEY ("port_wharehouse_id")
                                            REFERENCES "port_warehouse"("port_warehouse_id"),
                                    CONSTRAINT "CK_employe_schedule_day_of_the_week" CHECK ("day_of_the_week" = 'Monday' OR "day_of_the_week" = 'Tuesday' OR "day_of_the_week"='Wednesday' OR "day_of_the_week"='Thursday'
                                        OR "day_of_the_week"='Friday' OR "day_of_the_week"='Saturday' OR "day_of_the_week"='Sunday' OR
                                                                                            "day_of_the_week" = 'monday' OR "day_of_the_week" = 'tuesday' OR "day_of_the_week"='wednesday' OR "day_of_the_week"='thursday'
                                        OR "day_of_the_week"='friday' OR "day_of_the_week"='saturday' OR "day_of_the_week"='sunday'),
                                    CONSTRAINT  "CK_employe_schedule_hour" CHECK (length(hour)=5)
);
--FAZER AUTO_INCREMENT------------------------------------------DONE
--TRIGER PARA COLOCAR REGISTER_DATE AUTOMATICA ------------------ DONE
CREATE TABLE "registo_container" (
                                     "registo_id" int ,
                                     "container_id" INT,
                                     "user_id" INT,
                                     "date" DATE NOT NULL,
                                     "register_date" DATE NOT NULL,
                                     "source" int NOT NULL,
                                     "destiny" int NOT NULL,
                                     "delivered" VARCHAR(255),
                                     CONSTRAINT "PK_Registos_container"
                                         PRIMARY KEY ("registo_id"),
                                     CONSTRAINT "FK_registos_container_id.container_id"
                                         FOREIGN KEY ("container_id")
                                             REFERENCES "container"("container_id"),
                                     CONSTRAINT "FK_container_id.user_id"
                                         FOREIGN KEY ("user_id")
                                             REFERENCES "user"("user_id"),
                                     CONSTRAINT "FK_registo_container.source"
                                         FOREIGN KEY ("source")
                                             REFERENCES "port_warehouse"("port_warehouse_id"),
                                     CONSTRAINT "FK_registos_container.destiny"
                                         FOREIGN KEY ("destiny")
                                             REFERENCES "port_warehouse"("port_warehouse_id"),
                                     CONSTRAINT "CK_delivered" CHECK ("delivered" = 'YES' OR "delivered" = 'NO' OR "delivered" = 'yes' OR "delivered" = 'no')
);

CREATE TABLE "cargo_manifest_container" (
                                            "registo_id" INT,
                                            "cargo_manifesto_id" INT,
                                            "container_position_x" INT,
                                            "container_position_y" INT,
                                            "container_position_z" INT,
                                            "container_gross_weigth" FLOAT,
                                            CONSTRAINT "FK_cargo_manifesto_container.registo_id"
                                                FOREIGN KEY ("registo_id")
                                                    REFERENCES "registo_container"("registo_id"),
                                            CONSTRAINT "FK_container_manifesto_container.cargo_manifesto_id"
                                                FOREIGN KEY ("cargo_manifesto_id")
                                                    REFERENCES "cargo_manifest"("cargo_manifesto_id"),
                                            CONSTRAINT PK_registo_id
                                                PRIMARY KEY ("registo_id","cargo_manifesto_id")
);
CREATE TABLE "country"(
                          "name" VARCHAR(255) NOT NULL,
                          "continent" VARCHAR(255) NOT NULL,
                          "alpha2code" VARCHAR(2) NOT NULL,
                          "alpha3code" VARCHAR(3) NOT NULL,
                          "population" FLOAT NOT NULL,
                          "capital" VARCHAR(255) NOT NULL,
                          "lat" FLOAT NOT NULL,
                          "lon" FLOAT NOT NULL,
                          CONSTRAINT PK_country
                              PRIMARY KEY ("alpha3code"),
                          CONSTRAINT "CK_country_latitude_menor" CHECK ("lat"<=90),
                          CONSTRAINT "CK_country_latitude_maior" CHECK ("lat">=-90),
                          CONSTRAINT "CK_country_Longitude_menor" CHECK ("lon"<=180),
                          CONSTRAINT "CK_country_Longitude_maior" CHECK ("lon">=-180)
);

--FAZER AUTO_INCREMENT------------------------------------------DONE
CREATE TABLE "border"(
                         "border_id" INT NOT NULL,
                         "country1" VARCHAR(3) NOT NULL,
                         "country2" VARCHAR(3) NOT NULL,
                         CONSTRAINT PK_border
                             PRIMARY KEY ("border_id"),
                         CONSTRAINT "FK_border.country1"
                             FOREIGN KEY ("country1")
                                 REFERENCES "country"("alpha3code"),
                         CONSTRAINT "FK_border.country2"
                             FOREIGN KEY ("country2")
                                 REFERENCES "country"("alpha3code")

);

--FAZER AUTO_INCREMENT------------------------------------------DONE
CREATE TABLE "seadist"(
                          "seadist_id" INT NOT NULL,
                          "from_country" VARCHAR(3) NOT NULL,
                          "from_port_id" INT NOT NULL,
                          "to_country" VARCHAR(255) NOT NULL,
                          "to_port_id" INT NOT NULL,
                          "distance" FLOAT NOT NULL,
                          CONSTRAINT PK_seadist PRIMARY KEY ("seadist_id"),
                          CONSTRAINT "FK_seadist.from_country"
                              FOREIGN KEY ("from_country")
                                  REFERENCES "country"("alpha3code"),
                          CONSTRAINT "FK_seadist.to_country"
                              FOREIGN KEY ("to_country")
                                  REFERENCES "country"("alpha3code"),
                          CONSTRAINT "FK_seadist.from_port_id"
                              FOREIGN KEY ("from_port_id")
                                  REFERENCES "port_warehouse" ("port_warehouse_id"),
                          CONSTRAINT "FK_seadist.to_port_id"
                              FOREIGN KEY ("to_port_id")
                                  REFERENCES "port_warehouse" ("port_warehouse_id"),
                          CONSTRAINT CK_seadist_distance CHECK ("distance">0)
);
--FAZER AUTO_INCREMENT------------------------------------------DONE
CREATE TABLE "audit_trails"(
                               "audit_trails_id" INT NOT NULL,
                               "user_id" INT NOT NULL,
                               "container_id" INT NOT NULL,
                               "cargo_manifest_id" INT NOT NULL,
                               "date" DATE NOT NULL ,
                               "operation_type" VARCHAR(255) NOT NULL,
                               CONSTRAINT "PK_audit_trails" PRIMARY KEY ("audit_trails_id"),
                               CONSTRAINT "FK_audit.user_id"
                                   FOREIGN KEY ("user_id")
                                       REFERENCES "user"("user_id"),
                               CONSTRAINT  "FK_audit.container"
                                   FOREIGN KEY ("container_id")
                                       REFERENCES "container"("container_id"),
                               CONSTRAINT "FK_audit.cargo_manifest"
                                   FOREIGN KEY ("cargo_manifest_id")
                                       REFERENCES "cargo_manifest"("cargo_manifesto_id"),
                               CONSTRAINT "CK_audit_operation" CHECK ("operation_type" = 'UPDATE' OR "operation_type" = 'INSERT'
                                   OR "operation_type" = 'DELETE')
);


CREATE TABLE "goods"(
                        "client_id" INT NOT NULL,
                        "container_id" INT NOT NULL,
                        "cargo_manifest_id" INT NOT NULL,
                        "weigth" FLOAT,
                        CONSTRAINT "PK_goods" PRIMARY KEY ("client_id","container_id","cargo_manifest_id"),
                        CONSTRAINT "FK_goods.cargo_manifest"
                            FOREIGN KEY ("cargo_manifest_id")
                                REFERENCES "cargo_manifest"("cargo_manifesto_id"),
                        CONSTRAINT "FK_goods.container_id"
                            FOREIGN KEY ("container_id")
                                REFERENCES "container"("container_id"),
                        CONSTRAINT "FK_goods.client_id"
                            FOREIGN KEY ("client_id")
                                REFERENCES "user"("user_id")


);
CREATE TABLE "port_manager"(
                               "user_id" INT NOT NULL,
                               "port_id" INT NOT NULL,
                               CONSTRAINT "PK_portmanager" PRIMARY KEY ("user_id","port_id"),
                               CONSTRAINT "FK_port_manager.user_id"
                                   FOREIGN KEY ("user_id")
                                       REFERENCES "user"("user_id"),
                               CONSTRAINT "FK_port_manager.port_id"
                                   FOREIGN KEY ("port_id")
                                       REFERENCES "port_warehouse"("port_warehouse_id")
);

CREATE TABLE "ship_staff"(
                             "user_id" INT NOT NULL,
                             "ship_id" INT NOT NULL,
                             "date" DATE,
                             CONSTRAINT "PK_ship_staff" PRIMARY KEY ("ship_id","date"),
                             CONSTRAINT "FK_ship_staff.ship_id"
                                 FOREIGN KEY ("ship_id")
                                     REFERENCES "ship"("mmsi"),
                             CONSTRAINT "FK_ship_staff.user_id"
                                 FOREIGN KEY ("user_id")
                                     REFERENCES "user"("user_id")
);

CREATE TABLE "client"(
                         "user_id" INT NOT NULL,
                         "credit_card" INT,
                         "adress" VARCHAR(255),
                         CONSTRAINT "PK_client" PRIMARY KEY ("user_id"),
                         CONSTRAINT "FK_client.user_id"
                             FOREIGN KEY ("user_id")
                                 REFERENCES "user"("user_id")

);

--TEST
CREATE TABLE "current_capacity" (
                                    "vehicle_id" INT NOT NULL,
                                    "total_capacity" INT NOT NULL,
                                    "current_capacity" INT NOT NULL

);
