package com.softserve.javaweb.model;

import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.softserve.javaweb.parser.LocalDateDeserializer;
import com.softserve.javaweb.parser.LocalDateSerializer;


@Valid
public class Person {

    private long id;

    @Size(min = 3, max = 50, message = "The \"Name\" field must have a length greater than 3 characters")
    private String name;

    @Past(message = "Date of birth should be in the past!")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    public LocalDate birthDay;

    private int age;

    private String address;

    @Email(message = "Invalid email format")
    private String email;
    @Pattern(regexp = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}", message = "Invalid phone number")
    private String phoneNumber;
    private String specialization;
    private List<Experience> experience = new ArrayList<>();
    private Image photo;

    private Person() {

    }


    public long getId() {

        return id;

    }


    public int getAge() {
        return age;
    }


    public String getAddress() {
        return address;
    }


    public String getEmail() {
        return email;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }


    public LocalDate getBirthDay() {
        return birthDay;
    }


    public String getName() {
        return name;
    }


    public String getSpecialization() {
        return specialization;
    }


    public List<Experience> getExperience() {
        return experience;
    }


    public static class Builder {
        private Person newPerson;

        public Builder() {
            newPerson = new Person();
        }

        public Builder withId(Long id) {
            newPerson.id = id;
            return this;
        }

        public Builder withName(String name) {
            newPerson.name = name;
            return this;
        }

        public Builder withAge(int age) {
            newPerson.age = age;
            return this;
        }

        public Builder withBirthDay(LocalDate birthDay) {
            newPerson.birthDay = birthDay;
            return this;
        }

        public Builder withAddress(String address) {
            newPerson.address = address;
            return this;
        }

        public Builder withEmail(String email) {
            newPerson.email = email;
            return this;
        }

        public Builder withPhoneNumber(String phoneNumber) {
            newPerson.phoneNumber = phoneNumber;
            return this;
        }

        public Builder withSpecialization(String specialization) {
            newPerson.specialization = specialization;
            return this;
        }

        public Builder withExperience(List<Experience> experiences) {
            newPerson.experience.addAll(experiences);
            return this;
        }

        public Person build() {
            return newPerson;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id &&
                age == person.age &&
                Objects.equals(name, person.name) &&
                Objects.equals(birthDay, person.birthDay) &&
                Objects.equals(address, person.address) &&
                Objects.equals(email, person.email) &&
                Objects.equals(phoneNumber, person.phoneNumber) &&
                Objects.equals(specialization, person.specialization) &&
                Objects.equals(experience, person.experience);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, birthDay, age, address, email, phoneNumber, specialization, experience);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDay=" + birthDay +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", specialization='" + specialization + '\'' +
                ", experience=" + experience +
                '}';
    }
}
