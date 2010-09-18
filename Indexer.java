package IR1;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Hashtable;

public class Indexer {

	Hashtable<String, String[]> invtidx = new Hashtable<String, String[]>();
	String[] paragraphs;
	String[] docID;

	public Indexer(String filename) {

		FileInputStream fis;
		InputStreamReader isr;
		char[] text = null;

		try {
			fis = new FileInputStream(new File(filename));
			isr = new InputStreamReader(fis);
			text = new char[fis.available()];
			isr.read(text, 0, fis.available());
			isr.close();
			fis.close();
		}
		catch(Exception e){
			e.printStackTrace();;
		}

		String data = new String(text);

		paragraphs = data.split("\r\n\r\n");

		System.out.println("Number of paragraphs of text: " + paragraphs.length + "\n");

		for (int i = 0; i < paragraphs.length; i++) {
			System.out.println("Paragraph " + (i + 1) + ": " + paragraphs[i] + "\n------------------------\n");
		}

	}

	public void createIndextable(Hashtable temp){

		try{

			for (Enumeration e = temp.keys(); e.hasMoreElements();){
				int j=0;
				docID = new String[paragraphs.length];
				String str = (String) temp.get( e.nextElement() );

				System.out.println(str);
				for (int i = 0; i < paragraphs.length; i++) {
					if(paragraphs[i].contains(str)){
						docID[j]=String.valueOf(i+1);
						//System.out.println("Doc ID is : " + docID[j]);
						j+=1;
					}
				}
				// Adding the token name and the documents in which they are found into a hashtable "invtidx"
				invtidx.put(str, docID);
			}

			for (Enumeration e = invtidx.keys(); e.hasMoreElements();){

				String nextElement = (String) e.nextElement();
				String[] str = invtidx.get( nextElement );
				for (int i = 0; i < str.length; i++) {
					if(str[i] != null){
						System.out.println("Paragraph in which token \"" + nextElement +"\" is located in is: " + str[i]  );
					}

				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}

}