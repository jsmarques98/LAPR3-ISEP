#include "asm.h"
#include "cargo.c"
#include "container.c"
#include "checkenergy.c"
#include "containerTemperature.c"

// Main Menu
int main(void) {
    int choice = 0;

    static Container containerArray[100];
    Container *ptr1 = containerArray;
    do {

        printf("----------------------- Menu ---------------------\n");
        printf(" 1 > Load CSV File                                \n");
        printf(" 2 > Print Matrix with loades positions and empty \n");
        printf(" 3 > Check Free/Occupied Slots                    \n");
        printf(" 4 > Check if a position on the boat exists       \n");
        printf(" 5 > Checks in a position, for a container        \n");
        printf(" 6 > Check if the container is refrigerated       \n");
        printf(" 7 > Check if the generated energy is enough      \n");
        printf("--------------------------------------------------\n");
        printf(" 0 > Exit Application                             \n");
        
        printf("Choose an option: ");
        scanf(" %d", &choice);

        switch (choice)
        {
        case 0: 
            break;
        case 1:
            parse_csv();
            break;
        case 2:
            //printMatrix();
            break;
        case 3:
            //freeSlots();
            break;
        case 4:
            //verifyPosition();
            break;
        case 5:
			// 409
            ptr1 = table_read(PATH_TABLE, N);
            break;
        case 6:
			// 410
            amountEnergy(ptr1, N); 
            break; 
        case 7:
			// 411  
			check_energy();
			break;
        default:
        printf("Your choice is invalid.\n");
            break;
        }
    } while (choice != 0);

    return 0;
}
