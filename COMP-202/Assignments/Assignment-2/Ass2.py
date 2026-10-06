#Samuel Kinyua
# Student ID removed
#Feb 10th 2023
#This program gets information about a vessel (its color, length, speed, etc)
#and determines what point the hydrodynamic lift will be in charge of keeping
#the vessel above the water.

G = 9.81
METER_PER_FEET = 0.3
KNOT_PER_METER_SEC = 0.51

def get_color():
    RED = "1"
    BLUE = "2"
    WHITE = "3"
    print("Select which number corresponds to the color of your vessel\n"
          "1-Red\n2-Blue \n3-White ")
    color = input("What is the color of your vessel? ")
    while not is_valid_text(color, RED, WHITE) or not color in (RED, BLUE, WHITE):
        print("ERROR: The value you selected does not correspond to a color")
        color = input("Please choose another number between 1 and 3: ")
    return color

def ship_or_boat():
    MIN_SHIP_WEIGHT = 500
    vessel_weight = float(input("Please enter the weight of your vessel in Tons: "))
    if vessel_weight < MIN_SHIP_WEIGHT:
        return "Boat"
    else:
        return "Ship"

def feet_to_meter(feet):
    return round(feet * METER_PER_FEET, 2)

def knot_to_meter_sec(knot):
    return round(knot * KNOT_PER_METER_SEC, 2)

def is_positive(number):
    return number > 0

def compute_froude(feet, knot):
    length = feet_to_meter(feet)
    velocity_ms = knot_to_meter_sec(knot)
    return round(velocity_ms / (length * G) ** 0.5, 2)

def display_hydro_lift(feet, knot):
    froude_number = compute_froude(feet, knot)
    if froude_number < 0.3:
        print("Negligible hydrodynamic lift compared to Archimedes thrust")
    elif froude_number < 0.7:
        print("Negative hydrodynamic lift")
    else:
        print("Hydrodynamic lift represents 50% of the displacement")

def is_valid_text(text, string_min, string_max):
    for letter in text:
        validity = letter >= string_min and letter <= string_max
    return validity

def vessel_program():
    print("*" * 4, "WELCOME TO THE SHIP SCIENCE PROGRAM!", "*" * 4)
    print("You selected color number:",get_color())
    if ship_or_boat() == "Ship":
        feet = float(input("What is the length of the Ship in feet? "))
        if is_positive(feet):
            knot = float(input("What is the speed of the Ship in knot? : "))
            display_hydro_lift(feet, knot)
        else:
            print("It is impossible to compute Froude number since the length is less than or equal to 0")
    else:
        feet = float(input("What is the length of the Boat in feet? "))
        if is_positive(feet):
            knot = float(input("What is the speed of the Boat in knot? : "))
            display_hydro_lift(feet, knot)
        else:
            print("It is impossible to compute Froude number since the length is less than or equal to 0")
    print("*" * 4, "END OF THE PROGRAM. GOODBYE!", "*" * 4)
