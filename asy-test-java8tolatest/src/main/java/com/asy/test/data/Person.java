package com.asy.test.data;

import java.util.Date;
import java.util.Optional;

public class Person {
    private String name;
    private Optional<String> middleName = Optional.empty();
    private String surname;
    private Date birthDate;
    private Gender gender;
    private String uniqueId;
    private Long magicNumber;
    private boolean vip;

    public Person(String name, Optional<String> middleName, String surname, Date birthDate, Gender gender, String uniqueId, Long magicNumber, boolean vip) {
        this.name = name;
        this.middleName = middleName;
        this.surname = surname;
        this.birthDate = birthDate;
        this.gender = gender;
        this.uniqueId = uniqueId;
        this.magicNumber = magicNumber;
        this.vip = vip;
    }

    public Person(String dummyName, String dummySurname) {
        this.name = dummyName;
        //this.middleName = Optional.empty();
        this.surname = dummySurname;
    }

    public String getName() {
        return name;
    }

    public Optional<String> getMiddleName() {
        return middleName;
    }

    public String getSurname() {
        return surname;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public Long getMagicNumber() {
        return magicNumber;
    }

    public boolean isVip() {
        return vip;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public void setMagicNumber(Long magicNumber) {
        this.magicNumber = magicNumber;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                (middleName.isPresent() ? ", middleName='" + middleName.get() + '\'' : "") +
                //(Optional.ofNullable(middleName).isPresent() ? ", middleName='" + middleName.get() + '\'' : "") +
                //", middleName= \'" + middleName.orElse("") + "\'" +
                ", surname='" + surname + '\'' +
                ", birthDate=" + birthDate + //todo format
                ", gender=" + gender +
                ", uniqueId=" + uniqueId +
                ", magicNumber=" + magicNumber +
                ", vip=" + vip +
                '}';
    }

    public enum Gender {
        MALE, FEMALE
    }
}

