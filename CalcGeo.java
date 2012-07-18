/**
 * 
 * 
 * You should probably make an array of distances/delta/theta/rho angles and the like, 
 * that way we can have one 'geometries' file for each secondary structure. Each of these does correspond 
 * to a secondary structure so hopefully that should work out. I would also like to see stored the 
 * first and last residue pdbResNum in the data structure of the geometry.
 * 
 * I'll see about putting together the geometries data structure if you'll see how it works out...
 * */


import java.util.*;
import java.io.*;

public class CalcGeo {
	
	//these should be arrays
	public static double distance;
	public static double deltaAngle;
	CartesianCoord p0, p1, p2, p3, e1, l, e2;
	// also need theta and rho angles, then we have all of the geometries
	public static double thetaAngle;
	public static double rhoAngle;

    public CalcGeo(ArrayList<Residue> residueList) {
		calculate(residueList);
    }
    
    public void calculate(ArrayList<Residue> residueList) {
	   	double magnitudeOfX = 0;
		for (int i = 0; i<residueList.size(); ++i) {
		    p0 = (residueList.get(i).getCoords()); 
		    p1 = (residueList.get(i+1).getCoords());
		    p2 = (residueList.get(i+2).getCoords());
		    p3 = (residueList.get(i+3).getCoords());
		    
		    //calculate e1, vector
		    e1 = calcE1LE2(p0, p1);
		    //calculate l, vector
		    l = calcE1LE2(p2, p1);
		    //calculate e2, vector
		    e2 = calcE1LE2(p3, p2);
		    
		    //calculate distance, d, scalar
		    this.distance = calcDistance(p2, p1);
		    //calc delta angle
		    this.deltaAngle = calcAngles(e1, l);
		    //calc theta angle
		    this.thetaAngle = calcAngles(e1, e2);
		    //calc n, this is a vector
            	    CartesianCoord crossProd = calc3By3CrossProd(l, e1);
	      	    double lengthOfCrossProd = calcMagntidue(l)*calcMagnitude(e1)*Math.sin(thetaAngle);
	    	    CartesianCoord n = new CartesianCoord(crossProd.getX()/lengthOfCrossProd,
		    crossProd.getY()/lengthOfCrossProd, crossProd.getZ()/lengthOfCrossProd);
	    	    //calc x, this is a vector
	    	    double e1e2DotProd = calc3DDotProduct(e1, e2);
	    	    CartesianCoord secondTermOfX = new CartesianCoord(e1.getX()*e1e2DotProd,
		    e2.getY()*e1e2DotProd, e3.getZ()*e1e2DotProd);
	            CartesianCoord x = new CartesianCoord(e2.getX()-e1.getX(),
		    e2.getY()-e1.getY(), e2.getZ()-e1.getZ());
	    	    //calc rho angle, has two cases
            	    double firstCaseRho = dotProd(x, crossProd(e1, n));
	    	    if(firstCaseRho >= 0) {
			magnitudeOfX = calcMagnitude(x);
			rho = Math.acos(dotProd(x/magnitudeOfX, n));
	     	     }
	   	    else{
			magnitudeOfX = calcMagnitude(x);
			rho = (2*Math.pi)-Math.acos(dotProd(x/magnitudeOfX, n));
	     	    }
		}
    }
    
    public double calcAngles(CartesianCoord first, CartesianCoord second){
	double dotProduct = calc3DDotProduct(first, second);
	return Math.acos(dotProduct);
    }
    
    public double calcMagnitude(CartesianCoord a){
	double a1 = a.getX();
	double a2 = a.getY();
	double a3 = a.getZ();
	sqrtA1 = Math.pow(a1, 2);
	sqrtA2 = Math.pow(a2, 2);
	sqrtA3 = Math.pow(a3, 2);
	double magnitude = Math.sqrt(sqrtA1+sqrtA2+sqrtA3);
	return magnitude;
    }
    
    public CartesianCoord calcCrossProd(CartesianCoord first, CartesianCoord second){
    	double a1 = first.getX();
    	double a2 = first.getY();
    	double a3 = first.getZ();
    	double b1 = first.getX();
    	double b2 = first.getY();
    	double b3 = first.getZ();
    	double firstTerm=(a2*b3)-(a3*b2);
    	double secondTerm = (a3*b1)-(a1*b3);
   	double thirdTerm = (a1*b2)-(a2*b1);
 	CartesianCoord crossProd = new CartesianCoord(firstTerm, secondTerm, thirdTerm);
   	return crossProd;
    }
    
    public double calc3DDotProduct(CartesianCoord first, CartesianCoord second){
	double firstTerm = first.getX()*second.getX();
	double secondTerm = first.getY()*second.getY();
	double thirdTerm = first.getZ()*second.getZ();
	return firstTerm+secondTerm+thirdTerm;
    }

    public double calcDistance(CartesianCoord p2, CartesianCoord p1) {
	double differenceX, differenceY, differenceZ, sqDiffX, sqDiffY, sqDiffZ;
	differenceX = p2.getX()-p1.getX();
	differenceY = p2.getY()-p1.getY();
	differenceZ = p2.getZ()-p1.getZ();
	sqDiffX = Math.pow(differenceX, 2);
	sqDiffY = Math.pow(differenceY, 2);
	sqDiffZ = Math.pow(differenceZ, 2);
	return Math.sqrt(sqDiffX+sqDiffY+sqDiffZ);
    }

    public CartesianCoord calcE1LE2(CartesianCoord first, CartesianCoord second){
	double differenceX, differenceY, differenceZ, denominator;
	differenceX = second.getX()-first.getX();
	differenceY = second.getY()-first.getY();
	differenceZ = second.getZ()-first.getZ();
	denominator = calcDistance(first, second);
	return new CartesianCoord((differenceX/denominator), (differenceY/denominator), (differenceZ/denominator));
    }
}    