import java.util.ArrayList;
import java.util.List;


/*
public class Berechnung
{
    private static double komplexeBerechnung(double x)
    {
        double y = 0;
        final int N = 100000000;
        for (int i=0; i<N; i++)
            y += Math.sin(x + i * Math.PI/N) * (Math.PI/N);
        return y;
    }

    public static void main(String[] args)
    {
        double values[] = { 1.111, 2.222 };

        double res1 = komplexeBerechnung(values[0]);
        double res2 = komplexeBerechnung(values[1]);
        double res = res1 + res2;

        System.out.println("Das Ergebnis ist " + res + " (korrekter Wert ist: -0.32475713878320844)");
    }
}

 */
public class KomplexeRechnungParallel
{
    
    //Aufgabe 4
    public static void main(String[] args) throws InterruptedException
    {
        main();
    }

    public static void main() throws InterruptedException
    {
        double[] values = { 1.111, 2.222 };
        Doublewrapper res1 = new Doublewrapper();
        Doublewrapper res2 = new Doublewrapper();
        Thread r1 = new komplexeBerechnung(values[0], res1);
        r1.start();
        Thread r2 = new komplexeBerechnung(values[1], res2);
        r2.start();
        r1.join();
        r2.join();
        double res = res1.getErgebnis() + res2.getErgebnis();

        System.out.println("Das Ergebnis ist " + res + " (korrekter Wert ist: -0.32475713878320844)");
    }
}

class komplexeBerechnung extends Thread
{
    public komplexeBerechnung(double x, Doublewrapper Y){
        this.x = x;
        ergebnis = Y;
    }
    double x;
    Doublewrapper ergebnis;
    public void run(){
        double y = 0.0;
        final int N = 100000000;
        /*
        List<Thread> res = new ArrayList<>();

        for (int i=0; i<N; i++){
            int finalI = i;
            Thread re = new Thread(() -> {
                ergebnis.increment(Math.sin(x + finalI * Math.PI/N) * (Math.PI/N));
            });
            re.start();
            res.add(re);
        }
        for (Thread re : res){
            try
            {
                re.join();
            } catch (InterruptedException e)
            {
                throw new RuntimeException(e);
            }
        }
         */

        for (int i=0; i<N; i++){
            y += Math.sin(x + i * Math.PI/N) * (Math.PI/N);
        }

        ergebnis.setdouble(y);
    }
}

class Doublewrapper
{
    private double ergebnis;

    public Doublewrapper (){

    }

    public void setdouble(double x){
        ergebnis = x;
    }

    public double getErgebnis(){
        return ergebnis;
    }

    public void increment(double increment){
        ergebnis += increment;
    }
}
