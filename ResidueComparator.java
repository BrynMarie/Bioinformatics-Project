import java.util.*;
import java.io.*;

public class ResidueComparator implements Comparator<Residue> {

    public int compare(Residue r1, Residue r2) {
	return r1.getResNum().compareTo(r2.getResNum());
    }

    public boolean equals(Object obj) {
	return this.equals(obj);
    }
}