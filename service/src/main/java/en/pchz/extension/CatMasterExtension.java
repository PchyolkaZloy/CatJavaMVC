package en.pchz.extension;

import en.pchz.dto.CatMasterDto;
import en.pchz.entity.CatMaster;

import java.util.stream.Collectors;


public class CatMasterExtension {
    public static CatMasterDto asDto(CatMaster catMaster) {
        if (catMaster == null) {
            return CatMasterDto.builder().build();
        }
        return CatMasterDto.builder()
                .id(catMaster.getId())
                .name(catMaster.getName())
                .birthDate(catMaster.getBirthDate())
                .cats(catMaster.getCats().stream()
                        .map(CatExtension::asDtoWithoutCatsFriends)
                        .collect(Collectors.toSet()))
                .build();
    }

    public static CatMasterDto asDtoWithoutCats(CatMaster catMaster) {
        if (catMaster == null) {
            return CatMasterDto.builder().build();
        }
        return CatMasterDto.builder()
                .id(catMaster.getId())
                .name(catMaster.getName())
                .birthDate(catMaster.getBirthDate())
                .cats(null)
                .build();
    }

    public static CatMaster asEntity(CatMasterDto catMasterDto) {
        if (catMasterDto == null) {
            return new CatMaster(null, null, null, null);
        }
        return new CatMaster(
                catMasterDto.getId(),
                catMasterDto.getName(),
                catMasterDto.getBirthDate(),
                catMasterDto.getCats() == null ? null :
                        catMasterDto.getCats().stream()
                                .map(CatExtension::asEntity)
                                .collect(Collectors.toSet()));
    }
}
