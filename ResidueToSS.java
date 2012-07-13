import java.util.*;
import java.io.*;

public class ResidueToSS {

    //this class will take an array of residues, some of which are missing in the dssp file or the pdb file. 
    //It has not been filtered of loops that are too long, either.

    //Done: Filter out loops that are greater than 12 residues long w/ loopCounter
    //Done: Discard loops with missing residues
    //look over coding ideas docs before going too far on this one.
    public ResidueToSS(ArrayList<Residue> resArray) {      
        ArrayList<Residue> currentInSS = new ArrayList<Residue>();
        ArrayList<SecondaryStructure> ssArray = new ArrayList<SecondaryStructure>();
        oldRes = resArray.get(0);
        currentInSS.add(oldRes);
        boolean turn = false;
        String nextNotExist;
        String ss;
        
        if(oldRes.getSS().equals("T")) {
            turn = true;
        }
        
        loopCounter = 0;
        
        for (int i=1; i<resArray.size(); ++i) {
            currentRes = resArray.get(i);
            
            //the old and current res are in the same ss, and current res isn't missing
            if(currentRes.getSS().equals(oldRes.getSS() && !currentRes.isMissing()) {
                ss = currentRes.getSS();
                
                //if the next one exists you'll add it; if not, you won't.
                if(!nextNotExist.equals(ss)) { 
                    if (turn) { ++loopCounter; }
                    if (turn && loopCounter == 13) { 
                        currentInSS.clear();
                        ssArray.add(new SecondaryStructure(currentRes.getSS(), false));
                        nextNotExist = currentRes.getSS();
                    }
                    currentInSS.add(currentRes);
                }
                
                
            }
            //the old and current res are in different ss's
            else if(!currentRes.getSS().equals(oldRes.getSS())) {
                //add currentInSS to the ssArray
                //if missing, marker for next not to exist
                ss = currentRes.getSS();
                if(ss.equals("T")) {
                    turn = true;
                    loopCounter = 1;
                }
                else {
                    turn = false;
                    loopCounter = 0;
                }
                if(currentRes.isMissing()) {
                    ssArray.add(new SecondaryStructure(ss, false));
                    nextNotExist = ss;
                }
                else {
                    nextNotExist = "";
                }
            }
            // they are in the same ss and the current res is missing
            else if(currentRes.getSS().equals(oldRes.getSS()){
                ss = currentRes.getSS();
                
                if(ss.equals("T")) {
                    currentInSS.clear();
                    ssArray.add(new SecondaryStructure(currentRes.getSS(),false)); 
                    nextNotExist = ss;
                }
                else {
                    currentInSS.add(currentRes);
                }
            }
        }
    }
}