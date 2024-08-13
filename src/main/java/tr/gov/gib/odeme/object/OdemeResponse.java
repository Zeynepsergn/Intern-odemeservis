package tr.gov.gib.odeme.object;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OdemeResponse {
    private String oid;

    //Odeme tablosundan gelen ID
    private Integer odemeOid;
    private BigDecimal odenecekMiktar;

    //Pos Servisleri TCKN ile işlem yapmıyor. Nakit Ödeme için lazım olur mu?
    private String tckn;
    private String kartNo;
    private Integer ccv;
    private Integer sonKullanimTarihiAy;
    private Integer sonKullanimTarihiYil;
    private String kartSahibi;

    public OdemeResponse(String oid, Integer id, BorcSorguReponse borc) {
        this.oid = oid;
        this.odemeOid = id;
        this.tckn = borc.getTckn();
        setOdenecekMiktar(borc.getBorc());
        setKartNo("5110530090500");
        setCcv(681);
        setSonKullanimTarihiAy(4);
        setSonKullanimTarihiYil(2029);
        setKartSahibi("Yargı Kısakürek");
    }
}

