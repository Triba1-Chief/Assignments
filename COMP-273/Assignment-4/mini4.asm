# Program that asks the user for their name and displays it.

.data
prompt1: .asciiz "First name:\n"
prompt2: .asciiz "Last name:\n"
prompt3: .asciiz "You entered:"
buffer_firstn: .space 50
buffer_secondn: .space 50

.text
.globl __main
__main:
	la $a0,prompt1
	jal puts

	la $a0,buffer_firstn
	li $a1,50
	jal gets
	move $s0,$v0

	la $a0,prompt2
	jal puts

	la $a0,buffer_secondn
	li $a1,50
	jal gets
	move $s1,$v0

	la $a0,prompt3
	jal puts
	la $a0,buffer_secondn
	jal puts
	li $a0,','
	jal PUTCHAR
	li $a0,' '
	jal PUTCHAR
	la $a0,buffer_firstn
	jal puts
	li $a0,'.'
	jal PUTCHAR

	li $v0,10
	syscall

GETCHAR:
	lui $a3,0xffff
CkReady:
	lw $t1,0($a3)
	andi $t1,$t1,0x1
	beqz $t1,CkReady
	lw $v0,4($a3)
	jr $ra

PUTCHAR:
	lui $a3,0xffff
XReady:
	lw $t1,8($a3)
	andi $t1,$t1,0x1
	beqz $t1,XReady

	beq $a0,',',Comma
	beq $a0,' ',Space
	beq $a0,'.',FullStop

	sw $a0,12($a3)
	jr $ra

Comma:
	li $a0,','
	sw $a0,12($a3)
	jr $ra

Space:
	li $a0,' '
	sw $a0,12($a3)
	jr $ra

FullStop:
	li $a0,'.'
	sw $a0,12($a3)
	jr $ra

gets:
	addi $sp,$sp,-16
	sw $a0,12($sp)
	sw $a1,8($sp)
	sw $s0,4($sp)
	sw $ra,0($sp)

	li $t0,0
	move $s0,$a0

loop:
	beq $t0,$a1,exit
	jal GETCHAR
	sb $v0,($s0)
	addi $s0,$s0,1
	addi $t0,$t0,1
	bne $v0,'\n',loop

	beq $t0,$a1,exit
	li $t0,'\0'
	add $s0,$s0,-1
	sb $t0,0($s0)

exit:
	move $v0,$t0
	lw $ra,0($sp)
	lw $s0,4($sp)
	lw $a1,8($sp)
	lw $a0,12($sp)
	addi $sp,$sp,16
	jr $ra

puts:
	addi $sp,$sp,-12
	sw $a0,8($sp)
	sw $s0,4($sp)
	sw $ra,0($sp)

	li $t0,0
	move $s0,$a0

loop2:
	lb $a0,0($s0)
	beq $a0,'\0',exit2
	jal PUTCHAR
	addi $s0,$s0,1
	addi $t0,$t0,1
	j loop2

exit2:
	move $v0,$t0
	lw $ra,0($sp)
	lw $s0,4($sp)
	lw $a0,8($sp)
	addi $sp,$sp,12
	jr $ra
