# Samuel Kinyua
# Feb 1st 2023
# A program that lists a given number of prime numbers.

def is_prime(num):
    counter = 0
    match = 0
 
    while num > match:
        match = match + 1
        if num % match == 0:
            counter = counter + 1
            
    if counter == 2:
        return True
    else:
        return False

def first_n_primes(n):
    num = 1
    prime_counter = 0
    
    while prime_counter < n:
        if not is_prime(num):
            num = num + 1
        else:
            prime_counter = prime_counter + 1
            print(num,end=" ")
            num = num + 1
    print()

first_n_primes(2)
