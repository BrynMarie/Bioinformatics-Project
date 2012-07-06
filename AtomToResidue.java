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
    
    public turnIntoResidueArray(ArrayList<Atom> atomList) {
    	for (int i = 0; i<atomList.size(); ++i) {
    		
    	}
    }

	public ArrayList<Double> calculateBfactorZScore(ArrayList<String> rawPdbFile, double totalMean, double totalStdDev) {
		ArrayList<Double> zScoresOfPDBFile = new ArrayList<Double>();
		int countTotalAtoms = 0;
		int currentResidue = 0;
		int pastResidue = 0;
		double currentResidueBfactor = 0;
		long countResidues = 0;
		double meanOfCurrentResidue = 0;
		for (int i = 0; i < rawPdbFile.size(); i++) {
			String[] strs = rawPdbFile.get(i).split("\\s+");
			if (strs[0].equals("ATOM")) { 
				// calculate total B-factor of a residue
				if (countTotalAtoms == 0) {
					pastResidue = Integer.parseInt(strs[5]);
					countResidues++;
					currentResidueBfactor += Double.valueOf(strs[10].trim()).doubleValue();
				} else {
					currentResidue = Integer.parseInt(strs[5]);
					if (currentResidue == pastResidue) {
						countResidues++;
						currentResidueBfactor += Double
								.valueOf(strs[10].trim()).doubleValue();
					} else {
						meanOfCurrentResidue = currentResidueBfactor
								/ (countResidues); // calculate mean B-factor of a residue
						zScoresOfPDBFile.add(zScore(meanOfCurrentResidue, totalMean, totalStdDev));
						countResidues = 0;
						pastResidue = currentResidue;
						currentResidueBfactor = 0;
						currentResidueBfactor += Double.valueOf(strs[10].trim()).doubleValue();
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

}