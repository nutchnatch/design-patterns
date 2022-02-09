package com.activemesa.solid.dip;

import org.javatuples.Triplet;

import javax.management.relation.Relation;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Person {

    public String name;

    public Person(String name) {
        this.name = name;
    }


}

/**
 * This is the abstraction that we should have to implement in the low level module
 */
interface RelationshipBrowser {
    List<Person> findAllChildrenOf(String name);
}

/**
 * low level class because it is related to data storage,
 * it simply keeps a list and give access to it
 * So, it does not have any business logic, it simply allows to  manipulate the list and
 * which is a single responsibility (from single responsibility principle)
 */
class Relationships implements RelationshipBrowser{

    private List<Triplet<Person, Relationship, Person>> relations = new ArrayList<Triplet<Person, Relationship, Person>>();

    //problem - we are exposing an internal storage implementation of relations as a public getter to everyone to access.
    public List<Triplet<Person, Relationship, Person>> getRelations() {
        return relations;
    }

    public void addParentAndChild(Person parent, Person child) {
        relations.add(new Triplet<Person, Relationship, Person>(parent, Relationship.PARENT, child));
        relations.add(new Triplet<Person, Relationship, Person>(child, Relationship.CHILD, parent));
    }

    @Override
    public List<Person> findAllChildrenOf(String name) {
        return relations.stream()
                .filter(x -> x.getValue0().name.equals("John") && x.getValue1() == Relationship.PARENT)
                .map(Triplet::getValue2)
                .collect(Collectors.toList());
    }
}

/**
 * high level class, because it allows to operate some sort of operations over those low level constructs
 * so, it is higher to the end user, because he does not care about the storage implementation, he just care about
 * the actual research, about the search results.
 */
class Research {
        /**
         * this is a violation of dependency inversion principle, because, it
         * has a dependency on low level module (Relationships)
         * Instead, we should only depend on abstractions
         * @param relationships
         */
//    public Research(Relationships relationships) {
//        List<Triplet<Person, Relationship, Person>> relations = relationships.getRelations();
//        relations.stream()
//                .filter(x -> x.getValue0().name.equals("John") && x.getValue1() == Relationship.PARENT)
//                .forEach(ch -> System.out.println(
//                        "John has a child called: " + ch.getValue2().name
//                ));
//    }

        /**
         * Here we are respecting the dependency inversion principle where we are not depending on an implementation
         * Instead we are depending on an abstraction. With this abstraction, we don't have to change anything on Demo
         * class, the great advantage here is that we not depending on a static implementation and so, we can change it
         * anytime we want to without impacting the rest of the code.
         */
    public Research(RelationshipBrowser browser) {
        List<Person> children =  browser.findAllChildrenOf("John");
        for(Person person : children) {
            System.out.println("John has a child " + person.name);
        }
    }
}

class Demo{
    public static void main(String[] args) {
        Person parent = new Person("John");
        Person child1 = new Person("Chris");
        Person child2 = new Person("Matt");

        Relationships relationships = new Relationships();
        relationships.addParentAndChild(parent, child1);
        relationships.addParentAndChild(parent, child2);

        new Research(relationships);
    }
}
