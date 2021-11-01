package ch.bag.screening.storage.statistics;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SymptomSqlRepository extends JpaRepository<SymptomEntity, Integer> {
  List<SymptomEntity> findAllByVersionAndNameIn(String version, List<String> names);
}
