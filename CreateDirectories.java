import java.io.*;

public class CreateDirectories {

    public CreateDirectories() {
	try {
	    String binning = "Bins";
	    boolean success = (new File(binning)).mkdir();
	    if(success) {
		System.out.println("Folder 'bins' created");
	    }

	    for(int ab = 0; ab<4; ++ab) { // alpha/beta designation
		for(int d=0; d<10; ++d) { // distance
		    for (int delta=0; delta<3; ++delta) {
			for (int theta=0; theta<3; ++theta) {
			    for(int rho=0; rho<6; ++rho) {
				String dirStr = "Bins/" + ab + "/" + d + "/" + 
				    delta + "/" + theta + "/" + rho + "";
				success = (new File(dirStr)).mkdirs();
			    }
			}
		    }
		}
	    }

	    if(success) {
		System.out.println("Folders successfully completed.");
	    }

	}
	catch (Exception e) {
	    System.err.println("Error: " + e.getMessage());
	}
    }
}