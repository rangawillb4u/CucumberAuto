package PageObjects;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;


public class AdoptionPage extends AbstractPage{

	public AdoptionPage(WebDriver driver) {
		super(driver);
	}
	
	public AdoptionResultPage checkAnimal(String strAnimalName){
		Select startDate = new Select(driver.findElement(By.id("start_select")));
		startDate.selectByVisibleText("Today");
		driver.findElement(By.id("check_btn_02")).click();
		return new AdoptionResultPage(driver);		
	}

}
