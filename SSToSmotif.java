import java.io.*;
import java.util.*;

public class SSToSmotif {
    
	// takes an arraylist of secondary structures
	// prints out final information


	public SSToSmotif(ArrayList<SecondaryStructure> ssList, ArrayList<Geometry> geometries) {
		syncArrays(geometries, ssArray);
		
		printOutInformation(ssArray, geometries);
	}
	
	public String parseToString(SecondaryStructure ss, Geometry gg) {
		//return information needed in appropriate manner
		String stRes = gg.getStart();
		String endREs = gg.getEnd();
		String type;
		switch (ss.getSSType()) {
			case "S":
				type = "β";
				break;
				
			case "H":
				type = "α";
				break;
				
			default:
				type = "T";
				break;
		}
		String d = gg.getD();
		String delta = gg.getDelta();
		String theta = gg.getTheta();
		String rho = gg.getRho();
		
		// figure out how to set string at specific places?
	}
	
	// will be replaced by a print to file eventually
	public void printOutInformation(ArrayList<SecondaryStructure> ssArray, ArrayList<Geometry> geoArray) {
		try {
			PrintWriter out = new PrintWriter(new FileWriter(args[0]));
			
			// print header
			out.println("" + 
			"Bryn Reinstadler and Jennifer Van, 2012.\n" + 
			"Ran with _________ DSSP/PDB files on ___________(date)." + 
			"PDB/DSSP     StartRes    EndRes    (αα/αβ/βα/ββ)      d       δ       θ       ρ       ");
			// start res starts at 12
			// endres starts at 24
			// type information starts at 34
			// d at 53
			// delta at 61
			// theta at 69
			// rho at 76
			for (int i=0; i<ssArray.size(); ++i) {
				if(ssArray.get(i).exists()) {
					String printMe = parseToString(ssArray.get(i), geoArray.get(i));
					out.println();
				}
			}
			out.close();
		}
		catch (IOException ioe) { e.printStackTrace(); }		
	}
}