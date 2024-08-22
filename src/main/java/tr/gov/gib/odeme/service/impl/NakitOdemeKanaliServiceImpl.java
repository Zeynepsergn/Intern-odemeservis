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
import tr.gov.gib.gibcore.object.request.GibRequest;
import tr.gov.gib.gibcore.object.response.GibResponse;
import tr.gov.gib.gibcore.util.OIDGenerator;
import tr.gov.gib.odeme.entity.Odeme;
import tr.gov.gib.odeme.entity.OdemeDetay;
import tr.gov.gib.odeme.object.NakitOdemeResponse;
import tr.gov.gib.odeme.object.OdemeKanaliDTO;
import tr.gov.gib.odeme.object.OdemeResponse;
import tr.gov.gib.odeme.repository.OdemeDetayRepository;
import tr.gov.gib.odeme.repository.OdemeRepository;
import tr.gov.gib.odeme.service.NakitOdemeKanaliService;
import tr.gov.gib.odeme.util.Util;

@Service("NakitOdemeKanaliServiceImpl")
@RequiredArgsConstructor
public class NakitOdemeKanaliServiceImpl implements NakitOdemeKanaliService {
    @Value("${nakit.servis.url}")
    private String nakitUrl;
    private final OdemeRepository odemeRepository;
    private final OdemeDetayRepository odemeDetayRepository;

    private static final Logger logger = LoggerFactory.getLogger(OdemeServiceImpl.class);

    @Override
    public GibResponse nakitOdemeYap(OdemeKanaliDTO odemeKanaliDTO) {
        Odeme odeme = odemeKanaliDTO.getOdeme();
        OdemeDetay odemeDetay = odemeKanaliDTO.getOdemeDetay();
        //OID üretme işlemi.
        odemeDetay.setOid(OIDGenerator.getNakitOID());
        odemeRepository.save(odeme);
        OdemeResponse odemeResponse = new OdemeResponse(odemeDetay.getOid(),odeme.getId(), odemeKanaliDTO.getBorcSorguReponse());
        GibRequest<OdemeResponse> gibRequest = new GibRequest<>();
        gibRequest.setData(odemeResponse);

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<GibRequest> requestEntity = new HttpEntity<>(gibRequest);
        GibResponse response = restTemplate.exchange(nakitUrl, HttpMethod.POST, requestEntity, GibResponse.class).getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        NakitOdemeResponse nakitOdemeResponse = objectMapper.convertValue(response.getData(), NakitOdemeResponse.class);
        logger.info("Processing OdemeServisRequest data: {}", response.getData());
        logger.info("NakitResponse data: {}", nakitOdemeResponse);
        odemeDetay.setOdemeDetayDurum(Util.getDurum(nakitOdemeResponse.getDurum()).getOdemeDetayDurumKodu());
        odemeDetayRepository.save(odemeDetay);

        return response;
    }
}
