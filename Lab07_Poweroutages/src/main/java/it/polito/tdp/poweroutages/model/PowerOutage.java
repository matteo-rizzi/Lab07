package it.polito.tdp.poweroutages.model;

import java.time.Duration;
import java.time.LocalDateTime;

public class PowerOutage {

	private int id;
	private LocalDateTime dateEventBegan;
	private LocalDateTime dateEventFinished;
	private long customersAffected;


	public PowerOutage(int id, LocalDateTime dateEventBegan, LocalDateTime dateEventFinished, long customerAffected) {
		super();
		this.id = id;
		this.dateEventBegan = dateEventBegan;
		this.dateEventFinished = dateEventFinished;
		this.customersAffected = customerAffected;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getDateEventBegan() {
		return dateEventBegan;
	}

	public void setDateEventBegan(LocalDateTime dateEventBegan) {
		this.dateEventBegan = dateEventBegan;
	}

	public LocalDateTime getDateEventFinished() {
		return dateEventFinished;
	}

	public void setDateEventFinished(LocalDateTime dateEventFinished) {
		this.dateEventFinished = dateEventFinished;
	}

	public long getCustomersAffected() {
		return customersAffected;
	}

	public void setCustomersAffected(long customerAffected) {
		this.customersAffected = customerAffected;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	
	public long getOre() {
		Duration d = Duration.between(getDateEventBegan(), getDateEventFinished());
		long ore = d.getSeconds() / 3600;
		return ore;
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

	@Override
	public String toString() {
		return this.dateEventBegan.getYear() + " " + this.dateEventBegan + " " + this.dateEventFinished + " "+ this.getOre() + " " + this.customersAffected;
	}


}
