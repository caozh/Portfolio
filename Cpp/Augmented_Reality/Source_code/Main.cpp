#include <opencv2/core/core.hpp>
#include <opencv2/highgui/highgui.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <opencv2/calib3d/calib3d.hpp>
#include <iostream>
#include "MarkerDetector.h"

using namespace cv;

int main() {
	cv::VideoCapture capWebcam(0);		                // initialize camera and select camera 0

	if (capWebcam.isOpened() == false) {				// check if valid
		std::cout << "can not access the webcam\n\n";
		return(0);										// exit program
	}

	cv::Mat imgOriginal;		      // input image
	cv::Mat imgGray;                  // convert to Gray

	MarkerDetector m_detector;        //construct marker detector

	Point3f marker_corners[] ={       //marker corner 3d coordinate
		Point3f(-0.5f, -0.5f, 0),
		Point3f(-0.5f,  0.5f, 0),
		Point3f(0.5f,  0.5f, 0),
		Point3f(0.5f, -0.5f, 0)
	};

	std::vector<Point3f> axis ={       //axis 3d vector
		Point3f(0.5, 0, 0),            //x
		Point3f(0, 0.5, 0),            //y
		Point3f(0, 0, -0.5)            //z
 	};

	float camera_matrix[] = {          //my camera matrix obtain from calibrateCamera()
		848.069f,     0.0f, 268.697f,
		    0.0f, 847.687f, 264.266f,
		    0.0f,     0.0f,     1.0f
	};

	float distortion[] = { -0.445957f, 0.278828f, -0.002213f, -0.000656f };      //distortion coefficients

	std::vector<Point3f> m_marker_corners;
	m_marker_corners = std::vector<Point3f>(marker_corners, marker_corners + 4); //marker corners vector
	cv::Mat m_camera_matrix;
	m_camera_matrix = Mat(3, 3, CV_32FC1, camera_matrix).clone();                //Camera Matrix
	cv::Mat m_distortion;
	m_distortion = Mat(1, 4, CV_32FC1, distortion).clone();                      //Distortion coefficient

	std::vector<Point2f> imgpts;     //declear my axis projection points
	//float m_model_view_matrix[16];

	char charCheckForEscKey = 0;
	while (charCheckForEscKey != 27 && capWebcam.isOpened()) {		        //Esc key or webcam lose connection to exit
		bool blnFrameReadSuccessfully = capWebcam.read(imgOriginal);		//load frame

		if (!blnFrameReadSuccessfully || imgOriginal.empty()) {		        // check if frame load successfully
			std::cout << "fail to load frame from webcam\n";		
			break;													
		}

		cv::cvtColor(imgOriginal, imgGray, CV_RGBA2GRAY);                   //convet to gray img
		m_detector.startDetect(imgGray, 100);                                    //load img into marker detector component (input img, marker min-length)

		Mat m_rotation, m_translation;                                                           //declear rotation and tranlation
		std::vector<Marker>& markers = m_detector.get_marker();             //obtain markers from the marker detector component
		for (int i = 0; i < markers.size(); ++i){	                        //for each detected marker	
			//use camera parameters to calculate the rotation and tranlation matrix
			markers[i].marker_solvePnP(m_marker_corners, m_camera_matrix, m_distortion, m_rotation, m_translation);
			//call projectPoints() to project 3d object(axis) into the camera img
			projectPoints(axis, m_rotation, m_translation, m_camera_matrix, m_distortion, imgpts); //return imgpts (projection vector)
			//draw to camera image
			markers[i].drawImg(imgOriginal, 2, imgpts); //(input img, thickness, draw img)
		}

	    // declare windows
		cv::namedWindow("imgOriginal", CV_WINDOW_AUTOSIZE);	
		cv::imshow("imgOriginal", imgOriginal);			// show results
		charCheckForEscKey = cv::waitKey(1);			// waitKey
	}	// end while

	return(0);
}
