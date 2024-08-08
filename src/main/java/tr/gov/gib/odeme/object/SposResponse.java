package tr.gov.gib.odeme.object;

import lombok.Data;

@Data
public class SposResponse {
    private String oid;
    private Integer odemeOid;
    private Short durum;
}
