--Auto increment- AUDIT TRAILS------------------------------------------
CREATE SEQUENCE audit_seq
  START WITH 1
  INCREMENT BY 1;
  
CREATE OR REPLACE TRIGGER "audit_id_inc"
BEFORE INSERT ON "audit_trails" 
FOR EACH ROW

BEGIN
  SELECT audit_seq.NEXTVAL
  INTO   :new."audit_trails_id"
  FROM   dual;
END;
--Auto increment- BORDER---------------------------------------
CREATE SEQUENCE border_seq
  START WITH 1
  INCREMENT BY 1;
  
CREATE OR REPLACE TRIGGER "border_id_inc"
BEFORE INSERT ON "border" 
FOR EACH ROW

BEGIN
  SELECT border_seq.NEXTVAL
  INTO   :new."border_id"
  FROM   dual;
END;
--Auto increment- SEADIST---------------------------------------
CREATE SEQUENCE seadist_seq
  START WITH 1
  INCREMENT BY 1;
  
CREATE OR REPLACE TRIGGER "seadist_id_inc"
BEFORE INSERT ON "seadist" 
FOR EACH ROW

BEGIN
  SELECT seadist_seq.NEXTVAL
  INTO   :new."seadist_id"
  FROM   dual;
END;

--Auto increment- REGISTO_container---------------------------------------
CREATE SEQUENCE registo_container_seq
  START WITH 1
  INCREMENT BY 1;

CREATE OR REPLACE TRIGGER "registo_container_id_inc"
BEFORE INSERT ON "registo_container"
FOR EACH ROW

BEGIN
  SELECT registo_container_seq.NEXTVAL
  INTO   :new."registo_id"
  FROM   dual;
END;

--Auto increment- TRIP---------------------------------------
CREATE SEQUENCE trip_seq
  START WITH 1
  INCREMENT BY 1;

CREATE OR REPLACE TRIGGER "trip_id_inc"
BEFORE INSERT ON "trip"
FOR EACH ROW

BEGIN
  SELECT trip_seq.NEXTVAL
  INTO   :new."trip_id"
  FROM   dual;
END;
--Auto increment- VEHICLE---------------------------------------
CREATE SEQUENCE vehicle_seq
  START WITH 1
  INCREMENT BY 1;

CREATE OR REPLACE TRIGGER "vehicle_id_inc"
BEFORE INSERT ON "vehicle"
FOR EACH ROW

BEGIN
  SELECT vehicle_seq.NEXTVAL
  INTO   :new."vehicle_id"
  FROM   dual;
END;
--Auto increment- CARGO MANIFEST---------------------------------------
CREATE SEQUENCE cargo_manifest_seq
  START WITH 1
  INCREMENT BY 1;

CREATE OR REPLACE TRIGGER "cargo_manifest_id_inc"
BEFORE INSERT ON "cargo_manifest"
FOR EACH ROW

BEGIN
  SELECT cargo_manifest_seq.NEXTVAL
  INTO   :new."cargo_manifesto_id"
  FROM   dual;
END;
--Auto increment- ROLE---------------------------------------
CREATE SEQUENCE role_seq
    START WITH 1
    INCREMENT BY 1;

CREATE OR REPLACE TRIGGER "role_id_inc"
    BEFORE INSERT ON "role"
    FOR EACH ROW

BEGIN
    SELECT role_seq.NEXTVAL
    INTO   :new."role_id"
    FROM   dual;
END;
--Auto increment- CERTIFICATE---------------------------------------
CREATE SEQUENCE certificate_seq
    START WITH 1
    INCREMENT BY 1;

CREATE OR REPLACE TRIGGER "certificate_id_inc"
    BEFORE INSERT ON "certificate"
    FOR EACH ROW

BEGIN
    SELECT vehicle_seq.NEXTVAL
    INTO   :new."certificate_id"
    FROM   dual;
END;
--Auto increment- CONTAINER---------------------------------------
CREATE SEQUENCE container_seq
    START WITH 1
    INCREMENT BY 1;

CREATE OR REPLACE TRIGGER "container_id_inc"
    BEFORE INSERT ON "container"
    FOR EACH ROW

BEGIN
    SELECT container_seq.NEXTVAL
    INTO   :new."container_id"
    FROM   dual;
END;
--Trigger automatic date registo----------------------------------
CREATE OR REPLACE TRIGGER "registo_container_date"
    BEFORE INSERT OR UPDATE ON "registo_container"
    FOR EACH ROW
BEGIN
    :new."register_date" := sysdate;
END;

--[US308]As Traffic manager, I want to have a system that ensures that the number of containers in a manifest does not exceed the ship's available capacity
CREATE OR REPLACE TRIGGER "US308_number_of_containers"
    BEFORE INSERT OR UPDATE ON "cargo_manifest_container"
    FOR EACH ROW
DECLARE
    NUMBER_OF_NEW_CONTAINERS INT;
    NUMBER_OF_CONTAINERS_IN_THE_SHIP INT;
    SPACE_LEFT INT;
    SHIP_CAPACITY INT;
    SHIP_ID INT;
BEGIN

    SELECT "ship_id" INTO SHIP_ID
    FROM "vehicle" INNER JOIN "cargo_manifest" using("vehicle_id") WHERE "cargo_manifest"."cargo_manifesto_id"=new."cargo_manifesto_id";

    SELECT "capacity" INTO SHIP_CAPACITY
    FROM "ship" WHERE "mmsi"=SHIP_ID;

    SELECT COUNT(*) INTO NUMBER_OF_NEW_CONTAINERS
    FROM "cargo_manifest_container"
    WHERE "cargo_manifesto_id"=new."cargo_manifesto_id";
    --pegar na trip do cargo_manifest pela trip_stop
    --verificar tds os cargo manifest dos trip stop q a data é null

    SPACE_LEFT:= SHIP_CAPACITY-NUMBER_OF_CONTAINERS_IN_THE_SHIP;

    IF SPACE_LEFT<NUMBER_OF_NEW_CONTAINERS THEN
        raise_application_error(-0308,'NO SPACE MORE SPACE ON THE SHIP');
    end if;
END;

--TRIGGER atualizar capacity- COMO CHEGAR AO VEHICLE ID????-----------------------------------------------------
CREATE OR REPLACE TRIGGER "capacity_update"
    BEFORE UPDATE ON "trip_stop"
    FOR EACH ROW
DECLARE
    TOTAL_CONTAINERS  INT;
    CURRENT_CONTAINERS INT;
    UNLOAD_CONTAINERS INT;
    LOAD_CONTAINERS   INT;
    TRIP_VEHICLE_ID INT;
    TYPE CARGO_MANIFEST_ID_ARRAY IS TABLE OF "trip_stop"."cargo_manifest_id"%TYPE;
    cities_ids        "CARGO_MANIFEST_ID_ARRAY";
BEGIN
    IF new."data" != NULL THEN
        --BUSCA TDS OS CARGO_MANIFEST NAQUELA STOP
        SELECT "cargo_manifest_id" BULK COLLECT INTO cities_ids FROM "trip_stop" WHERE "trip_id"=old."trip_id";
        --BUSCA A CAPACIDADE MAXIMA DO BARCO
        SELECT "total_capacity" INTO TOTAL_CONTAINERS
        FROM "current_capacity" INNER JOIN "cargo_manifest" USING("vehicle_id") WHERE;
        --GUARDA OS CONTAINERS Q TEM O NAVIO
        SELECT "current_capacity" INTO CURRENT_CONTAINERS
        FROM "current_capacity" INNER JOIN "cargo_manifest" USING("vehicle_id") WHERE;
        FOR i in 1..cities_ids.COUNT LOOP
            --REMOVE OS ANTIGOS CONTAINERS
            SELECT COUNT("registo_id") INTO UNLOAD_CONTAINERS
                FROM "cargo_manifest" INNER JOIN "cargo_manifest_container" on "cargo_manifest"."cargo_manifesto_id"="cargo_manifest_container"."cargo_manifesto_id"
                WHERE "operation_type"='unload' OR "operation_type"='UNLOAD' AND "cargo_manifest"."cargo_manifesto_id"=i;;
            CURRENT_CONTAINERS:= CURRENT_CONTAINERS-UNLOAD_CONTAINERS;
            --ADICIONA OS NOVOS CONTAINERS
            SELECT COUNT("registo_id") INTO LOAD_CONTAINERS
                FROM "cargo_manifest" INNER JOIN "cargo_manifest_container" on "cargo_manifest"."cargo_manifesto_id"="cargo_manifest_container"."cargo_manifesto_id"
                WHERE "operation_type"='load' OR "operation_type"='Load' AND "cargo_manifest"."cargo_manifesto_id"=i;
                CURRENT_CONTAINERS:= CURRENT_CONTAINERS+LOAD_CONTAINERS;
            --VERIFICA SE PODE SER EFETUADO O LOAD
                IF CURRENT_CONTAINERS>TOTAL_CONTAINERS THEN
                    raise_application_error(-0308,'NO SPACE MORE SPACE ON THE SHIP');
                end if;

        end loop;
        SELECT "vehicle_id" INTO TRIP_VEHICLE_ID FROM "trip" WHERE "trip"."trip_id"=old."trip_id";
        --ATUALIZA A TABELA
        UPDATE "current_capacity" SET "current_capacity"=CURRENT_CONTAINERS WHERE "vehicle_id"=TRIP_VEHICLE_ID;



    end if;
END;

--TRIGGER FROM PORT ----------------------------------------
CREATE OR REPLACE TRIGGER "from_port_validation"
    BEFORE INSERT OR UPDATE ON "port_warehouse"
    FOR EACH ROW
DECLARE
    TYPE_PLACE VARCHAR(255);
BEGIN
    IF new."from_port" != NULL THEN
        SELECT "type" INTO TYPE_PLACE FROM "port_warehouse" WHERE "port_warehouse_id"=new."from_port";
        IF TYPE_PLACE ='port' OR TYPE_PLACE ='Port' THEN
            raise_application_error(-0666,'The id is not from a port');
        end if;
        SELECT "type" INTO TYPE_PLACE FROM "port_warehouse" WHERE "port_warehouse_id"=old."port_warehouse_id";
        IF TYPE_PLACE ='warehouse' OR TYPE_PLACE ='Warehouse' THEN
            raise_application_error(-0666,'U cant insert a port in a port');
        end if;
    end if;
END;

--[US306] As Port manager, I want to know the occupancy rate of each warehouse and an estimate of the containers leaving the warehouse during the next 30 days.

CREATE OR REPLACE FUNCTION  "occunpacy_rate"(port_manager_id "port_manager"."user_id"%TYPE) RETURN BOOLEAN
    IS
    TYPE WAREHOUSE_LIST IS TABLE OF "port_manager"."port_id"%TYPE;
    portsList        WAREHOUSE_LIST;
    RATE FLOAT;
    CAPACITY_WAREHOUSE INT;
    COUNT_CONT INT;
    COUNT_REMOVE_CONT INT;
    BEGIN
        SELECT "port_id" BULK COLLECT INTO portsList FROM "port_manager" WHERE "user_id"=port_manager_id;
        for i in 1..portsList.COUNT LOOP
            --Ocupacy Rate each warehouse
                SELECT "capacity" INTO CAPACITY_WAREHOUSE FROM "port_warehouse" WHERE "port_warehouse_id"=i;
                SELECT COUNT("registo_id") INTO COUNT_CONT FROM "cargo_manifest_container" INNER JOIN "trip_stop" on "cargo_manifesto_id"="cargo_manifest_id"
                INNER JOIN "cargo_manifest" cm on cm."cargo_manifesto_id" = "cargo_manifest_container"."cargo_manifesto_id"
                WHERE "trip_stop"."port_wharehouse_id"=i AND "trip_stop"."estimate_date"<sysdate AND
                cm."operation_type"='load' OR cm."operation_type"='Load';
                SELECT COUNT("registo_id") INTO COUNT_REMOVE_CONT FROM "cargo_manifest_container" INNER JOIN "trip_stop" on "cargo_manifesto_id"="cargo_manifest_id"
                INNER JOIN "cargo_manifest" cm on cm."cargo_manifesto_id" = "cargo_manifest_container"."cargo_manifesto_id"
                WHERE "trip_stop"."port_wharehouse_id"=i AND "trip_stop"."estimate_date"<sysdate AND
                      cm."operation_type"='unload' OR cm."operation_type"='Unload';
                COUNT_CONT:=COUNT_CONT-COUNT_REMOVE_CONT;
                RATE := COUNT_CONT/CAPACITY_WAREHOUSE;
                dbms_output.put_line('Warehouse id: ' || i || 'occupancy rate: ' || RATE);
            end loop;
        return true;
    end;