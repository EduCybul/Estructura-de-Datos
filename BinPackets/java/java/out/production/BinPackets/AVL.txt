/**
 * Student's name:Eduard Cybulkiewicz
 *
 * Student's group:INF C
 */
import dataStructures.list.List;
import dataStructures.list.LinkedList;
import java.util.Iterator;

class Bin {
    private int remainingCapacity; // capacity left for this bin
    private final List<Integer> weights; // weights of objects included in this bin
    public Bin(int initialCapacity) {
        this.remainingCapacity=initialCapacity;
        weights =new LinkedList<>();
    }
    // returns capacity left for this bin
    public int remainingCapacity() {
        return this.remainingCapacity;
    }
    // adds a new object to this bin
    public void addObject(int weight) {
        if(weight> this.remainingCapacity){
            throw new RuntimeException("El objeto no cabe");
        }
        remainingCapacity -= weight;//reducimos la capacidad restante.
        weights.append(weight);//lo introducimos en la lista de pesos.
    }
    // returns an iterable through weights of objects included in this bin
    public Iterable<Integer> objects() {
        // todo
        //  SOLO PARA ALUMNOS SIN EVALUACION CONTINUA
        //  ONLY FOR STUDENTS WITHOUT CONTINUOUS ASSESSMENT
        return null;
    }

    public String toString() {
        String className = getClass().getSimpleName();
        StringBuilder sb = new StringBuilder(className);
        sb.append("(");
        sb.append(remainingCapacity);
        sb.append(", ");
        sb.append(weights.toString());
        sb.append(")");
        return sb.toString();
    }
}

// Class for representing an AVL tree of bins
public class AVL {
    static private class Node {
        Bin bin; // Bin stored in this node
        int height; // height of this node in AVL tree
        int maxRemainingCapacity; // max capacity left among all bins in tree rooted at this node
        Node left, right; // left and right children of this node in AVL tree
        // recomputes height of this node
        void setHeight() {
            height=Math.max(height(left),height(right))+1;
        }
        // recomputes max capacity among bins in tree rooted at this node
        void setMaxRemainingCapacity() {
            maxRemainingCapacity = Math.max(bin.remainingCapacity(),Math.max(maxRemainingCapacity(right),maxRemainingCapacity(left)));
        }
        // left-rotates this node. Returns root of resulting rotated tree
        Node rotateLeft() {
            Node rt = this.right;
            Node rlt = rt.left;
            this.right = rlt;
            setHeight();
            setMaxRemainingCapacity();
            rt.left=this;
            rt.setHeight();
            rt.setMaxRemainingCapacity();
            return rt;
        }
    }
    private static int height(Node node) {
        if(node!=null){
            return node.height;
        }else{
            return 0;
        }
    }
    private static int maxRemainingCapacity(Node node) {
        if(node!=null){
            return node.maxRemainingCapacity;
        }else{
            return 0;
        }
    }
    private Node root; // root of AVL tree

    public AVL() {
        this.root = null;
    }

    // adds a new bin at the end of right spine.
    private void addNewBin(Bin bin) {
        Node aux = new Node();
        aux.height=1;
        aux.bin=bin;
        aux.maxRemainingCapacity=bin.remainingCapacity();
        aux.left=null;
        aux.right=null;
        root=addNewBinRec(root,aux);
    }
    private Node addNewBinRec(Node r , Node aux){
        if(r != null){//mientras no sea null vamos mirando recursivamente a la derecha
            r.right=addNewBinRec(r.right,aux);
            r.setHeight();
            r.setMaxRemainingCapacity();
            if(height(r.right)-height(r.left)>1){
                r=r.rotateLeft();
            }
        }else{//el nodo raiz esta vacio o hemos llegado al final
            r=aux;
        }
        return r;
    }

    // adds an object to first suitable bin. Adds
    // a new bin if object cannot be inserted in any existing bin
    public void addFirst(int initialCapacity, int weight) {
        insertarRec(initialCapacity,weight,this.root);
    }
    public void insertarRec(int initCap, int weight, Node r){
        if(r == null || maxRemainingCapacity(r)<weight){//Esta vacio o no cabe en ningun cubo
            Bin b = new Bin(initCap);
            b.addObject(weight);
            addNewBin(b);//lo añadira al final de la espina derecha
        }else if(maxRemainingCapacity(r.left)>=weight){//si el peso restante del nodo izq es mayor que el peso
                        insertarRec(initCap,weight,r.left);//se añade en el primer sitio posible del nodo izquierdo
        }else if( r.bin.remainingCapacity()>=weight){//capacidad restante del cubo del nodo raiz
            r.bin.addObject(weight);// se añade el objeto al cubo raiz
            r.setMaxRemainingCapacity();
        }else{
            insertarRec(initCap,weight,r.right);
        }
    }
    public void addAll(int initialCapacity, int[] weights) {
        for( int i : weights){
            addFirst(initialCapacity,i);
        }
    }
    public List<Bin> toList() {
        return toListRec(root);
    }

    private List<Bin> toListRec(Node r){
        List<Bin> lista  = new LinkedList<>();
        lista.append(r.bin);
        if(r.right !=null){
            List<Bin>aux = toListRec(r.right);
            for(Bin b : aux){
                lista.append(b);
            }
        }
        if(r.left != null){
            List<Bin> aux = toListRec(r.left);
            for(Bin b : aux){
                lista.append(b);
            }
        }
        return lista;
    }
    public String toString() {
        String className = getClass().getSimpleName();
        StringBuilder sb = new StringBuilder(className);
        sb.append("(");
        stringBuild(sb, root);
        sb.append(")");
        return sb.toString();
    }

    private static void stringBuild(StringBuilder sb, Node node) {
        if(node==null)
            sb.append("null");
        else {
            sb.append(node.getClass().getSimpleName());
            sb.append("(");
            sb.append(node.bin);
            sb.append(", ");
            sb.append(node.height);
            sb.append(", ");
            sb.append(node.maxRemainingCapacity);
            sb.append(", ");
            stringBuild(sb, node.left);
            sb.append(", ");
            stringBuild(sb, node.right);
            sb.append(")");
        }
    }
}

class LinearBinPacking {
    public static List<Bin> linearBinPacking(int initialCapacity, List<Integer> weights) {
        // todo
        //  SOLO PARA ALUMNOS SIN EVALUACION CONTINUA
        //  ONLY FOR STUDENTS WITHOUT CONTINUOUS ASSESSMENT
        return null;
    }
	
	public static Iterable<Integer> allWeights(Iterable<Bin> bins) {
        // todo
        //  SOLO PARA ALUMNOS SIN EVALUACION CONTINUA
        //  ONLY FOR STUDENTS WITHOUT CONTINUOUS ASSESSMENT
        return null;		
	}
}