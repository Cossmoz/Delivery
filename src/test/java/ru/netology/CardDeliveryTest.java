package ru.netology;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {
    String testDay(int day) {
        return LocalDate.now().plusDays(day).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    void testPositiveAllFieldsWithValidData() {
        open("http://localhost:9999/");
        SelenideElement form = $("[action='/']");
        form.$("[data-test-id='city'] input").setValue("Самара");
        form.$("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id='date'] input").setValue(testDay(3));
        form.$("[data-test-id='name'] input").setValue("Константин");
        form.$("[data-test-id='phone'] input").setValue("+79270000000");
        form.$("[data-test-id='agreement']").click();
        form.$(".button__content").click();
        $("[data-test-id='notification']")
                .waitUntil(visible, 15000).shouldHave(text("Встреча успешно забронирована"));


    }

    @Test
    void testNegativeCityFieldEmpty(){
        open("http://localhost:9999/");
        SelenideElement form = $("[action='/']");
        form.$("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id='date'] input").setValue(testDay(3));
        form.$("[data-test-id='name'] input").setValue("Константин");
        form.$("[data-test-id='phone'] input").setValue("+79270000000");
        form.$("[data-test-id='agreement']").click();
        form.$(".button__content").click();
        form.$("[data-test-id='city'].input_invalid .input__sub").shouldBe(visible)
                .shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void testNegativeDateFieldEmpty(){
        open("http://localhost:9999/");
        SelenideElement form = $("[action='/']");
        form.$("[data-test-id='city'] input").setValue("Самара");
        form.$("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id='name'] input").setValue("Константин");
        form.$("[data-test-id='phone'] input").setValue("+79270000000");
        form.$("[data-test-id='agreement']").click();
        form.$(".button__content").click();
        form.$("[data-test-id='date'] .input__sub").shouldBe(visible).shouldHave(text("Неверно введена дата"));
    }

    @Test
    void testNegativeDateLess3day(){
        open("http://localhost:9999/");
        SelenideElement form = $("[action='/']");
        form.$("[data-test-id='city'] input").setValue("Самара");
        form.$("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id='date'] input").setValue(testDay(2));
        form.$("[data-test-id='name'] input").setValue("Константин");
        form.$("[data-test-id='phone'] input").setValue("+79270000000");
        form.$("[data-test-id='agreement']").click();
        form.$(".button__content").click();
        form.$("[data-test-id='date'] .input__sub").shouldBe(visible)
                .shouldHave(text("Заказ на выбранную дату невозможен"));
    }
    @Test
    void testNegativeNameFieldEmpty(){
        open("http://localhost:9999/");
        SelenideElement form = $("[action='/']");
        form.$("[data-test-id='city'] input").setValue("Самара");
        form.$("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id='date'] input").setValue(testDay(3));
        form.$("[data-test-id='phone'] input").setValue("+79270000000");
        form.$("[data-test-id='agreement']").click();
        form.$(".button__content").click();
        form.$("[data-test-id='name'] .input__sub").shouldBe(visible)
                .shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void testNegativePhoneFieldEmpty(){
        open("http://localhost:9999/");
        SelenideElement form = $("[action='/']");
        form.$("[data-test-id='city'] input").setValue("Самара");
        form.$("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id='date'] input").setValue(testDay(7));
        form.$("[data-test-id='name'] input").setValue("Константин");
        form.$("[data-test-id='agreement']").click();
        form.$(".button__content").click();
        form.$("[data-test-id='phone'] .input__sub").shouldBe(visible)
                .shouldHave(text("Поле обязательно для заполнения"));
    }
    @Test
    void testNegativeAgreementFieldEmpty(){
        open("http://localhost:9999/");
        SelenideElement form = $("[action='/']");
        form.$("[data-test-id='city'] input").setValue("Самара");
        form.$("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id='date'] input").setValue(testDay(3));
        form.$("[data-test-id='name'] input").setValue("Константин");
        form.$("[data-test-id='phone'] input").setValue("+79270000000");
        form.$(".button__content").click();
        form.$("[data-test-id='agreement'].input_invalid").shouldBe(visible)
                .shouldHave(text("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }
}

