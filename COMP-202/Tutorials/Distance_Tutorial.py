#Samuel Kinyua
#Jan 20th 2023
# A program that calculates distance between two points

#Asking for distance from the user
x1 = float( input( "Enter x1: "))
y1 = float( input( "Enter y1: "))
x2 = float( input( "Enter x2: "))
y2 = float( input( "Enter y2: "))

#Show coordinates
print("Given points:",(x1,y1),(x2,y2))

#Getting Euclidean distance
Euc_Distance = ((x1 - x2) **2 + (y1 - y2) **2) **0.5

print("Euclidean distance:",Euc_Distance)

#Getting Manhattan taxicab distance
Tax_Distance = abs(x1 - x2) + abs(y1 - y2)

print("Taxicab distance:",Tax_Distance)
