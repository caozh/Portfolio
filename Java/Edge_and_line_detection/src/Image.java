package csci557_project3_;

public class Image {
	  public static int IMG_GRAY = 0;
	  public static int IMG_RGB = 1;

	  private int type;
	  private int width;
	  private int height;
	  private byte[] data;

	  public int getType() {return type;}
	  public void setType(int type) {this.type = type;}
	  public int getWidth() {return width;}
	  public void setWidth(int width) {this.width = width;}
	  public int getHeight() {return height;}
	  public void setHeight(int height) {this.height = height;}
	  public byte[] getData() {return data;}
	  public void setData(byte[] data) {this.data = data;}

	}
