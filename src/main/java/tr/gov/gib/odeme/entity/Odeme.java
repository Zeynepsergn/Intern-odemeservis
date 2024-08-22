package tr.gov.gib.odeme.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tr.gov.gib.odeme.object.BorcSorguReponse;
import tr.gov.gib.odeme.util.enums.OdemeDurum;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(name = "vergi_tur_id")
    private Integer vergiTur;

    @Column(name = "mukellef_kullanici_id")
    private Long mukellefKullaniciId;

    public Odeme(BorcSorguReponse borc) {
        this.mukellefBorcId = borc.getMukellefBorcId();
        this.odemeDurum = OdemeDurum.ODEME_GELDI.getOdemeDurumKodu();
        this.optime = new Date();
    }

}