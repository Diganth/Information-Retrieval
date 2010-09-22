package IR1;
import java.util.*;

public class Query{

	static String[] documents;
	String query;
	static int errFlag = 0;
	static Hashtable<String, Integer[]> invtidx;
	
	public Query(String[] paragraphs) {
		documents = paragraphs;
	}
	
	static public void resolveQuery(String query){
		
		invtidx = Indexer.invtidx;
		String[] temp;
		Integer[][] tempanswers;
		System.out.println(query);
		if (query.contains("OR")){ //checking query to see if it contains "OR" //
			temp = query.split("OR");
			tempanswers = new Integer[temp.length][documents.length];
			for(int n=0;n<temp.length;n++)
			{
				tempanswers[n] = tokenize(temp[n], invtidx);
			}
			Integer[] result= new Integer[documents.length];
			Integer[] answer=new Integer[documents.length];
			result=tempanswers[0];
			int j=1;
			
			while(j < tempanswers.length){

				answer=disjunction(result, tempanswers[j]);
				result=answer;
				j++;
			}
			//Final result after executing the query which contains "OR" in it.
			
			for (int i =0; i < documents.length; i++){
				if(answer[i] != null)
					System.out.println("tempanswers for OR " + answer[i]);
			}
			
			Gui.result(errFlag, answer, documents);

		}
		else if (query.startsWith("\"") && query.endsWith("\"")){
			query = query.substring(1, query.length()-1);
			Integer ans[] = tokenize(query, invtidx);
		}
		else{
			Integer ans[] = tokenize(query, invtidx);
			
			//Final result after executing the query which contains CONJUNCTIVE in it.
			for (int i =0; i < ans.length; i++){
				if(ans[i] != null)
					System.out.println("tempanswers for NOT " + ans[i]);
			}
			Gui.result(errFlag, ans, documents);
		}
	}
	/*
	 * This function tokenizes the query and calls the appropriate operations
	 */
	public static Integer[] tokenize(String temp, Hashtable<String, Integer[]> invtidx){

		Hashtable<String, Integer[]> postings = new Hashtable<String, Integer[]>(); 
		StringTokenizer st = new StringTokenizer(temp);
		String[] tokens= new String[st.countTokens()];
		int i=0;

		while(st.hasMoreTokens()) { //inserting all the tokens from a part of the query to a string array //

			tokens[i]=st.nextToken();
			i++;
		}

		for(String str:tokens)
		{
			if ((str.contains("-")==true)&& str.indexOf("-")==0) { //checking the query for negation symbols
				str = str.substring(1);
				if(invtidx.containsKey(str)) {
					postings.put(str,negation(invtidx.get(str)));
				}
				else{
					System.out.println("Token" + str + "not found in Document. Please retype query !!");
					errFlag = 1;
					return null;
				}
			}
			else {
				if(invtidx.containsKey(str)) {
					postings.put(str,invtidx.get(str));
				}
				else{
					System.out.println("Token" + str + "not found in Document. Please retype query !!");
					errFlag = 1;
					return null;
				}
			}

		}
		
		if(tokens.length != 1){
			Integer[] answer = intersect(postings);
			errFlag = 0;
			return answer;
		}
		else {
			if ((tokens[0].contains("-")==true)&& tokens[0].indexOf("-")==0) {
				tokens[0] = tokens[0].substring(1);
				Integer[] answer = postings.get(tokens[0]);
				errFlag = 0;
				return answer;
			}
			else{
				Integer[] answer = postings.get(tokens[0]);
				errFlag = 0;
				return answer;
			}
		}
}
	
	/*
	 * Intersection Function
	 */
	
	public static Integer[] intersect(Hashtable<String, Integer[]> posting)
	{
		Integer [][] poslist = new Integer[posting.size()][documents.length];
		int i=0;
		for (Enumeration e = posting.keys(); e.hasMoreElements();)
		{
			String nextElement = (String) e.nextElement();
			poslist[i]  = (Integer[])posting.get( nextElement );
			i++;
		}
		Integer[] result= new Integer[documents.length];
		Integer[] answer=new Integer[documents.length];
		result=poslist[0];
		int j=1;
		System.out.println(" Poslist length : " + poslist.length);
		while(j < poslist.length)
		{

			answer=intersectPostings(result, poslist[j]);
			result=answer;
			j++;

		}
		return answer;

	}

	public static Integer[] intersectPostings(Integer[] result,Integer[] poslist )
	{
		Integer[] answer = new Integer[documents.length];

		int i=0;
		int k=0;
		int r=0;
		while(i < result.length && k < poslist.length && result[i] !=null && poslist[k] != null )
		{
			if(result[i]==poslist[k])
			{
				answer[r++]=result[i];
				i++;
				k++;
			}
			else if(result[i]<poslist[k])
			{ 
				i++;
			}
			else
			{
				k++;

			}
		}
		for (int l =0; l < documents.length; l++){
			if(answer[l] != null)
				System.out.println( answer[i]);
		}

		return answer;
	}

	public static Integer[] disjunction(Integer[] result,Integer[] poslist ){

		Integer[] answer=new Integer[documents.length];

		int i=0;
		int k=0;
		int r=0;
		while(i < result.length && k < poslist.length && result[i] !=null && poslist[k] != null)
		{
			if(result[i]==poslist[k])
			{
				answer[r++]=result[i];
				i++;
				k++;
			}
			else if(result[i]<poslist[k])
			{ 
				answer[r++]=result[i];
				i++;
			}
			else
			{
				answer[r++]=poslist[k];
				k++;
			}
		}
		if(result[i]==null)
		{
			while(poslist[k]!=null)
			{ 
				answer[r++]=poslist[k];
				k++;
			}
		}
		else
		{
			while(result[i]!=null)
			{
				answer[r++]=result[i];
				i++;
			}
		}

		return answer;

	}

	public static Integer[] negation(Integer[] list){
		
		Integer[] normlist=new Integer[documents.length];
		Integer[] neglist= new Integer[documents.length];
		
		int k=0;
		int r=0;
		int j=0;

		for(int i=0;i<documents.length;i++)
		{
			normlist[i]=i;
		}
		while(k<documents.length && list[j] != null)
		{
			if(normlist[k]==list[j])
			{
				j++;
				k++;
			}
			else if(normlist[k]<list[j])
			{ 
				neglist[r++]=normlist[k];
				k++;
			}

			else
			{
				j++;

			}
			if(list[j]==null)
			{
				while(k<documents.length)
				{
					neglist[r++]=normlist[k];
					k++;
				}
			}
		}
		return neglist;
	}
}

