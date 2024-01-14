package Util;

import java.util.Random;

public class Rand
{
    public static double range(double minValue, double maxValue)
    {
        return minValue + (maxValue - minValue) * new Random().nextDouble();
    }
}
