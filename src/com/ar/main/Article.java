package com.ar.main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Article implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String content;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	private String fileName;
	private List<String> nouns = new ArrayList<String>();
	private List<String> tokens = new ArrayList<String>();
	private List<String> lemmatizedWords = new ArrayList<String>();
	private List<String> hypernymy = new ArrayList<String>();
	private List<String> namedEntities = new ArrayList<String>();
	private List<String> headWords = new ArrayList<String>();
	
	public List<String> getNamedEntities() {
		return namedEntities;
	}
	public List<String> getHeadWords() {
		return headWords;
	}
	public void setHeadWords(List<String> headWords) {
		this.headWords = headWords;
	}
	public void setNamedEntities(List<String> namedEntities) {
		this.namedEntities = namedEntities;
	}
	public List<String> getHypernymy() {
		return hypernymy;
	}
	public void setHypernymy(List<String> hypernymy) {
		this.hypernymy = hypernymy;
	}
	public List<String> getLemmatizedWords() {
		return lemmatizedWords;
	}
	public void setLemmatizedWords(List<String> lemmatizedWords) {
		this.lemmatizedWords = lemmatizedWords;
	}
	public List<String> getTokens() {
		return tokens;
	}
	public void setTokens(List<String> tokens) {
		this.tokens = tokens;
	}
	public String getFileName() {
		return fileName;
	}
	public List<String> getNouns() {
		return nouns;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public void setNouns(List<String> nouns) {
		this.nouns = nouns;
	}
	
	
}
