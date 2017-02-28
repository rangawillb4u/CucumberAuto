package com.Hive.CucumberAuto;



import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Steps_Test extends AbstractStep{
	
	Reporter reporter= getReporter();
	
	@Given("^First given step$")
	public void first_given_step() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    System.out.println("Printing First given step executed" );
	    reporter.HTML_TC_BusFlowKeyword_Initialize("Given");
	    reporter.ReportEvent("Step1", "First given step executed", "PASS");
	}
	

	@When("^First when step$")
	public void first_when_step() throws Throwable {
	    System.out.println("Printing First when step executed");
	    reporter.HTML_TC_BusFlowKeyword_Initialize("When");
	    reporter.ReportEvent("Step1", "First When step executed", "PASS");
	}

	@Then("^First then step$")
	public void first_then_step() throws Throwable {
	    System.out.println("Printing First then step executed");
	    reporter.HTML_TC_BusFlowKeyword_Initialize("THen");
	    reporter.ReportEvent("Step1", "First THen step executed", "PASS");
	}
}
