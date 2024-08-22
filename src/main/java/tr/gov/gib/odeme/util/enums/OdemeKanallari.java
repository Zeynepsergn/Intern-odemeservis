package tr.gov.gib.odeme.util.enums;

import tr.gov.gib.gibcore.object.response.GibResponse;
import tr.gov.gib.odeme.object.OdemeKanaliDTO;
import tr.gov.gib.odeme.service.OdemeService;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

public enum OdemeKanallari {
    SPOS("S", OdemeService::sposOdemeYap),
    NAKIT("N", OdemeService::nakitOdemeYap),
    FPOS("F", OdemeService::fposOdemeYap);

    private String odemeKanaliAciklama;
    private BiFunction<OdemeService, OdemeKanaliDTO, GibResponse> odemeKanaliService;

    public String getOdemeKanaliAciklama() {
        return odemeKanaliAciklama;
    }

    public BiFunction<OdemeService, OdemeKanaliDTO, GibResponse> getOdemeKanaliService() {
        return odemeKanaliService;
    }

    OdemeKanallari(String odemeKanaliAciklama, BiFunction<OdemeService, OdemeKanaliDTO, GibResponse> odemeKanaliService) {
        this.odemeKanaliAciklama = odemeKanaliAciklama;
        this.odemeKanaliService = odemeKanaliService;
    }

    public static final List<OdemeKanallari> all = Arrays.asList(OdemeKanallari.values());

    public static OdemeKanallari getOdemeKanaliService(String odemeTur) {
        return all.stream().filter(odemeKanallari -> odemeKanallari.getOdemeKanaliAciklama().equals(odemeTur)).findFirst().orElse(null);
    }

}
