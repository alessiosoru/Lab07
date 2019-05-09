package it.polito.tdp.poweroutages.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.TimeZone;

public class PowerOutage {
	
	private int id;
	private Nerc nerc;
	private int customers_affected;
	private LocalDateTime data_event_began;
	private LocalDateTime data_event_finished;
	private long duration;
	
	public PowerOutage(int id, Nerc nerc, int customers_affected, LocalDateTime data_event_began,
			LocalDateTime data_event_finished, long duration) {
		super();
		this.id = id;
		this.nerc = nerc;
		this.customers_affected = customers_affected;
		this.data_event_began = data_event_began;
		this.data_event_finished = data_event_finished;
		this.duration=duration;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Nerc getNerc() {
		return nerc;
	}

	public void setNerc(Nerc nerc) {
		this.nerc = nerc;
	}

	public int getCustomers_affected() {
		return customers_affected;
	}

	public void setCustomers_affected(int customers_affected) {
		this.customers_affected = customers_affected;
	}

	public LocalDateTime getData_event_began() {
		return data_event_began;
	}

	public void setData_event_began(LocalDateTime data_event_began) {
		this.data_event_began = data_event_began;
	}

	public LocalDateTime getData_event_finished() {
		return data_event_finished;
	}

	public void setData_event_finished(LocalDateTime data_event_finished) {
		this.data_event_finished = data_event_finished;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PowerOutage other = (PowerOutage) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
//	// costruttore clonante
//	protected PowerOutage(PowerOutage altro) {
//		this.id = altro.id;
//		this.nerc = new Nerc(othher), this.customers_affected, this.data_event_began, this.data_event_finished, this.duration);
//		return po;
//	}

	@Override
	public String toString() {
		return "PowerOutage [id=" + id + ", nerc=" + nerc + ", customers_affected=" + customers_affected
				+ ", data_event_began=" + data_event_began + ", data_event_finished=" + data_event_finished + "]\n";
	}
	

}
