--EJ1
--Lo que suelta la g es el segundo parametro de lo que usa la f
--Lo que suelta la g----(a->b)
subst::(a->b->c)->(a->b)-> a->c
subst f g x = f x (g x)

cross::(a->c,b->d) -> (a,b) ->(c,d)
cross (f,g) (x,y) = (f x, g y)



--EJ2--Da un habitante al tipo
---Aplica la funcion a los parametros cambiados 
--Esta funcion flip hace que se apliquen las funciones al reves
flip'::(a->b->c)->b->a->c
flip' f x y = f y x


--EJ3
ff::(Eq a,Ord b)=> a -> a -> b ->b ->Bool
ff x y z t
    | z < t     = x == y 
    | otherwise  = False


--EJ4

ack m n 
    | m == 0 = n+1 
    | m > 0 && n==0 = ack (m-1) 1 
    | m > 0 && n>0  = ack m-1  (ack m (n-1) )

{-
ack 0 n = n + 1
ack m=0 | m > 0 = ack (m-1) 1
ack m n | m>0 && n>0 = ack m-1 (ack m (n-1))
-}

--EJ5
--1010101010000011101

cerosUnos :: Integer -> (Integer,Integer)
cerosUnos 0 = (1,0)--casos base en el que solo sean un solo numero o cero o uno
cerosUnos 1 = (0,1)
cerosUnos n
        | r==0 = (nc+1,nu)
        | r==1 = (nc,nu+1)
    
    where
        r = mod n 10--nos da el ultimo numero de la secuencia binaria
        (nc,nu) = cerosUnos (div n 10)--llamada recursiva habiendo ya contado el ultimo numero



iguales3:: Eq a => (a,a,a) -> Bool
iguales3 (x,y,z) = x == y && x ==z


