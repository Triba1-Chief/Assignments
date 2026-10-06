# Data segment
.data
N: .word 10
MAX: .word 999
A: .space 40

.text
.globl main
main:
	lw $a0,N
	lw $a1,MAX
	la $a2,A
	jal fill
	li $v0,10
	syscall

fill:
	addi $sp,$sp,-12
	sw $s0,0($sp)
	sw $s1,4($sp)
	sw $s2,8($sp)

	lw $s0,12($sp)
	lw $s1,16($sp)
	lw $s2,20($sp)

	li $t0,0
loop:
	beq $t0,$s0,end
	sw $s1,($s2)
	addi $s2,$s2,4
	addi $t0,$t0,1
	j loop

end:
	lw $s0,0($sp)
	lw $s1,4($sp)
	lw $s2,8($sp)
	addi $sp,$sp,12
	jr $ra
