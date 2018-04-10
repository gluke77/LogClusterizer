package com.github.gluke77;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {

        if (args.length < 1) {
            System.out.println("Usage: java -jar <JAR_FILE> <INPUT_FILE>");
            return;
        }

        LogClusterizer logClusterizer = new LogClusterizer();

        //TODO: What should we do if a message does not follow the pattern <DATE> <DIME> <TEXT> ?
        try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
            br.lines().forEach(logClusterizer::append);
        }

        System.out.println("=====");

        for (LogEntryCluster cluster : logClusterizer.getClusters()) {
            //TODO: Do we want to skip one-message clusters?
            if (cluster.getMessages().size() > 1) {
                for (String m : cluster.getMessages()) {
                    System.out.println(m);
                }

                System.out.println("The changing word was: " + String.join(", ", cluster.getAlteringWords()));
            }
        }

        System.out.println("=====");
    }
}
