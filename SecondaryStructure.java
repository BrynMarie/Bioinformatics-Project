/**
 * Programmers: Bryn Reinstadler and Jennifer Van
 * Date: July 18th 2012
 * Filename: SecondaryStructure.java
 * 
 * Purpose: This file is an object that is meant to carry information about secondary structures, including
 * the type of secondary structure, residues in the secondary structure, the length, and whether or not,
 * by the criteria of Fuentes-Fernandez, the secondary structure should be counted as existing.
 * */

import java.io.*;
import java.util.*;

public class SecondaryStructure {

    String sstype;
    ArrayList<Residue> resArray = new ArrayList<Residue>();
    int length;
    boolean exists;

	/* A constructor for Secondary Structure that provides information on 
		type, length, and the residues within. */
    public SecondaryStructure(String sstype, int length, ArrayList<Residue> resArray) {
    	this.sstype = sstype;
    	this.length = length;
    	this.resArray = resArray;
    }
    
    public SecondaryStructure(String sstype, boolean exists) {
    	this.sstype = sstype;
    	this.exists = exists;
    }

    //Accessor fields
    public String getSSType() {
		return sstype;
    }
    
    public int length() {
		return length;
    }
    
    public Residue firstResidue() {
    	return resArray.get(0);
    }

    public boolean exists() {
		return exists;
    }

	// is this ever used?
    public Residue lastResidue() {
    	return resArray.get(resArray.size() - 1);
    }

    //Mutator fields
    public void setSSType(String arg) {
		if(arg.equals("H") || arg.equals("S") || arg.equals("T")) {
		    sstype = arg;
		}
		else {
		    sstype = ""; //throw an exception
		}
    }

    public void setExists(boolean state) {
		exists = state;
    }

    public void setLength(int newLength) {
		length = newLength;
    }
    
    public void addResidue(Residue res){
    	resArray.add(res);
    }
    
    public void addResArray(ArrayList<Residue> resArray) {
    	this.resArray = resArray;
    }
}