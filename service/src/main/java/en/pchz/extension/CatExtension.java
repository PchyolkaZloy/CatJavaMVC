package en.pchz.extension;

import en.pchz.dto.CatColorDto;
import en.pchz.dto.CatDto;
import en.pchz.entity.Cat;

import java.util.stream.Collectors;


public class CatExtension {
    public static CatDto asDto(Cat cat) {
        if (cat == null) {
            return CatDto.builder().build();
        }
        return CatDto.builder()
                .id(cat.getId())
                .name(cat.getName())
                .birthDate(cat.getBirthDate())
                .breed(cat.getBreed())
                .color(CatColorDto.transferToDto(cat.getColor()))
                .catMaster(cat.getCatMaster() == null ? null : CatMasterExtension.asDtoWithoutCats(cat.getCatMaster()))
                .catFriends(cat.getCatFriends() == null ? null :
                        cat.getCatFriends().stream()
                                .map(CatExtension::asDtoWithoutCatsFriends)
                                .collect(Collectors.toSet()))
                .build();
    }

    public static CatDto asDtoWithoutCatsFriends(Cat cat) {
        if (cat == null) {
            return CatDto.builder().build();
        }
        return CatDto.builder()
                .id(cat.getId())
                .name(cat.getName())
                .birthDate(cat.getBirthDate())
                .breed(cat.getBreed())
                .color(CatColorDto.transferToDto(cat.getColor()))
                .catMaster(cat.getCatMaster() == null ? null : CatMasterExtension.asDtoWithoutCats(cat.getCatMaster()))
                .catFriends(null)
                .build();
    }

    public static Cat asEntity(CatDto catDto) {
        if (catDto == null) {
            return Cat.builder().build();
        }
        return Cat.builder()
                .name(catDto.getName())
                .birthDate(catDto.getBirthDate())
                .breed(catDto.getBreed())
                .color(CatColorDto.transferToEntity(catDto.getColor()))
                .catMaster(
                        catDto.getCatMaster() == null ? null : CatMasterExtension.asEntity(catDto.getCatMaster())
                )
                .catFriends(
                        catDto.getCatFriends() == null ? null :
                                catDto.getCatFriends().stream()
                                        .map(CatExtension::asEntity)
                                        .collect(Collectors.toSet()))
                .build();
    }
}
