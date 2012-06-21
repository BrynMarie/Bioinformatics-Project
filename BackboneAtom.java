import java.util.*;

public class BackboneAtom {

    CartesianCoord coords;
    String atomType;
    int bFactor;
    
    public BackboneAtom(String atom, int x, int y, int z) {
	atomType = atom;
	coords = new CartesianCoord(x,y,z);
    }

    public BackboneAtom(String atom) {
	atomType = atom;
	coords = new CartesianCoord();
    }

    public BackboneAtom() {
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

    public int getX() {
	return coords.x;
    }

    public int getY() {
	return coords.y;
    }

    public int getZ() {
	return coords.z;
    }

    //mutator methods
    public void setAtomType(String newAtomType) {
	atomType = newAtomType;
    }

    public void setCoords(int x, int y, int z) {
	coords = new CartesianCoord(x, y, z);
    }

    public void setCoords(CartesianCoord newCoord) {
	coords = newCoord;
    }

    public void setX(int newX) {
	coords.setX(newX);
    }

    public void setY(int newY) {
	coords.setY(newY);
    }

    public void setZ(int newZ) {
	coords.setZ(newZ);
    }
}