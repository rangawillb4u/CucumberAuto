package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AdoptionResultPage extends AdoptionPage {

	public AdoptionResultPage(WebDriver driver) {
		super(driver);
	}
	
	public AdoptionResultPage enterAdoptionDetails(){
		
		driver.findElement(By.name("name_field")).sendKeys("TestName");
		driver.findElement(By.name("address_field")).sendKeys("TestAddress");
		driver.findElement(By.name("postcode_field")).sendKeys("TestPostcode");
		driver.findElement(By.name("email_field")).sendKeys("TestEmail@Test.com");
		return new AdoptionResultPage(driver);
	}


	public AdoptionResultPage submitForm(){
		driver.findElement(By.id("submit_adoption")).click();
		return new AdoptionResultPage(driver);
	}
}
