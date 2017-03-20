#include <iostream>
#include <sstream>
#include <opencv2\calib3d\calib3d.hpp>
#include <math.h>
#include <opencv2\imgproc\imgproc.hpp>
#include <string>
#include "MarkerDetector.h"

#define CELL_LENGTH 10
#define MARKER_LENGTH (7*CELL_LENGTH)

using namespace std;
using namespace cv;

//MarkerDetector class
MarkerDetector::MarkerDetector() { 
	//assign marker 2d position, counterclockwise
	marker_position.push_back(Point2f(0, 0));
	marker_position.push_back(Point2f(0, MARKER_LENGTH - 1));
	marker_position.push_back(Point2f(MARKER_LENGTH - 1, MARKER_LENGTH - 1));
	marker_position.push_back(Point2f(MARKER_LENGTH - 1, 0));
}

int MarkerDetector::startDetect(Mat& input_img, int img_size, int img_length) {
	CV_Assert(!input_img.empty());            //check if imput image is valid
	CV_Assert(input_img.type() == CV_8UC1);

	Mat my_img = input_img.clone();         //real copy
	//increase the image contrast
	equalizeHist(my_img, my_img);
	//declare the marker vector to hold found markers
	vector<Marker> marker_found;
	//find the markers
	find_marker(my_img, marker_found, img_size, img_length);
	//filter out the wrong markers and decode markers
	validate_marker(my_img, marker_found, marker_result);
	//refine marker corners
	refine_mraker(my_img, marker_result);

	return marker_result.size();
}

void MarkerDetector::find_marker(Mat& img_gray, vector<Marker>& marker_found, int img_size, int img_length) {
	Mat binary_image;                            //declare binary image
	int threshold = (img_size / 4) * 2 + 1;    //calculate the size threshold of finding markers
	//apply openCV adaptiveThreshold() to obtain the binary image
	adaptiveThreshold(img_gray, binary_image, 255, ADAPTIVE_THRESH_GAUSSIAN_C, THRESH_BINARY_INV, threshold, threshold / 3);
	//use open operation to remove noise
	morphologyEx(binary_image, binary_image, MORPH_OPEN, Mat());

	vector<vector<Point>> contour_result;     //contours found
	vector<vector<Point>> valid_contour;         //valid marker contours
	//apply openCV findContours() to obtain image contours
	findContours(binary_image, contour_result, CV_RETR_LIST, CV_CHAIN_APPROX_NONE);

	//filter out non-marker contours by size
	for (int i = 0; i < contour_result.size(); ++i) {
		if (contour_result[i].size() > img_size) {
			valid_contour.push_back(contour_result[i]);
		}
	}
	
	//approximate curves of polygonal in the contour
	vector<Point> poly_curves;  //declare the pologon curve variable
	for (int i = 0; i < valid_contour.size(); ++i) {
		double epsilon = valid_contour[i].size()*0.08;
		//apply openCV approxPolyDP() to approximate the number of curves in a polygon
		approxPolyDP(valid_contour[i], poly_curves, epsilon, true); 

		//filter out not 4-curve polygon
		if (poly_curves.size() != 4) continue;
			
		//filter out non-convex contours 
		if (!isContourConvex(poly_curves)) continue;
			
		//check if two point is far enough
		float edge_min = FLT_MAX;
		for (int j = 0; j < 4; ++j){
			Point edge = poly_curves[j] - poly_curves[(j + 1) % 4];
			edge_min = min(img_size, edge.dot(edge));
		}
		//filter out too small contours
		if (edge_min < img_length*img_length) continue;

		//assign the corners in counterclockwise order
		Marker marker_final = Marker(0, poly_curves[0], poly_curves[1], poly_curves[2], poly_curves[3]); //(id,corner1-4)
		Point2f corner_1 = marker_final.marker_corners[1] - marker_final.marker_corners[0];
		Point2f corner_2 = marker_final.marker_corners[2] - marker_final.marker_corners[0];
		if (corner_1.cross(corner_2) > 0) {  //swap y
			swap(marker_final.marker_corners[1], marker_final.marker_corners[3]);
		}
		marker_found.push_back(marker_final);
	}
}

void MarkerDetector::validate_marker(cv::Mat& input_img, vector<Marker>& marker_found, vector<Marker>& true_markers) {
	//clear the marker vector
	true_markers.clear();

	Mat img_marker;                         //declare the marker image
	Mat marker_binary_matrix(5, 5, CV_8UC1);          //declare the marker code matrix                                  
	//for each marker
	for (int i = 0; i < marker_found.size(); ++i) {
		//obtain the marker canonical form by openCV getPerspectiveTransform() and warpPerspective()
		Mat myMat = getPerspectiveTransform(marker_found[i].marker_corners, marker_position);
		warpPerspective(input_img, img_marker, myMat, Size(MARKER_LENGTH, MARKER_LENGTH));
		//obtain the binarization marker img by openCV threshold()
		threshold(img_marker, img_marker, 125, 255, THRESH_BINARY | THRESH_OTSU); 

		//A marker must has a whole black border.
		for (int y = 0; y < 7; ++y) {          //for each y-cell of the marker
			int increment = (y == 0 || y == 6) ? 1 : 6;
			int cellY = y*CELL_LENGTH;

			for (int x = 0; x < 7; x += increment) {  //for each yx-cell of the marker
				int cellX = x*CELL_LENGTH;
				//apply openCV countNonZero to obtain the number of non zero elements
				int count_result = countNonZero(img_marker(Rect(cellX, cellY, CELL_LENGTH, CELL_LENGTH)));
				if (count_result > CELL_LENGTH*CELL_LENGTH / 4) goto marker_fail;
			}
		}

		//read the marker code
		for (int y = 0; y < 5; ++y) {     //for each row
			int cellY = (y + 1)*CELL_LENGTH;

			for (int x = 0; x < 5; ++x) { //for each col
				int cellX = (x + 1)*CELL_LENGTH;
				//apply openCV countNonZero to obtain the number of non zero elements
				int count_result = countNonZero(img_marker(Rect(cellX, cellY, CELL_LENGTH, CELL_LENGTH)));
				//conver the marker code into binary matrix
				if (count_result > CELL_LENGTH*CELL_LENGTH / 2)
					marker_binary_matrix.at<uchar>(y, x) = 1;
				else
					marker_binary_matrix.at<uchar>(y, x) = 0;
			}
		}

		//obtain the marker orientation
		bool valid_marker = false;
		int rot_counter; 
		for (rot_counter = 0; rot_counter < 4; ++rot_counter) {// loop 4 orientation	
			if (orientation(marker_binary_matrix) == 0){
				valid_marker = true;
				break;
			}
			marker_binary_matrix = rotate_matrix(marker_binary_matrix);
		}
		//fail to find right orientation
		if (!valid_marker) goto marker_fail; 

		//decode the marker identification
		Marker& true_marker = marker_found[i];
		true_marker.marker_id = decodeID(marker_binary_matrix);
		//rotate the marker to right orientation
		std::rotate(true_marker.marker_corners.begin(), true_marker.marker_corners.begin() + rot_counter, true_marker.marker_corners.end());
		//store to marker vector
		true_markers.push_back(true_marker);

		marker_fail: continue;
	}
}

vector<Marker>& MarkerDetector::get_marker() {
	return marker_result;
}

//decode the marker identification
int MarkerDetector::decodeID(Mat& input_matrix) {
	int matrix_id = 0;   //initialize the id
						 //for each row
	for (int r = 0; r < 5; ++r) {
		matrix_id <<= 1;        //left bit shift
		matrix_id |= input_matrix.at<uchar>(r, 1); //insert second row of bit value

		matrix_id <<= 1;        //left bit shift
		matrix_id |= input_matrix.at<uchar>(r, 3); //insert fourth row of bit value
	}
	return matrix_id;
}

//rotate matrix by counterclockwise
Mat MarkerDetector::rotate_matrix(cv::Mat& input_matrix) {
	Mat result = input_matrix.clone();    //get real copy
	int r = input_matrix.rows;
	int c = input_matrix.cols;
	//rotate by counterclockwise
	for (int i = 0; i<r; ++i) {       //for each row
		for (int j = 0; j<c; j++) {   //for each col
			result.at<uchar>(i, j) = input_matrix.at<uchar>(c - j - 1, i);
		}
	}
	return result;
}

//find the orientation
int MarkerDetector::orientation(Mat& input_matrix) {
	const int codex[4][5] = {
		{ 1,0,0,0,0 },	// 00
		{ 1,0,1,1,1 },	// 01
		{ 0,1,0,0,1 },	// 10
		{ 0,1,1,1,0 }	// 11
	};

	int result = 0;
	//for each row
	for (int y = 0; y < 5; ++y) {
		int minimum_sum = INT_MAX;
		for (int p = 0; p < 4; ++p) {
			int m_sum = 0;
			//match the input_matrix with the codex, sum up the result
			for (int x = 0; x < 5; ++x) {
				m_sum += !(input_matrix.at<uchar>(y, x) == codex[p][x]); //return 1 if fail
			}
			minimum_sum = min(minimum_sum, m_sum);
		}
		//sum up each loop result
		result += minimum_sum;
	}
	return result;  //expect 0 result which means all match
}

//refine marker corners
void MarkerDetector::refine_mraker(cv::Mat& input_img, vector<Marker>& true_marker) {
	//for each true marker
	for (int i = 0; i < true_marker.size(); ++i) {
		vector<Point2f>& corner_vec = true_marker[i].marker_corners;
		//refines the corner locations by calling openCV cornerSubPix()
		cornerSubPix(input_img, corner_vec, Size(5, 5), Size(-1, -1), TermCriteria(CV_TERMCRIT_ITER, 30, 0.1));
	}
}



//Marker class
Marker::Marker() {
	//initialize marker id
	marker_id = -1;
	marker_corners.resize(4, Point2f(0.f, 0.f));
}

//construct marker object
Marker::Marker(int input_id, cv::Point2f input_0, cv::Point2f input_1, cv::Point2f input_2, cv::Point2f input_3) {
	marker_id = input_id;
	marker_corners.reserve(4);
	marker_corners.push_back(input_0);
	marker_corners.push_back(input_1);
	marker_corners.push_back(input_2);
	marker_corners.push_back(input_3);
}

//draw the result into camera image
void Marker::drawImg(cv::Mat& img, float t, vector<Point2f> imgpts) {
	//calculate the center coordinate of marker
	Point center_point = marker_corners[0] + marker_corners[2];
	center_point.x /= 2;
	center_point.y /= 2;

	//draw the 3d-axis
	line(img, center_point, imgpts[0], cv::Scalar(255, 0, 0), 3);
	line(img, center_point, imgpts[1], cv::Scalar(0, 255, 0), 3);
	line(img, center_point, imgpts[2], cv::Scalar(0, 0, 255), 3);

	//draw marker borders
	line(img, marker_corners[0], marker_corners[1], cv::Scalar(100, 100, 255), t, CV_AA);
	line(img, marker_corners[1], marker_corners[2], cv::Scalar(100, 100, 255), t, CV_AA);
	line(img, marker_corners[2], marker_corners[3], cv::Scalar(100, 100, 255), t, CV_AA);
	line(img, marker_corners[3], marker_corners[0], cv::Scalar(100, 100, 255), t, CV_AA);

	//draw marker id
	stringstream myText;
	myText << marker_id;
	putText(img, myText.str(), center_point, FONT_HERSHEY_SIMPLEX, 0.5, cv::Scalar(100, 100, 255));
}

//calculate the rotation and translation vector
void Marker::marker_solvePnP(vector<Point3f> input_corners, cv::Mat& camera_matrix, cv::Mat& distortion, cv::Mat& rotation, cv::Mat& translation) {
	Mat rotation_vector;
	//call openCV solvePnP() to obtain rotation and translation vector
	bool res = solvePnP(input_corners, marker_corners, camera_matrix, distortion, rotation_vector, translation);
	//apply Rodrigues to set the rotation vector
	Rodrigues(rotation_vector, rotation);
}
