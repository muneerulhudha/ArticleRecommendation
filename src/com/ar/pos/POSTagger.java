package com.ar.pos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class POSTagger {
	
	@SuppressWarnings("unchecked")
	public static List<String> getNouns(String content){
		ParsingHelper parseHelper = new ParsingHelper();
		
		String input = parseHelper.cleanSentence(content);
		
		String taggedSentence=parseHelper.getTaggedSentence(input);
		Map taggedMap= parseHelper.getTaggedMap(input, taggedSentence);
		
		return (List<String>) taggedMap.get("NN");
		
		//return null;
	}
	
	
//	public static void main(String args[])
//	{
//		String line;
//		ParsingHelper parseHelper = new ParsingHelper();
//		String filePath = "bbc/business";
//		try {
//			BufferedReader reader = new BufferedReader(new FileReader(filePath));
//			//Map taggedMap = new HashMap<String, LinkedList<String>>();
//		
//		
//			while((line = reader.readLine())!=null){
//			String input=parseHelper.cleanSentence(line);
//			if(!input.equals("")){
//				String taggedSentence=parseHelper.getTaggedSentence(input);
//				Map taggedMap= parseHelper.getTaggedMap(input, taggedSentence);	
//				
////				taggedMap.values();
////				Map maptemp=new HashMap<String, String>();
////				List<String> temp = (List<String>)maptemp.values();
//				
////				List<List<String>> test1 = (List<List<String>>)taggedMap.values();
//				
//				System.out.println(taggedMap.get("NN"));
////				for(Map<String, LinkedList<String>> entry : taggedMap.entrySet()) {
////				    
////					
////					String key = entry.getKey();
////				    LinkedList<String> value = entry.getValue();
////				}
////				Iterator itr = (Iterator) taggedMap.keySet().iterator();
////			    while(((Object) itr).hasnext()) {
////			        String key = itr.next();
////			    }
////				if(!taggedMap.isEmpty()){   // list is an ArrayList
////
////		            for(int k = 0; k < taggedMap.size(); k++){
////		                HashMap = (HashMap)taggedMap.get(k);
////		            }
////
////		        }
//				
//
//			}
//			
////			String taggedSentence=parseHelper.getTaggedSentence(input);
////			HashMap taggedMap= parseHelper.getTaggedMap(input, taggedSentence);
////
////			String allWordsSingular=parseHelper.changePluralToSingular(input, taggedMap);
////			System.out.println(allWordsSingular);
////			String lamatizedInput=parseHelper.getLemmatizedforWord(taggedMap, allWordsSingular);
////			System.out.println(lamatizedInput);
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}