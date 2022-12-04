/******************************************************************************
 * Student's name:
 * Student's group:
 * Data Structures. Grado en Informática. UMA.
******************************************************************************/

package dataStructures.vector;

import dataStructures.list.LinkedList;
import dataStructures.list.List;

public class TreeVector<T> {

    private final int size;
    private final Tree<T> root ;

    private interface Tree<E> {
        E get(int index);

        void set(int index, E x);

        List<E> toList();
    }

    private static class Leaf<E> implements Tree<E> {
        private E value;

        public Leaf(E x) {
            value = x;
        }

        @Override
        public E get(int i) {return this.value;}
        @Override
        public void set(int i, E x) {this.value=x;}

        @Override
        public List<E> toList() {
            List<E> l = new LinkedList<E>();
            l.append(value);
            return l;
        }
    }

    private static class Node<E> implements Tree<E> {
        private Tree<E> left;
        private Tree<E> right;

        public Node(Tree<E> l, Tree<E> r) {
            left = l;
            right = r;
        }
        @Override
        public E get(int i) {
            int j =i/2;
        	if(i%2==0){
               return left.get(j);
            }else{
                return right.get(j);
            }
        }
        @Override
        public void set(int i, E x) {
            int j =i/2;
        	if(i%2==0){
                left.set(j,x);
            }else{
                right.set(j,x);
            }
        }

        @Override
        public List<E> toList() {return null;}
    }

    public TreeVector(int n, T value) {
        if (n < 0) {
            throw new VectorException("n no puede ser negativo");
        }
        this.size = (int) Math.pow(2, n);

        this.root=new Leaf<T>(value);


    }
    public int size() {
        return this.size;
    }

    public T get(int i) {
        if(i < 0 || i > size-1){
            throw new VectorException("Fuera de limites bro");
        }
        return this.root.get(i);//Cogera el get de la hoja o de una rama interna
    }

    public void set(int i, T x) {
        if(i < 0 || i > size-1){
            throw new VectorException("Fuera de limites bro");
        }
        this.root.set(i,x);
    }

    public List<T> toList() {
        return null;
    }

    protected static <E> List<E> intercalate(List<E> xs, List<E> ys) {
    	//to do
        return null;
    }

    static protected boolean isPowerOfTwo(int n) {
    	//to do
        return false;
    }

    public static <E> TreeVector<E> fromList(List<E> l) {
    	//to do
        return null;
    }
}
