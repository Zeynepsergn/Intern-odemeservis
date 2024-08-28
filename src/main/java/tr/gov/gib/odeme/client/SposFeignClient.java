package tr.gov.gib.odeme.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import tr.gov.gib.gibcore.object.request.GibRequest;
import tr.gov.gib.gibcore.object.response.GibResponse;
import tr.gov.gib.odeme.object.OdemeResponse;
import tr.gov.gib.odeme.object.SposResponse;

@FeignClient(name = "spos-service")
public interface SposFeignClient {

    @PostMapping("/sposOdemeYap")
    GibResponse<SposResponse> sposOdemeYap( @RequestBody GibRequest<OdemeResponse> request);
}
