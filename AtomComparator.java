import java.util.*;
import java.io.*;

//to be used to sort atoms according to which residue they are in.
public class AtomComparator implements Comparator<Atom> {
    
    public int compare(Atom a1, Atom a2) {
        Integer parsed1 = a1.getResNum();
	Integer parsed2 = a2.getResNum();
	return parsed1.compareTo(parsed2);
    }
    
    public boolean equals(Object obj){
	return this.equals(obj);
    }
}