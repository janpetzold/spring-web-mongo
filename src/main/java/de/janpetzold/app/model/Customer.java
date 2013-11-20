package de.janpetzold.app.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "customers")
public class Customer {
	@Id
    private String id;
	private String firstName;
	private String familyName;
    private String city;
    private Long code;
    
    public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getFamilyName() {
		return familyName;
	}
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Long getCode() {
		return code;
	}
	public void setCode(Long code) {
		this.code = code;
	}
	
	public Customer() {
	}
	@Override
	public String toString() {
		return "Customer [id=" + id + ", firstName=" + firstName
				+ ", familyName=" + familyName + ", city=" + city + ", code="
				+ code + "]";
	}
}
