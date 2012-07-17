import java.util.*;
import java.io.*;

public class AnalyzeOneProtein {

    ArrayList<String> dsspFile;
    ArrayList<String> pdbFile;
    
    public AnalyzeOneProtein(){
	//read in single file
	dsspFile = readFile("C:/Users/Bryn/Documents/CodingProjects/dssp/1a00.dssp");
	pdbFile = readFile("C:/Users/Bryn/Documents/CodingProjects/pdb/a0/pdb1a00.ent");

	//Opens parsefiles and gives it the arguments of the dsspFile and the pdbFile
	ParseFiles f2 = new ParseFiles(dsspFile, pdbFile);
    }

    public ArrayList<String> readFile(String arg) {

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