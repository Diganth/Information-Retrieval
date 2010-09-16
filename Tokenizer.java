package IR1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import java.util.Hashtable;

public class Tokenizer {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 * 
	 */
	
	static Pattern p;
	static Hashtable words = new Hashtable();
	static Hashtable sortedwords = new Hashtable();
	
	 public static void main(String[] args) throws FileNotFoundException {

	    FileReader file = new FileReader("E:\\Eclipse-Workspace\\Workspace1\\IR1\\src\\IR1\\sample.txt");
	    BufferedReader br = new BufferedReader(file);
	    String storeWordList = "";
	    try {
 
	      while ((storeWordList = br.readLine()) != null) {
	    	    
	    	  StringTokenizer st = new StringTokenizer(storeWordList);
	    	  while (st.hasMoreTokens()){
	    		  //System.out.println(st.nextToken());
	    		  try
			      {
			         p = Pattern.compile ("[\\p{Punct}]"); //matching pattern for punctuation
			      }
			      catch (PatternSyntaxException e)
			      {
			         System.err.println ("Regex syntax error: " + e.getMessage ());
			         System.err.println ("Error description: " + e.getDescription ());
			         System.err.println ("Error index: " + e.getIndex ());
			         System.err.println ("Erroneous pattern: " + e.getPattern ());
			         return;
			      }
			      String token = st.nextToken().toLowerCase();
			      Matcher m = p.matcher (token);
			      if(m.find() == true){
			    	 while(m.find ()){}
			    	  token=m.replaceAll("");
			      }
			      
			      if(!token.isEmpty()){
			    	  System.out.println("text 1 = " + token);
			    	  words.put(token,token);
			      }
		  	  }
	      }
	      System.out.println("Contents of hash table:" + words.values());
	      	      
	      // dispose all the resources after using them.
	      br.close();

	    } catch (FileNotFoundException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	  }
}

