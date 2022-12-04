-------------------------------------------------------------------------------
-- Student's name:
-- Student's group:
--
-- Data Structures. November 2022. Grado en Informática. UMA.
-------------------------------------------------------------------------------

module TreeVector( Vector
                 , vector
                 , size
                 , get
                 , set
                 , mapVector
                 , toList
                 , fromList
                 , pretty
                 ) where

import Test.QuickCheck hiding (vector)

data Vector a = TreeVector Int (BinTree a) -- size, binary tree
                deriving Show

data BinTree a = Leaf a
               | Node (BinTree a) (BinTree a)
               deriving Show


-- | Exercise a. vector

{- |

>>> pretty $ vector 2 'a'
    _______
   /       \
   _       _
  / \     / \
'a' 'a' 'a' 'a'

>>> pretty $ vector 0 7
7

>>> pretty $ vector (-1) 5
*** Exception: vector: negative exponent
...

-}
vector :: Int -> a -> Vector a 
vector n x
      |n < 0 = error "n no puede ser negativo"
   -- |n == 0 = TreeVector ((^) 2 n) (Leaf x)
      |otherwise = TreeVector ((^) 2 n)  (aux (n) x)
    where 
       aux :: Int -> a -> BinTree a
       aux 0 x = Leaf x
       aux n x = Node (aux (n-1) x) (aux (n-1) x)


-- | Exercise b. size

{- |

>>> size $ vector 3 'a'
8

>>> size $ vector 0 5
1

-}
size :: Vector a -> Int
size  (TreeVector n (b)) = n 

-- | Exercise c. get

{- |

>>> get 0 (vector 3 'a')
'a'

>>> get 5 (vector 3 'a')
'a'

>>> get 7 (vector 3 'a')
'a'

>>> get 8 (vector 3 'a')
*** Exception: get: index out of bounds
...

-}
get :: Int -> Vector a -> a
get i (TreeVector n b) 
          | i < 0 || i> n-1 = error "Index out of bounds"
          | otherwise = aux i (b)
    where 
       aux:: Int -> BinTree a -> a
       aux i (Leaf a) = a
       aux i (Node tr tl) 
               |mod i 2 == 0 =aux (div i 2) tl
               |otherwise = aux (div i 2) tr
          
      
       
-- | Exercise d. set

{- |

>>> pretty (set 0 'b' (vector 2 'a'))
    _______
   /       \
   _       _
  / \     / \
'b' 'a' 'a' 'a'

>>> pretty (set 1 'b' (vector 2 'a'))
    _______
   /       \
   _       _
  / \     / \
'a' 'a' 'b' 'a'

>>> pretty (set 3 'b' (vector 2 'a'))
    _______
   /       \
   _       _
  / \     / \
'a' 'a' 'a' 'b'

> pretty $ foldr (\ (i, x) v -> set i x v) (vector 3 '*') (zip [0..] "function")
        _______________
       /               \
    _______         _______
   /       \       /       \
   _       _       _       _
  / \     / \     / \     / \
'f' 't' 'n' 'o' 'u' 'i' 'c' 'n'

>>> pretty (set 4 'b' (vector 2 'a'))
*** Exception: set: index out of bounds
...

-}
set :: Int -> a -> Vector a -> Vector a
set ind x (TreeVector s b) 
              | ind < 0 || ind > s-1 = error "Index out of bounds"
              | otherwise = TreeVector s (aux x ind b)
      where
        aux :: a ->Int -> BinTree a -> BinTree a
        aux x ind (Leaf a)   = Leaf x
        aux x ind (Node tl tr) 
                  | mod ind 2 == 0 = Node  (aux x (div ind 2) tl)   (tr)
                  | otherwise = Node  (tl)  (aux x (div ind 2) tr)

-- | Exercise e. mapVector

{- |

>>> pretty $ mapVector (*2) (vector 2 3)
  ___
 /   \
 _   _
/ \ / \
6 6 6 6

>>> pretty $ mapVector (\ x -> 2*x + 5) (foldr (\ (i, x) v -> set i x v) (vector 3 0) (zip [0..] [4, 0, 9, 2, 0, 1, 7, -3]))
     _________
    /         \
   ____      ___
  /    \    /   \
  _    _    _   _
 / \  / \  / \ / \
13 5 23 19 5 7 9 -1

-}
mapVector :: (a -> a) -> Vector a -> Vector a
mapVector f (TreeVector s (Node lt rt)) = TreeVector s (Node (aux f lt) (aux f rt))
          where 
              aux :: (a->a) -> BinTree a ->BinTree a
              aux f (Leaf a) = Leaf (f a)
              aux f (Node lt rt) = Node (aux f lt) (aux f rt) 


-- | Exercise f. intercalate

{- |

>>> intercalate [0,1,2,3] [4,5,6,7]
[0,4,1,5,2,6,3,7]

>>> intercalate "haskell" "java"
"hjaasvka"

>>> intercalate [] "java"
""

-}
intercalate :: [a] -> [a] -> [a]
intercalate xs [] = []
intercalate [] ys = []
intercalate (x:xs) (y:ys)  = x:y:(intercalate xs ys)

-- | Exercise g. toList

{- |

>>> toList (vector 3 'a')
(Error while loading modules for evaluation)
[1 of 1] Compiling TreeVector       ( C:\Users\edu-a\OneDrive - Universidad de Mlaga\ED DEFINITIVO\2023\Estructura-de-Datos\Tree Vector\haskell\TreeVector.hs, interpreted )
<BLANKLINE>
C:\Users\edu-a\OneDrive - Universidad de Mlaga\ED DEFINITIVO\2023\Estructura-de-Datos\Tree Vector\haskell\TreeVector.hs:51:4-45: error:
    parse error on input `-- |n == 0 = TreeVector ((^) 2 n) (Leaf x)'
Failed, no modules loaded.

>>> toList $ foldr (\ (i, x) v -> set i x v) (vector 3 '*') (zip [0..] "function")
(Error while loading modules for evaluation)
[1 of 1] Compiling TreeVector       ( C:\Users\edu-a\OneDrive - Universidad de Mlaga\ED DEFINITIVO\2023\Estructura-de-Datos\Tree Vector\haskell\TreeVector.hs, interpreted )
<BLANKLINE>
C:\Users\edu-a\OneDrive - Universidad de Mlaga\ED DEFINITIVO\2023\Estructura-de-Datos\Tree Vector\haskell\TreeVector.hs:51:4-45: error:
    parse error on input `-- |n == 0 = TreeVector ((^) 2 n) (Leaf x)'
Failed, no modules loaded.

-}
toList :: Vector a -> [a] 
toList (TreeVector s (Node tl tr)) = intercalate (aux tl) (aux tr) 
    where
        aux :: BinTree a -> [a] 
        aux (Leaf a)= [a]
        aux (Node tl tr)  = (aux tl) ++ (aux tr)


-- | Exercise h. Complexity

-- Fill the table below:
--
--    operation        complexity class
--    ---------------------------------
--    vector
--    ---------------------------------
--    size
--    ---------------------------------
--    get
--    ---------------------------------
--    set
--    ---------------------------------
--    mapVector
--    ---------------------------------
--    intercalate
--    ---------------------------------
--    toList
--    ---------------------------------


-- | Exercise i. isPowerOfTwo

{- |

>>> isPowerOfTwo 16
True

>>> isPowerOfTwo 17
False

-}
isPowerOfTwo :: Int -> Bool 
isPowerOfTwo n 
            | n < 0 = error "n no puede ser negativo"
            | mod n 2== 0 = True
            | otherwise = False    

-- | Exercise j. fromList

{- |

>>> pretty $ fromList [0..7]
    _______
   /       \
  ___     ___
 /   \   /   \
 _   _   _   _
/ \ / \ / \ / \
0 4 2 6 1 5 3 7

>>> pretty $ fromList "property"
(Error while loading modules for evaluation)
[1 of 1] Compiling TreeVector       ( C:\Users\edu-a\OneDrive - Universidad de Mlaga\ED DEFINITIVO\2023\Estructura-de-Datos\Tree Vector\haskell\TreeVector.hs, interpreted )
<BLANKLINE>
C:\Users\edu-a\OneDrive - Universidad de Mlaga\ED DEFINITIVO\2023\Estructura-de-Datos\Tree Vector\haskell\TreeVector.hs:51:4-45: error:
    parse error on input `-- |n == 0 = TreeVector ((^) 2 n) (Leaf x)'
Failed, no modules loaded.

-}
fromList :: [a] -> Vector a
fromList xs
          |isPowerOfTwo (long xs) = TreeVector (long xs) (aux xs) 
          |otherwise = error "no es potencia de dos"  
      where
        long :: [a]->Int --Longitud del vector para ver si es potencia de dos
        long [] = 0
        long (x:xs) = 1 + long xs
        aux :: [a] -> BinTree a
        aux [x]  = (Leaf x)
        aux (x:y:xs) = Node (Leaf x) (Leaf y)

-------------------------------------------------------------------------------
-- Do not write any code below
-------------------------------------------------------------------------------

-------------------------------------------------------------------------------
-- QuickCheck Properties to check your implementation
-------------------------------------------------------------------------------

{-

> checkAll
+++ OK, passed 100 tests.
+++ OK, passed 100 tests.

> checkAll
*** Gave up! Passed only 99 tests.
+++ OK, passed 100 tests.

-}

-- This instace is used by QuickCheck to generate random arrays
instance (Arbitrary a) => Arbitrary (Vector a) where
    arbitrary  = do
      exp <- arbitrary
      v <- arbitrary
      return ((vector :: Int -> a -> Vector a) (abs exp) v)

prop_vector_OK :: Eq a => Int -> a -> Property
prop_vector_OK n v =
  n >= 0 && n < 10 ==> size a == 2^n &&
                       and [ get i a == v | i <- [0..size a-1] ]
  where a = vector n v

prop_set_OK :: Eq a => Int -> a -> Vector a -> Property
prop_set_OK i v a =
  i >= 0 && i < size a ==> get i (set i v a) == v

type T = Char

checkAll :: IO ()
checkAll =
  do
    quickCheck (prop_vector_OK :: Int -> T -> Property)
    quickCheck (prop_set_OK :: Int -> T -> Vector T -> Property)

-------------------------------------------------------------------------------
-- Pretty Printing a Vector
-- (adapted from http://stackoverflow.com/questions/1733311/pretty-print-a-tree)
-------------------------------------------------------------------------------

pretty :: (Show a) => Vector a -> IO ()
pretty (TreeVector _ t)  = putStr (unlines xss)
 where
   (xss,_,_,_) = pprint t

pprint :: Show t => BinTree t -> ([String], Int, Int, Int)
pprint (Leaf x)              = ([s], ls, 0, ls-1)
  where
    s = show x
    ls = length s
pprint (Node lt rt)         =  (resultLines, w, lw'-swl, totLW+1+swr)
  where
    nSpaces n = replicate n ' '
    nBars n = replicate n '_'
    -- compute info for string of this node's data
    s = ""
    sw = length s
    swl = div sw 2
    swr = div (sw-1) 2
    (lp,lw,_,lc) = pprint lt
    (rp,rw,rc,_) = pprint rt
    -- recurse
    (lw',lb) = if lw==0 then (1," ") else (lw,"/")
    (rw',rb) = if rw==0 then (1," ") else (rw,"\\")
    -- compute full width of this tree
    totLW = maximum [lw', swl,  1]
    totRW = maximum [rw', swr, 1]
    w = totLW + 1 + totRW
{-
A suggestive example:
     dddd | d | dddd__
        / |   |       \
      lll |   |       rr
          |   |      ...
          |   | rrrrrrrrrrr
     ----       ----           swl, swr (left/right string width (of this node) before any padding)
      ---       -----------    lw, rw   (left/right width (of subtree) before any padding)
     ----                      totLW
                -----------    totRW
     ----   -   -----------    w (total width)
-}
    -- get right column info that accounts for left side
    rc2 = totLW + 1 + rc
    -- make left and right tree same height
    llp = length lp
    lrp = length rp
    lp' = if llp < lrp then lp ++ replicate (lrp - llp) "" else lp
    rp' = if lrp < llp then rp ++ replicate (llp - lrp) "" else rp
    -- widen left and right trees if necessary (in case parent node is wider, and also to fix the 'added height')
    lp'' = map (\s -> if length s < totLW then nSpaces (totLW - length s) ++ s else s) lp'
    rp'' = map (\s -> if length s < totRW then s ++ nSpaces (totRW - length s) else s) rp'
    -- first part of line1
    line1 = if swl < lw' - lc - 1 then
                nSpaces (lc + 1) ++ nBars (lw' - lc - swl) ++ s
            else
                nSpaces (totLW - swl) ++ s
    -- line1 right bars
    lline1 = length line1
    line1' = if rc2 > lline1 then
                line1 ++ nBars (rc2 - lline1)
             else
                line1
    -- line1 right padding
    line1'' = line1' ++ nSpaces (w - length line1')
    -- first part of line2
    line2 = nSpaces (totLW - lw' + lc) ++ lb
    -- pad rest of left half
    line2' = line2 ++ nSpaces (totLW - length line2)
    -- add right content
    line2'' = line2' ++ " " ++ nSpaces rc ++ rb
    -- add right padding
    line2''' = line2'' ++ nSpaces (w - length line2'')
    resultLines = line1'' : line2''' : zipWith (\lt rt -> lt ++ " " ++ rt) lp'' rp''
