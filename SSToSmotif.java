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
    
    public static ArrayList<Smotifs> smotifArray;

    public SSToSmotif(ArrayList<SecondaryStructure> ssList, ArrayList<Geometry> geometries) {
	smotifArray = new ArrayList<Smotifs>();
	printOutInformation(ssList, geometries);
	// You only need to do this once; I have done it. Success!
	//CreateDirectories f2 = new CreateDirectories();
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
	
	type = cutToSize(type, 10);
	String d = cutToSize(gg.getD(), 8);
	String delta = cutToSize(gg.getDelta(), 8);
	String theta = cutToSize(gg.getTheta(), 8);
	String rho = cutToSize(gg.getRho(), 8);

	String bin = binMe(type, d, delta, theta, rho, Integer.parseInt(ss1.firstResidue().getResNum()), Integer.parseInt(ss2.lastResidue().getResNum()));
	return AnalyzeOneProtein.pdbID + "         " + bin + "  " + stRes + "" + endRes + 
	    "" + type + " " + d + "  " + delta + "  " + theta + "  " + rho + "";
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
	try {
	    PrintWriter out = new PrintWriter(new FileWriter("OutputFile.txt"));

	    // print header
	    out.println("" + 
			"Bryn Reinstadler and Jennifer Van, 2012.\n" + 
			"Ran with _________ DSSP/PDB files on ___________(date). \n\n" + 
			"PDB/DSSP     bin    StartRes    EndRes    (a/b)      d         delta     theta     rho       ");
	    // bin starts at 13 
	    // startres starts at 20
	    // endres starts at 32
	    // type information starts at 42
	    // d at 53
	    // delta at 63
	    // theta at 73
	    // rho at 80

	    for (int i=0; i<ssArray.size() - 2; ++i) {
		for(int k=0; k<geoArray.size(); ++k){
		    if(ssArray.get(i).exists() && !ssArray.get(i).getSSType().equals("T") && ssArray.get(i+1).exists() && 
		       ssArray.get(i+2).exists() && !ssArray.get(i+2).getSSType().equals("T")) {
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
    
    public String binMe(String type, String d, String delta, String theta, String rho,
			int startRes, int endRes) {
	String bin = "";

	if(d.trim().equals("NaN") || delta.trim().equals("NaN") || theta.trim().equals("NaN") || rho.trim().equals("NaN")) {
	    return "Nobin";
	}

	//determine type
	if(type.trim().equals("AA")) { bin += "0"; }
	else if(type.trim().equals("AB")) { bin += "1"; }
	else if(type.trim().equals("BA")) { bin += "2"; }
	else if(type.trim().equals("BB")) { bin += "3"; }

	//determine d
	double dInt = Double.parseDouble(d);
	if(dInt >= 40) { dInt = 39; }
	bin += "" + (int)Math.floor(dInt/4) + "";

	//determine delta
	double deltaInt = Double.parseDouble(delta);
	if(deltaInt >= 180) { deltaInt = 179; }
	bin += "" + (int)Math.floor(deltaInt/60) + "";

	//determine theta
	double thetaInt = Double.parseDouble(theta);
	if(thetaInt >= 180) { thetaInt  = 179; }
	bin += "" + (int)Math.floor(thetaInt/60) + "";

	//determine rho
	double rhoInt = Double.parseDouble(rho);
	if (rhoInt >= 360) { rhoInt = 159; }
	if (rhoInt < 30) { rhoInt = 30; }
	bin += "" + (int)Math.floor(rhoInt/60) + "";

	smotifArray.add(new Smotifs(bin, dInt, deltaInt, thetaInt, rhoInt, Integer.parseInt(bin.substring(0,1)), AnalyzeOneProtein.pdbID, startRes, endRes));

	return bin;
    }

}