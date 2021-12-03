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

--insert cargo manifest(cargo_manifesto_id,ship_id,operation_type,entry_date)
INSERT INTO "cargo_manifest" VALUES (1, 210950000, 'load', to_date('11.09.20','dd.mm.yy'));
INSERT INTO "cargo_manifest" VALUES (2, 235092459, 'unload', to_date('15.03.20','dd.mm.yy'));
INSERT INTO "cargo_manifest" VALUES (3, 249047000, 'load', to_date('05.09.21','dd.mm.yy'));
INSERT INTO "cargo_manifest" VALUES (4, 256888000, 'unload', to_date('05.12.21','dd.mm.yy'));
INSERT INTO "cargo_manifest" VALUES (5, 303221000, 'load', to_date('22.04.21','dd.mm.yy'));
INSERT INTO "cargo_manifest" VALUES (6, 636019825, 'unload', to_date('01.10.21','dd.mm.yy'));
INSERT INTO "cargo_manifest" VALUES (7, 636092932, 'load', to_date('23.09.21','dd.mm.yy'));

--insert user(user_id,role_id,name,email,pass)
INSERT INTO "user" VALUES (1,1, 'Antonio', 'tone@gmail.com', 'qwerty');
INSERT INTO "user" VALUES (2,2, 'Pedro', 'pedro@gmail.com', 'qwerty');
INSERT INTO "user" VALUES (3,3, 'Manuel', 'nel@gmail.com', 'qwerty');
INSERT INTO "user" VALUES (4,4, 'Joaquim', 'quim@gmail.com', 'qwerty');
INSERT INTO "user" VALUES (5,5, 'Jose', 'ze@gmail.com', 'qwerty');


-- insert trip(trip_id,ship_id,source,destiny,start_date,end_date,estimated_date)
INSERT INTO "trip" VALUES (1 ,210950000, 29002, 20351, to_date('11.09.20','dd.mm.yy'), null, to_date('14.10.20','dd.mm.yy'));
INSERT INTO "trip" VALUES (2 ,228339600, 22226, 18476, to_date('01.08.20','dd.mm.yy'), null, to_date('14.10.20','dd.mm.yy'));

-- insert trip_stops(trip_id,port_wharehouse_id,data)
INSERT INTO "trip_stop" VALUES (1,28261,to_date('20.09.20','dd.mm.yy'));
INSERT INTO "trip_stop" VALUES (1,18476,to_date('25.09.20','dd.mm.yy'));
INSERT INTO "trip_stop" VALUES (1,18476,null);
INSERT INTO "trip_stop" VALUES (1,18476,null);
INSERT INTO "trip_stop" VALUES (3,18476,to_date('22.08.20','dd.mm.yy'));
INSERT INTO "trip_stop" VALUES (3,18476,null);

--insert registos container (registo_id,container_id,cargo_manifesto_id,user_id,date,source,destiny,delivered)
INSERT INTO "registo_container" VALUES (3,123456789, 1,5,to_date('17.11.20 12:30','yyyy.mm.dd hh24:mi'),29002,18476,'no');
INSERT INTO "registo_container" VALUES (1,123453333, 1,5,to_date('11.09.20 12:30','yyyy.mm.dd hh24:mi'),29002,27248,'no');
INSERT INTO "registo_container" VALUES (4,123451111, 1,5,to_date('11.09.20 12:35','yyyy.mm.dd hh24:mi'),29002,27248,'yes');
INSERT INTO "registo_container" VALUES (5,123451111, 1,5,to_date('11.09.20 12:35','yyyy.mm.dd hh24:mi'),29002,27248,'yes');
INSERT INTO "registo_container" VALUES (6,123453333, 1,5,to_date('2.11.19 12:30','yyyy.mm.dd hh24:mi'),29002,27248,'no');

--insert cargo_manifest_container (registo_id,cargo_manifest,container_position_x,
--container_position_y,container_position_z, container_gross_weigth)
INSERT INTO "cargo_manifest_container" VALUES (1,1,0,1,2,0.4);
INSERT INTO "cargo_manifest_container" VALUES (2,1,10,1,2,0.4);
INSERT INTO "cargo_manifest_container" VALUES (3,2,18,19,24,1798);

--insert port_warehouse(port_warehouse_id,name,continent,country,type,lat,log,capacity)
INSERT INTO "port_warehouse" VALUES (29003, 'Liverpool', 'Europe', 'United Kingdom', 'warehouse', 53.46666667, -3.033333333,200);
INSERT INTO "port_warehouse" VALUES (14636, 'Los Angeles', 'America', 'United States', 'warehouse', 33.71666667, -118.2666667,250);
INSERT INTO "port_warehouse" VALUES (25008, 'New Jersey', 'America', 'United States', 'warehouse', 40.66666667, -74.16666667,300);
INSERT INTO "port_warehouse" VALUES (20302, 'Rio Grande', 'America', 'Brazil', 'warehouse', -32.06666667, -52.06666667,150);
INSERT INTO "port_warehouse" VALUES (34562, 'Barcelona', 'Europe', 'Spain', 'warehouse', 41.33333333, 2.166666667,250);
