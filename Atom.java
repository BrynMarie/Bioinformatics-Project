import java.util.*;

public class Atom {

    CartesianCoord coords;
    String atomType;
    int bFactor;
    
    public Atom(String atom, double x, double y, double z) {
	atomType = atom;
	coords = new CartesianCoord(x,y,z);
    }

    public Atom(String atom) {
	atomType = atom;
	coords = new CartesianCoord();
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
}