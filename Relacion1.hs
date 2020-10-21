-------------------------------------------------------------------------------
-- Estructuras de Datos. 2º Curso. ETSI Informática. UMA
--
-- (completa y sustituye los siguientes datos)
-- Titulación: Grado en Ingeniería …………………………………… [Informática | del Software | de Computadores].
-- Alumno: Cybulkiewicz, Eduard
-- Fecha de entrega: DIA | MES | AÑO
--
-- Relación de Ejercicios 1. Ejercicios resueltos: ..........
--
-------------------------------------------------------------------------------
import Test.QuickCheck

--EJ2
--A)
esTerna::Integer->Integer->Integer->Bool
esTerna x y z 
        | x^2+y^2==z^2  = True
        | otherwise  = False
--B)
terna::Integer->Integer->(Integer,Integer,Integer)
terna x y = (x^2-y^2,2*x*y,x^2+y^2)
--C)--D)
p_ternas x y = x>0 && y>0 && x>y ==> esTerna l1 l2 h
    where 
        (l1,l2,h) = terna x y
--EJ2
intercambia::(a,b) -> (b,a)
intercambia (x,y) = (y,x)

--EJ3
ordena2::Ord a => (a,a) -> (a,a)
ordena2 (x,y) 
        | x > y    = (y,x)  
        |otherwise = (x,y)  
p1_ordena2 x y = enOrden (ordena2 (x,y))
  where enOrden (x,y) = x<=y

p2_ordena2 x y = mismoElemntos (x,y) (ordena2 (x,y))
  where 
mismoElemntos (x,y) (z,v) = (x==z && y==v) || (x==v && y==z)

ordena3::Ord a =>(a,a,a)->(a,a,a)
ordena3 (x,y,z) 
        |y < x && z < x && mayor1   = (y,z,x)
        |y < x && z < x && not mayor1  = (z,y,x)
        |x < y  && z < y && mayor2  = (x,z,y) 
        |x < y  && z < y && not mayor2 = (z,x,y)           
        |x < z && y < z && mayor3   = (x,y,z) 
        |x < z && y < z && not mayor3   = (y,x,z)

        where 
                mayor1 = z > y
                mayor2 = x > z
                mayor3 = y > x
p1_ordena3 x y z = enOrden (ordena3 (x,y,z))
        where enOrden (x,y,z) = x<=y && y<=z
p2_ordena3 x y z = mismoElemntos (x,y,z) (ordena3 (x,y,z))
        where 
                mismoElemntos (x,y,z) (a,b,c) = (x==a && y==b && z==c) || (x==c && y==b && z==a)

--EJ4





--EJ8

unEuro:: Double
unEuro = 166.386

pesetasAEuro :: Double ->Double
pesetasAEuro x = x*unEuro

--p_inversa :: Double -> Bool
--p_inversa x = eurosAPesetas(pesetasAEuros x) ~= x


--EJ11
esMultiplo :: Integral a => a -> a -> Bool
esMultiplo x y = mod x y == 0

--EJ12

(==>>):: Bool->Bool->Bool
False ==>> q = True
True ==>> q = q

--EJ13

esBisiesto:: Integer->Bool
esBisiesto n = (esMultiplo n 4 ) && (esMultiplo n 100 ==>> esMultiplo n 400) 

