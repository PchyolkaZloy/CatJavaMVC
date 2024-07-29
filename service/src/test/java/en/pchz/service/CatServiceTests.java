package en.pchz.service;

import en.pchz.dto.CatColorDto;
import en.pchz.dto.CatDto;
import en.pchz.entity.Cat;
import en.pchz.exception.CatServiceException;
import en.pchz.repository.CatRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CatServiceTests {
    @Mock
    private CatRepository catRepository;
    @InjectMocks
    private CatServiceImpl catService;


    @Test
    void FindCatById_ValidValues_Success() {
        // Arrange
        final String exceptedName = "Whiskers";
        final LocalDate exceptedBirthDate = LocalDate.MIN;
        final String exceptedBreed = "Persian";
        final CatColorDto exceptedColor = CatColorDto.GRAY;

        // Act
        Cat cat = new Cat("Whiskers", LocalDate.MIN, "Persian", "Gray");
        when(catRepository.findById(1)).thenReturn(Optional.of(cat));

        CatDto result = catService.findCatById(1);

        // Assert
        assertEquals(exceptedName, result.getName());
        assertEquals(exceptedBirthDate, result.getBirthDate());
        assertEquals(exceptedBreed, result.getBreed());
        assertEquals(exceptedColor, result.getColor());
    }

    @Test
    void createCat_ValidValues_Success() {
        // Arrange
        CatDto catDto = CatDto.builder().build();
        catDto.setName("Whiskers");
        catDto.setBirthDate(LocalDate.MIN);
        catDto.setBreed("Persian");
        catDto.setColor(CatColorDto.GRAY);

        // Act
        catService.createCat(catDto);

        // Assert
        verify(catRepository).save(any(Cat.class));
    }

    @Test
    void createFriendship_ValidValues_Success() {
        // Arrange
        Cat cat1 = new Cat();
        Cat cat2 = new Cat();
        when(catRepository.findById(1)).thenReturn(Optional.of(cat1));
        when(catRepository.findById(2)).thenReturn(Optional.of(cat2));

        // Act
        catService.createFriendship(1, 2);

        // Assert
        assertTrue(cat1.getCatFriends().contains(cat2));
        assertTrue(cat2.getCatFriends().contains(cat1));
    }

    @Test
    void findAllFriends_ValidValues_Success() {
        // Arrange
        Cat cat = new Cat("Whiskers", LocalDate.MIN, "Persian", "Gray");
        cat.setCatFriends(new HashSet<>());

        Cat friend1 = new Cat("Killer", LocalDate.MIN, "Indian", "Black");
        Cat friend2 = new Cat("Boomer", LocalDate.MIN, "American", "Red");

        cat.getCatFriends().add(friend1);
        cat.getCatFriends().add(friend2);
        when(catRepository.findById(1)).thenReturn(Optional.of(cat));

        // Act
        List<CatDto> result = catService.findAllFriends(1);

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    void createFriendship_CatNotFound_ThrowsCatServiceException() {
        // Arrange
        when(catRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CatServiceException.class, () -> catService.createFriendship(1, 2));
    }

    @Test
    void createFriendship_BothCatsNotFound_ThrowsException() {
        // Arrange
        when(catRepository.findById(1)).thenReturn(Optional.empty());
        when(catRepository.findById(2)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CatServiceException.class, () -> catService.createFriendship(1, 2));
    }

    @Test
    void findAllFriends_CatNotFound_ThrowsException() {
        // Arrange
        when(catRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CatServiceException.class, () -> catService.findAllFriends(1));
    }
}
