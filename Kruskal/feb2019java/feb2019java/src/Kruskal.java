/**----------------------------------------------
 * -- Estructuras de Datos.  2018/19
 * -- 2º Curso del Grado en Ingeniería [Informática | del Software | de Computadores].
 * -- Escuela Técnica Superior de Ingeniería en Informática. UMA
 * --
 * -- Examen 4 de febrero de 2019
 * --
 * -- ALUMNO/NAME:Eduard Cybulkiewicz
 * -- GRADO/STUDIES: Inf A
 * -- NÚM. MÁQUINA/MACHINE NUMBER: 23
 * --
 * ----------------------------------------------
 */

import dataStructures.graph.DictionaryWeightedGraph;
import dataStructures.graph.WeightedGraph;
import dataStructures.graph.WeightedGraph.WeightedEdge;

import dataStructures.dictionary.Dictionary;
import dataStructures.dictionary.HashDictionary;
import dataStructures.priorityQueue.PriorityQueue;
import dataStructures.priorityQueue.LinkedPriorityQueue;
import dataStructures.set.Set;
import dataStructures.set.HashSet;
import dataStructures.tuple.Tuple2;

public class Kruskal {
	public static <V,W> Set<WeightedEdge<V,W>> kruskal(WeightedGraph<V,W> g) {

		Set<WeightedEdge<V,W>> set = new HashSet<>();

		Dictionary<V,V> d = new HashDictionary<>();

		for(V v : g.vertices()){
			d.insert(v,v);
		}

		LinkedPriorityQueue<WeightedEdge<V,W>> cola = new LinkedPriorityQueue<>();

		for(WeightedEdge<V, W> aristas : g.edges()){
			cola.enqueue(aristas);
		}
		while(!cola.isEmpty()){
			WeightedEdge<V,W> arista = cola.first();
			cola.dequeue();
			if(!(representante(arista.source(), d).equals(representante(arista.destination(), d)))){
				d.insert(representante(arista.destination(),d), arista.source());
				set.insert(arista);
			}
		}
		return set;
	}

	static <V> V representante(V src, Dictionary<V,V> dic ){

		if(src.equals(dic.valueOf(src))){
			return src;
		}else{
			return representante(dic.valueOf(src),dic);
		}
	}

	// Sólo para evaluación continua / only for part time students
	public static <V,W> Set<Set<WeightedEdge<V,W>>> kruskals(WeightedGraph<V,W> g) {

		// COMPLETAR
		
		return null;
	}
}
