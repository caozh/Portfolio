package project2;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

public class PNMFileManager {
	  public static void main(String... aArgs) throws IOException
	  {
//	    try
//	    {
//	      PNMFileManager pnmFileManager = new PNMFileManager();
//	      Image img = pnmFileManager.loadFile("images/Lenna.pnm");
//	      pnmFileManager.storeFile("images/Lenna_java2.pnm", img);
//	    }
//	    catch (Exception e)
//	    {
//	      log(e);
//	    }
	  }

	  public Image loadFile(String fileName) throws IOException {
	    Image img = new Image();
	    log("Reading binary file named : " + fileName);

	    Path path = Paths.get(fileName);
	    byte[] data = Files.readAllBytes(path);
	    String[] headers = new String[4];  // we are interested only in 4 lines.
	    int byteIndex = 0;
	    for (int i = 0; i <headers.length; i++)
	    {
	      headers[i] = "";
	      while (data[byteIndex] != 0x0A)
	      {
	    	  //log(i+"checker "+byteIndex);
	    	  headers[i] += (char)data[byteIndex];
	    	  byteIndex++;
	      }
	      byteIndex++;
	    }

	    img.setType(Image.IMG_GRAY);
	    if (headers[0].equals("P6")) 
	    	img.setType(Image.IMG_RGB);
	    String[] dims = headers[2].split(" ");
	   // log(headers[2]+"checker ");

	    log("dims : " + dims[0]+" "+dims[1]);
	    img.setWidth(Integer.parseInt(dims[0]));
	    img.setHeight(Integer.parseInt(dims[1]));
	    
	    img.setData(Arrays.copyOfRange(data, byteIndex, data.length));
	    
	    log("byteIndex: "+byteIndex);
	    int headerValue = data.length-46;
	    log("data length: "+ headerValue);
	    int counter = byteIndex;
	    
	    //checker
//	    while (data[counter] != 0x0A)
//	    {
//	        log("Content: " + data[counter]);
//	        counter++;
//	    }
	    
	    //counter = counter-byteIndex;
	    //log("Number of array in a row : " + counter);
	    ////////////////////////////////

	    return img;
	  }

	  public void storeFile(String fileName, Image img) throws IOException
	  {
	    log("Writing binary file named : " + fileName);
	    Path path = Paths.get(fileName);
	    Files.deleteIfExists(path);
	    String type, comment, size, maxValue;

	    if (img.getType() == Image.IMG_GRAY)
	      type = "P5\n";
	    else
	      type = "P6\n";
	    Files.write(path, type.getBytes(), StandardOpenOption.CREATE);

	    comment = "# Generated by CSCI557 Java code\n";
	    Files.write(path, comment.getBytes(), StandardOpenOption.APPEND);

	    size = String.format("%d %d\n", img.getWidth(), img.getHeight());
	    Files.write(path, size.getBytes(), StandardOpenOption.APPEND);

	    maxValue = "255\n";
	    Files.write(path, maxValue.getBytes(), StandardOpenOption.APPEND);

	    Files.write(path, img.getData(), StandardOpenOption.APPEND);
	  }

	  private static void log(Object msg){
	    System.out.println(String.valueOf(msg));
	  }

}