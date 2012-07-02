import java.io*;
import java.util.*;

public class ParseFiles {
    ArrayList<String> dsspFile;

    public ParseFiles () {
        
    }

    public void parseDSSP(ArrayList<String> dsspFile) {
        this.dsspFile = dsspFile;
    }
    
     	public ArrayList<String> getCoordinates(ArrayList<String> rawPdbFile) {
    		ArrayList<String> coordinatesOfAtoms = new ArrayList<String>();
		for (int i = 0; i < rawPdbFile.size(); i++) {
			String[] strs = rawPdbFile.get(i).split("\\s+");
			String line = "";
			if (strs[0].equals("ATOM")) {
				//collect atom name, residue, residue ID #, and X, Y, Z coordinates in a single string
				line = strs[0] + " " + strs[2] + " " + strs[3] + " " + strs[5] + strs[6] + " " + strs[7] + " " + strs[8];
				coordinatesOfAtoms.add(line);
			}
			else if (strs[0].equals("TER"))
				//C-terminus flag
				line = strs[0] + " " + strs[2] + " " + strs[4];
				coordinatesOfAtoms.add(line);
			}
		}
		return coordinatesOfAtoms;
	}
    
	public double getTotalMean(ArrayList<String> rawPdbFile) {
		// calculate mean b-factor and std deviation b-factors for whole
		// protein
		int countTotalAtoms = 0;
		double totalbFactor = 0;
		for (int i = 0; i < rawPdbFile.size(); i++) {
			String[] strs = rawPdbFile.get(i).split("\\s+");
			if (strs[0].equals("ATOM")) {
				countTotalAtoms++;
				double strbFactortoDbl = Double.valueOf(strs[10].trim())
						.doubleValue();
				totalbFactor += strbFactortoDbl;
			}
		}
		double meanbFactor = totalbFactor / countTotalAtoms;
		return meanbFactor;
	}

	public double getTotalStdDev(ArrayList<String> rawPdbFile) {
		int countTotalAtoms = 0;
		double totalbFactor = 0;
		double totalsquaredbFactor = 0;
		for (int i = 0; i < rawPdbFile.size(); i++) {
			String[] strs = rawPdbFile.get(i).split("\\s+");
			if (strs[0].equals("ATOM")) {
				countTotalAtoms++;
				double strbFactortoDbl = Double.valueOf(strs[10].trim())
						.doubleValue();
				double squaredbFactor = Math.pow(strbFactortoDbl, 2);
				totalbFactor += strbFactortoDbl;
				totalsquaredbFactor += squaredbFactor;
			}
		}
		double StdDev = calcStdDev(totalbFactor, totalsquaredbFactor,
				countTotalAtoms);
		return StdDev;
	}

	public ArrayList<Double> calculateBfactorZScore(ArrayList<String> rawPdbFile,
			double totalMean, double totalStdDev) {
		ArrayList<Double> zScoresOfPDBFile = new ArrayList<Double>();
		int countTotalAtoms = 0;
		int currentResidue = 0;
		int pastResidue = 0;
		double currentResidueBfactor = 0;
		long countResidues = 0;
		double meanOfCurrentResidue = 0;
		for (int i = 0; i < rawPdbFile.size(); i++) {
			String[] strs = rawPdbFile.get(i).split("\\s+");
			if (strs[0].equals("ATOM")) { // only want lines from PDB that are
											// from ATOM section
				// calculate total B-factor of a residue
				if (countTotalAtoms == 0) {
					pastResidue = Integer.parseInt(strs[5]);
					countResidues++;
					currentResidueBfactor += Double.valueOf(strs[10].trim())
							.doubleValue();
				} else {
					currentResidue = Integer.parseInt(strs[5]);
					if (currentResidue == pastResidue) {
						countResidues++;
						currentResidueBfactor += Double
								.valueOf(strs[10].trim()).doubleValue();
					} else {
						meanOfCurrentResidue = currentResidueBfactor
								/ (countResidues); // calculate mean B-factor of
													// a
													// residue
						zScoresOfPDBFile.add(zScore(meanOfCurrentResidue, totalMean, totalStdDev));
						countResidues = 0;
						pastResidue = currentResidue;
						currentResidueBfactor = 0;
						currentResidueBfactor += Double
								.valueOf(strs[10].trim()).doubleValue();
					}
				}
			}
		}
		return zScoresOfPDBFile;
	}
	
	public double zScore(double currentMean, double totalMean,
			double totalStdDev) {
		double zScore = (currentMean - totalMean) / totalStdDev;
		return zScore;
	}

    
    public double calcStdDev(double total, double squaredTotal, int counter) {
    	double firstTerm = squaredTotal/(counter-1);
    	double secondTerm = (counter/(counter-1));
    	double thirdTerm = Math.pow((total/counter), 2);
    	double stdDev = Math.sqrt(firstTerm-(secondTerm*thirdTerm));
    	return stdDev;
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
    }
}