package tr.gov.gib.odeme.service;

import tr.gov.gib.gibcore.object.response.GibResponse;
import tr.gov.gib.gibcore.object.reuest.GibRequest;
import tr.gov.gib.odeme.object.OdemeKanaliDTO;

public interface FposOdemeKanaliService extends OdemeKanallariService{
    GibResponse fposOdemeYap(OdemeKanaliDTO odemeKanaliDTO);
}
