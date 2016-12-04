package com.ar.main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.ar.lemma.Lemmatizer;
import com.ar.ner.NER;
import com.ar.pos.POSTagger;
import com.ar.semantic.WordNetSemanticHelper;
import com.ar.token.Tokenizer;
import com.ar.head.HeadWordParser;

public class ArticleParser {

	static Map<String, Article> articles = new HashMap<String, Article>();
	
	@SuppressWarnings("deprecation")
	public static void preprocess() throws IOException{
		File folder = new File("bbc/business");
		File[] listOfFiles = folder.listFiles();
		
		//*************************************************************
		//Getting Tokens using Stanford Tokenizer
		//*************************************************************
		for (File file : listOfFiles) {
			Article a = new Article();
			a.setFileName(file.getName());
			
			List<String> tokens = Tokenizer.getTokens(file.getAbsolutePath());
			a.setTokens(tokens);
			
			System.out.println( "Fetching tokens.... " + file.getName());
			articles.put(file.getName(), a);
		}
		
		//*************************************************************
		//Getting File Contents into articles
		//*************************************************************
		for (File file : listOfFiles) {
			Article a = articles.get(file.getName());
			
			String fileContent = FileUtils.readFileToString(file);
			
			a.setContent(fileContent);
			
			System.out.println("File Content ... " + file.getName());
			articles.put(file.getName(), a);
		}
		
		//*************************************************************
		//Getting Nouns using POS Tagger
		//*************************************************************
		for (File file : listOfFiles) {
			Article a = articles.get(file.getName());
			
			String fileContent = FileUtils.readFileToString(file);
			List<String> nouns = POSTagger.getNouns(fileContent);
			
			a.setNouns(nouns);
			
			System.out.println("POS Tagging ... " + file.getName());
			articles.put(file.getName(), a);
		}
		
		//*************************************************************
		//Getting List of Lemmatized words for nouns
		//*************************************************************
		for(File file: listOfFiles) {
			Article a = articles.get(file.getName());
			
			List<String> lemmatizedWords = Lemmatizer.getLemmatizedWord(a.getNouns());
			
			//System.out.println(lemmatizedWords);
			
			a.setLemmatizedWords(lemmatizedWords);
			System.out.println("Lemmatizing ... " + file.getName());
			articles.put(file.getName(), a);
		}
		
		//*************************************************************
		//Getting Hypernymy for nouns
		//*************************************************************
		for(File file: listOfFiles) {
			Article a = articles.get(file.getName());
			
			List<String> hypernymy = WordNetSemanticHelper.getHypernymy(a.getLemmatizedWords());
			
			a.setHypernymy(hypernymy);
			System.out.println("Fetching nouns  .... " + file.getName());
			articles.put(file.getName(), a);
		}
		
		//*************************************************************
		//Getting NamedEntities using StanfordNER
		//*************************************************************
		for(File file: listOfFiles) {
			Article a = articles.get(file.getName());
			
			String fileContent = FileUtils.readFileToString(file);
			List<String> ner = NER.identifyNER(fileContent);
			
			a.setNamedEntities(ner);
			System.out.println("Fetching Named Entities ... " + file.getName());
			articles.put(file.getName(), a);
		}
		
		//*************************************************************
		//Getting HeadWords for NounPhrases
		//*************************************************************
		for(File file: listOfFiles) {
			Article a = articles.get(file.getName());
			
			String fileContent = FileUtils.readFileToString(file);
			List<String> head = HeadWordParser.getHeadWords(fileContent);
			
			a.setHeadWords(head);
			System.out.println("Fetching HeadWords .... " + file.getName());
			articles.put(file.getName(), a);
		}
		
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		
		preprocess();
		writeToFile();
		
	}

	private static void writeToFile() {
		// TODO Auto-generated method stub
		try {
			FileOutputStream fos = new FileOutputStream("articles.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(articles);
			oos.close();
			fos.close();
			System.out.printf("Serialized HashMap data is saved in articles.ser");
        } catch(IOException ioe) {
        	ioe.printStackTrace();
        }
	}
	
}
