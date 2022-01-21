#ifndef ASM_H
#define ASM_H

#include <stdio.h>
#include <string.h>
#include <stdbool.h>


typedef struct {
    int id;
    int x;
    int y;
    int z;
    int iso_code;
    double tare;
    double gross;
    double payload;
    int refrigerated_id;
} container;

// Matrix Dimensions
#define A 3
#define B 3
#define C 2

// This is our max. number of characters to be read from CSV file
#define MAXCHAR 256

// Our Matrix with dimensions A,B,C
int matrix[A][B][C];

// Max. Capacity of Containers
int capacity_max = A * B * C;

// Our file CSV path
#define PATH "../data/containers.csv"
#define PATH_TABLE "../data/container_table.csv"
#define PATH_ENG "energy.csv"

// C functions
void amountEnergy();
void refrigirationEnergy();

// Assembly Functions
int returnId(int a, int b, int c);
int containerRefrig(container* array, int id);

#endif