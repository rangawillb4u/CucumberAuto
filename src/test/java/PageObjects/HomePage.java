package PageObjects;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class HomePage extends AbstractPage {

	public HomePage(WebDriver driver) {
		super(driver);
	}
	
	public HomePage navigateHomePage(){
		driver.navigate().to("http://thetestroom.com/webapp/index.html");
		driver.manage().window().maximize();
		return new HomePage(driver);
	}
	
	public AdoptionPage navigateAdoptionPage(){
		driver.findElement(By.id("adoption_link")).click();		
		return new AdoptionPage(driver);
	}

}
