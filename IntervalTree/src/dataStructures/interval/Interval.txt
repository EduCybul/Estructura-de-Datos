package dataStructures.interval;

public class Interval implements Comparable<Interval>{
    int high, low;

    public Interval (int low, int high){
        this.low = low;
        this.high = high;
    }


    @Override
    public String toString(){
        return "["+low+", "+high+"]";
    }
    @Override
    public int compareTo(Interval o) {
        /* Devuelve <0 si el intervalo this tiene un límite inferior menor que el intervalo o.
         *            0 si ambos intervalos tienen el mismo límite inferior
         *            >0 si el intervalo this tiene un límite inferior mayor que el intervalo o
         */
        return Integer.compare(low, o.low);
    }

    public boolean overlap(Interval y){
        return (low <= y.high && high >= y.low);
    }

}
