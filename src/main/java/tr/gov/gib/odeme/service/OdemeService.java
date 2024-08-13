package tr.gov.gib.odeme.service;

import tr.gov.gib.gibcore.object.response.GibResponse;
import tr.gov.gib.gibcore.object.reuest.GibRequest;
import tr.gov.gib.odeme.object.BorcSorguReponse;
import tr.gov.gib.odeme.object.OdemeKanaliDTO;

public interface OdemeService {
    GibResponse odemeYap(GibRequest<BorcSorguReponse> request);

    GibResponse sposOdemeYap(GibRequest request);

    GibResponse fposOdemeYap(GibRequest request);

    GibResponse sposOdemeYap(OdemeKanaliDTO odemeKanaliDTO);

    GibResponse fposOdemeYap(OdemeKanaliDTO odemeKanaliDTO);
}
