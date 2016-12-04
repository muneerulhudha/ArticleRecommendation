package com.ar.ner;

import java.util.LinkedList;
import java.util.List;

import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
 
public class NER {
	
	public static List<String> identifyNER(String text) {
		String serializedClassifier = "/Users/muneerhudha/code/jars/stanford-ner-2014-01-04/classifiers/english.muc.7class.distsim.crf.ser.gz";
		List<String> NamedEntity = new LinkedList<String>();
		
		CRFClassifier<CoreLabel> classifier = CRFClassifier.getClassifierNoExceptions(serializedClassifier);
		List<List<CoreLabel>> classify = classifier.classify(text);
		for (List<CoreLabel> coreLabels : classify) {
			for (CoreLabel coreLabel : coreLabels) {
				String word = coreLabel.word();
				String category = coreLabel.get(CoreAnnotations.AnswerAnnotation.class);
				if(!"O".equals(category)) {
					NamedEntity.add(word);
				}
			}
		}
		return NamedEntity;
	}
	
}