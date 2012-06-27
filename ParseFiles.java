import java.io*;
import java.util.*;

public class ParseFiles {
    ArrayList<String> dsspFile;

    public ParseFiles () {
        
    }

    public void parseDSSP(ArrayList<String> dsspFile) {
        this.dsspFile = dsspFile;
    }
    
    public ArrayList<String> parsePDB(ArrayList<String> rawPdbFile){
    	//takes raw PDB file and parses 
    	//final output parsed pdb
    	ArrayList<String> parsedPDBFile = new ArrayList<String>();
    	int countTotalAtoms=0;
    	double totalbFactor = 0;
    	double totalsquaredbFactor = 0;
    	for(int i=0; i<rawPdbFile.size(); i++) {
    		String[] strs = rawPdbFile.get(i).split("\\s+");
        	if(strs[0].equals("ATOM")){ //only want lines from PDB that are from ATOM section
        		String line = strs[1] + " " + strs[2] + " " + strs[3] + " " + strs[6] + " " + strs[7] + " " + strs[8] + " " + strs[10];
        		parsedPDBFile.add(line);
        		//calculate mean b-factor and std deviation b-factors for whole protein
        		countTotalAtoms++;
        		double strbFactortoDbl = Double.valueOf(strs[10].trim()).doubleValue();
        		double squaredbFactor = Math.pow(strbFactortoDbl, 2);
        		totalbFactor += strbFactortoDbl;
        		totalsquaredbFactor += squaredbFactor;
        	}
        	else if(strs[0].equals("TER")){
        		String line = strs[0] + " " + strs[1] + " " + strs[2] + " " + strs[4];
        		parsedPDBFile.add(line);
        	}
    	}
    	double meanbFactor = totalbFactor/countTotalAtoms;
    	parsedPDBFile.add(Double.toString(meanbFactor));
    	parsedPDBFile.add(Double.toString(calcStdDev(totalbFactor, totalsquaredbFactor, countTotalAtoms)));
    	return parsedPDBFile;
    }
    
    public double calcStdDev(double total, double squaredTotal, int counter) {
    	double firstTerm = squaredTotal/(counter-1);
    	double secondTerm = (counter/(counter-1));
    	double thirdTerm = Math.pow((total/counter), 2);
    	double stdDev = Math.sqrt(firstTerm-(secondTerm*thirdTerm));
    	return stdDev;
    }
    
    public ArrayList<SecondaryStructure> extractSS(ArrayList<String> dsspFile) {
    	ArrayList<SecondaryStructure> tempArray = new ArrayList<SecondaryStructure>();
    	
    	String[] sheetArray = {"E","B"};
    	String[] helixArray = {"G","H","I"};
    	String[] turnArray = {" ","S","T"};
    	for (int i = 0; i<dsspFile.size(); ++i) {
	    	if(charsAtEqual(dsspFile, i, 16, sheetArray)) {
	    		tempArray.get(i).setSSType("S");
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
	       AnalyzeOneProtein ap1 = new AnalyzeOneProtein();
    }

}