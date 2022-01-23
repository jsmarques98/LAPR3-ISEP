#ifndef ASM_H
#define ASM_H

#include <stdio.h>
#include <string.h>
#include <stdbool.h>
#include <stdlib.h>

typedef struct{
	int id;
	int x;
	int y;
	int z;
	int iso_code;
	double tare;
	double gross;
	double payload;
	int refrigerated;
} Container;

#define N 22

// Matrix Dimensions
#define A 3
#define B 3
#define C 2

// This is our max. number of characters to be read from CSV file
#define MAXCHAR 256

// Our Matrix with dimensions A,B,C
int matrix[A][B][C];

// Pointer that points to a position that contains the address of the position [0][0][0]
int *ptr1 = &matrix[0][0][0];

// Max. Capacity of Containers
int capacity_max = A * B * C;

// Our file CSV path
#define PATH "../data/containers.csv"
#define PATH_TABLE "../data/container_table.csv"
#define PATH_ENG "energy.csv"

// C functions
void parse_csv();
//void printMatrix();
//void freeSlots();
//void verifyPosition();
int func_energy(float energia, float t1, float t2);
void amountEnergy(Container* ptr, int size);
Container* table_read(const char *path, int size_lines);
void check_energy();

// Assembly Functions
//long long int freeSlots(void);
//int verifyContainer(int a, int b, int c);
int returnId(int a, int b, int c);
int containerRefrig(Container *ptr1, int id, int size);

#endif