--insert container (container_id, ISOCode, checkDigit, maxWeightC, weightC, maxVol, repairRecomendation, refrigeration)
insert into "container" VALUES ( 123456789, 'ISOC', 5, 10, 30, 45, 0.5, 2);
insert into "container" VALUES ( 123451111, 'COGR', 4, 10, 30, 45, 0.5, 2);
insert into "container" VALUES ( 123452222, 'PIEF', 1, 10, 30, 45, 0.5, 2);
insert into "container" VALUES ( 123453333, 'AKJS', 3, 10, 30, 45, 0.5, 2);
insert into "container" VALUES ( 123454444, 'DBAQ', 2, 10, 30, 45, 0.5, 2);
insert into "container" VALUES ( 123455555, 'POIQ', 7, 10, 30, 45, 0.5, 2);
insert into "container" VALUES ( 123466666, 'MKSA', 8, 10, 30, 45, 0.5, 2);
insert into "container" VALUES ( 123457777, 'QPQW', 9, 10, 30, 45, 0.5, 2);
insert into "container" VALUES ( 123458888, 'WIQN', 3, 10, 30, 45, 0.5, 2);

--insert certificate (certificate_id, name)
INSERT INTO "certificate" VALUES (12, 'csc');

--insert container_certificate (container_id, certificate_id)
INSERT INTO "container_certificate" VALUES (123456789,12);
INSERT INTO "container_certificate" VALUES (123451111,12);
INSERT INTO "container_certificate" VALUES (123452222,12);
INSERT INTO "container_certificate" VALUES (123453333,12);
INSERT INTO "container_certificate" VALUES (123454444,12);

--insert role (role_id, name)
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

--insert truck (plate, fuelCapacity)
INSERT INTO "truck" VALUES ('22:CP:66', 123);

--insert truck_transportation (plate, date, lat, log)
INSERT INTO "truck_transportation" VALUES(123466666,to_date('11.09.20 12:30','yyyy.mm.dd hh24:mi'),to_date('11.09.20 16:30','yyyy.mm.dd hh24:mi'),null,'22:CP:66');

--insert truck_data(plate,date,lat,log)
INSERT INTO "truck_data" VALUES('22:CP:66',to_date('11.09.20 12:30','yyyy.mm.dd hh24:mi'),52,-2);

--insert cargo manifest(cargo_manifesto_id,vehicle_id,operation_type,destiny,entry_date)FRANCISCO
INSERT INTO "cargo_manifest" VALUES (1, 210950000, 'load',29003, to_date('11.09.20','dd.mm.yy'));
INSERT INTO "cargo_manifest" VALUES (2, 235092459, 'unload',29003, to_date('15.03.20','dd.mm.yy'));
INSERT INTO "cargo_manifest" VALUES (3, 249047000, 'load',14636, to_date('05.09.21','dd.mm.yy'));
INSERT INTO "cargo_manifest" VALUES (4, 256888000, 'unload',14636, to_date('05.12.21','dd.mm.yy'));
INSERT INTO "cargo_manifest" VALUES (5, 303221000, 'load',25008, to_date('22.04.21','dd.mm.yy'));
INSERT INTO "cargo_manifest" VALUES (6, 636019825, 'unload',25008, to_date('01.10.21','dd.mm.yy'));
INSERT INTO "cargo_manifest" VALUES (7, 636092932, 'load',20302, to_date('23.09.21','dd.mm.yy'));

--insert user(user_id,role_id,name,email,pass)
INSERT INTO "user" VALUES (1,1, 'Antonio', 'tone@gmail.com', 'qwerty');
INSERT INTO "user" VALUES (2,2, 'Pedro', 'pedro@gmail.com', 'qwerty');
INSERT INTO "user" VALUES (3,3, 'Manuel', 'nel@gmail.com', 'qwerty');
INSERT INTO "user" VALUES (4,4, 'Joaquim', 'quim@gmail.com', 'qwerty');
INSERT INTO "user" VALUES (5,5, 'Jose', 'ze@gmail.com', 'qwerty');


-- insert trip(trip_id,vehicle_id,source,destiny,start_date,end_date,estimated_date)francisco
INSERT INTO "trip" VALUES (1 ,210950000, 29002, 20351, to_date('11.09.20','dd.mm.yy'), null, to_date('14.10.20','dd.mm.yy'));
INSERT INTO "trip" VALUES (2 ,228339600, 22226, 18476, to_date('01.08.20','dd.mm.yy'), null, to_date('14.10.20','dd.mm.yy'));

-- insert trip_stops(trip_id,port_wharehouse_id,cargo_manifest_id,data,estimate_date)francisco
INSERT INTO "trip_stop" VALUES (1,28261,1,to_date('20.09.20','dd.mm.yy'),to_date('23.10.20','dd.mm.yy'));
INSERT INTO "trip_stop" VALUES (1,18476,2,to_date('25.09.20','dd.mm.yy'),to_date('20.10.20','dd.mm.yy'));
INSERT INTO "trip_stop" VALUES (1,17386,3,to_date('22.08.20','dd.mm.yy'),to_date('25.10.20','dd.mm.yy'));
INSERT INTO "trip_stop" VALUES (2,18476,4,to_date('22.08.20','dd.mm.yy'),to_date('20.10.20','dd.mm.yy'));
INSERT INTO "trip_stop" VALUES (2,17386,5,to_date('22.08.20','dd.mm.yy'),to_date('30.10.20','dd.mm.yy'));

--insert registos container (registo_id,container_id,user_id,date,register_date,source,destiny,delivered)
INSERT INTO "registo_container" VALUES (3,123456789,5,to_date('17.11.20 12:30','yyyy.mm.dd hh24:mi'),to_date('15.11.20 12:30','yyyy.mm.dd hh24:mi'),29002,18476,'no');
INSERT INTO "registo_container" VALUES (1,123453333,5,to_date('11.09.20 12:30','yyyy.mm.dd hh24:mi'),to_date('09.09.20 12:30','yyyy.mm.dd hh24:mi'),29002,27248,'no');
INSERT INTO "registo_container" VALUES (4,123454444,5,to_date('11.09.20 12:35','yyyy.mm.dd hh24:mi'),to_date('10.09.20 12:30','yyyy.mm.dd hh24:mi'),29002,22226,'yes');
INSERT INTO "registo_container" VALUES (5,123451111,5,to_date('11.09.20 12:35','yyyy.mm.dd hh24:mi'),to_date('11.09.20 12:00','yyyy.mm.dd hh24:mi'),29002,25350,'yes');
INSERT INTO "registo_container" VALUES (6,123458888,5,to_date('2.11.19 12:30','yyyy.mm.dd hh24:mi'),to_date('20.10.19 12:30','yyyy.mm.dd hh24:mi'),29002,30045,'no');
INSERT INTO "registo_container" VALUES (7,123458888,5,to_date('2.11.19 12:30','yyyy.mm.dd hh24:mi'),to_date('30.10.19 12:30','yyyy.mm.dd hh24:mi'),29002,17386,'no');
INSERT INTO "registo_container" VALUES (8,123458888,5,to_date('2.11.19 12:30','yyyy.mm.dd hh24:mi'),to_date('23.10.19 12:30','yyyy.mm.dd hh24:mi'),17386,22226,'no');

--insert cargo_manifest_container (registo_id,cargo_manifest,container_position_x,
--container_position_y,container_position_z, container_gross_weigth)
INSERT INTO "cargo_manifest_container" VALUES (1,1,0,1,2,0.4);
INSERT INTO "cargo_manifest_container" VALUES (4,1,10,1,2,0.4);
INSERT INTO "cargo_manifest_container" VALUES (7,2,18,19,24,1798);
INSERT INTO "cargo_manifest_container" VALUES (8,2,18,19,24,1798);

--insert port_warehouse(port_warehouse_id,name,continent,country,type,lat,log,capacity,from_port)francisco
INSERT INTO "port_warehouse" VALUES (29003, 'Liverpool', 'Europe', 'United Kingdom', 'warehouse', 53.46666667, -3.033333333,200,14636);
INSERT INTO "port_warehouse" VALUES (14636, 'Los Angeles', 'America', 'United States', 'warehouse', 33.71666667, -118.2666667,250,20302);
INSERT INTO "port_warehouse" VALUES (25008, 'New Jersey', 'America', 'United States', 'warehouse', 40.66666667, -74.16666667,300,29003);
INSERT INTO "port_warehouse" VALUES (20302, 'Rio Grande', 'America', 'Brazil', 'warehouse', -32.06666667, -52.06666667,150,34562);
INSERT INTO "port_warehouse" VALUES (34562, 'Barcelona', 'Europe', 'Spain', 'warehouse', 41.33333333, 2.166666667,250,14636);

--insert audit_trails(audit_trails_id,user_id,container_id,cargo_manifest_id, date, operation_type)FRANCISCO
INSERT INTO "audit_trails" VALUES (1,1,123456789,1,to_date('20.09.20','dd.mm.yy'),'optype');
INSERT INTO "audit_trails" VALUES (2,1,123333789,3,to_date('23.09.20','dd.mm.yy'),'optype');
INSERT INTO "audit_trails" VALUES (3,1,187654789,4,to_date('21.09.20','dd.mm.yy'),'optype');
INSERT INTO "audit_trails" VALUES (4,1,234444789,5,to_date('20.10.20','dd.mm.yy'),'optype');