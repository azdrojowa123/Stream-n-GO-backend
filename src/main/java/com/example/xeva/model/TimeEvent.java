package com.example.xeva.model;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="time_events")
public class TimeEvent {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "Start_date")
	private Timestamp startDate;

	@Column(name = "End_date")
	private Timestamp endDate;
	
	@ManyToOne
    @JoinColumn(name="Event_id", nullable = false)
    private Event event;
	
	public TimeEvent() {
		
	}
	
	public TimeEvent(Timestamp startDate, Timestamp endDate, Event event) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.event = event;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
	
	
}
