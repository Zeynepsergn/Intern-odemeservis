package tr.gov.gib.odeme.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tr.gov.gib.gibcore.exception.GibException;
import tr.gov.gib.gibcore.object.response.GibResponse;
import tr.gov.gib.gibcore.object.reuest.GibRequest;
import tr.gov.gib.gibcore.util.OIDGenerator;
import tr.gov.gib.odeme.entity.Odeme;
import tr.gov.gib.odeme.entity.OdemeDetay;
import tr.gov.gib.odeme.object.BorcSorguReponse;
import tr.gov.gib.odeme.object.FposResponse;
import tr.gov.gib.odeme.object.OdemeResponse;
import tr.gov.gib.odeme.object.SposResponse;
import tr.gov.gib.odeme.repository.OdemeDetayRepository;
import tr.gov.gib.odeme.repository.OdemeRepository;
import tr.gov.gib.odeme.service.OdemeService;
import tr.gov.gib.odeme.util.Util;
import tr.gov.gib.odeme.util.enums.OdemeDurum;

import java.util.Date;

@Service("OdemeService")
@RequiredArgsConstructor
public class OdemeServiceImpl implements OdemeService {
    @Value("${spos.servis.url}")
    private String sposUrl;

    @Value("${fpos.servis.url}")
    private String fposUrl;
    private static final Logger logger = LoggerFactory.getLogger(OdemeServiceImpl.class);

    private final OdemeRepository odemeRepository;
    private final OdemeDetayRepository odemeDetayRepository;

    @Override
    public GibResponse odemeYap(GibRequest<BorcSorguReponse> request) {
        GibResponse result;
        BorcSorguReponse borc = request.getData();
        Odeme odeme = new Odeme();
        odeme.setMukellefBorcId(borc.getMukellefBorcId());
        odeme.setOdemeDurum(OdemeDurum.ODEME_GELDI.getOdemeDurumKodu());
        odeme.setOptime(new Date());

        OdemeDetay odemeDetay = new OdemeDetay();
        odemeDetay.setOdeme(odeme);
        odemeDetay.setOptime(new Date());
        odemeDetay.setMukellefKullaniciId(borc.getMukellefKullaniciId());
        odemeDetay.setOdenenBorcMiktari(borc.getBorc());
        odemeDetay.setVergiId(borc.getVergiId());
        //OID üretme işlemi.
        if (borc.getOdemeTur().compareTo('S') == 0) {
            odemeDetay.setOid(OIDGenerator.getSposOID());
        } else if (borc.getOdemeTur().compareTo('F') == 0) {
            //OID generator çalışırken hata fırlatıyor.
            odemeDetay.setOid(OIDGenerator.getFposOID());
        }
        odemeRepository.save(odeme);
        OdemeResponse odemeResponse = new OdemeResponse();
        odemeResponse.setOid(odemeDetay.getOid());
        odemeResponse.setOdemeOid(odeme.getId());
        odemeResponse.setTckn(borc.getTckn());
        odemeResponse.setOdenecekMiktar(borc.getBorc());
        odemeResponse.setKartNo("5110530090500");
        odemeResponse.setCcv(681);
        odemeResponse.setSonKullanimTarihiAy(4);
        odemeResponse.setSonKullanimTarihiYil(2029);
        odemeResponse.setKartSahibi("Yargı Kısakürek");

        GibRequest<OdemeResponse> gibRequest = new GibRequest<>();
        gibRequest.setData(odemeResponse);
        if (borc.getOdemeTur().compareTo('S') == 0) {
            result = sposOdemeYap(gibRequest);
            SposResponse sposResponse = (SposResponse) result.getData();
            odemeDetay.setOdemeDetayDurum(Util.getDurum(sposResponse.getDurum()).getOdemeDetayDurumKodu());

        } else if (borc.getOdemeTur().compareTo('F') == 0) {
            result = fposOdemeYap(gibRequest);
            ObjectMapper objectMapper = new ObjectMapper();
            FposResponse fposResponse = objectMapper.convertValue(result.getData(), FposResponse.class);
            logger.info("Processing OdemeServisRequest data: {}", result.getData());
            logger.info("FposResponse data: {}", fposResponse);
            odemeDetay.setOdemeDetayDurum(Util.getDurum(fposResponse.getDurum()).getOdemeDetayDurumKodu());
            odemeDetayRepository.save(odemeDetay);
            return result;
        }

        odemeDetayRepository.save(odemeDetay);
        GibResponse response = new GibResponse();
        response.setData(odeme);
        return response;
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
}
