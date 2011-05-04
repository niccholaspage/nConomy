package com.niccholaspage.nConomy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileHandler {
	public final String name;
	private BufferedWriter out;
	private BufferedReader in;
	
	public FileHandler(String name){
		this.name = name;
	      try{
	    	  out = new BufferedWriter(new FileWriter(name,true));
	    	  in = new BufferedReader(new FileReader(name));
	      }catch (Exception e){
	    	  e.printStackTrace();
	      }
	}
	
	public void close(){
		try {
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeLine(String str){
		try {
			out.write(str + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public ArrayList<String> filetoarray(){
		  String line = "";
		  ArrayList<String> data = new ArrayList<String>();
		  try {
		   while((line = in.readLine()) != null) {
		    data.add(line);
		   }
		  }catch(FileNotFoundException fN) {
			  fN.printStackTrace();
		  }catch(IOException e) {
			  e.printStackTrace();
		  }
		  return data;
	}
}
