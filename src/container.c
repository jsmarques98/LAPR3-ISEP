#include "asm.h"



Container* table_read(const char *path, int size_lines)
{

	FILE *infile;
	char line[121];
	char **info = NULL;
	int len;
	int counter = 0;
	static Container containerArray[100];

	int forward;

	infile = fopen(path, "r");

	while (fgets(line, 120, infile))
	{
		// Allocate memory for pointer to line just added
		info = realloc(info, (counter + 1) * sizeof(char *));
		// And allocate memory for that line itself!
		len = strlen(line);
		info[counter] = calloc(sizeof(char), len + 1);
		// Copy the line just read into that memory
		strcpy(info[counter], line);

		counter++;
	}
	fclose(infile);

	int containerCounter = 0;
	for (forward = 1; forward <= counter - 1; forward++)
	{
		char *field = strtok(info[forward], ",");
		int id, payload, tare, gross, iso_code, refrigerated, x, y, z;

		int counter = 0;
		Container container;
		while (field != NULL)
		{

			if (counter == 0)
			{
				id = atoi(field);
			}
			if (counter == 1)
			{
				payload = atoi(field);
			}
			if (counter == 2)
			{
				tare = atoi(field);
			}
			if (counter == 3)
			{
				gross = atoi(field);
			}
			if (counter == 4)
			{
				iso_code = atoi(field);
			}
			if (counter == 5)
			{
				refrigerated = atoi(field);
			}
			if (counter == 6)
			{
				x = atoi(field);
			}
			if (counter == 7)
			{
				y = atoi(field);
			}
			if (counter == 8)
			{
				z = atoi(field);
			}
			field = strtok(NULL, ",");
			counter++;
		}
			container.id = id;
			container.payload = payload;
			container.tare = tare;
			container.gross = gross;
			container.iso_code = iso_code;
			container.refrigerated = refrigerated;
			container.x = x;
			container.y = y;
			container.z = z;
			
			containerArray[containerCounter] = container;
			containerCounter++;
			
	}

		for (int i = 0; i < containerCounter; i++) {
			printf("Container ID: %d \n", containerArray[i].id);
			printf("Payload: %lf \n", containerArray[i].payload);
			printf("Tare: %lf \n", containerArray[i].tare);
			printf("Gross: %lf \n", containerArray[i].gross);
			printf("ISO Code: %d \n", containerArray[i].iso_code);
			printf("Refrigeration: %d\n\n", containerArray[i].refrigerated);
			
		}
	
	return containerArray; 
}
