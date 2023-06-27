package it.polito.tdp.PremierLeague.model;

import java.util.List;

public class TestModel {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Model m = new Model();
		m.buildGraph();
		List<Classifica> list = m.findClassifica();
		
		for(Classifica c: list) {
			System.out.println(c.getT1().getName()+" "+c.getPunteggio());
		}
	}

}
