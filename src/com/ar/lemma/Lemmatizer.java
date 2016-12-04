package com.ar.lemma;

import java.util.List;

public class Lemmatizer {

	public static List<String> getLemmatizedWord(List<String> words){
		ParsingHelper helper = new ParsingHelper();
		List<String> lemma = helper.getLemmatizedforWord(words);
		
		return lemma;
	}
	
}
