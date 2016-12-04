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
 
//    private final String regEXRemoveUppStop="(?:'(?:[tdsm]|[vr]e|ll))+\\b";
//    private final String regEXpunc="[,;'\"?]";
//    private final String regEXequal = "=";
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

////    public String cleanSentence(String input)
////    {
////        input = input.replaceAll(regEXRemoveUppStop, "");
////        input = input.replaceAll(regEXpunc,"");
////        input = input.replaceAll(regEXequal, " ");
////        //System.out.println(input);
////        return input;
////    }          
//   
////    public String getTaggedSentence(String input)
////    {
////        //System.out.println("input : \n" + input);
////       
////        String POSSentence="";
////        List<List<HasWord>> sentences = MaxentTagger.tokenizeText(new StringReader(input));
////        for (List<HasWord> sentence : sentences) {
////           List<TaggedWord> tSentence = this.tagger.tagSentence(sentence);
////           POSSentence = POSSentence.concat(Sentence.listToString(tSentence, false));
////        }
////        //System.out.println("POS : \n" + POSSentence);
////        return POSSentence;
////    }
//   
//    public HashMap<String,LinkedList<String>> getTaggedMap(String input,String taggedSentence)
//    {
//       
//          //System.out.println(input);
//          //System.out.println(taggedSentence);
//          String tokenizeRequest[] = input.split("\\s+");
//          //System.out.println(taggedSentence);
//          String tokenizePOS[] = taggedSentence.split("\\s+");
//          //System.out.println(tokenizeRequest.length);
//          //System.out.println(tokenizePOS.length);
//         
//          HashMap<String,LinkedList<String>> hm=new HashMap<>();
//          for(int i = 0;i<tokenizePOS.length;i++){
//              String tagsplit[] = tokenizePOS[i].split("/");
//              String tag=tagsplit[1];
//              String word=tagsplit[0];
//              LinkedList<String> list;
//              if(hm.containsKey(tag))
//                  list=hm.get(tag);
//              else
//                  list=new LinkedList<>();
//              list.add(word);
//              hm.put(tag, list);
//          }
//        return hm;
//    }
//   
//   
//    public String changePluralToSingular(String input,HashMap<String,LinkedList<String>> hm)
//    {
//        LinkedList<String> list=new LinkedList<>();
//        if(hm.containsKey("NNS"))
//            list.addAll(hm.get("NNS"));
//        if(hm.containsKey("NNPS"))
//            list.addAll(hm.get("NNPS"));
//       
//        HashMap<String,String> hMap=new HashMap<>();
////      for(String value: list)
////      {
////          String ans = chatSession.multisentenceRespond("singular word of " + value);
////          if(!ans.equalsIgnoreCase("I have no answer for that.") && !ans.equalsIgnoreCase("unknown"))
////          {  
////                hMap.put(value, ans);
////          }
////      }
//       
//        String temp[]=input.split("\\s+");
//        String result=null;
//       
//        for(String value: temp)
//        {
//            String toReplace=value;
//            if(hMap.containsKey(value))
//            {
//                toReplace=hMap.get(value);
//            }
//           
//            if(result==null)
//                result=toReplace;
//            else
//                result=result+" "+toReplace;
//        }
//        return result;
//    }

  public List<String> getLemmatizedforWord(List<String> nouns)
    {
      String input="";
      for(String s:nouns){
          if(input == null){
              input = input + s;
          }
          else{
          input = input + " " + s;
          }
      }
        HashMap<String,String> hMap=getLamatizedforWord(input);
        String returnValue=null;
        String temp[]=input.split("\\s+");
       
        for(String value: temp)
        {
            String toReplace=value;
            if(hMap.containsKey(value))
            {
                toReplace=hMap.get(value);
            }
           
            if(returnValue==null)
                returnValue=toReplace;
            else
                returnValue=returnValue+" "+toReplace;
     
        }
        String[] strTemp = returnValue.split(" ");
        List<String> returnList = new ArrayList<>();
        for(int i=1;i<strTemp.length;i++){
            returnList.add(strTemp[i]);
        }
        return returnList;
    }
   
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