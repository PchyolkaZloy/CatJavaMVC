package en.pchz.service;

import en.pchz.dto.CatColorDto;
import en.pchz.dto.CatDto;

import java.util.List;


public interface CatService {
    CatDto findCatById(Integer id);

    void createCat(CatDto catDto);

    Integer createCatAndReturnId(CatDto catDto);

    void deleteCatById(Integer id);

    void createFriendship(Integer firstId, Integer secondId);

    List<CatDto> findAll();
    List<CatDto> findAllFriends(Integer id);

    List<CatDto> findAllCatsByColor(CatColorDto catColorDto);

    List<CatDto> findAllCatsByBreed(String breed);
}
