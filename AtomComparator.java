import java.util.*;
import java.io.*;

//to be used to sort atoms according to which residue they are in.
public class AtomComparator implements Comparator<Atom> {
	
    public int compare(Atom a1, Atom a2) {
		return a1.getResNum().compareTo(a2.getResNum());
    }

    public boolean equals(Object obj){
		return this.equals(obj);
    }
}