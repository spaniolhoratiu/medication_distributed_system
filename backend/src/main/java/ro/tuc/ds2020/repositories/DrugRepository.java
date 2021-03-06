package ro.tuc.ds2020.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.tuc.ds2020.entities.Drug;

import java.util.UUID;

public interface DrugRepository extends JpaRepository<Drug, UUID>  {
}
