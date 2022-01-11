file: main.o asm.o
	gcc main.o asm.o -o file

main.o: main.c
	gcc -g -Wall -c main.c -o main.o

asm.o: asm.s
	gcc -g -Wall -c asm.s -o asm.o

run: file
	./file

clean:
	rm *.o file
