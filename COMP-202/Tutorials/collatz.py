def collatz(n):
    """
        (number) --> number
        
        Returns the number of steps it takes to reach 1
        by applying the collatz conjecture rules beginning from n
        
        >>> collatz(4)
        2
        >>> collatz(5)
        5
        >>> collatz(7)
        16
    """
    counter = 0
    
    while n > 1:
        if n % 2 == 0:
            n = n/2
            counter = counter + 1
        else:
            n = (n * 3) + 1
            counter = counter + 1
    return counter
        
print(collatz(6))  # 8
print(collatz(27))  # 111
