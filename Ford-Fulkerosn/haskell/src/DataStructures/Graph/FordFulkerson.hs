-------------------------------------------------------------------------------
-- Ford-Fulkerson Algorithm. Maximal flow for a weighted directed graph.
--
-- Student's name:Eduard Cybulkiewicz
-- Student's group:Inf C
--
-- Data Structures. Grado en Informática. UMA.
-------------------------------------------------------------------------------

module DataStructures.Graph.FordFulkerson where

import Data.List  ((\\))
import DataStructures.Graph.WeightedDiGraph
import DataStructures.Graph.WeightedDiGraphBFT

maxFlowPath :: Path (WDiEdge a Integer) -> Integer
maxFlowPath [] = 0
maxFlowPath path = minimum [peso |(E a peso b)  <- path]--Vamos cogiendo de la lista path solo los pesos


updateEdge ::(Eq a) => a -> a -> Integer -> [WDiEdge a Integer] -> [WDiEdge a Integer]
updateEdge x y p [] = [(E x p y)]
updateEdge x y p ((E src w dst):xs) 
                            |esta && peso==0   = xs 
                            |esta && not(peso==0)   = (E src (w+p) dst):xs
                            |otherwise         = (E src w dst):updateEdge x y p xs
            where
                esta = (x==src && y==dst)
                peso = w+p

updateEdges :: (Eq a) => Path (WDiEdge a Integer) -> Integer -> [WDiEdge a Integer] -> [WDiEdge a Integer]
updateEdges [] _ edges = edges 
updateEdges ((E src' w' dst'):resto) p edges = updateEdges resto p (updateEdge src' dst' p edges)  

addFlow :: (Eq a) => a -> a -> Integer -> [WDiEdge a Integer] -> [WDiEdge a Integer]
addFlow x y p sol@((E src w dst):xs) 
                    |esta = (E src (w+p) dst):xs 
                    |estaReves = xs
                    |estaReves && menor = (E x (p-w) y):xs
                    |estaReves && not(menor) = (E y (w-p) x ):xs
                    |otherwise =(E x p y):sol  
    where 
        esta =( x==src && y==dst)
        estaReves = (x==dst && y==src)
        menor= w<p

addFlows :: (Eq a) => Path (WDiEdge a Integer) -> Integer -> [WDiEdge a Integer] -> [WDiEdge a Integer]
addFlows [] _ edges = edges
addFlows ((E src w dst):xs) p edges = addFlows xs p (addFlow src dst p edges)

fordFulkerson :: (Ord a) => (WeightedDiGraph a Integer) -> a -> a -> [WDiEdge a Integer]
fordFulkerson = undefined

maxFlow :: (Ord a) => [WDiEdge a Integer] -> a -> Integer
maxFlow sol s = sum [w |(E src w dst) <-sol, s==src ]

maxFlowMinCut :: (Ord a) => (WeightedDiGraph a Integer) -> a -> a -> [a] -> Integer
maxFlowMinCut = undefined



-- A partir de aquí hasta el final
-- SOLO para alumnos a tiempo parcial 
-- sin evaluación continua

localEquilibrium :: (Ord a) => WeightedDiGraph a Integer -> a -> a -> Bool
localEquilibrium = undefined

sourcesAndSinks :: (Eq a) => WeightedDiGraph a b -> ([a],[a])
sourcesAndSinks = undefined

unifySourceAndSink :: (Eq a) => WeightedDiGraph a Integer -> a -> a -> WeightedDiGraph a Integer
unifySourceAndSink = undefined
