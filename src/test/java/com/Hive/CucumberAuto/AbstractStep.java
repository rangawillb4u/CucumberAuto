package com.Hive.CucumberAuto;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;


public class AbstractStep {
	
	protected static Reporter reporter;
	protected static WebDriver driver;
	
	public Reporter getReporter(){
		if (reporter == null){
			try {
				reporter = new Reporter();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return reporter;
	}
	
	public WebDriver getWebDriver(){
		if (driver == null){

			ProfilesIni profile = new ProfilesIni();
			 
			FirefoxProfile myprofile = profile.getProfile("default");
			 
			driver = new FirefoxDriver(myprofile);
			//System.setProperty("webdriver.chrome.driver", "/Users/ranganathan.veluswamy/Downloads/chromedriver");
			//driver = new ChromeDriver();
		}
		return driver;
	}
	
	
}
