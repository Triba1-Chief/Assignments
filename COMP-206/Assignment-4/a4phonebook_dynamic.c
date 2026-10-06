#include <stdio.h>
#include <stdlib.h>
#include <string.h>

struct PHONE_RECORD {
	char name[50];
	char birthdate[12];
	char phone[15];
};

struct PHONE_RECORD *phonebook;
int phonebook_size;
int phonebook_length = 0;

int sizeArray(int input) {
        phonebook = malloc(input * sizeof(struct PHONE_RECORD));

        if (phonebook == NULL) {
                return 1;
        }

        phonebook_size = input;
        return 0;
}

int addRecord(char name[], char birth[], char phone[]) {
	if (phonebook_length < phonebook_size) {
		strcpy(phonebook[phonebook_length].name, name);
		strcpy(phonebook[phonebook_length].birthdate, birth);
		strcpy(phonebook[phonebook_length].phone, phone);

		phonebook_length++;
		return 0;
	}
	else {
		return 1;
	}
}

int listRecords(void) {
	if (phonebook_length == 0) {
                return 1;
        }

	printf("---- NAME ---- ---- BIRTH DATE ---- ---- PHONE ----\n");

	for (int i = 0; i < phonebook_length; i++) {
		printf("%-14s ",phonebook[i].name);
		printf("%-20s ",phonebook[i].birthdate);
		printf("%-10s\n",phonebook[i].phone);
	}

	return 0;
}

int findRecord(char name[]) {
	for (int i = 0; i < phonebook_length; i++) {
		if (strcmp(phonebook[i].name,name) == 0) {
			printf("---- NAME ---- ---- BIRTH DATE ---- ---- PHONE ----\n");
                	printf("%-14s ",phonebook[i].name);
                	printf("%-20s ",phonebook[i].birthdate);
                	printf("%-10s\n",phonebook[i].phone);
			return i;
        	}
	}

	printf("Phone record not found\n");
	return -1;
}

int loadCSV(char *filename) {
	phonebook_length = 0;

	FILE *csvFile = fopen(filename,"rt");

	if (csvFile == NULL) {
		return 1;
	}

	char csvData[100000];
	int tempCounter = 0;
	int charCounter = 0;
	char name[50];
        char birth[12];
        char phone[15];

	fgets(csvData,99999,csvFile);
	if (fgets(csvData,99999,csvFile) == NULL) {
		fclose(csvFile);
		return 1;
	}

	while (!feof(csvFile)) {
		charCounter = 0;

		for (int i = 0; csvData[charCounter] != '\0' && i < 50 && csvData[charCounter] != ','; charCounter++, i++) {
                	name[i] = csvData[charCounter];
			tempCounter = i;
        	}
		name[tempCounter + 1] = '\0';
                charCounter++;

        	for (int i = 0; csvData[charCounter] != '\0' && i < 12 && csvData[charCounter]!= ','; charCounter++, i++) {
                	birth[i] = csvData[charCounter];
			tempCounter = i;
        	}
                birth[tempCounter + 1] = '\0';
                charCounter++;

        	for (int i = 0; csvData[charCounter] != '\0' && i < 15 && csvData[charCounter]!= ','; charCounter++, i++) {
                	phone[i] = csvData[charCounter];
			tempCounter = i;
        	}
                phone[tempCounter + 1] = '\0';

		addRecord(name, birth, phone);
		fgets(csvData,99999,csvFile);
	}
	fclose(csvFile);
}

int saveCSV(char *filename) {
	FILE *csvFile = fopen(filename,"wt");

	if (csvFile == NULL) {
		return 1;
	}

	if (phonebook_length != 0) {
		fprintf(csvFile, "name,birthdate,phone\n");

		for (int i = 0; i < phonebook_length; i++) {
			fprintf(csvFile, "%s,%s,%s\n", phonebook[i].name, phonebook[i].birthdate, phonebook[i].phone);
                }

		fclose(csvFile);
                return 0;
        }
        else {
		fclose(csvFile);
                return 2;
        }
}
