package com.hramyko.finalapp.service.impl;

import com.hramyko.finalapp.persistence.entity.Admin;
import com.hramyko.finalapp.persistence.repository.AdminRepository;
import com.hramyko.finalapp.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    @Autowired
    AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public List<Admin> findAll() {
        return adminRepository.findAll();
    }

    @Override
    public Admin save(Admin user) {
        return adminRepository.save(user);
    }
}
