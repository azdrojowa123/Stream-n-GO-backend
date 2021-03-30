package com.example.xeva.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="time_events")
public class TimeEvent {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "Start_date")
	private LocalDateTime startDate;

	@Column(name = "End_date")
	private LocalDateTime endDate;
	
	@ManyToOne
    @JoinColumn(name="Event_id", nullable = false)
    private Event event;

	@ManyToMany(mappedBy = "savedEvents")
	Set<User> savedBy;

	public Set<User> getSavedBy() {
		return savedBy;
	}

	public void setSavedBy(Set<User> savedBy) {
		this.savedBy = savedBy;
	}

	public TimeEvent() {}
	
	public TimeEvent(LocalDateTime startDate, LocalDateTime endDate, Event event) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.event = event;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}
	
	
}
