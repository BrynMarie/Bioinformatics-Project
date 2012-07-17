import java.util.*;
import java.io.*;

public class CalcGeo {
	
	public static double distance;
	public static double deltaAngle;
	CartesianCoord p0, p1, p2, p3, e1, l, e2;

    public CalcGeo(ArrayList<Residue> residueList) {
		calculate(residueList);
    }
    
    public void calculate(ArrayList<Residue> residueList) {
	   	
		for (int i = 0; i<residueList.size(); ++i) {
		    p0 = (residueList.get(i).getCoords()); 
		    p1 = (residueList.get(i+1).getCoords());
		    p2 = (residueList.get(i+2).getCoords());
		    p3 = (residueList.get(i+3).getCoords());
		    
		    //calculate e1
		    e1 = calcE1LE2(p0, p1);
		    //calculate l
		    l = calcE1LE2(p2, p1);
		    //calculate e2
		    e2 = calcE1LE2(p3, p2);
		    
		    //calculate distance, d
		    this.distance = calcDistance(p2, p1);
		    //calc delta angle
		    this.deltaAngle = calcAngles(e1, l);
		}
    }
    
    public double calcAngles(CartesianCoord first, CartesianCoord second){
	double dotProduct = calc3DDotProduct(first, second);
	return Math.acos(dotProduct);
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