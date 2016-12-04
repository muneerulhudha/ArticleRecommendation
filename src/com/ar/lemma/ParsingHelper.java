package com.ar.lemma;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.util.CoreMap;

public class ParsingHelper {
 
    private final String taggarName="english-left3words-distsim.tagger";
    @SuppressWarnings("unused")
	private MaxentTagger tagger;
    private StanfordCoreNLP pipeline;
   
    ParsingHelper() {
          this.tagger = new MaxentTagger(taggarName);  
          Properties props = new Properties();
          props.put("annotators", "tokenize, ssplit, pos, lemma");
          this.pipeline = new StanfordCoreNLP(props, false);
    }

    @SuppressWarnings("unchecked")
  	public List<String> getLemmatizedforWord(List<String> nouns) {
		String input="";
		for(String s:nouns) {
			if(input == null) {
				input = input + s;
		    } else {
		    	input = input + " " + s;
		    }
		}
		HashMap<String,String> hMap=getLamatizedforWord(input);
		String returnValue=null;
		String temp[]=input.split("\\s+");
		   
		for(String value: temp) {
			String toReplace=value;
			if(hMap.containsKey(value)) {
			    toReplace=hMap.get(value);
			}
			   
			if(returnValue==null)
			    returnValue=toReplace;
			else
			    returnValue=returnValue+" "+toReplace;
			 
		}
		String[] strTemp = returnValue.split(" ");
		List<String> returnList = new ArrayList<>();
		for(int i=1;i<strTemp.length;i++) {
		    returnList.add(strTemp[i]);
		}
		return returnList;
    }
   
    @SuppressWarnings("rawtypes")
	private HashMap getLamatizedforWord(String input)
    {
        HashMap<String,String> hm=new HashMap<>();
        Annotation document = this.pipeline.process(input);  
        for(CoreMap sentence: document.get(SentencesAnnotation.class))
        {    
            for(CoreLabel token: sentence.get(TokensAnnotation.class))
            {      
                String word = token.get(TextAnnotation.class);      
                String lemma = token.get(LemmaAnnotation.class);
                hm.put(word, lemma);
            }
        }
        return hm;
    }

}