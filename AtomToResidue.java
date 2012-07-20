import java.io.*;
import java.util.*;
import java.lang.*;

public class AtomToResidue {
	
    public static ArrayList<Atom> atomList;
    public static ArrayList<Residue> pmoiArray;

    //takes as input an unsorted arraylist of atoms
    public AtomToResidue(ArrayList<Atom> al, ArrayList<String> dsspFile, double bFactorMean, double bFactorSTD) {
	atomList = al;
	Collections.sort(atomList, new AtomComparator());
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
	ArrayList<Residue> tempArray = extractSS(dsspFile);
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
		
	Collections.sort(tempArray, new ResidueComparator());
	Collections.sort(resArray, new ResidueComparator());
	ArrayList<Residue> finalResArray;

	int firstNum = Integer.parseInt(tempArray.get(0).getResNum());
	int secNum = Integer.parseInt(resArray.get(0).getResNum());

	System.out.println("Merging arrays");
	if (firstNum < secNum) {
	    finalResArray = mergeArrays(tempArray, resArray, pmoiArray, true);	
	}
	else {
	    finalResArray = mergeArrays(resArray, tempArray, pmoiArray, false);
	}
		
	return finalResArray;
    }
    
    public double zScore(double currentMean, double totalMean, double totalStdDev) {
	double zScore = (currentMean - totalMean) / totalStdDev;
	return zScore;
    }
      
    public ArrayList<Residue> mergeArrays(ArrayList<Residue> lowerArray, ArrayList<Residue> higherArray, 
					  ArrayList<Residue> pmoiArray, boolean ssFirst) {
		
	// go through that one til they both match up, marking them as 'don't exist in both',
	int lCounter = 0, finalCounter = 0;
	ArrayList<Residue> finalResArray = new ArrayList<Residue>();
		    
	while (Integer.parseInt(lowerArray.get(lCounter).getResNum()) != 
	       Integer.parseInt(higherArray.get(0).getResNum())) {
		
	    String pdb = lowerArray.get(lCounter).getResNum();
	    finalResArray.add(new Residue(pdb, false));
	    ++lCounter;
	} // end while
	System.out.println("Got out of first while in a timely fashion");

	//now we are at a point where the two arrays are synced, starting at lowerArray(0) and higherArray(hCounter)
	int hCounter = 0;
	int limit = Math.max(lowerArray.size(), higherArray.size() - hCounter);
	int lowerLimit = Math.min(lowerArray.size(), higherArray.size() - hCounter);
	System.out.println("LA : " + lowerArray.size() + " UA: " + higherArray.size());
	for (int j = 0; j < limit; ++j) {
	    Residue currentLower = lowerArray.get(lCounter + j);
	    Residue currentHigher = higherArray.get(hCounter + j);
	    int lResNum = Integer.parseInt(currentLower.getResNum());
	    int hResNum = Integer.parseInt(currentHigher.getResNum());
			
	    while (lResNum < hResNum) {
		//mark ones that don't match as 'don't exist'...this may be more complicated than previously thought.
		finalResArray.add(new Residue("" + lResNum + "", false)); 	
		++lCounter;
		if(((hCounter + j) >= lowerLimit) || ((lCounter + j) >= limit)) { //breaks out of for loop
		    j = limit;
		    break;
		}
		currentLower = lowerArray.get(lCounter + j);
		lResNum = Integer.parseInt(currentLower.getResNum());
	    } // end while 
	
	    while (lResNum > hResNum) {
		finalResArray.add(new Residue("" + hResNum + "", false));
		++hCounter;
		if(((hCounter + j) >= lowerLimit) || ((lCounter + j) >= limit)) { //breaks out of for loop
		    j = limit;
		    break;
		}
		currentHigher = higherArray.get(hCounter + j);
		hResNum = Integer.parseInt(currentHigher.getResNum());
	    } // end other while
			
	    while (lResNum == hResNum) {
		finalResArray.add(mergeResidues(currentLower, currentHigher, ssFirst));	
		++lCounter;
		++hCounter;
		if(((hCounter + j) >= lowerLimit) || ((lCounter + j) >= limit)) { //breaks out of for loop
		    j = limit;
		    break;
		}
		currentHigher = higherArray.get(hCounter + j);
		currentLower = lowerArray.get(lCounter + j);
		hResNum = Integer.parseInt(currentHigher.getResNum());
		lResNum = Integer.parseInt(currentLower.getResNum());
	    } // end while
	} // end for
		
	return finalResArray;
    } // end method
    
    public Residue mergeResidues(Residue res1, Residue res2, boolean ssFirst) {
	if(!ssFirst) {
	    Residue temp = res2;
	    res2 = res1;
	    res1 = temp;
	}
	//ss info is first
	String ss = res1.getSS();
	String pdb = res1.getResNum();
	double bF = res2.getBFactor();
	boolean cTerm = res2.getCTerm();
	boolean nTerm = res2.getNTerm();
	ArrayList<Atom> aL = res2.getAtomList();
	CartesianCoord holder = new CartesianCoord (0, 0, 0);
		
	//String pdbResNum, double bFactor, String ssType, 
	//boolean nTerm, boolean cTerm, ArrayList<Atom> atomList, CartesianCoord pmoi
	return new Residue(pdb, bF, ss, nTerm, cTerm, aL, holder);	
    }

    // returns arraylist of residues that only have ss information
    public ArrayList<Residue> extractSS(ArrayList<String> dsspFile) {
	ArrayList<Residue> tempArray = new ArrayList<Residue>();
		
	String[] sheetArray = {"E","B"};
	String[] helixArray = {"G","H","I"};
	String[] turnArray = {" ","S","T"};

	for (int i = 0; i<dsspFile.size(); ++i) {
	    try {
		String strResNum = dsspFile.get(i).substring(0,5).trim();
		Integer resNum = Integer.parseInt(strResNum); 

		if(charsAtEqual(dsspFile, i, 13, sheetArray)) {
		    tempArray.add(new Residue(strResNum, "S"));
		}
		else if(charsAtEqual(dsspFile, i, 13, helixArray)) {
		    tempArray.add(new Residue(strResNum,"H"));
		}
		else if(charsAtEqual(dsspFile, i, 13, turnArray)) {
		    tempArray.add(new Residue(strResNum,"T"));
		}
	    }
	    catch (Exception e) { }
	}
	/*
	for (int i=0; i<tempArray.size(); ++i) {
	    System.out.println("" + tempArray.get(i).getResNum() + ": " + tempArray.get(i).getSS());
	    }*/

	return tempArray;
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