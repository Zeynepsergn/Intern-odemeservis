package tr.gov.gib.odeme.object;

import lombok.Data;

@Data
public class FposResponse {
    private String oid;
    private Integer odemeOid;
    private Short durum;
    private String posIslemId;
    private String aciklama;
}
