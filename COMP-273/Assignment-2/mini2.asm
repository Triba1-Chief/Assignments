# Program that asks the user for input and displays the modified version
#	$a0 - captures the input.
#	$t0 - temporarily holds characters/integers
#	$t1 - temporarily holds characters/integers
#	$t2 - counter
#	$s3 - holds temporary information from buffer
#	$s0 - holds saved info about buffer
#	$s1-  holds saved info about buffer2

.data
prompt1: .asciiz "Input a string 30 characters or less: "
prompt2: .asciiz "No input. Run again."
prompt3: .asciiz "Input an integer greater than 0: "
prompt4: .asciiz "Wrong input. Run again."
prompt5: .asciiz "Shifted string = "
buffer: .space 31
buffer2: .space 33

.text
.globl __main
__main:
String_prompt:
	la $a0,prompt1
	li $v0,4
	syscall

	la $a0,buffer
	li $a1,30
	li $v0,8
	syscall

check1:
	lb $t0,buffer
	beq $t0,10,terminate1
	j Integer_prompt

terminate1:
	la $a0,prompt2
	li $v0,4
	syscall
	li $v0,10
	syscall

Integer_prompt:
	la $a0,prompt3
	li $v0,4
	syscall

	li $v0,5
	syscall
	move $t1,$v0

check2:
	blez $t1,terminate2
	j declarations

terminate2:
	la $a0,prompt4
	li $v0,4
	syscall
	li $v0,10
	syscall

declarations:
	la $s0,buffer
	la $s1,buffer2
	li $t0,91
	sb $t0,0($s1)
	addi $s1,$s1,1
	li $t2,0

skip_char:
	addi $s0,$s0,1
	addi $t2,$t2,1
	beq $t2,$t1,copy_char
	j skip_char

copy_char:
	lb $t0,0($s0)
	beq $t0,10,skip_newline
	sb $t0,0($s1)
	addi $s1,$s1,1

skip_newline:
	addi $s0,$s0,1
	bne $t0,10,copy_char

	li $t2,0
	la $s2,buffer

shift_char:
	lb $t0,0($s2)
	sb $t0,0($s1)
	addi $s2,$s2,1
	addi $s1,$s1,1
	addi $t2,$t2,1
	beq $t2,$t1,display_char
	j shift_char

display_char:
	li $t0,93
	sb $t0,0($s1)
	addi $s1,$s1,1
	li $t0,0
	sb $t0,0($s1)
	la $a0,prompt5
	li $v0,4
	syscall

	la $a0,buffer2
	li $v0,4
	syscall
	li $v0,10
	syscall
