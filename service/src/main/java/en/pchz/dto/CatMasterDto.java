package en.pchz.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class CatMasterDto {
    private Integer id;
    private String name;
    private LocalDate birthDate;
    private Set<CatDto> cats;

    public CatMasterDto(String name, LocalDate birthDate) {
        this.name = name;
        this.birthDate = birthDate;
        this.cats = null;
    }
}
