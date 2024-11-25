import java.util.Random;
import java.util.ArrayList;
import java.util.List;
public class ThreadSchlafen extends Thread
{

    public static void main(){
        List<Thread> meineThreads = new ArrayList<>();
        for (int i = 0; i <  100; i++){
            meineThreads.add(new ThreadSchlafen(i+1));
        }
        for(Thread thread : meineThreads)
        {
            thread.start();
        }
        for(Thread thread : meineThreads)
        {
            try
            {
                thread.join();//thread.join(zeit); -> stirbt nach zeit
            } catch (InterruptedException e)
            {
                //Falls die ewig laufen
                throw new RuntimeException(e);
            }
        }
    }

    public int id;

    public ThreadSchlafen(int id){
        this.id = id;
    }

    @Override
    public void run ()
    {
        int timeToSleep = new Random().nextInt(1000) + 100;
        try{
            Thread.sleep(timeToSleep);
        }
        catch (InterruptedException e){
            Thread.currentThread().interrupt();
            System.out.println(e.toString());
        }
        System.out.println(id + " slept for " + timeToSleep + " millies");
    }

    @Override
    public void start ()
    {
        super.start();
    }
}
