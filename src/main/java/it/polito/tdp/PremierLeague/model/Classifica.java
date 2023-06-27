package it.polito.tdp.PremierLeague.model;

public class Classifica implements Comparable<Classifica>{
	
	Team t1;
	int punteggio;
	
	public Classifica(Team t1, int punteggio) {
		super();
		this.t1 = t1;
		this.punteggio = punteggio;
	}
	/**
	 * @return the t1
	 */
	public Team getT1() {
		return t1;
	}
	/**
	 * @param t1 the t1 to set
	 */
	public void setT1(Team t1) {
		this.t1 = t1;
	}
	/**
	 * @return the punteggio
	 */
	public int getPunteggio() {
		return punteggio;
	}
	/**
	 * @param punteggio the punteggio to set
	 */
	public void setPunteggio(int punteggio) {
		this.punteggio = punteggio;
	}
	@Override
	public int compareTo(Classifica o) {
		// TODO Auto-generated method stub
		return -(o.getPunteggio()-this.punteggio);
	}
	
	
	
}
