package com.hramyko.finalapp.service;

import com.hramyko.finalapp.persistence.entity.CommonUser;

import java.util.List;

public interface CommonUserService {
    List<CommonUser> findAll();
    CommonUser save(CommonUser user);
}
