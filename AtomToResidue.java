import java.io.*;
import java.util.*;
import java.lang.*;

public class AtomToResidue {
	
    public static ArrayList<Atom> atomList;
    public static ArrayList<Residue> pmoiArray;

    //takes as input an unsorted arraylist of atoms
    public AtomToResidue(ArrayList<Atom> al, ArrayList<String> dsspFile, double bFactorMean, double bFactorSTD) {
	atomList = al;
	ArrayList<Residue> resArray = turnIntoResidueArray(atomList, dsspFile, bFactorMean, bFactorSTD);
	CalculatePMOI f5 = new CalculatePMOI(resArray);
	ArrayList<Residue> pmoiArray = CalculatePMOI.newResArray;
	System.out.println("PMOI Size: " + pmoiArray.size());
	ResidueToSS f4 = new ResidueToSS(resArray, pmoiArray);
    }
    
    public ArrayList<Residue> turnIntoResidueArray(ArrayList<Atom> atomList, 
						   ArrayList<String> dsspFile, double bFactorMean, double bFactorSTD) {
		
	int residueAtoms=0, newResNum;
	double currentResidueBFactor=0, meanOfCurrentResidue, zScore;
	long countResidues = 0;
	boolean cTerm = false, nTerm = false;
	int currentResNum = Integer.parseInt(atomList.get(0).getResNum());
	Atom curAtom = atomList.get(0); 
	Atom newAtom;
	ArrayList<Residue> resArray = new ArrayList<Residue>();
	ArrayList<Atom> currentlyInResidue = new ArrayList<Atom>();
		
	currentlyInResidue.add(curAtom);
		
	for (int i = 0; i<atomList.size(); ++i) {
	    newAtom = atomList.get(i);
	    newResNum = Integer.parseInt(newAtom.getResNum());
			
	    //if we're still on the same residue as before...
	    if (newResNum == currentResNum) {
		++residueAtoms;
		currentResidueBFactor += newAtom.getBFactor();
		currentlyInResidue.add(newAtom);
		if(newAtom.getNTerm()) { nTerm = true; }
		else if(newAtom.getCTerm()) { cTerm = true; }
	    }

	    //if we've moved on to the next residue
	    else {
		meanOfCurrentResidue = currentResidueBFactor / residueAtoms;
		zScore = zScore(meanOfCurrentResidue, bFactorMean, bFactorSTD);

		//String pdbResNum, double bFactor, String ssType, 
		//boolean nTerm, boolean cTerm, ArrayList<Atom> atomList, CartesianCoord pmoi
		resArray.add(new Residue("" + currentResNum + "" , zScore, "", nTerm, cTerm, currentlyInResidue, new CartesianCoord(0,0,0)));

		currentlyInResidue.clear();
		currentResNum = newResNum;
		residueAtoms = 0;
		currentResidueBFactor = atomList.get(i).getBFactor();
		cTerm = atomList.get(i).getCTerm();
		nTerm = atomList.get(i).getNTerm();
	    }
	}
	resArray.get(0).setNTerm(true);
	resArray.get(resArray.size() - 1).setCTerm(true);
	extractSS(dsspFile, resArray);

	for(int i=0; i<resArray.size(); ++i) {
	    System.out.println(resArray.get(i).toString());
	}

	return resArray;
    }
    
    public double zScore(double currentMean, double totalMean, double totalStdDev) {
	double zScore = (currentMean - totalMean) / totalStdDev;
	return zScore;
    }

    // returns arraylist of residues that only have ss information
    public void extractSS(ArrayList<String> dsspFile, ArrayList<Residue> resArray) {

	int resArrayCounter = 0;	
	String[] sheetArray = {"E","B"};
	String[] helixArray = {"G","H","I"};
	String[] turnArray = {"","S","T"," "};

	for (int i = 0; i<dsspFile.size(); ++i) {
	    try {
		String strResNum = dsspFile.get(i).substring(6,10).trim();
		Integer resNum = Integer.parseInt(strResNum); 

		if(charsAtEqual(dsspFile, i, 16, sheetArray)) {
		    resArray.get(resArrayCounter).setSSType("S");
		    ++resArrayCounter;
		}
		else if(charsAtEqual(dsspFile, i, 16, helixArray)) {
		    resArray.get(resArrayCounter).setSSType("H");
		    ++resArrayCounter;
		}
		else if(charsAtEqual(dsspFile, i, 16, turnArray)) {
		    resArray.get(resArrayCounter).setSSType("T");
		    ++resArrayCounter;
		}
	    }
	    catch (Exception e) { }
	}
    }

    public boolean charAtEquals(ArrayList<String> file, int index, int num, String charac) {
	String str = Character.toString(file.get(index).charAt(num));
	return str.equals(charac);
    }
    
    public boolean charsAtEqual(ArrayList<String> file, int index, int num, String[] chars) {
	for (int j=0; j<chars.length; ++j){
	    if(charAtEquals(file, index, num, chars[j])) {
		return true;
	    } // end if   	
	} // end for
	return false;
    }
}