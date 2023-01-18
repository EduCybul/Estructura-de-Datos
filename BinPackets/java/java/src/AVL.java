/**
 * Student's name:
 *
 * Student's group:
 */

import dataStructures.list.ArrayList;
import dataStructures.list.List;
import dataStructures.list.LinkedList;
import dataStructures.list.ListException;

import java.util.Iterator;


class Bin {
    private int remainingCapacity; // capacity left for this bin
    private List<Integer> weights; // weights of objects included in this bin

    public Bin(int initialCapacity) {
        this.remainingCapacity=initialCapacity;
        this.weights=new LinkedList<Integer>();
    }

    // returns capacity left for this bin
    public int remainingCapacity() {
        return this.remainingCapacity;
    }
    // adds a new object to this bin
    public void addObject(int weight) {
        if(this.remainingCapacity-weight < 0){//Si el objeto no cabe en el cubo
            throw new ListException("el objeto no cabe");
        };
        weights.append(weight);//lo añadimos a la lista de objetos
        this.remainingCapacity=this.remainingCapacity-weight;
    }

    // returns an iterable through weights of objects included in this bin
    public Iterable<Integer> objects() {
        // todo
        //  SOLO PARA ALUMNOS SIN EVALUACION CONTINUA
        //  ONLY FOR STUDENTS WITHOUT CONTINUOUS ASSESSMENT
        /*Iterable<Integer> it = new Iterable<Integer>() {
            @Override
            public Iterator<Integer> iterator() {
                return null;
            }
        }*/


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
    static class Node {
        Bin bin; // Bin stored in this node
        int height; // height of this node in AVL tree
        int maxRemainingCapacity; // max capacity left among all bins in tree rooted at this node
        Node left, right; // left and right children of this node in AVL tree

        // recomputes height of this node
        void setHeight() {
            if(this.left!=null && this.right!=null){
                this.height= 1+ Math.max(this.left.height,this.right.height);
            }else if(this.left==null){
                this.height =1+this.right.height;

            }else if(this.right==null){
                this.height= 1+this.left.height;
            }
        }

        // recomputes max capacity among bins in tree rooted at this node
        void setMaxRemainingCapacity() {
            if(this.left!=null && this.right!=null){
                maxRemainingCapacity=Math.max(Math.max(left.maxRemainingCapacity,right.maxRemainingCapacity),maxRemainingCapacity);
            }else if(this.left==null){
                maxRemainingCapacity=Math.max(maxRemainingCapacity,this.right.maxRemainingCapacity);
            }else if(this.right==null){
                maxRemainingCapacity=Math.max(maxRemainingCapacity,this.left.maxRemainingCapacity);
            }

        }

        // left-rotates this node. Returns root of resulting rotated tree
        Node rotateLeft() {

            Node aux =this.right;
            this.right=this.right.left;
            this.setHeight();
            this.setMaxRemainingCapacity();
            aux.left=this;
            aux.setHeight();
            aux.setMaxRemainingCapacity();
            return aux;
        }

    }

    private static int height(Node node) {
        if(node!=null){
            return node.height;
        }
        return 0;
    }

    private static int maxRemainingCapacity(Node node) {
        if(node!=null) {
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
        Node aux= new Node();
        aux.bin=bin;
        aux.height=1;
        aux.maxRemainingCapacity=bin.remainingCapacity();
        aux.left=null;
        aux.right=null;
        root=addNewBinRec(root,aux);

    }

    private Node addNewBinRec(Node root, Node aux){
        if(root != null){//Mientras no sea null recursivamente vamos mirando en la derecha
            root.right= addNewBinRec(root.right,aux);
            root.setHeight();
            root.setMaxRemainingCapacity();
            if(root.left!=null &&root.right!=null) {
                if (height(root.right) - height(root.left) > 1) {
                    root = root.rotateLeft();
                }
            }
        }else{//Cuando llegamos al final derecho del arbol AVL
            root=aux;
        }
        return root;
    }
    // adds an object to first suitable bin. Adds
    // a new bin if object cannot be inserted in any existing bin
    public void addFirst(int initialCapacity, int weight) {
            insertarRec(initialCapacity,weight, root);
    }
    public void insertarRec(int initialCapacity, int weight, Node root){
        if(root==null || maxRemainingCapacity(root)<weight ) {
            Bin b = new Bin(initialCapacity);
            b.addObject(weight);
            addNewBin(b);
        }else if(root.left!=null && maxRemainingCapacity(root.left)>=weight){
                insertarRec(initialCapacity,weight, root.left);
        }else if(maxRemainingCapacity(root)>=weight){
                root.bin.addObject(weight);
                root.setMaxRemainingCapacity();
        }else{
            insertarRec(initialCapacity,weight,root.right);
        }
    }

    public void addAll(int initialCapacity, int[] weights) {
        for (int i : weights){
            addFirst(initialCapacity,i);
        }
    }

    public List<Bin> toList() {
        return toListRec(root);
    }
    public List<Bin> toListRec(Node root){
        List<Bin> l = new ArrayList<>();
        l.append(root.bin);
        if(root.right !=null){
           List<Bin> aux=toListRec(root.right);
            for(Bin t : aux){
                l.append(t);
            }
        }

        if(root.left != null){
            List<Bin> aux =toListRec(root.left);
            for(Bin t : aux){
                l.append(t);
            }
        }
        return l;
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