package com.ar.pos;

import java.util.List;
import java.util.Map;

public class POSTagger {
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<String> getNouns(String content){
		ParsingHelper parseHelper = new ParsingHelper();
		
		String input = parseHelper.cleanSentence(content);
		
		String taggedSentence=parseHelper.getTaggedSentence(input);
		Map taggedMap= parseHelper.getTaggedMap(input, taggedSentence);
		
		return (List<String>) taggedMap.get("NN");
		
	}

}