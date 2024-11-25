import java.io.*;


public class LesenUndRechnen
{
    public static void main(){
        Thread leseThread = new Thread(() -> {
            FileReader reader;
            try
            {
                reader = new FileReader(new File("src/Text"));
            } catch (FileNotFoundException e)
            {
                throw new RuntimeException(e);
            }

            String string = "";
            while (true){
                try
                {
                    for (int i = 0; i < 1 ; i++)
                    {
                        string += (char)reader.read();
                        Thread.sleep(100);
                    }
                    System.out.print(string.replace("\r", "").replace("\n", ""));
                    string = "";
                }
                catch (IOException e)
                {
                    throw new RuntimeException(e);
                } catch (InterruptedException e)
                {
                    throw new RuntimeException(e);
                }

            }
        });


        leseThread.start();
        while (true){
            int rechenn = 2;
            for (int i = 0; i < 1000000000; i++)
            {
                rechenn *= (i * i * i + i * i * i * 9 + i * i * 3 + i* 90 + i * i * -2 -
                        i * i * 7 + i * 45* i * i + i * 5 * i * i - 112 * i * i + 19 * i * i * i );
            }

            System.out.println(" <er rechnerte> " +  rechenn);
        }
    }
}
