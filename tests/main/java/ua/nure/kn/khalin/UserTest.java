package main.java.ua.nure.kn.khalin;


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class UserTest {

    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";

    private User user;

    @Before
    public void setUp() {
        this.user = new User();
    }

    @Test
    public void is_full_name_returned() {

        this.user.setFirstName(FIRST_NAME);
        this.user.setLastName(LAST_NAME);

        String expectedResult = new StringBuilder(LAST_NAME).append(", ").append(FIRST_NAME).toString();

        assertEquals(expectedResult, this.user.getFullName());
    }

    @Test
    public void is_returned_age_correct() throws ParseException {
        Date dob = new SimpleDateFormat("d-MM-yyyy").parse("01-01-1971");
        LocalDate localDate = dob.toInstant().atZone(
                ZoneId.systemDefault()
        ).toLocalDate();

        int expectedAge = Period.between(
                localDate,
                LocalDate.now()
        ).getYears();

        this.user.setDateOfBirth(dob);

        assertEquals(expectedAge, this.user.getAge());
    }

    @Test
    public void is_age_zero_when_dob_today() {
        int expectedAge = 0;
        this.user.setDateOfBirth(new Date());

        assertEquals(expectedAge, this.user.getAge());
    }
}