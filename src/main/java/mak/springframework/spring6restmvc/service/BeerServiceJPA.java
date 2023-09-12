package mak.springframework.spring6restmvc.service;

import lombok.RequiredArgsConstructor;
import mak.springframework.spring6restmvc.mapper.BeerMapper;
import mak.springframework.spring6restmvc.mapper.BeerMapperImpl;
import mak.springframework.spring6restmvc.model.BeerDTO;
import mak.springframework.spring6restmvc.repositories.BeerRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by Mehmet AKGUL on 9/12/23.
 */
@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;
    @Override
    public List<BeerDTO> listBeers() {
        return null;
    }

    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        return Optional.empty();
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beer) {
        return null;
    }

    @Override
    public void updateById(UUID beerId, BeerDTO beer) {

    }

    @Override
    public void deleteById(UUID id) {

    }

    @Override
    public void patchedById(UUID beerId, BeerDTO beer) {

    }
}
