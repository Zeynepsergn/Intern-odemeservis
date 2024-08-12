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
}

