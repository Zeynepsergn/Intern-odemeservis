package tr.gov.gib.odeme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tr.gov.gib.odeme.entity.OdemeDetay;

@Repository
public interface OdemeDetayRepository extends JpaRepository<OdemeDetay, Integer> {

}
