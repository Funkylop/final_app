package com.hramyko.finalapp.service.impl;

import com.hramyko.finalapp.persistence.entity.WaitingList;
import com.hramyko.finalapp.persistence.repository.WaitingListRepository;
import com.hramyko.finalapp.service.WaitingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class WaitingListServiceImpl implements WaitingListService {

    private final WaitingListRepository waitingListRepository;

    @Autowired
    WaitingListServiceImpl(WaitingListRepository waitingListRepository) {
        this.waitingListRepository = waitingListRepository;
    }

    @Transactional
    @Override
    public int getUserId(int formId) {
        Optional<WaitingList> optionalWaitingList = waitingListRepository.findById(formId);
        if (optionalWaitingList.isPresent()) {
            return optionalWaitingList.get().getUser().getId();
        } else throw new RuntimeException("Form with such id doesn't exist");
    }

    @Transactional
    @Override
    public void deleteUserForm(int formId) {
        waitingListRepository.deleteById(formId);
    }

    @Transactional
    @Override
    public void addUserForm(WaitingList waitingList) {
        waitingListRepository.save(waitingList);
    }

    @Transactional
    @Override
    public List<WaitingList> findAll() {
        return waitingListRepository.findAll();
    }
}
