package mak.springframework.spring6restmvc.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by Mehmet AKGUL on 7/29/23.
 */

@Builder
@Data
public class BeerDTO {
    private UUID id;
    private Integer version;
    @NotBlank
    //Yani, bu alan hem null olamaz hem de boş veya yalnızca beyaz boşluk karakterleri (" ", "\t", "\n", vb.) ICEREMEZ.
    @NotNull
    //  Bu anotasyon, bir alanın null olmaması gerektiğini belirtir. Yani, alanın bir değeri olmalıdır, bu değer 0, false veya boş bir dize ("") olabilir.  Sadece null değer kabul edilmez.
    private String beerName;
    @NotNull
    private String beerStyle;
    @NotBlank
    @NotNull
    private String upc;
    private Integer quantityOnHand;
    @NotNull
    private BigDecimal price;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
