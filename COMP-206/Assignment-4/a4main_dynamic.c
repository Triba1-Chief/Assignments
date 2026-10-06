#include "a4phonebook_dynamic.c"
#include <stdio.h>

int arrayLength(void) {
 	int arrayInput;

	printf("Size of the phonebook: ");
        scanf("%i", &arrayInput);
        getchar();

        int errorCode = sizeArray(arrayInput);
        if (errorCode != 0) {
                printf("Not enough space to store phonebook of this size! Program terminated.\n");
                return -1;
        }
}

int menu(void) {
	int menuInput;

	printf("Phonebook Menu: (1)Add, (2)Find, (3)List, (4)Quit > ");
	scanf("%i", &menuInput);
	getchar();

	return menuInput;
}

int main(void) {
	loadCSV("newphonebook.csv");

	int errorCode;
	int result = arrayLength();

	if (result == -1) {
		return -1;
	}

	result = menu();

	while (result != 4) {
		if (result == 1) {
                	char name[50];
                        char birth[12];
                        char phone[15];

                        printf("Name: ");
                        fgets(name,50, stdin);
                        name[strcspn(name, "\n")] = '\0';

                        printf("Birth date: ");
                        fgets(birth,12,stdin);
                        birth[strcspn(birth, "\n")] = '\0';

                        printf("Phone: ");
                        fgets(phone,15,stdin);
                        phone[strcspn(phone, "\n")] = '\0';

                        errorCode = addRecord(name,birth,phone);

                        if (errorCode == 1) {
                                printf("The phonebook is full\n");

                        }
                        else if (errorCode != 0) {
                                printf("Your data was unsuccessfully processed as it is may be too large to process.\n");
                        }
                }
		else if (result == 2) {
                        char input[50];

                        printf("Find name: ");
                        fgets(input, 50 ,stdin);
                        input[strcspn(input, "\n")] = '\0';

                        findRecord(input);
		}
		else if (result == 3) {
			errorCode = listRecords();

			if (errorCode != 0) {
				printf("The phonebook is empty\n");
			}
		}
		else {
			printf("Invalid menu selection\n");
		}

		result = menu();
	}

	saveCSV("newphonebook.csv");
	free(phonebook);
	printf("End of phonebook program\n");

	return 0;
}
