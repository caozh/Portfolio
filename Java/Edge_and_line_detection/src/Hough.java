package csci557_project3_;

import java.io.File;
import javax.imageio.*;
import java.io.IOException;
import java.awt.image.*;
 
public class Hough {
	public static void main(BufferedImage inputImage,String filename) throws IOException{
		//convert the input image into my defined Hough array for calculation convenience
		ArrayHough myArray = convertData(inputImage);
		//apply Hough transform
		ArrayHough result = houghTransform(myArray, 320, 240);//(input, width, height)
		writeImage(filename, result);
	}

	//convert input image data into ArrayHouse array
	public static ArrayHough convertData(BufferedImage input) throws IOException{
	    int width = input.getWidth();
	    int height = input.getHeight();
	    ArrayHough arrayData = new ArrayHough(width, height);
		WritableRaster raster=input.getRaster();
	    int[] iArray=new int[1];
	    for (int y = 0; y < height; y++){
	    	for (int x = 0; x < width; x++){
	    		raster.getPixel(x, y, iArray);
	    	    //flip y 
	    		arrayData.set(x, height - y - 1, iArray[0]);
	    	}
	    }
	    return arrayData;
	}
	
	//Hough transform
	public static ArrayHough houghTransform(ArrayHough myArray, int thetaSize, int rSize){
	    int height = myArray.height;
	    int width = myArray.width;
	    //max radius
	    int maxR = (int)Math.ceil(Math.hypot(width, height));
	    //bit operation, take half
	    int halfSize = rSize >>> 1;
	    //initialize output array
	    ArrayHough result = new ArrayHough(thetaSize, rSize);
	    // x output ranges from 0 to pi
	    // y output ranges from -maxRadius to maxRadius
	    double[] cos = new double[thetaSize];
	    double[] sin = new double[thetaSize];
	    for (int i=thetaSize-1; i>=0; i--){
	    	double radiansTheta = i * Math.PI / thetaSize;
	    	cos[i] = Math.cos(radiansTheta);
	    	sin[i] = Math.sin(radiansTheta);
	    }
	    for (int y=height-1; y>=0; y--){
	    	for (int x=width-1; x>= 0; x--){
	    		if (myArray.contrast(x, y)){
	    			for (int i=thetaSize-1; i>=0; i--){
	    				double val = cos[i] * x + sin[i] * y;
	    				int scaled = (int)Math.round(val * halfSize / maxR) + halfSize;
	    				result.accumulate(i, scaled, 1);
	    			}
	    		}
	    	}
	    }
	    return result;
	}
 
	//create a Hough array class (1D)
	public static class ArrayHough{
	    public final int[] myArray;
	    public final int width;
	    public final int height;
	 
	    public ArrayHough(int w, int h){
	      this(new int[w * h], w, h);
	    }
	 
	    public ArrayHough(int[] input, int w, int h){
	      this.myArray = input;
	      this.width = w;
	      this.height = h;
	    }
	 
	    public int get(int x, int y){  
	    	return myArray[y * width + x];  
	    }
	 
	    public void set(int x, int y, int v){  
	    	myArray[y * width + x] = v;  
	    }
	 
	    public void accumulate(int x, int y, int v){  
	    	set(x, y, get(x, y) + v);  
	    }
	 
	    public boolean contrast(int x, int y){
	    	int contrast = 70;
	    	//center
	    	int c = get(x, y);
	    	for (int i = 8; i >= 0; i--){
	    		if (i == 4)
	    			continue;
	    		int nx = x + (i % 3) - 1;
	    		int ny = y + (i / 3) - 1;
	    		if ((nx < 0) || (nx >= width) || (ny < 0) || (ny >= height))
	    			continue;
	    		if (Math.abs(get(nx, ny) - c) >= contrast)
	    			return true;
	    	}
	    	return false;
	    }
	 
	    public int max() {
	    	int m = myArray[0];
	    	for (int i = width * height - 1; i > 0; i--)
	    		if (myArray[i] > m)
	    			m = myArray[i];
	    	return m;
	    }
	}
	
	//output image
	public static void writeImage(String filename, ArrayHough arrayData) throws IOException {
		int max = arrayData.max();
		BufferedImage out = new BufferedImage(arrayData.width, arrayData.height, BufferedImage.TYPE_BYTE_GRAY);
		for (int y = 0; y < arrayData.height; y++){
			for (int x = 0; x < arrayData.width; x++){
				//back projection
				int n = Math.min((int)Math.round(arrayData.get(x, y) * 255.0 / max), 255);
				out.setRGB(x, y, (n << 16) | (n << 8) | 0x90 | -0x01000000);
			}
		}
		ImageIO.write(out, "JPG", new File("images/result/Hough_"+filename+".jpg"));
		return;
	}
}
