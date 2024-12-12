import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.function.Consumer;


public class AenderungenMitbekommen
{
    //Aufgabe 3
    public static void main(String[] args){
        Thread aenderungenPruefen = new Thread(() -> {
            FileTime fileTimeLast = getLastModidfied();

            while (true){ //Status pruefen
                FileTime fileTimeNow = getLastModidfied();
                if(!fileTimeLast.equals(fileTimeNow)){
                    System.out.println("Da, da, da war eine Änderung der Datei!!!! Böse Böse Böse !!!");
                    fileTimeLast = fileTimeNow;
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
        aenderungenPruefen.start();

        AenderungenMitbekommen.doalways(() -> test());
    }

    private static void test(){
        System.out.println("test");
    }
    private static void test2(){
        System.out.println("test4");
    }
    private static void doalways(Runnable fn){
        while (true){
            fn.run();
        }
    }

    private static FileTime getLastModidfied(){
        try
        {
            return java.nio.file.Files.getLastModifiedTime(Path.of("src/Text"));
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
