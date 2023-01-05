----------------------------------------------
-- Estructuras de Datos.  2018/19
-- 2º Curso del Grado en Ingeniería [Informática | del Software | de Computadores].
-- Escuela Técnica Superior de Ingeniería en Informática. UMA
--
-- Examen 4 de febrero de 2019
--
-- ALUMNO/NAME:
-- GRADO/STUDIES:
-- NÚM. MÁQUINA/MACHINE NUMBER:
--
----------------------------------------------

module Kruskal(kruskal, kruskals) where

import qualified DataStructures.Dictionary.AVLDictionary as D
import qualified DataStructures.PriorityQueue.LinearPriorityQueue as Q
import DataStructures.Graph.DictionaryWeightedGraph 

kruskal :: (Ord a, Ord w) => WeightedGraph a w -> [WeightedEdge a w]
kruskal a = aux cola' dic' [] 
    where
        cola' = foldr (Q.enqueue) (Q.empty) (edges a) 
        dic'  =   foldr (\v d ->D.insert v v d ) (D.empty) (vertices a)
        
        aux cola dic lista
            |Q.isEmpty cola = lista
            |r1 == r2  = aux (Q.dequeue cola) dic lista
            |otherwise = aux (Q.dequeue cola) (D.insert r2 v1 dic) (we:lista)
            where
                r1 = representante v1 dic
                r2 = representante v2 dic
                we@(WE v1 peso v2) = Q.first cola


representante :: (Ord a) => a->D.Dictionary a a -> a
representante v dic
                |w==v = v
                |otherwise = representante w dic 
            where 
                Just w = D.valueOf v dic


-- Solo para evaluación continua / only for part time students
kruskals :: (Ord a, Ord w) => WeightedGraph a w -> [[WeightedEdge a w]]
kruskals = undefined
