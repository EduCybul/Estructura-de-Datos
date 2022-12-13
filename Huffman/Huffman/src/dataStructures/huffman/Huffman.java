package dataStructures.huffman;
/**
 * Huffman trees and codes.
 *
 * Data Structures, Grado en Informatica. UMA.
 *
 *
 * Student's name: Eduard Cybulkiewicz
 * Student's group: Inf C
 */

import dataStructures.dictionary.AVLDictionary;
import dataStructures.dictionary.Dictionary;
import dataStructures.dictionary.HashDictionary;
import dataStructures.list.ArrayList;
import dataStructures.list.LinkedList;
import dataStructures.list.List;
import dataStructures.priorityQueue.BinaryHeapPriorityQueue;
import dataStructures.priorityQueue.LinkedPriorityQueue;
import dataStructures.priorityQueue.PriorityQueue;
import dataStructures.tuple.Tuple2;
import sun.awt.image.ImageWatched;

public class Huffman {

    // Exercise 1  
    public static Dictionary<Character, Integer> weights(String s) {
        Dictionary<Character, Integer> dic = new AVLDictionary<Character, Integer>();

        char[] c = s.toCharArray();
        for (char i : c) {
            if (dic.isDefinedAt(i)) {
                dic.insert(i, dic.valueOf(i) + 1);
            } else {
                dic.insert(i, 1);
            }

        }
        return dic;
    }

    // Exercise 2.a 
    public static PriorityQueue<WLeafTree<Character>> huffmanLeaves(String s) {
    	PriorityQueue<WLeafTree<Character>> cola = new LinkedPriorityQueue<>();
        Dictionary<Character,Integer> dic = weights(s);
        for(Tuple2<Character,Integer> it : dic.keysValues()){
            WLeafTree<Character> t = new WLeafTree<Character>(it._1(),it._2());
            cola.enqueue(t);
        }
    	        return cola;
    }
    // Exercise 2.b  
    public static WLeafTree<Character> huffmanTree(String s) {
    	// s debe tener dos caracteres distintos. Si no, excepcion
        if(s.length()<2){
            throw new HuffmanException("Caracter muy corto");
        }

        PriorityQueue<WLeafTree<Character>> cola = huffmanLeaves(s);
        WLeafTree<Character> w1 = cola.first();
        cola.dequeue();
        WLeafTree<Character> w2 = cola.first();
        cola.dequeue();
        WLeafTree<Character> w3 = new WLeafTree<Character>(w1,w2);

        while(!cola.isEmpty()){
            w3 = new WLeafTree<Character>(w3,cola.first());
            cola.dequeue();
        }
    	return w3;
    }
    // Exercise 3.a 
    public static Dictionary<Character, List<Integer>> joinDics(Dictionary<Character, List<Integer>> d1, Dictionary<Character, List<Integer>> d2) {
        Dictionary<Character,List<Integer>> dic = new AVLDictionary<Character,List<Integer>>();
        for (Tuple2<Character,List<Integer>> it : d1.keysValues()){
            dic.insert(it._1(),it._2());
        }
        for(Tuple2<Character,List<Integer>> iter : d2.keysValues()){
            dic.insert(iter._1(),iter._2());
        }
    	return dic;
    }
    // Exercise 3.b  
    public static Dictionary<Character, List<Integer>> prefixWith(int i, Dictionary<Character, List<Integer>> d) {

        Dictionary<Character,List<Integer>> dic = new AVLDictionary<Character,List<Integer>>();
        for(Tuple2<Character,List<Integer>> it : d.keysValues()){
            List<Integer> l = it._2();
            l.prepend(i);
            dic.insert(it._1(),l);
        }
    	return dic;
    }

    // Exercise 3.c  
    public static Dictionary<Character, List<Integer>> huffmanCode(WLeafTree<Character> ht) {
        if(ht.isLeaf()){
        Dictionary<Character,List<Integer>> dic = new AVLDictionary<Character, List<Integer>>();
         dic.insert(ht.elem(),new LinkedList<>());
         return dic;
        }else{
        Dictionary<Character,List<Integer>> dic = joinDics(prefixWith(1,huffmanCode(ht.leftChild())),prefixWith(0,huffmanCode(ht.rightChild())));
        return dic;
        }
    }
    // Exercise 4  
    public static List<Integer> encode(String s, Dictionary<Character, List<Integer>> hc) {
        //to do 
    	return null;
    }

    // Exercise 5 
    public static String decode(List<Integer> bits, WLeafTree<Character> ht) {
        //to do 
    	return null;
    }
}
