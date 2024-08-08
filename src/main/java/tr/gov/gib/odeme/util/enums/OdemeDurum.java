package tr.gov.gib.odeme.util.enums;

public enum OdemeDurum {
    ODEME_GELDI("Ödeme Geldi", (short) 0),
    ODEME_GERCEKLESTI("Ödeme Gerçekleşti", (short) 1),
    ODEME_DETAY_YAZILDI("Detay Yazildi", (short) 2),
    ODEME_TAHSIL_EDILDI("Tahsil Edildi", (short) 3);

    private String odemeDurumu;
    private Short odemeDurumKodu;

    public String getOdemeDurumu() {
        return odemeDurumu;
    }

    public Short getOdemeDurumKodu() {
        return odemeDurumKodu;
    }

    OdemeDurum(String odemeDurumu, Short odemeDurumKodu) {
        this.odemeDurumu = odemeDurumu;
        this.odemeDurumKodu = odemeDurumKodu;
    }
}
