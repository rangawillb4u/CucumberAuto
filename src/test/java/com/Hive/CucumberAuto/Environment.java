package com.Hive.CucumberAuto;


import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
public class Environment extends AbstractStep{
	Reporter reporter = getReporter();
	
	private static boolean dunit = false;
	private static boolean eunit = false;
	private static String strPreviousScenario = "";

    @Before
    public void beforeAll() {
        if(!dunit) {
            Runtime.getRuntime().addShutdownHook(new Thread());
            // do the beforeAll stuff...
            System.out.println("FFFFFFFFFFFIIIIIIIIIIIIIRRRRRRSSSSTT");
            dunit = true;

            reporter.strCurrentApplication = "HIVE";
            reporter.strCurrentModule = "Hive_Automation";
            reporter.HTML_Execution_Summary_Initialize();
            //reporter.strCurrentEnvironment = "DummyYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY";
        }
    }
	
	
	@Before
	public void scenarioInitialize(Scenario scenario){
		String strCurrentScenario = scenario.getName();
		String strScenarioID = strCurrentScenario;
		if (strCurrentScenario.indexOf("_")>=0){
			strScenarioID = strCurrentScenario.split("_")[0];
		}
		if (strPreviousScenario != ""){        
	        if (strPreviousScenario != strCurrentScenario){
	        		reporter.HTML_TestCase_Footer();
	            reporter.HTML_Execution_Summary_TCAddLink();
	            strPreviousScenario = strCurrentScenario;
	            System.out.println("Scenario Ending");
	        }
		}
	    else{
	        strPreviousScenario = strCurrentScenario;
	    }
		System.out.println("Printing BeforeScenario NAME : " + scenario.getName());
		System.out.println("Printing BeforeScenario ID : " +scenario.getId());
		String[] arrScenDetails = scenario.getId().split(";");
		if (arrScenDetails.length ==4){
			int intIteration = Integer.parseInt(arrScenDetails[3]) - 1;
			System.out.println("Iteration : " + intIteration);
			if (intIteration==1){
				System.out.println("initialize scenario");
				reporter.strCurrentScenarioID = "SC1";
				reporter.strCurrentTestID = strScenarioID;
				reporter.strCurrentTestDesc = strCurrentScenario;						
				reporter.HTML_TestCase_Initialize();
				reporter.HTML_TC_Iteration_Initialize(1);
			}else{
				reporter.HTML_TC_Iteration_Initialize(intIteration);
			}
			
		}	
		else{
			System.out.println("initialize scenario");
			reporter.strCurrentScenarioID = "SC1";
			reporter.strCurrentTestID = strScenarioID;
			reporter.strCurrentTestDesc = strCurrentScenario;						
			reporter.HTML_TestCase_Initialize();
			reporter.HTML_TC_Iteration_Initialize(1);
			
		}
		
		
	}
	
	@After
	public void tearDown(Scenario scenario){
		//driver.quit();
	    reporter.HTML_TC_Iteration_Footer();
		System.out.println("Printing After Scenario : " + scenario.getName());
	}
	
	@After
	public void attachShutDownHook(){
    		if(!eunit){
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                reporter.HTML_TestCase_Footer();
                reporter.HTML_Execution_Summary_TCAddLink();
                reporter.HTML_Execution_Summary_Footer();
	            System.out.println("Scenario Ending");	
            		System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAALLLLLLLLLLLLLLLLLLLLLLLLLL");
            		
            }
        });
        eunit = true;
    		}
    }
	
}
