def harmonic_sum(a):
    """
    Returns the sum of the first n terms of the harmonic series
        
        Parameters:
            a: a number (int or float)
    
        Returns:
            Sum (number): The summation of the series
        
        Examples:
        >>> harmonic_sum(2)
        1.5
        >>> harmonic_sum(5)
        2.283333333333333
        >>> harmonic_sum(7)
        6.25952380952381
    """
    
    h_sum = 0
    
    while a>0:
        h_sum = h_sum + 1 / a
        a = a - 1
    return h_sum
    
print(harmonic_sum(100))  # 5.187377517639621
print(harmonic_sum(1000))  # 7.485470860550343
