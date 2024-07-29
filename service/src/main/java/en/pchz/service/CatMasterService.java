package en.pchz.service;

import en.pchz.dto.CatColorDto;
import en.pchz.dto.CatDto;
import en.pchz.dto.CatMasterDto;

import java.util.List;

public interface CatMasterService {
    CatMasterDto findCatMasterById(Integer catMasterId);

    List<CatMasterDto> findAllCatMasters();

    void createCatMaster(CatMasterDto catMasterDto);

    void deleteCatMasterById(Integer catMasterId);

    void addCatToCatMaster(Integer catMasterId, Integer catId);

    CatDto findCatById(Integer catMasterId, Integer catId);

    List<CatDto> findAllCats(Integer catMasterId);

    List<CatDto> findAllCatsByColor(Integer catMasterId, CatColorDto catColorDto);

    List<CatDto> findAllCatsByBreed(Integer catMasterId, String breed);
}
