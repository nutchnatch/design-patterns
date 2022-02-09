package com.activemesa.creational.singleton.monostate;

/**
 * Having this class, if we want to make it a singleton, all we have to do is to make its variable as static
 * So, no matter how many number of instance are created for this class, in the end it will be just a pair
 * of static fields
 * Anyway, it is a little confusing, because it does not communicate that it is indeed a singleton
 */
public class ChiefExecutiveOfficer {

    private static String name;
    private static int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        ChiefExecutiveOfficer.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        ChiefExecutiveOfficer.age = age;
    }

    @Override
    public String toString() {
        return "ChiefExecutiveOfficer{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

class Demo {
    public static void main(String[] args) {
        ChiefExecutiveOfficer ceo = new ChiefExecutiveOfficer();
        ceo.setName("Adam Smith");
        ceo.setAge(55);

        // In this case, we will see that this class is already initialized.
        ChiefExecutiveOfficer ceo2 = new ChiefExecutiveOfficer();
        System.out.println(ceo2);
    }
}
