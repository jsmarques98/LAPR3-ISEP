--insert ships
insert into "ship" values (123456789, 'calsgni2', 1234567, 'Barquinho', 1, 120, 30, 10, 60, 5, 1);
insert into "ship" values (12345678, 'calsgni', 1234568, 'Barquinho', 1, 120, 30, 10, 60, 5, 1);
insert into "ship" values (123456789, 'calsgni1', 1234567, 'Barquinho', 1, 120, 30, 10, 60, 5, 1);
insert into "ship" values (123456789, 'calsgni2', 12345679, 'Barquinho', 1, 120, 30, 10, 60, 5, 1);
insert into "ship" values (123456789, 'calsgni2', 1234567, 'Barquinho', 0, 120, 30, 10, 60, 5, 1);
insert into "ship" values (123456789, 'calsgni2', 1234567, 'Barquinho', 1, 0, 30, 10, 60, 5, 1);
insert into "ship" values (123456789, 'calsgni2', 1234567, 'Barquinho', 1, 120, 0, 10, 60, 5, 1);
insert into "ship" values (123456789, 'calsgni2', 1234567, 'Barquinho', 1, 120, 30, 0, 60, 5, 1);
insert into "ship" values (123456789, 'calsgni2', 1234567, 'Barquinho', 1, 120, 30, 10, 0, 5, 1);
insert into "ship" values (123456789, 'calsgni2', 1234567, 'Barquinho', 1, 120, 30, 10, 60, 0, 1);
insert into "ship" values (123456789, 'calsgni2', 1234567, 'Barquinho', 1, 120, 30, 10, 60, 5, 111);


--insert port_warehouse
INSERT INTO "port_warehouse" VALUES (1, 'casa da avo 1', 'europe', 'Portugal', 'wharehouse', 50.555, 32.322);
INSERT INTO "port_warehouse" VALUES (2, 'casa da avo 3', 'europe', 'Portugal', 'wharehouse', -100.555, 102.322);
INSERT INTO "port_warehouse" VALUES (3, 'casa da avo 4', 'europe', 'Portugal', 'wharehouse', -100.555, 103.322);


--insert container
insert into "container" VALUES ( 123456789, 'ISOC', 123456789, 10, 30, 45, 0.5, 2);
insert into "container" VALUES ( 123456789, 'ISOCod5', 123456789, 10, 30, 45, 0.5, 2);
insert into "container" VALUES ( 123456789, 'ISOC', 123456789, -10, 30, 45, 0.5, 2);
insert into "container" VALUES ( 123456789, 'ISOC', 123456789, 10, 30, 45, 0.5, 2);
insert into "container" VALUES ( 123456789, 'ISOC', 123456789, 10, -30, 45, 0.5, 2);
insert into "container" VALUES ( 123456789, 'ISOC', 123456789, 10, 30, -20, 0.5, 2);

--insert certificate
INSERT INTO "certificate" VALUES (12, 'csc');

--insert role
INSERT INTO "role" VALUES (1, 'Ship employe');

--insert truck
INSERT INTO "truck" VALUES ('22:CP:66', 123);
INSERT INTO "truck" VALUES ('22-CP-66', 123);
INSERT INTO "truck" VALUES ('22:CP:66', 0);


--insert cargo manifest
INSERT INTO "cargo_manifest" VALUES (1, 1, 1, 12, 22, 2, 3332.3, 'load', to_date('11.09.20','dd.mm.yy'));
INSERT INTO "cargo_manifest" VALUES (2, 3, 3, -12, 22, 2, 3332.3, 'load', to_date('11.09.20','dd.mm.yy'));
INSERT INTO "cargo_manifest" VALUES (3, 4, 3, 12, -12, 2, 3332.3, 'load', to_date('11.09.20','dd.mm.yy'));
INSERT INTO "cargo_manifest" VALUES (4, 4, 3, 12, 22, -12, 3332.3, 'type', to_date('11.09.20','dd.mm.yy'));
INSERT INTO "cargo_manifest" VALUES (5, 4, 3, 12, 22, 2, 3332.3, 'unload', to_date('11.09.20','dd.mm.yy'));
INSERT INTO "cargo_manifest" VALUES (6, 4, 3, 12, 22, 2, -3332.3, 'unload', to_date('11.09.20','dd.mm.yy'));

--insert container_certificate
INSERT INTO "container_certificate" VALUES (123465789, 1);

--INSERT ship_data
insert into "ship_data" values (123456789,to_date('11.09.21'),to_date('13.09.21'), 20, 20, 30, 10, 'heading2' , 'cargo2' , 'transceiver_class3')
insert into "ship_data" values (123456789,to_date('11.09.21'),to_date('13.09.21'), 100, 20, 30, 10, 'heading2' , 'cargo2' , 'transceiver_class3')
insert into "ship_data" values (123456789,to_date('11.09.21'),to_date('13.09.21'), -100, 20, 30, 10, 'heading2' , 'cargo2' , 'transceiver_class3')
insert into "ship_data" values (123456789,to_date('11.09.21'),to_date('13.09.21'), 20, 100, 30, 10, 'heading2' , 'cargo2' , 'transceiver_class3' )
insert into "ship_data" values (123456789,to_date('11.09.21'),to_date('13.09.21'), 20, -100, 30, 10, 'heading2' , 'cargo2' , 'transceiver_class3' )
insert into "ship_data" values (123456789,to_date('11.09.21'),to_date('13.09.21'), 20, 20, -100, 10, 'heading2' , 'cargo2' , 'transceiver_class3' )
insert into "ship_data" values (123456789,to_date('11.09.21'),to_date('13.09.21'), 20, 20, 30, -10, 'heading2' , 'cargo2' , 'transceiver_class3insert')
insert into "ship_data" values (123456789,to_date('11.09.21'),to_date('13.09.21'), 20, 20, 30, 1010, 'heading2' , 'cargo2' , 'transceiver_class3insert')

--insert truck_data into
INSERT INTO "truck_data" VALUES ('22:CP:66', TO_DATE('11.09.21'), TO_DATE('14.09.21'), 80,80);
INSERT INTO "truck_data" VALUES ('22:CP:66', TO_DATE('11.09.21'), TO_DATE('14.09.21'), 91,80);
INSERT INTO "truck_data" VALUES ('22:CP:66', TO_DATE('11.09.21'), TO_DATE('14.09.21'), 80,91);
INSERT INTO "truck_data" VALUES ('22:CP:66', TO_DATE('11.09.21'), TO_DATE('14.09.21'), 80,80);

--insert employe
INSERT INTO "employe" VALUES (1, 'Francisco', 'francisco@gmail.com', 'qwerty', 1);
INSERT INTO "employe" VALUES (1, 'Francisco', 'francisco.gmail.com', 'qwerty', 1);

--insert employe_schedule
INSERT INTO "employee_schedule" VALUES (1, 1, 'Friday','ok');
INSERT INTO "employee_schedule" VALUES (1, 1, 'Naosei','ok');
