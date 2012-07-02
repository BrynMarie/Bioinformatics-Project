import java.io.*;
import java.util.*;

public class Residue {
    
    double bFactor;
    CartesianCoord coords;
    String pdbResNum;
    String ssType;
    // other state

    //make as many constructors as needed
    public Residue(double bFactor, String pdbResNum, int x, int y, int z) {
        this.bFactor = bFactor;
        this.coords = new CartesianCoord(x, y, z);
        this.pdbResNum = pdbResNum;
    }
    
    public Residue(double bFactor, String pdbResNum, CartesianCoord coords) {
        this.bFactor = bFactor;
        this.pdbResNum = pdbResNum;
        this.coords = coords;
    }

    public Residue() {
    }
    //accessor methods
    

    //mutator methods
    public void setSSType(String newSsType) {
	ssType = newSsType;
    }
}