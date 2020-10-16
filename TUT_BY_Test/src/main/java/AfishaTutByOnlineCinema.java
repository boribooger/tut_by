import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.*;

public class AfishaTutByOnlineCinema {


    WebDriver driver;


    //Next 3 lines include variables with xpath for "Select": animation, serial, film.
    private By filmGenreAnimation =
            By.xpath("//*[@id=\"tab-cartoons\"]/div[1]/div/div/div[1]/div/div/div/ul/li[2]/a");
    private By filmGenreSerial =
            By.xpath("//*[@id=\"tab-tv-series\"]/div[1]/div/div/div[1]/div/div/div/ul/li[2]/a");
    private By filmGenreFilm =
            By.xpath("//*[@id=\"tab-films\"]/div[1]/div/div/div[1]/div/div/div/ul/li[2]/a");

    /*
    Selenium "Select" works incorrectly for this site and I choose genre by xpath.
    Next 3 lines include variables with xpath for "Select-Button" animation, serial, film.
     */
    private By genreSelectAnamationButtonXpath =
            By.xpath("//*[@id=\"tab-cartoons\"]/div[1]/div/div/div[1]/div/div/button");
    private By genreSelectASerialButtonXpath =
            By.xpath("//*[@id=\"tab-tv-series\"]/div[1]/div/div/div[1]/div/div/button");
    private By genreSelectFilmButtonXpath =
            By.xpath("//*[@id=\"tab-films\"]/div[1]/div/div/div[1]/div/div/button");


    //Next 3 lines include variables with xpath for category animation, serial, film.
    private By film = By.xpath("//li[1]/a[@href=\"https://afisha.tut.by/online-cinema/\"]");
    private By animation = By.xpath("//li[3]/a[@href=\"https://afisha.tut.by/online-cinema/animation/\"]");
    private By serial = By.xpath("//li[2]/a[@href=\"https://afisha.tut.by/online-cinema/serial/\"]");


    public AfishaTutByOnlineCinema(WebDriver driver) {
        this.driver = driver;
    }

    /*
    The function selects the category "movie", genre "Боевик",
    and then checks that all displayed movies are in the genre "Боевик".
    */
    public Boolean isGenreFilmsCoincidenceWithGenreEachFilm() throws InterruptedException {
        Boolean result;
        chooseGenreThrillerFromDropDown(film, genreSelectFilmButtonXpath, filmGenreFilm);
        result = checkingGenreIsCorrect();
        return result;
    }

    /*
    The function selects the category "animation", genre "Боевик",
    and then checks that all displayed animations are in the genre "Боевик".
    */
    public Boolean isGenreCartoonsCoincidenceWithGenreEachAnimation() throws InterruptedException {
        Boolean result = null;
        chooseGenreThrillerFromDropDown(animation, genreSelectAnamationButtonXpath, filmGenreAnimation);
        result = checkingGenreIsCorrect();
        return result;
    }

    /*
    The function selects the category "serial", genre "Боевик",
    and then checks that all displayed serials are in the genre "Боевик".
    */
    public Boolean isGenreSerialsCoincidenceWithGenreEachSerial() throws InterruptedException {
        Boolean result = null;
        chooseGenreThrillerFromDropDown(serial, genreSelectASerialButtonXpath, filmGenreSerial);
        result = checkingGenreIsCorrect();
        return result;
    }


    /*
    Function chooses genre category animation/serial/film, clicks select-button and click genre.
    I could do 3 short functions, but I had a goal of fewer strings in this code.
    */
    private void chooseGenreThrillerFromDropDown(By category, By button, By filmGenre)
            throws InterruptedException {
        ArrayDeque<WebElement> clickWebElement = new ArrayDeque<WebElement>();

        WebElement chooseCategory = driver.findElement(category);
        WebElement selectButton = driver.findElement(button);
        WebElement chooseTypeOfGenre = driver.findElement(filmGenre);

        clickWebElement.addFirst(chooseCategory);
        clickWebElement.add(selectButton);
        clickWebElement.addLast(chooseTypeOfGenre);

        for(WebElement el: clickWebElement){
            Thread.sleep(500);
            el.click();
        }
    }

    /*
    Function opens each page in pagination, collect data about genre from each film/animation/serial
    saves it in HashSet true when the item has "Боевик" and false if not.
    Then function checks the HashSet has "false" or not.
    If HashSet has "false" test is false.
     */
    private Boolean checkingGenreIsCorrect() throws InterruptedException {
        Set<Boolean> allWebElementsIsTriller = new HashSet<Boolean>();
        WebElement pageSource;
        WebElement nextPage;
        Boolean pageSourseHasNextPage;
        Boolean result = false;

        do {
            List<WebElement> webElementsFromCuttentPage =
                    driver.findElements(By.xpath("//*[@id=\"online-cinema\"]/div[1]/ul/li/div"));
            for(WebElement webEl : webElementsFromCuttentPage){
                allWebElementsIsTriller.add(webEl.getText().contains("Боевик"));

                //This line for "False" test
/*
                allWebElementsIsTriller.add(webEl.getText().contains("sdfsdf"));
*/
            }
            pageSource = driver.findElement(By.xpath("//*[@id=\"online-cinema\"]"));
            pageSourseHasNextPage = pageSource.getText().contains("Следующая страница");
            if(pageSourseHasNextPage){
                nextPage = driver.findElement(By.xpath("//*[@id=\"online-cinema\"]/div[2]/ul/li[2]/a"));
                nextPage.click();
            }
        }
        while (pageSourseHasNextPage == true);

        Iterator<Boolean> iterator = allWebElementsIsTriller.iterator();

        if (allWebElementsIsTriller.size() != 1 || iterator.next() == false){
            result = false;
            return result;
        }
        else {
            result = true;
            return true;
        }

    }
}