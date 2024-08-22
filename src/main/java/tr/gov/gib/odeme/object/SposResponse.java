package tr.gov.gib.odeme.object;

import lombok.Data;

import java.io.Serializable;

@Data
public class SposResponse implements Serializable {
    private String oid;
    private Integer odemeOid;
    private Short durum;
    private String bankaAdi;
}
