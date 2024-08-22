package tr.gov.gib.odeme.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import tr.gov.gib.gibcore.exception.GibException;
import tr.gov.gib.gibcore.object.response.GibResponse;
import tr.gov.gib.gibcore.object.request.GibRequest;
import tr.gov.gib.gibcore.util.OIDGenerator;
import tr.gov.gib.gibcore.util.ServiceMessage;
import tr.gov.gib.odeme.component.OdemePublisher;
import tr.gov.gib.odeme.entity.Odeme;
import tr.gov.gib.odeme.entity.OdemeDetay;
import tr.gov.gib.odeme.object.FposResponse;
import tr.gov.gib.odeme.object.OdemeKanaliDTO;
import tr.gov.gib.odeme.object.OdemeResponse;
import tr.gov.gib.odeme.repository.OdemeDetayRepository;
import tr.gov.gib.odeme.repository.OdemeRepository;
import tr.gov.gib.odeme.service.FposOdemeKanaliService;
import tr.gov.gib.odeme.util.Util;
import tr.gov.gib.odeme.util.enums.OdemeDetayDurum;
import tr.gov.gib.odeme.util.enums.OdemeDurum;

@RequiredArgsConstructor
@Service("FposOdemeKanaliService")
public class FposOdemeKanaliServiceImpl implements FposOdemeKanaliService {
    @Value("${fpos.servis.url}")
    private String fposUrl;
    private final OdemeRepository odemeRepository;
    private final OdemeDetayRepository odemeDetayRepository;
    private final OdemePublisher rabbitPublisher;

    private static final Logger logger = LoggerFactory.getLogger(OdemeServiceImpl.class);

    @Override
    public GibResponse fposOdemeYap(OdemeKanaliDTO odemeKanaliDTO) {
        RestTemplate restTemplate = new RestTemplate();

        try {
            Odeme odeme = odemeKanaliDTO.getOdeme();
            odeme.setMukellefKullaniciId((long)odemeKanaliDTO.getBorcSorguReponse().getMukellefKullaniciId());
            odeme.setVergiTur(odemeKanaliDTO.getBorcSorguReponse().getVergiId());

            OdemeDetay odemeDetay = odemeKanaliDTO.getOdemeDetay();
            //OID üretme işlemi.
            odemeDetay.setOid(OIDGenerator.getFposOID());
            odemeRepository.save(odeme);
            OdemeResponse odemeResponse = new OdemeResponse(odemeDetay.getOid(),odeme.getId(), odemeKanaliDTO.getBorcSorguReponse());
            GibRequest<OdemeResponse> gibRequest = new GibRequest<>();
            gibRequest.setData(odemeResponse);

            HttpEntity<GibRequest> requestEntity = new HttpEntity<>(gibRequest);
            GibResponse response = restTemplate.exchange(fposUrl, HttpMethod.POST, requestEntity, GibResponse.class).getBody();

            ObjectMapper objectMapper = new ObjectMapper();
            FposResponse fposResponse = objectMapper.convertValue(response.getData(), FposResponse.class);
            logger.info("Processing OdemeServisRequest data: {}", response.getData());
            logger.info("FposResponse data: {}", fposResponse);

            OdemeDetayDurum odemeDetayDurum = Util.getDurum(fposResponse.getDurum());
            if(odemeDetayDurum != OdemeDetayDurum.BASARILI){
                odeme.setOdemeDurum(OdemeDurum.ODEME_YAPILAMADI.getOdemeDurumKodu());
                odemeDetay.setOdemeDetayDurum(odemeDetayDurum.getOdemeDetayDurumKodu());
                odemeDetay.setAciklama(odemeDetayDurum.getOdemeDetayDurum());
                odemeRepository.save(odeme);
                odemeDetayRepository.save(odemeDetay);
                return GibResponse.builder().service(ServiceMessage.OK).data(odeme).build();
            }

            odeme.setOdemeDurum(OdemeDurum.ODEME_GERCEKLESTI.getOdemeDurumKodu());
            odemeDetay.setOdemeDetayDurum(OdemeDetayDurum.BASARILI.getOdemeDetayDurumKodu());
            odemeDetay.setAciklama(OdemeDetayDurum.BASARILI.getOdemeDetayDurum());
            odemeRepository.save(odeme);
            odemeDetayRepository.save(odemeDetay);

            //Kuyruga gonderme islemi tahsil etmek icin
            rabbitPublisher.publish(odeme);

            return GibResponse.builder().service(ServiceMessage.OK).data(odeme).build();
        } catch (Exception e) {
            throw new GibException(e.getMessage());
        }
    }
}
