package csci55500_hw2;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

public class RSA {
	
	public static void main(String[] args) throws IOException{
		char[][] matrix = {
				  {' ' , '!', '"', '#', '$', '%', '&', '\'', '(', ')'},
				  {'*' , '+', ',', '-', '.', '/', '0', '1' , '2', '3'},
				  {'4' , '5', '6', '7', '8', '9', ':', ';' , '<', '='},
				  {'>' , '?', '@', 'A', 'B', 'C', 'D', 'E' , 'F', 'G'},
				  {'H' , 'I', 'J', 'K', 'L', 'M', 'N', 'O' , 'P', 'Q'},
				  {'R' , 'S', 'T', 'U', 'V', 'W', 'X', 'Y' , 'Z', '['},
				  {'\\', ']', '^', '_', '`', 'a', 'b', 'c' , 'd', 'e'},
				  {'f' , 'g', 'h', 'i', 'j', 'k', 'l', 'm' , 'n', 'o'},
				  {'p' , 'q', 'r', 's', 't', 'u', 'v', 'w' , 'x', 'y'},
				  {'z' , '{', '|', '}', '~', ' ', ' ', '\n', '\r',' '}		
		};
		String filePath = "hw2-ciphers-parameter-matrix\\RSA-ciphertext.txt";
		BigInteger n = new BigInteger("68102916241556953901301068745501609390192169871097881297");
		BigInteger b = new BigInteger("36639088738407540894550923202224101809992059348223191165");
		int        B = 1500;

		BigInteger p = Pollard_p1(n, B);
		System.out.println("p: "+p);
		
		if (p.compareTo(BigInteger.ZERO) != 0){ //if p is found
			//calculate parameters: q, phi_n, and a
			BigInteger q = n.divide(p);
			BigInteger phi_n = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
			BigInteger a = b.modInverse(phi_n);
			System.out.println("q: "+q);
			System.out.println("Phi n: "+phi_n);
			System.out.println("a: "+a+"\n");
			
			//load file
			BufferedReader br = new BufferedReader(new InputStreamReader( new FileInputStream(filePath), "Cp1252"));
			String line = null;
			while ((line = br.readLine()) != null) { //line by line
				BigInteger y = new BigInteger(line);
				BigInteger x = y.modPow(a,n);
				String tmp = x.toString();
				
				if(tmp.length()%2 != 0) tmp = '0'+tmp; //to even digits
					
				for (int i = 0; i < tmp.length(); i += 2){
					int row = tmp.charAt(i)-'0';
					int col = tmp.charAt(i+1)-'0';
					System.out.print(matrix[row][col]);
				}
			}
			br.close();
		}
	}
	
	public static BigInteger Pollard_p1(BigInteger n, int B){
		BigInteger a = new BigInteger("2");
		BigInteger one = new BigInteger("1");
		BigInteger d;
	    for (int i = 2; i <= B; i++){
	    	a = a.pow(i).mod(n);
	    	d = a.subtract(one).gcd(n);
	    	if (d.compareTo(one)==1 && d.compareTo(n)==-1)
	            return d;
	    }
	    return BigInteger.ZERO;
	}
	
}
