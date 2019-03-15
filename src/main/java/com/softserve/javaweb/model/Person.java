package com.softserve.javaweb.model;

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
import com.softserve.javaweb.service.LocalDateDeserializer;
import com.softserve.javaweb.service.LocalDateSerializer;


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

    public Person() {

    }

    public Person(long id) {
	this.id = id;

    }

    public Person(String name, int age, LocalDate birthDay, String address, String email, String phoneNumber,
	    String specialization, List<Experience> experience) {
	this.name = name;
	this.age = age;
	this.birthDay = birthDay;
	this.address = address;
	this.email = email;
	this.phoneNumber = phoneNumber;
	this.specialization = specialization;
	this.experience = experience;
    }

    public Person(String name, LocalDate dateBirth, String specialization, List<Experience> experience) {
	super();
	this.name = name;
	this.birthDay = dateBirth;
	this.specialization = specialization;
	this.experience = experience;
    }

    public Person(
	    @Size(min = 3, max = 50, message = "The \"Name\" field must have a length greater than 3 characters") String name,
	    @Past(message = "Date of birth should be in the past!") LocalDate birthDay, int age, String address,
	    @Email(message = "Invalid email format") String email,
	    @Pattern(regexp = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}", message = "Invalid phone number") String phoneNumber,
	    String specialization) {
	super();
	this.name = name;
	this.birthDay = birthDay;
	this.age = age;
	this.address = address;
	this.email = email;
	this.phoneNumber = phoneNumber;
	this.specialization = specialization;
    }

	public Person(long id, @Size(min = 3, max = 50, message = "The \"Name\" field must have a length greater than 3 characters") String name,int age, @Past(message = "Date of birth should be in the past!") LocalDate birthDay,  String address, @Email(message = "Invalid email format") String email, @Pattern(regexp = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}", message = "Invalid phone number") String phoneNumber, String specialization, List<Experience> experience) {
		this.id = id;
		this.name = name;
		this.birthDay = birthDay;
		this.age = age;
		this.address = address;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.specialization = specialization;
		this.experience = experience;
	}

	public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public int getAge() {
	return age;
    }

    public void setAge(int age) {
	this.age = age;
    }

    public String getAddress() {
	return address;
    }

    public void setAddress(String address) {
	this.address = address;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getPhoneNumber() {
	return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
	this.phoneNumber = phoneNumber;
    }

    public LocalDate getBirthDay() {
	return birthDay;
    }

    public void setBirthDay(LocalDate birthDate) {
	this.birthDay = birthDate;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getSpecialization() {
	return specialization;
    }

    public void setSpecialization(String specialization) {
	this.specialization = specialization;
    }

    public List<Experience> getExperience() {
	return experience;
    }

    public void setExperience(List<Experience> experience) {
	this.experience.addAll(experience);
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
