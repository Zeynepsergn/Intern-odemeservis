package tr.gov.gib.odeme.object;

import lombok.Data;

@Data
public class FposResponse {
    private String oid;
    private Integer odemeOid;
    private Short durum;
    private String aciklama;

    //FPostan Gelmeli
    private String posId;
    private String bankaAdi;
}
