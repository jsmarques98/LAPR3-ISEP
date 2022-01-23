#include <stdio.h>

#include "asm.h"

void parse_csv()
{
    FILE *fp;
    char row[MAXCHAR];

    fp = fopen(PATH, "r");

    while (feof(fp) != true)
    {
        fgets(row, MAXCHAR, fp);

        // split the array of chars by index - no commas
        char *field = strtok(row, ",");
        int id, a, b, c;

        int counter = 0;
        while (field != NULL)
        {

            if (counter == 0)
            {
                id = atoi(field);
            }
            if (counter == 1)
            {
                a = atoi(field);
            }
            if (counter == 2)
            {
                b = atoi(field);
            }
            if (counter == 3)
            {
                c = atoi(field);
            }
            field = strtok(NULL, ",");
            counter++;
        }
        matrix[a][b][c] = id;
    }

    fclose(fp);
    printf("\nContainers were imported successfully.\n");
}

// C Function that prints all filled positions of matrix
//void printMatrix()
//{
//    for (int i = 0; i <= A; i++)
//    {
//        for (int j = 0; j < B; j++)
//        {
//            for (int k = 0; k < C; k++)
//            {
//                if (matrix[i][j][k] != 0)
//                {
//                    printf("%d %d %d", i, j, k);
//                    printf(" %d\n", matrix[i][j][k]);
//                }
//            }
//        }
//    }
//}
//
//// C Function that prints number of occupied and free slots
//void freeSlots() {
//    long res = free_slots();
//    int *res_ptr = (int *)&res;
//    printf("\n%d of %d are free.\n", *(res_ptr + 1), capacity_max);
//    printf("\n");
//    printf("%d of %d are occupied.\n", *(res_ptr), capacity_max);
//}
//
//// C Function that checks if given a position, verifies that exists or not a container
//void verifyPosition(){
//	int a,b,c;
//	printf("Que posição do array Pertende saber se exite containers (Linha, Coluna, Profundidade):\n");
//	scanf("%d%d%d",&a,&b,&c);
//	int flag = verifyContainer(a,b,c);
//	printf("Valor da flag: %d\n",flag);
//    if(flag == 0){
//		printf("Na posição [%d][%d][%d] não existem containers\n",a,b,c);
//	}
//	if(flag == 1){
//		printf("Existe um container na posição [%d][%d][%d]\n",a,b,c);
//	}
//}

                                                                                                     
