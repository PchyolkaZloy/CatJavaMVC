package en.pchz.repository;

import en.pchz.entity.CatMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatMasterRepository extends JpaRepository<CatMaster, Integer> {
}
