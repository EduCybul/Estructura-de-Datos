

fact::Int->Int
fact 0 = 1
fact n | n>0 = n * fact (n-1)


resto :: Integer->Integer-> Integer
resto x y 
        | x < y     = x
        | otherwise = resto (x-y) y


cociente::Integer->Integer->Integer
cociente x y
        | x < y = 0
        |otherwise = 1+ cociente (x-y) y

second::Integer->Integer->Integer
second x y = y

f1:: a->(a->b)->b
f1 x f = f x 

f2:: a->(b->c)->(a->b)-> c
f2 x f g = f (g x) 

f3:: (a->b->c) -> (a->b) -> a -> c
f3 f g x =  f x (g x)  

f4:: (a->b, c->d) -> a -> c -> (b,d)
f4 (f,g) x y = (f x ,g y )

f5:: (a -> b -> c) -> b -> a -> c
f5 f x y = f y x ---Se invierten los parametros que recib la funcion

--f6:: (a -> b) ->a -> c
--f6 f x = False-----No se puede obtener un c aqui OJO!!


---Lo definimos de forma generica que pueda valer para cualquier tipo de numero
--que reciba
--twice::Integer->Integer
twice:: (Num a) => a -> a
twice x = x + x


----------------------------------------------------------------------------------------------------------------------------------------------------------