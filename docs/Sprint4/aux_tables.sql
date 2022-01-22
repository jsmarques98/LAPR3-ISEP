create table "idle_ship"(
    ship_id int,
    days int
);
CREATE TABLE "weakly_operation_table"(
            "data" Date,
            "type" varchar(255),
            "vehicle_id" number,
            "num_containers" number,
            "registo_id" number,
            "x" number,
            "y" number,
            "z" number,
            "weigth" FLOAT);
CREATE TABLE "func_avg_occu_rate_table"(
                "cargo_id" int,
                 avg_occu FLOAT
                );

create table "func_avg_ocup_ship_threshold_table"(
origem int,
data_origem DATE,
data_fim DATE,
fim int
);