import java.util.*;
import java.io.*;

public class ResidueToSS {

    //this class will take an array of residues, some of which are missing in the dssp file or the pdb file. 
    //It has not been filtered of loops that are too long, either.

    //DONE: Filter out loops that are greater than 12 residues long w/ loopCounter
    //TODO: Discard loops with missing residues
    //look over coding ideas docs before going too far on this one.
    public ResidueToSS(ArrayList<Residue> resArray) {
        oldRes = resArray.get(0);
        ArrayList<Residue> currentInSS = new ArrayList<Residue>();
        ArrayList<SecondaryStructure> ssArray = new ArrayList<SecondaryStructure>();
        currentInSS.add(oldRes);
        boolean turn = false;
        String nextNotExist;
        
        if(oldRes.getSS().equals("T")) {
            turn = true;
        }
        
        loopCounter = 0;
        
        for (int i=1; i<resArray.size(); ++i) {
            currentRes = resArray.get(i);
            
            //the old and current res are in the same ss, and current res isn't missing
            if(currentRes.getSS().equals(oldRes.getSS() && !currentRes.isMissing()) {
                currentInSS.add(currentRes);
                if (loopCounter != 0) { ++loopCounter; }
                if (loopCounter == 13) { 
                    currentInSS.clear();
                    ssArray.add(new SecondaryStructure(currentRes.getSS(), false));
                    nextNotExist = currentRes.getSS();
                }
            }
            //the old and current res are in different ss's
            else if(!currentRes.getSS().equals(oldRes.getSS())) {
                //add currentInSS to the ssArray
                //if missing, marker for next not to exist
                if(currentRes.getSS().equals("T")) {
                    turn = true;
                    loopCounter = 1;
                }
                else {
                    turn = false;
                    loopCounter = 0;
                }
                if(currentRes.isMissing()) {
                    nextNotExist = currentRes.getSS();
                }
                else {
                    nextNotExist = "";
                }
            }
            // they are in the same ss and the current res is missing
            else if(currentRes.getSS().equals(oldRes.getSS()){
                if(currentRes.getSS().equals("T")) {
                    currentInSS.clear();
                    ssArray.add(new SecondaryStructure(currentRes.getSS(),false));  
                }
                else {
                    currentInSS.add(currentRes);
                }
            }
        }
    }

}