package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    private String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    public void positiveCase() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        String meetingDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(meetingDate);
        $("[data-test-id='name'] input").setValue("Римский-Корсаков Николай");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='notification']").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(Condition.exactText("Встреча успешно забронирована на " + meetingDate));
    }

    @Test
    public void wrongCity() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Печорск");
        String meetingDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(meetingDate);
        $("[data-test-id='name'] input").setValue("Римский-Корсаков Николай");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='city'] .input__sub").shouldHave(Condition.exactText("Доставка в выбранный город недоступна"));

    }

    @Test
    public void emptyCityField() {
        open("http://localhost:9999");
        String meetingDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(meetingDate);
        $("[data-test-id='name'] input").setValue("Римский-Корсаков Николай");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='city'] .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));

    }

    @Test
    public void dateTooEarly() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        String meetingDate = generateDate(2, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(meetingDate);
        $("[data-test-id='name'] input").setValue("Римский-Корсаков Николай");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='date'] .input__sub").shouldHave(Condition.exactText("Заказ на выбранную дату невозможен"));

    }

    @Test
    public void noAgreementFlag() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        String meetingDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(meetingDate);
        $("[data-test-id='name'] input").setValue("Римский-Корсаков Николай");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $(".button").click();
        $("[data-test-id='agreement'].input_invalid").shouldBe(Condition.visible);

    }

    @Test
    public void phoneNoPlus() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        String meetingDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(meetingDate);
        $("[data-test-id='name'] input").setValue("Римский-Корсаков Николай");
        $("[data-test-id='phone'] input").setValue("89012345678");
        $(".button").click();
        $("[data-test-id='phone'] .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));

    }

    @Test
    public void phoneTenNumbers() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        String meetingDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(meetingDate);
        $("[data-test-id='name'] input").setValue("Римский-Корсаков Николай");
        $("[data-test-id='phone'] input").setValue("+7901234567");
        $(".button").click();
        $("[data-test-id='phone'] .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));

    }

    @Test
    public void phoneTwelveNumbers() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        String meetingDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(meetingDate);
        $("[data-test-id='name'] input").setValue("Римский-Корсаков Николай");
        $("[data-test-id='phone'] input").setValue("+790123456789");
        $(".button").click();
        $("[data-test-id='phone'] .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));

    }

    @Test
    public void phoneLetters() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        String meetingDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(meetingDate);
        $("[data-test-id='name'] input").setValue("Римский-Корсаков Николай");
        $("[data-test-id='phone'] input").setValue("+7901234567B");
        $(".button").click();
        $("[data-test-id='phone'] .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));

    }

    @Test
    public void nameInEnglish() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        String meetingDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(meetingDate);
        $("[data-test-id='name'] input").setValue("Pavel");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $(".button").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));

    }

    @Test
    public void nameWithDot() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        String meetingDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(meetingDate);
        $("[data-test-id='name'] input").setValue("Павел.");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $(".button").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));

    }

    @Test
    public void nameWithComma() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        String meetingDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(meetingDate);
        $("[data-test-id='name'] input").setValue("Павел,");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $(".button").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));

    }

    @Test
    public void nameWithExclamation() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        String meetingDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(meetingDate);
        $("[data-test-id='name'] input").setValue("Павел!");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $(".button").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));

    }

    @Test
    public void nameWithQuestion() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        String meetingDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(meetingDate);
        $("[data-test-id='name'] input").setValue("Павел?");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $(".button").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));

    }

    @Test
    public void nameWithPlus() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        String meetingDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(meetingDate);
        $("[data-test-id='name'] input").setValue("Павел+");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $(".button").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));

    }

    @Test
    public void nameWithEqual() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        String meetingDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(meetingDate);
        $("[data-test-id='name'] input").setValue("Павел=");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $(".button").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));

    }

    @Test
    public void nameWithRoundBracket() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        String meetingDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(meetingDate);
        $("[data-test-id='name'] input").setValue("Павел)");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $(".button").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));

    }

    @Test
    public void nameWithBracket() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        String meetingDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(meetingDate);
        $("[data-test-id='name'] input").setValue("Павел]");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $(".button").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));

    }

    @Test
    public void nameWithAsterisk() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        String meetingDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(meetingDate);
        $("[data-test-id='name'] input").setValue("Павел*");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $(".button").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));

    }

    @Test
    public void nameWithDollarSign() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        String meetingDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(meetingDate);
        $("[data-test-id='name'] input").setValue("Павел$");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $(".button").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));

    }

    @Test
    public void nameWithAndSign() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        String meetingDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(meetingDate);
        $("[data-test-id='name'] input").setValue("Павел&");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $(".button").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));

    }

    @Test
    public void nameWithPercent() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        String meetingDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(meetingDate);
        $("[data-test-id='name'] input").setValue("Павел%");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $(".button").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));

    }

    @Test
    public void nameWithSharpSign() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        String meetingDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(meetingDate);
        $("[data-test-id='name'] input").setValue("Павел#");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $(".button").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));

    }

    @Test
    public void nameWithAtSign() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        String meetingDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(meetingDate);
        $("[data-test-id='name'] input").setValue("Павел@");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $(".button").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));

    }

    @Test
    public void nameWithApostrophe() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        String meetingDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(meetingDate);
        $("[data-test-id='name'] input").setValue("Павел`");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $(".button").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));

    }

    @Test
    public void nameWithTilde() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        String meetingDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(meetingDate);
        $("[data-test-id='name'] input").setValue("Павел~");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $(".button").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));

    }

    @Test
    public void nameWithUnderscore() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        String meetingDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(meetingDate);
        $("[data-test-id='name'] input").setValue("Павел_");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $(".button").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));

    }

    @Test
    public void nameWithArrow() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        String meetingDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(meetingDate);
        $("[data-test-id='name'] input").setValue("Павел>");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $(".button").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));

    }

    @Test
    public void nameWithSlash() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        String meetingDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(meetingDate);
        $("[data-test-id='name'] input").setValue("Павел/");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $(".button").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));

    }

    @Test
    public void nameWithFigureBracket() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        String meetingDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(meetingDate);
        $("[data-test-id='name'] input").setValue("Павел}");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $(".button").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));

    }

    @Test
    public void nameWithQuotation() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        String meetingDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(meetingDate);
        $("[data-test-id='name'] input").setValue("\"Павел\"");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $(".button").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));

    }

    @Test
    public void popupCityPick() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("ка");
        $$(".popup .menu-item").findBy(Condition.exactText("Курган")).click();
        String meetingDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(meetingDate);
        $("[data-test-id='name'] input").setValue("Римский-Корсаков Николай");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='notification']").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(Condition.exactText("Встреча успешно забронирована на " + meetingDate));
    }

}
