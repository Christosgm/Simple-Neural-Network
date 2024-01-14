package Network;

public abstract class Layer
{
    protected Layer _nextLayer;

    public Layer get_nextLayer() {
        return _nextLayer;
    }

    public void set_nextLayer(Layer _nextLayer) {
        this._nextLayer = _nextLayer;
    }

    public Layer get_previousLayer() {
        return _previousLayer;
    }

    public void set_previousLayer(Layer _previousLayer) {
        this._previousLayer = _previousLayer;
    }

    protected Layer _previousLayer;

    public abstract double[] getOutput(double[] input);

    public abstract void backpropagate(double[] der);

    public abstract int getOutputLength();
}
