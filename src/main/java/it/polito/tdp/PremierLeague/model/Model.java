package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	List<Team> vertici;
	PremierLeagueDAO dao;
	SimpleDirectedWeightedGraph<Team, DefaultWeightedEdge> graph;
	
	public Model() {
		
		vertici = new ArrayList<>();
		dao = new PremierLeagueDAO();
		
		
	}
	
	
	public void buildGraph() {
		
		loadNodes();
		graph = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		
		Graphs.addAllVertices(graph, vertici);
		System.out.println(this.graph.vertexSet().size());
		
		List<Classifica> clas = findClassifica();
		for(Classifica c1: clas) {
			
			Team t1 = c1.getT1();
			int p1 = c1.getPunteggio();
			
			for(Classifica c2: clas) {
				
				Team t2 = c2.getT1();
				int p2 = c2.getPunteggio();
				//condizione aggiunta arco
				if(p1-p2>0) {
					
					Graphs.addEdgeWithVertices(graph, t1, t2, p1-p2);
					
				}
			}
		}
		
		System.out.println(this.graph.edgeSet().size()+"\n");
		
		
	}
	
	public void loadNodes() {
		
		if(vertici.isEmpty())
			vertici = this.dao.listAllTeams();
		
	}
	
	public List<Classifica> findClassifica() {
		
		//importiamo tutti i match
		List<Match> partite = new ArrayList<>(this.dao.listAllMatches());
		List<Classifica> clas = new ArrayList<>();
		
		//facciamo una classifica non ordinata
		
		for(Team t: this.graph.vertexSet()) {
			
			int punteggio = 0;
			
			for(Match m: partite) {
				
				//condizione vittoria
				if(m.getTeamHomeID()==t.getTeamID()&& m.getReaultOfTeamHome()==1) {
					punteggio += 3;
				}else if(m.getTeamAwayID()==t.getTeamID()&& m.getReaultOfTeamHome()==-1) {
					punteggio += 3;
				}
				//condizione pareggio
				if(m.getTeamHomeID()==t.getTeamID() && m.getReaultOfTeamHome()== 0 ) {
					punteggio += 1;
				}else if(m.getTeamAwayID()==t.getTeamID() && m.getReaultOfTeamHome() == 0) {
					punteggio += 1;
				}
				//condizione sconfitta
				if(m.getTeamHomeID()==t.getTeamID() && m.getReaultOfTeamHome() == -1) {
					punteggio += 0;
				}else if(m.getTeamAwayID() == t.getTeamID() && m.getReaultOfTeamHome() == 1) {
					punteggio += 0;
				}
				
				
			}
			
			clas.add(new Classifica(t, punteggio));
			
			
		}
		return clas;
	}
	
	public List<Classifica> getPeggiori(Team t){
		
		int punteggio = 0;
		List<Classifica> peggiori = new ArrayList<>();
		
		
		for(Classifica c: findClassifica()) {
			if(c.getT1().getTeamID()==t.getTeamID()) {
				punteggio = c.getPunteggio();
			}
		}
		
		for(Classifica c: findClassifica()) {
			if(c.getPunteggio()<punteggio) {
				peggiori.add(new Classifica(c.getT1(), punteggio-c.getPunteggio()));
			}
		}
		Collections.sort(peggiori);
		
		return peggiori;
	}
	
	public List<Classifica> getMigliori(Team t){
		
		int punteggio = 0;
		List<Classifica> migliori = new ArrayList<>();
		
		
		for(Classifica c: findClassifica()) {
			if(c.getT1().getTeamID()==t.getTeamID()) {
				punteggio = c.getPunteggio();
			}
		}
		
		for(Classifica c: findClassifica()) {
			if(c.getPunteggio()>punteggio) {
				migliori.add(new Classifica(c.getT1(), c.getPunteggio()-punteggio));
			}
		}
		Collections.sort(migliori);
		
		return migliori;
		
	}
	
	public SimpleDirectedWeightedGraph<Team, DefaultWeightedEdge> getGraph() {
		return graph;
	}
}
