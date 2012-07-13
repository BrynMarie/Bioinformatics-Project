import java.io.*;
import java.util.*;

public class SecondaryStructure {

    String sstype;
    int length;
    ArrayList<Residue> resArray = new ArrayList<Residue>();
    CartesianCoord coords = new CartesianCoord(); //aka pmoi
    boolean exists

    public SecondaryStructure(String ss, int length, ArrayList<Residue> resArray, CartesianCoords coords) {
	sstype = ss;
	this.length = length;
	this.resArray = resArray;
	this.coords = coords;
    }
    
    public SecondaryStructure(String ss, int length, ArrayList<Residue> resArray) {
    	sstype = ss;
    	this.length = length;
    	this.resArray = resArray;
    }
    
    public SecondaryStructure(String ss, boolean exists) {
    	sstype = ss;
    	this.exists = exists;
    }

    //Accessor fields
    public String getSSType() {
	return sstype;
    }

    public boolean getExists() {
	return exists;
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
    
    public void setCoords(CartesianCoord newCoord) {
    	coords = newCoord;
    }
    
    public void setCoords(double x, double y, double z) {
    	coords = new CartesianCoord(x,y,z);
    }
    
    public void addResidue(Residue res){
    	resArray.add(res);
    }
    
    public void addResArray(ArrayList<Residue> resArray) {
    	this.resArray = resArray;
    }

    //Other
}