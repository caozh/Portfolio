#include "utility.h"
#include "filer.h"
#include "timer.h"
#include "quicksort.h"
#include "quicksort_col.h"

bool SortCheck(short int* myArray, int size) {
	int n = size - 1;
	for (int i = 0; i < n; i++) {
		if (myArray[i] > myArray[i + 1]) {
			//cout << i <<" "<<myArray[i] << "vs" << myArray[i + 1] << endl << "false" << endl;
			return false;
		}
	}
	return true;
}

void outFile(short int* myArray, int size_arr, string fileName) {
	string out_name = "Results_" + fileName;
	ofstream myfile_out1(out_name);

	if (myfile_out1.is_open()) {
		for (int i = 0; i < size_arr; i++) {
			myfile_out1 << myArray[i] << "\n";
		}
		myfile_out1.close();
	} else cout << "Unable to open file";
}

void insertion_sort(short int arr[], int length) {
	int j;
	short int temp;

	for (int i = 0; i < length; i++) {
		j = i;

		while (j > 0 && arr[j] < arr[j - 1]) {
			temp = arr[j];
			arr[j] = arr[j - 1];
			arr[j - 1] = temp;
			j--;
		}
	}
}

void insertion_sort_col_swap(short int* a, short int* b)
{
	short int t = *a;
	*a = *b;
	*b = t;
}

void insertion_sort_col(short int ** arr, int length, int k) {
	int j;
	short int temp;

	for (int i = 0; i < length; i++) {
		j = i;
		while (j > 0 && arr[j][k] < arr[j - 1][k]) {
			temp = arr[j][k];
			arr[j][k] = arr[j - 1][k];
			arr[j - 1][k] = temp;
			j--;
		}
	}
}

void insertion_sort_array(string fileName) {
	static short int myArray[100000];
	int size_arr = sizeof(myArray) / sizeof(myArray[0]);
	int i = 0;
	string line;
	ifstream myfile(fileName);
	if (myfile.is_open()) { //load the file
		for (int i = 0; i < size_arr; i++) {
			myfile >> myArray[i]; //load each element into array
		}
		
		//timer
		double duration;
		std::clock_t start;
		start = std::clock();

		//sort array
		insertion_sort(myArray, size_arr);

		//stop timer
		duration = ((double)std::clock() - start) / (double)CLOCKS_PER_SEC;
		std::cout << "Insertion sort: " << duration << 's' << endl;

		//sort test
		if (SortCheck(myArray, 100000)) {
			//cout <<" sorted" << endl; 
		}
		else {
			cout << " not sorted" << endl;
		};

		//out file
		string fileName_ = "insert_" + fileName;
		outFile(myArray, size_arr, fileName_); //output result array to a file

		myfile.close();
	}
	else cout << "Unable to open file";


}

void quick_sort(string fileName) {
	static short int myArray[100000];
	int size_arr = sizeof(myArray) / sizeof(myArray[0]);
	//cout << "Size: " << size_arr << endl;
	int i = 0;
	string line;
	ifstream myfile(fileName);
	if (myfile.is_open()) { //load the file
		for (int i = 0; i < size_arr; i++) {
			myfile >> myArray[i]; //load each element into array
		}

		//Timer timer;
		double duration; 
		std::clock_t start;
		start = std::clock();

		//sort array
		Quicksort quickSort; 
		quickSort.quickSort(myArray, 0, size_arr - 1); //sort the array

		//stop timer
		duration = ((double)std::clock() - start) / (double)CLOCKS_PER_SEC;
		std::cout << "Quick sort: " << duration << 's' <<endl;

		//sort test
		if (SortCheck(myArray, 100000)) {
			//cout <<" sorted" << endl; 
		}
		else {
			cout << " not sorted" << endl;
		};

		//output result array to a file
		string fileName_ = "quick_" + fileName;
		outFile(myArray, size_arr, fileName_); 

		myfile.close();
	} else cout << "Unable to open file";


}

void columnsort_quicksort_array(string fileName) {
	//load file
	static short int myArray[100000];
	int size_arr = sizeof(myArray) / sizeof(myArray[0]);
	int i = 0;
	string line;
	ifstream myfile(fileName);
	if (myfile.is_open()) { //load the file
		for (int i = 0; i < size_arr; i++) {
			myfile >> myArray[i]; //load each element into array
		}
		myfile.close();
	}
	else cout << "Unable to open file";

	//start timer
	double duration;
	std::clock_t start;
	start = std::clock();

	//column sort
	int r = 4000;
	int s = 25;
	//creating 2d array
	short int** col_arr = new short int*[r];
	for (int i = 0; i < r; ++i) {
		col_arr[i] = new short int[s];
	}
	int counter = 0;
	//load into 2d array
	for (int i = 0; i < s; i++) { //col
		for (int j = 0; j < r; j++) { //row
			if (counter >= size_arr) {
				cout << counter << "counter overflow\n\n";
				break;
			}
			col_arr[j][i] = myArray[counter];
			counter++;
		}// end j
	}//end i


	 //step 1
	 Quicksort_col quickSort_col;
	for (int i = 0; i < s; i++) { //25
		quickSort_col.quickSort(col_arr, 0, r-1, i); //sort the array
	}

	//step 2
	//creating 2d array
	short int** col_arr_tr = new short int*[r];
	for (int i = 0; i < r; ++i) {
		col_arr_tr[i] = new short int[s];
	}
	//tanspose
	int row_tr = -1;
	for (int i = 0; i < s; i++) {     //25
		int k = 0;
		row_tr++;
		for (int j = 0; j < r; j++) { //4000
			if (k == s) { //check to get newline
				k = 0;
				row_tr++;
			}
			col_arr_tr[row_tr][k] = col_arr[j][i];
			k++;
		}
	}

	//step 3
	for (int i = 0; i < s; i++) {
		quickSort_col.quickSort(col_arr_tr, 0, r-1, i); //sort the array
	}

	//step 4
	//creating 2d array
	short int** col_arr_tr2 = new short int*[r];
	for (int i = 0; i < r; ++i) {
		col_arr_tr2[i] = new short int[s];
	}
	//tanspose
	int col_tr = 0;
	int k = 0;
	for (int i = 0; i < r; i++) {     //25
		for (int j = 0; j < s; j++) { //4000
			if (k == r) { //check to get newline
				k = 0;
				col_tr++;
			}
			col_arr_tr2[k][col_tr] = col_arr_tr[i][j];
			k++;
		}
	}

	//step 5
	for (int i = 0; i < s; i++) {     //25
		quickSort_col.quickSort(col_arr_tr2, 0, r-1, i); //sort the array
	}

	//step 6
	//creating 2d array
	short int** col_arr_shift = new short int*[r];
	for (int i = 0; i < r; ++i) {
		col_arr_shift[i] = new short int[s + 1];
	}
	for (int i = 0; i < r / 2; i++) {
		col_arr_shift[i][0] = -32766;         //top half of first col
		col_arr_shift[r / 2 + i][25] = 32767;   //bot hafl of last col
	}
	//shifting middle cols
	for (int j = 0; j < s; j++) {     //25
		for (int i = 0; i < r; i++) {  //4000
			if (i >= r / 2) { //second half of col
				col_arr_shift[i - r / 2][j + 1] = col_arr_tr2[i][j];     //load to next top col
			}
			else {
				col_arr_shift[i + r / 2][j] = col_arr_tr2[i][j];     //bot half of first col
			}
		} //end 4000
	} //end 26

	  //step 7
	for (int i = 0; i < s + 1; i++) { //25
		quickSort_col.quickSort(col_arr_shift, 0, r-1, i); //sort the array
	}

	//step 8
	//creating 2d array
	short int** col_arr_shift2 = new short int*[r];
	for (int i = 0; i < r; ++i) {
		col_arr_shift2[i] = new short int[s];
	}
	for (int i = 0; i < r / 2; i++) {
		col_arr_shift2[i][0] = -32766;         //top half of first col
		col_arr_shift2[r / 2 + i][25] = 32767;   //bot hafl of last col
	}
	//shifting middle cols
	for (int j = 0; j < s; j++) {     //25
		for (int i = 0; i < r; i++) {  //4000
			if (i >= r / 2) { //second half of col
				col_arr_shift2[i][j] = col_arr_shift[i - r / 2][j + 1];     //load to next top col
			}
			else {
				col_arr_shift2[i][j] = col_arr_shift[i + r / 2][j];     //bot half of first col
			}
		} //end 4000
	} //end 26

	  //stop timer
	duration = ((double)std::clock() - start) / (double)CLOCKS_PER_SEC;
	std::cout << "Column sort(Quick sort): " << duration << 's' << endl;

	//sort test
	int myCounter = r;
	short int myArray_test[4000];
	for (int j = 0; j < s; j++) { //4000
		for (int i = 0; i < myCounter; i++) { //4000
			myArray_test[i] = col_arr_shift2[i][0];
		}
		if (SortCheck(myArray_test, myCounter)) {
			//cout <<" sorted" << endl; 
		}
		else { cout << " not sorted" << endl; };
	}

	//load result into 1d array
	int myArray_c = 0;
	for (int i = 0; i < s; i++) {
		for (int j = 0; j < r; j++) {
			myArray[myArray_c] = col_arr_shift2[j][i];
			myArray_c++;
		}
	}

	//out file
	string fileName_ = "col(Quick)_" + fileName ;
	outFile(myArray, size_arr, fileName_); //output result array to a file
}

void columnsort_insertion_array(string fileName) {
	//load file
	static short int myArray[100000];
	int size_arr = sizeof(myArray) / sizeof(myArray[0]);
	int i = 0;
	string line;
	ifstream myfile(fileName);
	if (myfile.is_open()) { //load the file
		for (int i = 0; i < size_arr; i++) {
			myfile >> myArray[i]; //load each element into array
		}
		myfile.close();
	}
	else cout << "Unable to open file";

	//start timer
	double duration;
	std::clock_t start;
	start = std::clock();

	//column sort
	int r = 4000;
	int s = 25;
	//creating 2d array
	short int** col_arr = new short int*[r];
	for (int i = 0; i < r; ++i) {
		col_arr[i] = new short int[s];
	}
	int counter = 0;
	//load into 2d array
	for (int i = 0; i < s; i++) { //col
		for (int j = 0; j < r; j++) { //row
			if (counter >= size_arr) {
				cout << counter << "counter overflow\n\n";
				break;
			}
			col_arr[j][i] = myArray[counter];
			counter++;
		}// end j
	}//end i


	//step 1
	//Quicksort_col quickSort_col;
	for (int i = 0; i < s; i++) { //25
		//quickSort_col.quickSort(col_arr, 0, r-1, i); //sort the array
		insertion_sort_col(col_arr, r, i);
	}

	//step 2
	//creating 2d array
	short int** col_arr_tr = new short int*[r];
	for (int i = 0; i < r; ++i) {
		col_arr_tr[i] = new short int[s];
	}
	//tanspose
	int row_tr = -1; 
	for (int i = 0; i < s; i++) {     //25
		int k = 0;
		row_tr++;
		for (int j = 0; j < r; j++) { //4000
			if (k == s) { //check to get newline
				k = 0;
				row_tr++;
			}
			col_arr_tr[row_tr][k] = col_arr[j][i];
			k++;
		}
	}

	//step 3
	for (int i = 0; i < s; i++) { 
		//quickSort_col.quickSort(col_arr_tr, 0, r-1, i); //sort the array
		insertion_sort_col(col_arr_tr, r, i);
	}

	//step 4
	//creating 2d array
	short int** col_arr_tr2 = new short int*[r];
	for (int i = 0; i < r; ++i) {
		col_arr_tr2[i] = new short int[s];
	}
	//tanspose
	int col_tr = 0;
	int k = 0;
	for (int i = 0; i < r; i++) {     //25
		for (int j = 0; j < s; j++) { //4000
			if (k == r) { //check to get newline
				k = 0;
				col_tr++;
			}
			col_arr_tr2[k][col_tr] = col_arr_tr[i][j];
			k++;
		}
	}

	//step 5
	for (int i = 0; i < s; i++) {     //25
		//quickSort_col.quickSort(col_arr_tr2, 0, r-1, i); //sort the array
		insertion_sort_col(col_arr_tr2, r, i);
	}

	//step 6
	//creating 2d array
	short int** col_arr_shift = new short int*[r];
	for (int i = 0; i < r; ++i) {
		col_arr_shift[i] = new short int[s+1];
	}
	for (int i = 0; i < r/2; i++) {
		col_arr_shift[i][0] = -32766;         //top half of first col
		col_arr_shift[r/2 + i][25] = 32767;   //bot hafl of last col
	}
	//shifting middle cols
	for (int j = 0; j < s; j++) {     //25
		for (int i = 0; i < r; i++) {  //4000
			if (i >= r/2) { //second half of col
				col_arr_shift[i - r / 2][j+1] = col_arr_tr2[i][j];     //load to next top col
			}
			else {
				col_arr_shift[i + r / 2][j] = col_arr_tr2[i][j];     //bot half of first col
			}
		} //end 4000
	} //end 26

	//step 7
	for (int i = 0; i < s+1; i++) { //25
		//quickSort_col.quickSort(col_arr_shift, 0, r-1, i); //sort the array
		insertion_sort_col(col_arr_shift, r, i);
	}

	//step 8
	//creating 2d array
	short int** col_arr_shift2 = new short int*[r];
	for (int i = 0; i < r; ++i) {
		col_arr_shift2[i] = new short int[s];
	}
	for (int i = 0; i < r / 2; i++) {
		col_arr_shift2[i][0] = -32766;         //top half of first col
		col_arr_shift2[r / 2 + i][25] = 32767;   //bot hafl of last col
	}
	//shifting middle cols
	for (int j = 0; j < s; j++) {     //25
		for (int i = 0; i < r; i++) {  //4000
			if (i >= r / 2) { //second half of col
				col_arr_shift2[i][j] = col_arr_shift[i - r / 2][j+1];     //load to next top col
			}
			else {
				col_arr_shift2[i][j] = col_arr_shift[i + r / 2][j];     //bot half of first col
			}
		} //end 4000
	} //end 26

	//stop timer
	duration = ((double)std::clock() - start) / (double)CLOCKS_PER_SEC;
	std::cout << "Column sort(Insertion sort): " << duration << 's' << endl;

	//sort test
	int myCounter = r;
	short int myArray_test[4000];
	for (int j = 0; j < s; j++) { //4000
		for (int i = 0; i < myCounter; i++) { //4000
			myArray_test[i] = col_arr_shift2[i][0];
		}
		if (SortCheck(myArray_test, myCounter)) { 
			//cout <<" sorted" << endl; 
		}
		else {cout << " not sorted" << endl;};
	}

	//load result into 1d array
	int myArray_c = 0;
	for (int i = 0; i < s; i++) {
		for (int j = 0; j < r; j++) {
			myArray[myArray_c] = col_arr_shift2[j][i];
			myArray_c++;
		}
	}

	//out file
	string fileName_ = "col(Insert)_" + fileName;
	outFile(myArray, size_arr, fileName_); //output result array to a file
}

int main() {
	string fileName[] = {"Part1Data_1.txt","Part1Data_2.txt","Part1Data_3.txt"};
	filer myFiler; //instantiate filer class
	for (int i = 0; i < 3; i++) {
		myFiler.makefile(100000, 32000, fileName[i]); //generate file (int n, int range, string file_name)
	}

	//Quick sort
	for (int i = 0; i < 3; i++) {
		quick_sort(fileName[i]);
	}

	//ColumnSort (Quick sort)
	for (int i = 0; i < 3; i++) {
		columnsort_quicksort_array(fileName[i]);
	}

	//Insertion sort
	for (int i = 0; i < 3; i++) {
		insertion_sort_array(fileName[i]);
	}

	//ColumnSort (Insertion sort)
	for (int i = 0; i < 3; i++) {
		columnsort_insertion_array(fileName[i]);
	}

	system("pause");
	return(0);
}

