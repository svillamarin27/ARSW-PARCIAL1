package eci.arsw.covidanalyzer.model;

import java.util.UUID;

public class Result {
    private UUID id;
    private String firstName;
    private String lastName;
    private String gender;
    private String email;
    private String birthString;

    private String testString;
    private boolean result;
    private double testSpecifity;
    private ResultType resulTy;
    public int pruebaCont=1;
    
    public Result(UUID id,String firstName,String lastName,String gender,String email,String birthString,String testString,boolean result,double testSpecifity,ResultType resulTy ){
        this.id = id;
        this.birthString = birthString;
        this.email = email;
        this.firstName = firstName;
        this.gender = gender;
        this.lastName = lastName;
        this.pruebaCont = pruebaCont;
        this.resulTy = resulTy;
        this.result = result;
        this.testSpecifity = testSpecifity;
        this.testString = testString;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthString() {
        return birthString;
    }

    public void setBirthString(String birthString) {
        this.birthString = birthString;
    }

    public String getTestString() {
        return testString;
    }

    public void setTestString(String testString) {
        this.testString = testString;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public double getTestSpecifity() {
        return testSpecifity;
    }

    public void setTestSpecifity(double testSpecifity) {
        this.testSpecifity = testSpecifity;
    }

    public ResultType getResulTy() {
        return resulTy;
    }

    public void setResulTy(ResultType resulTy) {
        this.resulTy = resulTy;
    }

    public void prueba() {
        pruebaCont+=1;
    }
}
