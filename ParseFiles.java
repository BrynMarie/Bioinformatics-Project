import java.io*;
import java.util.*;

public class ParseFiles {
    ArrayList<String> dsspFile;

    public ParseFiles () {
        
    }

    public void parseDSSP(ArrayList<String> dsspFile) {
        this.dsspFile = dsspFile;
    }
    
    public ArrayList<SecondaryStructure> extractSS(ArrayList<String> dsspFile) {
    	ArrayList<Residue> tempArray = new ArrayList<Residue>();
    	
    	String[] sheetArray = {"E","B"};
    	String[] helixArray = {"G","H","I"};
    	String[] turnArray = {" ","S","T"};
    	for (int i = 0; i<dsspFile.size(); ++i) {
	    tempArray.get(i) = new Residue();
	    if(charsAtEqual(dsspFile, i, 14, sheetArray)) {
		tempArray.get(i).setSSType("S");
	    }
	    else if(charsAtEqual(dsspFile, i, 14, helixArray)) {
		tempArray.get(i).setSSType("H");
	    }
	    else if(charsAtEqual(dsspfile, i, 14, turnArray)) {
		tempArray.get(i).setSSType("T");
	    }
    	}
    }
    
    public boolean charAtEquals(ArrayList<String> file, int index, int num, String charac) {
    	return file.get(index).charAt(num).equals(charac);
    }
    
    public boolean charsAtEqual(ArrayList<String> file, int index, int num, String[] chars) {
    	for (int j=0; j<chars.size(); ++j){
	    if(!charAtEquals(file, index, num, chars.get(j))) {
		return false;
	    }    	
    	}
    	return true;
    }
    
    public void printOut(ArrayList<String> al) {
    	for (int i=0; i<al.size(); ++i){
    	    System.out.println(al.get(i));
    	}
    }
    
    public static void main(String[] args){
    }
}