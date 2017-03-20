#include "utility.h"
#include "filer.h"

void filer::makefile(int n, int range, string file_name) {
	ofstream myfile(file_name);

	//srand((unsigned)time(NULL));
	if (myfile.is_open()){
		
		for (int i = 0; i < n; i++) {
			int tmp = next_number(range);
			myfile << tmp << "\n";
		}
		myfile.close();
	}
	else cout << "Unable to open file";
}

int filer::next_number(int range) {
	return rand() % (range+1);
}

