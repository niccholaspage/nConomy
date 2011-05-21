package com.niccholaspage.nConomy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
	public final String name;
	private List<String> cache = new ArrayList<String>();
	
	public FileHandler(String name){
		this.name = name;
		if (new File(name).exists() == false) return;
		String line = "";
		try {
			BufferedReader in = new BufferedReader(new FileReader(name));
			while((line = in.readLine()) != null) {
				cache.add(line);
			}
		  	}catch(FileNotFoundException fN) {
		  		fN.printStackTrace();
		  	}catch(IOException e) {
		  		e.printStackTrace();
		  	}
	}
	
	public void writeLine(String str){
		cache.add(str);
	}

	public List<String> getCache(){
		return cache;
	}
	
	public void writeCacheToFile(){
		new File(name).delete();
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(name,true));
			for (int i = 0; i < cache.size(); i++){
				out.write(cache.get(i) + "\n");
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
