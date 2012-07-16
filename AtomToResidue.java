import java.io.*;
import java.util.*;
import Jama.*;

// to be debugged

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
    
    public sortResidues(ArrayList<Residue> resList) {
    	Collections.sort(resList, new ResidueComparator);
    }
    
    public double getAtomicWeight(Atom currentAtom) { //NEW PMoI method to get atomic weight given atom type
		double atomWeight = 0;
		atomType = currentAtom.getAtomType().charAt(0);
		String[] atomTypeArray = {"H","N","C","O","S"};
		double[] atomWeightArray = {1.00794, 14.0067, 12.0107, 15.9994, 32.065};
		
		for(int i=0; i<atomTypeArray.length; ++i) {
			if (atomType.equals(atomTypeArray[i])) {
				atomWeight = atomWeightArray[i];
			}
		}
		return atomWeight;
	}
    
    public arrayList<Residue> turnIntoResidueArray(ArrayList<Atom> atomList, ArrayList<String> dsspFile, double bFactorMean, double bFactorSTD) {
		int residueAtoms = 0;
		double currentResidueBFactor = 0;
		long countResidues = 0;
		double meanOfCurrentResidue = 0;
		double zScore;
		boolean cTerm = false, nTerm = false;
		int newResNum=0;
    	int currentResNum = atomList.get(0).getResNum();
    	ArrayList<Residue> resArray = new ArrayList<Residue>();
    	ArrayList<Residue> tempArray = extractSS(dsspFile)
	ArrayList<CartesianCoord> cartCoordOfAtomsOfResidueList = new ArrayList<CartesianCoord>();
    	
    	for (int i = 0; i<atomList.size(); ++i) {
    		newResNum = atomList.get(i).getResNum();
    			//if we're still on the same residue as before...
			if (newResNum == currentResNum) {
				++residueAtoms;
				currentResidueBFactor += atomList.get(i).getBFactor();
				CartesianCoord atomCoordinates = new CartesianCoord(atomList.get(i).getX(), atomList.get(i).getY(), atomList.get(i).getZ());
				cartCoordOfAtomsOfResidueList.add(atomCoordinates);
			}
				//if we've moved on to the next residue
			else {
				CartesianCoord atomCoordinates = new CartesianCoord(atomList.get(i).getX(), atomList.get(i).getY(), atomList.get(i).getZ());
				cartCoordOfAtomsOfResidueList.add(atomCoordinates);
				meanOfCurrentResidue = currentResidueBFactor / residueAtoms;
				zScore = zScore(meanOfCurrentResidue, bFactorMean, bFactorSTD);
				//String pdbResNum, double bFactor, String ssType, 
				//CartesianCoord coords, boolean nTerm, boolean cTerm
				//if zscore is too high set as missing HERE
				resArray.add(new Residue(currentResNum, zScore, cartCoordOfAtomsOfResidueList));
				cartCoordOfAtomsOfResidueList.clear();
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
    
public ArrayList<Residue> calcPMOI(ArrayList<Residue> residueList) {
	ArrayList<Residue> newResArray = new ArrayList<Residue>();
	// NEW PMoI variable declarations
	int pastResidue = 0, currentResidue=0;
	double firstTermOfIxx = 0, firstTermOfIyy = 0, firstTermOfIzz = 0, firstTermOfIxy = 0, firstTermOfIxz = 0, firstTermOfIzx = 0, firstTermOfIyx = 0, firstTermOfIyz = 0, firstTermOfIzy = 0;
	double secondTermOfIxx = 0, secondTermOfIyy = 0, secondTermOfIzz = 0, secondTermOfIxy = 0, secondTermOfIxz = 0, secondTermOfIzx = 0, secondTermOfIyx = 0, secondTermOfIyz = 0, secondTermOfIzy = 0;
	double thirdTermOfIxx = 0, thirdTermOfIyy = 0, thirdTermOfIzz = 0, thirdTermOfIxy = 0, thirdTermOfIxz = 0, thirdTermOfIzx = 0, thirdTermOfIyx = 0, thirdTermOfIyz = 0, thirdTermOfIzy = 0;
	double Ixx = 0, Iyy = 0, Izz = 0, Ixy = 0, Iyx = 0, Ixz = 0, Izx = 0, Iyz = 0, Izy = 0;
	double totalAtomicWeight = 0;
	int countBFactorGreaterThanOne = 0;
	boolean discardBFactor = false;
	// END PMoI variable declaration
	for (int i = 0; i<residueList.size(); ++i) {
		if (residueList.get(i).getCTerm() || residueList.get(i).getNTerm() == true) { 
			// if C-terminus or N-terminus => calculate PMOI
			//set up calculations for Ixx term by term
			//Has been abstracted to a pretty clear point
			//this also ideally will be in a helper method. 
			aw = getAtomicWeight(atomList.get(i));
			x = residueList.get(i).getCoords().getX();
			y = residueList.get(i).getCoords().getY();
			z = residueList.get(i).getCoords().getZ();
			xSq = Math.pow(x,2);
			ySq = Math.pow(y,2);
			zSq = Math.pow(z,2);
			// calc Ixx term by term
			firstTermOfIxx += (aw) * (ySq + zSq);
			secondTermOfIxx += Math.pow((aw * y), 2);
			thirdTermOfIxx += Math.pow((aw * z), 2);
			//calc Iyy term by term
			firstTermOfIyy += aw * (xSq + zSq);
			secondTermOfIyy += Math.pow((aw * x),2)
			thirdTermOfIyy += Math.pow((aw * z), 2);
			//set up calculations for Izz term by term
			firstTermOfIzz += aw * (xSq + ySq);
			secondTermOfIyy += Math.pow((aw * x), 2);
			thirdTermOfIyy += Math.pow((aw * y), 2);
			//set up calculations for Ixy/Iyx term by term
			firstTermOfIxy += aw * x * y;
			secondTermOfIxy += aw * x;
			thirdTermOfIxy += aw * y;
			//set up calculations for Ixz/Izx term by term
			firstTermOfIxz += aw * x * z;
			secondTermOfIxz += aw * x;
			thirdTermOfIxz += aw * z;
			//set up calculations for Iyz/Izy term by term
			firstTermOfIxz += aw * y * z;
			secondTermOfIxz += aw * y;
			thirdTermOfIxz += aw * z;
			// calculate total sum of atom weights of a C-terminus/N-terminus for later calculation
			totalAtomicWeight += getAtomicWeight(atomList.get(i));
			//BEGIN calculate PMoI
			Ixx = firstTermOfIxx - (1 / totalAtomicWeight) * (secondTermOfIxx) - (1 / totalAtomicWeight)
						* (thirdTermOfIxx);
			Iyy = firstTermOfIyy - (1 / totalAtomicWeight)	* (secondTermOfIyy) - (1 / totalAtomicWeight)
						* (thirdTermOfIyy);
			Izz = firstTermOfIzz - (1 / totalAtomicWeight)
						* (secondTermOfIzz) - (1 / totalAtomicWeight)
						* (thirdTermOfIzz);
			Ixy = -firstTermOfIxy + (1 / totalAtomicWeight)
						* (secondTermOfIxy) * (thirdTermOfIxy);
			Iyx = Ixy;
			Ixz = -firstTermOfIxz + (1 / totalAtomicWeight)	* (secondTermOfIxz) * (thirdTermOfIxz);
			Izx = Ixz;
			Iyz = -firstTermOfIyz + (1 / totalAtomicWeight) * (secondTermOfIyz) * (thirdTermOfIyz);
			Izy = Iyz;

			double[][] populateMatrix = new double[][] { { Ixx, Ixy, Izz },{ Iyx, Iyy, Iyz }, { Izx, Izy, Izz } };
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
			//need xyz coordinates to calculate geometries
			newResArray.add(new Residue(principalMomentsOfInertia);
			}
	}
	return newResArray; //returns a residue array of PMOI xyz coordinates
}

public double calcGeometries(ArrayList<Residue> residueList){
	ArrayList<Double> p0 = new ArrayList<Double>();
	ArrayList<Double> p1 = new ArrayList<Double>();
	ArrayList<Double> p2 = new ArrayList<Double>();
	ArrayList<Double> p3 = new ArrayList<Double>();
   	for (int i = 0; i<residueList.size(); ++i) {
		p0.add(residueList.get(i).getCoords().getX());
		p0.add(residueList.get(i).getCoords().getY());
		p0.add(residueList.get(i).getCoords().getZ());
		p1.add(residueList.get(i+1).getCoords().getX());
		p1.add(residueList.get(i+1).getCoords().getY());
		p1.add(residueList.get(i+1).getCoords().getZ());
		p2.add(residueList.get(i+2).getCoords().getX());
		p2.add(residueList.get(i+2).getCoords().getY());
		p2.add(residueList.get(i+2).getCoords().getZ());
		p3.add(residueList.get(i+3).getCoords().getX());
		p3.add(residueList.get(i+3).getCoords().getY());
		p3.add(residueList.get(i+3).getCoords().getZ());
		//calculate e1
		calcE1LE2(p0, p1);
		//calculate l
		calcE1LE2(p2, p1);
		//calculate e2
		calcE1LE2(p3, p2);
		//calculate distance, d
		calcDistance(p2, p1);
	}
}

public double calcDistance(ArrayList<Double> p2, ArrayList<Double> p1) {
	double differenceX = 0, differenceY=0, differenceZ=0, distance=0;
	differenceX = p2.get(0)-p1.get(0);
	differenceY = p2.get(1)-p1.get(1);
	differenceZ = p2.get(2)-p1.get(2);
	sqDiffX = Math.pow(differenceX, 2);
	sqDiffY = Math.pow(differenceY, 2);
	sqDiffZ = Math.pow(differenceZ, 2);
	distance = sqrt(sqDiffX+sqDiffY+sqDiffZ);
	return distance;
}

public ArrayList<Double> calcE1LE2(ArrayList<Double> p0, ArrayList<Double> p1){
	ArrayList<Double> e1 = new ArrayList<Double>();
	double differenceX = 0, differenceY=0, differenceZ=0, denominator=0;
	differenceX = p1.get(0)-p0.get(0);
	differenceY = p1.get(1)-p0.get(1);
	differenceZ = p1.get(2)-p0.get(2);
	sqDiffX = Math.pow(differenceX, 2);
	sqDiffY = Math.pow(differenceY, 2);
	sqDiffZ = Math.pow(differenceZ, 2);
	denominator = sqrt(sqDiffX+sqDiffY+sqDiffZ);
	e1.add(differenceX/denominator);
	e1.add(differenceY/denominator);
	e1.add(differenceZ/denominator);
	return e1;
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