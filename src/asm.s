.section .data                  # our global variables
    .global ptr1
    .global capacity_max
    .global N
    
    .equ DATA_SIZE, 56 			# total size
	.equ ID_OFFSET, 0 			# id is at the beginning of the structure
	.equ ID_REFRIG_OFFSET, 48 	# the id refrigiration starts at byte 48


.section .text                  # our Assembly Functions
    #.global freeSlots
    #.global verifyContainer
    .global returnId
    .global containerRefrig

# ----------------------------- US 314

#freeSlots:

#pushq %rbp
#movq %rsp,%rbp
#pushq %rbx 						#salvaguarda do registo rbx
#movq $0,%rbx 					#limpa o registo rbx
#movq $0,%rcx 					#limpa o registo rcx
#movq $0,%rsi 					#limpa o registo rsi
#movq $0,%rax 					#limpa o registo rax
#movq $0,%rdi 					#limpa o registo rdi
#movq $0,%r8 					#limpa o registo r8
#movq $0,%rdx 					#limpa o registo rdx

#movq ptrMatrix(%rip),%r8         #move valores do pointer ptrMatrix em memoria para o registo r8
#movl sizeX(%rip),%ecx            #move o valor de sizeX em memoria para o registo ecx
#movl sizeY(%rip),%esi            #move o valor de sizeY em memoria para o registo esi
#movl sizeZ(%rip),%ebx            #move o valor de sizeZ em memoria para o registo ebx

#loop_first_matrix:															#percorre a matrix num loop
#cmpl $0,%ecx 																#verifica se já foram percorridos todos os indices
#je end2                                           							#termina
#jmp loop_second_matrix

#incrementX:                 #incrementa a posição do indice x da matriz
#movl sizeY(%rip),%esi		#volta a mover o sizeY para voltar a percorrer a segunda dimensão da matriz
#subl $1,%ecx 				#diminui aos indices que faltam percorrer
#jmp loop_first_matrix
#
##loop_second_matrix: 		# percorre a segunda dimensão da matriz
##cmpl $0,%esi 			#verifica se todos os elementos já foram percorridos
##je incrementX 			# se já vai incrementar o valor de X para ir para a proximo valor da primeira dimensão da matriz
##jmp loop_third_matrix
#
#increment_Y:				#incrementa Y
#movl sizeZ(%rip),%ebx 		#volta a mover o sizeZ para voltar a percorrer a terceira dimensão da matriz
#subl $1,%esi 				#reduz ao valores que ainda faltam percorrer para  a segunda dimensão da matriz
#jmp loop_second_matrix
#
#loop_third_matrix: 			#percorre a terceira dimensão da matriz
#cmpl $0,%ebx 				#verifica se todos os elementos já foram percorridos na terceira dimensão
#je increment_Y				#se já vai incrementar o valor de y para ir para a proximo valor da primeira dimensão da matriz
#
#movl (%r8),%edi 			#move o valor atual da matriz para o registo edi
#cmpl $0,%edi 				#verifica se valor em edi é igual ou diferente de 0
#jne occupiedSlot 			#se for diferente essa posição está ocupada
#addq $1,%rdx 				#se nao adiciona 1 ao registo rdx que guarda o numero de posições livres
#
#increment:
#addq $4,%r8 				#incrementa a posição da matriz para verificar o valor no próximo endereço.
#subl $1,%ebx 				#reduz ao valores que ainda faltam percorrer para a terceira dimensão da matriz
#jmp loop_third_matrix
#
#occupiedSlot:
#addl $1,%eax 				#se a posição continha um contentor então soma 1 ao registo eax que guarda o valor de posições ocupadas
#jmp increment
#
#end2:
#rclq $0x20, %rax 			#roda o registo rax em 32 bits (4 bytes) para a esquerda
#shrd $0x20,%rdx,%rax 		#shifts bits 32 bits (4 bytes) para a direita e copia os bits em rdx para o registo rax
#popq %rbx 					#retira o valor de rbx da stack
#
#movq %rbp,%rsp              #move os registos rbp para rsp
#popq %rbp
#ret
#
# ----------------------------- US 315

#verifyContainer:

#    pushq %rdi					# Valor a
#    pushq %rsi					# Valor b
#    pushq %rdx					# Valor c
#
#    movq %rdx,%r8          # Guardamos em r8 devido a uma posterior multiplicação
#    movq ptr1(%rip),%rbx   # Guardar o endereço inicial do pointer em %rbx
#
#    movq $6,%rax           # Valor de X = Y * Z que, neste caso é 3*2 = 6
#    mulq %rdi
#    movq %rax,%rcx         # Guardar o valor Total em %rcx
#
#    movq $2,%rax           # Valor de Y = Z que, neste caso é 2
#    mulq %rsi
#    addq %rax,%rcx
#
#    movq $1,%rax           # Valor de Z = Z - 1 que, neste caso é 2-1 = 1
#    mulq %r8
#    addq %rax,%rcx
#
#    movq $4,%rax           # Visto que temos que mover o apontador em bites
#    mulq %rcx
#    addq %rax,%rbx         # Posição final do apontador
#
#    cmpq $0,(%rbx)         # Verifica se o existe id nessa posição do apontador - %rbx
#    je containerVazio
#    movq $1,%rax           # Caso exista passa 1
#    jmp end

#containerVazio:
#    movq $0,%rax           # Caso não exista passa 0
#
#end:
#    popq %rdx
#    popq %rsi
#    popq %rdi
#    ret
#

# ----------------------------- US 410

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
    addq %rax,%rbx         # Posição final do apontador 
    movq (%rbx),%rax
    
    popq %rdx              # Fazemos aqui os pops devido a função anterior 
    popq %rsi
    popq %rdi
    
	jmp end
	

containerRefrig:

	# %rdi - Apontador para o início da struct
	# %rsi - Id do container selecionado pelo user
	# %rdx - N = 22 
	# Cada Struct de container tem 56 bytes
	
	movq $1,%rcx						# Contador 
	
loop:
	cmpq $0,%rdx						# Loop que percorre o array de Structs
	je end
	
	cmpq %rsi,%rcx						# Compra o id recebido com o Id da Struct no momento
    je verificar_refrigiration_id

    addq $DATA_SIZE,%rdi				# O apontador do array passa a assumir o valor da próxima Struct
	
	incq %rcx
	decq %rdx
jmp loop
	
verificar_refrigiration_id:	
	
	movq ID_REFRIG_OFFSET(%rdi),%rax	# Move o apontador para o registo onde se encontra o id de refrigiração
	jmp end
	
end:
    ret
