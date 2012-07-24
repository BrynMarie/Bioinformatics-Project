/**
 * 
 * this class will take an array of residues, some of which are missing in the dssp file or the pdb file. 
 * Filter out loops that are greater than 12 residues long w/ loopCounter
 * Discard loops with missing residues
 * 
 * */ 


import java.util.*;
import java.io.*;

public class ResidueToSS {

    public static ArrayList<SecondaryStructure> ssList;
    public static ArrayList<Geometry> geometries;
	
    public ResidueToSS(ArrayList<Residue> resArray, ArrayList<Residue> pmoiArray) {
	this.ssList = resToSS(resArray);
	System.out.println("Size of secondary Structure list: " + ssList.size());    	
	CalcGeo f5 = new CalcGeo(pmoiArray);
    	this.geometries = f5.calculate(pmoiArray);
	System.out.println("Geo size : " + geometries.size());
    	// pass geometries to SSToSmotif
    	//ArrayList<SecondaryStructure> ssList, ArrayList<Geometry> geometries
    	SSToSmotif f6 = new SSToSmotif(ssList, geometries);
    }
    
    public ArrayList<SecondaryStructure> resToSS(ArrayList<Residue> resArray) {
    	
    	//holds residues in each respective ss
        ArrayList<Residue> currentInSS = new ArrayList<Residue>();
        // array to hold ss's
        ArrayList<SecondaryStructure> ssArray = new ArrayList<SecondaryStructure>();
        Residue oldRes = resArray.get(0);
	Residue currentRes;
        currentInSS.add(oldRes);
        boolean turn = false;
        boolean nextNotExist = false;
	int loopCounter = 0;
	int oldResNum = Integer.parseInt(oldRes.getResNum()), currentResNum;
	int nec = 0;

	if(oldRes.getSS().equals("T")) {
            turn = true;
        }
        
        for (int i=1; i<resArray.size(); ++i) { 
	    currentRes = resArray.get(i);
	    currentResNum = Integer.parseInt(currentRes.getResNum());
	    
	    if(!currentRes.getCTerm()) { // same secondary structure
		if(turn) {
		    ++loopCounter;
		    if(loopCounter == 13) { nextNotExist = true; ++nec; }
		    if(oldResNum + 1 != currentResNum) { nextNotExist = true; ++nec; }
		}
		currentInSS.add(currentRes);
		if(i == resArray.size() - 1) {
		    if(nextNotExist) {
			ssArray.add(parseSS(currentInSS, false));
			nextNotExist = false;
		    }
		    else {
			ssArray.add(parseSS(currentInSS, true));
		    }
		}
	    }
	    if(currentRes.getNTerm()) { // different secondary structure 
		if(currentRes.getCTerm()) {
		    if(nextNotExist) {
			ssArray.add(parseSS(currentInSS, false));
			nextNotExist = false;
		    }
		    else {
			ssArray.add(parseSS(currentInSS, true));
		    }
		    if(!currentRes.getSS().equals("T")) { turn = false; }
		    currentInSS = new ArrayList<Residue>();
		}
		currentInSS.add(currentRes);
		if(nextNotExist) {
		    ssArray.add(parseSS(currentInSS, false));
		    nextNotExist = false;
		}
		else {
		    ssArray.add(parseSS(currentInSS, true));
		}
		if(!currentRes.getSS().equals("T")) {
		    turn = false;
		}
		currentInSS = new ArrayList<Residue>();
	    }
	    oldResNum = currentResNum;
	    oldRes = currentRes;
	    
        } // end of for
        return ssArray;
    } // end of method
    
    // merge resList into a secondary structure with all necessary information
    public SecondaryStructure parseSS(ArrayList<Residue> resList, boolean exists) {
	if(exists) {
	    return new SecondaryStructure(resList.get(0).getSS(), resList.size() - 1, resList);
	}
	return new SecondaryStructure("T", false);
    }
}