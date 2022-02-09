package com.activemesa.creational.singleton.lazyinitialization;

/**
 * When we want to create a instance only when we need it
 * In this case, it cannot be created with a static method
 */
public class LazySingleton {

    private static LazySingleton instance;

    private LazySingleton() {
        System.out.println("Initialing a lazy singleton");
    }

    /**
     *  This is a way to ensure that we create an lazyness instance
     *  But it is not safe for run condition when we have multiple threads trying to create the instance
     *  This problem can be solved with keyword synchronized to bring synchronization to this method
     *  But this has performance implications. This can be reached with double-checked locking
     * @return
     */
//    public static synchronized LazySingleton getInstance() {
//        if(instance == null) {
//            instance = new LazySingleton();
//        }
//        return instance;
//    }

    /**
     * This is a way to create instance in a lazy and thread safe way.
     * @return
     */
    public static LazySingleton getInstance() {
        if(instance == null) {
            synchronized (LazySingleton.class) {
                if(instance == null) {
                    instance = new LazySingleton();
                }
            }
        }
        return instance;
    }
}
