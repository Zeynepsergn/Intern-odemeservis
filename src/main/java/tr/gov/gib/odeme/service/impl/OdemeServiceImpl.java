package tr.gov.gib.odeme.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import tr.gov.gib.gibcore.exception.GibException;
import tr.gov.gib.gibcore.object.response.GibResponse;
import tr.gov.gib.gibcore.object.reuest.GibRequest;
import tr.gov.gib.odeme.object.BorcSorguReponse;
import tr.gov.gib.odeme.service.OdemeService;

@Service("OdemeService")
public class OdemeServiceImpl implements OdemeService {
    @Value("${spos.servis.url}")
    private String sposUrl;

    @Value("${fpos.servis.url}")
    private String fposUrl;

    @Override
    public GibResponse odemeYap(GibRequest<BorcSorguReponse> request) {
        BorcSorguReponse data = request.getData();
        if (data.getOdemeTur().compareTo('S') == 0) {
            return sposOdemeYap(request);
        } else if (data.getOdemeTur().compareTo('F') == 0) {
            return fposOdemeYap(request);
        }
        return null;
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
