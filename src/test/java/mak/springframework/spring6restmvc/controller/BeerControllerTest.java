package mak.springframework.spring6restmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mak.springframework.spring6restmvc.model.BeerDTO;
import mak.springframework.spring6restmvc.service.BeerService;
import mak.springframework.spring6restmvc.service.BeerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;
    /*
    ObjectMapper Jackson kütüphanesi tarafından sağlanan bir sınıftır
    ve JSON ile Java nesneleri arasında dönüşüm yapmaya yarar.
    Bu sınıf sayesinde Java nesneleri kolayca JSON formatına dönüştürülebilir
    veya JSON formatındaki bir metin Java nesnesine dönüştürülebilir.
     */
    @MockBean
    BeerService beerService;

    BeerServiceImpl beerServiceImpl;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<BeerDTO> beerArgumentCaptor;

    @BeforeEach
    void setUp() {
        beerServiceImpl = new BeerServiceImpl();
    }

    @Test
    void testPatchBeer() throws Exception {
        BeerDTO beer = beerServiceImpl.listBeers().get(0);
        Map<String, Object> beerMap = new HashMap<>();
        beerMap.put("beerName", "New Name");

        mockMvc.perform(patch(BeerController.BEER_PATH_ID, beer.getId()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(beerMap))).andExpect(status().isNoContent());

        verify(beerService).patchedById(uuidArgumentCaptor.capture(), beerArgumentCaptor.capture());

        assertThat(beer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
        assertThat(beerMap.get("beerName")).isEqualTo(beerArgumentCaptor.getValue().getBeerName());
    }

    @Test
    void testDeleteBeer() throws Exception {
        BeerDTO beer = beerServiceImpl.listBeers().get(0);

        given(beerService.deleteById(any())).willReturn(true);

        mockMvc.perform(delete(BeerController.BEER_PATH_ID, beer.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        verify(beerService).deleteById(uuidArgumentCaptor.capture());

        assertThat(beer.getId()).isEqualTo(uuidArgumentCaptor.getValue());

    }

    @Test
    void updateBeer() throws Exception {
        BeerDTO beer = beerServiceImpl.listBeers().get(0);

        given(beerService.updateById(any(), any())).willReturn(Optional.of(beer));

        mockMvc.perform(put(BeerController.BEER_PATH_ID, beer.getId()).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsBytes(beer))).andExpect(status().isNoContent());

        verify(beerService).updateById(any(UUID.class), any(BeerDTO.class));
    }

    @Test
    void testCreateBeerNullBeerName() throws Exception {
        BeerDTO beerDTO = BeerDTO.builder().build();
        given(beerService.saveNewBeer(any(BeerDTO.class))).willReturn(beerServiceImpl.listBeers().get(1));
        MvcResult mvcResult =mockMvc.perform(
                        post(BeerController.BEER_PATH)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(beerDTO))
                )
                .andExpect(status().isBadRequest())
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testCreateNewBeer() throws Exception {

        BeerDTO beer = beerServiceImpl.listBeers().get(0);

        beer.setVersion(null);
        beer.setId(null);

        given(beerService.saveNewBeer(any(BeerDTO.class))).willReturn(beerServiceImpl.listBeers().get(1));

        mockMvc.perform(post(BeerController.BEER_PATH).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(beer))).andExpect(status().isCreated()).andExpect(header().exists("Location"));

    }

    @Test
    void getBeerByIdNotFound() throws Exception {
        given(beerService.getBeerById(any(UUID.class))).willReturn(Optional.empty());

        mockMvc.perform(get(BeerController.BEER_PATH_ID, UUID.randomUUID())).andExpect(status().isNotFound());
    }

    @Test
    void getBeerById() throws Exception {
        BeerDTO testBeer = beerServiceImpl.listBeers().get(0);

        given(beerService.getBeerById(testBeer.getId())).willReturn(Optional.of(testBeer));

        mockMvc.perform(get(BeerController.BEER_PATH + "/" + testBeer.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.id", is(testBeer.getId().toString()))).andExpect(jsonPath("$.beerName", is(testBeer.getBeerName())));
    }

    @Test
    void listBeer() throws Exception {
        given(beerService.listBeers()).willReturn(beerServiceImpl.listBeers());

        mockMvc.perform(get(BeerController.BEER_PATH).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.length()", is(3)));
    }
}


/*
Jackson kütüphanesi, Java'da JSON ile çalışmak için en yaygın olarak kullanılan kütüphanelerden biridir. JSON formatındaki veriyi Java nesnelerine dönüştürme (deserialization) ve Java nesnelerini JSON formatına dönüştürme (serialization) işlemleri için kullanılır. Jackson, performansı, esnekliği ve kolay kullanımı ile bilinir.

Jackson'ın başlıca özellikleri şunlardır:

1. **Hızlı ve Etkin:** Jackson, hızlı bir şekilde JSON serialization ve deserialization işlemlerini gerçekleştirir.

2. **Esneklik:** Jackson, çok sayıda yapılandırma seçeneği sunar. Örneğin, sadece belirli özelliklerin dönüştürülmesini isteyip istemediğinizi, belirli isimlendirmeleri nasıl kullanacağınızı ve daha birçok şeyi özelleştirebilirsiniz.

3. **Annotations (Anotasyonlar):** Jackson, JSON ile çalışırken farklı davranışları özelleştirmek için birçok anotasyon sunar. Örneğin, `@JsonProperty` veya `@JsonIgnore` gibi anotasyonlarla nesnelerin nasıl serialize edileceğini veya deserialize edileceğini kontrol edebilirsiniz.

4. **Veri Tipleri:** Jackson, Java'nın temel veri tiplerinin yanı sıra, koleksiyonlar (listeler, map'ler vb.) ve kullanıcı tanımlı tipleri de destekler.

5. **Tree Model:** Jackson, JSON verisini ağaç yapısı olarak da temsil edebilir, bu sayede JSON nesnesinin belirli bölümlerine doğrudan erişim sağlar.

6. **Streaming API:** Jackson, JSON okuma ve yazma işlemleri için bir streaming API sunar. Bu, büyük JSON verileriyle çalışırken bellek tüketimini minimize eder.

7. **Genişletilebilirlik:** Jackson, kullanıcıların kendi serialization ve deserialization işlevlerini tanımlamalarına olanak tanır.

Java dünyasında JSON ile çalışırken Jackson, genellikle ilk tercih edilen kütüphanedir. Spring Boot gibi modern Java çerçeveleri de bu kütüphaneyi varsayılan olarak kullanmaktadır.
 */