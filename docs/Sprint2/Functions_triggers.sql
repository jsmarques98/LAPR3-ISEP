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
/
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
/
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
/
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
/
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
/
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
/
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
/
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
/
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
/
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
/
--Trigger automatic date registo----------------------------------
CREATE OR REPLACE TRIGGER "registo_container_date"
    BEFORE INSERT OR UPDATE ON "registo_container"
    FOR EACH ROW
BEGIN
    :new."register_date" := sysdate;
END;
/
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
    FROM "vehicle" INNER JOIN "cargo_manifest" using("vehicle_id") WHERE "cargo_manifest"."cargo_manifesto_id"=:new."cargo_manifesto_id";

    SELECT "capacity" INTO SHIP_CAPACITY
    FROM "ship" WHERE "mmsi"=SHIP_ID;

    SELECT COUNT(*) INTO NUMBER_OF_NEW_CONTAINERS
    FROM "cargo_manifest_container"
    WHERE "cargo_manifesto_id"=:new."cargo_manifesto_id";
    --pegar na trip do cargo_manifest pela trip_stop
    --verificar tds os cargo manifest dos trip stop q a data é null

    SPACE_LEFT:= SHIP_CAPACITY-NUMBER_OF_CONTAINERS_IN_THE_SHIP;

    IF SPACE_LEFT<NUMBER_OF_NEW_CONTAINERS THEN
        raise_application_error(-0308,'NO SPACE MORE SPACE ON THE SHIP');
    end if;
END;
/
--TRIGGER atualizar capacity------------------------------------------------------
CREATE OR REPLACE TRIGGER "capacity_update"
    BEFORE UPDATE ON "trip_stop"
    FOR EACH ROW
DECLARE
    VEHICLEID INT;
    TOTAL_CONTAINERS  INT;
    CURRENT_CONTAINERS INT;
    UNLOAD_CONTAINERS INT;
    LOAD_CONTAINERS   INT;
    TRIP_VEHICLE_ID INT;
    TYPE CARGO_MANIFEST_ID_ARRAY IS TABLE OF "trip_stop"."cargo_manifest_id"%TYPE;
    cities_ids        "CARGO_MANIFEST_ID_ARRAY";
BEGIN
    IF new."data" != NULL THEN
        --BUSCA VEHICLEID ID
        SELECT "vehicle_id" into VEHICLEID 
        FROM "vehicle" INNER JOIN "cargo_manifest" using("vehicle_id") WHERE "cargo_manifest"."cargo_manifesto_id"=:new."cargo_manifest_id";
        --BUSCA TDS OS CARGO_MANIFEST NAQUELA STOP
        SELECT "cargo_manifest_id" BULK COLLECT INTO cities_ids FROM "trip_stop" WHERE "trip_id"=old."trip_id";
        --BUSCA A CAPACIDADE MAXIMA DO BARCO
        SELECT "total_capacity" INTO TOTAL_CONTAINERS
        FROM "current_capacity" INNER JOIN "cargo_manifest" USING("vehicle_id") WHERE "vehicle_id"= VEHICLEID;
        --GUARDA OS CONTAINERS QUE TEM O NAVIO
        SELECT "current_capacity" INTO CURRENT_CONTAINERS
        FROM "current_capacity" INNER JOIN "cargo_manifest" USING("vehicle_id") WHERE "vehicle_id"= VEHICLEID;
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
        SELECT "vehicle_id" INTO TRIP_VEHICLE_ID FROM "trip" WHERE "trip"."trip_id"=:new."trip_id";
        --ATUALIZA A TABELA
        UPDATE "current_capacity" SET "current_capacity"=CURRENT_CONTAINERS WHERE "vehicle_id"=TRIP_VEHICLE_ID;
    end if;
END;
/
--[US309] As Traffic manager, I do not allow a cargo manifest for a particular ship to be
--registered in the system on a date when the ship is already occupied. 
CREATE OR REPLACE TRIGGER "cargo_manifest_on_date"
    BEFORE INSERT OR UPDATE ON "cargo_manifest"
    FOR EACH ROW
DECLARE
    SHIP_CAPACITY INT;
    SHIP_ID INT;
    CURRENT_CONTAINERS INT;
    UNLOAD_CONTAINERS INT;
    LOAD_CONTAINERS   INT;
    TRIP_ID INT;
    TYPE CARGO_MANIFEST_ID_ARRAY IS TABLE OF "trip_stop"."cargo_manifest_id"%TYPE;
    cargo_manifest_ids        "CARGO_MANIFEST_ID_ARRAY";
    vehicleID INT;
BEGIN
    IF :new."entry_date" != NULL THEN
        --BUSCA SHIP ID 
        SELECT  "vehicle_id" into vehicleID  FROM "vehicle" INNER JOIN "cargo_manifest" using("vehicle_id") WHERE "cargo_manifest"."cargo_manifesto_id"=:new."cargo_manifesto_id";
        SELECT "ship_id" INTO SHIP_ID FROM "vehicle" INNER JOIN "cargo_manifest" using("vehicle_id") WHERE "cargo_manifest"."cargo_manifesto_id"=:new."cargo_manifesto_id";
        --BUSCA A CAPACIDADE MAXIMA DO BARCO
        SELECT "capacity" INTO SHIP_CAPACITY
        FROM "ship" WHERE "mmsi"=SHIP_ID;
        --BUSCA TDS OS CARGO_MANIFEST NAQUELA STOP
        SELECT "trip_id" INTO TRIP_ID FROM "trip_stop" WHERE "cargo_manifest_id"=:new."cargo_manifesto_id";
        SELECT "cargo_manifest_id" BULK COLLECT INTO cargo_manifest_ids FROM "trip_stop" WHERE "trip_id"=TRIP_ID AND "estimated_date"<=:new."entry_date";
        --GUARDA OS CONTAINERS QUE TEM O NAVIO
        SELECT "current_capacity" INTO CURRENT_CONTAINERS
        FROM "current_capacity" INNER JOIN "cargo_manifest" USING("vehicle_id") WHERE "vehicle_id"= vehicleID;
        FOR i in 1..cargo_manifest_ids.COUNT LOOP
            --REMOVE OS ANTIGOS CONTAINERS
            SELECT COUNT("registo_id") INTO UNLOAD_CONTAINERS
                FROM "cargo_manifest_container" INNER JOIN "cargo_manifest" on "cargo_manifest"."cargo_manifesto_id"="cargo_manifest_container"."cargo_manifest_id"
                WHERE "operation_type"='unload' OR "operation_type"='UNLOAD' AND "cargo_manifest"."cargo_manifesto_id"=i;;
            CURRENT_CONTAINERS:= CURRENT_CONTAINERS-UNLOAD_CONTAINERS;
            --ADICIONA OS NOVOS CONTAINERS
            SELECT COUNT("registo_id") INTO LOAD_CONTAINERS
                FROM "cargo_manifest_container" INNER JOIN "cargo_manifest" on "cargo_manifest"."cargo_manifesto_id"="cargo_manifest_container"."cargo_manifest_id"
                WHERE "operation_type"='load' OR "operation_type"='Load' AND "cargo_manifest"."cargo_manifesto_id"=i;
                CURRENT_CONTAINERS:= CURRENT_CONTAINERS+LOAD_CONTAINERS;
        end loop;
        --VERIFICA SE PODE SER EFETUADO O CARGO MANIFEST
                IF CURRENT_CONTAINERS>SHIP_CAPACITY THEN
                    raise_application_error(-0308,'NO SPACE MORE SPACE ON THE SHIP ON THAT DATE');
                end if;
    END IF;
END;
/
--TRIGGER FROM PORT ----------------------------------------
CREATE OR REPLACE TRIGGER "from_port_validation"
    BEFORE INSERT OR UPDATE ON "port_warehouse"
    FOR EACH ROW
DECLARE
    TYPE_PLACE VARCHAR(255);
BEGIN
    IF :new."from_port" != NULL THEN
        SELECT "type" INTO TYPE_PLACE FROM "port_warehouse" WHERE "port_warehouse_id"=:new."from_port";
        IF TYPE_PLACE ='port' OR TYPE_PLACE ='Port' THEN
            raise_application_error(-0666,'The id is not from a port');
        end if;
        SELECT "type" INTO TYPE_PLACE FROM "port_warehouse" WHERE "port_warehouse_id"=:new."port_warehouse_id";
        IF TYPE_PLACE ='warehouse' OR TYPE_PLACE ='Warehouse' THEN
            raise_application_error(-0666,'U cant insert a port in a port');
        end if;
    end if;
END;
/
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
    /
--[US307]As Port manager, I intend to get a warning whenever I issue a cargo manifest destined for a warehouse whose available capacity is insufficient to accommodate the new manifest.
CREATE OR REPLACE TRIGGER "capacity_update_warehouse"
    BEFORE INSERT OR UPDATE ON "cargo_manifest_container"
    FOR EACH ROW
DECLARE
    DESTINO INT;
    DATA_ DATE;
    CAPACITY_WAREHOUSE INT;
    COUNT_CONT INT;
    COUNT_REMOVE_CONT INT;
    COUNT_NEW_CONTAINER INT;
BEGIN
                SELECT "destiny" INTO DESTINO FROM "cargo_manifest" WHERE "cargo_manifesto_id"=:new."cargo_manifesto_id";
                SELECT "entry_date" INTO DATA_ FROM "cargo_manifest" WHERE "cargo_manifesto_id"=:new."cargo_manifesto_id";
                SELECT "capacity" INTO CAPACITY_WAREHOUSE FROM "port_warehouse" WHERE "port_warehouse_id"=DESTINO;
                SELECT COUNT("registo_id") INTO COUNT_CONT FROM "cargo_manifest_container" INNER JOIN "trip_stop" on "cargo_manifesto_id"="cargo_manifest_id"
                INNER JOIN "cargo_manifest" cm on cm."cargo_manifesto_id" = "cargo_manifest_container"."cargo_manifesto_id"
                WHERE "trip_stop"."port_wharehouse_id"=DESTINO AND "trip_stop"."estimate_date"<DATA_ AND
                cm."operation_type"='load' OR cm."operation_type"='Load';
                SELECT COUNT("registo_id") INTO COUNT_REMOVE_CONT FROM "cargo_manifest_container" INNER JOIN "trip_stop" on "cargo_manifesto_id"="cargo_manifest_id"
                INNER JOIN "cargo_manifest" cm on cm."cargo_manifesto_id" = "cargo_manifest_container"."cargo_manifesto_id"
                WHERE "trip_stop"."port_wharehouse_id"=DESTINO AND "trip_stop"."estimate_date"<DATA_ AND
                      cm."operation_type"='unload' OR cm."operation_type"='Unload';
                      
                SELECT COUNT ("registo_id") INTO COUNT_NEW_CONTAINER FROM "cargo_manifest_container" where "cargo_manifesto_id"=:new."cargo_manifesto_id";
                COUNT_CONT:=(COUNT_CONT + COUNT_NEW_CONTAINER)-COUNT_REMOVE_CONT;
                
            if COUNT_CONT < 0 THEN
                  raise_application_error(-0307,'NO SPACE MORE SPACE ON THE WAREHOUSE');
            end if;
            
end;
/
--[US310] As Port manager, I intend to have a map of the occupation of the existing resources in the port during a given month.
CREATE OR REPLACE FUNCTION  "occupation_map"(port_manager_id "port_manager"."user_id"%TYPE, MONTH_GIVEN DATE) RETURN BOOLEAN
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

            for j in 1..30 LOOP
                SELECT "capacity" INTO CAPACITY_WAREHOUSE FROM "port_warehouse" WHERE "port_warehouse_id"=i;
                SELECT COUNT("registo_id") INTO COUNT_CONT FROM "cargo_manifest_container" INNER JOIN "trip_stop" on "cargo_manifesto_id"="cargo_manifest_id"
                INNER JOIN "cargo_manifest" cm on cm."cargo_manifesto_id" = "cargo_manifest_container"."cargo_manifesto_id"
                WHERE "trip_stop"."port_wharehouse_id"=i AND cm."operation_type"='load' OR cm."operation_type"='Load'
                AND extract(month from "trip_stop"."estimate_date")=extract(month from MONTH_GIVEN) AND extract(day from "trip_stop"."estimate_date")=j;
                SELECT COUNT("registo_id") INTO COUNT_REMOVE_CONT FROM "cargo_manifest_container" INNER JOIN "trip_stop" on "cargo_manifesto_id"="cargo_manifest_id"
                INNER JOIN "cargo_manifest" cm on cm."cargo_manifesto_id" = "cargo_manifest_container"."cargo_manifesto_id"
                WHERE "trip_stop"."port_wharehouse_id"=i AND  cm."operation_type"='unload' OR cm."operation_type"='Unload'
                AND extract(month from "trip_stop"."estimate_date")=extract(month from MONTH_GIVEN) AND extract(day from "trip_stop"."estimate_date")=j ;
                COUNT_CONT:=COUNT_CONT-COUNT_REMOVE_CONT;
                RATE := COUNT_CONT/CAPACITY_WAREHOUSE;
                dbms_output.put_line('Warehouse id: ' || i || 'occupancy rate: ' || RATE || 'day: ' || j);
end loop;

end loop;
return true;
end;

/
--[US312] As Client, I want to know the current situation of a specific container being used to transport my goods – US204.

CREATE OR REPLACE FUNCTION  "get_container_situation"(codeContainer "container"."container_id"%TYPE, codeClient "user"."user_id"%TYPE) RETURN BOOLEAN
IS
    AUX int;
    AUX1 int;
    CARGOMANIFESTID "cargo_manifest"."cargo_manifesto_id"%type;
    CARGOMANIFESTID "cargo_manifest"."cargo_manifesto_id"%type;
    DELIVERED "registo_container"."delivered"%type;
    SHIPID "vehicle"."vehicle_id"%type;
    PORTNAME "port_warehouse"."port_warehouse_id"%type;
    PORTTYPE "port_warehouse"."type"%type;
    SHIPNAME "ship"."ship_name"%type;
    PLATE "truck"."plate"%type;
BEGIN

    SELECT COUNT(*) INTO AUX FROM "container" WHERE "container"."container_id" = codeContainer;

    IF AUX = 0 THEN
        raise_application_error(-20010, 'Invalid Container ID');
    end if;

    SELECT COUNT(*) INTO AUX1 FROM "goods" WHERE "goods"."client_id" = codeClient AND "goods"."container_id" = codeContainer;

    IF AUX1 = 0 THEN
        raise_application_error(-20011, 'Container is not leased by client.');
    end if;

    SELECT "cargo_manifest_id" INTO CARGOMANIFESTID FROM "goods" WHERE "goods"."container_id" = codeContainer AND "goods"."client_id" = codeClient;

    SELECT "delivered" INTO DELIVERED FROM "registo_container" WHERE "registo_container"."container_id" = codeContainer;

    SELECT "ship_id" INTO SHIPID FROM "vehicle" WHERE "vehicle"."vehicle_id" = (
        SELECT "vehicle_id" FROM "cargo_manifest" WHERE "cargo_manifest"."cargo_manifesto_id" = CARGOMANIFESTID);

    IF DELIVERED = 'YES' OR 'Yes' OR 'yes' THEN
        SELECT "name" INTO PORTNAME FROM "port_warehouse" WHERE "port_warehouse"."port_warehouse_id" = (
            SELECT "destiny" FROM "cargo_manifest" WHERE "cargo_manifest"."cargo_manifesto_id" = CARGOMANIFESTID);
        SELECT "type" INTO PORTTYPE FROM "port_warehouse" WHERE "port_warehouse"."port_warehouse_id" = (
            SELECT "destiny" FROM "cargo_manifest" WHERE "cargo_manifest"."cargo_manifesto_id" = CARGOMANIFESTID);
        DBMS_OUTPUT('DELIVERED AT THE' || PORTTYPE || ' CALLED ' || PORTNAME);
        ELSE
            IF SHIPID IS NOT NULL THEN
                SELECT "ship_name" INTO SHIPNAME FROM "ship" WHERE "ship"."mmsi" = SHIPID;
                DBMS_OUTPUT('ON BOARD OF THE SHIP CALLED' || SHIPNAME);
            ELSE
                SELECT "plate" INTO PLATE FROM "truck" WHERE "truck"."plate" = (
                    SELECT "plate" FROM "vehicle" WHERE "vehicle"."vehicle_id" = (
                        SELECT "vehicle_id" FROM "cargo_manifest" WHERE "cargo_manifest"."cargo_manifesto_id" = CARGOMANIFESTID));
                DBMS_OUTPUT('ON BOARD OF THE TRUCK WITH THE PLATE' || PLATE);
            end if;
    end if;

    RETURN TRUE;
END;
/

--[US305]As Client, I want to know the route of a specific container I am leasing
CREATE OR REPLACE FUNCTION  "get_container_situation"(codeContainer "container"."container_id"%TYPE, codeClient "user"."user_id"%TYPE) RETURN BOOLEAN
IS
    AUX int;
    AUX1 int;
    CARGOMANIFESTID "cargo_manifest"."cargo_manifesto_id"%type;
    CARGOMANIFESTID "cargo_manifest"."cargo_manifesto_id"%type;
    SHIPID "vehicle"."vehicle_id"%type;
    SHIPNAME "ship"."ship_name"%type;
    PLATE "truck"."plate"%type;
    RegistoID int;
	TYPE WAREHOUSE_LIST IS TABLE OF "port_manager"."port_id"%TYPE;
    Stops       WAREHOUSE_LIST;
	NomePort VARCHAR(255);

BEGIN

    SELECT COUNT(*) INTO AUX FROM "container" WHERE "container"."container_id" = codeContainer;

    IF AUX = 0 THEN
        raise_application_error(-0305, 'Invalid Container ID');
    end if;

    SELECT COUNT(*) INTO AUX1 FROM "goods" WHERE "goods"."client_id" = codeClient AND "goods"."container_id" = codeContainer;

    IF AUX1 = 0 THEN
        raise_application_error(-03050, 'Container is not leased by client.');
    end if;

 SELECT "registo_id" INTO RegistoID FROM "registo_container" WHERE "registo_container"."container_id"= codeContainer AND rownum = (select MAX("registo_id") FROM "registo_container" WHERE "container_id"=codeContainer);
 SELECT "cargo_manifest_id" INTO CargoManifestID FROM "Cargo_manifest_container" WHERE "registo_id"=RegistoID;
 
 SELECT "port_warehouse_id" BULK COLLECT INTO Stops FROM "trip_stop" WHERE "cargo_manifest_id"=CargoManifestID;
 for i in 1..Stops.COUNT LOOP
 SELECT "name" INTO NomePort FROM "port_warehouse" WHERE "port_warehouse_id"=Stops;
 dbms_output.put_line('NomePort: '  || NomePort);
end loop;

SELECT "vehicle_id" INTO VEHICLE_ID FROM "vehicle" WHERE "vehicle"."vehicle_id"=(
SELECT "vehicle_id" FROM "cargo_manifest" WHERE "cargo_manifest"."cargo_manifest_id"=CARGO_MANIFEST_ID);
 
IF SHIPID IS NOT NULL THEN
                SELECT "ship_name" INTO SHIPNAME FROM "ship" WHERE "ship"."mmsi" = SHIPID;
                DBMS_OUTPUT('ON BOARD OF THE SHIP CALLED' || SHIPNAME);
            ELSE
                SELECT "plate" INTO PLATE FROM "truck" WHERE "truck"."plate" = (
                    SELECT "plate" FROM "vehicle" WHERE "vehicle"."vehicle_id" = (
                        SELECT "vehicle_id" FROM "cargo_manifest" WHERE "cargo_manifest"."cargo_manifesto_id" = CARGOMANIFESTID));
                DBMS_OUTPUT('ON BOARD OF THE TRUCK WITH THE PLATE' || PLATE);
            end if;
            
            
end;
/
--[US407] As Port manager, I intend to generate, a week in advance, the loading and unloading map based on ships and trucks load manifests and corresponding travel plans
create or replace FUNCTION  "weakly_operation_map"(port_ID "port_warehouse"."port_warehouse_id"%type) RETURN SYS_REFCURSOR
AS
    TABLECURSOR     SYS_REFCURSOR;
	TYPE CARGOMANIFEST_LIST IS TABLE OF "cargo_manifest"."cargo_manifesto_id"%TYPE;
    CARGOS       CARGOMANIFEST_LIST;
    TYPE CONTAINER_LIST IS TABLE OF "registo_container"."container_id"%TYPE;
    CONTAINERS_l       CONTAINER_LIST;
    aux int;
    NUMCONT int;
    operation_type VARCHAR(255);
    DATACARGO DATE;
    x number;
    y number;
    z number;
    weigth float;
    vehicle_id int;
BEGIN
--Verifica se existe o port e se � um porto
    SELECT COUNT(*)INTO aux FROM "port_warehouse" WHERE "port_warehouse_id"=port_ID AND "type"='port';
    if aux=0 then
        raise_application_error(-2314,'This id does not exist');
   end if;
  
   --Vai buscar todos os cargos manifest que vai chegar nessa semana
   SELECT "cargo_manifesto_id" BULK COLLECT INTO CARGOS FROM "cargo_manifest" WHERE "destiny"=port_ID AND "entry_date"<(sysdate+7) AND "entry_date">sysdate;
   dbms_output.put_line(sysdate+7);
   for i in 1..CARGOS.COUNT LOOP
   --Conta o numero de containers em cada cargo
        SELECT COUNT("registo_id") INTO NUMCONT FROM "cargo_manifest_container" WHERE "cargo_manifesto_id"=CARGOS(i);
   --Vai buscar o veiculo     
        SELECT "vehicle_id" INTO vehicle_id FROM "cargo_manifest" WHERE "cargo_manifesto_id"=CARGOS(i);
   --Guarda o tipo de operacao 
        SELECT "operation_type" INTO operation_type FROM "cargo_manifest" WHERE "cargo_manifesto_id"=CARGOS(i);
   --Guarda a data da operacao
        SELECT "entry_date" INTO DATACARGO FROM "cargo_manifest" WHERE "cargo_manifesto_id"=CARGOS(i);
   --Vai buscar informa��o individual de cada container     
        SELECT "registo_id" BULK COLLECT INTO CONTAINERS_l FROM "cargo_manifest_container" WHERE "cargo_manifesto_id"=  CARGOS(i);
        for j in 1..CONTAINERS_l.COUNT LOOP
            SELECT "container_position_x" INTO x FROM "cargo_manifest_container" WHERE "registo_id"=CONTAINERS_l(j) AND "cargo_manifesto_id"=CARGOS(i);
            SELECT "container_position_y" INTO y FROM "cargo_manifest_container" WHERE "registo_id"=CONTAINERS_l(j)  AND "cargo_manifesto_id"=CARGOS(i);
            SELECT "container_position_z" INTO z FROM "cargo_manifest_container" WHERE "registo_id"=CONTAINERS_l(j)  AND "cargo_manifesto_id"=CARGOS(i);
            SELECT "container_gross_weigth" INTO weigth FROM "cargo_manifest_container" WHERE "registo_id"=CONTAINERS_l(j)  AND "cargo_manifesto_id"=CARGOS(i);
            INSERT INTO "weakly_operation_table" values(DATACARGO,operation_type,vehicle_id,NUMCONT,CONTAINERS_l(j) ,x,y,z,weigth);
        end loop;
   end loop;
    open tablecursor FOR
    SELECT * FROM "weakly_operation_table";
    RETURN (tablecursor);
end;


/
--[US404] As Fleet Manager, I want to know the number of days each ship has been idle since the beginning of the current year.
create or replace FUNCTION  "idle_time_ship" RETURN SYS_REFCURSOR
    IS
    NUMBERDAYS INT;
    TYPE SHIP_LIST IS TABLE OF "ship"."mmsi"%TYPE;
    shipList        SHIP_LIST;
    TYPE TRIP_LIST IS TABLE OF "trip"."trip_id"%TYPE;
    tripList        TRIP_LIST;
    DAYUNTILTODAY INT;
    SDATE "trip"."start_date"%TYPE;
    EDATE "trip"."start_date"%TYPE;
    YEARSYS DATE;
    SHIPIDLE SYS_REFCURSOR;
BEGIN
    SELECT trunc(SYSDATE , 'YEAR') INTO YEARSYS FROM DUAL;
    -- GUARDAR LISTA DE TODOS OS SHIP'S
    SELECT "vehicle_id" BULK COLLECT INTO shipList FROM "trip" WHERE "trip"."vehicle_id" = (SELECT "vehicle_id" FROM "vehicle" WHERE "ship_id" IS NOT NULL);
    for i in 1..shipList.COUNT LOOP
            select trunc(sysdate) - YEARSYS INTO DAYUNTILTODAY  from dual;

            -- GUARDAR TODAS AS TRIP ID'S ONDE SHIP E IGUAL AO DA LISTA E DATA DE FIM E DESTE ANO E E MENOR QUE A DATA ATUAL
            SELECT "trip_id" BULK COLLECT INTO tripList FROM "trip" WHERE "vehicle_id" = i AND extract(Year from sysdate) = extract(year from "end_date") AND sysdate > "end_date";
            FOR J IN 1..tripList.COUNT LOOP
                    SELECT "start_date" INTO SDATE FROM "trip" WHERE "trip"."trip_id"=J;
                    SELECT "end_date" INTO EDATE FROM "trip" WHERE "trip"."trip_id"=J;
                    IF extract(year from SDATE) = extract(Year from sysdate) THEN
                        -- CONTA O NUMERO DE DIAS E SOMA AO TOTAL
                        select EDATE - SDATE INTO NUMBERDAYS from dual;
                        DAYUNTILTODAY := DAYUNTILTODAY - NUMBERDAYS;
                    END IF;
                    IF extract(year from SDATE) = (extract(Year from sysdate)-1) THEN
                        select EDATE - YEARSYS INTO NUMBERDAYS from dual;
                        DAYUNTILTODAY := DAYUNTILTODAY - NUMBERDAYS;
                    END IF;
                END LOOP;
            insert into "idle_ship" values (i,NUMBERDAYS);
            dbms_output.put_line('The ship '||i||' has been idle '|| NUMBERDAYS ||' days');
        end loop;
    OPEN SHIPIDLE FOR
        SELECT * FROM "idle_ship";
    RETURN (SHIPIDLE);
end;
/
--[405] As Fleet Manager, I want to know the average occupancy rate per manifest of a given ship during a given period.

create or replace FUNCTION "func_avg_occu_rate"(start_date_  IN DATE, end_date_ IN DATE, ship_id_ IN INT)  RETURN SYS_REFCURSOR
IS
    result SYS_REFCURSOR;
    avg_occu FLOAT;
    ship_cap INT;
    manif_cont INT;
    ship_id int;
    TYPE CARGOMANIFEST_LIST IS TABLE OF "cargo_manifest"."cargo_manifesto_id"%TYPE;
    cargo_man_List       CARGOMANIFEST_LIST;
BEGIN
     SELECT "cargo_manifesto_id" BULK COLLECT INTO cargo_man_List FROM "cargo_manifest" INNER JOIN "vehicle" USING("vehicle_id")
        WHERE "ship_id" = ship_id AND "entry_date" > start_date_ AND "entry_date" < end_date_ AND "operation_type" = 'LOAD' OR "operation_type" = 'load';
        for i in 1..cargo_man_List.COUNT LOOP
            SELECT "capacity" INTO ship_cap FROM "ship" where "mmsi"= ship_id;
            SELECT COUNT("registo_id") INTO manif_cont FROM "cargo_manifest_container" WHERE "cargo_manifesto_id" = cargo_man_List(i);
                avg_occu := manif_cont/ship_cap;
                INSERT INTO "func_avg_occu_rate_table" VALUES(cargo_man_List(i),avg_occu);
                dbms_output.put_line('cargo manifest id: ' || cargo_man_List(i) || 'occupancy rate: ' || avg_occu);
        end loop;
        open result for
            select *from "func_avg_occu_rate_table";
    RETURN result;
END;
/



--[US406] As Fleet Manager, I want to know which ship voyages – place and date of origin and destination – had an occupancy rate below a certain threshold; by default, consider an occupancy rate threshold of 66%. Only the trips already concluded are to be considered.

create or replace FUNCTION "func_avg_ocup_ship_threshold" RETURN SYS_REFCURSOR
IS
    result SYS_REFCURSOR;
    avgcons SYS_REFCURSOR;
    avg_occu FLOAT;
    cargomanifestid int;
    ocupacit_rate float;
    tripid int;
    TYPE SHIP_LIST IS TABLE OF "vehicle"."ship_id"%TYPE;
    SHIP_ID_LIST       SHIP_LIST;
BEGIN
        SELECT "ship_id" BULK COLLECT INTO SHIP_ID_LIST FROM "vehicle" inner join "trip" using ("vehicle_id") WHERE "end_date"< sysdate;
        for i in 1..SHIP_ID_LIST.COUNT LOOP
        avg_occu := 0;
        dbms_output.put_line('HERE');
        avgcons :="func_avg_occu_rate" (to_date('2021-01-01', 'yyyy-mm-dd'), to_date(sysdate, 'yyyy-mm-dd'), SHIP_ID_LIST(i));
        loop 
        fetch avgcons into cargomanifestid, ocupacit_rate;
        SELECT "trip_id"  into tripid from "trip_stop" inner join "trip" using ("trip_id") where "cargo_manifest_id"=cargomanifestid and "trip"."end_date"< sysdate;
        if tripid != 0 then 
        avg_occu:= avg_occu + ocupacit_rate;
        end if;
        end loop;
        if avg_occu < 66 then
        insert into "func_avg_ocup_ship_threshold_table" select "source", "start_date","end_date","destiny"  from "trip" where "trip_id" = tripid;

        open result for
            select *from "func_avg_ocup_ship_threshold_table";
            end if;
        END LOOP;

        RETURN result;

END;
/