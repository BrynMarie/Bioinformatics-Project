import java.io.*;
import java.util.*;

public class SecondaryStructure {

    String sstype;
    boolean prevConnected, nextConnected;
    int length;
    ArrayList<Residue> resArray = new ArrayList<Residue>();
    CartesianCoord coords = new CartesianCoord();
    //also need principal moment of inertia, not sure which datatype is best for that -- Jennifer?

    public SecondaryStructure() {
	sstype = "";
	prevConnected = false;
	nextConnected = false;
	length = 0;
    }

    //Accessor fields
    public String getSSType() {
	return sstype;
    }

    public boolean getPrevCnx() {
	return prevConnected;
    }

    public boolean getNextCnx() {
	return nextConnected;
    }

    public int length() {
	return length;
    }
    
    public CartesianCoord getCoords() {
    	return coords;
    }
    
    public Residue firstResidue() {
    	return resArray.get(0);
    }
    
    public Residue lastResidue() {
    	return resArray.get(resArray.size() - 1);
    }

    //Mutator fields
    public void setSSType(String arg) {
	if(arg.toString().equals("H") || arg.toString().equals("S") || arg.toString().equals("T")) {
	    sstype = arg;
	}
	else {
	    sstype = ""; //throw an exception
	}
    }

    public void setPrevCnx(boolean state) {
	prevConnected = state;
    }
    
    public void setNextCnx(boolean state) {
	nextConnected = state;
    }

    public void setLength(int newLength) {
	length = newLength;
    }
    
    public void setCoords(CartesianCoord newCoord) {
    	coords = newCoord;
    }
    
    public void setCoords(double x, double y, double z) {
    	coords = new CartesianCoord(x,y,z);
    }
    
    public void addResidue(Residue res){
    	resArray.add(res);
    }

    //Other
}