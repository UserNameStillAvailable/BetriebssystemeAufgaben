import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.Semaphore;


public class ProducerConsumer
{
    public static void main(){
        Semaphore mutex = new Semaphore(1);
        Semaphore pufferLeer = new Semaphore(0);
        Semaphore pufferFuelle = new Semaphore(3);
        Stack<Item> lager = new Stack<>();

        List<Thread> threads = new ArrayList<Thread>();
        for (int i = 0; i < 2 ; i++)
        {
            threads.add(new Producer(mutex, pufferLeer, pufferFuelle, lager));
        }
        for (int i = 0; i < 2 ; i++)
        {
            threads.add(new Consumer(mutex, pufferLeer, pufferFuelle, lager));
        }
        for(Thread thread : threads){
            System.out.println(thread.getId());
            thread.start();
        }

    }
}
class Item{
    private int a;

    public Item(int a){
        this.a = a;
    }


    @Override
    public String toString ()
    {
        return "Item{" +
                "a=" + a +
                '}';
    }
}
class Producer extends Thread
{
    public Producer(Semaphore mutex, Semaphore pufferLeer, Semaphore pufferFuelle, Stack<Item> itemList){
        this.mutex = mutex;
        this.pufferFuelle = pufferFuelle;
        this.pufferLeer = pufferLeer;
        this.itemList = itemList;
    }
    Semaphore mutex;
    Semaphore pufferFuelle;
    Semaphore pufferLeer;
    Stack<Item> itemList;

    @Override
    public void run ()
    {
        while (true)
        {


            Item item = new Item(1);

            try
            {
                pufferFuelle.acquire();
                mutex.acquire();

                itemList.add(item);

                mutex.release();
                pufferLeer.release();
            } catch (InterruptedException e)
            {
                throw new RuntimeException(e);
            }
        }
    }
}

class Consumer extends Thread
{
    public Consumer(Semaphore mutex, Semaphore pufferLeer, Semaphore pufferFuelle, Stack<Item> itemList){
        this.mutex = mutex;
        this.pufferFuelle = pufferFuelle;
        this.pufferLeer = pufferLeer;
        this.itemList = itemList;
    }
    Semaphore mutex;
    Semaphore pufferFuelle;
    Semaphore pufferLeer;
    Stack<Item> itemList;

    @Override
    public void run ()
    {
        while (true)
        {


            Item item;
            try
            {
                pufferLeer.acquire();
                mutex.acquire();

                item = itemList.pop();

                mutex.release();
                pufferFuelle.release();
            } catch (InterruptedException e)
            {
                throw new RuntimeException(e);
            }

            System.out.println(item.toString());
        }
    }
}
