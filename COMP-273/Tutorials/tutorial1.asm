# Hello World in MIPS Assembly
.data
msg: .asciiz "Hello World!\n"

.text
main:
	li $t0,10
loop:
	la $a0,msg
	li $v0,4
	syscall
	subi $t0,$t0,1
	bnez $t0,loop

	li $v0,10
	syscall
