package com.ar.head;

import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.Tokenizer;
import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.trees.HeadFinder;
import edu.stanford.nlp.trees.PennTreebankLanguagePack;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.tregex.TregexMatcher;
import edu.stanford.nlp.trees.tregex.TregexPattern;

public class HeadWordParser {

    private final static String PCG_MODEL = "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz";        

    private final TokenizerFactory<CoreLabel> tokenizerFactory = PTBTokenizer.factory(new CoreLabelTokenFactory(), "invertible=true");

    private final static LexicalizedParser parser = LexicalizedParser.loadModel(PCG_MODEL);

    public Tree parse(String str) {                
        List<CoreLabel> tokens = tokenize(str);
        Tree tree = parser.apply(tokens);
        return tree;
    }

    private List<CoreLabel> tokenize(String str) {
        Tokenizer<CoreLabel> tokenizer =
            tokenizerFactory.getTokenizer(
                new StringReader(str));    
        return tokenizer.tokenize();
    }

    public static List<String> getHeadWords(String content) { 
    	List<String> headWords = new LinkedList<String>();
    	
    	//String str = "Ad sales boost Time Warner profit";
        HeadFinder hf = new PennTreebankLanguagePack().headFinder();
        
        Reader reader = new StringReader(content);
        DocumentPreprocessor dp = new DocumentPreprocessor(reader);
        
        for (List<HasWord> sentence : dp) {
    	   String sentenceString = Sentence.listToString(sentence);
    	   Tree tree = parser.parse(sentenceString);  
           
           TregexPattern tPattern = TregexPattern.compile("NP");
           TregexMatcher tMatcher = tPattern.matcher(tree);
           while (tMatcher.find()) {
             Tree nounPhrase = tMatcher.getMatch();

             Tree headConstituent = hf.determineHead(nounPhrase);
//             System.out.println("NP : " + nounPhrase);
//             System.out.println("HC : " + headConstituent);
             
//             String headCons = headConstituent.toString();
//             char[] headChar = headCons.toCharArray();
//             char[] headCharRemovedBrackets = Arrays.copyOfRange(headChar, 1, headChar.length -1);
//             String[] words = new String(headCharRemovedBrackets).split(" ");
//             String headWord = words[1];
             
             headWords.add(headConstituent.toString());
           }
    	}
		return headWords;
    }
}