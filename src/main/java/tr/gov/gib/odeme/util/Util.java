package tr.gov.gib.odeme.util;

import tr.gov.gib.odeme.util.enums.OdemeDetayDurum;

public class Util {
    public static OdemeDetayDurum getDurum(Short durum) {
        if (durum == 0) {
            return OdemeDetayDurum.HATA;
        } else if (durum == 1) {
            return OdemeDetayDurum.BASARISIZ;
        } else if (durum == 2) {
            return OdemeDetayDurum.BASARILI;
        } else if (durum == 3) {
            return OdemeDetayDurum.IPTAL;
        } else if (durum == 4) {
            return OdemeDetayDurum.IADE;
        }
        return OdemeDetayDurum.BELIRSIZ;
    }
}
