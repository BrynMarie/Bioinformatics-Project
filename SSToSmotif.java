import java.io.*;
import java.util.*;

public class SSToSmotif {
    
	// takes an arraylist of secondary structures
	// prints out final information
	public SSToSmotif(ArrayList<SecondaryStructure> ssList, ArrayList<Geometry> geometries) {
		printOutInformation(ssList, geometries);
	}
	
	public String parseToString(SecondaryStructure ss1, SecondaryStructure ss2, Geometry gg) {
		//return information needed in appropriate manner
		String stRes = cutToSize(gg.getStart(), 12);
		String endRes = cutToSize(gg.getEnd(), 10);
		String type = "";
		switch (ss1.getSSType()) {
			case "S":
				type += "B";
				break;
				
			case "H":
				type += "A";
				break;
				
			default:
				type += "T";
				break;
		}
		switch (ss2.getSSType()) {
			case "S":
				type += "B";
				break;
				
			case "H":
				type += "A";
				break;
				
			default:
				type += "T";
				break;
		}
		type = cutToSize(type, 19);
		String d = cutToSize(gg.getD(), 8);
		String delta = cutToSize(gg.getDelta(), 8);
		String theta = cutToSize(gg.getTheta(), 8);
		String rho = cutToSize(gg.getRho(), 8);
		
		return "" + stRes + "" + endRes + "" + type + "" + d + 
		    "" + delta + "" + theta + "" + rho + "";
	}
	
	public String cutToSize(String o, int limit) {
		
		if(o.length() > limit) {
			o = o.substring(0, limit);	
		}
		else if (o.length() < limit) {
			while (o.length() < limit) {
				o += " ";
			}
		}
		
		return o;
	}
	
	// will be replaced by a print to file eventually
	public void printOutInformation(ArrayList<SecondaryStructure> ssArray, ArrayList<Geometry> geoArray) {
		try {
			PrintWriter out = new PrintWriter(new FileWriter("OutputFile"));
			
			// print header
			out.println("" + 
			"Bryn Reinstadler and Jennifer Van, 2012.\n" + 
			"Ran with _________ DSSP/PDB files on ___________(date)." + 
			"PDB/DSSP     StartRes    EndRes    (aa/ab/ba/bb)      d       delta   theta   rho     ");
			// start res starts at 12
			// endres starts at 24
			// type information starts at 34
			// d at 53
			// delta at 61
			// theta at 69
			// rho at 76
			int gCounter = 0;
			
			for (int i=0; i<ssArray.size(); ++i) {
				if(ssArray.get(i).exists() && !ssArray.get(i).getSSType().equals("T")) {
					if(ssArray.get(i+1).exists()) { // this should be the turn
						String printMe = parseToString(ssArray.get(i), 
							ssArray.get(i+2), geoArray.get(i));
						out.println(printMe);
					}

					i += 2; // check for off by one error
					++gCounter;
					
				}
			}
			out.close();
		}
		catch (IOException ioe) { ioe.printStackTrace(); }		
	}
}