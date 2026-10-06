# Data segment
.data
x_name: .byte 'B','o','b',0,0,0,0,0,0,0
x_age: .word 18

.text
.globl main
main:
	la $a0,x_name
	lw $a1,x_age
	jal print
	li $v0,10
	syscall

print:
	li $v0,4
	syscall
	li $v0,1
	move $a0,$a1
	syscall
	jr $ra
