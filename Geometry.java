import java.io.*;
import java.util.*;

public class Geometry {

    public static String distance;
    public static String delta;
    public static String theta;
    public static String rho;
    public static String stRes;
    public static String endRes;

    public Geometry (String stRes, String endRes,
		     String distance, String delta, String theta, String rho) {
	this.distance = distance;
	this.delta = delta;
	this.theta = theta;
	this.rho = rho;
	this.stRes = stRes;
	this.endRes = endRes;
    }

    //accessor methods 
    public String getD() { return distance; }
    public String getDelta() { return delta; }
    public String getTheta() { return theta; }
    public String getRho() { return rho; }
    public String getStart() { return stRes; }
    public String getEnd() { return endRes; }
}