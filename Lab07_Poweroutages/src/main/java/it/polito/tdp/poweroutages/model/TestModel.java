package it.polito.tdp.poweroutages.model;
import java.util.*;
import java.time.*;
public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		System.out.println(model.getNercList());
		
		List<Blackout> test = new ArrayList<>(model.setDisponibili("RFC"));
		
		/*for(Blackout b:test) {
			System.out.println(model.calcolaOre(b));
		}*/
		
		Set<Blackout> prova = model.trovaSoluzione("RFC", 4, 200);
		
		System.out.println(prova);

	}

}
