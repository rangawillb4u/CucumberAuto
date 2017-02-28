package com.Hive.CucumberAuto;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;

public class Reporter {

	private String strExecSummaryHTMLFilePath;
	private String strTestResHTMLFilePath;
	public String strCurrentApplication;
	public String strCurrentEnvironment;
	public String strOnError;
	private String strRelativePath;
	private String strTestRunResultPath;
	private String strLocalHostName;
	private String strCSSFilePath;

	private String sysFileSeperator = System.getProperty("file.separator");
	private String sysNewline = System.getProperty("line.separator");
	
	public String strCurrentUserID;
	public String strCurrentPassword;
	public String strCurrentURL;
	public String strCurrentBrowser;
	
	public String strCurrentModule;
	public String strCurrentScenarioID;
	public String strCurrentScenarioDesc;
	
	public String strCurrentTestID;
	public String strCurrentTestIterationList;
	public int intCurrentTestIteration = 1;
	public String strCurrentTestDesc;
	private String strTCStatus;
	
	private int intScreenshotCount = 0;
	private int intCurrentIteration = 1;
	private String strCurrentBusFlowKeyword = "";
	private int intStepNumber = 1;
	private int intPassStepCount = 0;
	private int intFailStepCount = 0;
	private int intPassTCCount = 0;
	private int intFailTCCount = 0;
	
	private long intTCStartTime = 0;
	private long intTCEndTime = 0;
	private String strTCDuration = "";
	private long intExecStartTime = 0;
	private long intExecEndTime = 0;
	
	//Constructor
	public Reporter() throws IOException{

		setRelativePath();
		setTestResultFoders();
	}
	
	

		public void initialSettings() throws AWTException, IOException{
			sysFileSeperator = System.getProperty("file.separator");
			sysNewline = System.getProperty("line.separator");
			
			strCurrentApplication = "Banking";
			strCurrentModule = "Gurru99_Banking";
			
			
			

			strCurrentScenarioID = "SC1";
			strCurrentTestID = "TC1";
			
			HTML_TestCase_Initialize();
			
			HTML_TC_Iteration_Initialize(1);
			
			HTML_TC_BusFlowKeyword_Initialize("Logeen");
			
			ReportEvent("Step1", "StepSampleDesc", "PASS");
			ReportEvent("Step1", "StepSampleDesc", "PASS");
			ReportEvent("Step1", "StepSampleDesc", "PASS");
			ReportEvent("Step1", "StepSampleDesc", "PASS");
			ReportEvent("Step1", "StepSampleDesc", "PASS");
			
			HTML_TC_Iteration_Footer();
			
			//
			
			HTML_TC_Iteration_Initialize(2);			
			HTML_TC_BusFlowKeyword_Initialize("Logout");			
			ReportEvent("Step10", "StepSampleDesc22222222222222222", "FAIL");			
			HTML_TC_Iteration_Footer();
			
			
			HTML_TestCase_Footer();			
			HTML_Execution_Summary_TCAddLink();
			
			HTML_Execution_Summary_Footer();
			
			/*
				
			
			GlbVar.strCurrentScenarioID = "SC2";
			GlbVar.strCurrentTestID = "TC2";
			HTMLReporting_Initialize();
			Reporting_TestCase_Initialize();
			Log_Error("Thiss is a error3");
			Log_Error("Thiss is a error4");
			Log_Message("Error Message");
			Reporting_TestCase_Footer();		
			HTML_Execution_Summary_AddLink();
			
			HTML_Execution_Summary_Close();
			
			*/
			
			
		}
		
	

	private void setRelativePath(){
		strRelativePath = System.getProperty("user.dir") + sysFileSeperator;
		
	}
	
		
	private void setTestResultFoders() throws IOException{
		
		File oDirectory = new File(strRelativePath + "04_Results_Tier");
		if (!oDirectory.exists()){
			FileUtils.forceMkdir(oDirectory);
		}
		System.out.println(strRelativePath + "04_Results_Tier");
		//get all the files from a directory
		File[] oFileList = oDirectory.listFiles();
		for (File oFile : oFileList){
			System.out.println(oFile.getName().toUpperCase());
			String strDirectoryname = oFile.getName().toUpperCase().toString();
			if ((strDirectoryname.indexOf("BACKUP")<0) && (strDirectoryname.indexOf(".DS_STORE")<0)){
				File oBackupDirectory = new File(strRelativePath + "04_Results_Tier" + 
							sysFileSeperator + "BackUp" + sysFileSeperator + oFile.getName());
				FileUtils.moveDirectory(oFile, oBackupDirectory);
			}
		}
		
		
		String strRunFolderName = "Run_" + getTimeStamp(true);
		strTestRunResultPath = strRelativePath + "04_Results_Tier" + sysFileSeperator +
				strRunFolderName + sysFileSeperator;
		createDirectory(strTestRunResultPath);
		createDirectory(strTestRunResultPath + "HTML" + sysFileSeperator);
		createDirectory(strTestRunResultPath + "TEXT" + sysFileSeperator);
		createDirectory(strTestRunResultPath + "SCREENSHOTS" + sysFileSeperator);
	
	}
	
	private void createDirectory(String strDirectory){
		File files = new File(strDirectory);
		if (!files.exists()) {
			if (files.mkdirs()) {
				//System.out.println("Directory: " + strDirectory + " is created!");
			} else {
				System.out.println("Directory: " + strDirectory + " is not created!" + sysNewline +
						"Please check the Directory path");
			}
		}

	}
	
	
	public String getTimeStamp(boolean boolForFolderCreation){
		DateFormat dateFormat;
	   if (boolForFolderCreation){
	   dateFormat = new SimpleDateFormat("dd-MMM-yyyy_hh-mm-ss_aa");
	   }else{
		   dateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa");
	   }
	   //get current date time with Date()
	   Date date = new Date();
	   String strTimeStamp = dateFormat.format(date);
	   return strTimeStamp;
	}
	
	public void HTML_Execution_Summary_Initialize(){

		intPassTCCount = 0;
		intFailTCCount = 0;
		strExecSummaryHTMLFilePath = strTestRunResultPath + strCurrentApplication + "-" + strCurrentEnvironment + "_Execution_Summary.HTML";
        try {        	
        		strCSSFilePath = strRelativePath + sysFileSeperator +
        							"Utilities" + sysFileSeperator + "Reporting" + sysFileSeperator + "Style.CSS";
        	//		 				"01_Manager_Tier" + sysFileSeperator + "enviromentFile" + sysFileSeperator + "Style.CSS";
        	
        	FileReader oFileReader = new FileReader(strCSSFilePath); 
        	BufferedReader oBuffReader = new BufferedReader(oFileReader);
        	
            FileWriter fileWriter = new FileWriter(strExecSummaryHTMLFilePath);
            BufferedWriter oBuffWriter = new BufferedWriter(fileWriter);	            
            

            writeWithNewLine(oBuffWriter, "<!DOCTYPE html>");
            writeWithNewLine(oBuffWriter, "<html>");
    		writeWithNewLine(oBuffWriter, "<head>");
    		writeWithNewLine(oBuffWriter, "		 <meta charset='UTF-8'>"); 
    		writeWithNewLine(oBuffWriter, "		 <title>Selenium - Automation Execution Results Summary</title>"); 
            
            String strData;
			while ((strData = oBuffReader.readLine()) != null) {
				writeWithNewLine(oBuffWriter, strData);
				oBuffWriter.newLine();
			}
        	oFileReader.close();
        	
        	writeWithNewLine(oBuffWriter, "</head>"); 
       	 
        	writeWithNewLine(oBuffWriter, "<body>");
        	writeWithNewLine(oBuffWriter, "<table id='header'>"); 
			writeWithNewLine(oBuffWriter, "<colgroup>");
			writeWithNewLine(oBuffWriter, "<col style='width: 25%' />"); 
			writeWithNewLine(oBuffWriter, "<col style='width: 25%' />"); 
			writeWithNewLine(oBuffWriter, "<col style='width: 25%' />"); 
			writeWithNewLine(oBuffWriter, "<col style='width: 25%' />"); 
			writeWithNewLine(oBuffWriter, "</colgroup>"); 
     
			writeWithNewLine(oBuffWriter, "<thead>"); 
			writeWithNewLine(oBuffWriter, "<tr class='heading'>"); 
			writeWithNewLine(oBuffWriter, "<th colspan='4' style='font-family:Copperplate Gothic Bold; font-size:1.4em;'>"); 
			writeWithNewLine(oBuffWriter, "Selenium -  Automation Execution Result Summary"); 
			writeWithNewLine(oBuffWriter, "</th>"); 
			writeWithNewLine(oBuffWriter, "</tr>"); 
			writeWithNewLine(oBuffWriter, "<tr class='subheading'>"); 
			writeWithNewLine(oBuffWriter, "<th>&nbsp;Date&nbsp;&&nbsp;Time</th>"); 
			//writeWithNewLine(oBuffWriter, "<th>&nbsp;:&nbsp;25-Jul-2014&nbsp;05:02:20&nbsp;PM</th>");
			writeWithNewLine(oBuffWriter, "<th>&nbsp;:&nbsp;" + getTimeStamp(false) + "</th>"); 
			intExecStartTime = System.currentTimeMillis();
			writeWithNewLine(oBuffWriter, "<th>&nbsp;OnError</th>"); 
			writeWithNewLine(oBuffWriter, "<th>&nbsp;:&nbsp;" + strOnError + "</th>"); 
			writeWithNewLine(oBuffWriter, "</tr>"); 
			writeWithNewLine(oBuffWriter, "<tr class='subheading'>"); 
			writeWithNewLine(oBuffWriter, "<th>&nbsp;Application</th>"); 
			writeWithNewLine(oBuffWriter, "<th>&nbsp;:&nbsp;" + strCurrentApplication + "</th>"); 
			writeWithNewLine(oBuffWriter, "<th>&nbsp;Environment</th>"); 
			writeWithNewLine(oBuffWriter, "<th>&nbsp;:&nbsp;" + strCurrentEnvironment + "</th>"); 
			writeWithNewLine(oBuffWriter, "</tr>"); 
			writeWithNewLine(oBuffWriter, "</thead>"); 
			writeWithNewLine(oBuffWriter, "</table>"); 
			 
			writeWithNewLine(oBuffWriter, "<table id='main'>"); 
			writeWithNewLine(oBuffWriter, "<colgroup>"); 
			writeWithNewLine(oBuffWriter, "<col style='width: 10%' />"); 
			writeWithNewLine(oBuffWriter, "<col style='width: 10%' />"); 
			writeWithNewLine(oBuffWriter, "<col style='width: 10%' />"); 
			writeWithNewLine(oBuffWriter, "<col style='width: 44%' />"); 
			writeWithNewLine(oBuffWriter, "<col style='width: 16%' />"); 
			writeWithNewLine(oBuffWriter, "<col style='width: 10%' />"); 
			writeWithNewLine(oBuffWriter, "</colgroup>"); 
			 
			writeWithNewLine(oBuffWriter, "<thead>"); 
			writeWithNewLine(oBuffWriter, "<tr class='heading'>"); 
			writeWithNewLine(oBuffWriter, "<th>Module</th>"); 
			writeWithNewLine(oBuffWriter, "<th>Test_Scenario</th>"); 
			writeWithNewLine(oBuffWriter, "<th>Test_Case</th>"); 
			writeWithNewLine(oBuffWriter, "<th>Test_Description</th>"); 
			writeWithNewLine(oBuffWriter, "<th>Execution_Time</th>"); 
			writeWithNewLine(oBuffWriter, "<th>Test_Status</th>"); 
			writeWithNewLine(oBuffWriter, "</tr>"); 
			writeWithNewLine(oBuffWriter, "</thead>");
     
            // Always close files.	
            oBuffWriter.close();
            oBuffWriter = null;
            fileWriter = null;
        }
        catch(IOException ex) {
            System.out.println(
                "Error writing to file '"
                + strExecSummaryHTMLFilePath + "'");
            ex.printStackTrace();
        }
	}
		
	public void HTML_TestCase_Initialize(){
		
		
		strTestResHTMLFilePath = strTestRunResultPath + "HTML" + sysFileSeperator + 
				strCurrentApplication + "-" + strCurrentEnvironment + "-" + strCurrentModule + "_" + strCurrentTestID + ".HTML";

		intPassStepCount = 0;
		intFailStepCount = 0;
		strTCStatus = "PASSED";
		
        try {
        	FileReader oFileReader = new FileReader(strCSSFilePath); 
        	BufferedReader oBuffReader = new BufferedReader(oFileReader);
            FileWriter fileWriter = new FileWriter(strTestResHTMLFilePath);
            BufferedWriter oBuffWriter = new BufferedWriter(fileWriter);
            
            writeWithNewLine(oBuffWriter, "<!DOCTYPE html>");
            writeWithNewLine(oBuffWriter, "<html>");
    		writeWithNewLine(oBuffWriter, "<head>");
    		writeWithNewLine(oBuffWriter, "		 <meta charset='UTF-8'>"); 
    		writeWithNewLine(oBuffWriter, "		 <title>" + strCurrentApplication + " Application - "+
    								strCurrentTestID + " Automation Execution Results</title>"); 
            
            String strData;
			while ((strData = oBuffReader.readLine()) != null) {
				writeWithNewLine(oBuffWriter, strData);
				oBuffWriter.newLine();
			}
			oBuffReader.close();
        	oFileReader.close();
        	
        	writeWithNewLine(oBuffWriter, "</head>"); 
            
        	writeWithNewLine(oBuffWriter, "<body>"); 
			writeWithNewLine(oBuffWriter, "<table id='header'>"); 
			writeWithNewLine(oBuffWriter, "<colgroup>"); 
			writeWithNewLine(oBuffWriter, "<col style='width: 25%' />"); 
			writeWithNewLine(oBuffWriter, "<col style='width: 25%' />"); 
			writeWithNewLine(oBuffWriter, "<col style='width: 25%' />"); 
			writeWithNewLine(oBuffWriter, "<col style='width: 25%' />"); 
			writeWithNewLine(oBuffWriter, "</colgroup>"); 
			
			writeWithNewLine(oBuffWriter, "<thead>"); 
			writeWithNewLine(oBuffWriter, "<tr class='heading'>"); 
			writeWithNewLine(oBuffWriter, "<th colspan='4' style='font-family:Copperplate Gothic Bold; font-size:1.4em;'>"); 
			writeWithNewLine(oBuffWriter, strCurrentApplication + " Application - "+
								strCurrentTestID + " Automation Execution Results");
			writeWithNewLine(oBuffWriter, "</th>"); 
			writeWithNewLine(oBuffWriter, "</tr>"); 
			writeWithNewLine(oBuffWriter, "<tr class='subheading'>"); 
			writeWithNewLine(oBuffWriter, "<th>&nbsp;Date&nbsp;&&nbsp;Time</th>"); 
			writeWithNewLine(oBuffWriter, "<th>&nbsp;:&nbsp;" + getTimeStamp(false) + "</th>"); 
			intTCStartTime = System.currentTimeMillis();
			writeWithNewLine(oBuffWriter, "<th>&nbsp;Iterations</th>"); 
			writeWithNewLine(oBuffWriter, "<th>&nbsp;:&nbsp;" + strCurrentTestIterationList + "</th>"); 
			writeWithNewLine(oBuffWriter, "</tr>"); /*
			writeWithNewLine(oBuffWriter, "<tr class='subheading'>"); 
			writeWithNewLine(oBuffWriter, "<th>&nbsp;Start&nbsp;Iteration</th>"); 
			writeWithNewLine(oBuffWriter, "<th>&nbsp;:&nbsp;1</th>"); 
			writeWithNewLine(oBuffWriter, "<th>&nbsp;End&nbsp;Iteration</th>"); 
			writeWithNewLine(oBuffWriter, "<th>&nbsp;:&nbsp;1</th>"); 
			writeWithNewLine(oBuffWriter, "</tr>"); */
			writeWithNewLine(oBuffWriter, "<tr class='subheading'>"); 
			writeWithNewLine(oBuffWriter, "<th>&nbsp;Browser</th>"); 
			writeWithNewLine(oBuffWriter, "<th>&nbsp;:&nbsp;" + strCurrentBrowser + "</th>"); 
			writeWithNewLine(oBuffWriter, "<th>&nbsp;Executed&nbsp;on</th>"); 
			writeWithNewLine(oBuffWriter, "<th>&nbsp;:&nbsp;" + strLocalHostName + "</th>"); 
			writeWithNewLine(oBuffWriter, "</tr>"); 
			writeWithNewLine(oBuffWriter, "</thead>"); 
			writeWithNewLine(oBuffWriter, "</table>"); 
        
			writeWithNewLine(oBuffWriter, "<table id='main'>"); 
			writeWithNewLine(oBuffWriter, "<colgroup>"); 
			writeWithNewLine(oBuffWriter, "<col style='width: 8%' />"); 
			writeWithNewLine(oBuffWriter, "<col style='width: 12%' />"); 
			writeWithNewLine(oBuffWriter, "<col style='width: 62%' />"); 
			writeWithNewLine(oBuffWriter, "<col style='width: 8%' />"); 
			writeWithNewLine(oBuffWriter, "<col style='width: 10%' />"); 
			writeWithNewLine(oBuffWriter, "</colgroup>"); 
			
			writeWithNewLine(oBuffWriter, "<thead>"); 
			writeWithNewLine(oBuffWriter, "<tr class='heading'>"); 
			writeWithNewLine(oBuffWriter, "<th>Step_No</th>"); 
			writeWithNewLine(oBuffWriter, "<th>Step_Name</th>"); 
			writeWithNewLine(oBuffWriter, "<th>Description</th>"); 
			writeWithNewLine(oBuffWriter, "<th>Status</th>"); 
			writeWithNewLine(oBuffWriter, "<th>Step_Time</th>"); 
			writeWithNewLine(oBuffWriter, "</tr>"); 
			writeWithNewLine(oBuffWriter, "</thead>"); 
    		
            // Always close files.	            
       		oBuffWriter.close();
       		oBuffWriter = null;
            fileWriter = null;
        }
        catch(IOException ex) {
            System.out.println(
                "Error writing to file '"
                + strTestResHTMLFilePath + "'");
            ex.printStackTrace();
        }
	}
	
	public void HTML_TC_Iteration_Initialize(int intIteration){
		intCurrentIteration = intIteration;
        try {
        	
            FileWriter fileWriter = new FileWriter(strTestResHTMLFilePath, true);
            BufferedWriter oBuffWriter = new BufferedWriter(fileWriter);
            
            String strIteration = "Iteration: " + intCurrentIteration;
            writeWithNewLine(oBuffWriter, "<tbody>");
			writeWithNewLine(oBuffWriter, "<tr class='section'>");
			writeWithNewLine(oBuffWriter, "<td colspan='5' onclick=\"toggleMenu('Iteration" + intCurrentIteration + "')\">+ Iteration: " + intCurrentIteration + "</td>"); 
			writeWithNewLine(oBuffWriter, "</tr>"); 
			writeWithNewLine(oBuffWriter, "</tbody>"); 
			writeWithNewLine(oBuffWriter, "<tbody id='Iteration" + intCurrentIteration + "' style='display:table-row-group'>");
    		
            // Always close files.	            
       		oBuffWriter.close();
       		oBuffWriter = null;
            fileWriter = null;
        }
        catch(IOException ex) {
            System.out.println(
                "Error writing to file '"
                + strTestResHTMLFilePath + "'");
            ex.printStackTrace();
        }
	}
	
	public void HTML_TC_Iteration_Footer(){
		
        try {
        	
            FileWriter fileWriter = new FileWriter(strTestResHTMLFilePath, true);
            BufferedWriter oBuffWriter = new BufferedWriter(fileWriter);
            
            writeWithNewLine(oBuffWriter, "</tbody>");
            
            // Always close files.	            
       		oBuffWriter.close();
       		oBuffWriter = null;
            fileWriter = null;
        }
        catch(IOException ex) {
            System.out.println(
                "Error writing to file '"
                + strTestResHTMLFilePath + "'");
            ex.printStackTrace();
        }
	}
		
	public void HTML_TC_BusFlowKeyword_Initialize(String strBusFlowKeyword){
		intStepNumber = 1;
		strCurrentBusFlowKeyword = strBusFlowKeyword;
        try {
        	
            FileWriter fileWriter = new FileWriter(strTestResHTMLFilePath, true);
            BufferedWriter oBuffWriter = new BufferedWriter(fileWriter);
            
			writeWithNewLine(oBuffWriter, "<tr class='subheading subsection'>"); 
			writeWithNewLine(oBuffWriter, "<td colspan='5' onclick=\"toggleSubMenu('Iteration"+ intCurrentIteration + strCurrentBusFlowKeyword + "')\">&nbsp;+ " + strCurrentBusFlowKeyword + "</td>");  
			writeWithNewLine(oBuffWriter, "</tr>");  
    		
            // Always close files.	            
       		oBuffWriter.close();
       		oBuffWriter = null;
            fileWriter = null;
        }
        catch(IOException ex) {
            System.out.println(
                "Error writing to file '"
                + strTestResHTMLFilePath + "'");
            ex.printStackTrace();
        }
	}
	
	public void ReportEvent(String strStepName, String strStepDescription, String strStatus) throws AWTException{
		
        try {
        	strStatus = strStatus.toUpperCase();
            FileWriter fileWriter = new FileWriter(strTestResHTMLFilePath, true);
            BufferedWriter oBuffWriter = new BufferedWriter(fileWriter);
            
            writeWithNewLine(oBuffWriter, "<tr class='content' id='Iteration" + intCurrentIteration + strCurrentBusFlowKeyword + intStepNumber + "'>"); 
			writeWithNewLine(oBuffWriter, "<td>" + intStepNumber + "</td>"); 
			writeWithNewLine(oBuffWriter, "<td class='justified'>" + strStepName + "</td>");  
			writeWithNewLine(oBuffWriter, "<td class='justified'>" + strStepDescription + "</td>");  
			if (((GlbVar.boolScreenshotForPass) && (strStatus == "PASS")) || ((GlbVar.boolScreenshotForFail) && (strStatus == "FAIL"))){				
				String strScreeshotPath = strTestRunResultPath +  "SCREENSHOTS" + sysFileSeperator + strCurrentApplication + strCurrentModule + "-" + 
														strCurrentScenarioID + strCurrentTestID + "-" +  intScreenshotCount + ".png";
				CaptureScreenShot(strScreeshotPath);
				
				writeWithNewLine(oBuffWriter, "<td class='" + strStatus.toLowerCase() + "'><a href='" + strScreeshotPath +
										"'>" + strStatus + "</a></td>");
			}else{
				writeWithNewLine(oBuffWriter, "<td class='" + strStatus.toLowerCase() + "'>" + strStatus + "</td>");				
			} 
			writeWithNewLine(oBuffWriter, "<td><small>" + getTimeStamp(false) + "</small></td>"); 
			writeWithNewLine(oBuffWriter, "</tr>"); 
    		
			
			if (strStatus == "PASS") intPassStepCount++;
			if (strStatus == "FAIL") {
				intFailStepCount++;
				strTCStatus = "FAILED";
			}
			
            // Always close files.	            
       		oBuffWriter.close();
       		oBuffWriter = null;
            fileWriter = null;
            
            intStepNumber++;
        }
        catch(IOException ex) {
            System.out.println(
                "Error writing to file '"
                + strTestResHTMLFilePath + "'");
            ex.printStackTrace();
        }
	}
	
	//Capture Screenshot
	private void CaptureScreenShot(String strScreenshotPath)throws
	    AWTException, IOException {
		// capture the whole screen
		BufferedImage screencapture = new Robot().createScreenCapture(
		    new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()) );
		
		// Save as png
		File file = new File(strScreenshotPath);
		ImageIO.write(screencapture, "png", file);
		
		intScreenshotCount++;
	}
	
	public void HTML_TestCase_Footer() {
		
		try{
			
			FileWriter fileWriter = new FileWriter(strTestResHTMLFilePath, true);
	        BufferedWriter oBuffWriter = new BufferedWriter(fileWriter);
	        
	        
	        
	        writeWithNewLine(oBuffWriter, "</table>"); 
	        
			writeWithNewLine(oBuffWriter, "<table id='footer'>");
			writeWithNewLine(oBuffWriter, "<colgroup>");
			writeWithNewLine(oBuffWriter, "<col style='width: 25%' />");
			writeWithNewLine(oBuffWriter, "<col style='width: 25%' />");
			writeWithNewLine(oBuffWriter, "<col style='width: 25%' />");
			writeWithNewLine(oBuffWriter, "<col style='width: 25%' />");
			writeWithNewLine(oBuffWriter, "</colgroup>");
			 
			writeWithNewLine(oBuffWriter, "<tfoot>");
			writeWithNewLine(oBuffWriter, "<tr class='heading'>");
			intTCEndTime = System.currentTimeMillis();
			strTCDuration = getDuration(intTCStartTime, intTCEndTime) ;
			writeWithNewLine(oBuffWriter, "<th colspan='4'>Execution Duration: " + strTCDuration + "</th>");
			writeWithNewLine(oBuffWriter, "</tr>");
			writeWithNewLine(oBuffWriter, "<tr class='subheading'>");
			writeWithNewLine(oBuffWriter, "<td class='pass'>&nbsp;Steps passed</td>");
			writeWithNewLine(oBuffWriter, "<td class='pass'>&nbsp;: " + intPassStepCount + "</td>");
			writeWithNewLine(oBuffWriter, "<td class='fail'>&nbsp;Steps failed</td>");
			writeWithNewLine(oBuffWriter, "<td class='fail'>&nbsp;: " + intFailStepCount + "</td>");
			writeWithNewLine(oBuffWriter, "</tr>");
			writeWithNewLine(oBuffWriter, "</tfoot>");
			writeWithNewLine(oBuffWriter, "</table>");
			writeWithNewLine(oBuffWriter, "</body>");
			writeWithNewLine(oBuffWriter, "</html>");
			
	        // Always close files.	            
	   		oBuffWriter.close();
	   		oBuffWriter = null;
	        fileWriter = null;
	        
	        intStepNumber++;
	    }
	    catch(IOException ex) {
	        System.out.println(
	            "Error writing to file '"
	            + strTestResHTMLFilePath + "'");
	        ex.printStackTrace();
	    }
		
	}
	
	public void HTML_Execution_Summary_TCAddLink(){
		
        try { 
        	
            FileWriter fileWriter = new FileWriter(strExecSummaryHTMLFilePath, true);
            BufferedWriter oBuffWriter = new BufferedWriter(fileWriter);	            
            
			writeWithNewLine(oBuffWriter, "<tr class='content' >");
			writeWithNewLine(oBuffWriter, "<td class='justified'>" + strCurrentModule + "</td>");
			writeWithNewLine(oBuffWriter, "<td class='justified'>" + strCurrentScenarioID + "</td>");
			writeWithNewLine(oBuffWriter, "<td class='justified'><a href='" + strTestResHTMLFilePath + "' target='about_blank'>" + strCurrentTestID + "</a></td>");
			writeWithNewLine(oBuffWriter, "<td class='justified'>" + strCurrentTestDesc + "</td>");
			writeWithNewLine(oBuffWriter, "<td>" + strTCDuration + "</td>");			
			writeWithNewLine(oBuffWriter, "<td class='" + strTCStatus.toLowerCase().substring(0, 4) + "'>" + strTCStatus + "</td>");
			writeWithNewLine(oBuffWriter, "</tr>");
     

			if (strTCStatus == "PASSED") intPassTCCount++;
			if (strTCStatus == "FAILED") intFailTCCount++;
			
            // Always close files.	
            oBuffWriter.close();
            oBuffWriter = null;
            fileWriter = null;
        }
        catch(IOException ex) {
            System.out.println(
                "Error writing to file '"
                + strExecSummaryHTMLFilePath + "'");
            ex.printStackTrace();
        }
	}
	
	public void HTML_Execution_Summary_Footer(){
		
        try { 
        	
            FileWriter fileWriter = new FileWriter(strExecSummaryHTMLFilePath, true);
            BufferedWriter oBuffWriter = new BufferedWriter(fileWriter);	            
            
						
			writeWithNewLine(oBuffWriter, "</tbody>");
			writeWithNewLine(oBuffWriter, "</table>");
			 
			writeWithNewLine(oBuffWriter, "<table id='footer'>");
			writeWithNewLine(oBuffWriter, "<colgroup>");
			writeWithNewLine(oBuffWriter, "<col style='width: 25%' />");
			writeWithNewLine(oBuffWriter, "<col style='width: 25%' />");
			writeWithNewLine(oBuffWriter, "<col style='width: 25%' />");
			writeWithNewLine(oBuffWriter, "<col style='width: 25%' />");
			writeWithNewLine(oBuffWriter, "</colgroup>");
			 
			writeWithNewLine(oBuffWriter, "<tfoot>");
			writeWithNewLine(oBuffWriter, "<tr class='heading'>");

			intExecEndTime = System.currentTimeMillis();
			writeWithNewLine(oBuffWriter, "<th colspan='4'>Total Duration: " + getDuration(intExecStartTime, intExecEndTime) + "</th>");
			writeWithNewLine(oBuffWriter, "</tr>");
			writeWithNewLine(oBuffWriter, "<tr class='subheading'>");
			writeWithNewLine(oBuffWriter, "<td class='pass'>&nbsp;Tests passed</td>");
			writeWithNewLine(oBuffWriter, "<td class='pass'>&nbsp;: " + intPassTCCount + "</td>");
			writeWithNewLine(oBuffWriter, "<td class='fail'>&nbsp;Tests failed</td>");
			writeWithNewLine(oBuffWriter, "<td class='fail'>&nbsp;: " + intFailTCCount + "</td>");
			writeWithNewLine(oBuffWriter, "</tr>");
			writeWithNewLine(oBuffWriter, "</tfoot>");
			writeWithNewLine(oBuffWriter, "</table>");
			writeWithNewLine(oBuffWriter, "</body>");
			writeWithNewLine(oBuffWriter, "</html>");
     
            // Always close files.	
            oBuffWriter.close();
            oBuffWriter = null;
            fileWriter = null;
        }
        catch(IOException ex) {
            System.out.println(
                "Error writing to file '"
                + strExecSummaryHTMLFilePath + "'");
            ex.printStackTrace();
        }
	}

	private void writeWithNewLine(BufferedWriter oBuffWriter, String strData) throws IOException{
		oBuffWriter.write(strData);
		oBuffWriter.newLine();
	}
	
	private String getDuration(long lngStartTime, long lngEndTime){
		String strDuration = "";
		int lngDiff = (int)(lngEndTime - lngStartTime)/1000;
		int intHour = lngDiff/(60*60);
		int intMin = (lngDiff % (60*60))/60;
		int intSec = (lngDiff % (60*60)) % 60;
		
		if (intHour > 0) {
			strDuration = intHour + " hour(s), " + intMin + " minute(s), " + intSec + " seconds";
		}else{
			strDuration = intMin + " minute(s), " + intSec + " seconds";
		}
		
		return strDuration;
	}
}
