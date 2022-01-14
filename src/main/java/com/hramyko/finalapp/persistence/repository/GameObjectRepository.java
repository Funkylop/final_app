package com.hramyko.finalapp.persistence.repository;

import com.hramyko.finalapp.persistence.entity.GameObject;
import com.hramyko.finalapp.persistence.entity.Trader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameObjectRepository extends JpaRepository<GameObject, Integer> {
    List<GameObject> findAllByTrader(Trader trader);
    List<GameObject> findAllByGameIdIn(List<Integer> ids);
}
