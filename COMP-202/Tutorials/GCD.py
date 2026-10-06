def gcd(x, y):
    """
        (Number,Number) --> Number
        
    Returns the GCD of two numbers
    
    >>> gcd(50, 100)
    50
    >>> gcd(1, 2)
    1
    >>> gcd(7,14)
    2
    """
    
    while x != y:
        if x > y:
            x = x - y
        else:
            y = y - x
    return x

#print(gcd(24, 36))  # 12
print(gcd(19, 15))  # 1
