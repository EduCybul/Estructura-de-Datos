-------------------------------------------------------------------------------
-- Estructuras de Datos. 2º Curso. ETSI Informática. UMA
--
-- (completa y sustituye los siguientes datos)
-- Titulación: Grado en Ingeniería Informática
-- Alumno: Cybulkiewicz, Eduard
-- Fecha de entrega: 14|10|2020
--
-- Relación de Ejercicios 1. Ejercicios resueltos: .......... 
-- 
-------------------------------------------------------------------------------
import Test.QuickCheck

-------------------------------------------------------------------------------
-- Ejercicio 5
-------------------------------------------------------------------------------


entre :: Ord a => a -> (a, a) -> Bool
entre x (p,q) 
         | x > p && x < q = True
         |otherwise   = False

-------------------------------------------------------------------------------
-- Ejercicio 7
-------------------------------------------------------------------------------

-- Para este ejercicio nos interesa utilizar la función predefinida en Prelude: 
--              divMod :: Integral a => a -> a -> (a, a)
-- que calcula simultáneamente el cociente y el resto:
--
--   *Main> divMod 30 7
--   (4,2)

type TotalSegundos = Integer
type Horas         = Integer
type Minutos       = Integer
type Segundos      = Integer


descomponer :: TotalSegundos -> (Horas,Minutos,Segundos)
descomponer x 
          | x <= 0   = error "No se puede" 
          |otherwise = (horas, minutos, segundos)
   
   where
     (horas,resto)      = divMod x 3600
     (minutos,resto1)   = divMod resto 60
     segundos           = mod resto1 60
     
p_descomponer x = x>=0 ==> h*3600 + m*60 + s == x
                           && m `entre` (0,59)
                           && s `entre` (0,59)
    where (h,m,s) = descomponer x
-- *Main> quickCheck p_descomponer
-- +++ OK, passed 100 tests.         
         
-------------------------------------------------------------------------------
-- Ejercicio 10
-------------------------------------------------------------------------------

-- Usaremos el operador ~= visto en clase (Tema 1, transparencia 31)
infix 0 ~=
(~=) :: (Ord a, Fractional a) => a -> a -> Bool
x ~= y = abs (x-y) < epsilon
  where epsilon = 1e-5
-- Primera solución
-- no consideramos el estudio de las raices para a=0, 
raíces :: (Ord a, Floating a) => a -> a -> a -> (a, a)
raíces a b c 
  | dis < 0     = error "Raíces no reales"
  | otherwise   = (r1,r2)
 where  dis     = b^2 - 4*a*c
        r1 = ((-b) + dis)/2*a
        r2 = ((-b) - dis)/2*a


p1_raíces a b c  = True   ==> esRaíz r1 && esRaíz r2
-- atención, en el caso de True, podemos eliminar:  True ==> 
  where
   (r1,r2) = raíces a b c
   esRaíz r = a*r^2 + b*r + c ~= 0

p2_raíces a b c  = True  && True   ==> esRaíz r1 && esRaíz r2
  where
   (r1,r2) = raíces a b c
   esRaíz r = a*r^2 + b*r + c ~= 0



-------------------------------------------------------------------------------
-- Ejercicio 14
-------------------------------------------------------------------------------

-- potencia con base un número arbitrario
potencia :: (Num b, Integral n) => b -> n -> b
potencia b 0         = 1
potencia b 1         = b
potencia b n 
            | n > 0  = b*potencia b (n-1)

potencia' :: (Num b, Integral n) => b -> n -> b
potencia' b 0           = 1
potencia' b 1           = b
potencia' b n
            | even n    = potencia' b (div n 2)^2   
            | otherwise = b *(potencia' b ( div (n-1) 2))^2    

-- con esta propiedad (BASE un entero) no hay problemas
p_pot :: Integer -> Integer -> Property
p_pot b n  = n>=0 ==> (potencia b n == sol)-- && (potencia' b n == sol)
   where sol = b^n
-- *Main> quickCheck p_pot
-- +++ OK, passed 100 tests.


-- SEGUNDA OPCION: si consideramos una base arbitraria hay muchos problemas
p_pot' :: (Ord b, Fractional b, Integral n) => b -> n -> Property
p_pot' b n  = n>=0 ==> (potencia b n ~= sol) && (potencia' b n ~= sol)
   where sol = b^n
-- *Main> quickCheck p_pot'
-- *** Failed! Falsifiable (after 7 tests and 1 shrink):  
-- 4.374147831506856
-- 4   

-- Main> potencia 850.1 5 - 850.1^5
-- 6.25e-2

-- Debemos ~= por un concepto de error relativo

-------------------------------------------------------------------------------
-- Ejercicio 17
-------------------------------------------------------------------------------

mediana :: Ord a => (a, a, a, a, a) -> a
mediana (x,y,z,t,u) 
        | x > z 		= mediana (z,y,x,t,u)
        | y > z   		= mediana (x,z,y,t,u)
        | t < z         = mediana (x,y,t,z,u)
        | t < u         = mediana (x,y,z,u,t)
        |otherwise      = z
  

