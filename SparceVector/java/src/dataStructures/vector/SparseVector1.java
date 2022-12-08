/******************************************************************************
 * Student's name:
 * Student's group:
 * Data Structures. Grado en Informática. UMA.
******************************************************************************/

package dataStructures.vector;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SparseVector1<T> implements Iterable<T> {

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
        public T get(int sz, int i){return elem;}

        @Override
        public Tree<T> set(int sz, int i, T x) {

            if(elem.equals(x)){return this;};//Si el elemento ya estaba en el vector no lo volvemos
                                            //si no que es esta misma hoja
            if(sz==1){//Si el size llega a ser uno devolvemos la hoja del indice indicado
                return new Unif<T>(x);
            }else{
                Node<T> t;
                if(i<sz/2){//Si i es menor que el tamaño/2 seguimos recursivamnete mirando por la izquierda
                    t= new Node<>(set(sz/2,i,x), this);
                }else{//Si es mayor tiramos por la derecha actualizando el nuevo valor de i
                    t= new Node<>( this, set(sz/2,i -(sz/2),x ));
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
            if(i< sz/2){
                return left.get(sz/2,i);
            }else{
                return right.get(sz/2,i-(sz/2));
            }

        }

        @Override
        public Tree<T> set(int sz, int i, T x) {
            if(i<sz/2){
                 this.left = left.set(sz/2,i,x);
            }else{
                this.right =right.set(sz/2,i-(sz/2),x);
            }
            simplify();//Unir el lado derecho o izquierdo nuevo
            return this;//Dependiendo de hacia donde haya tirado la recursividad devolverá left o right
        }
        protected Tree<T> simplify() {
            //Si izq y der son instancias del nodo hoja y el tam de izq y tam de der son 1
            //Entonces devuelvo solo izquierda parseado como nodo hoja, porq estariamos hablando del mismo numero
            //esto hace que se mantenga el invariante
            if(left instanceof Unif<?> && right instanceof Unif<?> && left.get(1,0) == right.get(1,0)){
                return (Unif<T>) left;
            }
        return this;//Devuelvo el arbol, solo que habiendo pasado por el if o no.
        }

        @Override
        public String toString() {return "Node(" + left + ", " + right + ")";}
    }

    // SparseVector Implementation

    private int size;
    private Tree<T> root;

    public SparseVector1(int n, T elem) {
        if(n<0){
            throw new VectorException("n no puede ser negativo bro");
        }
        size = (int) Math.pow(2,n);//Inicializamos el vector con tamaño 2^n
        root = new Unif<>(elem);//Inicializamos la raiz como un nodo hoja y el elemento dentro

    }

    public int size() {
        return size;
    }

    public T get(int i) {
        if(i<0  && i>size ){
            throw new VectorException("El indice esta fuera de los limites bro");
        }
        //llamamos al metodo get de Tree<> que puede ser un Node o puede ser Unif.
        //autoamticamente en la llamada va a saber de quien usar el get
        return root.get(size,i);
    }

    public void set(int i, T x) {
        if(i<0 && i>size){
            throw new VectorException("El indice esta fuera de los limites bro");
        }
        root.set(size,i,x);

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
