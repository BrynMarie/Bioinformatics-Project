import java.io.*;
import java.util.*;

public class CalculatePMOI {

	static ArrayList<Residue> newResArray;
	int countBFactorGreaterThanOne = 0;
	boolean discardBFactor = false;

	public CalculatePMOI(ArrayList<Residue> residueList) {
		this.newResArray = calcPMOI(residueList);
	}

	// calculate principal moment of inertia
	public ArrayList<Residue> calcPMOI(ArrayList<Residue> residueList) {
		newResArray = new ArrayList<Residue>();
		int bc = 0;
		int newC = 0;
		boolean nt, ct;
		
		for (int i = 0; i < residueList.size(); ++i) {
			// get relevant info for residue construction
			ArrayList<Atom> atomList = residueList.get(i).getAtomList();
			Residue currentResidue = residueList.get(i);
			String pdbNum = currentResidue.getResNum();
			String sstype = currentResidue.getSS();

			ct = currentResidue.getCTerm();
			nt = currentResidue.getNTerm();
			if (ct) {
				++newC;
			}
			if (nt) {
				++newC;
			}
			while (newC > 0) {
				--newC;
				++bc;
				// if C-terminus or N-terminus => calculate PMOI

				// calculate total mass of residue
				double totalMassOfResidue = calcMassResidue(atomList);

				// calc geo center of protein
				CartesianCoord centroid = calcCentroid(atomList,
						totalMassOfResidue);

				// calc inertia tensor matrix
				double[][] it = calcInertiaTensor(atomList, centroid);

				// get principal moments of inertia from it matrix
				double xCoordPMOI = it[0][0];
				double yCoordPMOI = it[1][1];
				double zCoordPMOI = it[2][2];

				// create principal moment of inertia cartesian coord object
				CartesianCoord principalMomentsOfInertia = new CartesianCoord(
						xCoordPMOI, yCoordPMOI, zCoordPMOI);

				// create residue list only if b factor score of residue is in acceptable range
				if (discardBFactor == false) {
					newResArray.add(new Residue(pdbNum, principalMomentsOfInertia,
									atomList, sstype, nt, ct));
				}
			}
		}
		return newResArray;
	}// end method

	//return atomic weight given atom type
	public static double getAtomicWeight(Atom currentAtom) {

		double atomWeight = 0;
		String atomType = Character.toString(currentAtom.getAtomType()
				.charAt(0));
		String[] atomTypeArray = { "H", "N", "C", "O", "S" };
		double[] atomWeightArray = { 1.00794, 14.0067, 12.0107, 15.9994, 32.065 };

		for (int i = 0; i < atomTypeArray.length; ++i) {
			if (atomType.equals(atomTypeArray[i])) {
				atomWeight = atomWeightArray[i];
			}
		}
		return atomWeight;
	}

	// calculate if a residue's bfactor score is too high
	public boolean countHighBFactor(ArrayList<Residue> residueList) {
		int countBFactorGreaterThanOne = 0;
		for (int i = 0; i < residueList.size(); i++) {
			Residue currentResidue = residueList.get(i);
			if (currentResidue.getBFactor() > 1.0) {
				countBFactorGreaterThanOne++;
			}
		}
		int halfOfResidueListSize = (residueList.size()) / 2;
		if (countBFactorGreaterThanOne > halfOfResidueListSize) {
			discardBFactor = true;
			System.out.println("Discard residue due to high residue B-factor z score.");
		}
		return discardBFactor;
	}

	// calc centroid for calc pmoi
	public static CartesianCoord calcCentroid(ArrayList<Atom> atomList,
			double totalMass) {
		int n = atomList.size();
		double xCoord = 0;
		double yCoord = 0;
		double zCoord = 0;
		for (int i = 0; i < n; i++) {
			Atom currentAtom = atomList.get(i);
			xCoord += currentAtom.getX();
			yCoord += currentAtom.getY();
			zCoord += currentAtom.getZ();
		}
		xCoord = xCoord / n;
		yCoord = yCoord / n;
		zCoord = zCoord / n;
		CartesianCoord centroidCoord = new CartesianCoord(xCoord, yCoord,
				zCoord);
		return centroidCoord;
	}

	public static double calcMassResidue(ArrayList<Atom> atomList) {
		double aw = 0;
		for (int i = 0; i < atomList.size(); i++) {
			Atom currentAtom = atomList.get(i);
			aw += getAtomicWeight(currentAtom);
		}
		return aw;
	}

	// calc inertia tensor
	public static double[][] calcInertiaTensor(ArrayList<Atom> atomList,
			CartesianCoord com) {
		// initialize current atom coordinates
		double x = 0;
		double y = 0;
		double z = 0;
		// initialize centroid coordinates
		double xOfCentroid = com.getX();
		double yOfCentroid = com.getY();
		double zOfCentroid = com.getZ();
		// initialize inertia tensor matrix
		double[][] it = new double[3][3];

		for (int i = 0; i < atomList.size(); i++) {
			// subtract the centroid from coordinates
			Atom currentAtom = atomList.get(i);
			x = currentAtom.getX() - xOfCentroid;
			y = currentAtom.getY() - yOfCentroid;
			z = currentAtom.getZ() - zOfCentroid;

			// populate matrix
			it[0][0] = it[0][0] + (y * y + z * z);
			it[1][1] = it[1][1] + (x * x + z * z);
			it[2][2] = it[2][2] + (x * x + y * y);

			it[0][1] = it[0][1] - (x * y);
			it[0][2] = it[0][2] - (x * z);
			it[1][2] = it[1][2] - (y * z);
		}
		it[1][0] = it[0][1];
		it[2][1] = it[1][2];
		it[2][0] = it[0][2];
		return it;
	}

}// end class