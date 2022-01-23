#include <stdio.h>                                                                                                          
#include <stdlib.h>                                                                                                           

#include "asm.h"

// US 410   
                                                                                                                           
void amountEnergy(Container *ptr1, int size){                                                                                                  
    int a,b,c;                                                                                                              
    printf("Qual a posição do contentor pretendido (Linha, Coluna, Profundidade): \n");          
	scanf("%d%d%d",&a,&b,&c);                                                                                               
    int id = returnId(a,b,c); 
    if(id == 0){
		printf("Posição inválida\n");
	}	
	printf("Id: %d\n",id);
	
	int flag = containerRefrig(ptr1, id, size);
    if(flag == 1){
		int refrigEnergy = 14952076;
		printf("\nA energia necessária para refrigirar o contentor é: %d\n", refrigEnergy);
	}else{
		printf("\nContentor não é do tipo refrigiração \n");
	}
}
