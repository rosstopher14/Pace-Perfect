/************************************************************************************/
//	Name:		Run Tracker	
//	Date:		July 27, 2023
//	Author:		CSC 229 Group Info Tech 2
//	Purpose:	This system allows users to track their run times and distances,
//				and compare against expert designed preset fitness programs. 
/*************************************************************************************/

package runTrackerv3;


import java.io.IOException;
import java.sql.SQLException;


public class PacePerfectMain {

	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
	
		PacePerfect test1 = new PacePerfect();
		test1.connectDB();
		test1.loadData();
		test1.login();
		
	}
	

}


