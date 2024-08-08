package tr.gov.gib.odeme.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "odeme_detay", schema = "gsths")
public class OdemeDetay {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "odeme_detay_id_gen")
    @SequenceGenerator(name = "odeme_detay_id_gen", sequenceName = "odeme_detay_odeme_detay_id_seq", allocationSize = 1)
    @Column(name = "odeme_detay_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "odeme_id")
    private Odeme odeme;

    @Column(name = "mukellef_kullanici_id")
    private Integer mukellefKullanici;

    @Column(name = "vergi_id")
    private Integer vergi;

    @Column(name = "oid", length = 10)
    private String oid;

    @Column(name = "aciklama", length = 100)
    private String aciklama;

    @Column(name = "odenen_borc_miktari")
    private Double odenenBorcMiktari;

    @Column(name = "odeme_detay_durum")
    private Short odemeDetayDurum;

    @Column(name = "iade_zamani")
    private OffsetDateTime iadeZamani;

    @Column(name = "optime")
    private OffsetDateTime optime;

}