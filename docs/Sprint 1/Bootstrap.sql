--insert container
insert into "container" VALUES ( 123456789, 'ISOC', 5, 10, 30, 45, 0.5, 2);
insert into "container" VALUES ( 123451111, 'COGR', 4, 10, 30, 45, 0.5, 2);
insert into "container" VALUES ( 123452222, 'PIEF', 1, 10, 30, 45, 0.5, 2);
insert into "container" VALUES ( 123453333, 'AKJS', 3, 10, 30, 45, 0.5, 2);
insert into "container" VALUES ( 123454444, 'DBAQ', 2, 10, 30, 45, 0.5, 2);
insert into "container" VALUES ( 123455555, 'POIQ', 7, 10, 30, 45, 0.5, 2);
insert into "container" VALUES ( 123466666, 'MKSA', 8, 10, 30, 45, 0.5, 2);
insert into "container" VALUES ( 123457777, 'QPQW', 9, 10, 30, 45, 0.5, 2);
insert into "container" VALUES ( 123458888, 'WIQN', 3, 10, 30, 45, 0.5, 2);

--insert certificate
INSERT INTO "certificate" VALUES (12, 'csc');

--insert container_certificate
INSERT INTO "container_certificate" VALUES (123456789,12);
INSERT INTO "container_certificate" VALUES (123451111,12);
INSERT INTO "container_certificate" VALUES (123452222,12);
INSERT INTO "container_certificate" VALUES (123453333,12);
INSERT INTO "container_certificate" VALUES (123454444,12);

--insert role
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

--insert truck
INSERT INTO "truck" VALUES ('22:CP:66', 123);

--insert truck truck_transportation
INSERT INTO "truck_transportation" VALUES(123466666,to_date('11.09.20 12:30','yyyy.mm.dd hh24:mi'),to_date('11.09.20 16:30','yyyy.mm.dd hh24:mi'),null,'22:CP:66');

--insert truck_data
INSERT INTO "truck_data" VALUES('22:CP:66',to_date('11.09.20 12:30','yyyy.mm.dd hh24:mi'),52,-2);

--insert cargo manifest
INSERT INTO "cargo_manifest" VALUES (1, 210950000, 12, 22, 2, 3332.3, 'load', to_date('11.09.20','dd.mm.yy'),to_date('14.10.20','dd.mm.yy'), 29002,20351);

--insert user
INSERT INTO "user" VALUES (1,1, 'Antonio', 'tone@gmail.com', 'qwerty');
INSERT INTO "user" VALUES (2,2, 'Pedro', 'pedro@gmail.com', 'qwerty');
INSERT INTO "user" VALUES (3,3, 'Manuel', 'nel@gmail.com', 'qwerty');
INSERT INTO "user" VALUES (4,4, 'Joaquim', 'quim@gmail.com', 'qwerty');
INSERT INTO "user" VALUES (5,5, 'Jose', 'ze@gmail.com', 'qwerty');


-- insert trip

INSERT INTO "trip" VALUES (1 ,210950000, 29002, 20351, to_date('11.09.20','dd.mm.yy'), null, to_date('14.10.20','dd.mm.yy'));

-- insert trip_stops
INSERT INTO "trip_stop" VALUES (1,28261,to_date('20.09.20','dd.mm.yy'));
INSERT INTO "trip_stop" VALUES (1,18476,to_date('25.09.20','dd.mm.yy'));
