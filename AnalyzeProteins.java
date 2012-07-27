import java.io.*;
import java.util.*;

public class AnalyzeProteins {

    public AnalyzeProteins() {
	//iterate through all dssp files on-hand
	int counter = 0;
	File dir = new File("C:/Users/Bryn/Documents/CodingProjects/dssp/");
	for (File child : dir.listFiles()) {
	    if (".".equals(child.getName()) || "..".equals(child.getName())) {
		continue;  // Ignore the self and parent aliases.
	    }
	    System.out.println(child.getName());
	    int last = child.getName().length();
	    String pdbID = child.getName().substring(last - 9, last - 5);
	    ++counter;
	    AnalyzeOneProtein ap1 = new AnalyzeOneProtein(pdbID);
	    if(counter > 2) {
		break;
	    }
	    //	    System.out.println(pdbID);
	    // Do something with child
	}
    }

    public static void main(String[] args) {
	AnalyzeProteins ap0 = new AnalyzeProteins();
    }
}