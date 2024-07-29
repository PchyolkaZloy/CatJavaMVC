package en.pchz.service;

import en.pchz.dto.CatColorDto;
import en.pchz.dto.CatDto;
import en.pchz.dto.CatMasterDto;
import en.pchz.entity.Cat;
import en.pchz.exception.CatMasterServiceException;
import en.pchz.exception.CatServiceException;
import en.pchz.extension.CatExtension;
import en.pchz.extension.CatMasterExtension;
import en.pchz.repository.CatMasterRepository;
import en.pchz.repository.CatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CatMasterServiceImpl implements CatMasterService {
    private final CatMasterRepository catMasterRepository;
    private final CatRepository catRepository;
    private final CatService catService;

    @Override
    public CatMasterDto findCatMasterById(Integer catMasterId) {
        var optionalCatMaster = catMasterRepository.findById(catMasterId);
        if (optionalCatMaster.isEmpty()) {
            throw new CatMasterServiceException("Error! No cat masters by this ID!");
        }

        return CatMasterExtension.asDto(optionalCatMaster.get());
    }

    @Override
    public List<CatMasterDto> findAllCatMasters() {
        var catMasterList = catMasterRepository.findAll();

        List<CatMasterDto> catMasterDtoList = new ArrayList<>();
        for (var catMaster : catMasterList) {
            catMasterDtoList.add(CatMasterExtension.asDto(catMaster));
        }

        return catMasterDtoList;
    }

    @Override
    public void createCatMaster(CatMasterDto catMasterDto) {
        catMasterRepository.save(CatMasterExtension.asEntity(catMasterDto));
    }

    @Override
    public void addCatToCatMaster(Integer catMasterId, Integer catId) {
        var optionalCatMaster = catMasterRepository.findById(catMasterId);
        if (optionalCatMaster.isEmpty()) {
            throw new CatMasterServiceException("Error! No cat masters by this ID!");
        }

        var optionalCat = catRepository.findById(catId);
        if (optionalCat.isEmpty()) {
            throw new CatServiceException("Error! No cat by this ID!");
        }

        var cat = optionalCat.get();
        var catMaster = optionalCatMaster.get();
        if (catMaster.getCats() == null) {
            catMaster.setCats(new HashSet<>());
        }

        catMaster.getCats().add(cat);
        cat.setCatMaster(catMaster);

        catRepository.save(cat);
        catMasterRepository.save(catMaster);
    }

    @Override
    public CatDto findCatById(Integer catMasterId, Integer catId) {
        var optionalCatMaster = catMasterRepository.findById(catMasterId);
        if (optionalCatMaster.isEmpty()) {
            throw new CatMasterServiceException("Error! No cat masters by this ID!");
        }

        for (Cat cat : optionalCatMaster.get().getCats()) {
            if (Objects.equals(cat.getId(), catId)) {
                return CatExtension.asDto(cat);
            }
        }

        return null;
    }

    @Override
    public List<CatDto> findAllCats(Integer catMasterId) {
        var optionalCatMaster = catMasterRepository.findById(catMasterId);
        if (optionalCatMaster.isEmpty()) {
            throw new CatMasterServiceException("Error! No cat masters by this ID!");
        }

        List<CatDto> catDtoList = new ArrayList<>();
        for (Cat cat : optionalCatMaster.get().getCats()) {
            catDtoList.add(CatExtension.asDto(cat));
        }

        return catDtoList;
    }

    @Override
    public List<CatDto> findAllCatsByColor(Integer catMasterId, CatColorDto catColorDto) {
        var optionalCatMaster = catMasterRepository.findById(catMasterId);
        if (optionalCatMaster.isEmpty()) {
            throw new CatMasterServiceException("Error! No cat masters by this ID!");
        }

        var catMaster = optionalCatMaster.get();
        List<CatDto> catDtoList = catService.findAllCatsByColor(catColorDto);
        List<CatDto> filteredCatDtoList = new ArrayList<>();

        for (CatDto catDto : catDtoList) {
            if (catDto.getCatMaster().getId().equals(catMaster.getId())) {
                filteredCatDtoList.add(catDto);
            }
        }

        return filteredCatDtoList;
    }

    @Override
    public List<CatDto> findAllCatsByBreed(Integer catMasterId, String breed) {
        var optionalCatMaster = catMasterRepository.findById(catMasterId);
        if (optionalCatMaster.isEmpty()) {
            throw new CatMasterServiceException("Error! No cat masters by this ID!");
        }

        var catMaster = optionalCatMaster.get();
        List<CatDto> catDtoList = catService.findAllCatsByBreed(breed);
        List<CatDto> filteredCatDtoList = new ArrayList<>();

        for (CatDto catDto : catDtoList) {
            if (catDto.getCatMaster().getId().equals(catMaster.getId())) {
                filteredCatDtoList.add(catDto);
            }
        }

        return filteredCatDtoList;
    }

    @Override
    public void deleteCatMasterById(Integer catMasterId) {
        var optionalCatMaster = catMasterRepository.findById(catMasterId);
        if (optionalCatMaster.isEmpty()) {
            throw new CatMasterServiceException("Error! No cat masters by this ID!");
        }

        catMasterRepository.delete(optionalCatMaster.get());
    }
}
