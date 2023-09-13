package mak.springframework.spring6restmvc.service;

import lombok.RequiredArgsConstructor;
import mak.springframework.spring6restmvc.mapper.BeerMapper;
import mak.springframework.spring6restmvc.model.BeerDTO;
import mak.springframework.spring6restmvc.repositories.BeerRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
        return beerRepository.findAll().stream().map(beerMapper::beerToBeerDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        return Optional.ofNullable(beerMapper.beerToBeerDTO(beerRepository.findById(id).orElse(null)));
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beerDTO) {
        return beerMapper.beerToBeerDTO(beerRepository.save(beerMapper.beerDTOToBeer(beerDTO)));
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
