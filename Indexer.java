package IR1;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Hashtable;

public class Indexer {

	static Hashtable<String, Integer[]> invtidx = new Hashtable<String, Integer[]>();
	Hashtable<String, Integer[][]> posidx = new Hashtable<String, Integer[][]>();
	String[] paragraphs, positions;
	Integer[] docID;
	
	Integer[][] position;
	Query query; 
	Gui gui;
	int docSize;

	
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
		docSize = data.length();

		paragraphs = data.split("\r\n\r\n");
		
		query = new Query(paragraphs);

		/*System.out.println("Number of paragraphs of text: " + paragraphs.length + "\n");

		for (int i = 0; i < paragraphs.length; i++) {
			System.out.println("Paragraph " + (i + 1) + ": " + paragraphs[i] + "\n------------------------\n");
		}*/

	}

	

	public void createIndextable(Hashtable temp){

		try{
			for (Enumeration e = temp.keys(); e.hasMoreElements();){

				int j=0, l=0;
				/*
				 * creating an array of Integers to store the location of the paragraphs 
				 * in which the tokens are found in and also the positions of the 
				 */
				docID = new Integer[paragraphs.length];
				System.out.println(docSize);
				position = new Integer[paragraphs.length][1000];
				String str = (String) temp.get( e.nextElement() );

				for (int i = 0; i < paragraphs.length; i++) {
					if(paragraphs[i].contains(str)){
						docID[j] = i;
						j+=1;
						positions =  paragraphs[i].split("\\s+");
						for (int k = 0; k < positions.length; k++){
							if (positions[k].contains(str)) { 
								position[i][l] = k;
								l+=1;
							}
						}
					}
					// Adding the token name and the documents in which they are found into a hashtable "invtidx"
					invtidx.put(str.toLowerCase(), docID);
					posidx.put(str, position);
				}

			}

			/*
			 * Printing the contents of the Inverted Index hashtable and the position index
			 */
			/*for (Enumeration e = invtidx.keys(); e.hasMoreElements();){

				String nextElement = (String) e.nextElement();
				Integer[] str = (Integer[])invtidx.get( nextElement );
				Integer[][] str1 = (Integer[][])posidx.get( nextElement );
				System.out.print(nextElement);
				for (int i = 0; i < str.length; i++) {
					if(str[i] != null){
						System.out.print( " --> " + str[i]  );
					}
				}
				System.out.println("\n");
				System.out.println("Token \"" + nextElement + "\" is positioned at ");
				for ( int i = 0; i< str1.length; i++){
					for ( int j = 0; j < 600; j++){
						if (str1[i][j] != null){
							System.out.println("-->" + str1[i][j] + " in DOCUMENT" + i);
						}
					}
				}
			}*/
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                gui.createAndShowGUI();
	            }
	        });

			//gui.createAndShowGUI();

		}catch (Exception e){
			e.printStackTrace();
		}
	}



}