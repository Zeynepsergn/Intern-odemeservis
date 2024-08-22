package tr.gov.gib.odeme.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tr.gov.gib.gibcore.exception.GibException;
import tr.gov.gib.gibcore.object.response.GibResponse;
import tr.gov.gib.gibcore.object.request.GibRequest;
import tr.gov.gib.odeme.entity.Odeme;
import tr.gov.gib.odeme.entity.OdemeDetay;
import tr.gov.gib.odeme.object.BorcSorguReponse;
import tr.gov.gib.odeme.object.OdemeKanaliDTO;
import tr.gov.gib.odeme.service.FposOdemeKanaliService;
import tr.gov.gib.odeme.service.NakitOdemeKanaliService;
import tr.gov.gib.odeme.service.OdemeService;
import tr.gov.gib.odeme.service.SposOdemeKanaliService;
import tr.gov.gib.odeme.util.enums.OdemeKanallari;

@Service("OdemeService")
@RequiredArgsConstructor
public class OdemeServiceImpl implements OdemeService {
    @Value("${spos.servis.url}")
    private String sposUrl;

    @Value("${fpos.servis.url}")
    private String fposUrl;

    @Value("${nakit.servis.url}")
    private String nakitUrl;

    private final FposOdemeKanaliService fposOdemeKanaliService;
    private final SposOdemeKanaliService sposOdemeKanaliService;
    private final NakitOdemeKanaliService nakitOdemeKanaliService;

    @Override
    public GibResponse odemeYap(GibRequest<BorcSorguReponse> request) {
        BorcSorguReponse borc = request.getData();

        // 0. Aşama : Odeme geldi.
        Odeme odeme = new Odeme(borc);

        // Odeme detay tablosuna ilk kayıt atma
        OdemeDetay odemeDetay = new OdemeDetay(borc);
        odemeDetay.setOdeme(odeme);

        OdemeKanaliDTO odemeKanaliDTO = new OdemeKanaliDTO();
        odemeKanaliDTO.setOdeme(odeme);
        odemeKanaliDTO.setOdemeDetay(odemeDetay);
        odemeKanaliDTO.setBorcSorguReponse(borc);

        return OdemeKanallari.getOdemeKanaliService(String.valueOf(borc.getOdemeTur())).getOdemeKanaliService().apply(this, odemeKanaliDTO);
    }

    @Override
    public GibResponse sposOdemeYap(GibRequest request) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            HttpEntity<GibRequest> requestEntity = new HttpEntity<>(request);
            GibResponse response = restTemplate.exchange(sposUrl, HttpMethod.POST, requestEntity, GibResponse.class).getBody();
            return response;
        } catch (Throwable e) {
            throw new GibException(e.getMessage());
        }
    }

    @Override
    public GibResponse fposOdemeYap(GibRequest request) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<GibRequest> requestEntity = new HttpEntity<>(request);
        return restTemplate.exchange(fposUrl, HttpMethod.POST, requestEntity, GibResponse.class).getBody();
    }

    @Override
    public GibResponse nakitOdemeYap(GibRequest request) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<GibRequest> requestEntity = new HttpEntity<>(request);
        return restTemplate.exchange(fposUrl, HttpMethod.POST, requestEntity, GibResponse.class).getBody();
    }

    @Override
    public GibResponse sposOdemeYap(OdemeKanaliDTO odemeKanaliDTO) {
        return sposOdemeKanaliService.sposOdemeYap(odemeKanaliDTO);
    }

    @Override
    public GibResponse fposOdemeYap(OdemeKanaliDTO odemeKanaliDTO) {
        return fposOdemeKanaliService.fposOdemeYap(odemeKanaliDTO);
    }

    @Override
    public GibResponse nakitOdemeYap(OdemeKanaliDTO odemeKanaliDTO) {
        return nakitOdemeKanaliService.nakitOdemeYap(odemeKanaliDTO);
    }
}
