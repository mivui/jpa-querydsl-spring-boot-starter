package com.github.uinio.test;

import com.github.uinio.jpa.utils.IdFieldUtils;
import com.github.uinio.test.domain.Person;
import com.github.uinio.test.domain.User;

import java.util.ArrayList;
import java.util.Optional;


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
        Optional<String> idFieldName = IdFieldUtils.getIdFieldName(person.getClass());
        idFieldName.flatMap(field -> IdFieldUtils.getIdFieldValue(person, field)).ifPresent(System.out::println);

        //get all properties
//        EntityUtils.getProperties(person).forEach((key, value) -> System.out.println(key + " : " + value));
    }
}
