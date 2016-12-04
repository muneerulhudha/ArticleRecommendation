package com.ar.main;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ar.util.MapUtil;

public class Recommender {

	static Map<String, Article> articles = new HashMap<String, Article>();
	static Map<String, Integer> countMap = new HashMap<String, Integer>();
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void loadArticles() throws ClassNotFoundException{
		try {
			FileInputStream fis = new FileInputStream("articles.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			articles = (HashMap) ois.readObject();
			ois.close();
			fis.close();
		} catch(IOException ioe) {
			ioe.printStackTrace();
			return;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws ClassNotFoundException {
		loadArticles();		
		//printMap();
		
		String input = "003.txt";
		Article inputArticle = articles.get(input);
		
		List<String> inputNamedEntity = inputArticle.getNamedEntities();
		List<String> inputHeadWords = inputArticle.getHeadWords();
		List<String> inputHypernymy = inputArticle.getHypernymy();
		
		for(Article a: articles.values()){
			int count1 = 0, count2 = 0, count3 = 0, count = 0;
			List<String> testNamedEntity = a.getNamedEntities();
			List<String> testHeadWords = a.getHeadWords();
			List<String> testHypernymy = a.getHypernymy();
			
			List<String> common1 = new ArrayList<String>(inputNamedEntity);
			common1.retainAll(testNamedEntity);
			count1 = common1.size();
			
			List<String> common2 = new ArrayList<String>(inputHeadWords);
			common2.retainAll(testHeadWords);
			count2 = common2.size();
			
			List<String> common3 = new ArrayList<String>(inputHypernymy);
			common3.retainAll(testHypernymy);
			count3 = common3.size();
						
			count = (count1 * 4) + count2 + (count3 * 2);
			countMap.put(a.getFileName(), count);
		}
		
		LinkedHashMap sortedCountMap = (LinkedHashMap) MapUtil.sortByValue(countMap);
		
		System.out.println("Input Article :");
		System.out.println(inputArticle.getContent());
		System.out.println("\n\n\n");
		
		// Get a set of the entries
		Set set = sortedCountMap.entrySet();
	      
		// Get an iterator
		Iterator i = set.iterator();
	      
		int j = 0;
		
		System.out.println("Recommendations : ");
		System.out.println();
		
	    // Display elements
	    while(i.hasNext()) {
	    	j++;
	    	Map.Entry me = (Map.Entry)i.next();
	    	if(j==1)
	    		continue;
	    	if(j>4)
	    		break;
	        System.out.println(me.getKey());
	        Article art = articles.get(me.getKey());
	        System.out.println(art.getContent());
	        System.out.println("\n\n\n");
	    }
		
	}

	@SuppressWarnings("unused")
	private static void printMap() {
		// TODO Auto-generated method stub
		for(Article a: articles.values()){
			System.out.println(a.getFileName());
			System.out.println("Nouns :");
			System.out.println(a.getNouns());
			System.out.println("Lemmatized :");
			System.out.println(a.getLemmatizedWords());
			System.out.println("Tokens :");
			System.out.println(a.getTokens());
			System.out.println("Hypernymy : ");
			System.out.println(a.getHypernymy());
			System.out.println("Named Entities :");
			System.out.println(a.getNamedEntities());
			System.out.println("Head words : ");
			System.out.println(a.getHeadWords());
		}
	}
	
	@SuppressWarnings("unused")
	private static void printArticle(Article a) {
		System.out.println(a.getFileName());
		System.out.println("Nouns :");
		System.out.println(a.getNouns());
		System.out.println("Lemmatized :");
		System.out.println(a.getLemmatizedWords());
		System.out.println("Tokens :");
		System.out.println(a.getTokens());
		System.out.println("Hypernymy : ");
		System.out.println(a.getHypernymy());
		System.out.println("Named Entities :");
		System.out.println(a.getNamedEntities());
		System.out.println("Head words : ");
		System.out.println(a.getHeadWords());
	}
}
