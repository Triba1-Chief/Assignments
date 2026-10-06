#Samuel Kinyua
#Jan 20th 2023
#A program to compute area of a triangle

#Getting coordinates from user
x1 = float(input("Enter x1: "))
y1 = float(input("Enter y1: "))
x2 = float(input("Enter x2: "))
y2 = float(input("Enter y2: "))
x3 = float(input("Enter x3: "))
y3 = float(input("Enter y3: "))

#Calculating distance between points
a = ((x1 - x2) **2 + (y1 - y2) **2) **0.5
b = ((x1 - x3) **2 + (y1 - y3) **2) **0.5
c = ((x2 - x3) **2 + (y2 - y3) **2) **0.5

#Calculating perimeter(p) and semi-perimetre(s)
perimetre = a + b + c
p = round( perimetre,2)
s = perimetre / 2

#Calculating area
area = round(((s*(s - a)*(s - b)*(s - c))**0.5),2)

print("Perimeter is",p,"and area is",area)
