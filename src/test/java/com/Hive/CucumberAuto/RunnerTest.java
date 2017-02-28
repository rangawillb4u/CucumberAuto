package com.Hive.CucumberAuto;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;


@RunWith(Cucumber.class)
@CucumberOptions(
		format = {"pretty", "html:target/html", "json:target/cucumber.json"},
		features = {"src/test/resource/ZooAdoption.feature"}
		)
public class RunnerTest {
	
}
