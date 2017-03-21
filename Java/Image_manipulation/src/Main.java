package project2;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

//Median filter fails on rectangular pnm image. 
//To process all images, switch the comment in main() function (line 28)
public class Main {
	public static int width,height;
	public static String[] fileNameList = {"building","tire","child","ct_scan","auto"};//
	public static String fileName,writeFileName;
	public static byte[] data;
	public static Image img;
	
	public static void main(String[] args) throws IOException
	{
		//for (int i=0; i < fileNameList.length; i++){
		for (int i=0; i < 1; i++){
			fileName=fileNameList[i];
			process();
		}
	}
	
	//main process
	private static void process() {
			//load image data
			int[][] image_matrix = readFile();
			
			//send input image to functions
			HQ(image_matrix);
			Log(image_matrix, 5); //constant c for log function
			Rotation(image_matrix, 45); //degree
	        Gaussian(image_matrix, 20, 5, 49); //noise, sigma, size
	        Median(image_matrix, 3);//0 < noise < 10 , salt-pepper-noise
	}
	
	public static int[][] readFile(){
		try{
			//load image data
			PNMFileManager pnmFileManager = new PNMFileManager();
			img = pnmFileManager.loadFile("images/"+fileName + ".pnm");
			data = img.getData();
			//load image into 3d array
			int byteIndex = 0;
			width =img.getWidth();
	        height =img.getHeight();
			int[][] image_matrix = new int[height][width];
			for (int i = 0; i < height; i++){
				for (int j = 0; j < width; j++){
					//normalize from [-128, 127] to [0, 255]
					int unsignedValue = data[byteIndex] & 0xff;
					image_matrix[i][j]=unsignedValue;
					byteIndex++;
				}
			}
			//log2d(image_matrix);
			log("Width: "+width+"\nHeight: "+height+"\nbyteIndex 'pixel': "+byteIndex);
			log("finish loading image into matrix");
			return image_matrix;
		}catch (Exception e){	
			log(e);
			return null;
			}
		
	}
	
	//Histogram Equalization
	public static void HQ( int[][] image_matrix) {
        int area= width*height;
        int[] histogram = new int[256];
        //initialize
        for(int i=0; i < histogram.length; i++) histogram[i] = 0;
        
        //load gray scale values into histogram
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int valueBefore = image_matrix[i][j];//
                histogram[valueBefore]++;
            }
        }
        
        // lookup table
        int sum =0;
        float[] lookUp = new float[256];
        for (int i=0; i < lookUp.length; i++) lookUp[i] = 0;
        for (int i=0; i < histogram.length; i++ ){
            sum += histogram[i];
            lookUp[i] = sum * 255 / area;
        }

        // transform image using sum histogram as a Lookup table
        int[][] image_matrix_result = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
            	image_matrix_result[i][j]=(int) lookUp[image_matrix[i][j]];
            }
        }
	    //write file
        writeFileName = fileName + "_HQ";
        writePnm(writeFileName, image_matrix_result, data, img);
        
	}
	
	//Log transform
	public static void Log(int[][] image_matrix, double c) {
		double tmp = 0;
		int[][] image_matrix_log = new int[height][width];
		
		for (int i = 0; i < height; i++) {
		for (int j = 0; j < width; j++) {
		tmp = Math.log(image_matrix[i][j]+1);
		int tmp2 = (int)(tmp * c);
		if(tmp2 > 255) {
		image_matrix_log[i][j] = 255;
		}
		else image_matrix_log[i][j] = tmp2;
		}
		}
		
		//write file
		writeFileName = fileName + "_log";
		writePnm(writeFileName, image_matrix_log, data, img);
	}
	
	//Rotation
	public static void Rotation(int[][] image_matrix, double degree) {
        int iw = width; int ih = height;
        int w = 0;  int h = 0;  int x = 0;  int y = 0; 
        //rotation theta
        degree = degree % 360; 
        if (degree < 0)  
            degree = 360 + degree;
        double angle = Math.toRadians(degree);
        
        if (degree == 180 || degree == 0 || degree == 360) {  
            w = iw;  
            h = ih;  
        } else if (degree == 90 || degree == 270) {  
            w = ih;  
            h = iw;  
        } else {  
            int d = iw + ih;  
            w = (int) (d * Math.abs(Math.cos(angle)));  
            h = (int) (d * Math.abs(Math.sin(angle))); 
            if (w>h) h=w;
            else w=h;
        }  
        
        x = (w / 2) - (iw / 2);
        y = (h / 2) - (ih / 2);  
        
        BufferedImage rotateBuffer = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster rotateRaster = rotateBuffer.getRaster();
        
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
            	rotateRaster.setSample(i,j,0,image_matrix[j][i]); 
            }
        }        
        
        BufferedImage rotatedImage = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_GRAY); 
        Graphics2D gs = (Graphics2D)rotatedImage.getGraphics();  
        gs.setColor(Color.black);  
        gs.fillRect(0, 0, w, h);
        
        AffineTransform at = new AffineTransform();  
        at.rotate(angle, w / 2, h / 2);
        at.translate(x, y);  
        AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BICUBIC);  
        op.filter(rotateBuffer, rotatedImage);  
        rotateBuffer = rotatedImage;  
        
        //write jpg file
        writeFileName = fileName+"_Rotate";
        writeJpg(writeFileName, rotateBuffer);
	}
	
    //Gaussian filter
	public static void Gaussian(int[][] image_matrix, double noise, double sigma, int size) {
        //Gaussian Noise
        //return a  2d array: image_matrix_gaussian[][]
        double tmp = 0;
        int[][] image_matrix_gaussian = new int[height][width];
        Random myRandom = new Random();
        //add gaussian value to each pixel
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
            	//set SD as 10
            	tmp = myRandom.nextGaussian()*noise;
            	int tmp2 = (int)(tmp + image_matrix[i][j]);
            	if(tmp2 > 255) {
            		image_matrix_gaussian[i][j] = 255;
                }
                else image_matrix_gaussian[i][j] = tmp2;
            }
        }
        writeFileName = fileName + "_Gaussian_Noise";
        writePnm(writeFileName, image_matrix_gaussian, data, img);
        
        //load 2d array image into bufferedImage
        BufferedImage GaussianBuffer = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster gaussianRaster = GaussianBuffer.getRaster();
        //load values to bufferedImage
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
            	gaussianRaster.setSample(i,j,0,image_matrix_gaussian[j][i]);//image_matrix[j][i]); 
            }
        }     
        //run gaussian filter   (input img, sigma, kernal size)
        GaussianBuffer = gaussianFilter(GaussianBuffer,sigma,size);
        
        //write jpg file
        writeFileName = fileName + "_Gaussian";
		writeJpg(writeFileName, GaussianBuffer);
	}
	
	//Median filter
	public static void Median(int[][] image_matrix, double noise) {
        int[][] image_matrix_median = new int[height][width];
        
        //Create salt and pepper noise
        //add random value to each pixel
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
            	Random randomMedian = new Random();
            	int value = randomMedian.nextInt(10+1);
            	if (value<noise) 
            		if(value<noise*.5) 
            			image_matrix_median[i][j] = 255;
            		else 
            			image_matrix_median[i][j] = 0;
            	else
            		image_matrix_median[i][j] = image_matrix[i][j];
            }
        }
        writeFileName = fileName + "_Median_Noise";
        writePnm(writeFileName, image_matrix_median, data, img);
        
        int[] filter; //the array that gets the pixel value at (x, y) and its neightbors
        int[][] image_matrix_median_result = new int[height][width];
        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++) {
            	//create the filter box (img, dimension, x, y)
            	filter = filterArray(image_matrix_median,3,i,j);
                //find the median for each color
                image_matrix_median_result[i][j] = findMedian(filter);
            }
        }
        
        writeFileName = fileName + "_Median";
        writePnm(writeFileName, image_matrix_median_result, data, img);
        
	}
		
    //write jpg file
	public static void writeJpg(String fileName, BufferedImage input){
		File file = new File("images/result/"+fileName+".jpg");
		try {
			ImageIO.write(input, "jpg", file);
			System.out.println( "Writing binary file named : images/result/"+fileName+".jpg");
		} catch (IOException e) {
			e.printStackTrace();
		}      
	}
		
	//write pnm file
	public static void writePnm(String fileName, int[][] input, byte[] data, Image img){
	    //write pnm file
        int byteIndex=0;
		for (int i=0;i<height;i++){
			for (int j=0;j<width;j++){
				data[byteIndex]=(byte) (input[i][j]);
				byteIndex++;
			}
		}			
	    img.setData(data);
	    PNMFileManager pnmFileManager = new PNMFileManager();
	    try {
			pnmFileManager.storeFile("images/result/"+fileName+".pnm", img);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//print log 
	private static void log(Object msg){
		System.out.println(String.valueOf(msg));
	}
	
	//Gaussian process
	public static BufferedImage gaussianFilter(BufferedImage myImage, double sigma, int size){
		int width =myImage.getWidth();
        int height =myImage.getHeight();
		//get raster pixel data
		WritableRaster raster = myImage.getRaster();
		byte[] dstBuff = ((DataBufferByte) myImage.getRaster().getDataBuffer()).getData();
        
		double[] kernel = myKernel(sigma, size);
		//for (int i =0;i<kernel.length;i++) System.out.println(kernel[i]);
		//loop for each pixel
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				double overflow = 0;
				int counter = 0;
				int subsize = (size - 1)/2;
				double value = 0;
				//double test = 0;
				//loop for the filter
				for(int k = i - subsize; k < i + subsize; k++){
					for(int l = j - subsize; l < j + subsize; l++){
						//if( l >= height || k >= width || k < 0 ||  l < 0 ){
					    if(k < 0 || k >= myImage.getWidth() || l < 0 || l >= myImage.getHeight()){
							counter++;
							overflow += kernel[counter];
							continue;
						}
						//test
						float oldValue = dstBuff[k+l*width] & 0xFF;
						//test = oldValue;
						value += oldValue * kernel[counter];
						//System.out.println("Old: " +test+" New: "+value);
						counter++;
					}// end for l
					counter++;
				}//end for k
				
				if(overflow > 0){
					value = 0;
					counter = 0;
					for(int k = i - subsize; k < i + subsize; k++){
						for(int l = j - subsize; l < j + subsize; l++){
							if(k < 0 || k >= width || l < 0 || l >= height){
								counter++;
								continue;
							}
							//test
							float oldValue = dstBuff[k+l*width] & 0xFF;
							//test = value;
							value += oldValue * kernel[counter]*(1/(1-overflow));
							counter++;
							//System.out.println("Old: " +test+"New: "+value);
						}
						counter++;
					}//end for k
				}
				raster.setSample(i,j,0,(int)value); 
			}// end for j
		}// end for i
		return myImage;
        
	}
	
	//Gaussian filter
	public static double[] myKernel(double sigma, int size){
		//setup filter values
		double[] kernel = new double[size*size];
		for(int i = 0;  i < size; i++){
			double x = i - (size -1) / 2;
			for(int j = 0; j < size; j++){
				double y = j - (size -1)/2;
				kernel[j + i*size] = 1 / (2 * Math.PI * sigma * sigma) * Math.exp(-(x*x + y*y) / (2 * sigma *sigma));
			}
		}
		float sum = 0;
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				sum += kernel[j + i*size];
			}
		}
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				kernel[j + i*size] /= sum;
			}
		}
		return kernel;
	}// end myKernel()

	//Median filter array
    public static int[] filterArray(int[][] image, int size, int center_x, int center_y){
        //detect the boundaries
        int xmin = center_x - size/2;
        int xmax = center_x + size/2; 
        int ymin = center_y - size/2; 
        int ymax = center_y + size/2;
        
        //border cases
        if (xmin < 0)
            xmin = 0;
        if (xmax > (width - 1))
            xmax = width - 1;
        if (ymin < 0)
            ymin = 0;
        if (ymax > (height - 1))
            ymax = height - 1;
        
        //load pixel values into array
        int area = Math.abs((xmax-xmin+1)*(ymax-ymin+1));
        int[] filter = new int[area];
        int counter = 0;
        for (int i = xmin; i <= xmax; i++)
            for (int j = ymin; j <= ymax; j++){
            	//load pixel values
            	filter[counter] = image[i][j]; 
            	counter++;
            }
        return filter;
    }

    //find the median of array
    public static int findMedian(int[] arr) {
    	if (arr.length == 0) 
    		return 0;
        int temp;
        int size = arr.length;
        //sort the array in ascending order
        for (int i = 0; i < size ; i++)
            for (int j = i+1; j < size; j++)
                if (arr[i] > arr[j]) {
                    temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
        //decide even or odd
        if (size%2 == 1)
            return arr[size/2];
        else
        	//log(arr.length);
            return ((arr[size/2]+arr[size/2 - 1])/2);
    }

}


