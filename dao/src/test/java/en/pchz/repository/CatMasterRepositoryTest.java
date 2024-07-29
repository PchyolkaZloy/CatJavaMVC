package en.pchz.repository;

import en.pchz.entity.CatMaster;
import integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


@RequiredArgsConstructor
public class CatMasterRepositoryTest extends IntegrationTestBase {
    @Autowired
    private final CatMasterRepository catMasterRepository;

    @Test
    public void FindCatMaster_ValidValues_Success() {
        // Arrange
        final Integer catMasterId = 1;
        CatMaster exceptedCatMaster = CatMaster.builder()
                .id(catMasterId)
                .name("John")
                .birthDate(LocalDate.of(1990, 5, 15))
                .build();

        // Act
        var actualCatMaster = catMasterRepository.findById(catMasterId);

        // Assert
        assertFalse(actualCatMaster.isEmpty());
        actualCatMaster.ifPresent(actual -> assertEquals(exceptedCatMaster.getId(), actual.getId()));
        actualCatMaster.ifPresent(actual -> assertEquals(exceptedCatMaster.getName(), actual.getName()));
        actualCatMaster.ifPresent(actual -> assertEquals(exceptedCatMaster.getBirthDate(), actual.getBirthDate()));
    }

    @Disabled
    @Test
    public void CreateCatMasterAndSave_ValidValues_Success() {
        // Arrange
        final Integer newCatMasterId = 4;
        CatMaster newCatMaster = CatMaster.builder()
                .name("Vova")
                .birthDate(LocalDate.now())
                .build();

        // Act
        catMasterRepository.save(newCatMaster);

        // Assert
        var actualCatMaster = catMasterRepository.findById(newCatMasterId);

        assertFalse(actualCatMaster.isEmpty());
        actualCatMaster.ifPresent(actual -> assertEquals(newCatMaster.getId(), actual.getId()));
        actualCatMaster.ifPresent(actual -> assertEquals(newCatMaster.getName(), actual.getName()));
        actualCatMaster.ifPresent(actual -> assertEquals(newCatMaster.getBirthDate(), actual.getBirthDate()));
    }

    @Test
    public void UpdateCatMaster_ValidValues_Success() {
        // Arrange
        final Integer catMasterId = 1;
        final String oldCatMasterName = "John";
        final LocalDate oldCatMasterBirthDate = LocalDate.of(1990, 5, 15);

        final String newCatMasterName = "Vovan";
        final LocalDate newCatMasterBirthDate = LocalDate.now();
        CatMaster catMaster = CatMaster.builder()
                .id(catMasterId)
                .name(newCatMasterName)
                .birthDate(newCatMasterBirthDate)
                .build();

        // Act
        catMasterRepository.save(catMaster);

        // Assert
        var actualCatMaster = catMasterRepository.findById(catMasterId);

        assertFalse(actualCatMaster.isEmpty());
        actualCatMaster.ifPresent(actual -> assertEquals(catMasterId, actual.getId()));
        actualCatMaster.ifPresent(actual -> assertNotEquals(oldCatMasterName, actual.getName()));
        actualCatMaster.ifPresent(actual -> assertEquals(newCatMasterName, actual.getName()));
        actualCatMaster.ifPresent(actual -> assertNotEquals(oldCatMasterBirthDate, actual.getBirthDate()));
        actualCatMaster.ifPresent(actual -> assertEquals(newCatMasterBirthDate, actual.getBirthDate()));
    }


    @Test
    public void DeleteCatMaster_ValidValues_Success() {
        // Arrange
        final Integer catMasterId = 2;
        CatMaster catMaster = CatMaster.builder()
                .id(catMasterId)
                .name("Alice")
                .birthDate(LocalDate.of(1985, 10, 20))
                .build();

        // Act
        var preDeleteActualCatMaster = catMasterRepository.findById(catMasterId);
        catMasterRepository.delete(catMaster);

        // Assert
        var afterDeleteActualCatMaster = catMasterRepository.findById(catMasterId);
        assertTrue(afterDeleteActualCatMaster.isEmpty());

        assertFalse(preDeleteActualCatMaster.isEmpty());
        preDeleteActualCatMaster.ifPresent(preActual -> assertEquals(preActual.getId(), catMaster.getId()));
        preDeleteActualCatMaster.ifPresent(preActual -> assertEquals(preActual.getName(), catMaster.getName()));
        preDeleteActualCatMaster.ifPresent(preActual -> assertEquals(preActual.getBirthDate(), catMaster.getBirthDate()));
        preDeleteActualCatMaster.ifPresent(preActual -> assertEquals(preActual.getCats(), catMaster.getCats()));
    }
}