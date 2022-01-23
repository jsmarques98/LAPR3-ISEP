project: main.o asm.o
	gcc main.o asm.o -o project
	
main.o: main.c
	gcc -g -Wall -c main.c -o main.o
	
asm.o: asm.s
	gcc -g -Wall -c asm.s -o asm.o
	
run: project
	./project

clean:
	rm *.o project
