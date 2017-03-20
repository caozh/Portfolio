/*
Edge_detection.cpp
Author:          Zhihao Cao
Last modified:   2014/4/14
Description:     Applying differentiation filters on XY-axis to retrieve image primitive edges.
*/

#include "stdafx.h"
#include <iostream>
#include <fstream>
#include <vector>
#define WIDTH 500
#define HEIGHT 500

using namespace std;

//Declare the static variable
static long myfileBytes; 
//create result buffer 
vector<vector<int> > resultBuffer_3x3_X;
vector<vector<int> > resultBuffer_3x3_Y;
vector<vector<int> > resultBuffer_3x3_XY;
vector<vector<int> > resultBuffer_5x5_X;
vector<vector<int> > resultBuffer_5x5_Y;
vector<vector<int> > resultBuffer_5x5_XY;

void outfile(char* fileName, char* buffer, long bytes){
	FILE * outFile;
	outFile = fopen(fileName, "wb");
	fwrite(buffer, 1, bytes, outFile);
	fclose(outFile);
	free(buffer);
	cout << "Finish processing" << endl;
}

void differentiation_5x5_XY_edge(int** image_matrix) {

	vector<vector<int> > resultBuffer_5x5_XY_edge;
	//set up 2DVector size
	resultBuffer_5x5_XY_edge.resize(HEIGHT);
	for (int i = 0; i < HEIGHT; i++)
	{
		resultBuffer_5x5_XY_edge[i].resize(WIDTH);
	}

	//filling values
	for (int i = 0; i < HEIGHT; i++)
	{
		for (int j = 0; j < WIDTH; j++)
		{
			resultBuffer_5x5_XY_edge[i][j] = resultBuffer_5x5_XY[i][j];
		}
	}


	//operate  differebtiated Edge in all direction
	for (int i = 0; i < HEIGHT; i++)
	{
		for (int j = 0; j < WIDTH; j++)
		{
			if (resultBuffer_5x5_XY_edge[i][j] < 128)
				resultBuffer_5x5_XY_edge[i][j] = 255;
			else
				resultBuffer_5x5_XY_edge[i][j] = 0;
		}
	}

	char* out_buffer;
	out_buffer = (char*)malloc(sizeof(char)*myfileBytes);
	int k = 0;
	for (int i = 0; i < HEIGHT; i++){
		for (int j = 0; j < WIDTH; j++){
			out_buffer[k++] = (char)resultBuffer_5x5_XY_edge[i][j];
		}
	}

	outfile("8_differentiation_5x5_XY_edge.raw", out_buffer, myfileBytes);
}

void differentiation_5x5_XY(int** image_matrix) {
	//set up 2DVector size
	resultBuffer_5x5_XY.resize(HEIGHT);
	for (int i = 0; i < HEIGHT; i++)
	{
		resultBuffer_5x5_XY[i].resize(WIDTH);
	}

	//operate  differebtiated in all direction
	for (int i = 0; i < HEIGHT; i++)
	{
		for (int j = 0; j < WIDTH; j++)
		{
			resultBuffer_5x5_XY[i][j] = (resultBuffer_5x5_X[i][j] + resultBuffer_5x5_Y[i][j]);
		}
	}

	char* out_buffer;
	out_buffer = (char*)malloc(sizeof(char)*myfileBytes);
	int k = 0;
	for (int i = 0; i < HEIGHT; i++){
		for (int j = 0; j < WIDTH; j++){
			out_buffer[k++] = (char)resultBuffer_5x5_XY[i][j];
		}
	}

	outfile("7_differentiation_5x5_XY.raw", out_buffer, myfileBytes);
}

void differentiation_5x5_Y(int** image_matrix) {
	//set up 2DVector
	vector<vector<int> > sobel_5x5_Y{ {   1,   2,   0,  -2,  -1 },
								   	  {   4,   8,   0,  -8,  -4 },
							    	  {   6,  12,   0, -12,  -6 },
								      {   4,   8,   0,  -8,  -4 },
								      {   1,   2,   0,  -2,  -1 } };

	//set up 2DVector size
	resultBuffer_5x5_Y.resize(HEIGHT);
	for (int i = 0; i < HEIGHT; i++)
	{
		resultBuffer_5x5_Y[i].resize(WIDTH);
	}


	//operate  differebtiated[5] in y direction
	int sum;
	for (int i = 2; i < HEIGHT - 2; i++)
	{
		for (int j = 2; j < WIDTH - 2; j++)
		{
			sum = 0;
			for (int k = 0; k < 5; k++)
			{
				for (int l = 0; l < 5; l++)
				{
					sum += sobel_5x5_Y[k][l] * image_matrix[i + k - 2][j + l - 2];
				}
			}
			resultBuffer_5x5_Y[i][j] = sum / 16 ;
		}
	}

	char* out_buffer;
	out_buffer = (char*)malloc(sizeof(char)*myfileBytes);
	int k = 0;
	for (int i = 0; i < HEIGHT; i++){
		for (int j = 0; j < WIDTH; j++){
			out_buffer[k++] = (char)resultBuffer_5x5_Y[i][j];
		}
	}

	outfile("6_differentiation_5x5_Y.raw", out_buffer, myfileBytes);
}

void differentiation_5x5_X(int** image_matrix) {
	//set up 2DVector
	vector<vector<int> > sobel_5x5_X{ { -1, -4,  -6, -4, -1 },
								      { -2, -8, -12, -8, -2 },
								      {  0,  0,   0,  0,  0 },
									  {  2,  8,  12,  8,  2 },
									  {  1,  4,   6,  4,  1 } };

	//set up 2DVector size
	resultBuffer_5x5_X.resize(HEIGHT);
	for (int i = 0; i < HEIGHT; i++)
	{
		resultBuffer_5x5_X[i].resize(WIDTH);
	}


	//operate  differebtiated[5] in x direction
	int sum;
	for (int i = 2; i < HEIGHT - 2; i++)
	{
		for (int j = 2; j < WIDTH - 2; j++)
		{
			sum = 0;
			for (int k = 0; k < 5; k++)
			{
				for (int l = 0; l < 5; l++)
				{
					sum += sobel_5x5_X[k][l] * image_matrix[i + k - 2][j + l - 2];
				}
			}
			resultBuffer_5x5_X[i][j] = sum /16 ;
		}
	}

	char* out_buffer;
	out_buffer = (char*)malloc(sizeof(char)*myfileBytes);
	int k = 0;
	for (int i = 0; i < HEIGHT; i++){
		for (int j = 0; j < WIDTH; j++){
			out_buffer[k++] = (char)resultBuffer_5x5_X[i][j];
		}
	}
	
	outfile("5_differentiation_5x5_X.raw", out_buffer, myfileBytes);

}

void differentiation_3x3_XY_edge(int** image_matrix) {

	vector<vector<int> > resultBuffer_3x3_XY_edge;
	//set up 2DVector size
	resultBuffer_3x3_XY_edge.resize(HEIGHT);
	for (int i = 0; i < HEIGHT; i++)
	{
		resultBuffer_3x3_XY_edge[i].resize(WIDTH);
	}

	//filling values
	for (int i = 0; i < HEIGHT; i++)
	{
		for (int j = 0; j < WIDTH; j++)
		{
			resultBuffer_3x3_XY_edge[i][j] = resultBuffer_3x3_XY[i][j];
		}
	}

	
	//operate  differebtiated Edge in all direction
	for (int i = 0; i < HEIGHT; i++)
	{
		for (int j = 0; j < WIDTH; j++)
		{
			if (resultBuffer_3x3_XY_edge[i][j] < 128)
				resultBuffer_3x3_XY_edge[i][j] = 255;
			else
				resultBuffer_3x3_XY_edge[i][j] = 0;
		}
	}

	char* out_buffer;
	out_buffer = (char*)malloc(sizeof(char)*myfileBytes);
	int k = 0;
	for (int i = 0; i < HEIGHT; i++){
		for (int j = 0; j < WIDTH; j++){
			out_buffer[k++] = (char)resultBuffer_3x3_XY_edge[i][j];
		}
	}

	outfile("4_differentiation_3x3_XY_edge.raw", out_buffer, myfileBytes);
}

void differentiation_3x3_XY(int** image_matrix) {
	
	//set up 2DVector size
	resultBuffer_3x3_XY.resize(HEIGHT);
	for (int i = 0; i < HEIGHT; i++)
	{
		resultBuffer_3x3_XY[i].resize(WIDTH);
	}

	//operate  differebtiated in all direction
	for (int i = 0; i < HEIGHT; i++)
	{
		for (int j = 0; j < WIDTH; j++)
		{
			resultBuffer_3x3_XY[i][j] = (resultBuffer_3x3_X[i][j] + resultBuffer_3x3_Y[i][j])/2.6;
		}
	}

	char* out_buffer;
	out_buffer = (char*)malloc(sizeof(char)*myfileBytes);
	int k = 0;
	for (int i = 0; i < HEIGHT; i++){
		for (int j = 0; j < WIDTH; j++){
			out_buffer[k++] = (char)resultBuffer_3x3_XY[i][j];
		}
	}

	outfile("3_differentiation_3x3_XY.raw", out_buffer, myfileBytes);
}

void differentiation_3x3_Y(int** image_matrix){
	//set up 2DVector
	vector<vector<int> > sobel_3x3_Y{ 
		{  3,  0,  -3 },
		{ 10,  0, -10 },
		{  3,  0,  -3 } };

	//set up 2DVector size
	resultBuffer_3x3_Y.resize(HEIGHT);
	for (int i = 0; i < HEIGHT; i++)
	{
		resultBuffer_3x3_Y[i].resize(WIDTH);
	}


	//operate  differebtiated[3] in y direction
	int sum;
	for (int i = 1; i < HEIGHT - 1; i++)
	{
		for (int j = 1; j < WIDTH - 1; j++)
		{
			sum = 0;
			for (int k = 0; k < 3; k++)
			{
				for (int l = 0; l < 3; l++)
				{
					sum += sobel_3x3_Y[k][l] * image_matrix[i + k - 1][j + l - 1];
				}
			}
			resultBuffer_3x3_Y[i][j] = 128 + sum/5;
		}
	}


	char* out_buffer;
	out_buffer = (char*)malloc(sizeof(char)*myfileBytes);
	int k = 0;
	for (int i = 0; i < HEIGHT; i++){
		for (int j = 0; j < WIDTH; j++){
			out_buffer[k++] = (char)resultBuffer_3x3_Y[i][j];
		}
	}



	outfile("2_differentiation_3x3_Y.raw", out_buffer, myfileBytes);


}

void differentiation_3x3_X(int** image_matrix){
	//set up 2DVector
	vector<vector<int> > sobel_3x3_X{ {  3,  10,  3 },
								      {  0,   0,  0 },
									  { -3, -10, -3 } };

	
	//set up 2DVector size
	resultBuffer_3x3_X.resize(HEIGHT);
	for (int i = 0; i < HEIGHT; i++)
	{
		resultBuffer_3x3_X[i].resize(WIDTH);
	}

	
	//operate  differebtiated[3] in x direction
	int sum;
	for (int i = 1; i < HEIGHT - 1; i++)
	{
		for (int j = 1; j < WIDTH - 1; j++)
		{
			sum = 0;
			for (int k = 0; k < 3; k++)
			{
				for (int l = 0; l < 3; l++)
				{
					sum += sobel_3x3_X[k][l] * image_matrix[i + k - 1][j + l - 1];
				}
			}
			resultBuffer_3x3_X[i][j] = 128 + sum /5;
		}
	}


	char* out_buffer;
	out_buffer = (char*)malloc(sizeof(char)*myfileBytes);
	int k = 0;
	for (int i = 0; i < HEIGHT; i++){
		for (int j = 0; j < WIDTH; j++){
			out_buffer[k++] = (char)resultBuffer_3x3_X[i][j];
		}
	}



	outfile("1_differentiation_3x3_X.raw", out_buffer, myfileBytes);

}

int** readImage(char* image_name) {
	//Read the image
	FILE * myfile;
	myfile = fopen(image_name, "rb");

	if (myfile == NULL){
		perror("Error in opening the file");
		exit(0);
	}

	//Read the size of the image in bytes 
	
	fseek(myfile, 0, SEEK_END);
	myfileBytes = ftell(myfile);
	rewind(myfile);

	//put the data in the buffer
	char * buffer;
	size_t errorCheck;//It is necessary to have a checker for the data

	buffer = (char*)malloc(sizeof(char)* myfileBytes);//allocate memory the create a buffer
	if (buffer == NULL) {
		fputs("Buffer error", stderr);
		exit(1);
	}
	//Check the data
	errorCheck = fread(buffer, 1, myfileBytes, myfile);
	if (errorCheck != myfileBytes) {
		fputs("Checker error", stderr);
		exit(2);
	}
	fclose(myfile);//close file

	//Create a buffer to store data
	int ** image_matrix = new int*[HEIGHT];
	for (int i = 0; i < HEIGHT; i++){
		image_matrix[i] = new int[WIDTH];
	}

	int k = 0;
	for (int i = 0; i < HEIGHT; i++){
		for (int j = 0; j < WIDTH; j++) {
			image_matrix[i][j] = (int)(unsigned char)buffer[k];
			k++;
		}//end  j loop
	}//end i loop
	free(buffer);

	return image_matrix;
};




int main() {

	//read the image into memory according to the input name, and return a matirx
	int ** image_matrix = readImage("image.raw");

	//Do the covolution in different filters.
	differentiation_3x3_X(image_matrix);
	differentiation_3x3_Y(image_matrix);
	differentiation_3x3_XY(image_matrix);
	differentiation_3x3_XY_edge(image_matrix); // Detect edges by 128
	differentiation_5x5_X(image_matrix);
	differentiation_5x5_Y(image_matrix);
	differentiation_5x5_XY(image_matrix);
	differentiation_5x5_XY_edge(image_matrix); // Detect edges by 128

	//cin.ignore();
	return 0;
}