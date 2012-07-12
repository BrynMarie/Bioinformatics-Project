import java.util.*;
import java.io.*;

public class ResidueToSS {

    //this class will take an array of residues, some of which are missing in the dssp file or the pdb file. It has not been filtered of loops that are too long, either.

    //TODO: Filter out loops that are greater than 12 residues long
    //TODO: Discard loops with missing residues and/or main chain atoms
    //TODO: Filter residues with too high of a zscore
    //look over coding ideas docs before going too far on this one.
    public ResidueToSS(ArrayList<Residue> resArray) {
        oldRes = resArray.get(0);
        ArrayList<Residue> currentInSS = new ArrayList<Residue>();
        ArrayList<SecondaryStructure> ssArray = new ArrayList<SecondaryStructure>();
        currentInSS.add(oldRes);
        for (int i=1; i<resArray.size(); ++i) {
            currentRes = resArray.get(i);
            
            //the old and current res are in the same ss, and current res isn't missing
            if(currentRes.getSS().equals(oldRes.getSS() && !currentRes.isMissing()) {
                currentInSS.add(currentRes);
            }
            //the old and current res are in different ss's, and the current res isn't missing
            else if(!currentRes.getSS().equals(oldRes.getSS())) {
                //add currentInSS to the ssArray
                //if missing, marker for next not to exist
            }
            // they are in the same ss and the current res is missing
            else if(currentRes.getSS().equals(oldRes.getSS()){
                currentInSS.clear();
                ssArray.add(new SecondaryStructure(currentRes.getSS(),false));
            }
        }
    }

}