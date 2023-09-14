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
import java.util.concurrent.atomic.AtomicReference;
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


    /**
     * `AtomicReference` Java'nın `java.util.concurrent.atomic` paketinde yer alan bir sınıftır. Temel olarak bir nesneyi "atomik" bir şekilde güncellemeye olanak tanır. "Atomik" terimi, bir işlemin ya tamamen gerçekleşmesi ya da hiç gerçekleşmemesi gerektiği durumlar için kullanılır. Yani, bir işlem sırasında kesinti olmaz.
     * <p>
     * Kodda `AtomicReference` neden kullanıldığına gelirsek, bunun ana sebebi Java'nın lambda ifadelerinde yerel değişkenlerin efektif final (yani bir kez atandıktan sonra değiştirilemez) olmasını gerektirmesidir. `AtomicReference` bu kısıtlamayı aşmanın bir yoludur.
     * <p>
     * Bu kodda `updateById` metodu, `AtomicReference<Optional<BeerDTO>> atomicReference = new AtomicReference<>();` ile bir `AtomicReference` nesnesi oluşturuyor. Ardından `beerRepository.findById(beerId).ifPresent(...)` çağrısı ile eğer belirtilen ID'de bir bira varsa, bu biranın bilgileri güncelleniyor. Güncelleme işlemi bittikten sonra, güncellenen bira nesnesi `beerMapper` ile `BeerDTO` nesnesine çevrilip `AtomicReference` içine yerleştiriliyor.
     * <p>
     * Son olarak, `return atomicReference.get();` ile bu `AtomicReference` içerisinde tutulan değer döndürülüyor.
     * <p>
     * Özetlemek gerekirse, `AtomicReference` burada iki işlev görüyor:
     * <p>
     * 1. Lambda ifadesi içinde değiştirilebilir bir değişken oluşturulmasına olanak sağlıyor.
     * 2. Atomik bir şekilde değer güncellemesi yapıyor.
     * <p>
     * Bu sayede, çoklu thread ortamlarında bile güvenli bir şekilde değer güncellemesi yapılıyor olabilir. Ancak kodda görüldüğü üzere, bu özel durumda `AtomicReference`'in atomik özelliklerini kullanmamıza gerek yok, burada esas amacı lambda ifadeleri içinde mutabile (değiştirilebilir) bir değişken oluşturabilmek.
     *
     * @param beerId
     * @param beer
     * @return
     */
    @Override
    public Optional<BeerDTO> updateById(UUID beerId, BeerDTO beer) {
        AtomicReference<Optional<BeerDTO>> atomicReference = new AtomicReference<>();


        beerRepository.findById(beerId).ifPresentOrElse(
                foundBeer -> {
                    foundBeer.setBeerName(beer.getBeerName());
                    foundBeer.setBeerStyle(beer.getBeerStyle());
                    foundBeer.setUpc(beer.getUpc());
                    foundBeer.setPrice(beer.getPrice());
                    atomicReference.set(Optional.of(
                            beerMapper.beerToBeerDTO(beerRepository.save(foundBeer))
                    ));
                }, () -> {
                    atomicReference.set(Optional.empty());
                }
        );
        return atomicReference.get();
    }

    @Override
    public void deleteById(UUID id) {

    }

    @Override
    public void patchedById(UUID beerId, BeerDTO beer) {

    }
}
