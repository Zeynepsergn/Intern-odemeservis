package tr.gov.gib.odeme.object;

import lombok.Data;
import tr.gov.gib.odeme.entity.Odeme;
import tr.gov.gib.odeme.entity.OdemeDetay;

@Data
public class OdemeKanaliDTO {
    private BorcSorguReponse borcSorguReponse;
    private Odeme odeme;
    private OdemeDetay odemeDetay;
}
