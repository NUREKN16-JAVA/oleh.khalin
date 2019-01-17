package main.java.ua.nure.kn.khalin;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class User implements Serializable {

    private static final long serialVersionUID = 280401130384252681L;
    private Long id;
    private String firstName;
    private String lastName;
    private Date dob;

    public User() {
    }

    public User(Long id, String firstName, String lastName, Date dob) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
    }

    public String getFullName() {
        return new StringBuilder(this.lastName).append(", ").append(this.firstName).toString();
    }

    public int getAge() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.dob);
        return Period.between(this.dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now())
                .getYears();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDob() {
        return this.dob;
    }

    public void setDateOfBirth(Date dob) {
        this.dob = dob;
    }

}
