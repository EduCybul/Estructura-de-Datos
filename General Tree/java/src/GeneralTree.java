package dataStructures.tree;
import dataStructures.list.ArrayList;
import dataStructures.list.LinkedList;
import dataStructures.list.List;
import java.util.Iterator;
public class GeneralTree<T> {
    Node<T> raiz;
    LinkedList<GeneralTree<T>> hijos;
    private static class Node<T> {
        private T elem;
        public Node(T x) {
            elem = x;
        }
    }
    public Node<T> getRaiz(){
        return raiz;
    }
    public LinkedList<GeneralTree<T>> getHijos(){
        return hijos;
    }
    public boolean isEmpty() {
            return raiz == null;
        }

    public static int size(GeneralTree tree){
        return (tree.getRaiz()==null)? 0 : sizeHijos(tree)+1;
    }
    private static int sizeHijos(GeneralTree tree){
        int suma=0;
        if(!tree.isEmpty()) {
            Iterator <GeneralTree> it = tree.getHijos().iterator();
            while (it.hasNext()){
                suma+=size(it.next())+1;
            }
        }
        return suma;
    }
    public static int sum(GeneralTree<Integer> tree){
        int sum =0;
        if(tree!=null){
            sum+=sumRec(tree.getHijos())+tree.getRaiz();
        }
        return sum;
    }
    private static int sumRec(LinkedList<GeneralTree<Integer>> hijos) {
        int suma=0;
        Iterator<GeneralTree<Integer>> it = hijos.iterator();
        while (it.hasNext()){
            suma+=sum(it.next());
        }
        return suma;
    }
    public static List postOrder(GeneralTree tree){
        List postLista = new ArrayList<>();
        if (tree.getRaiz()!=null) {
            postLista.append(tree.getRaiz());
            postHijos(postLista, tree);
        }
        return postLista;
    }
    private static void postHijos(List postLista, GeneralTree tree) {
        Iterator <GeneralTree> it = tree.getHijos().iterator();
        while (it.hasNext()){
            GeneralTree next = it.next();
            postHijos(postLista, next);
            postLista.append(next.getRaiz());
        }
    }
    public static <T> List<T> byLevels(GeneralTree tree){
        List list = new LinkedList<>();
        Queue<GeneralTree<T>> queue= new LinkedQueue<>();
        if (tree!=null){
            list.append(tree.getRaiz());
            Iterator<GeneralTree<T>> it = tree.getHijos().iterator();
            while (it.hasNext()) {
                queue.enqueue(it.next());
            }
            while (!queue.isEmpty()){
                GeneralTree<T> newTree = queue.first();
                list.append(newTree);
                queue.dequeue();
                it = tree.getHijos().iterator();
                while (it.hasNext()) {
                    queue.enqueue(it.next());
                }
            }
        }
        return list;
    }
}