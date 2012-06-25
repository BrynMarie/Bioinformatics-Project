import java.io.*;
import java.util.*;

public class SecondaryStructure {

    String sstype;
    boolean prevConnected, nextConnected;
    int length, resNumBegin, resNumEnd;
    CartesianCoord coords = new CartesianCoords();
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

    public int getBegin() {
    	return resNumBegin;
    } 	
    
    public int getEnd() {
    	return resNumEnd;
    }
    
    public CartersianCoord getCoords() {
    	return coords;
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
    
    public void setBegin(int beginning){
    	resNumBegin = beginning;
    }
    
    public void setEnd(int ending){
    	resNumEnd = ending;
    }
    
    public void setCoords(CartesianCoord newCoord) {
    	coords = newCoord;
    }
    
    public void setCoords(int x, int y, int z) {
    	coords = new CartesianCoord(x,y,z);
    }

    //Other
}