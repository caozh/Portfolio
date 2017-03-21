package csci55500_hw3;

import java.math.BigInteger;

public class Three {

	public static void main(String[] args) {
		//algorithm 5.16
		BigInteger n = new BigInteger("18721");
		BigInteger b1 = new BigInteger("43");
		BigInteger b2 = new BigInteger("7717");
		BigInteger y1 = new BigInteger("12677");
		BigInteger y2 = new BigInteger("14702");
		BigInteger c1 = b1.modInverse(b2);
		BigInteger partA = y1.pow(c1.intValue());
		BigInteger c2 = c1.multiply(b1).subtract(BigInteger.ONE).divide(b2);
		BigInteger partB = y2.pow(c2.intValue()).modInverse(n);
		BigInteger x = partA.multiply(partB).mod(n);
		System.out.println("x: "+x.toString());
		//verify
		BigInteger y_ = x.pow(43).mod(n);
		System.out.println("y1: "+y_.toString());
	}

}
