package com.nopcommerce.qa.testdata;

import java.util.HashMap;
import java.util.Map;

import com.github.javafaker.Faker;
import com.nopcommerce.qa.baseutils.TestBase;

public class RegisterData {
    private String firstName;
    private String lastName;
    private String email;
    private String companyName;
    private String password;
    private String confirmPassword;
    
    public final Faker faker;

    public RegisterData(Faker faker) {
        this.faker = faker;
    }
    
    public Map<String, String> getEmailPasswordMap() {
        Map<String, String> emailPasswordMap = new HashMap<>();
        emailPasswordMap.put("email", getEmail());
        emailPasswordMap.put("password", getPassword());
        return emailPasswordMap;
    }

    // Getters and setters for all fields

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    // Additional getters and setters as needed

    // Method Chaining
    public RegisterData registerNewUser() {
        setFirstName(faker.name().firstName());
        System.out.println("Generated First Name: " + getFirstName());

        setLastName(faker.name().lastName());
        System.out.println("Generated Last Name: " + getLastName());

        setEmail(faker.internet().emailAddress());
        System.out.println("Generated Email: " + getEmail());

        setCompanyName(faker.company().name());
        System.out.println("Generated Company Name: " + getCompanyName());

        setPassword(faker.internet().password());
        System.out.println("Generated Password: " + getPassword());

        setConfirmPassword(getPassword()); // Assume the same password for simplicity
        return this;
    }
}
