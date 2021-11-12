CREATE TABLE "ship"(
  mmsi INT ,
  call_sign VARCHAR(60) NOT NULL ,
  ship_imo_id INT NOT NULL,
  ship_name VARCHAR(60) NOT NULL,
  nr_energy_gens INT NOT NULL,
  gen_po FLOAT NOT NULL,
  length FLOAT NOT NULL,
  width FLOAT NOT NULL,
  capacity FLOAT NOT NULL,
  draft FLOAT NOT NULL,
  vessel_type int NOT NULL,
  CONSTRAINT PK_ship  PRIMARY KEY(mmsi),
  CONSTRAINT PK_ship_unique UNIQUE (call_sign, ship_imo_id),
  CONSTRAINT CK_ship_mmsi CHECK (mmsi<1000000000 AND mmsi>99999999),
  CONSTRAINT CK_ship_ship_imo_id CHECK  (ship_imo_id<10000000 AND ship_imo_id>999999),
  CONSTRAINT CK_ship_nr_energy_gens CHECK  (nr_energy_gens>0),
  CONSTRAINT CK_ship_gen_po CHECK  (gen_po>0),
  CONSTRAINT CK_ship_length CHECK  (length>0),
  CONSTRAINT CK_ship_width CHECK  (width>0),
  CONSTRAINT CK_ship_capacity CHECK  (capacity>0),
  CONSTRAINT CK_ship_draft CHECK  (draft>0),
  CONSTRAINT CK_ship_vessel_type CHECK  (vessel_type<100)

  );



CREATE TABLE "port_warehouse" (
  port_warehouse_id INT,
  nome VARCHAR(60) NOT NULL,
  continent VARCHAR(60) NOT NULL,
  country VARCHAR(60) NOT NULL,
  tipo VARCHAR(60) NOT NULL,
  lat FLOAT NOT NULL,
  logi FLOAT NOT NULL,
  CONSTRAINT PK_port_warehouse  PRIMARY KEY (port_warehouse_id),
  CONSTRAINT CK_port_warehouse_lat CHECK (lat>=-90 AND lat<=90),
  CONSTRAINT CK_port_warehouse_log CHECK (logi >=-90 AND logi<=90)
);

CREATE TABLE "container" (
  contNumber int,
  ISOCode varchar(4) NOT NULL,
  "checkDigit" int NOT NULL,
  maxWeightC FLOAT NOT NULL,
  weightC FLOAT NOT NULL,
  maxVol FLOAT NOT NULL,
  repairRecomendation NUMBER(1,0) NOT NULL,
  refrigeration FLOAT,
  CONSTRAINT "PK_container"
  PRIMARY KEY (contNumber),
  CONSTRAINT "CK_container_ISOCode" CHECK (length(ISOCode)=4),
  CONSTRAINT "CK_container_maxWeightC" CHECK (maxWeightC>0),
  CONSTRAINT "CK_container_weightC" CHECK (weightC>0),
  CONSTRAINT "CK_container_maxVol" CHECK (maxVol>0)
);

CREATE TABLE "certificate" (
  "certificate_id" int,
  "name" VARCHAR(60) NOT NULL,
  CONSTRAINT "PK_certificate"
  PRIMARY KEY ("certificate_id")
);


CREATE TABLE "roles" (
  "role_id" INT,
  "name" VARCHAR(60) NOT NULL,
  CONSTRAINT "PK_roles"
  PRIMARY KEY ("role_id")
);

CREATE TABLE "cargo_manifest" (
  container_id int,
  cargo_manifesto_id int ,
  ship_id int ,
  container_position_x int NOT NULL,
  container_position_y int NOT NULL,
  container_position_z int NOT NULL,
  container_gross_weigth FLOAT NOT NULL,
  tipo VARCHAR(60) NOT NULL,
  entry_date DATE NOT NULL,
  CONSTRAINT "FK_cargo_manifest.container_id"
    FOREIGN KEY (container_id)
      REFERENCES "container"(contNumber),
    CONSTRAINT "FK_cargo_manifest.ship_id"
    FOREIGN KEY (ship_id)
      REFERENCES "ship"(mmsi),
  CONSTRAINT PK_cargo_manifest
  PRIMARY KEY (container_id, cargo_manifesto_id, ship_id),
  CONSTRAINT "CK_cargo_manifest_container_position_x" CHECK (container_position_x>=0),
  CONSTRAINT "CK_cargo_manifest_container_position_y" CHECK (container_position_y>=0),
  CONSTRAINT "CK_cargo_manifest_container_position_z" CHECK (container_position_z>=0),
  CONSTRAINT "CK_cargo_manifest_type" CHECK (tipo= 'load' OR tipo='unload'),
  CONSTRAINT "CK_cargo_manifest_container_gross_weigth" CHECK (container_gross_weigth>0)
);

CREATE TABLE "truck" (
                         plate VARCHAR(8),
                         fuelCapacity FLOAT,
                         CONSTRAINT "PK_truck"
                             PRIMARY KEY (plate),
                         CONSTRAINT "CK_truck_plate" CHECK (REGEXP_LIKE(plate,'^(([A-Z]{2}:\d{2}:(\d{2}|[A-Z]{2}))|(\d{2}:(\d{2}:[A-Z]{2}|[A-Z]{2}:\d{2})))$$')) ,
                         CONSTRAINT "CK_truck_fuelCapacity" CHECK (fuelCapacity>0)
);

CREATE TABLE "container_certificate" (
  "container_id" int,
  "certificate_id" int,
  CONSTRAINT "FK_container_certificate.container_id"
      FOREIGN KEY ("container_id")
        REFERENCES "container"(contNumber),
  CONSTRAINT "FK_container_certificate.certificate_id"
      FOREIGN KEY ("certificate_id")
        REFERENCES "certificate"("certificate_id"),
  PRIMARY KEY ("container_id", "certificate_id")      
);


CREATE TABLE "ship_data" (
  "ship_id" INT,
  "Data" DATE,
  "time" DATE,
  latitude FLOAT NOT NULL,
  longitude FLOAT NOT NULL,
  SOG FLOAT NOT NULL,
  COG FLOAT NOT NULL,
  "heading" VARCHAR(60) NOT NULL,
  "cargo" VARCHAR(60) NOT NULL,
  "transceiver_class" VARCHAR(60) NOT NULL,
  CONSTRAINT "PK_ship_data"  PRIMARY KEY ("ship_id", "Data", "time"),
  CONSTRAINT "FK_ ship_data.ship_id"
      FOREIGN KEY ("ship_id")
        REFERENCES "ship"(mmsi),
  CONSTRAINT "CK_latitude_menor" CHECK (latitude<=90),
  CONSTRAINT "CK_latitude_maior" CHECK (latitude>=-90),
  CONSTRAINT "CK_Longitude_menor" CHECK (longitude<=90),
  CONSTRAINT "CK_Longitude_maior" CHECK (longitude>=-90),
  CONSTRAINT "CK_SOG" CHECK (SOG>=0),
  CONSTRAINT "CK_COG_menor" CHECK (COG>=0),
  CONSTRAINT "CK_COG_maior" CHECK (COG<=359)
);


CREATE TABLE "truck_data" (
  "plate" VARCHAR(8),
  "data" DATE NOT NULL,
  "time" DATE NOT NULL,
  lat FLOAT NOT NULL,
  "log" FLOAT NOT NULL,
  CONSTRAINT "PK_truck_data"
  PRIMARY KEY ("plate", "data", "time"),
  CONSTRAINT "FK_truck_data_plate.plate"
    FOREIGN KEY ("plate")
      REFERENCES "truck"(plate),
  CONSTRAINT "CK_truck_data_lat_menor" CHECK (lat<=90),
  CONSTRAINT "CK_truck_data_lat_maior" CHECK (lat>=-90),
  CONSTRAINT "CK_truck_data_log_menor" CHECK ("log"<=90),
  CONSTRAINT "CK_truck_data_log_maior" CHECK ("log">=-90),
  CONSTRAINT "CK_truck_data_plate" CHECK (REGEXP_LIKE("plate",'^(([A-Z]{2}:\d{2}:(\d{2}|[A-Z]{2}))|(\d{2}:(\d{2}:[A-Z]{2}|[A-Z]{2}:\d{2})))$$'))
);

CREATE TABLE "employe" (
  "employe_id" INT,
  "name" VARCHAR(60) NOT NULL,
  "email" VARCHAR(60) NOT NULL,
  "pass" VARCHAR(20) NOT NULL,
  "role_id" INT,
  CONSTRAINT "PK_employe"
  PRIMARY KEY ("employe_id"),
  CONSTRAINT "FK_employe.role_id"
    FOREIGN KEY ("role_id")
      REFERENCES "roles"("role_id"),
  CONSTRAINT "CK_employe_email" CHECK (REGEXP_LIKE("email",'^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,6}$'))
);

CREATE TABLE "employe_schedule" (
  "employe_id" INT,
  "port_whare_id" INT,
  day_of_the_week VARCHAR(60) NOT NULL,
  hour VARCHAR(20) NOT NULL,
  CONSTRAINT "PK_employe_schedule"
    PRIMARY KEY ("employe_id", "port_whare_id"),
  CONSTRAINT "FK_employe_schedule.employe_id"
    FOREIGN KEY ("employe_id")
      REFERENCES "employe"("employe_id"),
  CONSTRAINT "FK_employe_schedule.port_whare_id"
      FOREIGN KEY ("port_whare_id")
        REFERENCES "port_warehouse"(port_warehouse_id),
  CONSTRAINT "CK_employe_schedule_day_of_the_week" CHECK (day_of_the_week = 'Monday' OR day_of_the_week = 'Tuesday' OR day_of_the_week='Wednesday' OR day_of_the_week='Thursday'
  OR day_of_the_week='Friday' OR day_of_the_week='Saturday' OR day_of_the_week='Sunday' OR
  day_of_the_week = 'monday' OR day_of_the_week = 'tuesday' OR day_of_the_week='wednesday' OR day_of_the_week='thursday'
    OR day_of_the_week='friday' OR day_of_the_week='saturday' OR day_of_the_week='sunday'),
  CONSTRAINT  "CK_employe_schedule_hour" CHECK (length(hour)=5)
);

