/*
Image_filtering
Author:          Zhihao Cao
Last modified:   2014/2/24
Description:     Applying Average/Motion/Gaussian filters on an image.
*/

#include "stdafx.h"
#include <iostream>
#include <fstream>

#define WIDTH 500
#define HEIGHT 500

using namespace std;

void outfile(char* fileName, char* buffer, long bytes){
	FILE * outFile;
	outFile = fopen(fileName, "wb");
	fwrite(buffer, 1, bytes, outFile);
	fclose(outFile);
	free(buffer);
}


void ave(int* data[], long bytes) {
	int total;

	//3x3 Averaging blur filter
	int** buffer = new int*[HEIGHT];
	for (int i = 0; i < HEIGHT; i++){
		buffer[i] = new int[WIDTH];
	}
		
	//create filter matrix
	int filter_3x3[3][3] = {
		{ 1, 1, 1 },
		{ 1, 1, 1 },
		{ 1, 1, 1 }
	};

	for (int i = 0; i < HEIGHT; i++){
		for (int j = 0; j < WIDTH; j++){
			//3x3
			if (i == 0 || j == 0 || i == HEIGHT - 1 || j == WIDTH - 1){
				buffer[i][j] = data[i][j];
				continue;
			}
			total = 0;
			for (int m = 0; m < 3; m++){
				for (int n = 0; n < 3; n++)
					total += data[i + m - 1][j + n - 1] * filter_3x3[m][n];
			}
			if (total / 9 > 255)
				buffer[i][j] = 255;
			else
				buffer[i][j] = total / 9;
		}
	}

	char* out_buffer;
	out_buffer = (char*)malloc(sizeof(char)*bytes);
	int k = 0;
	for (int i = 0; i < HEIGHT; i++){
		for (int j = 0; j < WIDTH; j++){
			out_buffer[k++] = (char)buffer[i][j];
		}
	}
	outfile("Average_3x3.raw", out_buffer, bytes);

	//5x5Averaging blur filter
	int filter_5x5[5][5] = {
		{ 1, 1, 1, 1, 1 },
		{ 1, 1, 1, 1, 1 },
		{ 1, 1, 1, 1, 1 },
		{ 1, 1, 1, 1, 1 },
		{ 1, 1, 1, 1, 1 }
	};

	//Start convolution
	for (int i = 0; i < HEIGHT; i++){
		for (int j = 0; j < WIDTH; j++){
			//we ignore the pixel at the edge.
			if (i >= HEIGHT - 2 || j >= WIDTH - 2 || i <= 1 || j <= 1 ){
				buffer[i][j] = data[i][j];
				continue;
			}
			total = 0;
			for (int m = 0; m < 5; m++){
				for (int n = 0; n < 5; n++)
					total += data[i + m - 2][j + n - 2] * filter_5x5[m][n];
			}
			if (total / 25 > 255)
				buffer[i][j] = 255;
			else
				buffer[i][j] = total / 25;
		}
	}

	//allocate the data to out_buffer
	out_buffer = (char*)malloc(sizeof(char)*bytes);
	//put values into the out_buffer
	k = 0;
	for (int i = 0; i < HEIGHT; i++){
		for (int j = 0; j < WIDTH; j++){
			out_buffer[k++] = (char)buffer[i][j];
		}
	}
	//output buffer to a raw file.
	outfile("Average_5x5.raw", out_buffer, bytes);
}

void motion(int* data[], long bytes){
	//3x3 motion blur filter
	int** buffer = new int*[HEIGHT];
	for (int i = 0; i < HEIGHT; i++){
		buffer[i] = new int[WIDTH];
	}

	//create filter matrix
	int filter_3x3[3][3] = {
		{ 1, 0, 0 },
		{ 0, 1, 0 },
		{ 0, 0, 1 }
	};


	int total;
	for (int i = 0; i < HEIGHT; i++){
		for (int j = 0; j < WIDTH; j++){
			//3x3
			if (i == HEIGHT - 1 || j == WIDTH - 1 || i == 0 || j == 0  ){
				buffer[i][j] = data[i][j];
				continue;
			}
			total = 0;
			for (int m = 0; m < 3; m++){
				for (int n = 0; n < 3; n++)
					total += data[i + m - 1][j + n - 1] * filter_3x3[m][n];
			}
			if (total / 3 > 255)
				buffer[i][j] = 255;
			else
				buffer[i][j] = total / 3;

		}

	}

	char* out_buffer;
	out_buffer = (char*)malloc(sizeof(char)*bytes);
	int k = 0;
	for (int i = 0; i < HEIGHT; i++){
		for (int j = 0; j < WIDTH; j++){
			out_buffer[k++] = (char)buffer[i][j];
		}
	}
	outfile("Motion_3x3.raw", out_buffer, bytes);

	//5x5Averaging blur filter
	int filter_5x5[5][5] = {
		{ 1, 0, 0, 0, 0 },
		{ 0, 1, 0, 0, 0 },
		{ 0, 0, 1, 0, 0 },
		{ 0, 0, 0, 1, 0 },
		{ 0, 0, 0, 0, 1 }
	};

	//Start convolution
	for (int i = 0; i < HEIGHT; i++){
		for (int j = 0; j < WIDTH; j++){
			//we ignore the pixel at the edge.
			if (i >= HEIGHT - 2 || j >= WIDTH - 2 || i <= 1 || j <= 1){
				buffer[i][j] = data[i][j];
				continue;
			}
			total = 0;
			for (int m = 0; m < 5; m++){
				for (int n = 0; n < 5; n++)
					total += data[i + m - 2][j + n - 2] * filter_5x5[m][n];
			}
			if (total / 5 > 255)
				buffer[i][j] = 255;
			else
				buffer[i][j] = total / 5;
		}

	}

	//allocate the data to out_buffer
	out_buffer = (char*)malloc(sizeof(char)*bytes);
	//put values into the out_buffer
	k = 0;
	for (int i = 0; i < HEIGHT; i++){
		for (int j = 0; j < WIDTH; j++){
			out_buffer[k++] = (char)buffer[i][j];
		}
	}
	//output buffer to a raw file.
	outfile("Motion_5x5.raw", out_buffer, bytes);
}

void gaussian(int* data[], long bytes){
	//3x3 gaussian blur filter
	int** buffer = new int*[HEIGHT];
	for (int i = 0; i < HEIGHT; i++){
		buffer[i] = new int[WIDTH];
	}

	//create filter matrix
	int filter_3x3[3][3] = {
		{ 1, 2, 1 },
		{ 2, 4, 2 },
		{ 1, 2, 1 }
	};

	int total;
	for (int i = 0; i < HEIGHT; i++){
		for (int j = 0; j < WIDTH; j++){
			//3x3
			if (i == HEIGHT - 1 || j == WIDTH - 1 || i == 0 || j == 0){
				buffer[i][j] = data[i][j];
				continue;
			}
			total = 0;
			for (int m = 0; m < 3; m++){
				for (int n = 0; n < 3; n++)
					total += data[i + m - 1][j + n - 1] * filter_3x3[m][n];
			}
			if (total / 16 > 255)
				buffer[i][j] = 255;
			else
				buffer[i][j] = total / 16;
		}

	}

	char* out_buffer;
	out_buffer = (char*)malloc(sizeof(char)*bytes);
	int k = 0;
	for (int i = 0; i < HEIGHT; i++){
		for (int j = 0; j < WIDTH; j++){
			out_buffer[k++] = (char)buffer[i][j];
		}
	}
	outfile("Gaussian_3x3.raw", out_buffer, bytes);

	//5x5 gaussian blur filter
	int filter_5x5[5][5] = {
		{ 1, 2, 4, 2, 1 },
		{ 2, 4, 8, 4, 2 },
		{ 4, 8, 16, 8, 4 },
		{ 2, 4, 8, 4, 2 },
		{ 1, 2, 4, 2, 1 }
	};

	//Start convolution
	for (int i = 0; i < HEIGHT; i++){
		for (int j = 0; j < WIDTH; j++){
			//we ignore the pixel at the edge.
			if (i >= HEIGHT - 2 || j >= WIDTH - 2 || i <= 1 || j <= 1){
				buffer[i][j] = data[i][j];
				continue;
			}
			total = 0;
			for (int m = 0; m < 5; m++){
				for (int n = 0; n < 5; n++)
					total += data[i + m - 2][j + n - 2] * filter_5x5[m][n];
			}
			if (total / 100 > 255)
				buffer[i][j] = 255;
			else
				buffer[i][j] = total / 100;
		}
	}

	//allocate the data to out_buffer
	out_buffer = (char*)malloc(sizeof(char)*bytes);
	//put values into the out_buffer
	k = 0;
	for (int i = 0; i < HEIGHT; i++){
		for (int j = 0; j < WIDTH; j++){
			out_buffer[k++] = (char)buffer[i][j];
		}
	}
	//output buffer to a raw file.
	outfile("Gaussian_5x5.raw", out_buffer, bytes);
}

int main() {
	//Read the image
	FILE * myfile;
	myfile = fopen("car.raw", "rb");

	if (myfile == NULL){
		perror("Error in opening the file");
		exit(0);
	}

	//Read the size of the image in bytes 
	long myfileBytes;
	fseek(myfile, 0, SEEK_END);
	myfileBytes = ftell(myfile);
	rewind(myfile);

	//put the data in the buffer
	char * buffer;
	size_t errorCheck;//It is necessary to have a checker for the data
	
	buffer = (char*)malloc(sizeof(char)* myfileBytes);
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
	int ** buffer2 = new int*[HEIGHT];
	for (int i = 0; i < HEIGHT; i++){
		buffer2[i] = new int[WIDTH];
	}

	int k = 0;
	for (int i = 0; i < HEIGHT; i++){
		for (int j = 0; j < WIDTH; j++) {
			buffer2[i][j] = (int)(unsigned char)buffer[k];
			k++;
		}//end  j loop
	}//end i loop
	free(buffer);

	//run the image-operation functions.
	ave(buffer2, myfileBytes);
	motion(buffer2, myfileBytes);
	gaussian(buffer2, myfileBytes);

	cout << "Finish processing the image" << endl;

	cin.ignore();
	cin.get();
	return 0;
}



