package csci55500_hw1;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Main {	
	public static void main(String[] args) throws IOException {
		
		String[] loadFiles = {"hw1-ciphers\\ciphertext1.txt", 
							  "hw1-ciphers\\ciphertext2.txt", 
							  "hw1-ciphers\\ciphertext3.txt", 
							  "hw1-ciphers\\ciphertext4.txt", 
							  "hw1-ciphers\\ciphertext5.txt", 
							  "hw1-ciphers\\ciphertext6.txt",};
        
		/////////////////////////////////////////////////////////////////////////////////////////
        //Cipher 1 Permutation Cipher
		String[] cipherTxt_array = loadFile(loadFiles[0]);   //cipherTxt_array: [0:cipherTxt, 1:cipherTxt_strip, 2:cipherTxt_frequency]  
		frequencyAnalysis(cipherTxt_array[1],cipherTxt_array[2]);
        String result = permutationCipher(cipherTxt_array[1]);	
        unstrip(result,cipherTxt_array[0]);
        
        /////////////////////////////////////////////////////////////////////////////////////////
        //Cipher 2 Substitution Cipher 
//      String[] cipherTxt_array = loadFile(loadFiles[1]); //cipherTxt_array: [0:cipherTxt, 1:cipherTxt_strip, 2:cipherTxt_frequency]
//      frequencyAnalysis(cipherTxt_array[1],cipherTxt_array[2]);
//      char[] cipher     = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'}; //no use, for reference
//      char[] subsKey    = {'I','X','V','G','L','F','E','D','Q','C','B','A','Z','Y','R','S','J','M','H','N','P','W','K','U','O','T'}; //key
//      substitutionCipher(cipherTxt_array[0], cipherTxt_array[1],subsKey); //(cipherTxt, cipherTxt_strip,subsKey)
        
        /////////////////////////////////////////////////////////////////////////////////////////
        //Cipher 3 LFSR4 Cipher
		//Since no correct guess is found, all existing calculations are done on draft.
//		String[] cipherTxt_array = loadFile(loadFiles[2]); //cipherTxt_array: [0:cipherTxt, 1:cipherTxt_strip, 2:cipherTxt_frequency]
//		System.out.println(cipherTxt_array[0]);
//		frequencyAnalysis(cipherTxt_array[1],cipherTxt_array[2]);
		
		/////////////////////////////////////////////////////////////////////////////////////////
		//Cipher 4 Hill Cipher
//		String[] cipherTxt_array = loadFile(loadFiles[3]); //cipherTxt_array: [0:cipherTxt, 1:cipherTxt_strip, 2:cipherTxt_frequency]
//		frequencyAnalysis(cipherTxt_array[1],cipherTxt_array[2]);
//		String result = HillCipher(cipherTxt_array[1]);
//		unstrip(result,cipherTxt_array[0]);

		
		/////////////////////////////////////////////////////////////////////////////////////////
		//Cipher 5 Shift Cipher (Vigenere Cipher)
//		String[] cipherTxt_array = loadFile(loadFiles[4]); //cipherTxt_array: [0:cipherTxt, 1:cipherTxt_strip, 2:cipherTxt_frequency]
//		frequencyAnalysis(cipherTxt_array[1],cipherTxt_array[2]);
//		String result = shiftCipher(cipherTxt_array[1]);
//      unstrip(result,cipherTxt_array[0]); //(result,cipherTxt)
        
		/////////////////////////////////////////////////////////////////////////////////////////
		//Cipher 6 Affine Cipher
//		String[] cipherTxt_array = loadFile(loadFiles[5]); //cipherTxt_array: [0:cipherTxt, 1:cipherTxt_strip, 2:cipherTxt_frequency]
//		frequencyAnalysis(cipherTxt_array[1],cipherTxt_array[2]);
//		String result = AffineCipher(cipherTxt_array[1]);
//		unstrip(result,cipherTxt_array[0]); //(result,cipherTxt)
		
	}//end main()
	
	//load file based on input name. 
	//return an array cipherTxt_array: [0:cipherTxt, 1:cipherTxt_strip, 2:cipherTxt_frequency]
	public static String[] loadFile(String fileName) throws IOException{
		String myArrays[] = new String[3];
		//load file
		myArrays[0] = new String(Files.readAllBytes(Paths.get(fileName)));  //cipherTxt
		
		//remove punctuation
		myArrays[1] = myArrays[0].replaceAll("[^a-zA-Z]","");               //cipherTxt_strip          without space
		myArrays[2] = myArrays[0].replaceAll("[^a-zA-Z\\s]","");            //cipherTxt_frequency      with space
		return myArrays;
	}
	
	//Unstrip the plaintext, specific for substitution
	public static void unstrip_substitution(char[] subsKey, String cipherTxt){
		String toLower = "";
		for (int i = 0; i < cipherTxt.length(); i++) {
			char ch = (char) (cipherTxt.charAt(i));
			//skip punctuation and space
			if (Character.isLetter(ch)){
				int idx = ch -65;               //retrieve letter index
				ch = subsKey[idx];              //lookup the substitution array
				toLower = String.valueOf(ch);
				System.out.print(toLower.toLowerCase());
			} else
				System.out.print(ch);
		}
	}
	
	//Unstrip the plaintext
	public static void unstrip(String input, String cipherTxt){
		int j=0;
		String toLower = "";
		System.out.println("\nUnStrip: ");
		for (int i = 0; i < cipherTxt.length(); i++) {
			char ch = (char) (cipherTxt.charAt(i));
			//skip punctuation and space
			if (Character.isLetter(ch)){
				toLower = String.valueOf(input.charAt(j));
				System.out.print(toLower.toLowerCase()); //print plaintxt letters
				j++;
			} else
				System.out.print(ch);              //print punctuation and space (non-letters in input text) 
		}
		System.out.println();
	}
	
	//Frequency analysis
	public static void frequencyAnalysis(String cipherTxt_strip, String cipherTxt_frequency){
		char[] lookup     = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
		int[] frequencyTable = new int[26];
		int idx;
		//frequency table
		for (int i = 0; i < cipherTxt_strip.length(); i++) {
			idx = cipherTxt_strip.charAt(i)-65;
			frequencyTable[idx]++;
		}
		
		////////////////////////////////////////
		//Draw histogram
		String star ="*";
		for (int i = 0; i < frequencyTable.length; i++){
			System.out.print(lookup[i]+" " );
            for(int j = 0; j < frequencyTable[i]/2; j++)
            	System.out.print(star);
            System.out.println(frequencyTable[i]);
		}
		
		//////////////////////////////////////////////////
		//Find pairs
		String[] words = cipherTxt_frequency.split("\\s+");           //split by space
		
	    Arrays.sort(words);
		for (int i = 0; i < words.length; i++){
			//System.out.print(words[i]+"\n");                        //showing all items in array
		}
		
		//Collectors method	
	    Arrays.stream(words)
	      .collect(Collectors.groupingBy(s -> s))
	      .forEach((k, v) -> System.out.println(k+" "+v.size()));     //show items with quantities
	}
	
	//Substitution Cipher
	public static void substitutionCipher(String cipherTxt,String cipherTxt_strip, char[] subsKey){
		unstrip_substitution(subsKey, cipherTxt);              //For comparison (substitution)
		String toLower = "";
        for (int i = 0; i < cipherTxt_strip.length(); i++) {//for each char in plainTxt
        	char ch = (char) (cipherTxt_strip.charAt(i));
        	int idx = cipherTxt_strip.charAt(i)-65;         
        	ch = subsKey[idx];                              //lookup the subsKey using index
        	toLower = String.valueOf(ch);
            System.out.print(toLower.toLowerCase());
        }
        System.out.println();
	}
	
	//Shift Cipher
	public static String shiftCipher(String input){
		String result = "";
        //for (int k=1; k<26; k++){
        for (int k=3; k<4; k++){
        	System.out.print(k+": ");
	        for (int i = 0; i < input.length(); i++) {
	            char ch = (char) ((input.charAt(i) - 'A' - k) % 26 + 'A'); //chars[i]
	            if (ch < 'A')                            //check if moving to left of "A"
	            	ch += 26;
	            String output = String.valueOf(ch);      //convert char to string
	            System.out.print(output.toLowerCase());  //convert upper to lower
	            result += output;
	        }
	        System.out.print("\n");
        }
        return result;
	}//end shiftCipher
	
	//Affine Cipher
	public static String AffineCipher(String input) {
		
		//solve the equation, calculate a and b
		int p = 4; int r = 12;
		int q = 19; int s = 11;
		int D_inv = 0;
        for (int j =1; j< 27;j++){
        	D_inv = (p-q)*j%26;
        	if (D_inv<0) D_inv+= 26;
        	//System.out.println("D_inv: "+D_inv);
        	if (D_inv == 1){  //only satisfy once
        		System.out.println("Found inverse of D: "+j);
        		D_inv = j;
                int a_ = (D_inv*(r-s))%26;
                int b_ = (D_inv*(p*s-q*r))%26;
                if (a_<0) a_+= 26;
                if (b_<0) b_+= 26;
                System.out.println("a_: "+a_);
                System.out.println("b_: "+b_);
        	}
        }

        //decode the cipher text
		String result = "";
		//find inverse of a
		int a = 19;
        int b = 14;
        int inv= 0;
        for (int j =1; j< 27;j++){
        	inv = a*j%26;
        	if (inv == 1){  //only satisfy once
        		System.out.println("Found inverse of a: "+j);
        		inv = j;
                for (int i = 0; i < input.length(); i++) {
        	        int c = (input.charAt(i) - 'A');  //cipher
        	        int plain = (inv*(c-b))%26;
        	        if (plain<0) plain += 26;
        	        char out = (char)(plain+'A');
        	        //System.out.print(out);
        	        String output = String.valueOf(out);      //convert char to string
        	        result += output;
                }
                break;
        	} else if(j==26)
        		System.out.println("Can not find the inverse of a");
        }
        System.out.println();
        return result;
  
	}//end Affine cipher
	
	//Permutation Cipher
	public static String permutationCipher(String input){
		String result = "";
		int[] secKey = {7, 2, 10, 4, 3, 1, 9, 5, 6, 8};
		for (int i = 0; i < input.length(); i += 10) {     //loop for each next ten letter
			for(int j = 0; j < 10; j++){                   //loop for each column
				result += input.charAt(i+secKey[j]-1);
	            //System.out.print(input.charAt(i+secKey[j]-1));
			}
		}
		return result;
	}
		
	//Hill Cipher
	public static String HillCipher(String input){
		String result = "";
		int[][] secKey = {{15, 0},                            //the key 2x2 matrix
				          {19, 9}};

		for (int i = 0; i < input.length(); i += 2) {         //loop for each next two letter
			int a = input.charAt(i)  -65;                     //digit of cipher letters
			int b = input.charAt(i+1)-65;   
			int plain_A = (a*secKey[0][0]+b*secKey[1][0])%26; //matrix multiplication for a
			int plain_B = (a*secKey[0][1]+b*secKey[1][1])%26; //matrix multiplication for b
			if (plain_A<0) plain_A+= 26;                      //deal with negative situation
			if (plain_B<0) plain_B+= 26;
			char char_A = (char)(plain_A+'A');                //convert int back to char
			char char_B = (char)(plain_B+'A');
			String output = String.valueOf(char_A);           //convert char to string
			result += output;                                 //append a to output
			output = String.valueOf(char_B);
			result += output;                                 //append b to output
            //System.out.print(char_A);
            //System.out.print(char_B);
		}
		return result;
	}
	
	//Vigenere Cipher
	public static void VigenereCipher(String input){
	}
	
}













