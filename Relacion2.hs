-------------------------------------------------------------------------------
-- Estructuras de Datos. 2º Curso. ETSI Informática. UMA
--
-- (completa y sustituye los siguientes datos)
-- Titulación: Grado en Ingeniería Informática
-- Alumno: Cybulkiewicz, Eduard
-- Fecha de entrega: DIA | MES | AÑO
--
-- Relación de Ejercicios 2. Ejercicios resueltos: ..........
--
-------------------------------------------------------------------------------
import Test.QuickCheck

--EJ1
data Direction = North|South|East|West
                                deriving (Eq,Ord,Enum,Show)

(<<)::Direction->Direction->Bool
(<<) 
