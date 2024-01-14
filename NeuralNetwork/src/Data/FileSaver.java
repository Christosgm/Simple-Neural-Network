package Data;

import Network.NetworkArchitecture;
import Util.Point;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class FileSaver
{
    public void setFilename(String filename) {
        this.filename = filename;
    }

    private String filename;

    public FileSaver(String filename)
    {
        this.filename = filename;
    }

    public void saveClassificationFile(NetworkArchitecture network, List<Point> points)
    {
        try
        {
            FileWriter fileWriter = new FileWriter(filename);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            for (Point point : points)
            {
                printWriter.printf("%f %f %d %d\n", point.x(), point.y(), network.feed(point), point.getCategory());
            }
            fileWriter.close();
            printWriter.close();
        }
        catch (IOException e)
        {
            System.out.println("IOException: Class utils.Data.Dataset, Method createFile()");
            System.exit(-1);
        }
    }

    public void saveLossFile(List<Double> losses)
    {
        try
        {
            FileWriter fileWriter = new FileWriter(filename);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            for (double loss : losses)
            {
                printWriter.printf("%f\n", loss);
            }
            fileWriter.close();
            printWriter.close();
        }
        catch (IOException e)
        {
            System.out.println("IOException: Class utils.Data.Dataset, Method createFile()");
            System.exit(-1);
        }
    }
}
