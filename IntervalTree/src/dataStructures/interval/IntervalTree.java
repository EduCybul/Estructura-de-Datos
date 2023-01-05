package dataStructures.interval;

import dataStructures.list.LinkedList;
import dataStructures.list.List;

import java.util.ArrayList;

public class IntervalTree {

    private static class Node {
        Interval interval;
        int max;
        Node lt, rt;

        /* (1.25 ptos) Constructor de Node
         */
        public Node(Interval interval, Node lt, Node rt) {
            this.interval = interval;
            this.lt = lt;
            this.rt = rt;
            this.max = interval.high;

        }

        public Node(Interval interval) {
            this(interval, null, null);
        }
    }

    /* Atributos de IntervalTree */
    Node root;  //raíz del árbol
    int size;   //número de intervalos almacenados

    /* (0.75 pto) Constructor del árbol de intervalo vacío
     */
    public IntervalTree() {
        this.size = 0;
        this.root = null;
    }

    /* (0.5 ptos) Devuelve true si el árbol de intervalo está vacío
     */
    public boolean isEmpty() {
        return this.root == null;
    }

    /* (0.5 ptos) Devuelve el número de intervalos almacenados en el árbol de intervalo
     */
    public int size() {
        return this.size;
    }


    /* (2 ptos) Inserta el intervalo x en el árbol de intervalos.
        El árbol de intrevalo resultante debe mantener la propiedad de orden.
        Es similar a un BST: los nodos del árbol están están ordenados
        por el valor inferior del intervalo que almacenan.
        Si existe un nodo cuyo intervalo tiene el mismo valor inferior que x,
        actualiza el límite superior del intervalo almacenado en el árbol.
     */
    public void insert(Interval x) {
        root = insertRec(root, x.low, x.high, x);
    }

    private Node insertRec(Node node, int inf, int sup, Interval x) {
        if (node == null) {
            node = new Node(x);
            node.max = x.high;
            size++;
        } else if (inf == node.interval.low) {
            node.interval.high = sup;
            node.max = sup;
        } else if (inf < node.interval.low) {
            node.lt = insertRec(node.lt, inf, sup, x);
            if (node.lt.max > node.max) {
                node.max = node.lt.max;
            }
        } else if (inf > node.interval.low) {
            node.rt = insertRec(node.rt, inf, sup, x);
            if (node.rt.max > node.max) {
                node.max = node.rt.max;
            }
        }
        return node;
    }


    /* (0.5 ptos) Devuelve true si se satisface la condición 1:
       Condición C1: el nodo n no es nulo y su atributo max
                     es mayor o igual que el límite inferior del intervalo x
     */
    private boolean condicionC1(Node n, Interval x) {
        return n != null && n.max >= x.low;
    }

    /* (0.5 ptos) Devuelve true si se satisface la condición 2
       Condición C2: el hijo derecho de n no es nulo y el límite inferior de n
                    es menor o igual que el límite superior del intervalo x
     */
    private boolean condicionC2(Node n, Interval x) {
        return n.rt != null && n.interval.low <= x.high;
    }


    /* (2 ptos) Devuelve un intervalo del árbol que solapa con x.
      Debe ser una implementación eficiente que aproveche la propiedad de orden.
      Si no solapa con ningún intervalo del árbol devuelve null.
      En las transparencias 13-16 hay ejemplos de como debe explorarse el árbol.
     */
    public Interval searchOverlappingInterval(Interval x) {
        return searchOverlappingIntervalRec(root, x);
    }

    private Interval searchOverlappingIntervalRec(Node r, Interval x) {
        Interval i =null;
        if (this.condicionC1(r, x)) {
            if (r.interval.overlap(x)) {
                i=  r.interval;
            }else {
                if (r.lt != null) {
                    i = searchOverlappingIntervalRec(r.lt, x);
                }
            }
            if (i == null && this.condicionC2(r, x)) {
                return searchOverlappingIntervalRec(r.rt, x);
            }
        }
        return i;
    }
    /* (2 ptos) Devuelve una lista con todos los intervalos del árbol que solapan con x.
       Debe ser una implementación eficiente que aproveche la propiedad de orden.
       Si no solapa con ninguno devuelve una lista vacía.
       En la transparencia 17 hay un ejemplo.
     */
    public List<Interval> allOverlappingIntervals(Interval x){
        List<Interval> l = new LinkedList<Interval>();
         l=(allOverlappingIntervalsRec(root,x));
        if(root.interval.overlap(x)){

            l.prepend(root.interval);
        }
        return l;
    }
    private List<Interval> allOverlappingIntervalsRec(Node r,Interval x) {
        List<Interval> l = new LinkedList<Interval>();
        if(r.lt!=null) {
            allOverlappingIntervalsRec(r.lt,x);
            Interval i = searchOverlappingIntervalRec(r.lt, x);
            l.append(i);
        }
            if(r.rt!=null) {
                allOverlappingIntervalsRec(r.rt,x);
                Interval d = searchOverlappingIntervalRec(r.rt, x);
                l.append(d);
            }
       /* if(r.rt!=null) {
             l.append((Interval) allOverlappingIntervalsRec(r.rt, x));
        }*/
        return l;
    }
    /* ---------- No modificar esta parte ---------- */
    @Override
    public String toString() {
        return toStringRec(root);
    }

    private String toStringRec(Node root) {
        if(root!=null){
            return toStringRec(root.lt) + root.interval.toString()+"( "+ root.max +" )"+ toStringRec(root.rt);
        }
        return "";
    }
}
