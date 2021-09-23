package com.service;

import java.util.List;

public interface UserService {

    void setConnection();
    String getAllUsers();
    String addUser();
    String editUser();
    String deleteUser();
}