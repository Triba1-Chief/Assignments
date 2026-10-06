def to_letter_grade(grade):
    cutA = 90
    cutB = 80
    cutC = 70
    cutD = 60

    if grade >= cutA:
        letter = "A"
    elif grade >= cutB:
        letter = "B"
    elif grade >= cutC:
        letter = "C"
    elif grade >= cutD:
        letter = "D"
    else:
        letter = "F"
        
    return letter

print(to_letter_grade(90)) # A
print(to_letter_grade(82))  # B
print(to_letter_grade(59))  # F
