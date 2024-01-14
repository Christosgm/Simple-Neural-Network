package Network;

import Network.Function;
import Util.Matrix;
import Util.Point;

import java.util.List;

public class Evaluation {
    public static double accuracy(int[] given, int[] actual) {
        if (given.length != actual.length)
            System.out.printf("Different array lengths...\n expected: %d actual: %d\n", given.length, actual.length);
        double acc = 0;
        for (int i = 0; i < given.length; i++) {
            if (given[i] == actual[i]) acc += 1;
        }
        return acc / given.length;
    }

    public static double mse(NetworkArchitecture network, List<Point> data)
    {
        double se = 0;
        for (Point point : data)
        {
            double[] target = Function.oneHotEncode(point.getCategory(), network.getLastLayer().getOutputLength());
            double[] out = network.feed(point.toArray());
            double[] diff = Matrix.subtract(out, target);
            for (int i = 0; i < diff.length; i++)
            {
                se += Math.pow(diff[i], 2);
            }
        }
        return se/data.size();
    }
}
