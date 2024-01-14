package Network;

import Util.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Minibatch
{
    private List<List<Point>> batches;

    public int getTotalBatches() {
        return totalBatches;
    }

    private int totalBatches;

    public Minibatch(List<Point> data, int batchSize)
    {
        Collections.shuffle(data);
        totalBatches = data.size()/batchSize;
        batches = new ArrayList<>();
        int start = 0;

        for (int i = 0; i < totalBatches; i++)
        {
            int end = start + batchSize;
            batches.add(data.subList(start, end));
            start = end;
        }
    }

    public boolean hasNext()
    {
        return !batches.isEmpty();
    }

    public List<Point> next()
    {
        if (batches.isEmpty()) return null;
        Collections.shuffle(batches);
        return batches.remove(0);
    }
}
