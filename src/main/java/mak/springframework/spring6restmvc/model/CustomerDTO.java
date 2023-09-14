package mak.springframework.spring6restmvc.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by Mehmet AKGUL on 7/29/23.
 */

@Builder
@Data
public class CustomerDTO {

    private UUID id;
    private Integer version;
    private String customerName;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

}
