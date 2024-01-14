import Data.ConfigReader;
import Data.Dataset;
import Data.FileSaver;
import Network.Activation;
import Network.Minibatch;
import Network.NetworkArchitecture;
import Util.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class NeuralMain
{
    public static void help()
    {
        System.out.println("Usage: java NeuralMain [--create] [--config] [--help] [--OPTION num ...]");
        System.out.println("\t--create: Creates a data set of 8000 points with file name \"classification\"");
        System.out.println("\t--file:   Sets up the network based on \"nnconfig\" file");
        System.out.println("\t--help:   Shows this message");
        System.out.println("\t--OPTION:");
        System.out.println("\t\t--lr:  Sets the learning rate");
        System.out.println("\t\t--bs:  Sets the batch size");
        System.out.println("\t\t--eps: Sets the convergence epsilon");
        System.out.println("\t\t--me:  Sets the minimum epochs for the training");
        System.out.println("\t\t--Me:  Sets the maximum epochs for the training");
        System.out.println("\tThe default values for the network are:");
        System.out.println("\t2 - ReLU - 40 - ReLU - 40 - ReLU - 40 - Sigmoid - 4");
        System.out.println("\tlr = 0.01, bs = 100, eps = 1e-3, me = 700, Me = 2000");
        System.exit(0);
    }


    public static void main(String[] args)
    {
        List<String> arguments = new ArrayList<>(Arrays.asList(args));
        double lr = 0.01;
        double eps = 1e-3;
        int bs = 100;
        int me = 700;
        int Me = 2000;
        NetworkArchitecture network = new NetworkArchitecture();
        try
        {
            if (arguments.contains("--help"))
                help();
            if (arguments.contains("--create"))
                Dataset.createClassificationFile("classification", 8000, -1, 1);
            if (arguments.contains("--lr"))
                lr = Double.parseDouble(arguments.get(arguments.indexOf("--lr") + 1));
            if (arguments.contains("--bs"))
                bs = Integer.parseInt(arguments.get(arguments.indexOf("--bs") + 1));
            if (arguments.contains("--eps"))
                lr = Double.parseDouble(arguments.get(arguments.indexOf("--eps") + 1));
            if (arguments.contains("--me"))
                me = Integer.parseInt(arguments.get(arguments.indexOf("--me") + 1));
            if (arguments.contains("--Me"))
                Me = Integer.parseInt(arguments.get(arguments.indexOf("--Me") + 1));
            if (arguments.contains("--config"))
            {
                network = ConfigReader.readConfig(network);
            }
            else
            {
                network = new NetworkArchitecture(2, 40, 40, 40, 4);
                network.setActivationFunctions(Activation.ReLU, Activation.ReLU, Activation.ReLU, Activation.Sigmoid);
                network.setLearningRate(lr);
                network.setMinEpochs(me);
                network.setMaxEpochs(Me);
                network.setEpsilon(eps);
                network.setBatchSize(bs);
                network.configure();
            }
        }
        catch (Exception e)
        {
            help();
        }

        List<Point> trainTest = Dataset.readClassificationFile("classification");

        Collections.shuffle(trainTest);
        List<Point> train = new ArrayList<>(trainTest.subList(0, 4000));
        List<Point> test = new ArrayList<>(trainTest.subList(4000, trainTest.size()));

        System.out.printf("Pre-training Accuracy: %f\n", network.test(test));

        System.out.println("Training...");
        int epochs = 0;
        boolean converged = false;
        double previousLoss = Double.MAX_VALUE;

        List<Double> losses = new ArrayList<>();

        while (epochs < network.getMinEpochs() || !converged)
        {
            double total_loss = 0;
            converged = false;
            Collections.shuffle(train);
            Minibatch minibatch = new Minibatch(train, network.getBatchSize());
            while (minibatch.hasNext())
            {
                double loss_item = network.train(minibatch.next());
                total_loss += loss_item;
            }

            total_loss /= minibatch.getTotalBatches();
            losses.add(total_loss);
            System.out.print("\033[2K\033[1G");
            System.out.printf("Epoch %d Loss: %f", epochs+1, total_loss);
            double diff = Math.abs(total_loss - previousLoss);
            previousLoss = total_loss;
            if (diff < network.getEpsilon()) converged = true;
            epochs++;
            if (epochs >= network.getMaxEpochs()) break;
        }
        System.out.printf("\nAfter training Accuracy: %f\n", network.test(test));
        System.out.println("Writing classification results to file classification_result.txt");
        FileSaver fs = new FileSaver("classification_result.txt");
        fs.saveClassificationFile(network, trainTest);
        System.out.println("Writing classification results to file classification_result_loss.txt");
        fs.setFilename("classification_result_loss.txt");
        fs.saveLossFile(losses);
    }
}
