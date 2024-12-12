import java.sql.Array;
import java.util.Random;
import java.util.concurrent.Semaphore;


public class EigenerStackParallel<T>
{

    public static void main (String[] args)
    {
        EigenerStackParallel<Integer> meinStack = new EigenerStackParallel<>(25);

        Thread reinpacken = new Thread(() ->
        {
            while (true)
            {
                boolean reinpacken_klappte;
                System.out.println("rein");
                reinpacken_klappte = meinStack.push(new Random().nextInt(100));
                if(!reinpacken_klappte){
                    System.out.println("Reinpacken klappte nicht");
                }
                try
                {
                    Thread.sleep(10);
                } catch (InterruptedException e)
                {
                    throw new RuntimeException(e);
                }
            }
        });
        Thread rausnehmen = new Thread(() ->
        {
            while (true)
            {
                System.out.println("nahm heraus" + meinStack.pop());
                try
                {
                    Thread.sleep(10);
                } catch (InterruptedException e)
                {
                    throw new RuntimeException(e);
                }
            }
        });

        for (int i = 0; i < 2; i++)
        {
            new Thread(reinpacken).start();
        }
        for (int i = 0; i < 2; i++)
        {
            new Thread(rausnehmen).start();
        }
    }


    int m_size;
    T[] m_data;
    int m_stackTop = -1; //0-m_size -1
    Semaphore zugriff = new Semaphore(1);


    public EigenerStackParallel (int maxsize)
    {
        m_size = maxsize;
        m_data = (T[]) new Object[maxsize];
    }


    //Gefahr, der Wert kann sich direkt aendern
    public boolean isEmpty ()
    {
        return m_stackTop < 0;
    }


    //Gefahr, der Wert kann sich direkt aendern
    public boolean isFull ()
    {
        return m_stackTop >= m_size - 1;
    }


    public boolean push (T toPush)
    {
        try
        {
            zugriff.acquire();
        } catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
        if(isFull())
        {
            return false;
        }

        m_stackTop++;
        m_data[m_stackTop] = toPush;
        zugriff.release();

        return true;
    }


    public T pop ()
    {
        try
        {
            zugriff.acquire();
        } catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
        if(isEmpty())
        {
            return null;
        }

        m_stackTop--;
        T ret = m_data[m_stackTop + 1];

        zugriff.release();

        return ret;
    }

}
