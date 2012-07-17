import java.io.*;
import java.util.*;

public class Residue {
    
    double bFactor;
    String pdbResNum;
    String ssType;
    boolean nTerm, cTerm, exists;
    ArrayList<Atom> atomList = new ArrayList<Atom>();
    CartesianCoord pmoi;
    // other state
    
    //constructors
    public Residue(String pdbResNum, double bFactor, String ssType, 
    	boolean nTerm, boolean cTerm, ArrayList<Atom> atomList, CartesianCoord pmoi) {
        this.bFactor = bFactor;
        this.pdbResNum = pdbResNum;
        this.coords = coords;
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

    public Residue(ArrayList<CartesianCoord> pmoi) {
	this.coords = pmoi;
    }
    
    //accessor methods
    public boolean exists() {
	return exists;
    }

    public double getBFactor() {
    	return bFactor;
    }
    
    public ArrayList<CartesianCoord> getCoords() {
    	return coords;
    }

    public CartesianCoord getCoordsAt(int i) {
	return coords.get(i);
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
    public void setBF(double newBF){
    	bFactor = newBF;
    }
    
    public void setCoords(ArrayList<CartesianCoord> coordsList){
    	coords = coordsList;
    }
    
    public void setResNum(String pdbResNum){
    	this.pdbResNum = pdbResNum;
    }
    
    public void setSSType(String newSsType) {
	ssType = newSsType;
    }
    
    public void setNTerm(boolean newNTerm) {
    	nTerm = newNTerm;
    }
    
    public void setCTerm(boolean newCTerm) {
    	cTerm = newCTerm;
    }
    
    public void setExists(boolean ex) {
    	exists = ex;
    }
    
    public void setPMOI(CartesianCoord pmoi) {
    	this.pmoi = pmoi;
    }
}