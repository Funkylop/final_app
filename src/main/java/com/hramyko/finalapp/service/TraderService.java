package com.hramyko.finalapp.service;

import com.hramyko.finalapp.persistence.entity.Trader;

import java.util.List;
import java.util.Map;

public interface TraderService {
    List<Trader> findAll();
    Trader save(Trader trader);
    Map<Trader, Double> getTopTraders();
    double showTraderRating(int id);
}
