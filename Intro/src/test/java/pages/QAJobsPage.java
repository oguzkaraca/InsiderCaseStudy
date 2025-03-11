package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public class QAJobsPage {
    private WebDriver driver;

    @FindBy(linkText = "See all QA jobs")
    private WebElement seeAllQAJobsLink;

    @FindBy(css = "#select2-filter-by-location-container")
    private WebElement locationFilter;

    @FindBy(css = "#select2-filter-by-department-container")
    private WebElement departmentFilter;

    @FindBy(xpath = "//div[@id='jobs-list']/div")
    private List<WebElement> jobsList;

    public QAJobsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Navigate to the QA Jobs page
    public void navigateToQAJobsPage() throws InterruptedException {
        driver.get("https://useinsider.com/careers/quality-assurance/");
        Thread.sleep(2000); // 2-second delay after opening QA Jobs page
        System.out.println("Step 7: Navigated to QA Jobs page");
    }

    // Click the "See all QA jobs" button
    public void clickSeeAllQAJobs() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(seeAllQAJobsLink)).click();
        Thread.sleep(30000); // 30-second delay after clicking "See all QA jobs"
        System.out.println("Step 7.1: Clicked 'See all QA jobs'");
    }

    // Filter jobs by location and department
    public void filterJobsByLocationAndDepartment(String location, String department) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Open the location filter dropdown
        System.out.println("Opening location filter dropdown...");
        locationFilter.click();
        Thread.sleep(1000); // Wait for the dropdown to open

        // Select the location (Istanbul, Turkey)
        System.out.println("Selecting location: " + location);
        WebElement locationOption = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("/html[1]/body[1]/span[1]/span[1]/span[2]/ul[1]/li[2]")
        ));
        locationOption.click();
        Thread.sleep(5000); // Wait for the location to be selected

        // Verify the selected location
        String selectedLocation = locationFilter.getText();
        Assert.assertTrue(selectedLocation.contains("Istanbul, Turkiye"), "Selected location is incorrect: " + selectedLocation);
        System.out.println("Step 7.2: Selected Location: " + selectedLocation);

        // Verify the selected department (Quality Assurance)
        String selectedDepartment = departmentFilter.getText();
        Assert.assertTrue(selectedDepartment.contains(department), "Selected department is incorrect: " + selectedDepartment);
        System.out.println("Step 7.3: Selected Department: " + selectedDepartment);
    }

    // Verify job details for all visible job listings
    public void verifyJobDetails() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Actions actions = new Actions(driver);

        // Scroll to "Browse Open Positions" text
        WebElement browseOpenPositionsText = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("h3[class='mb-0']")
        ));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", browseOpenPositionsText);
        Thread.sleep(2000); // 2-second delay after scrolling
        System.out.println("Scrolled to 'Browse Open Positions' text");

        // Wait for the filtered jobs to load
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@id='jobs-list']/div")));

        // Get all visible job listings
        List<WebElement> jobListings = driver.findElements(By.xpath("//div[@id='jobs-list']/div"));

        // Verify each job listing
        for (int i = 0; i < jobListings.size(); i++) {
            try {
                // Re-locate the job listings to avoid StaleElementReferenceException
                jobListings = driver.findElements(By.xpath("//div[@id='jobs-list']/div"));
                WebElement job = jobListings.get(i);

                // Scroll to the job listing (especially for job 4)
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", job);
                Thread.sleep(1000); // 1-second delay after scrolling

                // Hover over the job listing
                actions.moveToElement(job).perform();
                Thread.sleep(1000); // 1-second delay after hovering
                System.out.println("Hovered over job " + (i + 1));

                // Get job title, department, and location using corrected XPath
                String jobTitle = job.findElement(By.xpath(".//p[@class='position-title font-weight-bold']")).getText();
                String department = job.findElement(By.xpath(".//span[@class='position-department text-large font-weight-600 text-primary']")).getText();
                String location = job.findElement(By.xpath(".//div[@class='position-location text-large']")).getText();

                // Log job title
                System.out.println("Job " + (i + 1) + " Title contains 'Quality Assurance': " + (jobTitle.contains("Quality Assurance") ? "Yes" : "No"));

                // Log department
                System.out.println("Job " + (i + 1) + " Department contains 'Quality Assurance': " + (department.contains("Quality Assurance") ? "Yes" : "No"));

                // Log location
                System.out.println("Job " + (i + 1) + " Location contains 'Istanbul, Turkiye': " + (location.contains("Istanbul, Turkiye") ? "Yes" : "No"));

                // Assertions for department and location
                Assert.assertTrue(department.contains("Quality Assurance"), "Department does not contain 'Quality Assurance': " + department);
                Assert.assertTrue(location.contains("Istanbul, Turkiye"), "Location does not contain 'Istanbul, Turkiye': " + location);

                System.out.println("Verified job listing " + (i + 1) + ":");
                System.out.println("  - Job Title: " + jobTitle);
                System.out.println("  - Department: " + department);
                System.out.println("  - Location: " + location);

                // Step 5: Click the "View Role" button and verify redirection
                clickAndVerifyViewRoleButton(job, i + 1);
            } catch (org.openqa.selenium.StaleElementReferenceException e) {
                System.out.println("StaleElementReferenceException encountered. Retrying for job " + (i + 1));
                i--; // Retry the same index
            }
        }

        System.out.println("Step 7.4: Verified job details for all job listings");
    }

    // Click the "View Role" button and verify redirection to Lever Application form
    private void clickAndVerifyViewRoleButton(WebElement job, int jobNumber) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Locate the "View Role" button within the job listing
        WebElement viewRoleButton = job.findElement(By.xpath(".//a[contains(text(), 'View Role')]"));
        System.out.println("Clicking 'View Role' button for job " + jobNumber + ": " + job.findElement(By.xpath(".//p[@class='position-title font-weight-bold']")).getText());

        // Get the current window handle (main tab)
        String mainWindowHandle = driver.getWindowHandle();

        // Click the "View Role" button
        viewRoleButton.click();
        Thread.sleep(3000); // Wait for the new tab to open

        // Get all window handles (tabs)
        Set<String> windowHandles = driver.getWindowHandles();

        // Switch to the new tab
        for (String handle : windowHandles) {
            if (!handle.equals(mainWindowHandle)) {
                driver.switchTo().window(handle);
                break;
            }
        }

        // Verify that the new tab's URL contains the Lever domain
        String leverDomain = "jobs.lever.co";
        Assert.assertTrue(driver.getCurrentUrl().contains(leverDomain), "Redirection to Lever Application form failed. Current URL: " + driver.getCurrentUrl());
        System.out.println("Step 5: Successfully redirected to Lever Application form for job " + jobNumber + ": " + driver.getCurrentUrl());

        // Close the new tab and switch back to the main tab
        driver.close();
        driver.switchTo().window(mainWindowHandle);
    }
}