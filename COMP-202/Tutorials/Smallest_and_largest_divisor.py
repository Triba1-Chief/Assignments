# Samuel Kinyua
# 7th Feb 2023

def smallest_divisor(num):
    if num < 2:
        return False
    else:
        for i in range(2, num):
            if num % i == 0:
                print(i)
                return i
    print(num)
    return num 

def largest_divisor(num):
    if num < 2:
        return False
    else:
        for i in range(2, num):
            if num % i == 0:
                print(round(num / i))
                return round(num / i)
    print(num)     
    return num

smallest_divisor(5)
smallest_divisor(15)
largest_divisor(5)
largest_divisor(15)
