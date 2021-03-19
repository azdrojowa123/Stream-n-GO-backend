package com.example.xeva.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "Organizations")
public class Organization {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;
	
	@NotEmpty(message = "Name cannot be empty")
	@Column(name = "Name")
	private String name;
	
	@NotEmpty(message = "Country cannot be empty")
	@Column(name = "Country")
	private String country;
	
	@NotEmpty(message = "Province cannot be empty")
	@Column(name = "Province")
	private String province;
	
	@NotEmpty(message = "City cannot be empty")
	@Column(name = "City")
	private String city;
	
	@NotEmpty(message = "Postal Code cannot be empty")
	@Column(name = "Postal_code")
	private String postalCode;
	
	@NotEmpty(message = "Street cannot be empty")
	@Column(name = "Street")
	private String street;
	
	@Column(name = "NIP")
	private String nip;
	

	@Column(name = "Phone_number")
	private long phoneNumber;
	
	@NotEmpty(message = "Email cannot be empty")
	@Column(name = "Email")
	private String email;
	
	@Column(name = "Web_page")
	private String webPage;
	
	@Column(name = "Photo")
	private String photo;
	
	@OneToMany(mappedBy = "organization", fetch = FetchType.LAZY)
	Set<Event> events;
	
	public Organization() {
		
	}	

	public Organization( String name, String country, String province, String city, String postalCode,
			 String street, String nip, long phoneNumber, String email, String webPage, String photo) {
		this.name = name;
		this.country = country;
		this.province = province;
		this.city = city;
		this.postalCode = postalCode;
		this.street = street;
		this.nip = nip;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.webPage = webPage;
		this.photo = photo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getNip() {
		return nip;
	}

	public void setNip(String nip) {
		this.nip = nip;
	}

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWebPage() {
		return webPage;
	}

	public void setWebPage(String webPage) {
		this.webPage = webPage;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	
}
