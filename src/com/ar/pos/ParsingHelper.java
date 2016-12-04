package com.ar.pos;

import java.io.StringReader;
//import org.alicebot.ab.Chat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class ParsingHelper {
	
    private final String regEXRemoveUppStop="(?:'(?:[tdsm]|[vr]e|ll))+\\b";
    private final String regEXpunc="[,;'\"?]";
    private final String regEXequal = "=";
    private final String taggarName="english-left3words-distsim.tagger";
    private MaxentTagger tagger;
    private StanfordCoreNLP pipeline;
	ParsingHelper()
    {
  	    this.tagger = new MaxentTagger(taggarName);  
  	    Properties props = new Properties(); 
          props.put("annotators", "tokenize, ssplit, pos, lemma"); 
          this.pipeline = new StanfordCoreNLP(props, false);
    }

    public String cleanSentence(String input)
    { 
  	  input = input.replaceAll(regEXRemoveUppStop, "");
  	  input = input.replaceAll(regEXpunc,"");
  	  input = input.replaceAll(regEXequal, " ");
  	  //System.out.println(input);
  	  return input;
    }
    
    public String getTaggedSentence(String input)
    {
  	  //System.out.println("input : \n" + input);
  	  
  	  String POSSentence="";
        List<List<HasWord>> sentences = MaxentTagger.tokenizeText(new StringReader(input));
        for (List<HasWord> sentence : sentences) {
	         List<TaggedWord> tSentence = this.tagger.tagSentence(sentence);
	         POSSentence = POSSentence.concat(Sentence.listToString(tSentence, false));
	      }
        //System.out.println("POS : \n" + POSSentence);
        return POSSentence;
    }
    
    public HashMap<String,LinkedList<String>> getTaggedMap(String input,String taggedSentence)
    {
  	  
  	  	//System.out.println(input);
  	  	//System.out.println(taggedSentence);
  	    String tokenizeRequest[] = input.split("\\s+");
  	    //System.out.println(taggedSentence);
		    String tokenizePOS[] = taggedSentence.split("\\s+");
		    //System.out.println(tokenizeRequest.length);
		    //System.out.println(tokenizePOS.length);
		    
		    HashMap<String,LinkedList<String>> hm=new HashMap<>();
			for(int i = 0;i<tokenizePOS.length;i++){
				String tagsplit[] = tokenizePOS[i].split("/");
				String tag=tagsplit[1];
				String word=tagsplit[0];
				LinkedList<String> list;
				if(hm.containsKey(tag))
					list=hm.get(tag);
				else
					list=new LinkedList<>();
				list.add(word);
				hm.put(tag, list);
	        }
  	  return hm;
    }
    
}