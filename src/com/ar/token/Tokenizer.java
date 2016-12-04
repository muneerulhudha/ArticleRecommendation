package com.ar.token;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;

public class Tokenizer {

  public static List<String> getTokens(String filename) throws FileNotFoundException{
	  
	  List<String> tokens = new LinkedList<String>();
	  
	  PTBTokenizer<CoreLabel> ptbt = new PTBTokenizer<>(new FileReader(filename),
              new CoreLabelTokenFactory(), "");
      while (ptbt.hasNext()) {
        CoreLabel label = ptbt.next();
        tokens.add(label.toString());
      }
	  return tokens;
  }

}