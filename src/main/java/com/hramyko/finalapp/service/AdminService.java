package com.hramyko.finalapp.service;

import com.hramyko.finalapp.persistence.entity.Admin;

import java.util.List;

public interface AdminService {
    List<Admin> findAll();
    Admin save(Admin user);
}
