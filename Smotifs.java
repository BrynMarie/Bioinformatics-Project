import java.io.*;
import java.util.*;

/**

A data structure class for storing Smotifs and their geometries

 */
public class Smotifs {
    double hoist, meridian, packing;
    ABEnum ab;
    
    public Smotifs(double hoist, double meridian, double packing, int des){
	this.hoist = hoist;
	this.meridian = meridian;
	this.packing = packing;
	this.ab = new ABEnum(des);
    }

    public Smotifs(int des) {
	hoist = null;
	meridian = null;
	packing = null;
	ab = new ABEnum(des);
    }

    //accessor methods
    public double getHoist() {
	return hoist;
    }

    public double getMeridian() {
	return meridian;
    }

    public double getPacking() {
	return packing;
    }

    public int getAB() {
	return ab.designator();
    }

    //mutator methods
    public void setHoist(double newHoist){
	hoist = newHoist;
    }

    public void setMeridian(double newMeridian){
	meridian = newMeridian;
    }

    public void setPacking(double newPacking) {
	packing = newPacking;
    }

    public void setAB(int newDes) {
	this.ab = new ABEnum(newDes);
    }
}