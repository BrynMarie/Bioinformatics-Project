import java.util.*;

public class Atom {

    CartesianCoord coords;
    String atomType;
    int bFactor;
    boolean isBackbone;
    
    public Atom(String atom, boolean isBB, double x, double y, double z) {
	atomType = atom;
	coords = new CartesianCoord(x,y,z);
	isBackbone = isBB;
    }

    public Atom(String atom, boolean isBB) {
	atomType = atom;
	isBackbone = isBB;
    }

    public Atom() {
	atomType = "";
	coords = new CartesianCoord();
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

    //mutator methods
    public void setAtomType(String newAtomType) {
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
}