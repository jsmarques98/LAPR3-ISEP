.section .data                  # our global variables
    .equ DATA_SIZE, 56 			# total size
	.equ ID_OFFSET, 0 			# id is at the beginning of the structure
	.equ ID_REFRIG_OFFSET, 48 	# the id refrigiration starts at byte 48


.section .text                  # our Assembly Functions
    .global returnId
    .global containerRefrig


returnId:

	pushq %rdi             # Valor a
    pushq %rsi             # Valor b
    pushq %rdx             # Valor c

    movq %rdx,%r8          # Guardamos em r8 devido a uma posterior multiplicação
    movq ptr1(%rip),%rbx   # Guardar o endereço inicial do pointer em %rbx

    movq $6,%rax           # Valor de X = Y * Z que, neste caso é 3*2 = 6
    mulq %rdi
    movq %rax,%rcx         # Guardar o valor Total em %rcx

    movq $2,%rax           # Valor de Y = Z que, neste caso é 2
    mulq %rsi
    addq %rax,%rcx

    movq $1,%rax           # Valor de Z = Z - 1 que, neste caso é 2-1 = 1
    mulq %r8
    addq %rax,%rcx

    movq $4,%rax           # Visto que temos que mover o apontador em bites
    mulq %rcx
    addq %rbx,%rax         # Posição final do apontador

    popq %rdx              # Fazemos aqui os pops devido a função anterior
    popq %rsi
    popq %rdi

	jmp end


containerRefrig:

	# %rdi - Apontador para o início da struct
	# %rsi - Id do container selecionado pelo user
	# Cada Struct de container tem 56 bytes

loop:
	cmpl $0,counter
	je end

	cmpq %rsi,(%rdi)
	je verificar_refrigiration_id
	addq DATA_SIZE(%rip),%rdi

	decl counter
jmp loop

verificar_refrigiration_id:
	cmpq $1,ID_REFRIG_OFFSET(%rdi)
	je refrigirado
	movl $1,%eax
	jmp end

refrigirado:
	movl $0,%eax


end:
    ret