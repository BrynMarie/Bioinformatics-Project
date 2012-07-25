/**
 * Programmer: Bryn Reinstadler
 * Date: July 25th, 2012
 * Filename: SSToSmotif.java
 * 
 * Purpose: This file takes in an arraylist of secondary structures
 * and uses them as well as their geometries as derived by the array of
 * Geometrys to finalize information about a single protein's Smotifs.
 * */

import java.io.*;
import java.util.*;

public class SSToSmotif {
    
    public SSToSmotif(ArrayList<SecondaryStructure> ssList, ArrayList<Geometry> geometries) {
	printOutInformation(ssList, geometries);
    }

    public String parseToString(SecondaryStructure ss1, SecondaryStructure ss2, Geometry gg) {
	//return information needed in appropriate manner
	String stRes = cutToSize(gg.getStart(), 12);
	String endRes = cutToSize(gg.getEnd(), 10);
	String type = "";
	
	if(ss1.getSSType().equals("S")) { type += "B"; }
	if(ss1.getSSType().equals("H")) { type += "A"; }
	if(ss1.getSSType().equals("T")) { type += "T"; }
	if(ss2.getSSType().equals("S")) { type += "B"; }
	if(ss2.getSSType().equals("H")) { type += "A"; }
	if(ss2.getSSType().equals("T")) { type += "T"; }
	
	type = cutToSize(type, 18);
	String d = cutToSize(gg.getD(), 8);
	String delta = cutToSize(gg.getDelta(), 8);
	String theta = cutToSize(gg.getTheta(), 8);
	String rho = cutToSize(gg.getRho(), 8);

	return "             " + stRes + "" + endRes + "" + type + " " + d + 
	    "  " + delta + "  " + theta + "  " + rho + "";
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

    public void printOutInformation(ArrayList<SecondaryStructure> ssArray, ArrayList<Geometry> geoArray) {
	System.out.println("Got to poi");
	try {
	    System.out.println("Inside try");
	    PrintWriter out = new PrintWriter(new FileWriter("OutputFile.txt"));

	    // print header
	    out.println("" + 
			"Bryn Reinstadler and Jennifer Van, 2012.\n" + 
			"Ran with _________ DSSP/PDB files on ___________(date). \n\n" + 
			"PDB/DSSP     StartRes    EndRes    (aa/ab/ba/bb)      d         delta     theta     rho       ");
	    // start res starts at 12
	    // endres starts at 24
	    // type information starts at 34
	    // d at 53
	    // delta at 63
	    // theta at 73
	    // rho at 80

	    for (int i=0; i<ssArray.size() - 2; ++i) {
		for(int k=0; k<geoArray.size(); ++k){
		    if(ssArray.get(i).exists() && ssArray.get(i+1).exists() && ssArray.get(i+2).exists()) {
			if(geoArray.get(k).getStart().equals(ssArray.get(i).firstResidue().getResNum())) {
			    System.out.print("yay");
			    String printMe = parseToString(ssArray.get(i), 
							   ssArray.get(i+2), geoArray.get(k));
			    out.println(printMe);
			    k = geoArray.size();
			}
		    }
		}
	    }
	    out.close();
	}
	catch (IOException ioe) { ioe.printStackTrace(); }		
    }
}