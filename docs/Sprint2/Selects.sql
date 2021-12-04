--[US204] As Client, I want to know the current situation of a specific container being used to transport my goods.

select  case "delivered" WHEN 'no' 
THEN 'Ship' ELSE 'PORT' END as "tipo",
case "delivered" WHEN 'no' 
THEN "ship"."ship_name" ELSE (select "name" from "port_warehouse" where "port_warehouse_id"="destiny") END as "name"
from "cargo_manifest" 
INNER JOIN "registo_container" using("cargo_manifesto_id")    
INNER JOIN "ship" on "ship"."mmsi"="cargo_manifest"."ship_id"           
where "container_id"='123456789';


--[US205] - As Ship Captain, I want the list of containers to be offloaded in the next port, including container identifier, type, position, and load
select "container_id" as identificador,
case "refrigeration" WHEN NULL THEN 'nao refrigerado' ELSE 'refrigerado' end as type,
(select "container_position_x" from "cargo_manifest_container" where "registo_container"."registo_id" = "cargo_manifest_container"."registo_id")as x,
(select "container_position_y" from "cargo_manifest_container" where "registo_container"."registo_id" = "cargo_manifest_container"."registo_id")as y,
(select "container_position_z" from "cargo_manifest_container" where "registo_container"."registo_id" = "cargo_manifest_container"."registo_id")as z,
"weightC"
from "container" INNER JOIN "registo_container" using("container_id") 
where "destiny" = (select "port_wharehouse_id" from "trip_stop" where "data" is null and "trip_id" = 2 and ROWNUM =1);


--[US206] - As Ship Captain, I want the list of containers to be loaded in the next port, including container identifier, type, and load.
select "container_id" as identificador,
case "refrigeration" WHEN NULL THEN 'nao refrigerado' ELSE 'refrigerado' end as type,
(select "container_position_x" from "cargo_manifest_container" where "registo_container"."registo_id" = "cargo_manifest_container"."registo_id")as x,
(select "container_position_y" from "cargo_manifest_container" where "registo_container"."registo_id" = "cargo_manifest_container"."registo_id")as y,
(select "container_position_z" from "cargo_manifest_container" where "registo_container"."registo_id" = "cargo_manifest_container"."registo_id")as z,
"weightC"
from "container" INNER JOIN "registo_container" using("container_id") 
where "source" = (select "port_wharehouse_id" from "trip_stop" where "data" is null and "trip_id" = 2 and ROWNUM =1);


--[US209] As Ship Captain, I want to know the occupancy rate (percentage) of a given ship
--for a given cargo manifest. Occupancy rate is the ratio between total number of containers
--in the ship coming from a given manifest and the total capacity of the ship, i.e., the
--maximum number of containers the ship can load
select "mmsi","cargo_manifesto_id",(100*((select count("registo_id") from "cargo_manifest_container" where "cargo_manifesto_id"=
(select "cargo_manifesto_id" from "cargo_manifest" where "ship_id" = '210950000'))/("capacity"))) as perc from "ship" 
INNER JOIN "cargo_manifest" on "cargo_manifest"."ship_id"="ship"."mmsi" where "mmsi" ='210950000' and "cargo_manifesto_id" = '1';

--[US209] - As Ship Captain, I want to know the occupancy rate of a given ship at a given moment.
select "mmsi","cargo_manifesto_id",(100*((select count("registo_id") from "cargo_manifest_container" where "cargo_manifesto_id"=
(select "cargo_manifesto_id" from "cargo_manifest" where "ship_id" = '210950000'))/("capacity"))) as perc from "ship" 
INNER JOIN "cargo_manifest" on "cargo_manifest"."ship_id"="ship"."mmsi" where "mmsi" ='210950000' and "entry_date" BETWEEN '2020.09.10' AND '2020.09.17' ;

