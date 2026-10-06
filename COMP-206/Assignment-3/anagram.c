#include <stdio.h>
#include <string.h>

int main(int argc, char *argv[]) {
	char *f_word = argv[1];
	char *s_word = argv[2];

	if (argc != 3 || strlen(f_word) != strlen(s_word)) {
		puts("Not an anagram");
		return 1;
	}

	char copy_of_first_word[sizeof(f_word)];
	strcpy(copy_of_first_word, f_word);
	int counter = strlen(copy_of_first_word);

	for (int i = 0; i < strlen(s_word); i++) {
		int j = 0;
		while (j < strlen(f_word)) {
			if (s_word[i] != copy_of_first_word[j]) {
				j++;
			}
			else {
				copy_of_first_word[j] = 0;
				counter--;
				break;
			}
		}
	}

	if (counter == 0) {
		puts("Anagram");
		return 0;
	}
	else {
		puts("Not Anagram");
		return 1;
	}
}
