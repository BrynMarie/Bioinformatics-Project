import java.io.*;
import java.util.*;

public class AtomToResidue {

    //takes as input an unsorted arraylist of atoms
    public AtomToResidue(ArrayList<Atom> atomList) {
		ArrayList<Atom> sortedAtomList = sortAtoms(atomList);
		ArrayList<Residue> resArray = turnIntoResidueArray(sortedAtomList);
    }
    
    public sortAtoms(ArrayList<Atom> atomList) {
    	//write a sort here, oh gosh...
    	//this will *certainly* need to be checked for accuracy but according to the link below it works
    	//http://stackoverflow.com/questions/2784514/sort-arraylist-of-custom-objects-by-property
    	Collections.sort(atomList, new AtomComparator);
    }
    
    public turnIntoResidueArray(ArrayList<Atom> atomList, ArrayList<String> dsspFile, double bFactorMean, double bFactorSTD) {
    	
		int residueAtoms = 0;
		double currentResidueBFactor = 0;
		long countResidues = 0;
		double meanOfCurrentResidue = 0;
		double zScore;
		boolean cTerm = false, nTerm = false;
    	int currentResNum = atomList.get(0).getResNum();
    	ArrayList<Residue> resArray = new ArrayList<Residue>();
    	ArrayList<Residue> tempArray = extractSS(dsspFile)
    	
    	for (int i = 0; i<atomList.size(); ++i) {
    		newResNum = atomList.get(i).getResNum();
			//if we're still on the same residue as before...
			if (newResNum == currentResNum) {
				++residueAtoms;
				currentResidueBFactor += atomList.get(i).getBFactor();
				if(atomList.get(i).getCTerm()) {
					cTerm = true;
				}
				if(atomList.get(i).getNTerm()) {
					nTerm = true;
				}
			}
			
			//if we've moved on to the next residue
			else {
				//make new residue here?
				meanOfCurrentResidue = currentResidueBFactor / residueAtoms;
				zScore = zScore(meanOfCurrentResidue, bFactorMean, bFactorSTD);
				// String pdbResNum, double bFactor, String ssType, CartesianCoord coords
				//coords information needed, ss added below
				
				resArray.add(new Residue(currentResNum, zScore));
				
				currentResNum = newResNum;
				residueAtom = 0;
				currentResidueBFactor = atomList.get(i).getBFactor();
				cTerm = atomList.get(i).getCTerm(); // will set back to false if false; keep true if true
				nTerm = atomList.get(i).getNTerm();
			}
    	}
    	
    	//once we have tempArray and resArray, sort
    	// Pick the one with the lowest pdb res num
    	// go through that one til they both match up, marking them as 'don't exist in both'
    	// when they match up, go through them in parallel until they are both exhausted, marking ones that don't match as 'don't exist in both'
    }
    
    
// this should be deprecated now; all information is above. Please check and make sure I did it correctly but I'm 95% sure I did. I renamed some variables to make them more concise so make sure to check those over.
	public ArrayList<Double> calculateBfactorZScore(ArrayList<String> rawPdbFile, double totalMean, double totalStdDev) {
		
		for (int i = 0; i< rawPdbFile.size(); ++i) {
		if(rawPdbFile.get(i).substring(0,6).trim().equals("ATOM")) {
     			String[] strs = customPDBSplit(rawPdbFile.get(i));
				if (i == 0) { 
					pastResidue = Integer.parseInt(strs[3]); 
					countTotalResidueAtoms++;
					currentResidueBfactor += Double.valueOf(strs[8].trim()).doubleValue(); // += necessary?
					//i need this to add up the b-factor value across the residue
					// but this is the first time that you're adding anything so you can just set it to equal, right?
				} else {
					currentResidue = Integer.parseInt(strs[5]);
					if (currentResidue == pastResidue) { // if on the same residue, add the next atom's bfactor
						countTotalResidueAtoms++;
						currentResidueBfactor += Double
								.valueOf(strs[10].trim()).doubleValue();
					} else { // if not on the same residue
						meanOfCurrentResidue = currentResidueBfactor
								/ (countTotalResidueAtoms); // calculate mean B-factor of a residue
						zScoresOfPDBFile.add(zScore(meanOfCurrentResidue, totalMean, totalStdDev)); // add zscore
						countResidues = 0;
						pastResidue = currentResidue;
						currentResidueBfactor = 0;
						currentResidueBfactor += Double.valueOf(strs[8].trim()).doubleValue();
					}
				}
			}
		}
		return zScoresOfPDBFile;
	}
	
	public double zScore(double currentMean, double totalMean, double totalStdDev) {
		double zScore = (currentMean - totalMean) / totalStdDev;
		return zScore;
	}

    public ArrayList<Residue> extractSS(ArrayList<String> dsspFile) {
    	ArrayList<Residue> tempArray = new ArrayList<Residue>();
    	
    	String[] sheetArray = {"E","B"};
    	String[] helixArray = {"G","H","I"};
    	String[] turnArray = {" ","S","T"};
    	
    	for (int i = 0; i<dsspFile.size(); ++i) {
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
    	
    	return tempArray;
    }

}