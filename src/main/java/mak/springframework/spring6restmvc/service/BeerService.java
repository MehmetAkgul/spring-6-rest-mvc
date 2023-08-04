package mak.springframework.spring6restmvc.service;

import mak.springframework.spring6restmvc.model.Beer;

import java.util.List;
import java.util.UUID;

public interface BeerService {
    List<Beer> listBeers();

    Beer getBeerById(UUID id);

    Beer saveNewBeer(Beer beer);

    void updateById(UUID beerId, Beer beer);

    void deleteById(UUID id);

    void patchedById(UUID beerId, Beer beer);
}
