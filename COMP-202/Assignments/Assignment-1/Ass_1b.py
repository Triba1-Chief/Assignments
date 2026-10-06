#Samuel Kinyua
# Student ID removed
#Jan 20th 2023
#A program that calculates the number of long emails sent to many people and
#to one person based on a user input of CO2e emissions.

#Constants
    #Emissions
LONG_EMAIL_ONE_PERSON = 17
LONG_EMAIL_MANY_PEOPLE = 26

#Start
emissions = float(input("What is the amount of CO2e emissions (in grams)?"))

#Processing
num_long_emails_many = round(emissions // LONG_EMAIL_MANY_PEOPLE)
rem_long_emails_many = emissions % LONG_EMAIL_MANY_PEOPLE
num_long_emails_one = round(rem_long_emails_many // LONG_EMAIL_ONE_PERSON)
rem_CO2e_emissions = round(rem_long_emails_many % LONG_EMAIL_ONE_PERSON, 2)

#Output
if rem_long_emails_many == 0:
    print(emissions,"g of CO2e corresponds to:\n" 
         + str(num_long_emails_many) + " Long emails sent to many recipients")
elif rem_long_emails_many > 0:
    if num_long_emails_one < 1:
        print(emissions,"g of CO2e corresponds to:\n"
        + str(num_long_emails_many) + " Long emails sent to many recipients\n"
        + "with a remaining", rem_CO2e_emissions, "g left")
    else:
        print(emissions,"g of CO2e corresponds to:\n"
        + str(num_long_emails_many) + " Long emails sent to many recipients\nand "
        + str(num_long_emails_one) + " Long emails sent to one recipient\n"
        +"with a remaining", rem_CO2e_emissions, "g left")
