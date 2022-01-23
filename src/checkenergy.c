#include <stdio.h>
#include <stdlib.h>


void check_energy(){

    FILE * file_gens = fopen("gens.txt", "r");
    FILE * file_gens_power = fopen("gens_power.txt", "r");   //cria um aontador para o ficheiro
    FILE * file_refrig_container = fopen("refrig_containers.txt", "r");
    FILE * file_gens_needed_power = fopen("gens_needed_power.txt", "r");


    if(file_gens == NULL) {
        perror("file_gens not found");  //verificar se o ficheiro file_gens existe
        exit(1);         
   }
   if(file_gens_power == NULL) {
        perror("file_gens_power not found");  //verificar se o ficheiro file_gens_power existe
        exit(1);         
   }
   if(file_refrig_container == NULL) {
        perror("file_refrig_container not found");  //verificar se o ficheiro file_refrig_container existe
        exit(1);         
   }
   if(file_gens_needed_power == NULL) {
        perror("file_gens_needed_power not found");  //verificar se o ficheiro file_gens_needed_power existe
        exit(1);         
   }

    int nr_generators; //5
    int gen_power;//75
    int total_power;//nr_generators * gen_power
    int needed_power;//sumatorio de container_needed_power 
    int nr_refrigerated_containers;//1000
    int container_needed_power;//1

    fscanf(file_gens,"nr_generators:%d\n",&nr_generators);
    fclose(file_gens);
    fscanf(file_gens_power,"gen_power:%d\n",&gen_power);
    fclose(file_gens_power);
    fscanf(file_refrig_container,"nr_refrigerated_containers:%d\n",&nr_refrigerated_containers);
    fclose(file_refrig_container);
    fscanf(file_gens_needed_power,"container_needed_power:%d\n",&container_needed_power);
    fclose(file_gens_needed_power);

    total_power = nr_generators * gen_power;
    
    needed_power = nr_refrigerated_containers * container_needed_power;

    if(total_power < needed_power){

        printf("TOTAL POWER LOWER THEN NEEDED. TOTAL POWER: %d NEEDED POWER: %d\a\n", total_power, needed_power);

    }else{
		printf("Total power enough for all refrigerated containers\n");
	}
}
