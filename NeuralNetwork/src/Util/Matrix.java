package Util;

public class Matrix
{
    public static double[] subtract (double[] v1, double[] v2)
    {
        if (v1.length != v2.length)
        {
            System.out.println("Vectors v1 and v2 must have the same dimension to subtract...");
            System.out.printf("v1: %d\nv2: %d\n", v1.length, v2.length);
            System.exit(-1);
        }
        double[] res = new double[v1.length];
        for (int i = 0; i < res.length; i++)
        {
            res[i] = v1[i] - v2[i];
        }
        return res;
    }
}
