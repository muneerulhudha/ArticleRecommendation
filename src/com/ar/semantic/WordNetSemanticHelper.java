package com.ar.semantic;
import java.util.LinkedList;
import java.util.List;

import edu.smu.tspell.wordnet.NounSynset;
import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.SynsetType;
import edu.smu.tspell.wordnet.WordNetDatabase;

public class WordNetSemanticHelper {
	
	private static WordNetDatabase database;

	public static List<String> getHypernymy(List<String> lemma){
		List<String> hypernymy = new LinkedList<String>();
		System.setProperty("wordnet.database.dir", "/Users/muneerhudha/code/jars/WordNet-2.1/dict");
		
		database = WordNetDatabase.getFileInstance();
		
		for(String str: lemma){
			Synset[] nounSynsets = database.getSynsets(str, SynsetType.NOUN);
			
			for (Synset si : nounSynsets) {
				NounSynset[] ns=((NounSynset)si).getHypernyms();    
				
				for(NounSynset n: ns)
				{
					String[] s=n.getWordForms();
					 
					    for(String ss: s)
					    {
					    	hypernymy.add(ss);
					    }
				}
	        }
		}
		
		return hypernymy;
	}

}