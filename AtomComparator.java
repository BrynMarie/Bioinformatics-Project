import java.util.*;
import java.io.*;

public class AtomComparator implements Comparator<Atom> {

    public int compare(Atom a1, Atom a2) {
	if(a1.resNum > a2.resNum) {
	    return 1;
	}
	else if(a1.resNum < a2.resNum) {
	    return -1;
	}
	else {
	    return 0;
	}
    }

    public boolean equals(Object obj){
	return this.equals(obj);
    }
}