package com.softserve.javaweb.model;

import java.time.LocalDate;
import java.util.Objects;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.softserve.javaweb.service.LocalDateDeserializer;
import com.softserve.javaweb.service.LocalDateSerializer;


public class Experience {

	private String place;
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate dateFrom;
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate dateTo;

	public Experience() {
	}

	public Experience(String place, LocalDate dateFrom, LocalDate dateTo) {
		super();
		this.place = place;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public LocalDate getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(LocalDate dateFrom) {
		this.dateFrom = dateFrom;
	}

	public LocalDate getDateTo() {
		return dateTo;
	}

	public void setDateTo(LocalDate dateTo) {
		this.dateTo = dateTo;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Experience that = (Experience) o;
		return Objects.equals(place, that.place) &&
				Objects.equals(dateFrom, that.dateFrom) &&
				Objects.equals(dateTo, that.dateTo);
	}

	@Override
	public int hashCode() {
		return Objects.hash(place, dateFrom, dateTo);
	}

	@Override
	public String toString() {
		return "Experience{" +
				"place='" + place + '\'' +
				", dateFrom=" + dateFrom +
				", dateTo=" + dateTo +
				'}';
	}
}
