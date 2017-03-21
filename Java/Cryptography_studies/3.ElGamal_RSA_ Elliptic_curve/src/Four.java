package csci55500_hw3;

import java.math.BigInteger;
import java.util.ArrayList;

public class Four {

	public final static BigInteger p_ = new BigInteger("1039");
	public final static int p = 1039;
	public final static int k = 100;
	public final static int b = 939; //private key
	public final static Point alpha = new Point(799,790);
	public static Boolean findkey = false;
	
	public static void main(String[] args) {
		//Number of points: 1008
		//Lexically largest: (1038,1037)
		//ArrayList<Point> E_points  = E_generator();
		
		//Finding the private key (939)
//		findkey = true;
//		Point alpha = new Point(799,790);
//		Point y1 = point_mult(1000, alpha);
//		findkey = false;
		
		//Encrypt given point (575,419)
//		y1 (873,233)
//		y2 (963,817)
		ArrayList<Point> y = Encryption();
//		Decryption(y);
		
		//Decrypt given points
		//Output: (319,784)
		Point y1 = new Point(873,233);
		Point y2 = new Point(234,14);
		ArrayList<Point> ys = new ArrayList<Point>();
		ys.add(y1);
		ys.add(y2);
		Decryption(ys);
		
		//Diffie-Hellman key exchange
//		share_key = (191,568)
		DH_key();
		
	}//end main
	
	//Diffie-Hellman key exchange
	public static Point DH_key(){
		Point B = new Point(815,519);
		Point share_key = null;
		
		//Find Alice secret key a
//		findkey = true;
//		Point alpha2 = new Point(818,121);
//		Point tmp = point_mult(1000, alpha2); //key_a = (199,72)
//		findkey = false;
		
		//Calculate share key
		Point key_a = new Point(199,72);
		share_key = point_add(key_a, B);
		System.out.println("share_key "+share_key.toString());
		return share_key;
	}
	//Encryption
	public static ArrayList<Point> Encryption(){
		Point x = new Point(575,419); //input plaintext
		
		//y1 = k*alpha
		Point y1 = point_mult(k, alpha);
		//y2 = x + k*beta
		Point beta = new Point(385,749);//point_mult(b, alpha);
		Point y2 = point_mult(k, beta);
		y2 = point_add(x, y2);
		System.out.println("Encryption ");
		System.out.println("y1 "+y1.toString());
		System.out.println("y2 "+y2.toString());
		//output
		ArrayList<Point> arr = new ArrayList<Point>();
		arr.add(y1);
		arr.add(y2);
		return arr;
	}
	
	public static Point Decryption(ArrayList<Point> y){
		//Decryption
		//x = y2-b*y1
		Point y1 = new Point(y.get(0).getX(),y.get(0).getY());
		Point y2 = new Point(y.get(1).getX(),y.get(1).getY());
		Point x_decrypt = point_mult(b, y1);
		int x_decrypt_y_inv = (x_decrypt.getY()*(-1))+p;
		Point x_partB = new Point(x_decrypt.getX(), x_decrypt_y_inv);
		x_decrypt = point_add(y2,x_partB);
		
		System.out.println("Decryption ");
		System.out.println("x_decrypt "+x_decrypt.toString());
		return x_decrypt;
	}
	
	//calculate the "power" of a point
	public static Point point_mult(int power, Point a_){
		Point a = new Point(a_.getX(),a_.getY());
		for (int i = 1; i<power; i++){
			a = point_add(a, a_);
			if(findkey){
			System.out.println(i+1+": "+a); //for finding private key
			}
		}
		return a;
	}
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
			BigInteger partA = x1.pow(2).multiply(three).add(BigInteger.ONE);
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
		//System.out.println("point3 "+point3.toString());
		
		return point3;
	}
	
	public static ArrayList<Point> E_generator(){
		ArrayList<Point> arr = new ArrayList<Point>();
		BigInteger LS_ =  new BigInteger("519");
		BigInteger four_ =  new BigInteger("4");
		BigInteger SRp_ =  p_.add(BigInteger.ONE).divide(four_); //(p+1)/4 Squre Roots power
		// x[0:1039-1]
		for (int i=0; i<p; i++){
			int z = (int) ((Math.pow(i, 3)+i+6))% p; //(i^3+i+6)%p
			BigInteger z_ = new BigInteger(Integer.toString(z));
			BigInteger QR_ = z_.modPow(LS_, p_);   //calculate Legendra Symbol (SL)
			if (QR_.intValue()==1){  //if z is a quadratic residue
				BigInteger y1_ = z_.modPow(SRp_, p_);      //z^(p+1)/4 mod p
				BigInteger y2_ = z_.pow(SRp_.intValue()).negate().mod(p_); //-z^(p+1)/4 mod p
				
				Point point_1= new Point(i,y1_.intValue());
				Point point_2= new Point(i,y2_.intValue());
				arr.add(point_1);
				arr.add(point_2);
			}
		}
		
		//print the points
		for(int i = 0; i<arr.size();i++) System.out.println(i+": "+arr.get(i).toString());
			
		System.out.println("Number of points: "+ arr.size());
		return arr;
	}
}//end class


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