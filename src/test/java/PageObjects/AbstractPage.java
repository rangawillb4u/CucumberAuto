package PageObjects;
import org.openqa.selenium.WebDriver;

import com.Hive.CucumberAuto.AbstractStep;


public class AbstractPage extends AbstractStep {

	protected WebDriver driver = getWebDriver();
	
	public AbstractPage(WebDriver driver){
		this.driver = driver;
	}
}
