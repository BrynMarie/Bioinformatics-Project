import java.util.*;
import java.io.*;

public class AnalyzeOneProtein {

    public AnalyzeOneProtein(){
	//All global variables
	//Holds the dssp file in an ArrayList of type String
	ArrayList<String> dsspFile = new ArrayList<String>();
	//Holds the pdb file in an ArrayList of type String
	ArrayList<String> pdbFile = new ArrayList<String>();
	
	//read in single file
	dsspFile = readFile("C:/Users/Bryn/Documents/CodingProjects/dssp/1a00.dssp");
	pdbFile = readFile("C:/Users/Bryn/Documents/CodingProjects/pdb/a0/pdb1a00.ent");

	/*
	  See SecondaryStructure.java for more information and documentation on what this data structure does.
	  See 3DCoord.java for more information and documentation on what this data structure does.
	*/

    }

    public ArrayList<String> readFile(String arg) {
	//local variables
	//Helper variable for storing the array as a list of lines read in one-by-one into the arraylist
	String thisLine;
	ArrayList<String> fileToRead = new ArrayList<String>();
	try {
	    //BufferedReader to scan in the file
	    BufferedReader buffy = new BufferedReader(new FileReader(arg));
	    while((thisLine = buffy.readLine()) != null) { //while there are still lines in the document
		fileToRead.add(thisLine);
	    } //end while
	} // end try
	catch (IOException ioe) {
	    System.err.println("Error: " + ioe);
	    fileToRead.clear();
	}
	return fileToRead;
    }

    public void printOut(ArrayList<String> al) {
	for (int i=0; i<al.size(); ++i){
	    System.out.println(al.get(i));
	}
    }

    public static void main(String[] args){
	AnalyzeOneProtein ap1 = new AnalyzeOneProtein();
    }
}