package com.Hive.CucumberAuto;

import org.openqa.selenium.WebDriver;

import PageObjects.AdoptionPage;
import PageObjects.AdoptionResultPage;
import PageObjects.HomePage;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Steps_ZooAdopt extends AbstractStep {
	WebDriver driver = getWebDriver();
	HomePage homePage;
	AdoptionPage adoptionPage;
	AdoptionResultPage adoptionResultPage;
	
	@Given("^Im in the ZOO Adoption homepage$")
	public void im_in_the_ZOO_Adoption_homepage() throws Throwable {
		reporter.HTML_TC_BusFlowKeyword_Initialize("Launch Zoo Adoption App");
		homePage = new HomePage(driver);
		homePage.navigateHomePage();
		reporter.ReportEvent("Launch ZooApp", "Zoo Adoption Application is Launched successfully", "PASS");
	}

	@When("^I navigate to the Adoption page$")
	public void i_navigate_to_the_Adoption_page() throws Throwable {
		reporter.HTML_TC_BusFlowKeyword_Initialize("Navigate Adoption Page");
		homePage.navigateAdoptionPage();
		adoptionPage = homePage.navigateAdoptionPage();
		reporter.ReportEvent("Navigate Adoption Page", "Navigated to Adoption Page successfully", "PASS");
	}

	@And("^Check Availability of \"(.*?)\"$")
	public void check_Availability_of_Turtle(String Animal) throws Throwable {
		reporter.HTML_TC_BusFlowKeyword_Initialize("Check Avilability");
		adoptionResultPage = adoptionPage.checkAnimal(Animal);
		reporter.ReportEvent("Check " + Animal + "Availability", "The Availability of the " + Animal + " is checked successfully", "PASS");
	}

	@Then("^Enter the Details$")
	public void enter_the_Details() throws Throwable {
		reporter.HTML_TC_BusFlowKeyword_Initialize("Enter Adoption Details");
		adoptionResultPage.enterAdoptionDetails();
		reporter.ReportEvent("Enter Adoption Details", "Adoption details are entered successfully", "PASS");
	}

	@And("^Submit the Form$")
	public void submit_the_Form() throws Throwable {
		reporter.HTML_TC_BusFlowKeyword_Initialize("Submit Form");
		adoptionResultPage.submitForm();
		reporter.ReportEvent("Submit Form", "The Adoption form is submitted successfully", "PASS");
	}
}
