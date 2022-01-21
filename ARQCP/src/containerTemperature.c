#include <stdio.h>                                                                                                          
#include <stdlib.h>                                                                                                           

#include "asm.h"

// US 410   
                                                                                                                           
void amountEnergy(){                                                                                                  
    int a,b,c;                                                                                                              
    printf("Que posição do array pertende (Linha, Coluna, Profundidade): \n");          
    scanf("%d%d%d",&a,&b,&c);                                                                                               
    int id = returnId(a,b,c); 
    if(id == 0){
		printf("Posição inválida\n");
	}	
	printf("Id: %d",id);
	
	int flag = containerRefrig();
    if(flag == 1){
		refrigEnergy = refrigirationEnergy();
		printf("A energia ", refrigEnergy);
	}else{
		printf("Container não é do tipo refrigiração \n");
	}
}

float refrigirationEnergy(){
	printf("Considerando que a viagem é de 1h e a temp exterior são 20ºC \n");
	int tempViagem = 3600;      // segundos
	int tempExt = 20;			// ºC
	int tempMaxContainer = 7;	// ºC
	float rt = 0.00313          // K/W
	float fluxoCalor = 0;		// W
	float refrigEnergy = 0;		// J
	
	
	fluxoCalor = ((tempExt - tempMaxContainer) / rt);
	
	refrigEnergy = (fluxoCalor * tempViagem);
	
	return refrigEnergy
}	
