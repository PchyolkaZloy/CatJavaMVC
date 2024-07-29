package en.pchz.repository;

import en.pchz.common.CatColor;
import en.pchz.entity.Cat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CatRepository extends JpaRepository<Cat, Integer> {
    Set<Cat> findAllByColor(CatColor catColor);

    Set<Cat> findAllByBreedIgnoreCase(String breed);
}
