package it.polito.tdp.poweroutages.model;

import java.util.List;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		System.out.println(model.getNercList());
		
		System.out.println(model.getNercList().get(3));

		
															  // nerc			 //maxanni //maxore
		List<PowerOutage> solution =  model.trovaWorstCase(model.getNercList().get(2), 2, 15);
		System.out.println(model.getMaxTotalAffected());
		System.out.println(model.getTotalHours());
		for(PowerOutage po : solution) {
			System.out.println(po);
		}

	}

}
