package en.pchz.service;

import en.pchz.dto.CatColorDto;
import en.pchz.dto.CatDto;
import en.pchz.entity.Cat;
import en.pchz.exception.CatServiceException;
import en.pchz.extension.CatExtension;
import en.pchz.repository.CatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CatServiceImpl implements CatService {
    private final CatRepository catRepository;

    @Override
    public CatDto findCatById(Integer id) {
        var optionalCat = catRepository.findById(id);
        if (optionalCat.isEmpty()) {
            throw new CatServiceException("Error! No cat by this ID!");
        }

        return CatExtension.asDto(optionalCat.get());
    }

    @Override
    public void createCat(CatDto catDto) {
        catRepository.save(CatExtension.asEntity(catDto));
    }

    @Override
    public Integer createCatAndReturnId(CatDto catDto) {
        var cat = catRepository.save(CatExtension.asEntity(catDto));
        return cat.getId();
    }

    @Override
    public void createFriendship(Integer firstId, Integer secondId) {
        var optionalCat1 = catRepository.findById(firstId);
        var optionalCat2 = catRepository.findById(secondId);

        if (optionalCat1.isEmpty() || optionalCat2.isEmpty()) {
            throw new CatServiceException("Error! Cats have to be in data base!");
        }

        var cat1 = optionalCat1.get();
        var cat2 = optionalCat2.get();

        if (cat1.getCatFriends() == null) {
            cat1.setCatFriends(new HashSet<>());
        }

        if (cat2.getCatFriends() == null) {
            cat2.setCatFriends(new HashSet<>());
        }

        cat1.getCatFriends().add(cat2);
        cat2.getCatFriends().add(cat1);

        catRepository.save(cat1);
        catRepository.save(cat2);
    }

    @Override
    public List<CatDto> findAll() {
        var cats = catRepository.findAll();
        List<CatDto> catDtos = new ArrayList<>();

        for (Cat cat : cats) {
            catDtos.add(CatExtension.asDto(cat));
        }

        return catDtos;
    }

    @Override
    public List<CatDto> findAllFriends(Integer id) {
        var optionalCat = catRepository.findById(id);
        if (optionalCat.isEmpty()) {
            throw new CatServiceException("Error! No cats by this ID!");
        }

        List<CatDto> catDtos = new ArrayList<>();
        for (Cat friends : optionalCat.get().getCatFriends()) {
            catDtos.add(CatExtension.asDto(friends));
        }

        return catDtos;
    }

    @Override
    public List<CatDto> findAllCatsByColor(CatColorDto catColorDto) {
        var cats = catRepository.findAllByColor(CatColorDto.transferToEntity(catColorDto));
        List<CatDto> catDtos = new ArrayList<>();

        for (Cat cat : cats) {
            catDtos.add(CatExtension.asDto(cat));
        }

        return catDtos;
    }

    @Override
    public List<CatDto> findAllCatsByBreed(String breed) {
        var cats = catRepository.findAllByBreedIgnoreCase(breed);
        List<CatDto> catDtos = new ArrayList<>();

        for (Cat cat : cats) {
            catDtos.add(CatExtension.asDto(cat));
        }

        return catDtos;
    }

    @Override
    public void deleteCatById(Integer id) {
        var optionalCat = catRepository.findById(id);
        if (optionalCat.isEmpty()) {
            throw new CatServiceException("Error! No cats by this ID!");
        }

        catRepository.delete(optionalCat.get());
    }
}
