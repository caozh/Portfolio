/* 
Author:      Zhihao Cao
Last modify: 10/3/2016
Title:       Program#1 finding peak
Copyright 2016 Indiana University Purdue University Indianapolis. All rights reserved.
*/

#include <iostream> 
#include <iomanip>
#include <fstream> 
#include <vector>

using namespace std;

vector<int> loadFile(string fineName)
{
	vector<int> vec;            //initialize output vector
	ifstream fin(fineName);
	if (fin.is_open())
	{
		int value;
		while (fin >> value)
		{
			vec.push_back(value);  //push each value into back of vector
		}
	}
	else //file could not be opened
	{
		cout << "File could not be opened." << endl;
	}

	cout << "Displaying Array:" << endl;                 //show array
	for (int i = 0; i < vec.size(); i++)
	{
		cout << setfill(' ') << setw(3) << vec[i] << " ";
	}
	cout << " value" <<endl;	
	for (int i = 0; i < vec.size(); i++)
	{
		cout << setfill(' ') << setw(3) << i << " ";
	}
	cout << " index" << endl;
	cout << "Vector size: " << vec.size() << endl << endl; //show vector size
	return vec;
}

int peak(vector<int> input, int l, int r) 
{
	if (l == r)  //peak value found
	{
		cout << "Peak: " << input[l] << endl;
		return input[l];
	}
	int m = floor((l + r) / 2);
	cout << "period: " << l << "~" << m << " and " << m+1 << "~" << r <<endl; //floor value

	if (input[m] < input[m + 1])           //peak is in right part
	{
		cout << "right larger " << endl << endl;
		peak(input, m + 1, r);
	}
	else if(input[m] > input[m + 1])       //peak is in left part
	{
		cout << "left larger " << endl << endl;
		peak(input, l, m);
	}
	else if (input[m] == input[m + 1])
	{
		cout << "Not valid input (all values must be distinct)" << endl << endl;
		return 0;
	}
}

int main()
{
	vector<int> vec = loadFile("peak.txt");   //load the input txt file

	int l = 0;
	int r = vec.size()-1;
	int result = peak(vec, l, r);  //recurssive function to find peak value from the input vector
	//cout << "Result: " << result << endl;

	system("pause");
	return 0;
}