package ru.netology.web;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.*;


class CallbackTest {
    @BeforeEach
    void setup() {
        Configuration.holdBrowserOpen = true;
        Calendar c = new GregorianCalendar();
        c.add(Calendar.DAY_OF_YEAR, 3); // увеличиваем на 3 дня от текущей даты
        SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy"); //придаем нужный формат дате
        String str = format1.format(c.getTime());//c.getTime().toString();//вытягиваем измененную дату в нужном формате и присваиваем переменной
        open("http://localhost:9999");
        $("[data-test-id=date] input").sendKeys(str);
    }
    @AfterEach
    void end() {
        $("[data-test-id=notification]").should(appear, Duration.ofMillis(15000));
    }
    @Test
    void shouldTestSuccessfulFormSubmission() {
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79251112233");
        $("[data-test-id=agreement] span").click();
        $x("//*[@class='button__content']").click();
    }
    @Test
    void shouldTestIfDoubleNameAndSurname() {
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=name] input").setValue("Иванов-Петров Иван-Нариман");
        $("[data-test-id=phone] input").setValue("+79251112233");
        $("[data-test-id=agreement] span").click();
        $x("//*[@class='button__content']").click();
    }
    @Test
    void shouldTestIfShortNameAndSurname() {
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=name] input").setValue("Чи Ян");
        $("[data-test-id=phone] input").setValue("+79251112233");
        $("[data-test-id=agreement] span").click();
        $x("//*[@class='button__content']").click();
    }
    @Test
    void shouldTestIfPhoneNumberBeginWithEight() {
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+89251112233"); // код +8 принадлежит восточной Азии и специальным службам, тест должен падать, но проходит
        $("[data-test-id=agreement] span").click();
        $x("//*[@class='button__content']").click();
    }
}

