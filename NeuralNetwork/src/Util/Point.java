package Util;

public class Point
{
    private final double x;
    private final double y;

    public double x()
    {
        return x;
    }

    public double y()
    {
        return y;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    private int category;

    public Point(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public String toString()
    {
        return String.format("(%.5f, %.5f)", x, y);
    }

    public double[] toArray() { return new double[]{this.x, this.y}; }
}
