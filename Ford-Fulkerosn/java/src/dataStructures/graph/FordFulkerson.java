/**
 * Student's name:Eduard Cybulkiewicz
 *
 * Student's group:INF B
 */

package dataStructures.graph;

import dataStructures.list.ArrayList;
import dataStructures.list.LinkedList;
import dataStructures.list.List;
import dataStructures.set.HashSet;
import dataStructures.set.Set;
import dataStructures.tuple.Tuple2;

public class FordFulkerson<V> {
    private WeightedDiGraph<V,Integer> g; // Initial graph 
    private List<WDiEdge<V,Integer>> sol; // List of edges representing maximal flow graph
    private V src; 			  // Source
    private V dst; 		  	  // Sink
	
    /**
     * Constructors and methods
     */

    public static <V> int maxFlowPath(List<WDiEdge<V,Integer>> path) {

        int max= Integer.MAX_VALUE;

        for( WDiEdge<V,Integer> w : path){
            if(w.getWeight().compareTo(max)<0){
                max=w.getWeight();
            }
        }
        return max;
    }

    public static <V> List<WDiEdge<V,Integer>> updateEdge(V x, V y, Integer p, List<WDiEdge<V,Integer>> edges) {
        int i=0;
        WDiEdge<V,Integer> arco = new WDiEdge<V,Integer>(x,p,y);
        boolean encontrado = false;
        while(i<edges.size()){
            if(edges.get(i).getSrc().equals(x) && edges.get(i).getDst().equals(y)){
                int peso = edges.get(i).getWeight() + p;
                encontrado=true;
                if(peso!=0){
                    WDiEdge<V,Integer> aux = new WDiEdge<V,Integer>(edges.get(i).getSrc(),peso,edges.get(i).getDst());
                    edges.set(i,aux);

                }else{
                    edges.remove(i);
                }
            }
            i++;
        }
        if(!encontrado) {
            WDiEdge<V, Integer> aux = new WDiEdge<V, Integer>(x, p, y);
            edges.append(aux);
        }
        return edges;
    }

    public static <V> List<WDiEdge<V,Integer>> updateEdges(List<WDiEdge<V,Integer>> path, Integer p, List<WDiEdge<V,Integer>> edges) {

        int i=0;
        while (i<edges.size()){
            edges =updateEdge(path.get(i).getSrc(),path.get(i).getDst(),path.get(i).getWeight(),path);
        }
        return edges;
    }

    public static <V> List<WDiEdge<V,Integer>> addFlow(V x, V y, Integer p, List<WDiEdge<V,Integer>> sol) {
        int i=0;
        boolean encontrado =false;
        while(i<sol.size()){
            if(sol.get(i).getSrc().equals(x) && sol.get(i).getDst().equals(y)){
                encontrado=true;
                int peso = sol.get(i).getWeight()+p;
                WDiEdge<V,Integer> aux = new WDiEdge<V,Integer>(sol.get(i).getSrc(),peso,sol.get(i).getDst());
                sol.set(i,aux);
            }else if(sol.get(i).getSrc().equals(y) && sol.get(i).getDst().equals(x) && sol.get(i).getWeight().equals(p)){
                encontrado=true;
                sol.remove(i);
            } else if (sol.get(i).getSrc().equals(y) && sol.get(i).getDst().equals(x) && sol.get(i).getWeight().compareTo(p)<0 ){
                int peso = p-sol.get(i).getWeight();
                encontrado=true;
                WDiEdge<V,Integer> aux= new WDiEdge<V,Integer>(sol.get(i).getSrc(),peso,sol.get(i).getDst());
                sol.set(i,aux);
            }else if(sol.get(i).getSrc().equals(y) && sol.get(i).getDst().equals(x) && sol.get(i).getWeight().compareTo(p)>0){
                int peso = sol.get(i).getWeight()-p;
                encontrado=true;
                WDiEdge<V,Integer> aux= new WDiEdge<V,Integer>(sol.get(i).getDst(),peso, sol.get(i).getSrc());
                sol.set(i,aux);
            }
        }
        if(!encontrado){
            WDiEdge<V,Integer> arco = new WDiEdge<V,Integer>(x,p,y);
            sol.append(arco);
        }
        return sol;
    }

    public static <V> List<WDiEdge<V,Integer>> addFlows(List<WDiEdge<V,Integer>> path, Integer p, List<WDiEdge<V,Integer>> sol) {

        int i =0;
        while(i < path.size()){
            sol = addFlow(path.get(i).getSrc(),path.get(i).getDst(),path.get(i).getWeight(),sol);
            i++;
        }
        return sol;
    }

    public FordFulkerson(WeightedDiGraph<V,Integer> g, V src, V dst) {
        List<WDiEdge<V,Integer>> sol = new ArrayList<>();
        this.src=src;
        this.dst=dst;
        this.g=g;
        this.sol=sol;
        WeightedBreadthFirstTraversal wdg = new WeightedBreadthFirstTraversal<>(g,src);

        List<WDiEdge<V,Integer>> path = wdg.pathTo(dst);

        while(path!=null){
            int mf = this.maxFlowPath(path);
            List<WDiEdge<V,Integer>> edges = this.g.wDiEdges();
            edges = updateEdges(path,-mf,edges);
            List<WDiEdge<V,Integer>> pathInverse = new LinkedList<>();
                for(WDiEdge<V,Integer> w : path){
                    pathInverse.append(new WDiEdge<>(w.getDst(),w.getWeight(),w.getSrc()));
                }
                edges = updateEdges(pathInverse, mf, edges);
                this.g = new WeightedDictionaryDiGraph<>(this.g.vertices(), edges);
                wdg = new WeightedBreadthFirstTraversal(this.g,src);
                this.sol = addFlows(path,mf,sol);

                path = wdg.pathTo(dst);
                System.out.println(sol);

        }
        this.g=g;
    }

    public int maxFlow() {
        int max=0;
        for(WDiEdge<V,Integer> arco :this.sol){
            if(arco.getSrc()==this.src){
                max+=arco.getWeight();
            }
        }
        return max;
    }

    public int maxFlowMinCut(Set<V> set) {
        // TO DO
        return 0;
    }

    /**
     * Provided auxiliary methods
     */
    public List<WDiEdge<V, Integer>> getSolution() {
        return sol;
    }
	
    /**********************************************************************************
     * A partir de aquí SOLO para estudiantes a tiempo parcial sin evaluación continua.
     * ONLY for part time students.
     * ********************************************************************************/

    public static <V> boolean localEquilibrium(WeightedDiGraph<V,Integer> g, V src, V dst) {
        // TO DO
        return false;
    }
    public static <V,W> Tuple2<List<V>,List<V>> sourcesAndSinks(WeightedDiGraph<V,W> g) {
        // TO DO
        return null;
    }

    public static <V> void unifySourceAndSink(WeightedDiGraph<V,Integer> g, V newSrc, V newDst) {
        // TO DO
    }
}
