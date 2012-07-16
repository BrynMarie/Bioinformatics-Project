import java.io.*;
import java.util.*;
import java.lang.*;

public class ParseFiles {
    ArrayList<String> dsspFile;
    ArrayList<String> pdbFile;

    public ParseFiles (ArrayList<String> dsspFile, ArrayList<String> pdbFile) {
        this.dsspFile = dsspFile;
        this.pdbFile = pdbFile;
    }
    
    public ArrayList<Atom> getInfoFromPDB(ArrayList<String> pdbFile) {
    	
    	ArrayList<Atom> atomList = new ArrayList<Atom>();
    	CartesianCoord coords;
    	String atomType;
    	int pdbResNum;
    	double tempFact;
    	boolean backbone = false;
    	boolean nTerm, cTerm = false;
    	boolean nextIsNTerm = false;
    	double totalBFactor = 0;
    	double meanBFactor;
    	double totalSquaredBFactor = 0;
    	double std;
    	
    	for (int i = 0; i<pdbFile.size(); ++i) {		
    		if(pdbFile.get(i).substring(0,6).trim().equals("ATOM")) {
    			String[] strs = customPDBSplit(pdbFile.get(i));
    			coords = getCoordinates(strs);		
    			atomType = strs[1].trim();
    			nTerm = nextIsNTerm;
    			nextIsNTerm = false;
    			String[] atomTypeArray = {"N","CA","C","O","OXT","OT1","OT2"};
    			if(multiEquals(atomType, atomTypeArray)) {
    				backbone = true;
    			}
    			pdbResNum = Integer.parseInt(strs[3].trim());
    			tempFact = Double.parseDouble(strs[8].trim());	
    			totalBFactor += tempFact;
    			totalSquaredBFactor += Math.pow(tempFact, 2);
    			atomList.add(new Atom(atomType, pdbResNum, backbone, nTerm, cTerm, tempFact, coords));
    		}
    		if(pdbFile.get(i).substring(0,6).trim().equals("TER")) {
    			nextIsNTerm = true;
    			atomList.get(atomList.size()-1).setCTerm(true);
    		}
    	}// end of for
    	
    	meanBFactor = totalBFactor / (atomList.size() - 1);
    	std = calcStdDev(totalBFactor, totalSquaredBFactor, atomList.size() - 1);
    	//atomList.add(meanBfactor); is this the only way for me to get the meanBfactor value if the return value is the
    	//arrayList atomList?
    	//atomList.add(std);
    	return atomList; //needs a return
    }
    
    public boolean multiEquals(String checkMe, String[] againstMe) {
    	for(int i = 0; i < againstMe.length; ++i) {
    		if(checkMe.equals(againstMe[i])) {
    			return true;
    		}
    	}
    	return false;
    }
    
    public double calcStdDev(double total, double squaredTotal, int counter) {
    	double firstTerm = squaredTotal/(counter-1);
    	double secondTerm = (counter/(counter-1));
    	double thirdTerm = Math.pow((total/counter), 2);
    	double stdDev = Math.sqrt(firstTerm-(secondTerm*thirdTerm));
    	return stdDev;
    }
    
        //this method splits a pdb file line into 
	public static String[] customPDBSplit(String splitMe) {
		ArrayList<String> strArrayList = new ArrayList<String>();
		strArrayList.add(splitMe.substring(0,6).trim()); //    0   ATOM designation
		strArrayList.add(splitMe.substring(11,16).trim()); //  1   atom name type
		strArrayList.add(splitMe.substring(17,20).trim()); //  2   residue name
		strArrayList.add(splitMe.substring(22,26).trim()); //  3   residue sequence number
		strArrayList.add(splitMe.substring(30,38).trim()); //  4   x
		strArrayList.add(splitMe.substring(38,46).trim()); //  5   y
		strArrayList.add(splitMe.substring(47,54).trim()); //  6   z
		strArrayList.add(splitMe.substring(60,66).trim()); //  8   temperature factor
		
		String strArray[] = new String[strArrayList.size()];
		strArray = strArrayList.toArray(strArray);
		return strArray; //used this: http://stackoverflow.com/questions/5374311/convert-arrayliststring-to-string
	}
   
    //At indices 4, 5, and 6 are where the xyz coordinates are stored, accounting for zero indexing.
    public CartesianCoord getCoordinates(String[] strs){
    	double x = Double.parseDouble(strs[4]); 
    	double y = Double.parseDouble(strs[5]);
    	double z = Double.parseDouble(strs[6]);
    	return new CartesianCoord(x,y,z);
    }
    
    public boolean charAtEquals(ArrayList<String> file, int index, int num, String charac) {
    	String fileStr = Character.toString(file.get(index).charAt(num));
	return fileStr.equals(charac);
    }
    
    public boolean charsAtEqual(ArrayList<String> file, int index, int num, String[] chars) {
    	for (int j=0; j<chars.length; ++j){
	    if(!charAtEquals(file, index, num, chars[j])) {
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