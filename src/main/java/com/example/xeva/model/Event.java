package com.example.xeva.model;

import java.sql.Time;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "events")
public class Event {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
    @JoinColumn(name="User_id", nullable = false)
	private User user;
	
	@ManyToOne(cascade= CascadeType.PERSIST)
    @JoinColumn(name="Organization_id", nullable = false)
	private Organization organization;
	
	@NotEmpty(message = "Name cannot be empty")
	@Column(name = "Name")
	private String name;
	
	@NotEmpty(message = "Description cannot be empty")
	@Column(name = "Description")
	private String description;


	@Column(name = "DaysOfWeek")
	private byte daysOfWeek;
	

	@Column(name = "Cyclical")
	private boolean cyclical;

	@Column(name = "Duration")
	private Time duration;
	
	@NotEmpty(message = "Mode cannot be empty")
	@Column(name = "Mode")
	private String mode;
	
	@NotEmpty(message = "Web address cannot be empty")
	@Column(name = "Web_address")
	private String webAddress;
	
	@Column(name = "Tags")
	private String tags;
	
	@NotEmpty(message = "Language cannot be empty")
	@Column(name = "Language")
	private String language;
	

	@Column(name = "Status")
	private byte status;
	
	@OneToMany(mappedBy="event", fetch = FetchType.LAZY)
	Set<TimeEvent> timeEvents;
	
	public Event() {
		
	}

	public Event(User user, Organization organization,  String name,  String description, byte daysOfWeek, boolean cyclical, Time duration, String mode, String webAddress, String tags,String language, byte status) {
		this.user = user;
		this.organization = organization;
		this.name = name;
		this.description = description;
		this.daysOfWeek = daysOfWeek;
		this.cyclical = cyclical;
		this.duration = duration;
		this.mode = mode;
		this.webAddress = webAddress;
		this.tags = tags;
		this.language = language;
		this.status = status;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public Set<TimeEvent> getTimeEvents() {
		return timeEvents;
	}

	public void setTimeEvents(Set<TimeEvent> timeEvents) {
		this.timeEvents = timeEvents;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte getDaysOfWeek() {
		return daysOfWeek;
	}

	public void setDaysOfWeek(byte daysOfWeek) {
		this.daysOfWeek = daysOfWeek;
	}

	public boolean isCyclical() {
		return cyclical;
	}

	public void setCyclical(boolean cyclical) {
		this.cyclical = cyclical;
	}

	public Time getDuration() {
		return duration;
	}

	public void setDuration(Time duration) {
		this.duration = duration;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getWebAddress() {
		return webAddress;
	}

	public void setWebAddress(String webAddress) {
		this.webAddress = webAddress;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}


		
}
