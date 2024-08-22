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
import tr.gov.gib.gibcore.util.OIDGenerator;
import tr.gov.gib.odeme.entity.Odeme;
import tr.gov.gib.odeme.entity.OdemeDetay;
import tr.gov.gib.odeme.object.OdemeKanaliDTO;
import tr.gov.gib.odeme.object.OdemeResponse;
import tr.gov.gib.odeme.object.SposResponse;
import tr.gov.gib.odeme.repository.OdemeDetayRepository;
import tr.gov.gib.odeme.repository.OdemeRepository;
import tr.gov.gib.odeme.service.SposOdemeKanaliService;
import tr.gov.gib.odeme.util.Util;

@Service("SposOdemeKanaliService")
@RequiredArgsConstructor
public class SposOdemeKanaliServiceImpl implements SposOdemeKanaliService {
    @Value("${spos.servis.url}")
    private String sposUrl;
    private final OdemeRepository odemeRepository;
    private final OdemeDetayRepository odemeDetayRepository;

    @Override
    public GibResponse sposOdemeYap(OdemeKanaliDTO odemeKanaliDTO) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            Odeme odeme = odemeKanaliDTO.getOdeme();
            OdemeDetay odemeDetay = odemeKanaliDTO.getOdemeDetay();
            //OID üretme işlemi.
            odemeDetay.setOid(OIDGenerator.getSposOID());
            odemeRepository.save(odeme);
            OdemeResponse odemeResponse = new OdemeResponse(odemeDetay.getOid(), odeme.getId(), odemeKanaliDTO.getBorcSorguReponse());

            GibRequest<OdemeResponse> gibRequest = new GibRequest<>();
            gibRequest.setData(odemeResponse);

            HttpEntity<GibRequest> requestEntity = new HttpEntity<>(gibRequest);
            GibResponse response = restTemplate.exchange(sposUrl, HttpMethod.POST, requestEntity, GibResponse.class).getBody();

            SposResponse sposResponse = (SposResponse) response.getData();
            odemeDetay.setOdemeDetayDurum(Util.getDurum(sposResponse.getDurum()).getOdemeDetayDurumKodu());
            odemeDetayRepository.save(odemeDetay);


            GibResponse gibResponse = new GibResponse();
            response.setData(odeme);
            return gibResponse;
        } catch (Throwable e) {
            throw new GibException(e.getMessage());
        }
    }
}
