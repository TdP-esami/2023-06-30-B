package it.polito.tdp.exam.model;

import it.polito.tdp.exam.db.BaseballDAO;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.*;

public class Model {

    private BaseballDAO dao;
    private SimpleWeightedGraph<Integer, DefaultWeightedEdge> grafo;
    private Map<Integer, List<PeopleSalary>> annoToPlayers;


    public Model() {
        this.dao = new BaseballDAO();
        this.annoToPlayers = new HashMap<Integer, List<PeopleSalary>>();
    }

    public void buildGraph(String name){

        this.grafo = new SimpleWeightedGraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);

        //Vertici
        List<Integer> vertici = this.dao.getVertici(name);
        Graphs.addAllVertices(this.grafo, vertici);

        //Leggere i giocatori per ogni anno
        this.annoToPlayers.clear();
        for (int anno : vertici) {
            this.annoToPlayers.put(anno, this.dao.getPlayersSalaryTeamYear(name, anno));
        }

        //verificare ogni coppia di anni, e creare un arco con il peso corrispondente
        for (int i = 0; i <vertici.size(); i++) {
            for (int j = i+1; j < vertici.size(); j++) {
                List<PeopleSalary> giocatori1 = new ArrayList<PeopleSalary>(this.annoToPlayers.get(vertici.get(i)));
                List<PeopleSalary> giocatori2 = new ArrayList<PeopleSalary>(this.annoToPlayers.get(vertici.get(j)));

                double peso = Math.abs(getCumulativeSalary(giocatori1) - getCumulativeSalary(giocatori2));
                Graphs.addEdgeWithVertices(this.grafo, vertici.get(i), vertici.get(j), peso);
            }
        }
    }

    public List<Dettaglio> getDettagli(int anno) {
        List<Dettaglio> result = new ArrayList<Dettaglio>();
        List<Integer> adiacenti = Graphs.neighborListOf(this.grafo, anno);


        for(Integer nodo : adiacenti) {
            DefaultWeightedEdge arco = this.grafo.getEdge(anno, nodo);
            result.add(new Dettaglio(nodo, (int)this.grafo.getEdgeWeight(arco)) );
        }
        Collections.sort(result);
        return result;
    }

    public Set<Integer> getVertici(){
        return this.grafo.vertexSet();
    }

    public Set<DefaultWeightedEdge> getArchi(){
        return this.grafo.edgeSet();
    }

    public double getCumulativeSalary(List<PeopleSalary> giocatori) {
        double salaryCum = 0;
        for (PeopleSalary ps : giocatori)
            salaryCum += ps.getSalary();

        return salaryCum;
    }


    public List<String> getTeamsNames(){
        return this.dao.getTeamsNames();
    }



}
