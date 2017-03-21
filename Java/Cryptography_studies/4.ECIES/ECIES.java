package csci55500_hw4;

import java.math.BigInteger;

public class ECIES {

	public final static BigInteger p_ = new BigInteger("31");
	public final static int p = 31;
	public final static int m = 8; //private key
	
	public static void main(String[] args) {
		//Question 1
		System.out.println("Question 1: ");
		Point P = new Point(2,9);
		Point y2 = point_mult(m, P);
		System.out.println("Q = mP = " + y2.toString());
		
		//Question 2
		int result[] = decryption();
		
		//Question 3
		System.out.println("\nQuestion 3: ");
		//convert into English word
		char[] lookup     = {'A','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
		System.out.println("Convert into English word: ");
		for (int i = 0; i < 4; i++){
			System.out.print(String.valueOf(lookup[result[i]]).toLowerCase());
		}
	}
	
	public static int[] decryption(){
		System.out.println("\nQuestion 2: ");
		//step 1: Decompress points
		Point D1 = new Point(18,1);
		Point D2 = new Point(3,1);
		Point D3 = new Point(17,0);
		Point D4 = new Point(28,0);
		//test
		Point asdasd[] = {D1, D2, D3, D4};
		
		System.out.println("Decompress: ");
		for (int i = 0; i < 4; i++){
			asdasd[i] = decompress(asdasd[i]);
			System.out.println(asdasd[i]);
		}
		
		//step 2: mDecompress
		System.out.println("m*Decompress: ");
		for (int i = 0; i < 4; i++){
			asdasd[i] = point_mult(m, asdasd[i]);
			System.out.println(asdasd[i]);
		}
		//step 3: decrypt calculation
		System.out.println("Plaintext: ");
		int arr_y2[] = {21, 18, 19, 8};
		int result[] = new int[4];
		for (int i = 0; i < 4; i++){
			BigInteger a = new BigInteger(Integer.toString(asdasd[i].getX()));
			BigInteger b = a.modInverse(p_);
			result[i] = (arr_y2[i]*b.intValue())%p;
			System.out.println(result[i]);
		}
		return result;
	}
	
	// point decompression function
	public static Point decompress(Point input){
		Point output = null;	
		BigInteger two = new BigInteger("2");
		BigInteger seven = new BigInteger("7");
		BigInteger x = new BigInteger(Integer.toString(input.getX()));
		BigInteger i = new BigInteger(Integer.toString(input.getY()));
		BigInteger z = x.pow(3).add((two.multiply(x))).add(seven).mod(p_);
		//D4, z is a quadratic non-residue modulo p
		if (z.intValue() == 5){
			return output = new Point(x.intValue(), 6);
		}
		//System.out.println("z: " + z.toString());
		//assume z is quadratic residue
		int tmp = (int) Math.sqrt(z.intValue());
		z = new BigInteger(Integer.toString(tmp));
		BigInteger y = z.mod(p_);
		//System.out.println("y: " + y.toString());
		if (y.mod(two).intValue() == i.intValue()){
			return output = new Point(x.intValue(), y.intValue());
		} else {
			return output = new Point(x.intValue(),(p_.subtract(y).intValue()));
		}
	};
	
	//calculate the "power" of a point
	public static Point point_mult(int power, Point a_){
		Point a = new Point(a_.getX(),a_.getY());
		for (int i = 1; i<power; i++){
			a = point_add(a, a_);
		}
		return a;
	}

	//addition operation under Elliptic curve
	public static Point point_add(Point a, Point b){
		Point point1 = new Point(a.getX(),a.getY());
		Point point2 = new Point(b.getX(),b.getY());
		Point point3 = null;
		BigInteger lumbda;
		//calculating lumbda
		if(point1.getX() == point2.getX() && point1.getY() == point2.getY()){ //case P = Q
			BigInteger three = new BigInteger("3");
			BigInteger two = new BigInteger("2");
			BigInteger x1 = new BigInteger(Integer.toString(point1.getX()));
			BigInteger partA = x1.pow(2).multiply(three).add(two);
			BigInteger y1 = new BigInteger(Integer.toString(point1.getY()));
			BigInteger partB = y1.multiply(two).modInverse(p_);
			lumbda = partA.multiply(partB).mod(p_);
		} else {                                          //case P != Q
			BigInteger partA = new BigInteger(Integer.toString(point2.getY()-point1.getY()));
			BigInteger partB = new BigInteger(Integer.toString(point2.getX()-point1.getX()));
			BigInteger partB_inv = partB.modInverse(p_);
			lumbda = partA.multiply(partB_inv).mod(p_); //lumbda = (y2-y1)(x2-x1)^-1 mode p
		}
		
		int x3 = (int) ((Math.pow(lumbda.intValue(), 2) - point1.getX() - point2.getX())%p);
		int y3 = (int) ((lumbda.intValue()*(point1.getX()- x3) - point1.getY())%p);
		if (x3 < 0){
			x3 += p;
		} else if (y3 < 0){
			y3 += p;
		} 
		point3 = new Point(x3,y3);
		
		return point3;
	}
	
}//end ECIES class

//construct Point class
class Point {
  private int x;
  private int y;

  public Point(int x, int y) {
      this.x = x;
      this.y = y;
  }

  public int getX() {
      return x;
  }

  public int getY() {
      return y;
  }

  @Override
  public String toString() {
      return ("(" + x + "," + y + ")"); 
  }
}