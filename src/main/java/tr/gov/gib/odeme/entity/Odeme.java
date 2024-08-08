package tr.gov.gib.odeme.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "odeme", schema = "gsths")
public class Odeme {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "odeme_id_gen")
    @SequenceGenerator(name = "odeme_id_gen", sequenceName = "odeme_odeme_id_seq", allocationSize = 1)
    @Column(name = "odeme_id", nullable = false)
    private Integer id;

    @Column(name = "mukellef_borc_id")
    private Integer mukellefBorcId;

    @Column(name = "optime")
    private Date optime;

    @Column(name = "odeme_durum")
    private Short odemeDurum;

}