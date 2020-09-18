package com.github.uinio.test;

import com.github.uinio.jpa.utils.KeyUtils;
import com.github.uinio.test.domain.Person;
import com.github.uinio.test.domain.User;

import java.util.ArrayList;


public class UnitTest {

    public static void main(String[] args) {
        Person person = new User();
        person.setId(1231241);
        person.setSex("man");
        ArrayList<String> users = new ArrayList<>();
        users.add("mike");
        person.setUsers(users);

//        Test copyNotNullProperties
//        Person person1 = new Person();
//        person1.setSex("woman");
//        users.add("hello");
//        users.add("word");
//        person1.setUsers(users);
//        JpaServiceUtils.copyNotNullProperties(person1,person);
//        System.out.println(person1.getId());
//        System.out.println(person1.getSex());
//        person1.getUsers().forEach(System.out::println);

        // get id test
        System.out.println(KeyUtils.fieldName(person.getClass()));
        System.out.println(KeyUtils.fieldValue(person));

        //get all properties
//        EntityUtils.getProperties(person).forEach((key, value) -> System.out.println(key + " : " + value));
    }
}
