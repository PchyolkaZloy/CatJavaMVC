package en.pchz.service;

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
        final String expectedName = "Vasya";
        final LocalDate expectedBirthDate = LocalDate.of(1980, 1, 1);

        CatMaster catMaster = new CatMaster(expectedName, expectedBirthDate);
        when(catMasterRepository.findById(1)).thenReturn(Optional.of(catMaster));

        // Act
        CatMasterDto result = catMasterService.findCatMasterById(1);

        // Assert
        assertEquals(expectedName, result.getName());
        assertEquals(expectedBirthDate, result.getBirthDate());
    }

    @Test
    void createCatMaster_ValidValues_Success() {
        // Arrange
        final String expectedName = "TK";
        final LocalDate expectedBirthDate = LocalDate.of(1980, 1, 1);

        CatMasterDto catMasterDto = CatMasterDto.builder().build();
        catMasterDto.setName(expectedName);
        catMasterDto.setBirthDate(expectedBirthDate);

        // Act
        catMasterService.createCatMaster(catMasterDto);

        // Assert
        verify(catMasterRepository).save(any(CatMaster.class));
    }

    @Test
    void addCatToCatMaster_ValidValues_Success() {
        // Arrange
        final Integer catId = 1;
        final Cat cat = new Cat(
                "Whiskers",
                LocalDate.of(2019, 1, 1),
                "Persian",
                "Gray",
                null,
                null);
        when(catRepository.findById(1)).thenReturn(Optional.of(cat));

        final Integer catMasterId = 1;
        CatMaster catMaster = new CatMaster("John Doe", LocalDate.of(1980, 1, 1));
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
        CatMaster catMaster = new CatMaster("John Doe", LocalDate.of(1980, 1, 1));
        Cat cat1 = new Cat("Whiskers", LocalDate.of(2019, 1, 1), "Persian", "GRAY");
        Cat cat2 = new Cat("Snowball", LocalDate.of(2020, 1, 1), "Siamese", "WHITE");
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
        CatMaster catMaster = new CatMaster("John Doe", LocalDate.of(1980, 1, 1));
        when(catMasterRepository.findById(1)).thenReturn(Optional.of(catMaster));

        // Act
        catMasterService.deleteCatMasterById(1);

        // Assert
        verify(catMasterRepository).delete(catMaster);
    }

    @Test
    void findAllCatsByColor_ValidIdAndColor_Success() {
        CatMaster catMaster = new CatMaster(1, "John Doe", LocalDate.of(1980, 1, 1));
        when(catMasterRepository.findById(1)).thenReturn(Optional.of(catMaster));

        final Integer catAmountWithGrayColor = 2;
        Cat cat1 = new Cat("Whiskers1", LocalDate.of(2019, 1, 1), "Persian", "GRAY", catMaster, null);
        Cat cat2 = new Cat("Snowball", LocalDate.of(2020, 1, 1), "Siamese", "WHITE", catMaster, null);
        Cat cat3 = new Cat("Whiskers2", LocalDate.of(2019, 1, 1), "Persian", "GRAY", catMaster, null);
        catMaster.getCats().add(cat1);
        catMaster.getCats().add(cat2);
        catMaster.getCats().add(cat3);

        CatDto catDto1 = CatExtension.asDtoWithoutCatsFriends(cat1);
        catDto1.getCatMaster().setId(1);
        CatDto catDto2 = CatExtension.asDtoWithoutCatsFriends(cat3);
        catDto2.getCatMaster().setId(1);
        when(catService.findAllCatsByColor(CatColorDto.GRAY)).thenReturn(List.of(catDto1, catDto2));
        // Act
        List<CatDto> catDtoList = catMasterService.findAllCatsByColor(1, CatColorDto.GRAY);

        // Assert
        verify(catMasterRepository).findById(1);
        assertEquals(catAmountWithGrayColor, catDtoList.size());
        assertTrue(catDtoList.contains(catDto1));
        assertTrue(catDtoList.contains(catDto2));
    }

    @Test
    void findAllCatsByBreed_ValidIdAndBreed_Success() {
        CatMaster catMaster = new CatMaster(1, "John Doe", LocalDate.of(1980, 1, 1));
        when(catMasterRepository.findById(1)).thenReturn(Optional.of(catMaster));

        final Integer catAmountWithPersianBreed = 2;
        Cat cat1 = new Cat("Whiskers1", LocalDate.of(2019, 1, 1), "Persian", "GRAY", catMaster, null);
        Cat cat2 = new Cat("Snowball", LocalDate.of(2020, 1, 1), "Siamese", "WHITE", catMaster, null);
        Cat cat3 = new Cat("Whiskers2", LocalDate.of(2019, 1, 1), "Persian", "GRAY", catMaster, null);
        catMaster.getCats().add(cat1);
        catMaster.getCats().add(cat2);
        catMaster.getCats().add(cat3);

        CatDto catDto1 = CatExtension.asDtoWithoutCatsFriends(cat1);
        catDto1.getCatMaster().setId(1);
        CatDto catDto2 = CatExtension.asDtoWithoutCatsFriends(cat3);
        catDto2.getCatMaster().setId(1);
        when(catService.findAllCatsByBreed("Persian")).thenReturn(List.of(catDto1, catDto2));

        // Act
        List<CatDto> catDtoList = catMasterService.findAllCatsByBreed(1, "Persian");

        // Assert
        verify(catMasterRepository).findById(1);
        assertEquals(catAmountWithPersianBreed, catDtoList.size());
        assertTrue(catDtoList.contains(catDto1));
        assertTrue(catDtoList.contains(catDto2));
    }
}
