package de.janpetzold.app.model;

import java.util.Arrays;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cities")
public class City {
	
	@Id
    private String id;
	private String country;
	private String city;
	// Can't use camelCase here since the variable name has to EXACTLY match the key value in the document
	private String accentcity;
	private String region;
    private Long population;
    private Float[] location;

    public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	public String getAccentCity() {
		return accentcity;
	}

	public void setAccentCity(String accentcity) {
		this.accentcity = accentcity;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public Long getPopulation() {
		return population;
	}

	public void setPopulation(Long population) {
		this.population = population;
	}

	public Float[] getLocation() {
		return location;
	}

	public void setLocation(Float[] location) {
		this.location = location;
	}

	public City() {
    }

    public City(String city, Long population) {
        this.city = city;
        this.population = population;
    }

	@Override
	public String toString() {
		return "City [id=" + id + ", country=" + country + ", city=" + city
				+ ", accentCity=" + accentcity + ", region=" + region
				+ ", population=" + population + ", location="
				+ Arrays.toString(location) + "]";
	}
}
