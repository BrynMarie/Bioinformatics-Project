/**
 * Programmer: Bryn Reinstadler
 * Date: July 25th 2012
 * Filename: Smotifs.java
 * 
 * Purpose: A custom object to store information about each
 * smotif.
 * Also needs information on dssp/pdb, start res and end res.
 * */

import java.io.*;
import java.util.*;

public class Smotifs {

    // (delta) the 'hoist'  which is the angle between L and M1
    // (theta) the 'packing' which is the angle between M1 and M2
    // (rho) the 'meridian' which is the angle between M2 and upside-down L

    String bin;
    double hoist, meridian, packing, distance;
    int designator;

    public Smotifs(String bin, double distance, double hoist, double packing, double meridian, int des){
	this.bin = bin;
	this.distance = distance;
	this.hoist = hoist;
	this.meridian = meridian;
	this.packing = packing;
	this.designator = des;
    }

    public Smotifs(int des) {
	this.designator = des;
    }

    public String toString() {
	return "" + bin + " " + distance + " " + hoist + " " + packing + " " + meridian;
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
	return designator;
    }

    public String getBin() {
	return bin;
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
	designator = newDes; 
    }

    public void setBin(String newBin) {
	bin = newBin;
    }
}