import java.util.*;
import java.io.*;

public class ResidueComparator implements Comparator<Residue> {

    public int compare(Residue r1, Residue r2) {
    	Integer r1i = Integer.parseInt(r1.getResNum());
    	Integer r2i = Integer.parseInt(r2.getResNum());
	return r1i.compareTo(r2i);
    }

    public boolean equals(Object obj) {
	return this.equals(obj);
    }
}