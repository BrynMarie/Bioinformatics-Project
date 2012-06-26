import java.io.*;
import java.util.*;

/**

A data structure class for storing Smotifs and their geometries

 */
public class Smotifs {
    Integer hoist, meridian, packing;
    ABEnum ab;
    
    public Smotifs(int hoist, int meridian, int packing, int des){
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
    public int getHoist() {
	return hoist;
    }

    public int getMeridian() {
	return meridian;
    }

    public int getPacking() {
	return packing;
    }

    public int getAB() {
	return ab.designator();
    }

    //mutator methods
    public void setHoist(int newHoist){
	hoist = newHoist;
    }

    public void setMeridian(int newMeridian){
	meridian = newMeridian;
    }

    public void setPacking(int newPacking) {
	packing = newPacking;
    }

    public void setAB(int newDes) {
	this.ab = new ABEnum(newDes);
    }
}