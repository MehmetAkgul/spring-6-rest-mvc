package mak.springframework.spring6restmvc.mapper;

import mak.springframework.spring6restmvc.entities.Beer;
import mak.springframework.spring6restmvc.model.BeerDTO;
import org.mapstruct.Mapper;

/**
 * Created by Mehmet AKGUL on 9/12/23.
 */
@Mapper
public interface BeerMapper {

    Beer beerDTOToBeer(BeerDTO beerDTO);

    BeerDTO beerToBeerDTO(Beer beer);
}
