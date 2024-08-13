package tr.gov.gib.odeme.service;

import tr.gov.gib.gibcore.object.response.GibResponse;
import tr.gov.gib.gibcore.object.reuest.GibRequest;
import tr.gov.gib.odeme.object.BorcSorguReponse;
import tr.gov.gib.odeme.object.OdemeKanaliDTO;

public interface OdemeKanallariService extends OdemeService {
    default GibResponse sposOdemeYap(OdemeKanaliDTO odemeKanaliDTO) {
        return null;
    }

    default GibResponse fposOdemeYap(OdemeKanaliDTO odemeKanaliDTO) {
        return null;
    }

    default GibResponse sposOdemeYap(GibRequest gibRequest) {
        return null;
    }

    default GibResponse fposOdemeYap(GibRequest gibRequest) {
        return null;
    }

    default GibResponse odemeYap(GibRequest<BorcSorguReponse> request) {
        return null;
    }
}
