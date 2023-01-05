/**----------------------------------------------
 * -- Estructuras de Datos.  2018/19
 * -- 2º Curso del Grado en Ingeniería [Informática | del Software | de Computadores].
 * -- Escuela Técnica Superior de Ingeniería en Informática. UMA
 * --
 * -- Examen 4 de febrero de 2019
 * --
 * -- ALUMNO/NAME: Eduard Cybulkiewicz
 * -- GRADO/STUDIES:Inf A
 * -- NÚM. MÁQUINA/MACHINE NUMBER: 23
 * --
 * ----------------------------------------------
 */

package dataStructures.graph;

import java.util.Iterator;

import dataStructures.dictionary.Dictionary;
import dataStructures.dictionary.HashDictionary;

import dataStructures.set.Set;
import dataStructures.set.HashSet;
import dataStructures.tuple.Tuple2;

public class DictionaryWeightedGraph<V, W extends Comparable<? super W>> implements WeightedGraph<V, W> {

    static class WE<V1, W1 extends Comparable<? super W1>> implements WeightedEdge<V1, W1> {

		V1 src, dst;
        W1 wght;

        WE(V1 s, V1 d, W1 w) {
            src = s;
            dst = d;
            wght = w;
        }

        public V1 source() {
            return src;
        }

        public V1 destination() {
            return dst;
        }

        public W1 weight() {
            return wght;
        }

        public String toString() {
            return "WE(" + src + "," + dst + "," + wght + ")";
        }

		public int hashCode() {
               return this.src.hashCode() + this.dst.hashCode();
		}
		public boolean equals(Object obj) {
            if (obj instanceof WeightedGraph.WeightedEdge<?, ?>) {
                return false;
            } else {
                WeightedEdge w = (WeightedEdge) obj;
                return this.src.equals(w.source()) && this.dst.equals(w.destination()) && this.wght.equals(w.weight());
            }
        }
            public int compareTo (WeightedEdge < V1, W1 > o){
                        return this.wght.compareTo(o.weight());
            }
        }
    /**
     * Each vertex is associated to a dictionary containing associations
     * from each successor to its weight
     */
    protected Dictionary<V, Dictionary<V, W>> graph;

    public DictionaryWeightedGraph() {
        graph = new HashDictionary<>();
    }
    public void addVertex(V v) {
            Dictionary<V,W> d = new HashDictionary<>();
            graph.insert(v,d);

    }
    public void addEdge(V src, V dst, W w) {
        if(!graph.isDefinedAt(src) && !graph.isDefinedAt(dst)){
            throw new GraphException("Los Vertices no estan en el grafo");
        }
        Dictionary<V,W> d = graph.valueOf(src);
        d.insert(dst,w);
        graph.insert(src,d);
    }

    public Set<Tuple2<V, W>> successors(V v) {
        if(!graph.isDefinedAt(v)){
            throw new GraphException("Vertice no esta en el grafo");
        }
        Set<Tuple2<V,W>> set =  new HashSet<>();

        Dictionary<V,W> d = graph.valueOf(v);

        for( Tuple2<V,W> it : d.keysValues()){
            set.insert(it);
        }
    	return set;
    }


    public Set<WeightedEdge<V, W>> edges() {
        Set<WeightedEdge<V,W>> set = new HashSet<>();

        for(Tuple2<V, Dictionary<V,W>> it : graph.keysValues()){
           for(Tuple2<V, W> d : it._2().keysValues()){
               WE<V,W> w = new WE<V, W>(it._1(),d._1(),d._2());
               set.insert(w);
           }
        }
        return set;
    }

    /** DON'T EDIT ANYTHING BELOW THIS COMMENT **/


    public Set<V> vertices() {
        Set<V> vs = new HashSet<>();
        for (V v : graph.keys())
            vs.insert(v);
        return vs;
    }


    public boolean isEmpty() {
        return graph.isEmpty();
    }

    public int numVertices() {
        return graph.size();
    }


    public int numEdges() {
        int num = 0;
        for (Dictionary<V, W> d : graph.values())
            num += d.size();
        return num / 2;
    }


    public String toString() {
        String className = getClass().getSimpleName();
        String s = className + "(vertices=(";

        Iterator<V> it1 = vertices().iterator();
        while (it1.hasNext())
            s += it1.next() + (it1.hasNext() ? ", " : "");
        s += ")";

        s += ", edges=(";
        Iterator<WeightedEdge<V, W>> it2 = edges().iterator();
        while (it2.hasNext())
            s += it2.next() + (it2.hasNext() ? ", " : "");
        s += "))";

        return s;
    }
}
