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
		
	int residueAtoms=1, curResNum, oldResNum;
	double currentResidueBFactor=0, meanOfCurrentResidue, zScore;
	boolean cTerm = false, nTerm = false;
	Atom curAtom, oldAtom;
	ArrayList<Residue> resArray = new ArrayList<Residue>();
	ArrayList<Atom> currentlyInResidue = new ArrayList<Atom>();
		
	oldAtom = atomList.get(0);
	oldResNum = Integer.parseInt(oldAtom.getResNum());
	currentlyInResidue.add(oldAtom);
	currentResidueBFactor += oldAtom.getBFactor();
		
	for (int i = 1; i<atomList.size(); ++i) {
	    curAtom = atomList.get(i);
	    curResNum = Integer.parseInt(curAtom.getResNum());
	    //if we're still on the same residue as before...
	    if (curResNum == oldResNum) {
		++residueAtoms;
		currentResidueBFactor += curAtom.getBFactor();
		currentlyInResidue.add(curAtom);
		if(curAtom.getNTerm()) { nTerm = true; }
		else if(curAtom.getCTerm()) { cTerm = true; }
		if(i == atomList.size() - 1) {
		    meanOfCurrentResidue = currentResidueBFactor / residueAtoms;
		    zScore = zScore(meanOfCurrentResidue, bFactorMean, bFactorSTD);
		    //String pdbResNum, double bFactor, String ssType, 
		    //boolean nTerm, boolean cTerm, ArrayList<Atom> atomList, CartesianCoord pmoi
		    resArray.add(new Residue("" + oldResNum + "" , zScore, "", nTerm, cTerm, 
					     currentlyInResidue, new CartesianCoord(0,0,0)));  
		    
		    
		}
	    }

	    //if we've moved on to the next residue
	    else {
		meanOfCurrentResidue = currentResidueBFactor / residueAtoms;
		zScore = zScore(meanOfCurrentResidue, bFactorMean, bFactorSTD);
		//String pdbResNum, double bFactor, String ssType, 
		//boolean nTerm, boolean cTerm, ArrayList<Atom> atomList, CartesianCoord pmoi
		resArray.add(new Residue("" + oldResNum + "" , zScore, "", nTerm, cTerm, 
					 currentlyInResidue, new CartesianCoord(0,0,0)));

		currentlyInResidue = new ArrayList<Atom>();
		currentlyInResidue.add(curAtom);
		residueAtoms = 1;
		currentResidueBFactor = curAtom.getBFactor();
		cTerm = curAtom.getCTerm();
		nTerm = curAtom.getNTerm();
	    }
	    oldResNum = curResNum;
	    oldAtom = curAtom;
	} // end for going through atom array
	extractSS(dsspFile, resArray);
	resArray.get(0).setNTerm(true);
	resArray.get(resArray.size() - 1).setCTerm(true);
	for (int i =0; i<resArray.size(); ++i) {
	    //System.out.println("CTerm / NTerm : " + resArray.get(i).getCTerm() + "/" + resArray.get(i).getNTerm());
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
	String current = "T";
	String[] sheetArray = {"E","B"};
	String[] helixArray = {"G","H","I"};
	String[] turnArray = {"","S","T"," "};

	for (int i = 0; i<dsspFile.size(); ++i) {
	    try {
		String strResNum = dsspFile.get(i).substring(6,10).trim();
		Integer resNum = Integer.parseInt(strResNum); 

		if(charsAtEqual(dsspFile, i, 16, sheetArray)) {
		    resArray.get(resArrayCounter).setSSType("S");
		    if(!current.equals("S")) {
			resArray.get(resArrayCounter).setNTerm(true);
			resArray.get(resArrayCounter - 1).setCTerm(true);
		    }
		    current = "S";
		    ++resArrayCounter;
		}
		else if(charsAtEqual(dsspFile, i, 16, helixArray)) {
		    resArray.get(resArrayCounter).setSSType("H");
		    if(!current.equals("H")) {
			resArray.get(resArrayCounter).setNTerm(true);
			resArray.get(resArrayCounter - 1).setCTerm(true);
		    }
		    current = "H";
		    ++resArrayCounter;
		}
		else if(charsAtEqual(dsspFile, i, 16, turnArray)) {
		    resArray.get(resArrayCounter).setSSType("T");
		    if(!current.equals("T")) {
			resArray.get(resArrayCounter).setNTerm(true);
			resArray.get(resArrayCounter - 1).setCTerm(true);
		    }
		    current = "T";
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