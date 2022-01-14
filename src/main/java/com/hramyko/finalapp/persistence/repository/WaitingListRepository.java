package com.hramyko.finalapp.persistence.repository;

import com.hramyko.finalapp.persistence.entity.WaitingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WaitingListRepository extends JpaRepository<WaitingList, Integer> {
}
