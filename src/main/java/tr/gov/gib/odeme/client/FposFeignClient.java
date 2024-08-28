package tr.gov.gib.odeme.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import tr.gov.gib.gibcore.object.request.GibRequest;
import tr.gov.gib.gibcore.object.response.GibResponse;
import tr.gov.gib.odeme.object.FposResponse;
import tr.gov.gib.odeme.object.OdemeResponse;


@FeignClient(name = "fpos-service")
public interface FposFeignClient {

    @PostMapping("/fposOdemeYap")
    GibResponse<FposResponse> fposOdemeYap(@RequestBody GibRequest<OdemeResponse> request);
}
