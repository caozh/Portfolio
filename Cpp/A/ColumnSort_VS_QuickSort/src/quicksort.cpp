#include "quicksort.h"

/* A typical recursive C/C++  implementation of QuickSort */

/* This function takes last element as pivot, places
the pivot element at its correct position in sorted
array, and places all smaller (smaller than pivot)
to left of pivot and all greater elements to right
of pivot */

// A utility function to swap two elements
void Quicksort::swap(short int* a, short int* b)
{
	int t = *a;
	*a = *b;
	*b = t;
}

int Quicksort::partition(short int arr[], int l, int h)
{
	int x = arr[h];
	int i = (l - 1);

	for (int j = l; j <= h - 1; j++)
	{
		if (arr[j] <= x)
		{
			i++;
			swap(&arr[i], &arr[j]);
		}
	}
	swap(&arr[i + 1], &arr[h]);
	return (i + 1);
}

/* A[] --> Array to be sorted,
l  --> Starting index,
h  --> Ending index */
void Quicksort::quickSort(short int A[], int l, int h)
{
	if (l < h)
	{
		/* Partitioning index */
		int p = partition(A, l, h);
		quickSort(A, l, p - 1);
		quickSort(A, p + 1, h);
	}
}