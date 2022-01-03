--Insert vessel_type(vessel_type_id,name)
INSERT INTO "vessel_type" VALUES(70,'setenta');
INSERT INTO "vessel_type" VALUES(80,'oitenta');
INSERT INTO "vessel_type" VALUES(60,'noventa');
INSERT INTO "vessel_type" VALUES(90,'sessenta');
INSERT INTO "vessel_type" VALUES(30,'trinta');
INSERT INTO "vessel_type" VALUES(79,'setenta e nove');
--insert container (container_id, ISOCode, checkDigit, maxWeightC, weightC, maxVol, repairRecomendation, refrigeration) Atualizado
insert into "container" VALUES ( 123456789, 'ISOC', 5, 10, 30, 45, 0.5, 2);
insert into "container" VALUES ( 123451111, 'COGR', 4, 10, 30, 45, 0.5, 2);
insert into "container" VALUES ( 123452222, 'PIEF', 1, 10, 30, 45, 0.5, 2);
insert into "container" VALUES ( 123453333, 'AKJS', 3, 10, 30, 45, 0.5, 2);
insert into "container" VALUES ( 123454444, 'DBAQ', 2, 10, 30, 45, 0.5, 2);
insert into "container" VALUES ( 123455555, 'POIQ', 7, 10, 30, 45, 0.5, 2);
insert into "container" VALUES ( 123466666, 'MKSA', 8, 10, 30, 45, 0.5, 2);
insert into "container" VALUES ( 123457777, 'QPQW', 9, 10, 30, 45, 0.5, 2);
insert into "container" VALUES ( 123458888, 'WIQN', 3, 10, 30, 45, 0.5, 2);

--insert certificate (certificate_id, name) Atualizado
INSERT INTO "certificate" VALUES (12, 'csc');

--insert container_certificate (container_id, certificate_id) Atualizado
INSERT INTO "container_certificate" VALUES (123456789,12);
INSERT INTO "container_certificate" VALUES (123451111,12);
INSERT INTO "container_certificate" VALUES (123452222,12);
INSERT INTO "container_certificate" VALUES (123453333,12);
INSERT INTO "container_certificate" VALUES (123454444,12);

--insert role (role_id, name) Atualizado
INSERT INTO "role" VALUES (1, 'Ship employe');
INSERT INTO "role" VALUES (2, 'Ship Capitan');
INSERT INTO "role" VALUES (3, 'Traffic Manager');
INSERT INTO "role" VALUES (4, 'Client');
INSERT INTO "role" VALUES (5, 'Fleet Manager');
INSERT INTO "role" VALUES (6, 'Warehouse Staff');
INSERT INTO "role" VALUES (7, 'Warehouse Manager');
INSERT INTO "role" VALUES (8, 'Port Staff');
INSERT INTO "role" VALUES (9, 'Port Manager');
INSERT INTO "role" VALUES (10, 'Ship chief electrical engineer');
INSERT INTO "role" VALUES (11, 'Truck driver');

--insert truck (plate, fuelCapacity) Atualizado
INSERT INTO "truck" VALUES ('22:CP:66', 123);


--insert truck_data(plate,date,lat,log) Atualizado
INSERT INTO "truck_data" VALUES('22:CP:66',to_date('11.09.20 12:30','yyyy.mm.dd hh24:mi'),52,-2);

--insert cargo_manifest(cargo_manifesto_id,vehicle_id,operation_type,destiny,entry_date) ADICIONAR DESTINY
INSERT INTO "cargo_manifest"("cargo_manifesto_id","vehicle_id","operation_type","destiny","entry_date") VALUES (1, 1, 'load',28261, to_date('11.09.20','dd.mm.yy'));
INSERT INTO "cargo_manifest"("cargo_manifesto_id","vehicle_id","operation_type","destiny","entry_date") VALUES (2, 2, 'unload',29003, to_date('15.03.20','dd.mm.yy'));
INSERT INTO "cargo_manifest"("cargo_manifesto_id","vehicle_id","operation_type","destiny","entry_date") VALUES (1, 1, 'load',28261, to_date('05.09.21','dd.mm.yy'));
INSERT INTO "cargo_manifest"("cargo_manifesto_id","vehicle_id","operation_type","destiny","entry_date") VALUES (1, 1, 'unload',14636, to_date('05.12.21','dd.mm.yy'));
INSERT INTO "cargo_manifest"("cargo_manifesto_id","vehicle_id","operation_type","destiny","entry_date") VALUES (2, 2, 'load',25008, to_date('22.04.21','dd.mm.yy'));
INSERT INTO "cargo_manifest"("cargo_manifesto_id","vehicle_id","operation_type","destiny","entry_date") VALUES (1, 1, 'unload',25008, to_date('01.10.21','dd.mm.yy'));
INSERT INTO "cargo_manifest"("cargo_manifesto_id","vehicle_id","operation_type","destiny","entry_date") VALUES (2, 2, 'load',20302, to_date('23.09.21','dd.mm.yy'));

--insert user(user_id,role_id,name,email,pass) Atualizado
INSERT INTO "user" VALUES (1,1, 'Antonio', 'tone@gmail.com', 'qwerty');
INSERT INTO "user" VALUES (2,2, 'Pedro', 'pedro@gmail.com', 'qwerty');
INSERT INTO "user" VALUES (3,3, 'Manuel', 'nel@gmail.com', 'qwerty');
INSERT INTO "user" VALUES (4,4, 'Joaquim', 'quim@gmail.com', 'qwerty');
INSERT INTO "user" VALUES (5,5, 'Jose', 'ze@gmail.com', 'qwerty');
INSERT INTO "user" VALUES (6,6, 'Tiago', 'ti@gmail.com', 'qwerty');
INSERT INTO "user" VALUES (7,7, 'Andre', 'dre@gmail.com', 'qwerty');
INSERT INTO "user" VALUES (8,8, 'Leonardo', 'leo@gmail.com', 'qwerty');
INSERT INTO "user" VALUES (9,9, 'Diogo', 'dig@gmail.com', 'qwerty');
INSERT INTO "user" VALUES (10,10, 'Jack', 'ja@gmail.com', 'qwerty');

--insert vehicle(vehicle_id,ship_id,plate) Atualizado
INSERT INTO "vehicle" ("ship_id") VALUES (1,228339600,null);--Ship
INSERT INTO "vehicle" ("ship_id") VALUES (2,210950000,null);--Ship
INSERT INTO "vehicle" ("plate") VALUES (3,null,'22:CP:66');--truck

-- insert trip(trip_id,vehicle_id,source,destiny,start_date,end_date,estimated_date) Atualizado
INSERT INTO "trip"("trip_id","vehicle_id","source","destiny","start_date","end_date","estimated_date") VALUES (1,1, 29002, 20351, to_date('11.09.20','dd.mm.yy'), null, to_date('14.10.20','dd.mm.yy'));
INSERT INTO "trip"("trip_id","vehicle_id","source","destiny","start_date","end_date","estimated_date") VALUES (2,3, 22226, 18476, to_date('01.08.20','dd.mm.yy'), null, to_date('14.10.20','dd.mm.yy'));

-- insert trip_stops(trip_id,port_wharehouse_id,cargo_manifest_id,data,estimate_date) Atualizado
INSERT INTO "trip_stop" VALUES (1,28261,1,to_date('20.09.20','dd.mm.yy'),to_date('23.10.20','dd.mm.yy'));
INSERT INTO "trip_stop" VALUES (1,18476,1,to_date('25.09.20','dd.mm.yy'),to_date('20.10.20','dd.mm.yy'));
INSERT INTO "trip_stop" VALUES (1,17386,1,to_date('22.08.20','dd.mm.yy'),to_date('25.10.20','dd.mm.yy'));
INSERT INTO "trip_stop" VALUES (2,18476,4,to_date('22.08.20','dd.mm.yy'),to_date('20.10.20','dd.mm.yy'));
INSERT INTO "trip_stop" VALUES (2,17386,5,to_date('22.08.20','dd.mm.yy'),to_date('30.10.20','dd.mm.yy'));

--insert registo_container (reisto_id,container_id,user_id,date,register_date,source,destiny,delivered) Atualizado
INSERT INTO "registo_container"("registo_id","container_id","user_id","date","register_date","source","destiny","delivered") VALUES (1,123456789,4,to_date('17.11.20 12:30','yyyy.mm.dd hh24:mi'),to_date('15.11.20 12:30','yyyy.mm.dd hh24:mi'),29002,18476,'no');
INSERT INTO "registo_container"("registo_id","container_id","user_id","date","register_date","source","destiny","delivered") VALUES (2,123456789,4,to_date('11.09.20 12:30','yyyy.mm.dd hh24:mi'),to_date('09.09.20 12:30','yyyy.mm.dd hh24:mi'),29002,27248,'no');
INSERT INTO "registo_container"("registo_id","container_id","user_id","date","register_date","source","destiny","delivered") VALUES (3,123456789,4,to_date('11.09.20 12:35','yyyy.mm.dd hh24:mi'),to_date('10.09.20 12:30','yyyy.mm.dd hh24:mi'),29002,22226,'yes');

--insert cargo_manifest_container (registo_id,cargo_manifest,container_position_x,
--container_position_y,container_position_z, container_gross_weigth) Atualizado
INSERT INTO "cargo_manifest_container" VALUES (1,1,0,1,2,0.4);
INSERT INTO "cargo_manifest_container" VALUES (4,1,10,1,2,0.4);
INSERT INTO "cargo_manifest_container" VALUES (7,2,18,19,24,1798);
INSERT INTO "cargo_manifest_container" VALUES (8,2,18,19,24,1798);

--insert port_warehouse(port_wharehouse_id,name,continent,country,type,lat,log,capacity,from_port) Atualizado
INSERT INTO "port_warehouse" VALUES (29003, 'Liverpool', 'Europe', 'United Kingdom', 'warehouse', 53.46666667, -3.033333333,200,14636);
INSERT INTO "port_warehouse" VALUES (14636, 'Los Angeles', 'America', 'United States', 'warehouse', 33.71666667, -118.2666667,250,20302);
INSERT INTO "port_warehouse" VALUES (25008, 'New Jersey', 'America', 'United States', 'warehouse', 40.66666667, -74.16666667,300,29003);
INSERT INTO "port_warehouse" VALUES (20302, 'Rio Grande', 'America', 'Brazil', 'warehouse', -32.06666667, -52.06666667,150,34562);
INSERT INTO "port_warehouse" VALUES (34562, 'Barcelona', 'Europe', 'Spain', 'warehouse', 41.33333333, 2.166666667,250,14636);

--insert audit_trails(audit_trails_id,user_id,container_id,cargo_manifest_id, date, operation_type)FRANCISCO
INSERT INTO "audit_trails" VALUES (1,1,123456789,1,to_date('20.09.20','dd.mm.yy'),'insert');
INSERT INTO "audit_trails" VALUES (2,1,123333789,3,to_date('23.09.20','dd.mm.yy'),'insert');
INSERT INTO "audit_trails" VALUES (3,1,187654789,4,to_date('21.09.20','dd.mm.yy'),'delete');
INSERT INTO "audit_trails" VALUES (4,1,234444789,5,to_date('20.10.20','dd.mm.yy'),'insert');

--Insert employe_shedule(employe_id,port_warehoue_id,day_of_the_week,hour)
INSERT INTO "employe_schedule" VALUES (8,29003,'Monday','12h30');
INSERT INTO "employe_schedule" VALUES (8,29003,'Friday','16h30');
INSERT INTO "employe_schedule" VALUES (8,29003,'Tuesday','12h30');

--Insert goods(client_id,container_id,cargo_manifest,weigth)
INSERT INTO "goods" VALUES (4,123456789,1,13.5);

--Insert client(user_id,credit_card,adress)
INSERT INTO "client" VALUES (4,123123,"adress");

--Insert port_manager(user_id,port_id)
INSERT INTO "port_manager" VALUES (9,28261);