/**
 * Programmer: Bryn Reinstadler
 * Date: July 25th 2012
 * Filename: ResidueToSS.java
 * 
 * Purpose: This class will take an array of residues, some of which are missing in the 
 * dssp file or the pdb file, and filter out loops that are greater than 12 residues long 
 * as well as apply some other filters.
 * */ 


import java.util.*;
import java.io.*;

public class ResidueToSS {

    public static ArrayList<SecondaryStructure> ssList;
    public static ArrayList<Geometry> geometries;
	
    public ResidueToSS(ArrayList<Residue> resArray, ArrayList<Residue> pmoiArray) {
	this.ssList = resToSS(resArray);
	CalcGeo f5 = new CalcGeo(pmoiArray);
    	this.geometries = f5.calculate(pmoiArray);
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

	if(oldRes.getSS().equals("T")) {
            turn = true;
        }
        
        for (int i=1; i<resArray.size(); ++i) { 
	    currentRes = resArray.get(i);
	    currentResNum = Integer.parseInt(currentRes.getResNum());
	    
	    if(!currentRes.getNTerm()) { // same secondary structure
		if(turn) {
		    ++loopCounter;
		    if(loopCounter == 13) { nextNotExist = true; ++nec; }
		    if(oldResNum + 1 != currentResNum) { nextNotExist = true; ++nec; }
		}
		currentInSS.add(currentRes);
		// end behavior
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
		if(nextNotExist) { 
		    ssArray.add(parseSS(currentInSS, false));
		    nextNotExist = false;
		}
		else {
		    ssArray.add(parseSS(currentInSS, true));
		}
		currentInSS = new ArrayList<Residue>();
		currentInSS.add(currentRes);
		if(!currentRes.getSS().equals("T")) {
		    turn = false;
		}
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