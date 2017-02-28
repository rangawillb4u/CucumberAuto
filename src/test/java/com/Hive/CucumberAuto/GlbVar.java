package com.Hive.CucumberAuto;
/*
 * Declare some common parameters for scripts
 * You can change them to adapt your environment
 *
 */

public class GlbVar {
	
	public static boolean  boolScreenshotForPass = true;
	public static boolean  boolScreenshotForFail = true;
	public static boolean  strCurrentTestStatus;
	public static int intScreenShotCount = 0;
	
	public static String strExpLogHeader = "===================================================================================================";
	
	
	/* You can change the Path of FireFox based on your environment here */
	public static final String FIREFOX_PATH = "C:\\Documents and Settings\\veluswar\\Local Settings\\Application Data\\Mozilla Firefox\\firefox.exe";
	
    
	// Time to wait when searching for a GUI element 
	public static final int WAIT_TIME = 30; 
	

}
