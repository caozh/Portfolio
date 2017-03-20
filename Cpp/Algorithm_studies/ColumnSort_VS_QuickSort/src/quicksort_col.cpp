#include "quicksort_col.h"

/* A typical recursive C/C++  implementation of QuickSort */

/* This function takes last element as pivot, places
the pivot element at its correct position in sorted
array, and places all smaller (smaller than pivot)
to left of pivot and all greater elements to right
of pivot */

// A utility function to swap two elements
void Quicksort_col::swap(short int* a, short int* b)
{
	short int t = *a;
	*a = *b;
	*b = t;
}

int Quicksort_col::partition(short int ** arr, int l, int h, int k)
{
	int x = arr[h][k];
	int i = (l - 1);

	for (int j = l; j <= h - 1; j++)
	{
		if (arr[j][k] <= x)
		{
			i++;
			swap(&arr[i][k], &arr[j][k]);
		}
	}
	swap(&arr[i + 1][k], &arr[h][k]);
	return (i + 1);
}

/* A[] --> Array to be sorted,
l  --> Starting index,
h  --> Ending index 
k  --> column index*/
void Quicksort_col::quickSort(short int ** arr, int l, int h, int k)
{
	if (l < h)
	{
		/* Partitioning index */
		int p = partition(arr, l, h, k);
		quickSort(arr, l, p - 1, k);
		quickSort(arr, p + 1, h, k);
	}
}