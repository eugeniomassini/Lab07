package it.polito.tdp.poweroutages.model;
import java.time.*;
public class Blackout {
	
	private Integer peopleAffected;
	private LocalDateTime inizio;
	private LocalDateTime fine;
	public Blackout(Integer peopleAffected, LocalDateTime inizio, LocalDateTime fine) {
		super();
		this.peopleAffected = peopleAffected;
		this.inizio = inizio;
		this.fine = fine;
	}
	public Integer getPeopleAffected() {
		return peopleAffected;
	}
	public void setPeopleAffected(Integer peopleAffected) {
		this.peopleAffected = peopleAffected;
	}
	public LocalDateTime getInizio() {
		return inizio;
	}
	public void setInizio(LocalDateTime inizio) {
		this.inizio = inizio;
	}
	public LocalDateTime getFine() {
		return fine;
	}
	public void setFine(LocalDateTime fine) {
		this.fine = fine;
	}
	@Override
	public String toString() {
		return "Blackout [peopleAffected=" + peopleAffected + ", inizio=" + inizio + ", fine=" + fine + "]\n";
	}
	
	
	
	
	
}
