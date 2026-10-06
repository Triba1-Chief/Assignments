#include <stdio.h>
#include <stdbool.h>

int main (void) {
	int first;
	int second;
	int third;
	int temp;
	bool Boolean = false;
	char input[100];
	int counter = 0;

	while (counter != 3) {
		puts("Please input three numbers: ");
		fgets(input, sizeof(input), stdin);
		counter = sscanf(input, "%d %d %d %d", &first, &second, &third, &temp);
	}

	if ((first < second) && (second < third)) {
		Boolean = true;
	}

	if ((first != 0) && (second % first) == 0 && (third % first) == 0) {
		if (Boolean) {
			puts("Divisible and Increasing");
			return 0;
		}
		else {
			puts("Divisible and Not increasing");
			return 2;
		}
	}
	else {
		if (Boolean) {
			puts("Not Divisible and Increasing");
			return 1;
		}
		else {
			puts("Not Divisible and Not Increasing");
			return 3;
		}
	}
}
