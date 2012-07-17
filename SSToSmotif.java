import java.io.*;
import java.util.*;

public class SSToSmotif {
    
    // takes an arraylist of secondary structures
    // prints out final information


	public SSToSmotif(ArrayList<SecondaryStructure> ssList, CalcGeo geometries) {
		ArrayList<SecondaryStructure> ssArray = ssList;
		
		printOutInformation(ssArray, geometries);
	}
	
	// will be replaced by a print to file eventually
	public void printOutInformation(ArrayList<SecondaryStructure> ssArray, CalcGeo geometries) {
		// we need to sync up the information from the ssarray and the geometries somehow or this is going to be a mess.
		
		try {
			PrintWriter out = new PrintWriter(new FileWriter(args[0]));
			
			for(int i = 0; i < ssArray.size(); ++i) {
				out.println("Put information here");
			}
			out.close();
		}
		catch (IOException ioe) { e.printStackTrace(); }		
	}
}