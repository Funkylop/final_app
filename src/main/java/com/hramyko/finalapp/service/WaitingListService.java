package com.hramyko.finalapp.service;

import com.hramyko.finalapp.persistence.entity.WaitingList;

import java.util.List;

public interface WaitingListService {
    int getUserId(int formId);
    void deleteUserForm(int formId);
    void addUserForm(WaitingList waitingList);
    List<WaitingList> findAll();
}
