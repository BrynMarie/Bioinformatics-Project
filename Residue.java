import java.io.*;
import java.util.*;

public class Residue {
    
    double bFactor;
    CartesianCoord coords;
    String pdbResNum;
    // other state

    //make as many constructors as needed
    public Residue(double bFactor, String pdbResNum, double x, double y, double z) {
        this.bFactor = bFactor;
        this.coords = new CartesianCoord(x, y, z);
        this.pdbResNum = pdbResNum;
    }
    
    public Residue(double bFactor, String pdbResNum, CartesianCoord coords) {
        this.bFactor = bFactor;
        this.pdbResNum = pdbResNum;
        this.coords = coords;
    }

    //accessor methods
    

    //mutator methods

}