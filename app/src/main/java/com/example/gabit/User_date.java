package com.example.gabit;

public class User_date {
    private String name;
    private int age;

    // 생성자
    public User_date(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Getter와 Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}