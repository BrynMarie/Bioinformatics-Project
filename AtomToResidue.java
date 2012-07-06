import java.io.*;
import java.util.*;

public class AtomToResidue {

    ArrayList<Atom> sortedAtomList;

    //takes as input an unsorted arraylist of atoms
    public AtomToResidue(ArrayList<Atom> atomList) {
	
	sortedAtomList = sortAtoms(atomList);
    }

}