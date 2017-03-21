package csci557_project3_;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class Main {
	
	public static String[] fileNameList = {"building","hinge","hinges","keys","pillsetc"};
	
	public static void main(String[] args) throws IOException {
		//process all images
		for (int i=0; i < fileNameList.length; i++){//fileNameList.length
			String fileName=fileNameList[i];
			process(fileName);
		}
	}//end main
	
	public static void process(String fileName) throws IOException{
		//Canny edge detection
		//Parameters: filename, sigma, threshold high, threshold low (fileName,1.0f, 0.5f, 0.1f)
		BufferedImage cannyResult = CannyEdgeDetector.main(fileName, 1.5f, 60f, 30f); 
		//i = 0  (fileName, 1.5f, 60f, 30f);
		//i = 1  (fileName, 0.5f, 30f, 5f);
		//i = 2  (fileName, 1.0f, 30f, 15f);
		//i = 3  (fileName, 1.5f, 60f, 30f); 
		//i = 4	 (fileName, 1.5f, 60f, 30f); 
		
		//Hough transform
		Hough.main(cannyResult,fileName);
	}
}

