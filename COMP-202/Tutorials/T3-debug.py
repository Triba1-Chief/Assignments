def find_max(a, b, c):
    if a > b and a > c:
         Max = a
    elif a == b and a > c:
        Max = a
    elif a > b and a == c:
        Max = a
    elif b > a and b > c:
         Max = b
    elif a == b and a > c:
        Max = a
    elif a > b and a == c:
        Max = a
    elif a < b and a < c:
        Max = c
    else:
         print("They are equal")
    return Max
 
print(find_max(-5, 10, 50))
print(find_max(2, 100, 100))
print(find_max(10, 10, 3))
