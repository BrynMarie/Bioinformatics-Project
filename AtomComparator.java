/**
 * Programmer: Bryn Reinstadler
 * Date: July 18th 2012
 * File Name: Atom Comparator
 * 
 * Purpose: Sorts atoms by pdb residue number
 * */

import java.util.*;
import java.io.*;

public class AtomComparator implements Comparator<Atom> {
    
    /* Compares two atoms and returns the one with -, 0, or + 
    	depending on whether a1 is less than, equal to, or greater than a2.
    */
    public int compare(Atom a1, Atom a2) {
        Integer parsed1 = Integer.parseInt(a1.getResNum());
	Integer parsed2 = Integer.parseInt(a2.getResNum());
	return parsed1.compareTo(parsed2);
    }
    
    /* Returns true only if both objects point are identical */
    public boolean equals(Object obj){
	return this.equals(obj);
    }
}