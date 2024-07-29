package en.pchz.repository;

import en.pchz.common.CatColor;
import en.pchz.entity.Cat;
import integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


@RequiredArgsConstructor
public class CatRepositoryTest extends IntegrationTestBase {
    @Autowired
    private final CatRepository catRepository;

    @Test
    public void FindCat_ValidValues_Success() {
        // Arrange
        Cat cat = new Cat(1, "Fluffy", LocalDate.of(2019, 7, 1), "Persian", "white");

        // Act
        var actualCat = catRepository.findById(1);

        // Assert
        assertFalse(actualCat.isEmpty());
        actualCat.ifPresent(actual -> assertEquals(cat.getId(), actual.getId()));
        actualCat.ifPresent(actual -> assertEquals(cat.getName(), actual.getName()));
        actualCat.ifPresent(actual -> assertEquals(cat.getBirthDate(), actual.getBirthDate()));
        actualCat.ifPresent(actual -> assertEquals(cat.getBreed(), actual.getBreed()));
        actualCat.ifPresent(actual -> assertEquals(cat.getColor(), actual.getColor()));
    }

    @Disabled
    @Test
    public void CreateCatAndSave_ValidValues_Success() {
        // Arrange
        final Integer exceptedCatId = 1;
        final String exceptedCatName = "Бобик";
        final LocalDate exceptedCatBirthDate = LocalDate.now();
        final CatColor exceptedColor = CatColor.BLUE;
        Cat cat = new Cat("Бобик", LocalDate.now(), "дворяга", "blue");

        // Act
        catRepository.save(cat);

        // Assert
        var actualCat = catRepository.findById(1);

        assertFalse(actualCat.isEmpty());
        actualCat.ifPresent(actual -> assertEquals(exceptedCatId, actual.getId()));
        actualCat.ifPresent(actual -> assertEquals(exceptedCatName, actual.getName()));
        actualCat.ifPresent(actual -> assertEquals(exceptedCatBirthDate, actual.getBirthDate()));
        actualCat.ifPresent(actual -> assertEquals(exceptedColor, actual.getColor()));
        actualCat.ifPresent(actual -> assertEquals(cat, actual));
    }

    @Test
    public void UpdateCat_ValidValues_Success() {
        // Arrange
        final Integer catId = 1;
        final String oldCatName = "Fluffy";
        final CatColor oldCatColor = CatColor.WHITE;

        final String newCatName = "Жучка";
        final CatColor newCatColor = CatColor.BLACK;
        Cat cat = new Cat(catId, newCatName, LocalDate.of(2019, 7, 1), "Persian", newCatColor.toString());

        // Act
        catRepository.save(cat);

        // Assert
        var actualCat = catRepository.findById(1);

        assertFalse(actualCat.isEmpty());
        actualCat.ifPresent(actual -> assertEquals(catId, actual.getId()));
        actualCat.ifPresent(actual -> assertNotEquals(oldCatName, actual.getName()));
        actualCat.ifPresent(actual -> assertEquals(newCatName, actual.getName()));
        actualCat.ifPresent(actual -> assertNotEquals(oldCatColor, actual.getColor()));
        actualCat.ifPresent(actual -> assertEquals(newCatColor, actual.getColor()));
    }

    @Test
    public void DeleteCat_ValidValues_Success() {
        // Arrange
        final Integer catId = 1;
        Cat cat = new Cat(catId, "Fluffy", LocalDate.of(2019, 7, 1), "Persian", "White");

        // Act
        var preDeleteActualCat = catRepository.findById(catId);
        catRepository.delete(cat);

        // Assert
        var afterDeleteActualCat = catRepository.findById(catId);
        assertTrue(afterDeleteActualCat.isEmpty());

        assertFalse(preDeleteActualCat.isEmpty());
        preDeleteActualCat.ifPresent(preActual -> assertEquals(preActual.getId(), cat.getId()));
        preDeleteActualCat.ifPresent(preActual -> assertEquals(preActual.getName(), cat.getName()));
        preDeleteActualCat.ifPresent(preActual -> assertEquals(preActual.getColor(), cat.getColor()));
        preDeleteActualCat.ifPresent(preActual -> assertEquals(preActual.getBreed(), cat.getBreed()));
        preDeleteActualCat.ifPresent(preActual -> assertEquals(preActual.getCatMaster(), cat.getCatMaster()));
        preDeleteActualCat.ifPresent(preActual -> assertEquals(preActual.getCatFriends(), cat.getCatFriends()));
    }
}
