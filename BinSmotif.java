import java.io.*;
import java.util.*;

public class BinSmotif {

    public BinSmotif(Smotifs binMe) {
	try {
	    String bin = binMe.getBin();
	    String path = bin.substring(0,1) + "/" + bin.substring(1,2) + "/" + 
		bin.substring(2,3) + "/" + bin.substring(3,4) + "/" + bin.substring(4,5);
	    String totalPath = "C:/emacs/Bioinformatics-Project/Bins" + path + "/" + 
		bin + "output.txt";
	    File file = new File(totalPath);
	    boolean exists = file.exists();

	    if(exists) {
		BufferedWriter buffy = new BufferedWriter(new FileWriter(totalPath, true));
		buffy.write(binMe.toString());
		buffy.newLine();
		buffy.close();
	    }
	    else {
		PrintWriter out = new PrintWriter(new FileWriter(totalPath));
		out.println("Bin    dssp/pdb     startres     endres     Distance (d)    Hoist (delta)     Packing (theta)    Meridian (Rho)");
		out.println(binMe.toString());
		out.close();
	    }
	}
	catch (Exception e) { e.printStackTrace(); }
    }
}