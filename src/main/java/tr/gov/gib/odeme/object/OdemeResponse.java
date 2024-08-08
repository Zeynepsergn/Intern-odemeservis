package tr.gov.gib.odeme.object;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OdemeResponse {
    private String oid;
    private Integer odemeOid;
    private String kartSahibi;
    private String kartBanka;
    private String posIslemId;
    private BigDecimal odenecekMiktar;
    private String tckn;
}
