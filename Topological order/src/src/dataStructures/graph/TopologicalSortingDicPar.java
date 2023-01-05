/**
 * APELLIDOS : ..........................  NOMBRE: ............
 *
 * TITULACION: ..............................
 *
 * Computes Topological Sorting for DiGraphs
 */

package dataStructures.graph;

import dataStructures.dictionary.Dictionary;
import dataStructures.dictionary.HashDictionary;
import dataStructures.list.List;
import dataStructures.list.ArrayList;
import dataStructures.set.Set;
import dataStructures.set.HashSet;

public class TopologicalSortingDicPar<V> {

    private List<Set<V>> topSort;
    private boolean hasCycle;

    public TopologicalSortingDicPar(DiGraph<V> graph) {

        topSort = new ArrayList<>();
        // dictionary: vertex -> # of pending predecessors
        Dictionary<V, Integer> pendingPredecessors = new HashDictionary<>();

        // completar
    }

    public boolean hasCycle() {
        return hasCycle;
    }

    public List<Set<V>> order() {
        return hasCycle ? null : topSort;
    }

}
