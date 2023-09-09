package mak.springframework.spring6restmvc.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mak.springframework.spring6restmvc.model.Beer;
import mak.springframework.spring6restmvc.service.BeerService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Created by Mehmet AKGUL on 7/29/23.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping
public class BeerController {

    public static final String BEER_PATH = "/api/v1/beer";
    public static final String BEER_PATH_ID = BEER_PATH + "/{id}";


    private final BeerService beerService;


    @PatchMapping(BEER_PATH_ID)
    public ResponseEntity patchById(@PathVariable("id") UUID beerId, @RequestBody Beer beer) {

        beerService.patchedById(beerId, beer);


        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(BEER_PATH_ID)
    public ResponseEntity deleteById(@PathVariable UUID id) {
        beerService.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(BEER_PATH_ID)
    public ResponseEntity updateById(@PathVariable("id") UUID beerId, @RequestBody Beer beer) {

        beerService.updateById(beerId, beer);


        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(BEER_PATH)
    // @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity handlePost(@RequestBody Beer beer) {
        Beer savedBeer = beerService.saveNewBeer(beer);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location", "/api/v1/beer/" + savedBeer.getId().toString());
        return new ResponseEntity(httpHeaders, HttpStatus.CREATED);
    }


    @GetMapping(value = BEER_PATH)
    public List<Beer> listBeer() {
        return beerService.listBeers();
    }



    @GetMapping(value = BEER_PATH_ID)
    public Beer getBeerById(@PathVariable UUID id) {
        // log.debug("Get Beer By Id - in controller");
        return beerService.getBeerById(id).orElseThrow(NotFoundException::new);
    }
}
