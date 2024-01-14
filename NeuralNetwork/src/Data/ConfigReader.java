package Data;

import Network.Activation;
import Network.NetworkArchitecture;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ConfigReader
{
    public static NetworkArchitecture readConfig(NetworkArchitecture network)
    {
        try
        {
            File file = new File("nnconfig");
            Scanner scanner = new Scanner(file);
            String line = scanner.nextLine();

            String[] layerStrings = line.split(" ");
            int[] ls = new int[layerStrings.length];
            for (int i = 0; i < ls.length; i++) {
                ls[i] = Integer.parseInt(layerStrings[i]);
            }

            line = scanner.nextLine();
            String[] functionStrings = line.split(" ");
            Activation[] af = new Activation[functionStrings.length];
            for (int i = 0; i < functionStrings.length; i++) {
                af[i] = Activation.valueOf(functionStrings[i]);
            }

            line = scanner.nextLine();
            double lr = Double.parseDouble(line);

            line = scanner.nextLine();
            int me = Integer.parseInt(line);

            line = scanner.nextLine();
            int Me = Integer.parseInt(line);

            line = scanner.nextLine();
            double eps = Double.parseDouble(line);

            line = scanner.nextLine();
            int bs = Integer.parseInt(line);

            scanner.close();

            network = new NetworkArchitecture(ls);
            network.setActivationFunctions(af);
            network.setLearningRate(lr);
            network.setMinEpochs(me);
            network.setMaxEpochs(Me);
            network.setEpsilon(eps);
            network.setBatchSize(bs);
            network.configure();
        }
        catch (FileNotFoundException e)
        {
            System.out.printf("%s was not found!...\n", "nnconfig");
            System.exit(-1);
        }
        return network;
    }
}
