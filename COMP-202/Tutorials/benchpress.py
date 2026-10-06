brain = 30  # brain mass in kg
fat = 30  # fat mass in kg
fat_decrease = 0.05
muscle = 20  # muscle mass in kg
muscle_increase = 0.1
days = 0  # counter to keep track of training days

# Write code below:
maximum_lift = 10 * muscle ** (2/3)
weight = brain + fat + muscle

while maximum_lift != weight and round(weight,4) > round(maximum_lift,4):
    muscle = muscle + muscle_increase
    fat = fat - fat_decrease
    weight = brain + fat + muscle
    days = days + 1
    maximum_lift = 10 * muscle ** (2/3)

print(days, "days of training will be needed.")
# Answer is 34 days
