package com.ar.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BaselineSystem {

	static Map<String, String> fileTitlesMap = new HashMap<String, String>();
	
	public static void init() throws FileNotFoundException, IOException{
		File folder = new File("bbc/business");
		File[] listOfFiles = folder.listFiles();

		for (File file : listOfFiles) {
			
		    if (file.isFile()) {
		    	try(BufferedReader br = new BufferedReader(new FileReader(file))){
		    		String line;
		    	    if ((line = br.readLine()) != null) {
		    	       fileTitlesMap.put(file.getName(), line);
		    	    }
		    	};
		    }
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		int max = 0;
		String reco = null;
		
		init();

		String inputString = "Yukos unit buyer faces loan claim";
		String[] inputArray = inputString.split(" ");
		
		//List<String> inputList = Arrays.asList(inputArray);
		
		final Set<String> input = Collections.unmodifiableSet(new HashSet<String>(Arrays.asList(inputArray)));
		
		
		
		for(String key: fileTitlesMap.keySet()){
			String compareString = fileTitlesMap.get(key);
			if(inputString.equals(compareString)){
				continue;
			}
			String[] compareArray = compareString.split(" ");
			List<String> compareList = new ArrayList<String>(Arrays.asList(compareArray));
			
			compareList.retainAll(input);
			int length = compareList.size();
			if(length > max){
				max = length;
				reco = compareString;
				System.out.println(key);
			}
		}
		
		System.out.println(reco);
		System.out.println(max);	
	}
	
}
