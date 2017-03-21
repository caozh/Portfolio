package csci55500_hw2;

import java.math.BigInteger;

public class Rabin {

	public static void main(String[] args) {
		
		BigInteger x = new BigInteger("32767");
		BigInteger p = new BigInteger("199");
		BigInteger q = new BigInteger("211");
		BigInteger n = p.multiply(q);
		System.out.println("n: "+n);
		//Question 1
		System.out.println("Question 1: ");
		
		//encryption
		BigInteger y = x.pow(2).mod(n);
		System.out.println("y: "+y);
		
		//decryption
		BigInteger a1 = y.pow((p.intValue()+1)/4);
		BigInteger a2 = y.pow((p.intValue()+1)/4).multiply(new BigInteger("-1"));
		BigInteger a3 = y.pow((q.intValue()+1)/4);
		BigInteger a4 = y.pow((q.intValue()+1)/4).multiply(new BigInteger("-1"));
		BigInteger m1 = p;
		BigInteger m2 = q;
		BigInteger M1 = n.divide(m1);
		BigInteger M2 = n.divide(m2);
		BigInteger y1 = M1.modInverse(m1);
		BigInteger y2 = M2.modInverse(m2);
		
		BigInteger x1 = a1.multiply(M1).multiply(y1).add(a3.multiply(M2).multiply(y2)).mod(n);
		BigInteger x2 = a1.multiply(M1).multiply(y1).add(a4.multiply(M2).multiply(y2)).mod(n);
		BigInteger x3 = a2.multiply(M1).multiply(y1).add(a3.multiply(M2).multiply(y2)).mod(n);
		BigInteger x4 = a2.multiply(M1).multiply(y1).add(a4.multiply(M2).multiply(y2)).mod(n);

		System.out.println("By CRT: ");
		System.out.println("x1: "+x1);
		System.out.println("x2: "+x2);
		System.out.println("x3: "+x3);
		System.out.println("x4: "+x4);
		
		//verification
		System.out.println("Verification: ");
		BigInteger y_ = x1.pow(2).mod(n);
		System.out.println("y1: "+y_);
		y_ = x2.pow(2).mod(n);
		System.out.println("y2: "+y_);
		y_ = x3.pow(2).mod(n);
		System.out.println("y3: "+y_);
		y_ = x4.pow(2).mod(n);
		System.out.println("y4: "+y_+"\n");
		
		//Question 2
		System.out.println("Question 2: ");
		BigInteger B = new BigInteger("1357");
		y = x.multiply(x.add(B)).mod(n);
		System.out.println("y: " + y);
		
		double tmp = (Math.pow(2,-1)*B.intValue())% n.intValue();
		System.out.println("1: " + tmp);
		tmp = (y.intValue()+ Math.pow(tmp,2))% n.intValue();
		System.out.println("2: " + tmp);
		
	
	}
}
