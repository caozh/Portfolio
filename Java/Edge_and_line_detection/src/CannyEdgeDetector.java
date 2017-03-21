package csci557_project3_;

import java.awt.image.BufferedImage;
import java.util.ArrayDeque;

class CannyEdgeDetector {
	
	public static String[] fileNameList = {"building","hinge","hinges","keys","pillsetc"};
	public static String fileName_local;
	//public static String fileName;
	
	public static BufferedImage main(String fileName, float sigma, float th, float tl) {
		fileName_local = fileName;
		ImageMatrix m = new ImageMatrix(fileName);
		
		ImageMatrix m2 = canny(m, sigma, th, tl); 
		BufferedImage result = m2.writeJPG(fileName+"_07_hysteresis_final_result");
		//BufferedImage result = null; //testing
		return result;
	}
	
	public static ImageMatrix canny(ImageMatrix image, float sigma, float th, float tl) {
		
		//apply median filter
		ImageMatrix medianResult = null;
		if (fileName_local.equals("keys")){
			System.out.println("median filter");
			medianResult = image.medianFilter(fileName_local);
			medianResult.writeJPG_testing(fileName_local+"_00_medianFilter");
	    } else {
	    	medianResult = new ImageMatrix(image);
	    }
		
		//apply Gaussian filter
		ImageMatrix gaussianResult = medianResult.performFilter(gaussianKernel(sigma));
		//writing gaussian result 
		gaussianResult.writeJPG_testing(fileName_local+"_01_Gaussian");
		//find gradient, quantization, nms, hysteresis 
		ImageMatrix result = process(gaussianResult,th,tl);
		return result;
	}
	
	//PrewittMask
	public static ImageMatrix process(ImageMatrix input, float th, float tl) {
		//create Prewitt Mask
		ImageMatrix prewittX = new ImageMatrix(3, 3, 0.0f);
		ImageMatrix prewittY = new ImageMatrix(3, 3, 0.0f);
		for (int i = 1; i <= 3; i++) {
			prewittX.set(i, 1, -1.0f);
			prewittX.set(i, 3, 1.0f);
			prewittY.set(1, i, -1.0f);
			prewittY.set(3, i, 1.0f);
		}
		
		//process the gradients
		ImageMatrix X = input.performFilter(prewittX); 
		ImageMatrix Y = input.performFilter(prewittY);
		X.writeJPG_testing(fileName_local+"_02_gradien_X");
		Y.writeJPG_testing(fileName_local+"_03_gradien_Y");
		
		//calculate the angle of gradients
		ImageMatrix angleGradient = ImageMatrix.atan2(Y, X);
		angleGradient.writeJPG_testing(fileName_local+"_04_gradien_angle");
		//quantization
		ImageMatrix quantization = quantization(angleGradient);
		//gradient magnitude 
		ImageMatrix magnitude = X.multiplyArray(X).add(Y.multiplyArray(Y)).performMethod(ImageMatrix.operator("sqrt"));
		magnitude.writeJPG_testing(fileName_local+"_05_gradien_magnitude");
		//log(M);
		//apply the non-maximum suppression
		ImageMatrix nms = NMS(magnitude,quantization);
		nms.writeJPG_testing(fileName_local+"_06_non-maximum");
		//apply the hysteresis thresholding
		ImageMatrix result = hysteresis(nms, th, tl); 
				
		return result;
	}
	
	//hysteresis
	private static ImageMatrix hysteresis(ImageMatrix input, float th, float tl) {
		int mx = input.getColumnNum();
		int my = input.getRowNum();
		//pixel index
		int index;
		//candidate index
		int candidateIndex; 
		//points to validate
		ArrayDeque<Point> candidates = new ArrayDeque<Point>(100000); 
		ImageMatrix output = new ImageMatrix(my, mx, 0.0f);
		//loop through the image
		for (int y = 0; y < my; y++) {
			for (int x = 0; x < mx; x++) {
				index = y * mx + x + 1;
				if ((input.get(index) >= th) && (output.get(index) != 1.0f)) {
					//accepted candidate
					candidates.addLast(new Point(x,y));
					// Process all candidates recursively through holding queue
					while(!candidates.isEmpty()) {
						Point candidate = candidates.pollFirst();
						//pass invalid points
						if ((candidate.x < 0) || (candidate.x >= mx) || (candidate.y < 0) || (candidate.y >= my)) 
							continue;
						//find the candidate pixel index 
						candidateIndex = candidate.y * mx + candidate.x + 1;
						//abandon those below low-threshold
						if (input.get(candidateIndex) < tl) 
							continue;
						//if pixel not visited yet, go visit neighbor pixels
						if (output.get(candidateIndex) != 1.0f) {
							//set pixel as edge
							output.set(candidateIndex, 1.0f); 
							//visit all neighbors
							candidates.addLast(new Point(candidate.x-1,candidate.y+1));
							candidates.addLast(new Point(candidate.x+1,candidate.y));
							candidates.addLast(new Point(candidate.x,candidate.y+1));
							candidates.addLast(new Point(candidate.x+1,candidate.y+1));
							candidates.addLast(new Point(candidate.x,candidate.y-1));
							candidates.addLast(new Point(candidate.x-1,candidate.y-1));
							candidates.addLast(new Point(candidate.x+1,candidate.y-1));
							candidates.addLast(new Point(candidate.x-1,candidate.y));
						}//end if
					}//end while
				}//end if 
			}//end for x
		}//end for y
		return output;
	}
	
	//define a Point class
	private static class Point {
		public int x,y;
		public Point(int x, int y) {
			this.x = x; this.y = y;
		}
	}
	
	//non-maximum suppression
	private static ImageMatrix NMS(ImageMatrix input, ImageMatrix sectors) {
		ImageMatrix output = new ImageMatrix(input);
		//neighbor coordinates
		int n1x, n1y, n2x, n2y, idx1, idx2; 
		//max X coordinate
		int mx = input.getColumnNum(); 
		//max Y coordinate
		int my = input.getRowNum(); 
		//pixels
		float k, n1, n2; 
		//loop all pixels
		for (int y = 0; y < my; y++) {
		    for (int x = 0; x < mx; x++) {
		    	int idx = y * mx + x + 1;
		    	//get gradient direction for comparison
		    	k = sectors.get(idx);
		    	//extract neighbors depending on gradient direction
		        if (k == 1.0f) {
		        	//horizontal
		            n1x = x-1; n1y = y; n2x = x+1; n2y = y;
		        } else if (k == 2.0f) {
		        	//diagonal upper Right
		            n1x = x+1; n1y = y+1; n2x = x-1; n2y = y-1;
		        } else if (k == 3.0f) { 
		        	//vertical
		            n1x = x; n1y = y+1; n2x = x; n2y = y-1;
		        } else {
		        	//diagonal upper Left
		            n1x = x-1; n1y = y+1; n2x = x+1; n2y = y-1;
		        }
		        //first neighbor
		        if ((n1x < 0) || (n1x >= mx) || (n1y < 0) || (n1y >= my)) {
		        	output.set(idx, 0.0f);
		            continue;
		        } else {
		        	idx1 = n1y * mx + n1x + 1;
		            n1 = input.get(idx1);
		        }
		        //second neighbor
		        if ((n2x < 0) || (n2x >= mx) || (n2y < 0) || (n2y >= my)) {
		        	output.set(idx, 0.0f);
		            continue;
		        } else {
		        	idx2 = n2y * mx + n2x + 1;
		            n2 = input.get(idx2);
		        }
		        
		        //keep local maximum
		        k = input.get(idx);
		        if (!((k >= n1) && (k >= n2))) {
		        	output.set(idx, 0.0f);
		        }
		        if (x == 0) System.out.println(k);
		    }
		}
		return output;
	}
	
	private static ImageMatrix quantization(ImageMatrix input) {
		ImageMatrix output = new ImageMatrix(input.getRowNum(), input.getColumnNum());
		int length = input.getRowNum()*input.getColumnNum();
		float pi = (float)Math.PI;
		//loop for all pixels
		for (int i = 1; i <= length; i++) {
		    float k = input.get(i);
		    //vertical 2 quadrants symmetry
		    if (k < 0.0)
		        k = pi + k;
		    // Reassignment by table 
		    if (k <= (pi/8))
		    	//horizontal
		    	output.set(i, 1.0f); 
		    else if (k <= (3*pi / 8))
		    	//upper right diagonal
		    	output.set(i, 2.0f); 
		    else if (k <= (5*pi / 8))
		    	//vertical
		    	output.set(i, 3.0f); 
		    else if (k <= (7*pi / 8))
		    	//upper left diagonal
		    	output.set(i, 4.0f); 
		    else
		    	//vertical
		    	output.set(i, 1.0f); 
		}
		return output;
	}
	
	public static ImageMatrix gaussianKernel(float sigma) {
		//calculate the Gaussian kernel size
		int n = 2 * Math.round((float)Math.sqrt(-Math.log(0.1) * 2.0 * (sigma*sigma)))+1;
		//Initiate mask filter
		ImageMatrix mask = new ImageMatrix(n, n, 0.0f);

		//calculate Gaussian distribution on impulse
		for (int i = -((n-1)/2); i <= ((n-1)/2); i++) {
		    for (int j = -((n-1)/2); j <= ((n-1)/2); j++) {
		    	mask.set((n+1)/2+i,(n+1)/2+j, (float)Math.exp(-((float)((i*i)+(j*j))/(2.0f*(sigma*sigma)))));
		    }
		}
		//Normalization
		mask = mask.divide(mask.sum());
		//log(mask);//testing
		return mask;
	}
	
	public static void log(ImageMatrix input){
		int c = input.getColumnNum();
		int r = input.getRowNum();
		//System.out.print("col: "+c+" row: "+r);
		for (int i=0; i<r; i++){
			for (int j=0; j<c; j++){
				System.out.print(input.get(i+1,j+1));
			}
			System.out.printf("\n");
		}
	}
}
