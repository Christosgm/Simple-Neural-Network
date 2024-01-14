package Network;

import Util.Rand;

import java.util.Arrays;

public class FCLayer extends Layer
{
    private double[][] _weights;
    private double[][] _weightsGradients;
    private double[] _biases;
    private double[] _biasesGradients;
    private Activation function;

    private double learningRate;

    private double[] lastU;
    private double[] lastInput;

    public FCLayer(int _inLength, int _outLength, Activation function, double learningRate) {
        this._inLength = _inLength;
        this._outLength = _outLength;
        this.function = function;
        this.learningRate = learningRate;

        _weights = new double[_inLength][_outLength];
        _weightsGradients = new double[_inLength][_outLength];

        _biases = new double[_outLength];
        _biasesGradients = new double[_outLength];
        for (int i = 0; i < _weights.length; i++)
        {
            for (int j = 0; j < _weights[0].length; j++)
            {
                _weights[i][j] = Rand.range(-1, 1);
            }
        }

        for (int i = 0; i < _biases.length; i++)
        {
            _biases[i] = Rand.range(-1, 1);
        }
    }

    public double[] feedForward(double[] input)
    {
        lastInput = input;
        double[] u  = new double[_outLength];

        for (int i = 0; i < _inLength; i++) {
            for (int j = 0; j < _outLength; j++) {
                u[j] += input[i]*_weights[i][j];
            }
        }
        for (int j = 0; j < _outLength; j++)
        {
            u[j] += _biases[j];
        }
        lastU = u;
        return Function.activate(u, function);
    }

    private int _inLength;
    private int _outLength;


    @Override
    public double[] getOutput(double[] input) {
        double[] fwdPass = feedForward(input);
        if (_nextLayer != null) return _nextLayer.getOutput(fwdPass);
        else return fwdPass;
    }

    @Override
    public void backpropagate(double[] dLdO) {

        double[] dLdX = new double[_inLength];

        double dOdZ;
        double dZdW;
        double dLdW;
        double dZdX;

        for (int k = 0; k < _inLength; k++)
        {
            double dLdX_sum = 0;
            for (int j = 0; j < _outLength; j++)
            {
                dOdZ = Function.functionDerivative(lastU[j], function);
                dZdW = lastInput[k];
                dLdW = dLdO[j]*dOdZ*dZdW;

                _weightsGradients[k][j] += dLdW;
                _biasesGradients[j] += dLdO[j];

                dZdX = _weights[k][j];
                dLdX_sum += dLdO[j]*dOdZ*dZdX;
            }
            dLdX[k] = dLdX_sum;
        }

        if (_previousLayer != null) _previousLayer.backpropagate(dLdX);
    }

    public void updateParameters(int batchSize)
    {
        for (int i = 0; i < _inLength; i++)
        {
            for (int j = 0; j < _outLength; j++)
            {
                _weights[i][j] -= learningRate*_weightsGradients[i][j]/batchSize;
            }
        }

        for (int i = 0; i < _outLength; i++)
        {
            _biases[i] -= learningRate*_biasesGradients[i]/batchSize;
        }
    }

    public void zeroGrad()
    {
        Arrays.fill(_biasesGradients, 0);
        for (double[] wg : _weightsGradients)
        {
            Arrays.fill(wg, 0);
        }
    }

    @Override
    public int getOutputLength() {
        return _outLength;
    }
}
