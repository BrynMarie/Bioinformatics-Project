import java.util.*;

public class Atom {

   // add setCTerm method
   //change constructors
   
	CartesianCoord coords;
    String atomType;
    int resNum;
    int bFactor;
    boolean isBackbone, nTerm, cTerm;
    
    public Atom(String atom, int resNum, boolean isBB, CartesianCoord cc) {
		atomType = atom;
		coords = cc;
		isBackbone = isBB;
    }

    public Atom(String atom, int resNum, boolean isBB) {
		atomType = atom;
		isBackbone = isBB;
    }

	//constructor template
    public Atom() {
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
    
    public int getResNum() {
    	return resNum;
    }

    //mutator methods
    public void setAtomType(String AtomType) {
		atomType = newAtomType;
    }

    public void setCoords(double x, double y, double z) {
		coords = new CartesianCoord(x, y, z);
    }

    public void setCoords(CartesianCoord newCoord) {
		coords = newCoord;
    }
    
    public void setBackbone(boolean isBB){
    	isBackbone = isBB;
    }
    
    public void setResNum(int newResNum){
    	resNum = newResNum;
    }
}