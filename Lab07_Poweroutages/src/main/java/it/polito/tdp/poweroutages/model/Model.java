package it.polito.tdp.poweroutages.model;

import java.util.*;
import java.time.*;
import it.polito.tdp.poweroutages.DAO.PowerOutageDAO;

public class Model {
	
	PowerOutageDAO podao;
	Set<Blackout> worstCase;
	long worstPersone;
	Set<Blackout> disponibili;
	
	public Model() {
		podao = new PowerOutageDAO();
	}
	
	public List<Nerc> getNercList() {
		return podao.getNercList();
	}
	
	public Set<Blackout> trovaSoluzione(String nerc, int anni, int ore){
		
		Set<Blackout> parziale = new HashSet();
		worstCase = new HashSet();
		
		disponibili = new HashSet<>(podao.getBlackout(nerc));
		worstPersone = 0;
		
		cerca(parziale, 0, disponibili, anni, ore);
		
		return worstCase;
		
	}

	private void cerca(Set<Blackout> parziale, int livello, Set<Blackout> disponibili, int anni, int ore) {
		
		if(livello==anni) {
			if(calcolaWorstPersone(parziale)>worstPersone && isValida(parziale,anni, ore)) {
				worstCase = new HashSet(parziale);
				worstPersone=calcolaWorstPersone(parziale);
			}
			return;
		}
		
		if(parziale!=null && isValida(parziale, anni, ore) && calcolaWorstPersone(parziale)>worstPersone) {
			worstCase = new HashSet(parziale);
			worstPersone=calcolaWorstPersone(parziale);
		}
		
		for(Blackout b: disponibili) {
			if(b.getPeopleAffected()!=0) {
				if(isValida(parziale, anni, ore)) {
					parziale.add(b);
					Set<Blackout>rimanenti = new HashSet<>(disponibili);
					rimanenti.remove(b);
					
					cerca(parziale, livello+1, rimanenti, anni, ore);
					parziale.remove(b);
					
					cerca(parziale, livello+1, rimanenti, anni, ore);
					
				}
				
			}
		}
		
		
		
	}
	
	private long calcolaOre(Blackout blackout) {
		return Duration.between(blackout.getInizio(), blackout.getFine()).toHours();
	}
	
	private long calcolaTotOre(Set<Blackout> parziale) {
		long somma=0;
		for(Blackout b:parziale) {
			somma+=calcolaOre(b);	
		}
		
		return somma;
	}

	private long calcolaWorstPersone(Set<Blackout> parziale) {
		long somma=0;
		for(Blackout b:parziale) {
			somma+=b.getPeopleAffected();	
		}
		
		return somma;
	}

	private boolean isValida(Set<Blackout> parziale, int anni, int ore) {
		int max=0;
		int min=0;
		for(Blackout b:parziale) {
			if(min==0 && max==0) {
				min=b.getInizio().getYear();
				max=b.getFine().getYear();
			}
			
			if(b.getInizio().getYear()<min)
				min=b.getInizio().getYear();
			
			if(b.getFine().getYear()>max)
				max=b.getFine().getYear();
			
		}
		
		return (max-min)<=anni && calcolaTotOre(parziale)<=ore;
		
	}
	
	public List<Blackout> setDisponibili(String nerc){
		return podao.getBlackout(nerc);
	}


	

}
