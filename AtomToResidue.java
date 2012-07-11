import java.io.*;
import java.util.*;
import Jama.*;

// to be debugged

public class AtomToResidue {
     //NEW PMoI variables of atomic weights used in calculation
     double nitrogenAtomicWeight = 14.0067;
     double carbonAtomicWeight = 12.0107;
     double hydrogenAtomicWeight = 1.00794;
     double oxygenAtomicWeight = 15.9994;
     double sulphurAtomicWeight = 32.065;
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
    
    public sortResidues(ArrayList<Residue> resList) {
    	Collections.sort(resList, new ResidueComparator);
    }
    
    public double getAtomicWeight(Atom currentAtom) { //NEW PMoI method to get atomic weight given atom type
	double atomWeight = 0;
	if (currentAtom.getAtomType().charAt(0).equals("H")) {
		atomWeight = hydrogenAtomicWeight;
	} else if (currentAtom.getAtomType().charAt(0).equals("N")) {
		atomWeight = nitrogenAtomicWeight;
	} else if (currentAtom.getAtomType().charAt(0).equals("C")) {
		atomWeight = carbonAtomicWeight;
	} else if (currentAtom.getAtomType().charAt(0).equals("O")) {
		atomWeight = oxygenAtomicWeight;
	} else if (currentAtom.getAtomType().charAt(0).equals("S")) {
		atomWeight = sulphurAtomicWeight;
	}
	return atomWeight;
	}
    
    public turnIntoResidueArray(ArrayList<Atom> atomList, ArrayList<String> dsspFile, double bFactorMean, double bFactorSTD) {
    	
		int residueAtoms = 0;
		double currentResidueBFactor = 0;
		long countResidues = 0;
		double meanOfCurrentResidue = 0;
		double zScore;
		boolean cTerm = false, nTerm = false;
		int newResNum=0;
		// NEW PMoI variable declarations
		int pastResidue = 0, currentResidue=0;
		double firstTermOfIxx = 0, firstTermOfIyy = 0, firstTermOfIzz = 0, firstTermOfIxy = 0, firstTermOfIxz = 0, firstTermOfIzx = 0, firstTermOfIyx = 0, firstTermOfIyz = 0, firstTermOfIzy = 0;
		double secondTermOfIxx = 0, secondTermOfIyy = 0, secondTermOfIzz = 0, secondTermOfIxy = 0, secondTermOfIxz = 0, secondTermOfIzx = 0, secondTermOfIyx = 0, secondTermOfIyz = 0, secondTermOfIzy = 0;
		double thirdTermOfIxx = 0, thirdTermOfIyy = 0, thirdTermOfIzz = 0, thirdTermOfIxy = 0, thirdTermOfIxz = 0, thirdTermOfIzx = 0, thirdTermOfIyx = 0, thirdTermOfIyz = 0, thirdTermOfIzy = 0;
		double Ixx = 0, Iyy = 0, Izz = 0, Ixy = 0, Iyx = 0, Ixz = 0, Izx = 0, Iyz = 0, Izy = 0;
		double totalAtomicWeight = 0;
		// END PMoI variable declaration
    	int currentResNum = atomList.get(0).getResNum();
    	ArrayList<Residue> resArray = new ArrayList<Residue>();
    	ArrayList<Residue> tempArray = extractSS(dsspFile)
    	
    	for (int i = 0; i<atomList.size(); ++i) {
    		newResNum = atomList.get(i).getResNum();
    			//if we're still on the same residue as before...
			if (newResNum == currentResNum) {
				++residueAtoms;
				currentResidueBFactor += atomList.get(i).getBFactor();
			if (atomList.get(i).getCTerm()	|| atomList.get(i).getNTerm() == true) { 
			// if C-terminus or N-terminus => calculate PMOI
				//set up calculations for Ixx term by term
				firstTermOfIxx += (getAtomicWeight(atomList.get(i)))
						* ((Math.pow(atomList.get(i).getCoords().getY(), 2)) + (Math
								.pow(atomList.get(i).getCoords().getZ(), 2)));
				secondTermOfIxx += Math.pow(
						(getAtomicWeight(atomList.get(i)) * atomList.get(i)
								.getCoords().getY()), 2);
				thirdTermOfIxx += Math.pow(
							(getAtomicWeight(atomList.get(i)) * atomList.get(i)
								.getCoords().getZ()), 2);
				//set up calculations for Iyy term by term
				firstTermOfIyy += (getAtomicWeight(atomList.get(i)))
						* ((Math.pow(atomList.get(i).getCoords().getX(), 2)) + (Math
								.pow(atomList.get(i).getCoords().getZ(), 2)));
				secondTermOfIyy += Math.pow(
						(getAtomicWeight(atomList.get(i)) * atomList.get(i)
								.getCoords().getX()), 2);
				thirdTermOfIyy += Math.pow(
						(getAtomicWeight(atomList.get(i)) * atomList.get(i)
								.getCoords().getZ()), 2);
				//set up calculations for Izz term by term
				firstTermOfIzz += (getAtomicWeight(atomList.get(i)))
						* ((Math.pow(atomList.get(i).getCoords().getX(), 2)) + (Math
								.pow(atomList.get(i).getCoords().getY(), 2)));
				secondTermOfIyy += Math.pow(
						(getAtomicWeight(atomList.get(i)))
								* (atomList.get(i).getCoords().getX()), 2);
				thirdTermOfIyy += Math.pow(
						(getAtomicWeight(atomList.get(i)))
								* (atomList.get(i).getCoords().getY()), 2);
				//set up calculations for Ixy/Iyx term by term
				firstTermOfIxy += (getAtomicWeight(atomList.get(i))
						* (atomList.get(i).getCoords().getX()) * (atomList
						.get(i).getCoords().getY()));
				secondTermOfIxy += (getAtomicWeight(atomList.get(i)))
						* (atomList.get(i).getCoords().getX());
				thirdTermOfIxy += (getAtomicWeight(atomList.get(i)))
						* (atomList.get(i).getCoords().getY());
				//set up calculations for Ixz/Izx term by term
				firstTermOfIxz += (getAtomicWeight(atomList.get(i))
						* (atomList.get(i).getCoords().getX()) * (atomList
						.get(i).getCoords().getZ()));
				secondTermOfIxz += (getAtomicWeight(atomList.get(i)))
						* (atomList.get(i).getCoords().getX());
				thirdTermOfIxz += (getAtomicWeight(atomList.get(i)))
						* (atomList.get(i).getCoords().getZ());
				//set up calculations for Iyz/Izy term by term
				firstTermOfIxz += (getAtomicWeight(atomList.get(i))
						* (atomList.get(i).getCoords().getY()) * (atomList
						.get(i).getCoords().getZ()));
				secondTermOfIxz += (getAtomicWeight(atomList.get(i)))
						* (atomList.get(i).getCoords().getY());
				thirdTermOfIxz += (getAtomicWeight(atomList.get(i)))
						* (atomList.get(i).getCoords().getZ());
				// calculate total sum of atom weights of a C-terminus/N-terminus for later calculation
				totalAtomicWeight += getAtomicWeight(atomList.get(i));
			}
			//if we've moved on to the next residue
			else {
				//make new residue here?
				//BEGIN calculate PMoI
				Ixx = firstTermOfIxx - (1 / totalAtomicWeight)
						* (secondTermOfIxx) - (1 / totalAtomicWeight)
						* (thirdTermOfIxx);
				Iyy = firstTermOfIyy - (1 / totalAtomicWeight)
						* (secondTermOfIyy) - (1 / totalAtomicWeight)
						* (thirdTermOfIyy);
				Izz = firstTermOfIzz - (1 / totalAtomicWeight)
						* (secondTermOfIzz) - (1 / totalAtomicWeight)
						* (thirdTermOfIzz);
				Ixy = -firstTermOfIxy + (1 / totalAtomicWeight)
						* (secondTermOfIxy) * (thirdTermOfIxy);
				Iyx = Ixy;
				Ixz = -firstTermOfIxz + (1 / totalAtomicWeight)
						* (secondTermOfIxz) * (thirdTermOfIxz);
				Izx = Ixz;
				Iyz = -firstTermOfIyz + (1 / totalAtomicWeight)
						* (secondTermOfIyz) * (thirdTermOfIyz);
				Izy = Iyz;

				double[][] populateMatrix = new double[][] { { Ixx, Ixy, Izz },
						{ Iyx, Iyy, Iyz }, { Izx, Izy, Izz } };
				Matrix matrixForEigen = new Matrix(populateMatrix);
				EigenvalueDecomposition ed = matrixForEigen.eig();
				double[] getRealEigenvalues = ed.getRealEigenvalues();
				double[] getImgEigenvalues = ed.getImagEigenvalues(); 
				CartesianCoord principalMomentsOfInertia = new CartesianCoord(getRealEigenvalues[1], getRealEigenvalues[2], getRealEigenvalues[3]);
				//reset terms
				Ixx=0, Iyy=0, Izz=0, Ixy=0, Iyx=0, Ixz=0, Izx=0, Izy=0, Iyz=0;
				//reset first terms
				firstTermOfIxx=0, firstTermOfIyy=0, firstTermOfIzz=0, firstTermOfIxy=0, firstTermOfIyx=0, 
				firstTermOfIxz=0, firstTermOfIzx=0, firstTermOfIzy=0, firstTermOfIyz=0;
				//reset second terms
				secondTermOfIxx=0, secondTermOfIyy=0, secondTermOfIzz=0, secondTermOfIxy=0, secondTermOfIyx=0, 
				secondTermOfIxz=0, secondTermOfIzx=0, secondTermOfIzy=0, secondTermOfIyz=0;
				//reset third terms
				thirdTermOfIxx=0, thirdTermOfIyy=0, thirdTermOfIzz=0, thirdTermOfIxy=0, thirdTermOfIyx=0, 
				thirdTermOfIxz=0, thirdTermOfIzx=0, thirdTermOfIzy=0, thirdTermOfIyz=0;
				totalAtomicWeight=0;
				//end calculate PMoI
				meanOfCurrentResidue = currentResidueBFactor / residueAtoms;
				zScore = zScore(meanOfCurrentResidue, bFactorMean, bFactorSTD);
				//String pdbResNum, double bFactor, String ssType, 
				//CartesianCoord coords, boolean nTerm, boolean cTerm
				
				resArray.add(new Residue(currentResNum, zScore));
				
				currentResNum = newResNum;
				residueAtoms = 0;
				currentResidueBFactor = atomList.get(i).getBFactor();
				cTerm = atomList.get(i).getCTerm(); // will set back to false if false; keep true if true
				nTerm = atomList.get(i).getNTerm();
			}
    	}
	
    	tempArray = sortResidues(tempArray);
    	resArray = sortResidues(resArray);
    	ArrayList<Residue> finalResArray = new ArrayList<Residue>();
    	
    	if (tempArray.get(0).getResNum() < resArray.get(0).getResNum()) {
    		finalResArray = mergeArrays(tempArray, resArray, true);	
    	}
    	else {
    		finalResArray = mergeArrays(resArray, tempArray, false);
    	}
    }
    
    public ArrayList<Residue> mergeArrays(ArrayList<Residue> lowerArray, ArrayList<Residue> higherArray, boolean ssFirst) {
    	// go through that one til they both match up, marking them as 'don't exist in both'
    	// when they match up,
    	lCounter = 0;
    	ArrayList<Residue> finalResArray = new ArrayList<Residue>();
    	finalCounter = 0;
    	while (Integer.parseInt(lowerArray.get(lCounter).getResNum()) != Integer.parseInt(higherArray.get(0).getResNum()) {
    		String pdb = lowerArray.get(lCounter).getResNum();
    		finalResArray.add(new Residue(pdb, true);
    		++lCounter;
    	}
    	//now we are at a point where the two arrays are synced, starting at lowerArray(0) and higherArray(counter)
    	hCounter = 0;
    	limit = Math.max(lowerArray.size(), higherArray.size());
    	for (int j = 0; j < limit; ++j) {
    		Residue currentLower = lowerArray.get(lCounter + j);
    		Residue currentHigher = higherArray.get(hCounter + j);
    		int lResNum = Integer.parseInt(currentLower.getResNum());
    		int hResNum = Integer.parseInt(currentHigher.getResNum());
	    	while (lResNum < hResNum) {
    			//mark ones that don't match as 'don't exist'...this may be more complicated than previously thought.
    			finalResArray.add(new Residue(lResNum, true); 
    			++lCounter;
    			currentLower = lowerArray.get(lCounter + j);
    			lResNum = Integer.parseInt(currentLower.getResNum());
    		} 
    		while (lResNum > hResNum) {
    			finalResArray.add(new Residue(hResNum, true);
    			++hCounter;
    			currentHigher = higherArray.get(hCounter + j);
    			int hResNum = Integer.parseInt(currentHigher.getResNum());
    		}
    		while (lResNum == hResNum) {
    			finalResArray.add(mergeResidues(currentLower, currentHigher, ssFirst));	
    		}
    	}
    }
    
    public Residue mergeResidues(Residue res1, Residue res2, boolean ssFirst) {
    	Residue retMe;
    	
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
    	
    	//String pdbResNum, double bFactor, String ssType, CartesianCoord coords, boolean nTerm, boolean cTerm
    	retMe = new Residue(pdb, bF, ss, COORDS_NEEDED, nTerm, cTerm);
    	
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