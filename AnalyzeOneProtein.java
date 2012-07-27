/**
 * Programmers: Bryn Reinstadler and Jennifer Van
 * Date: July 25th, 2012
 * Filename: AnalyzeOneProtein.java
 * 
 * Purpose: Read one pdb file and one dssp file to string arrays,
 * after which the string arrays are sent into the sieve-like
 * infrastructure starting with ParseFiles.
 * */

import java.util.*;
import java.io.*;

public class AnalyzeOneProtein {

    public static ArrayList<String> dsspFile;
    public static ArrayList<String> pdbFile;
    public static String pdbID;
    
    /* The constructor method of AnalyzeOneProtein reads in a dssp file and a pdb file 
    	and then sends them both to be parsed. Right now the files are hard-coded in.
    	*/
    public AnalyzeOneProtein(String pdbID){
	//read in single file
	this.pdbID = pdbID;
	String core = pdbID.substring(1,3);
	dsspFile = readFile("C:/Users/Bryn/Documents/CodingProjects/dssp/" + pdbID + ".dssp");
	pdbFile = readFile("C:/Users/Bryn/Documents/CodingProjects/pdb/" + core + "/pdb" + pdbID + ".ent");
	//Opens parsefiles and gives it the arguments of the dsspFile and the pdbFile
	ParseFiles f2 = new ParseFiles(dsspFile, pdbFile);
    }

	/* The readFile method takes a the string of a file, opens it, and scans each line in the file
		to a new string in a string arraylist. */
    public ArrayList<String> readFile(String arg) {
		String thisLine;
		ArrayList<String> fileToRead = new ArrayList<String>();
		try {
		    //BufferedReader to scan in the file
		    BufferedReader buffy = new BufferedReader(new FileReader(arg));
		    while((thisLine = buffy.readLine()) != null && !thisLine.substring(0,7).trim().equals("ENDMDL")) { //while there are still lines in the document
				fileToRead.add(thisLine);
		    } //end while
		} // end try
		catch (IOException ioe) {
		    System.err.println("Error: " + ioe);
		    fileToRead.clear();
		}
		return fileToRead;
    }

    /* The printOut method takes an ArrayList of Strings and prints out each element of it. */
    public void printOut(ArrayList<String> al) {
		for (int i=0; i<al.size(); ++i){
		    System.out.println(al.get(i));
		}
    }
	
	/* Main method */
    /*public static void main(String[] args){
		AnalyzeOneProtein ap1 = new AnalyzeOneProtein();
		}*/
}