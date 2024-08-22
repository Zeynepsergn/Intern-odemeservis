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
import tr.gov.gib.gibcore.object.response.GibResponse;
import tr.gov.gib.gibcore.object.request.GibRequest;
import tr.gov.gib.gibcore.util.OIDGenerator;
import tr.gov.gib.odeme.entity.Odeme;
import tr.gov.gib.odeme.entity.OdemeDetay;
import tr.gov.gib.odeme.object.FposResponse;
import tr.gov.gib.odeme.object.OdemeKanaliDTO;
import tr.gov.gib.odeme.object.OdemeResponse;
import tr.gov.gib.odeme.repository.OdemeDetayRepository;
import tr.gov.gib.odeme.repository.OdemeRepository;
import tr.gov.gib.odeme.service.FposOdemeKanaliService;
import tr.gov.gib.odeme.util.Util;

@RequiredArgsConstructor
@Service("FposOdemeKanaliService")
public class FposOdemeKanaliServiceImpl implements FposOdemeKanaliService {
    @Value("${fpos.servis.url}")
    private String fposUrl;
    private final OdemeRepository odemeRepository;
    private final OdemeDetayRepository odemeDetayRepository;

    private static final Logger logger = LoggerFactory.getLogger(OdemeServiceImpl.class);

    @Override
    public GibResponse fposOdemeYap(OdemeKanaliDTO odemeKanaliDTO) {
        Odeme odeme = odemeKanaliDTO.getOdeme();
        OdemeDetay odemeDetay = odemeKanaliDTO.getOdemeDetay();
        //OID üretme işlemi.
        odemeDetay.setOid(OIDGenerator.getFposOID());
        odemeRepository.save(odeme);
        OdemeResponse odemeResponse = new OdemeResponse(odemeDetay.getOid(),odeme.getId(), odemeKanaliDTO.getBorcSorguReponse());
        GibRequest<OdemeResponse> gibRequest = new GibRequest<>();
        gibRequest.setData(odemeResponse);

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<GibRequest> requestEntity = new HttpEntity<>(gibRequest);
        GibResponse response = restTemplate.exchange(fposUrl, HttpMethod.POST, requestEntity, GibResponse.class).getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        FposResponse fposResponse = objectMapper.convertValue(response.getData(), FposResponse.class);
        logger.info("Processing OdemeServisRequest data: {}", response.getData());
        logger.info("FposResponse data: {}", fposResponse);
        odemeDetay.setOdemeDetayDurum(Util.getDurum(fposResponse.getDurum()).getOdemeDetayDurumKodu());
        odemeDetayRepository.save(odemeDetay);

        return response;
    }
}
