package tr.gov.gib.odeme.util.enums;

public enum SposFposDurum {
    HATA_OLUSTU("Hata oluştu", (short) 0),
    BASARISIZ_ODEME("Ödeme Başarısız", (short) 1),
    BASARILI_ODEME("Ödeme Başarılı", (short) 2),
    ODEME_IPTAL_EDILDI("Ödeme İşlemi İptal Edildi", (short) 3);

    private String sposFposDurumu;
    private Short sposFposDurumKodu;

    public String getSposFposDurumu() {
        return sposFposDurumu;
    }

    public Short getSposFposDurumKodu() {
        return sposFposDurumKodu;
    }

    SposFposDurum(String sposFposDurumu, Short sposFposDurumKodu) {
        this.sposFposDurumu = sposFposDurumu;
        this.sposFposDurumKodu = sposFposDurumKodu;
    }
}
