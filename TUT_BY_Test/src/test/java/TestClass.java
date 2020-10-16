import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestClass {
    WebDriver driver;
    AfishaTutByOnlineCinema afishaTutByOnlineCinema;


    @BeforeMethod
    public void SetUp(){
        System.setProperty("webdriver.gecko.driver", "/home/boris/IdeaProjects/TestNG1/10/TUT_BY_Test/driver/geckodriver");
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get("https://afisha.tut.by/online-cinema/");
        afishaTutByOnlineCinema = new AfishaTutByOnlineCinema(driver);
    }
    @Test
    public void TestGenreFilm() throws InterruptedException {
        Boolean result = afishaTutByOnlineCinema.isGenreFilmsCoincidenceWithGenreEachFilm();
        Assert.assertTrue(result);
    }
    @Test
    public void TestGenreCartoon() throws InterruptedException {
        Boolean result = afishaTutByOnlineCinema.isGenreCartoonsCoincidenceWithGenreEachAnimation();
        Assert.assertTrue(result);
    }

    @Test
    public void TestGenreSerials() throws InterruptedException {
        Boolean result = afishaTutByOnlineCinema.isGenreSerialsCoincidenceWithGenreEachSerial();
        Assert.assertTrue(result);
    }
    @AfterMethod
    public void TearDown(){
        driver.quit();
    }

}
