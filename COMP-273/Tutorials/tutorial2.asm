# MIPS program to initialize an array with a for loop
.data
N: .word 10
MAX: .word 999
A: .space 40

.text
.globl main
main:
	lw $t0,N
	lw $t1,MAX
	li $t2,0

init_loop:
	bge $t2,$t0,exit_loop
	la $t3,A
	mul $t4,$t2,4
	add $t3,$t3,$t4
	sw $t1,0($t3)
	addi $t2,$t2,1
	j init_loop

exit_loop:
	li $v0,10
	syscall
