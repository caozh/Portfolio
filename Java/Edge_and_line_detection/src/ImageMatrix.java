package csci557_project3_;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import javax.imageio.ImageIO;

public class ImageMatrix {
	
	private float [] data;
	private int nRows;
	private int nCols;
	public Image img;
	public byte[] data2;
	
	public ImageMatrix(int nRows, int nCols) {
    	data = new float[nRows * nCols];
        this.nRows = nRows;
        this.nCols = nCols;
        
    }
	
	public ImageMatrix(int nRows, int nCols, float input) {
    	this(nRows, nCols);
    	setColor(input);
    }
	
	public void setColor(float value) {
    	for (int i = 0; i < data.length; i++) {
    		data[i] = value;
    	}
    }
	
	public ImageMatrix(String filename) {
		data = readFile(filename);
    }
	
	public ImageMatrix(ImageMatrix input) {
    	this.nRows = input.getRowNum();
    	this.nCols = input.getColumnNum();
    	data = Arrays.copyOf(input.getArray(), nRows * nCols);
    }
	
	//retrieve value from data array
	public float get(int i) {
    	return data[i - 1];
    }
	
	//get value by 2d index
	public float get(int r, int c) {
    	return data[(r-1)*nCols+(c-1)];
    }
	
	public int getRowNum() { 
		return nRows; 
	}
	
	public int getColumnNum() { 
		return nCols; 
	}
	
	public float[] readFile(String filename){
		try{
			//load image data
			PNMFileManager pnmFileManager = new PNMFileManager();
			img = pnmFileManager.loadFile("images/"+filename + ".pnm");
			data2 = img.getData();
			//load image into 3d array
			int byteIndex = 0;
			nCols =img.getWidth();
			nRows =img.getHeight();
			float [] data = new float[nRows*nCols];
			for (int i = 0; i < nRows*nCols; i++){
				//normalize from [-128, 127] to [0, 255]
				int unsignedValue = data2[byteIndex] & 0xff;
				data[i]=unsignedValue;
				byteIndex++;
			}
			//log2d(image_matrix);
			log("Width: "+nCols+" Height: "+nRows+"\nbyteIndex 'pixel': "+byteIndex);
			log("finish loading image into matrix");
			return data;
		}catch (Exception e){	
			log(e);
			return null;
			}
	}
	
	//set the value into data array
	public void set(int i, float v) {
    	data[i - 1] = v;
    }
	
	//set value by 2d index
	public void set(int r, int c, float v) {
		data[(r-1)*nCols+(c-1)] = v;
    	
    }
	
	//summation operation
    public float sum() {
    	double sum = 0.0;
    	//summation
    	for (int i = 0; i < data.length; i++) {
    		sum += data[i];
    	}
    	return (float)sum;
    }
    
    //retrieve this. data
    float[] getArray() {
    	return data;
    }
    
    //return the math operation
    static public Method operator(String input) {
    	return floatOperator(Math.class, input);
    }
    
	@SuppressWarnings("rawtypes")
	static public Method floatOperator(Class<Math> inputClass, String name) {
    	try {
		    Class[] tmp = new Class[1];
		    tmp[0]=Double.TYPE;
		    Method m = inputClass.getMethod(name, tmp);
		    return m;
      	} catch(Exception e) {
      		e.printStackTrace();
      		return null;
      	}
    }
	
	//divide operation
    public ImageMatrix divide(float divisor) {
    	ImageMatrix output = new ImageMatrix(this);
    	float [] outputArray = output.getArray();
    	for (int i = 0; i < data.length; i++) {
    		outputArray[i] = data[i] / divisor;
    	}
    	return output;
    }
    
    //multiply operation
    public ImageMatrix multiplyArray(ImageMatrix matrix) {
    	ImageMatrix output = new ImageMatrix(this);
    	float [] outputArray = output.getArray();
    	float [] tmp = matrix.getArray();
    	//check
    	if (tmp.length != data.length)
    		throw new IllegalArgumentException("Dimensions error");
    	//multiply
    	for (int i = 0; i < data.length; i++) {
    		outputArray[i] = data[i] * tmp[i];
    	}
    	return output;
    }
    
    //arrays addition
    public ImageMatrix add(ImageMatrix matrix) {
    	ImageMatrix output = new ImageMatrix(this);
    	float [] outputArray = output.getArray();
    	float [] tmp = matrix.getArray();
    	//check
    	if (tmp.length != data.length)
    		throw new IllegalArgumentException("Dimensions error");
    	//add values
    	for (int i = 0; i < data.length; i++) {
    		outputArray[i] = data[i] + tmp[i];
    	}
    	return output;
    }
    
    //add a float value to array
    public ImageMatrix add(float v) {
    	ImageMatrix output = new ImageMatrix(this);
    	float [] outputArray = output.getArray();
    	//apply addition to each value in data array
    	for (int i = 0; i < data.length; i++) {
    		outputArray[i] = data[i] + v;
    	}
    	return output;
    }
    
    public ImageMatrix performMethod(Method floatMethod) {
    	ImageMatrix output = new ImageMatrix(this);
    	float [] outputArray = output.getArray();
    	Float [] input = new Float[1];
    	try {
	    	for (int i = 0; i < data.length; i++) {
	    		input[0] = Float.valueOf(data[i]);
	    		outputArray[i] = ((Double)(floatMethod.invoke(this, (Object [])input))).floatValue();
	    	}
    	} catch (Exception e) {
    		e.printStackTrace();
    		output.setColor(Float.NaN);
    	}
    	return output;
    }
    
    //perform gaussian on the kernel matrix
    public ImageMatrix performFilter(ImageMatrix kernel) {
    	float sum;
    	int nx, ny; // New point coordinates
    	int lowx, lowy, highx, highy, cx, cy; // Traversal limits
    	int kernelWidth;
    	int x, y, i, j; // Indices
    	//System.out.print("nCols: "+nCols+" nRows: "+nRows+"\n");
    	ImageMatrix output = new ImageMatrix(nRows, nCols);
    	float [] outputArray = output.getArray();
    	float [] tmp = kernel.getArray();
    	//traversal indices for summation
    	if ((kernel.getColumnNum() & 0x01) == 0x01) {
    		highx = (kernel.getColumnNum() - 1) / 2;
    		lowx = -highx;
    	} else {
    		highx = kernel.getColumnNum() / 2;
    		lowx = -highx + 1;
    	}
    	//center offset
    	cx = -lowx; 
    	kernelWidth = kernel.getColumnNum();
    	if ((kernel.getRowNum() & 0x01) == 0x01) {
    		highy = (kernel.getRowNum() - 1) / 2;
    		lowy = -highx;
    	} else {
    		highy = kernel.getRowNum() / 2;
    		lowy = -highx + 1;
    	}
    	cy = -lowy;
    	//loop for all value in the matrix
    	for (y = 0; y < nRows; y++) {
    		for (x = 0; x < nCols; x++) {
    			sum = 0.0f;
    			//kernel summation
    			for (j = lowy; j <= highy; j++) {
    				for (i = lowx; i <= highx; i++) {
    					nx = x + i;
    					ny = y + j;
    					//pass bad pixels
    					if (nx < 0 || nx >= nCols || ny < 0 || ny >= nRows) continue;
    					//accumulate
    					sum += data[ny * nCols + nx] * tmp[(cy + j) * kernelWidth + (cx + i)]; 
    				}//end for i
    			}//end for j
    			outputArray[y * nCols + x] = sum;
    		}///end for x
    	}//end for y
    	return output;
    }
    
    static public ImageMatrix atan2(ImageMatrix input1, ImageMatrix input2) {
    	float [] up = input1.getArray();
    	float [] down = input2.getArray();
    	//check
    	if (up.length != down.length)
    		throw new IllegalArgumentException("Dimensions error");
    	ImageMatrix output = new ImageMatrix(input1.getRowNum(), input2.getColumnNum());
    	float [] outputArray = output.getArray();
    	//atan2
    	for (int i=0; i<up.length; i++) {
    		outputArray[i] = (float)Math.atan2(up[i], down[i]);
    	}
    	return output;
    }
    
    public ImageMatrix medianFilter(String filename){
    	//convert from 1d array to 2d array
    	int convertedData[][] = new int[nRows][nCols];
		for (int row = 0; row < nRows; row++) {
			for (int col = 0; col < nCols; col++) {
				convertedData[row][col]= (int) data[row*nCols + col];
			}
		}
		int[] filter; //the array that gets the pixel value at (x, y) and its neighbors
        int[][] image_matrix_median_result = new int[nRows][nCols];
        for (int i = 0; i < nRows; i++){
            for (int j = 0; j < nCols; j++) {
            	//create the filter box (img, dimension, x, y)
            	filter = filterArray(convertedData,3,i,j);
                //find the median for each color
                image_matrix_median_result[i][j] = findMedian(filter);
            }
        }
        
        //convert 2d array back to 1d array
        ImageMatrix output = new ImageMatrix(nRows, nCols);
        float [] outputArray = output.getArray();
        int counter = 0;
        for (int row = 0; row < nRows; row++) {
			for (int col = 0; col < nCols; col++) {
				//convertedData[row][col]= (int) data[row*nCols + col];
				outputArray[counter] = image_matrix_median_result[row][col];
				counter++;
			}
		}
        
        return output;
    }
    
	//Median filter array
    public int[] filterArray(int[][] image, int size, int center_x, int center_y){
        //detect the boundaries
        int xmin = center_x - size/2;
        int xmax = center_x + size/2; 
        int ymin = center_y - size/2; 
        int ymax = center_y + size/2;
        
        //border cases
        if (xmin < 0)
            xmin = 0;
        if (xmax > (image[0].length - 1))
            xmax = image[0].length - 1;
        if (ymin < 0)
            ymin = 0;
        if (ymax > (image.length - 1))
            ymax = image.length - 1;
        
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
    
    //convert data to 2d image array
    public BufferedImage writeJPG(String filename) {
		//convert from 1d array to 2d array
    	int convertedData[][] = new int[nRows][nCols];
		for (int row = 0; row < nRows; row++) {
			for (int col = 0; col < nCols; col++) {
				convertedData[row][col]= (int) data[row*nCols + col];
			}
		}
		
		//load 2d array image into bufferedImage
        BufferedImage imageBuffer = new BufferedImage(nCols, nRows, BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster gaussianRaster = imageBuffer.getRaster();
        //load values to bufferedImage
        for (int i = 0; i < nCols; i++) {
        	for (int j = 0; j < nRows; j++) {
            	int val= Math.round(convertedData[j][i]);
            	//edge color: white, background color: black
            	if (val > 0) val = 254;
            	else val = 0;
            	gaussianRaster.setSample(i,j,0,val);
            }
        }  
        File file = new File("images/result/Canny_"+filename+".jpg");
		try {
			ImageIO.write(imageBuffer, "jpg", file);
			System.out.println( "Writing binary file named : images/result/Canny_"+filename+".jpg");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return imageBuffer;   
    }
    
    public BufferedImage writeJPG_testing(String filename) {
		//convert from 1d array to 2d array
    	int convertedData[][] = new int[nRows][nCols];
		for (int row = 0; row < nRows; row++) {
			for (int col = 0; col < nCols; col++) {
				convertedData[row][col]= (int) data[row*nCols + col];
			}
		}
		
		//load 2d array image into bufferedImage
        BufferedImage imageBuffer = new BufferedImage(nCols, nRows, BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster gaussianRaster = imageBuffer.getRaster();
        //load values to bufferedImage
        for (int i = 0; i < nCols; i++) {
        	for (int j = 0; j < nRows; j++) {
            	gaussianRaster.setSample(i,j,0,convertedData[j][i]);
            }
        }  
		File file = new File("images/result/Canny_"+filename+".jpg");
		try {
			ImageIO.write(imageBuffer, "jpg", file);
			System.out.println( "Writing binary file named : images/result/Canny_"+filename+".jpg");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return imageBuffer;      
    }
    
	//print log 
	private void log(Object msg){
		System.out.println(String.valueOf(msg));
	}
}

