package com.marekzolek.controllerTestIntegration;


import com.marekzolek.TestContext;
import com.marekzolek.controller.CustomerController;
import com.marekzolek.exception.CustomerNotFoundException;
import com.marekzolek.model.Customer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.text.ParseException;

import static org.junit.Assert.*;

@WebAppConfiguration
@SpringBootTest(classes = TestContext.class)
@RunWith(SpringJUnit4ClassRunner.class)
@SqlGroup({@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/insert.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/delete.sql")})
public class CustomerControllerTest {

    @Autowired
    private CustomerController customerController;

    @Test
    public void findOne() throws CustomerNotFoundException {
        Assert.assertEquals("ANNA", customerController.findOne((long) 2).getName());
    }

    @Test
    public void findAll() {
        Customer customer = new Customer((long) 123123, "asdd", "asdasd");
        customerController.add(customer);
        Assert.assertEquals(11, customerController.findAll().size());
    }

    @Test
    public void customersBornBeforeYear() {
        Assert.assertEquals(10, customerController.customersBornBeforeYear(93).size());
    }

    @Test
    public void customersBornBeforeYear1997() {
        Customer customer = new Customer((long) 961245212, "Hubert","Kosa");
        customerController.add(customer);
        Assert.assertEquals(11, customerController.customersBornBeforeYear(97).size());
    }

    @Test
    public void womanBornBeforeYear() {
        Assert.assertEquals(10, customerController.womanBornBeforeYear(93).size());
    }

    @Test
    public void mansBornBeforeYear() {
        Assert.assertEquals(0, customerController.mansBornBeforeYear(93).size());
    }

    @Test
    public void customersWithSumOfServicesInGivenYear() throws ParseException {
        Assert.assertEquals(0, customerController.customersWithSumOfServicesInGivenYear(2015).size());
    }
}