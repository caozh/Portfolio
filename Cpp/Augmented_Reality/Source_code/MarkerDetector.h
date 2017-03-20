#ifndef __MARKER_DETECTOR_H__
#define __MARKER_DETECTOR_H__

#include <vector>
#include <opencv2\core\core.hpp>

class Marker {
public:
	std::vector<cv::Point2f> marker_corners;
	int marker_id;

	Marker();
	Marker(int input_id, cv::Point2f input_0, cv::Point2f input_1, cv::Point2f input_2, cv::Point2f input_3);
	void drawImg(cv::Mat& img, float t, std::vector<cv::Point2f> imgpts);
	void marker_solvePnP(std::vector<cv::Point3f> input_corners, cv::Mat& camera_matrix, cv::Mat& distortion, cv::Mat& rotation, cv::Mat& translation);
};

class MarkerDetector {
public:
	MarkerDetector();
	int startDetect(cv::Mat& input_img, int img_size, int img_length = 10);
	std::vector<Marker>& get_marker();

private:
	std::vector<Marker> marker_result;
	std::vector<cv::Point2f> marker_position;

	void validate_marker(cv::Mat& input_img, std::vector<Marker>& marker_found, std::vector<Marker>& true_marker);
	void find_marker(cv::Mat& img_gray, std::vector<Marker>& marker_found, int min_size, int img_length);
	void refine_mraker(cv::Mat& input_img, std::vector<Marker>& true_marker);
	int decodeID(cv::Mat& input_matrix);
	int orientation(cv::Mat& input_matrix);
	cv::Mat rotate_matrix(cv::Mat& input_matrix);
};
#endif
