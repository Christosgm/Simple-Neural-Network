package Network;

import Network.Activation;

public class Function
{
    public static double sigmoid(double x)
    {
        return 1.0/(1 + Math.exp(-x));
    }

    public static double[] sigmoid(double[] x)
    {
        for (int i = 0; i < x.length; i++) {
            x[i] = sigmoid(x[i]);
        }
        return x;
    }

    public static double sigmoidDerivative(double x)
    {
        return 1.0 - sigmoid(x);
    }

    public static double relu(double x)
    {
        return Math.max(0.0, x);
    }

    public static double[] relu(double[] x)
    {
        for (int i = 0; i < x.length; i++) {
            x[i] =  relu(x[i]);
        }
        return x;
    }

    public static double reluDerivative(double x)
    {
        return x <= 0 ? 0 : 1;
    }

    public static double tanh(double x)
    {
        return (Math.exp(x) - Math.exp(-x))/(Math.exp(x) + Math.exp(-x));
    }

    public static double[] tanh(double[] x)
    {
        for (int i = 0; i < x.length; i++) {
            x[i] = tanh(x[i]);
        }
        return x;
    }

    public static double tanhDerivative(double x)
    {
        return 1 - Math.pow(tanh(x), 2);
    }

    public static double[] activate(double[] array, Activation function) {
         switch (function) {
             case Sigmoid:
                 return sigmoid(array);
             case ReLU:
                 return relu(array);
             case TanH:
                 return tanh(array);
             case None:
                 return array;
        };
         return array;
    }

    public static double functionDerivative(double in, Activation function) {
        switch (function) {
            case Sigmoid:
                return sigmoidDerivative(in);
            case ReLU:
                return reluDerivative(in);
            case TanH:
                return tanhDerivative(in);
            case None:
                return 0;
        };
        return 0;
    }

    public static double[] oneHotEncode(int classInt, int maxIdx)
    {
        double[] c = new double[maxIdx];
        c[classInt] = 1;
        return c;
    }

}
