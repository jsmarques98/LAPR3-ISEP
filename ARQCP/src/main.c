#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "asm.h"


#define ID_MAXLENGTH 6  //comprimento máximo para o id 


char matrix[3][3][2][ID_MAXLENGTH];

int *ptrMatrix = &matrix[0][0][0][0], sizeX = 3, sizeY = 3, sizeZ = 2;; 

int main(void) {
	
    int option = 0;
    
    do {
        printf("----------------------- Menu --------------------\n\n");
        printf(" 1 > Print Matrix with loades positions and empty \n");
        printf(" 2 > Check Free/Occupied Slots                  \n");
        printf(" 3 > Check if a position on the boat exists     \n\n");
        printf(" 0 > Exit Application                           \n");
        printf("-----------------------------------------------\n");

        printf("Choose an option: ");
        scanf(" %d", &option);
        switch (option)
        {
        case 0: 
            break;
        case 1:
            fill_matrix();
            break;
        case 2:
            free_ocupied_slots();
            break;
        case 3:
            verifyPosition();
            break;
        default:
        printf("Your choice is invalid.\n");
            break;
        }
        
    } while (option != 0);
    
    return 0;
}


int fill_matrix(void){

    char zero[] = "0";
    char * zero_ptr = zero;
    int i = 0, j = 0, k = 0;
    for(i = 0; i < 3; i++){
        for(j = 0; j < 3; j++){                    //vai preencher a matrix com zeros
            for(k = 0; k < 2; k++){
                char * loc = &(matrix[i][j][k][0]);
                strcpy(loc, zero_ptr);
            }
        }
    }

    FILE * fileP = fopen("file.txt", "r"); //cria um aontador para o ficheiro

    if(fileP == NULL) {
        perror("file not found");           //verificar se o ficheiro existe
    }

    char id[ID_MAXLENGTH];
    char * string = id;
    int x,y,z;

    while(fscanf(fileP,"%d-%d-%d:%s\n",&x,&y,&z,string) != EOF) {       //ciclo while que vai correr ate o final do ficheiro(EOF)
        char * loc = &(matrix[x][y][z][0]);             //passa o endereço do que vai ser ocupado pelo id na matriz nos determinados xyz para um apontador
        strcpy(loc,string);             // copia o valor da string que tem o id para o apontador da posicao da matriz nos determinados xyz
    }
    
    
    int v = 0, b = 0, c = 0;
    for(i = 0; v < 3; i++){
        for(j = 0; b < 3; j++){
            for(k = 0; c < 2; k++){
                printf("%d,%d,%d:%s\n",v,b,c,(matrix[v][b][c]));
            }
        }
    }
    
    fclose(fileP); //close file

    return 0;
}

void verifyPosition(){
    int a,b,c;
    printf("\nQue posição do array pertende saber se exite containers (Linha, Coluna, Perfindidade):\n");
    scanf("%d%d%d",&a,&b,&c);
    int flag = verifyContainer(a,b,c);
    printf("Valor da flag: %d\n",flag);
    if(flag == 0){
        printf("Na posição [%d][%d][%d] não existem containers\n",a,b,c);
    }
    if(flag == 1){
        printf("Existe um container na posição [%d][%d][%d]\n",a,b,c);
    }
}

void free_ocupied_slots(){
	
	long long int result=freeSlots();

    printf("Result:%lld\n",result);
	
	}
