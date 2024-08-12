package tr.gov.gib.odeme.util.enums;

public enum OdemeDetayDurum {
    BELIRSIZ("Durum Belirsiz", (short) -1),
    HATA("Hata oluştu", (short) 0),
    BASARISIZ("Ödeme Başarısız", (short) 1),
    BASARILI("Ödeme Başarılı", (short) 2),
    IADE("Ödeme İşlemi İade Edildi", (short) 3),
    IPTAL("Ödeme İşlemi İptal Edildi", (short) 4);

    private String odemeDetayDurumu;
    private Short odemeDetayDurumKodu;

    public String getOdemeDetayDurumu() {
        return odemeDetayDurumu;
    }

    public Short getOdemeDetayDurumKodu() {
        return odemeDetayDurumKodu;
    }

    OdemeDetayDurum(String odemeDetayDurumu, Short odemeDetayDurumKodu) {
        this.odemeDetayDurumu = odemeDetayDurumu;
        this.odemeDetayDurumKodu = odemeDetayDurumKodu;
    }
}
