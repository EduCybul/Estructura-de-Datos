---------------------TEMA_2----------------------------
-------------------------------------------------------

suma:: Num a=> [a] -> a
suma [] =0
suma (x:xs) = x + suma xs


todosTrue :: [Bool] -> Bool
todosTrue [] = True
todosTrue (x:xs) = x && todosTrue xs

sorted :: (Ord a) => [a] ->Bool
sorted [] = True  
sorted [_] =True 
sorted(x:y:zs) = x <= y && sorted (y:zs)

reverse:: [a] -> [a]
reverse xs = revOn xs []
    where
        revOn [] ys     = ys
        revOn (x:xs) ys = revOn xs (x:ys)
        




