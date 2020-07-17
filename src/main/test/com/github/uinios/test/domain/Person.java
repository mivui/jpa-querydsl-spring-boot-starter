package com.github.uinios.test.domain;

import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

public class Person {

    @Id
    private Integer id;

    private String sex;

    private Integer age;

    @ManyToMany
    private List<String> users;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }
}
