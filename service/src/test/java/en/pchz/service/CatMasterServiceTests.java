package en.pchz.service;

import en.pchz.common.CatColor;
import en.pchz.dto.CatColorDto;
import en.pchz.dto.CatDto;
import en.pchz.dto.CatMasterDto;
import en.pchz.entity.Cat;
import en.pchz.entity.CatMaster;
import en.pchz.extension.CatExtension;
import en.pchz.repository.CatMasterRepository;
import en.pchz.repository.CatRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CatMasterServiceTests {
    @Mock
    private CatMasterRepository catMasterRepository;
    @Mock
    private CatRepository catRepository;
    @Mock
    private CatService catService;
    @InjectMocks
    private CatMasterServiceImpl catMasterService;

    @Test
    void findCatMasterById_ValidId_Success() {
        // Arrange
        final Integer catMasterId = 1;
        final String expectedName = "Vasya";
        final LocalDate expectedBirthDate = LocalDate.of(1980, 1, 1);
        CatMaster catMaster = CatMaster.builder()
                .id(catMasterId)
                .name(expectedName)
                .birthDate(expectedBirthDate)
                .cats(new HashSet<>())
                .build();

        when(catMasterRepository.findById(catMasterId)).thenReturn(Optional.of(catMaster));

        // Act
        CatMasterDto result = catMasterService.findCatMasterById(catMasterId);

        // Assert
        assertEquals(expectedName, result.getName());
        assertEquals(expectedBirthDate, result.getBirthDate());
    }

    @Test
    void createCatMaster_ValidValues_Success() {
        // Arrange
        final String expectedName = "KT";
        final LocalDate expectedBirthDate = LocalDate.of(1980, 1, 1);

        CatMasterDto catMasterDto = CatMasterDto.builder()
                .name(expectedName)
                .birthDate(expectedBirthDate)
                .build();

        // Act
        catMasterService.createCatMaster(catMasterDto);

        // Assert
        verify(catMasterRepository).save(any(CatMaster.class));
    }

    @Test
    void addCatToCatMaster_ValidValues_Success() {
        // Arrange
        final Integer catId = 1;
        final Cat cat = Cat.builder()
                .name("Whiskers")
                .birthDate(LocalDate.of(2019, 1, 1))
                .breed("Persian")
                .color(CatColor.BEIGE)
                .build();
        when(catRepository.findById(1)).thenReturn(Optional.of(cat));

        final Integer catMasterId = 1;
        CatMaster catMaster = CatMaster.builder()
                .name("John")
                .birthDate(LocalDate.of(1980, 1, 1))
                .build();
        when(catMasterRepository.findById(1)).thenReturn(Optional.of(catMaster));

        // Act
        catMasterService.addCatToCatMaster(catMasterId, catId);

        // Assert
        verify(catRepository).findById(1);
        verify(catMasterRepository).findById(1);
        verify(catRepository).save(cat);
        verify(catMasterRepository).save(catMaster);
    }

    @Test
    void findAllCats_ValidId_Success() {
        // Arrange
        CatMaster catMaster = CatMaster.builder()
                .name("John")
                .birthDate(LocalDate.of(1980, 1, 1))
                .cats(new HashSet<>())
                .build();
        Cat cat1 = Cat.builder()
                .name("Whiskers")
                .birthDate(LocalDate.of(2019, 1, 1))
                .breed("Persian")
                .color(CatColor.BEIGE)
                .build();
        Cat cat2 = Cat.builder()
                .name("Snowball")
                .birthDate(LocalDate.of(2020, 1, 1))
                .breed("Siamese")
                .color(CatColor.BLACK)
                .build();

        catMaster.getCats().add(cat1);
        catMaster.getCats().add(cat2);
        when(catMasterRepository.findById(1)).thenReturn(Optional.of(catMaster));

        // Act
        List<CatDto> result = catMasterService.findAllCats(1);

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    void deleteCatMasterById_ValidId_Success() {
        // Arrange
        CatMaster catMaster = CatMaster.builder()
                .name("John")
                .birthDate(LocalDate.of(1980, 1, 1))
                .build();
        when(catMasterRepository.findById(1)).thenReturn(Optional.of(catMaster));

        // Act
        catMasterService.deleteCatMasterById(1);

        // Assert
        verify(catMasterRepository).delete(catMaster);
    }

    @Test
    void findAllCatsByColor_ValidIdAndColor_Success() {
        final Integer catMasterId = 1;
        CatMaster catMaster = CatMaster.builder()
                .id(catMasterId)
                .name("John")
                .birthDate(LocalDate.of(1980, 1, 1))
                .cats(new HashSet<>())
                .build();
        when(catMasterRepository.findById(1)).thenReturn(Optional.of(catMaster));

        final Integer catAmountWithBlackColor = 2;
        Cat cat1 = Cat.builder()
                .name("Whiskers")
                .birthDate(LocalDate.of(2019, 1, 1))
                .breed("Persian")
                .color(CatColor.BEIGE)
                .catMaster(catMaster)
                .build();
        Cat cat2 = Cat.builder()
                .name("Snowball")
                .birthDate(LocalDate.of(2020, 1, 1))
                .breed("Siamese")
                .color(CatColor.BLACK)
                .catMaster(catMaster)
                .build();
        Cat cat3 = Cat.builder()
                .name("Snowball2")
                .birthDate(LocalDate.of(2020, 1, 1))
                .breed("Siamese")
                .color(CatColor.BLACK)
                .catMaster(catMaster)
                .build();
        catMaster.getCats().add(cat1);
        catMaster.getCats().add(cat2);
        catMaster.getCats().add(cat3);

        CatDto catDto1 = CatExtension.asDtoWithoutCatsFriends(cat1);
        catDto1.getCatMaster().setId(catMasterId);
        CatDto catDto2 = CatExtension.asDtoWithoutCatsFriends(cat3);
        catDto2.getCatMaster().setId(catMasterId);
        when(catService.findAllCatsByColor(CatColorDto.GRAY)).thenReturn(List.of(catDto1, catDto2));
        // Act
        List<CatDto> catDtoList = catMasterService.findAllCatsByColor(catMasterId, CatColorDto.GRAY);

        // Assert
        verify(catMasterRepository).findById(catMasterId);
        assertEquals(catAmountWithBlackColor, catDtoList.size());
        assertTrue(catDtoList.contains(catDto1));
        assertTrue(catDtoList.contains(catDto2));
    }

    @Test
    void findAllCatsByBreed_ValidIdAndBreed_Success() {
        final Integer catMasterId = 1;
        CatMaster catMaster = CatMaster.builder()
                .id(catMasterId)
                .name("John")
                .birthDate(LocalDate.of(1980, 1, 1))
                .cats(new HashSet<>())
                .build();
        when(catMasterRepository.findById(catMasterId)).thenReturn(Optional.of(catMaster));

        final Integer catAmountWithSiameseBreed = 2;
        Cat cat1 = Cat.builder()
                .name("Whiskers")
                .birthDate(LocalDate.of(2019, 1, 1))
                .breed("Persian")
                .color(CatColor.BEIGE)
                .catMaster(catMaster)
                .build();
        Cat cat2 = Cat.builder()
                .name("Snowball")
                .birthDate(LocalDate.of(2020, 1, 1))
                .breed("Siamese")
                .color(CatColor.BLACK)
                .catMaster(catMaster)
                .build();
        Cat cat3 = Cat.builder()
                .name("Snowball2")
                .birthDate(LocalDate.of(2020, 1, 1))
                .breed("Siamese")
                .color(CatColor.BLACK)
                .catMaster(catMaster)
                .build();
        catMaster.getCats().add(cat1);
        catMaster.getCats().add(cat2);
        catMaster.getCats().add(cat3);

        CatDto catDto1 = CatExtension.asDtoWithoutCatsFriends(cat1);
        catDto1.getCatMaster().setId(catMasterId);
        CatDto catDto2 = CatExtension.asDtoWithoutCatsFriends(cat3);
        catDto2.getCatMaster().setId(catMasterId);
        when(catService.findAllCatsByBreed("Persian")).thenReturn(List.of(catDto1, catDto2));

        // Act
        List<CatDto> catDtoList = catMasterService.findAllCatsByBreed(catMasterId, "Persian");

        // Assert
        verify(catMasterRepository).findById(catMasterId);
        assertEquals(catAmountWithSiameseBreed, catDtoList.size());
        assertTrue(catDtoList.contains(catDto1));
        assertTrue(catDtoList.contains(catDto2));
    }
}
