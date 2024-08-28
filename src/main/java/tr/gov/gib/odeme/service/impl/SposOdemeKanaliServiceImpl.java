package tr.gov.gib.odeme.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import tr.gov.gib.gibcore.util.ServiceMessage;
import tr.gov.gib.odeme.client.SposFeignClient;
import tr.gov.gib.odeme.component.OdemePublisher;
import tr.gov.gib.odeme.entity.Odeme;
import tr.gov.gib.odeme.entity.OdemeDetay;
import tr.gov.gib.odeme.object.OdemeKanaliDTO;
import tr.gov.gib.odeme.object.OdemeResponse;
import tr.gov.gib.odeme.object.SposResponse;
import tr.gov.gib.odeme.repository.OdemeDetayRepository;
import tr.gov.gib.odeme.repository.OdemeRepository;
import tr.gov.gib.odeme.service.SposOdemeKanaliService;
import tr.gov.gib.odeme.util.Util;
import tr.gov.gib.odeme.util.enums.OdemeDetayDurum;
import tr.gov.gib.odeme.util.enums.OdemeDurum;

@Service("SposOdemeKanaliService")
@RequiredArgsConstructor
public class SposOdemeKanaliServiceImpl implements SposOdemeKanaliService {
    @Value("${spos.servis.url}")
    private String sposUrl;
    private final OdemeRepository odemeRepository;
    private final OdemeDetayRepository odemeDetayRepository;
    private final OdemePublisher rabbitPublisher;
    private final SposFeignClient sposFeignClient;

    @Override
    public GibResponse sposOdemeYap(OdemeKanaliDTO odemeKanaliDTO) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            Odeme odeme = odemeKanaliDTO.getOdeme();
            odeme.setMukellefKullaniciId((long)odemeKanaliDTO.getBorcSorguReponse().getMukellefKullaniciId());
            odeme.setVergiTur(odemeKanaliDTO.getBorcSorguReponse().getVergiId());

            OdemeDetay odemeDetay = odemeKanaliDTO.getOdemeDetay();
            //OID üretme işlemi.
            odemeDetay.setOid(OIDGenerator.getSposOID());
            odemeRepository.save(odeme);
            OdemeResponse odemeResponse = new OdemeResponse(odemeDetay.getOid(), odeme.getId(), odemeKanaliDTO.getBorcSorguReponse());

            GibRequest<OdemeResponse> gibRequest = new GibRequest<>();
            gibRequest.setData(odemeResponse);

            GibResponse response = sposFeignClient.sposOdemeYap(gibRequest);

            ObjectMapper mapper = new ObjectMapper();
            SposResponse sposResponse = mapper.convertValue(response.getData(), SposResponse.class);
            // Kontrol koyulması lazım.

            OdemeDetayDurum odemeDetayDurum = Util.getDurum(sposResponse.getDurum());
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
