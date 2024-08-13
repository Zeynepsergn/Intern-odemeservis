package tr.gov.gib.odeme.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tr.gov.gib.odeme.object.BorcSorguReponse;
import tr.gov.gib.odeme.util.enums.OdemeDurum;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "odeme", schema = "gsths")
@AllArgsConstructor
@NoArgsConstructor
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

    @OneToOne(mappedBy = "odeme")
    private OdemeDetay odemeDetays;

    public Odeme(BorcSorguReponse borc) {
        this.mukellefBorcId = borc.getMukellefBorcId();
        this.odemeDurum = OdemeDurum.ODEME_GELDI.getOdemeDurumKodu();
        this.optime = new Date();
    }
}