package com.activemesa.creational.singleton.testability;

import com.google.common.collect.Iterables;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

/**
 * This interface provides a inversion of control, so we are not dependent on concrete implementation, but on abstraction
 * which improves testability of this singleton class
 */
interface Database {
    int getPopulation(String name);
}

public class SingletonDatabase implements Database{
    private Dictionary<String, Integer> capitals = new Hashtable<>();
    private static int instanceCount = 0;

    public static int getInstanceCount() {
        return instanceCount;
    }

    private SingletonDatabase() {
        instanceCount ++;
        System.out.println("Initializing database");
        try {
            final File file = new File(
                    SingletonDatabase.class.getProtectionDomain().getCodeSource().getLocation().getPath()
            );
            Path fullPath = Paths.get(file.getPath(), "capitals.txt");
            final List<String> lines = Files.readAllLines(fullPath);
            Iterables.partition(lines, 2)
                    .forEach(kv -> capitals.put(kv.get(0).trim(), Integer.parseInt(kv.get(1))));

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private static final SingletonDatabase INSTANCE = new SingletonDatabase();

    public static SingletonDatabase getInstance() {
        return INSTANCE;
    }

    public int getPopulation(String name) {
        return capitals.get(name);
    }
}

/**
 * Everything looks good with this class, but the problem is testability
 */
class SingletonRecordFinder {
    public int getTotalPopulation(List<String> cityNames) {
        int result = 0;
        for(String name : cityNames) {
            result += SingletonDatabase.getInstance().getPopulation(name);
        }
        return result;
    }
}

class ConfigurableRecordFibder {
    private Database database;

    /**
     * Provide the constructor to inject database
     * This allows to create a dummy database without having to know real data
     * @param database
     */
    public ConfigurableRecordFibder(Database database) {
        this.database = database;
    }

    public int getTotalPopulation(List<String> cityNames) {
        int result = 0;
        for(String name : cityNames) {
            result += database.getPopulation(name);
        }
        return result;
    }
}

class DummyDatabase implements Database {

    private Dictionary<String, Integer> data = new Hashtable<>();

    public DummyDatabase() {
        data.put("alpha", 1);
        data.put("beta", 2);
        data.put("gamma", 3);
    }

    @Override
    public int getPopulation(String name) {
        return data.get(name);
    }
}

