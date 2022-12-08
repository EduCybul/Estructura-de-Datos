/******************************************************************************
 * Student's name:
 * Student's group:
 * Data Structures. Grado en Informática. UMA.
******************************************************************************/

package dataStructures.vector;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SparseVector<T> implements Iterable<T> {

    protected interface Tree<T> {

        T get(int sz, int i);

        Tree<T> set(int sz, int i, T x);
    }

    // Unif Implementation

    protected static class Unif<T> implements Tree<T> {

        private T elem;

        public Unif(T e) {
            elem = e;
        }

        @Override
        public T get(int sz, int i){
            return elem;
        }

        @Override
        public Tree<T> set(int sz, int i, T x) {
                if(x.equals(elem)){return this;}

               if(sz==1){//Caso base de la recursividad
                    return new Unif<T>(x);
                }else {
                   Node<T> t;//Vamos creando el nodo a izq o der segun el size, de forma recursiva.
                   if (i < sz/2) {
                       t = new Node<>(set(sz / 2, i, x), this);
                   } else {
                       t = new Node<>(this, set(sz / 2, i - (sz / 2), x));
                   }
                   return t;
               }
        }

        @Override
        public String toString() {
            return "Unif(" + elem + ")";
        }
    }
    // Node Implementation
    protected static class Node<T> implements Tree<T> {

        private Tree<T> left, right;

        public Node(Tree<T> l, Tree<T> r) {
            left = l;
            right = r;
        }

        @Override
        public T get(int sz, int i) {
            //LLamadas recursivas a izquierda o a derecha hasta llegar a un Unif
            if(i<sz/2){
                return this.left.get(sz/2,i);
            }else{
                return this.right.get(sz/2,i-(sz/2));
            }
        }
        @Override
        public Tree<T> set(int sz, int i, T x) {
                //Llamadas recursivas a izq o a der hasta llegar a un Unif
                if(i<sz/2){
                    left =left.set(sz/2,i,x);
                }else{
                    right =right.set(sz/2,i-(sz/2),x);
                }

                simplify();
                return this;
        }
        protected Tree<T> simplify() {
            //En el caso que izq y der sean Unif y tengan ambos size 1 y mismo elemento devuelvo el izq como Unif solo.
            if(left instanceof Unif<?> && right instanceof Unif<?> && left.get(1,0) == right.get(1,0) ){
                return (Unif<T>) left;
            }
            return this;
        }

        @Override
        public String toString() {return "Node(" + left + ", " + right + ")";}
    }

    // SparseVector Implementation

    private int size;
    private Tree<T> root;

    public SparseVector(int n, T elem) {
        if(n<0){
            throw new VectorException("n no puede ser negativo g");
        }
        //Inicializamos el SparseVector con tam 2^n y como la raiz Unif y el elemento.
        this.size=(int)Math.pow(2,n);
        root=new Unif<T>(elem);
    }
    public int size() {
        return this.size;
    }

    public T get(int i) {
        if(i<0 || i>this.size){
            throw new VectorException("Fuera de rango bro");
        }
        return this.root.get(size,i);
    }
    public void set(int i, T x) {
        if(i<0 || i>this.size){
            throw new VectorException("fuera de rango bro");
        }
        root = root.set(size,i,x);
    }
    @Override
    public Iterator<T> iterator() {return new IteratorSparceVector();}

    //Creamos nueva clase Iterador para el SparceVector
    //que implemente Iterator<T>
    public class IteratorSparceVector implements Iterator<T>{

        //Creamos el indice i y lo incializamos a 0 que es la primera posicion
        int i;
        public IteratorSparceVector(){i=0;}

        //Metodo que va comprobando que habra un elemento en la proxima posicion
        //esto sera True siempre que el indice no supere el size del vector
        @Override
        public boolean hasNext() {
            return i<size;
        }

        //Cada vez que se llame a next iterara una posicion del vector
        @Override
        public T next(){
          if(!hasNext()){
              throw new NoSuchElementException();
          }
          T a = root.get(size,i);
          i++;
          return a;
        }
    }
    @Override
    public String toString() {
        return "SparseVector(" + size + "," + root + ")";
    }
}
