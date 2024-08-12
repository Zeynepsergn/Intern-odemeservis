package tr.gov.gib.odeme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tr.gov.gib.odeme.entity.Odeme;


@Repository
public interface OdemeRepository extends JpaRepository<Odeme, Integer> {

}
