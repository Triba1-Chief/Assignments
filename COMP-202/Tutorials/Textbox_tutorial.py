#Samuel Kinyua
#Jan 20th 2023
# A program that creates a textbox

a = input("Enter message: ")
num_spaces = int(input("Enter number of spaces: "))
b = (num_spaces * 2) + len(a)
print("+" + ("-" * b) + "+")
print("|" + (" " * b) + "|") 
print("|" + (" " * num_spaces) + a + (" "*num_spaces) + "|")
print("|" + (" " * b) + "|")
print("+" + ("-" * b) + "+")
