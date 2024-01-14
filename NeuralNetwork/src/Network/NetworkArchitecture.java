package Network;

import Network.*;
import Util.Matrix;
import Util.Point;

import java.util.Arrays;
import java.util.List;

public class NetworkArchitecture
{
    private int[] layerSizes;
    private FCLayer[] networkLayers;
    private Activation[] activationFunctions;

    private double learningRate;

    public int getMinEpochs() {
        return minEpochs;
    }

    public void setMinEpochs(int minEpochs) {
        this.minEpochs = minEpochs;
    }

    public double getEpsilon() {
        return epsilon;
    }

    public void setEpsilon(double epsilon) {
        this.epsilon = epsilon;
    }

    private int minEpochs;

    public int getMaxEpochs() {
        return maxEpochs;
    }

    public void setMaxEpochs(int maxEpochs) {
        this.maxEpochs = maxEpochs;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    private int maxEpochs;

    private int batchSize;

    private double epsilon;

    public NetworkArchitecture(int... layers)
    {
        layerSizes = Arrays.copyOf(layers, layers.length);
        networkLayers = new FCLayer[layerSizes.length - 1];
        minEpochs = 700;
        epsilon = 1e-3;
        learningRate = 0.01;
    }

    public NetworkArchitecture(){}

    public void setLearningRate(double learningRate)
    {
        this.learningRate = learningRate;
    }

    public void setActivationFunctions(Activation... functions)
    {
        activationFunctions = Arrays.copyOf(functions, functions.length);
    }

    public void configure()
    {
        for (int i = 0; i < layerSizes.length - 1; i++)
        {
            networkLayers[i] = new FCLayer(layerSizes[i], layerSizes[i+1], activationFunctions[i], learningRate);
        }
        if (networkLayers.length == 1) return;
        networkLayers[0].set_nextLayer(networkLayers[1]);
        for (int i = 1; i < networkLayers.length - 1; i++) {
            networkLayers[i].set_previousLayer(networkLayers[i-1]);
            networkLayers[i].set_nextLayer(networkLayers[i+1]);
        }
        networkLayers[networkLayers.length-1].set_previousLayer(networkLayers[networkLayers.length-2]);
    }

    public double[] feed(double[] input)
    {
        return networkLayers[0].getOutput(input);
    }

    public int feed(Point point)
    {
        return findMaxIndex(networkLayers[0].getOutput(point.toArray()));
    }

    public Layer getLastLayer()
    {
        return networkLayers[networkLayers.length - 1];
    }

    public int guess(Point point)
    {
        double[] out = feed(point.toArray());
        return findMaxIndex(out);
    }

    public double test(List<Point> points)
    {
        int[] given = new int[points.size()];
        int[] actual = new int[points.size()];

        for (int i = 0; i < points.size(); i++)
        {
            given[i] = guess(points.get(i));
            actual[i] = points.get(i).getCategory();
        }

        return Evaluation.accuracy(given, actual);
    }

    public double train(List<Point> points)
    {
        for (Point point : points)
        {
            double[] out = feed(point.toArray());
            double[] dLdO = getErrors(out, point.getCategory());
            getLastLayer().backpropagate(dLdO);
        }
        for (FCLayer layer : networkLayers) layer.updateParameters(batchSize);
        for (FCLayer layer : networkLayers) layer.zeroGrad();
        return Evaluation.mse(this, points);
    }

    public double[] getErrors(double[] networkOutput, int correctClass)
    {
         return Matrix.subtract(networkOutput, Function.oneHotEncode(correctClass, networkOutput.length));
    }

    private int findMaxIndex(double[] in)
    {
        double max = 0;
        int idx = 0;

        for (int i = 0; i < in.length; i++)
        {
            if(in[i] >= max)
            {
                max = in[i];
                idx = i;
            }
        }
        return idx;
    }
}
