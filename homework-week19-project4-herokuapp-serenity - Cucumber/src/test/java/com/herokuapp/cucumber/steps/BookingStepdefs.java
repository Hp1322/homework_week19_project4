package com.herokuapp.cucumber.steps;

import com.herokuapp.herokuappinfo.HerokuAppSteps;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.ValidatableResponse;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

public class BookingStepdefs {

    static String firstname = "Jim";
    static String lastName = "Brown";
    static int totalPrice = 110;
    static Boolean depositpaid = true;
    static String additionalneeds = "Breakfast";
    static int bookingID;
    static ValidatableResponse response;

    @Steps
    HerokuAppSteps herokuAppSteps;

    @Given("^I am on homepage of application of booking$")
    public void iAmOnHomepageOfApplicationOfBooking() {
    }

    @When("^I send Get request to list endpoint of booking$")
    public void iSendGetRequestToListEndpointOfBooking() {
        response = herokuAppSteps.getAllBooking();
    }

    @Then("^I must get back a valid status code (\\d+) of booking$")
    public void iMustGetBackAValidStatusCodeOfBooking(int code) {
        response.statusCode(code);
        response.assertThat().statusCode(code);
    }

    @When("^I send Post request to list endpoint of booking$")
    public void iSendPostRequestToListEndpointOfBooking() {
        HashMap<Object, Object> dates = new HashMap<>();
        dates.put("checkin", "2018-01-01");
        dates.put("checkout", "2019-01-01");

        response = herokuAppSteps.createBooking(firstname,lastName,totalPrice,depositpaid,dates,additionalneeds);
        response.log().all().statusCode(200);
        bookingID = response.log().all().extract().path("bookingid");
        System.out.println(bookingID);
    }

    @When("^I send Put request to list endpoint of booking$")
    public void iSendPutRequestToListEndpointOfBooking() {
        HashMap<Object, Object> dates = new HashMap<>();
        dates.put("checkin", "2018-01-01");
        dates.put("checkout", "2019-01-01");

        firstname = "James";
        depositpaid = false;

        response = herokuAppSteps.updateBooking(bookingID,firstname,lastName,totalPrice,depositpaid,dates,additionalneeds).log().all().statusCode(200);
        HashMap<String, Object> bookingMap = herokuAppSteps.getBookingMapInfoByID(bookingID);
        Assert.assertThat(bookingMap, hasValue(firstname));
    }

    @When("^I send Delete request to list endpoint of booking$")
    public void iSendDeleteRequestToListEndpointOfBooking() {
        response = herokuAppSteps.deleteBooking(bookingID);
        response.assertThat().statusCode(201);
    }

    @And("^I validate if booking is deleted$")
    public void iValidateIfBookingIsDeleted() {
        response = herokuAppSteps.getBookingById(bookingID);
        response.assertThat().statusCode(404);
    }
}
