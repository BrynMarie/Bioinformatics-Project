import java.util.*;

public class CartesianCoord {

    public Integer x,y,z;

    public CartesianCoord() {
	x = null;
	y = null;
	z = null;
    }

    public CartesianCoord(int newX, int newY, int newZ) {
	x = newX;
	y = newY;
	z = newZ;
    }

    //accessor methods
    public int getX() {
	return x;
    }

    public int getY() {
	return y;
    }

    public int getZ() {
	return z;
    }

    public boolean coordsHaveBeenSet() {
	if(x == null || y == null || z == null) {
	    return false;
	}
	return true;
    }

    //mutator methods
    public void setX(int newX) {
	x = newX;
    }

    public void setY(int newY) {
	y = newY;
    }
    
    public void setZ(int newZ) {
	z = newZ;
    }
}