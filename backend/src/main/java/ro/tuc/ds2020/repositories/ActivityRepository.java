package ro.tuc.ds2020.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.tuc.ds2020.entities.Activity;

import java.util.UUID;

public interface ActivityRepository extends JpaRepository<Activity, UUID> {
}
