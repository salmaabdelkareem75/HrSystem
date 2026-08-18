package com.humanresources.hr.users;

import java.util.List;

public class StaticUsers {

    public static final List<Users> USERS = List.of(

            new Users("ahmed@hr.com", "123456", "HR"),
            new Users("mariam@hr.com", "123456", "HR"),
            new Users("omar@hr.com", "123456", "HR"),
            new Users("sara@hr.com", "123456", "HR"),
            new Users("youssef@hr.com", "123456", "HR"),
            new Users("salma@hr.com", "123456", "HR"),
            new Users("menna@hr.com", "123456", "HR"),

            new Users("karim@hr.com", "123456", "ADMIN"),
            new Users("nour@hr.com", "123456", "ADMIN"),
            new Users("mohamed@hr.com", "123456", "ADMIN")
    );
}