package en.pchz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class CatDto {
    private Integer id;
    private String name;
    private LocalDate birthDate;
    private String breed;
    private CatColorDto color;
    private CatMasterDto catMaster;
    private Set<CatDto> catFriends;


    public CatDto(String name,
                  LocalDate birthDate,
                  String breed,
                  CatColorDto color) {
        this.name = name;
        this.birthDate = birthDate;
        this.breed = breed;
        this.color = color;
        this.catMaster = null;
        this.catFriends = null;
    }
}
