/*
Author: Zhihao Cao
Modify date: 2014/2/24
Subject: Solving Knight's tour problem 
Course: CSCI-36200
*/

#include "stdafx.h"
#include <iostream>
#include <fstream>
#include <string>
#include <sstream>
#include <ctime>
using namespace std;

//Create a map
const int N = 8;//modify the chess board size.
int map[N][N];

//Define the move direction.
int xMove[8] = { 2, 1, -1, -2, -2, -1, 1, 2 };
int yMove[8] = { 1, 2, 2, 1, -1, -2, -2, -1 };


template <typename T>
class Stack {
	T *sa;
	int sp;
public:
	Stack() {
		sp = -1;
		sa = new T [100];
	}


	void push(T i){
		if (isFull()){
			throw std::exception("Stack Overflow!");
			return;
		}
		sa[++sp] = i;
	}

	T pop() {
		if (isEmpty())
			throw std::exception("Stack Underflow!");
		return sa[sp--];
	}

	bool isFull(){
		if (sp < 99)
			return false;
		return true;
	}

	bool isEmpty(){
		if (sp == -1)
			return true;
		return false;
	}

	T getTop() {
		if (isEmpty())
			throw std::exception("Stack Underflow!");
		return sa[sp];
	}
};

class StkOp
{
	int row, col, dir;
public:
	StkOp()
	{
		set(0, 0, 0);
	}

	StkOp(int r, int c, int d)
	{
		set(r, c, d);
	}

	void set(int r, int c, int d)
	{
		row = r;
		col = c;
		dir = d;
	}

	void get(int& r, int & c, int & d)
	{
		r = row;
		c = col;
		d = dir;
	}

};

bool ifMove(int x, int y, int row, int col, int &nextRow, int &nextCol){
	//if (row + x >= 0 && row + x < 8 && col + y >= 0 && col + y < 8){
	if (row + y >= 0 && row + y < N && col + x >= 0 && col + x < N && map[row + y][col + x] == 0){
		//cout << "Current position: (" << row << ", " << col << "),  move direction:(" << x  << ", " << y << ")" << endl;
		nextRow = row + y;
		nextCol = col + x;
		//cout << "   Next position: (" << nextRow << ", " << nextCol << ")" << endl;
		return true;
	}
	//cout << "No further move, need trace back" << endl;
	return false;
	
}

int warnsdorff(int &x, int &y){
	//The position of next move.
	int nextX[8] = { 0 };
	int nextY[8] = { 0 };
	//Save the number of possible moves for the next move.
	int exist[8] = { 0 };
	int k, m, l;
	int tmpX, tmpY;
	int count, min, tmp;
	//First move of the night.
	map[x][y] = 1;

	//Start the main loop of Warnsdorff's rule.
	for (m = 2; m <= ((N*N) / 2); m++) {
	//for (m = 2; m <= (N*N); m++) {
		//Initialize the next available move counter && the array for the number of possible moves for the next move.
		for (l = 0; l < 8; l++)
			exist[l] = 0;
		l = 0;
		
		//Check next move from current position.
		for (k = 0; k < 8; k++){
			tmpX = x + xMove[k];
			tmpY = y + yMove[k];
			//check if next move is valid.
			if (tmpX >= 0 && tmpX < N && tmpY >= 0 && tmpY < N && map[tmpX][tmpY] == 0){
				//save the next available move position in the array.
				nextX[l] = tmpX;
				nextY[l] = tmpY;
				//Increase the number of next available move.
				l++;
			}
		}
		
		count = l; //create a counter for l(the next available move counter);
		//if there is no next move.
		if (count == 0){
			return 0;
		}
		//if there is only one next move, we direclty go to this move.
		else if (count == 1){
			min = 0;
		}
		//if there are more than 1 available moves.
		else{
			//Check the next available moves of the next move.
			for (l = 0; l < count; l++){
				for (k = 0; k < 8; k++){
					tmpX = nextX[l] + xMove[k];
					tmpY = nextY[l] + yMove[k];
					if (tmpX >= 0 && tmpX < N && tmpY >= 0 && tmpY < N && map[tmpX][tmpY] == 0)
						exist[l]++;
				}//end loop k
			}//end loop l

			//look for the minimun for exist[].
			tmp = exist[0];
			min = 0;
			for (l = 1; l < count; l++){
				if (exist[l] < tmp){
					tmp = exist[l];
					min = l;
				}
			}
		}//end else

		//Move to the least possible moves of next move.
		x = nextX[min];
		y = nextY[min];
		map[x][y] = m;
	}
	return 1;
}

int backTracking(int &x, int &y){
	//Instantiate the stack and push the first stack
	int row;
	int col;
	int counter = (N*N) / 2;
	int direction = 0;
	Stack<StkOp> myStack;
	row = x;
	col = y;
	StkOp stkT(row, col, 0);
	myStack.push(stkT);

	//the main loop of the algorithm
	int nextRow = 0, nextCol = 0;

	//When there is any empty block.
	while (counter <= (N*N)-1 && !myStack.isEmpty()){
		stkT = myStack.getTop();
		stkT.get(row, col, direction);

		//check if the next move is valid. If not, changes direction. 
		while (direction < 8 && !ifMove(xMove[direction], yMove[direction], row, col, nextRow, nextCol)){
			direction++;
		}
		//Push next move into stack
		if (direction != 8){
			myStack.pop();
			stkT.set(row, col, direction + 1);// When the pointer turns back, it poting to next direction.
			myStack.push(stkT);
			stkT.set(nextRow, nextCol, 0);//Push next move into stack, the direction should begin from 0.
			myStack.push(stkT);

			map[nextRow][nextCol] = ++counter;
			//print counter and position
			cout << "Counter: " << counter << ", position: " << "(" << nextCol + 1 << ", " << nextRow + 1 << ")" << endl << endl;
		}
		//No further move to go, pop the stack.
		else{
			myStack.pop();
			map[row][col] = 0;
			counter--;
		}
	}
	return 0;

}

int main() {

	//Input the initial position.
	int initX, initY;
	cout << "Please enter a valid initial row number(start from 1):\n";
	cin >> initX;
	cout << "Then, enter a valid initial column number(start from 1):\n" ;
	cin >> initY;
	cout << "Your initial position is: (" << initX << "," << initY << ")" << endl << endl;
	initX--;
	initY--;

	//initialize the chess board.
	for (int x = 0; x < N; x++)
		for (int y = 0; y < N; y++)
			map[x][y] = 0;

	//Start the Warnsdorff's rule.
	warnsdorff(initX, initY);
	//Start the back-tracking's rule.
	backTracking(initX, initY);

	//Print the chess board.
	ofstream myfile;
	myfile.open("a.out");



	for (int x = 0; x < N; x++) {
		for (int y = 0; y < N; y++)
			myfile << map[x][y] << "     ";
			//cout << map[x][y]<<"     ";
		myfile << endl << endl;
		//cout << endl << endl;
	}
	myfile.close();

	cin.ignore();
	cin.get();
	return 0;
}

