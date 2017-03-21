package csci55500_hw2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DES {
	
	public static void main(String[] args) {
		String filePath = "hw2-ciphers-parameter-matrix\\DES-ciphertext.txt";
		String DESkey   = "8B2A7FF25E98C35D";
		//generate keys
		String key = toBinary(DESkey);
		String[] keys = keyGenerator(key);
		//load cipher text
		String Cipher_ = loadFile(filePath);
		
		//Construct blocks
		String plaintext = "";
		for (int i = 0; i < Cipher_.length()/64; i++){  
			plaintext += decryptBlock(Cipher_.substring(i*64, (i+1)*64), keys);
		}
		
		System.out.println(plaintext);
	}
	
	public static String decryptBlock(String Cipher_, String[] keys){
		//parameters
		int[] IP={                      //64
				58,50,42,34,26,18,10,2,
				60,52,44,36,28,20,12,4,
				62,54,46,38,30,22,14,6,
				64,56,48,40,32,24,16,8,
				57,49,41,33,25,17,9,1,
				59,51,43,35,27,19,11,3,
				61,53,45,37,29,21,13,5,
				63,55,47,39,31,23,15,7};
		int[] IP_inv={                   //64
				40,8,48,16,56,24,64,32,
				39,7,47,15,55,23,63,31,
				38,6,46,14,54,22,62,30,
				37,5,45,13,53,21,61,29,
				36,4,44,12,52,20,60,28,
				35,3,43,11,51,19,59,27,
				34,2,42,10,50,18,58,26,
				33,1,41,9,49,17,57,25
				};
		int[] E={                 //48
				32,1,2,3,4,5,
				4,5,6,7,8,9,
				8,9,10,11,12,13,
				12,13,14,15,16,17,
				16,17,18,19,20,21,
				20,21,22,23,24,25,
				24,25,26,27,28,29,
				28,29,30,31,32,1
				};
		int[][][] S={      //[8][4][6]
				{{14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7},
				 {0,15,7,4,14,2,13,1,10,6,12,11,9,5,3,8},
				 {4,1,14,8,13,6,2,11,15,12,9,7,3,10,5,0},
				 {15,12,8,2,4,9,1,7,5,11,3,14,10,0,6,13}
				},
				{
				 {15,1,8,14,6,11,3,4,9,7,2,13,12,0,5,10},
				 {3,13,4,7,15,2,8,14,12,0,1,10,6,9,11,5},
				 {0,14,7,11,10,4,13,1,5,8,12,6,9,3,2,15},
				 {13,8,10,1,3,15,4,2,11,6,7,12,0,5,14,9}
				},
				{
				  {10,0,9,14,6,3,15,5,1,13,12,7,11,4,2,8},
				  {13,7,0,9,3,4,6,10,2,8,5,14,12,11,15,1},
				  {13,6,4,9,8,15,3,0,11,1,2,12,5,10,14,7},
				  {1,10,13,0,6,9,8,7,4,15,14,3,11,5,2,12}
				},
				{
				  {7,13,14,3,0,6,9,10,1,2,8,5,11,12,4,15},
				  {13,8,11,5,6,15,0,3,4,7,2,12,1,10,14,9},
				  {10,6,9,0,12,11,7,13,15,1,3,14,5,2,8,4},
				  {3,15,0,6,10,1,13,8,9,4,5,11,12,7,2,14}
				},
				{
				  {2,12,4,1,7,10,11,6,8,5,3,15,13,0,14,9},
				  {14,11,2,12,4,7,13,1,5,0,15,10,3,9,8,6},
				  {4,2,1,11,10,13,7,8,15,9,12,5,6,3,0,14},
				  {11,8,12,7,1,14,2,13,6,15,0,9,10,4,5,3},
				},
				{
				   {12,1,10,15,9,2,6,8,0,13,3,4,14,7,5,11},
				   {10,15,4,2,7,12,9,5,6,1,13,14,0,11,3,8},
				   {9,14,15,5,2,8,12,3,7,0,4,10,1,13,11,6},
				   {4,3,2,12,9,5,15,10,11,14,1,7,6,0,8,13}
				},
				{
				  {4,11,2,14,15,0,8,13,3,12,9,7,5,10,6,1},
				  {13,0,11,7,4,9,1,10,14,3,5,12,2,15,8,6},
				  {1,4,11,13,12,3,7,14,10,15,6,8,0,5,9,2},
				  {6,11,13,8,1,4,10,7,9,5,0,15,14,2,3,12}
				},
				{
				  {13,2,8,4,6,15,11,1,10,9,3,14,5,0,12,7},
				  {1,15,13,8,10,3,7,4,12,5,6,11,0,14,9,2},
				  {7,11,4,1,9,12,14,2,0,6,10,13,15,3,5,8},
				  {2,1,14,7,4,10,8,13,15,12,9,0,3,5,6,11}
				}
				};
		int[] P={           //32
				16,7,20,21,
				29,12,28,17,
				1,15,23,26,
				5,18,31,10,
				2,8,24,14,
				32,27,3,9,
				19,13,30,6,
				22,11,4,25
				};
		
		//IP
		String Cipher = "";
		for (int i = 0; i < IP.length; i++){       //each value of IP[]
			Cipher += Cipher_.charAt(IP[i]-1);
		}
		
		String L = Cipher.substring(0, Cipher.length()/2);                // 
		String R = Cipher.substring(Cipher.length()/2, Cipher.length()); // 
		//System.out.print("L: "+L+"\n");
		//System.out.print("R: "+R+"\n");
		String L_nxt = L;
		String R_nxt = R;		
		
		//16 rounds
		for (int i = 0; i < 16; i++){       //each round
			//System.out.println("\nRound: "+i);
			//System.out.println(L_nxt+" L_this");
			//System.out.println(R_nxt+" R_this");
			//initialize L and R in this round
			L = L_nxt;	
			R = R_nxt;
			
			//calculate L and R in previous round
			L_nxt = R;
			
			//f function
			//expand
			String E_result = "";
			for (int j = 0; j < E.length; j++){       //each value of E[]
				E_result += R.charAt(E[j]-1);
			}
			
			//XOR (E_result, keys[15-i])
			String XOR = "";
			for (int j = 0; j < E_result.length(); j++){  //length = 48
				char ch1 = (char) (E_result.charAt(j));
				char ch2 = (char) (keys[15-i].charAt(j));  //key_i from 15 to 0
				if (Character.isDigit(ch1) && Character.isDigit(ch2)){
					int tmp = ch1 ^ ch2;
					XOR += String.valueOf(tmp);
				} else System.out.println("XOR error");
			}
			
			//S Boxes
			//construct 8 boxes
			String[] XOR_S = new String[8];
			String S_result = "";
			for (int j = 0; j < 8; j++){  //length = 48
				XOR_S[j] = XOR.substring(j*6, (j+1)*6);
			}
				
			for (int j = 0; j < 8; j++){     //for each S box
				
				String row_ = String.valueOf(XOR_S[j].charAt(0))+String.valueOf(XOR_S[j].charAt(5));
				String col_ = XOR_S[j].substring(1, 5);  
				int row = Integer.parseInt(row_, 2);
				int col = Integer.parseInt(col_, 2);
				int tmp = S[j][row][col];
				String tmp_ = Integer.toString(tmp,2);
				if (tmp_.length() < 4)                                   //left padding with '0'
					tmp_ = String.format("%4s", tmp_).replace(' ', '0');
				S_result += tmp_;
			}
			
			//P permutaion
			String f_result = "";
			for (int j = 0; j < P.length; j++){       //each value of P matrix
				f_result += S_result.charAt(P[j]-1);
			}
			
			//XOR with R
			String XOR_L = "";
			for (int j = 0; j < f_result.length(); j++){  //length = 32
				char ch1 = (char) (L.charAt(j));
				char ch2 = (char) (f_result.charAt(j));
				if (Character.isDigit(ch1) && Character.isDigit(ch2)){
					int tmp = ch1 ^ ch2;
					XOR_L += String.valueOf(tmp);
				} 
			}
			R_nxt = XOR_L;
			//System.out.println(L_nxt + " L_nxt");
			//System.out.println(R_nxt + " R_nxt");
		} //end rounds
		
		//combine L and R
		String LR = R_nxt+L_nxt;

		//inverse IP
		String decrypted = "";
		for (int i = 0; i < IP_inv.length; i++){       //each value of IP_inv[]
			decrypted += LR.charAt(IP_inv[i]-1);
		}
		
		//System.out.println("Result: \n"+decrypted+" Result");
		//System.out.println(toBinary(plain_test)+ " plaintext");
		
	    //bits to english letters
	    String str = "";
	    for (int i = 0; i < decrypted.length()/8; i++) {
	        int a = Integer.parseInt(decrypted.substring(8*i,(i+1)*8),2);
	        str += (char)(a);
	    }
	    //System.out.println(str);
	    return str;
	}
	
	//load cipher text
	public static String loadFile(String filePath){
		//load cipherTxt
		String cipher_hex = null;
		try {
			cipher_hex = new String(Files.readAllBytes(Paths.get(filePath)));
		} catch (IOException e) {
			e.printStackTrace();
		}  
		cipher_hex = cipher_hex.replaceAll("\\r|\\n", ""); //strip newline

		String cipher_bin = toBinary(cipher_hex);   //Convert to binary 
		//System.out.println(cipher_bin.substring(0, 100));
		return cipher_bin;
	}
	
	//Convert to binary 
	public static String toBinary(String input_hex){
		String bin;
		String cipher_bin = "";
		for (int i = 0; i < input_hex.length(); i++){
			bin = Integer.toBinaryString(Integer.parseInt(String.valueOf(input_hex.charAt(i)), 16));
			if (bin.length() < 4)                                   //left padding with '0'
				bin = String.format("%4s", bin).replace(' ', '0');
			cipher_bin += bin;
			//System.out.println(bin);
		}
		return cipher_bin;
	}
	
	//generate 16 keys
	public static String[] keyGenerator(String key){
		int PC_1[]={                     //56
				  57,49,41,33,25,17,9,
				  1,58,50,42,34,26,18,
				  10,2,59,51,43,35,27,
				  19,11,3,60,52,44,36,
				  63,55,47,39,31,23,15,
				  7,62,54,46,38,30,22,
				  14,6,61,53,45,37,29,
				  21,13,5,28,20,12,4
		};
		int PC_2[]={					//48
				14,17,11,24,1,5,
				3,28,15,6,21,10,
				23,19,12,4,26,8,
				16,7,27,20,13,2,
				41,52,31,37,47,55,
				30,40,51,45,33,48,
				44,49,39,56,34,53,
				46,42,50,36,29,32
		};

		String[] output = new String[16];
		String PC_1_result = "";
		
		//PC-1 
		for (int i = 0; i < PC_1.length; i++){       //each value of a key
			PC_1_result += key.charAt(PC_1[i]-1);
			//bin_arr[i] = key.charAt(PC_1[i]-1);
		}
		//System.out.println("\n\nPC-1: \n"+PC_1_result);

		//Generating 16 keys
		String C = PC_1_result.substring(0, PC_1_result.length()/2);                
		String D = PC_1_result.substring(PC_1_result.length()/2, PC_1_result.length()); 
		String CD = "";
			
		//left shifting bits and PC-2
		for (int i = 0; i < 16; i++){       //16 keys
			output[i] = "";
		    System.out.print("Key "+i + ": ");
			
			if (i==0 || i==1 || i==8 || i==15){ //Shift left 1 position in round 1, 2, 9, 16
				C = C.substring(1, C.length()) + C.charAt(0);
				D = D.substring(1, D.length()) + D.charAt(0) ;
				//System.out.println(C+D+" shift1");
			} else { //Shift left two positions
				C =  C.substring(2, C.length()) + C.substring(0, 2);
				D =  D.substring(2, D.length()) + D.substring(0, 2);
				//System.out.println(C+D+" shift2");
			}
		    CD = C+D;
			//PC-2
			for (int j = 0; j < PC_2.length; j++){
				//System.out.print(CD.charAt(j) + " ");
				output[i] += String.valueOf(CD.charAt(PC_2[j]-1));
			}
			System.out.print(output[i]+"\n");
		}
		return output;
	}
}

