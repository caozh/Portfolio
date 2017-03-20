#pragma once

class Quicksort
{
public:
	void quickSort(short int A[], int l, int h);
	//constructor - turns on the timer

	int partition(short int arr[], int l, int h);
	//compute elapsed time between start and stop

	void swap(short int* a, short int* b);
	//restarts the timer

};