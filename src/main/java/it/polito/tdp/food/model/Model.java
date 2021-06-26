package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	FoodDao dao = new FoodDao();
	Graph<String,DefaultWeightedEdge> grafo;
	List<String> vertici;
	
	// PUNTO 2
	List<String> soluzioneMigliore;
	int costoMassimo;
	int N;
	
	public String creaGrafo(int calorie) {
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		// Aggiungo i vertici
		vertici = new LinkedList<>(this.dao.tipiDiPorzione(calorie));
		Graphs.addAllVertices(this.grafo,vertici);
		
		// Aggiungo gli archi
		for(Adiacenza a : this.dao.getArchi(calorie))
			if(this.grafo.vertexSet().contains(a.getP1()) && this.grafo.vertexSet().contains(a.getP2()))
				Graphs.addEdge(this.grafo,a.getP1(),a.getP2(),a.getPeso());
		
		return "Grafo creato!\n\nNumero vertici: "+this.grafo.vertexSet().size()+"\nNumero archi: "+this.grafo.edgeSet().size();
	}
	
	public List<String> getVertici() {
		return this.vertici;
	}
	
	public List<Adiacenza> getCorrelati(String partenza) {
		List<Adiacenza> result = new LinkedList<Adiacenza>();
		for(String vicino : Graphs.neighborListOf(this.grafo,partenza)) {
			DefaultWeightedEdge arco = this.grafo.getEdge(partenza, vicino);
			int peso = (int)this.grafo.getEdgeWeight(arco);
			result.add(new Adiacenza(vicino,null,peso));
		}
		return result;
	}
	
	
	// PUNTO 2
	public String cercaCammino(int N, String partenza) {
		this.N=N;
		List<String> parziale = new ArrayList<>();
		parziale.add(partenza);
		
		int costoParziale=0;
		this.costoMassimo=0;
		
		ricorsione(parziale,costoParziale);
		
		String result = "\n\nCammino di peso massimo trovato:\n";
		
		for(String s : this.soluzioneMigliore)
			result += s+"\n";
		result+="Con peso: "+this.costoMassimo;
		
		return result;
	}

	private void ricorsione(List<String> parziale, int costoParziale) {
		String ultimoInserito = parziale.get(parziale.size()-1);
		// Caso terminale
		if(parziale.size()==N) {
			// Controllo se è il cammino migliore finora
			if(costoParziale>this.costoMassimo) {
				this.costoMassimo=costoParziale;
				this.soluzioneMigliore = new ArrayList<>(parziale);
			}
			return;
		}
		
		// ...altrimenti
		for(DefaultWeightedEdge arco : this.grafo.edgesOf(ultimoInserito)) {
			String vicino = Graphs.getOppositeVertex(this.grafo,arco,ultimoInserito);
			int peso = (int)this.grafo.getEdgeWeight(arco);
			
			// Se non è già stato inserito provo a inserirlo
			if(!parziale.contains(vicino)) {
				List<String> nuovaParziale = new ArrayList<>(parziale);
				nuovaParziale.add(vicino);
				int nuovoCostoParziale = costoParziale + peso;
				ricorsione(nuovaParziale,nuovoCostoParziale);
			}
		}
		
	}
	
	
}
