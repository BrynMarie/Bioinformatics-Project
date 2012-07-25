import java.util.*;

public class Atom {
    //change constructors
   
    CartesianCoord coords;
    String atomType, resNum;
    double bFactor;
    boolean isBackbone, nTerm, cTerm;
    
    public Atom(String atomType, String resNum, boolean isBackbone, boolean nTerm, boolean cTerm, double bFactor, 
    	CartesianCoord coords) {
	this.resNum = resNum;
	this.nTerm = nTerm;
	this.cTerm = cTerm;
	this.bFactor = bFactor;
	this.atomType = atomType;
	this.coords = cc;
	this.isBackbone = isBackbone;
    }

    public Atom(String atomType, String resNum, boolean isBackbone) {
	this.atomType = atomType;
	this.isBackbone = isBackbone;
	this.resNum = resNum;
    }

    /* A toString method that returns the atom type, the res num, and the coordinates */
    public String toString() {	
	return "" + atomType + " " + resNum + " " + coords.toString();
    }

    //acessor methods
    public String getAtomType() {
	return atomType;
    }

    public CartesianCoord getCoords() {
	return coords;
    }
    
    public boolean isBackboneAtom() {
    	return isBackbone;
    }
    
    public String getResNum() {
    	return resNum;
    }
    
    public double getBFactor() {
	return bFactor;
    }
    
    public boolean getCTerm() {
	return cTerm;
    }
	
    public boolean getNTerm() {
	return nTerm;
    }

    public double getX() { return coords.x; }
    public double getY() { return coords.y; }
    public double getZ() { return coords.z; }


    //mutator methods
    public void setAtomType(String AtomType) {
	atomType = AtomType;
    }

    public void setCoords(CartesianCoord newCoord) {
	coords = newCoord;
    }
    
    public void setBackbone(boolean isBB){
    	isBackbone = isBB;
    }
    
    public void setResNum(String newResNum){
    	resNum = newResNum;
    }
    
    public void setBFactor(double BFactor) {
	bFactor = BFactor;
    }
    
    public void setCTerm(boolean CTerm){
	cTerm = CTerm;
    }
	
    public void setNTerm(boolean NTerm){
	nTerm = NTerm;
    }
}