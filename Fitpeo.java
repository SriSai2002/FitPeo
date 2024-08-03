package basics;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Fitpeo {
	public static void main(String[] args) {
		WebDriver driver = new ChromeDriver();

		try {
			// Navigate to FitPeo Homepage
			driver.manage().window().maximize();
			driver.get("https://www.fitpeo.com");

			// Set an implicit wait time
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			
			//Setting an Explicit Wait for Revenue Calculator
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // 10 seconds timeout

			// Wait until the element with text "Revenue Calculator" is displayed
			WebElement revenueCalculator = wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='Revenue Calculator']")));

			// Navigate to the Revenue Calculator Page
			WebElement revenueCalculatorLink = driver.findElement(By.xpath("//div[text()='Revenue Calculator']"));
			revenueCalculatorLink.click();

			// Create an instance of Actions class
			Actions actions = new Actions(driver);

			// Move to the Slider Element
			WebElement slider = driver.findElement(By.xpath("//span[text()='Patients should be between 0 to 2000']"));
			actions.moveToElement(slider).perform();

			Thread.sleep(5000);
			
			//Validating the SliderValue through While loop
			WebElement sliderValue = driver.findElement(By.xpath("//span[contains(@class,'MuiSlider-thumb')]//input"));
			while (true) {
				String value = sliderValue.getAttribute("aria-valuenow");
				if (value.equals("850")) {
					break;
				}
				actions.clickAndHold(sliderValue).moveByOffset(8, 0) // Adjust the offset values as needed
						.release().perform();
			}
			
			// Update the text field
			WebElement textField = driver.findElement(By.xpath("//input[contains(@class,'MuiInputBase-input')]"));
			String textFieldValue = textField.getAttribute("value");
			Thread.sleep(3000);
			driver.findElement(By.xpath("//div[contains(@class,\"MuiInputBase-root\")]")).click();
			Thread.sleep(2000);
			actions.click(textField)
		       .keyDown(Keys.CONTROL)
		       .sendKeys("a")
		       .keyUp(Keys.CONTROL)
		       .perform();
			textField.sendKeys("560");
			String value = sliderValue.getAttribute("aria-valuenow");
			if (value.equals("560")) {
				WebElement cptCode = driver
						.findElement(By.xpath("//p[text()='CPT-99091']/..//input[@type='checkbox']"));
				actions.moveToElement(cptCode);
				cptCode.click();
				driver.findElement(By.xpath("//p[text()='CPT-99453']/..//input[@type='checkbox']")).click();
				String recurringReimbursement = driver
						.findElement(
								By.xpath("(//p[text()='Total Recurring Reimbursement for all Patients Per Month']/..//p[2])"))
						.getText();
				if(recurringReimbursement.equals("$31920")){
					System.out.println("Passed");
				}
				}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			driver.quit();
		}
	}
}
