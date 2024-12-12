import java.util.*;
import java.util.concurrent.Semaphore;


class ZweiSensoren
{
    public static void main (String[] args)
    {
        new  ZweiSensoren().main();
    }

    public void main ()
    {
        Semaphore sensor1schreiben = new Semaphore(1);
        Semaphore sensor2schreiben = new Semaphore(0);
        Semaphore auslesen = new Semaphore(0);

        Queue<SensorWert> dieQueue = new ArrayDeque<>();

        Sensor sensor1 = new Sensor(
                dieQueue,
                Arrays.asList(sensor1schreiben), //P
                Arrays.asList(sensor2schreiben), //V
                "Sensor 1"
        );

        Sensor sensor2 = new Sensor(
                dieQueue,
                Arrays.asList(sensor2schreiben), //P
                Arrays.asList(auslesen),        //V
                "Sensor 2"
        );

        Ausgabe aus = new Ausgabe(
                dieQueue,
                Arrays.asList(auslesen),        //P
                Arrays.asList(sensor1schreiben) //V
        );

        sensor1.start();
        sensor2.start();
        aus.start();
    }


    public class Sensor extends Thread
    {
        private Queue<SensorWert> queue;
        private List<Semaphore> pbefore;
        private List<Semaphore> vafter;
        private String kennung;


        public Sensor (Queue<SensorWert> queue, List<Semaphore> pbefore, List<Semaphore> vafter, String kennung)
        {
            this.queue = queue;
            this.pbefore = pbefore;
            this.vafter = vafter;
            this.kennung = kennung;
        }


        public String getKennung ()
        {
            return kennung;
        }


        @Override
        public void run ()
        {
            while (true)
            {
                for (Semaphore sema : pbefore)
                {
                    try
                    {
                        sema.acquire();
                    } catch (InterruptedException e)
                    {
                        throw new RuntimeException(e);
                    }
                }


                for (int i = 0; i < 3; i++)
                {
                    Random rnd = new Random();
                    queue.add(new SensorWert<Float>(rnd.nextFloat(), this));
                }

                for (Semaphore sema : vafter)
                {
                    sema.release();
                }
            }
        }
    }


    class Ausgabe extends Thread
    {

        private Queue<SensorWert> queue;
        private List<Semaphore> pbefore;
        private List<Semaphore> vafter;


        public Ausgabe (Queue<SensorWert> queue, List<Semaphore> pbefore, List<Semaphore> vafter)
        {
            this.queue = queue;
            this.pbefore = pbefore;
            this.vafter = vafter;
        }


        @Override
        public void run ()
        {
            while (true)
            {
                for (Semaphore sema : pbefore)
                {
                    try
                    {
                        sema.acquire();
                    } catch (InterruptedException e)
                    {
                        throw new RuntimeException(e);
                    }
                }

                while (!queue.isEmpty())
                {
                    SensorWert wert = queue.poll();
                    System.out.println(wert.ursprung.getKennung() + " las " + wert.value);
                }

                for (Semaphore sema : vafter)
                {
                    sema.release();
                }
            }
        }
    }


    public class SensorWert<T>
    {
        public Sensor ursprung;
        public T value;


        public SensorWert (T val, Sensor ursprung)
        {
            this.ursprung = ursprung;
            this.value = val;
        }
    }

}
