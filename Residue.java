/**
 * Programmer: Bryn Reinstadler
 * Date: July 25th, 2012
 * Filename: Residue.java
 * 
 * Purpose: Provides a custom object that stores necessary
 * information about each residue.
 * */

import java.io.*;
import java.util.*;

public class Residue {
    
    double bFactor;
    String pdbResNum, ssType;
    boolean nTerm, cTerm, exists;
    ArrayList<Atom> atomList;
    CartesianCoord pmoi;
    
    //constructors
    public Residue(String pdbResNum, double bFactor, String ssType, 
    	boolean nTerm, boolean cTerm, ArrayList<Atom> atomList, CartesianCoord pmoi) {
        this.bFactor = bFactor;
        this.pdbResNum = pdbResNum;
        this.ssType = ssType;
        this.atomList = atomList;
        this.nTerm = nTerm;
        this.cTerm = cTerm;
	this.atomList = atomList;
	this.pmoi = pmoi;
    }

    public Residue(String pdbResNum, boolean exists) {
	this.pdbResNum = pdbResNum.toString();
	this.exists = exists;
    }

    public Residue(String pdbResNum, CartesianCoord pmoi, ArrayList<Atom> atomList, String sstype, boolean nt, boolean ct) {
    	this.pdbResNum = pdbResNum;
	this.pmoi = pmoi;
	this.atomList = atomList;
	this.ssType = sstype;
	this.nTerm = nt;
	this.cTerm = ct;
    }

    public Residue(String pdbResNum, String ss) {
	this.pdbResNum = pdbResNum;
	this.ssType = ss;
    }

    /* a toString method that returns information on res num, ss type, b factor,
    and the size of the atom list */
    public String toString() {
	return "Residue " + pdbResNum + ": " + ssType + " " + bFactor + " " + atomList.size();	    
    }
    
     //accessor methods
     public boolean exists() {
     	return exists;
    }

     public double getBFactor() {
    	return bFactor;
    }
    
    public String getResNum() {
     	return pdbResNum;
    }
    
    public String getSS() {
    	return ssType;
    }
    
    public boolean getCTerm() {
    	return cTerm;
    }
    
    public boolean getNTerm() {
    	return nTerm;
    }

    public ArrayList<Atom> getAtomList() {
	return atomList;
    }
    
    public CartesianCoord getPMOI() {
    	return pmoi;
    }

    //mutator methods
    public void setExists(boolean ex) {
    	exists = ex;
    }
    
    public void setBFactor(double newBF){
    	bFactor = newBF;
    }
    
    public void setResNum(String pdbResNum){
    	this.pdbResNum = pdbResNum;
    }
    
    public void setSSType(String newSsType) {
	ssType = newSsType;
    }
    
    public void setCTerm(boolean newCTerm) {
    	cTerm = newCTerm;
    }
    
    public void setNTerm(boolean newNTerm) {
    	nTerm = newNTerm;
    }
    
    public void setAtomList(ArrayList<Atom> al) {
    	atomList = al;
    }
    
    public void setPMOI(CartesianCoord pmoi) {
    	this.pmoi = pmoi;
    }
}