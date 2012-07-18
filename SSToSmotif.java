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
	}
	
	// will be replaced by a print to file eventually
	public void printOutInformation(ArrayList<SecondaryStructure> ssArray, ArrayList<Geometry> geoArray) {
		// we need to sync up the information from the ssarray and the geometries somehow or this is going to be a mess.
		
		try {
			PrintWriter out = new PrintWriter(new FileWriter(args[0]));
			
			// print header
			out.println("HEADER");
			
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