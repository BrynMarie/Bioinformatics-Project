import java.io.*;
import java.util.*;

public class SecondaryStructure {

    String sstype;
    boolean prevConnected, nextConnected;
    int length;

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

    //Other
}