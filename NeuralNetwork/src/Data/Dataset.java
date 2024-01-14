package Data;

import Network.Activation;
import Network.NetworkArchitecture;
import Util.Point;
import Util.Rand;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Dataset
{
    public static int classificationClass(double x1, double x2)
    {
        boolean pp = (Math.pow(x1 + 0.5, 2) + Math.pow(x2 + 0.5, 2)) < 0.2;
        boolean mm = (Math.pow(x1 - 0.5, 2) + Math.pow(x2 - 0.5, 2)) < 0.2;
        boolean mp = (Math.pow(x1 - 0.5, 2) + Math.pow(x2 + 0.5, 2)) < 0.2;
        boolean pm = (Math.pow(x1 + 0.5, 2) + Math.pow(x2 - 0.5, 2)) < 0.2;

        boolean C1 = (mm && (x1 > 0.5))
                || (pp && (x1 > -0.5))
                || (mp && (x1 > 0.5))
                || (pm && x1 > -0.5);

        boolean C2 = (mm && (x1 < 0.5))
                || (pp && (x1 < -0.5))
                || (mp && (x1 < 0.5))
                || (pm && (x1 < -0.5));

        if (C1) return 0;
        if (C2) return 1;
        if (x1 > 0) return 2;
        return 3;
    }

    public static List<Point> readClassificationFile(String fileName)
    {
        List<Point> points = new ArrayList<>();
        try
        {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine())
            {
                String data = scanner.nextLine();
                String[] pointString = data.split(" ");
                Point newPoint = new Point(Double.parseDouble(pointString[0]), Double.parseDouble(pointString[1]));
                newPoint.setCategory(Integer.parseInt(pointString[2]));
                points.add(newPoint);
            }
            scanner.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.printf("%s was not found!...\n", fileName);
        }
        return points;
    }

    public static void createClassificationFile(String fileName, int size, double minValue, double maxValue)
    {
        Random rand = new Random();

        try
        {
            FileWriter fileWriter = new FileWriter(fileName);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            for (int i = 0; i < size; i++)
            {
                double x1 = Rand.range(minValue, maxValue);
                double x2 = Rand.range(minValue, maxValue);
                int c = classificationClass(x1, x2);

                printWriter.printf("%.5f %.5f %d\n", x1, x2, c);
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
