package csci55500_hw3;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;

public class Two {

	public static void main(String[] args) throws IOException {
		//load file
		String filePath = "table.txt";
		ArrayList<Integer> y1 = new ArrayList<Integer>();
		ArrayList<Integer> y2 = new ArrayList<Integer>();
		BufferedReader br = new BufferedReader(new InputStreamReader( new FileInputStream(filePath), "Cp1252"));
		String line = null;
		while ((line = br.readLine()) != null) { //line by line
			String[] items = line.split(",");
			y1.add(Integer.parseInt(items[0]));
			y2.add(Integer.parseInt(items[1]));
		}
		br.close();
		
		//for(int i = 0; i<y1.size();i++) System.out.println(""+y1.get(i)+", "+y2.get(i));
		
		
		//BigInteger y1_ = new BigInteger(Integer.toString(y1.get(0)));
		//System.out.println(y1_.intValue());
		BigInteger p = new BigInteger("31847");
				
		ArrayList<Integer> x = new ArrayList<Integer>();
		char[] lookup     = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
		
		for(int i = 0; i<y1.size();i++){
			BigInteger y1_ = new BigInteger(Integer.toString(y1.get(i)));
			BigInteger y2_ = new BigInteger(Integer.toString(y2.get(i)));
			BigInteger x_ = y2_.multiply(y1_.pow(7899).modInverse(p)).mod(p); //x = y2(y1^a)^-1 mod p
			//System.out.println(i+": "+x_.intValue());
			//decode
			BigInteger ts = new BigInteger("26");
			BigInteger a = x_.divide(ts.pow(2)).mod(ts);
			BigInteger b = x_.mod(ts.pow(2)).divide(ts);
			BigInteger c = x_.mod(ts.pow(2)).mod(ts);
			//System.out.println("a: "+a.intValue()+" b: "+b.intValue()+" c: "+c.intValue());
			System.out.print(String.valueOf(lookup[a.intValue()]).toLowerCase());
			System.out.print(String.valueOf(lookup[b.intValue()]).toLowerCase());
			System.out.print(String.valueOf(lookup[c.intValue()]).toLowerCase());
		}
		
	}

}
