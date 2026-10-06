#Samuel Kinyua
# Student ID removed
#Jan 20th 2023
#This is a program that calculates the amount of carbon footprint of emails.

#Constants
SPAM_EMAIL = 0.03
SHORT_EMAIL_PHONE = 0.2
SHORT_EMAIL_LAPTOP = 0.3
LONG_EMAIL_ONE_PERSON = 17
LONG_EMAIL_MANY_PEOPLE = 26

#Start:
print("Welcome to the email CO2e calculator!\n"
      "Lets start by asking you a few questions about the email:")

#Processing:
#a) Spam email
spam = int(input("\t Is this email a spam "                  "(type 0 for yes and 1 for no)?"))
if spam == 0:
    print("The CO2e emissions for this email is", SPAM_EMAIL, "g")

#b) Whether short emails are from a phone or laptop
if spam == 1:
    short_email = int(input("\t Is this email a short one "                            "(type 0 for yes and 1 for no)?"))
    if short_email == 0:
        phone_email = int(input("\t Is this email being sent from a phone "                                "(type 0 for yes and 1 for no)?"))
        if phone_email == 0:
            print("The CO2e emissions for this email is"                   , SHORT_EMAIL_PHONE, "g")
        else:
            print("The CO2e emissions for this email is"                   , SHORT_EMAIL_LAPTOP, "g")

#c) Whether long emails are for one receipient or many receipients
    if short_email == 1:
        long_email = int(input("\t Is this email being sent for"                                "only one recipient (type 0 for yes and 1 for no)?"))
        if long_email == 0:
            print("The CO2e emissions for this email is"                   , LONG_EMAIL_ONE_PERSON, "g")
        else:
            print("The CO2e emissions for this email is"                   , LONG_EMAIL_MANY_PEOPLE, "g")
