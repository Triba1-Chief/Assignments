# Program that asks the user for a number and displays the factorial.

.data
prompt1: .asciiz "Please input an integer value greater than or equal to 0:"
prompt2: .asciiz "The value you entered is less than zero. This program only works with values greater than or equal to zero."
prompt3: .asciiz "Your input:"
prompt4: .asciiz "The factorial is:"
prompt5: .asciiz "Would you like to do this again(Y/N): (Enter the character Y to do it again. All other characters will terminate the program."

.text
.globl __main
__main:
	la $a0,prompt1
	li $v0,4
	syscall

	li $v0,5
	syscall
	move $s0,$v0
	bltz $s0,terminate1

	jal factorial
	move $s1,$v0
	j output

terminate1:
	la $a0,prompt2
	li $v0,4
	syscall
	li $v0,10
	syscall

output:
	la $a0,prompt3
	li $v0,4
	syscall

	move $a0,$s1
	li $v0,1
	syscall

	li $a0,10
	li $v0,11
	syscall

	la $a0,prompt4
	li $v0,4
	syscall

	move $a0,$s0
	li $v0,1
	syscall

	li $a0,10
	li $v0,11
	syscall

	la $a0,prompt5
	li $v0,4
	syscall

	li $a0,10
	li $v0,11
	syscall

	li $v0,12
	syscall
	move $t0,$v0

	li $a0,10
	li $v0,11
	syscall

	beq $t0,'Y',__main

	li $v0,10
	syscall

factorial:
	addi $sp,$sp,-12
	sw $a0,8($sp)
	sw $s0,4($sp)
	sw $ra,0($sp)

	move $s0,$a0
	beqz $s0,base_case
	sub $a0,$s0,1
	jal factorial

	mul $v0,$s0,$v0
	j f_exit

base_case:
	li $v0,1

f_exit:
	lw $ra,0($sp)
	lw $s0,4($sp)
	lw $a0,8($sp)
	addi $sp,$sp,12
	jr $ra
