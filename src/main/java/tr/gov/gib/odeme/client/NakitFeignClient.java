package tr.gov.gib.odeme.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import tr.gov.gib.gibcore.object.request.GibRequest;
import tr.gov.gib.gibcore.object.response.GibResponse;
import tr.gov.gib.odeme.object.NakitOdemeResponse;
import tr.gov.gib.odeme.object.OdemeResponse;


@FeignClient(name = "nakit-service")
public interface NakitFeignClient {
    @PostMapping("/nakitOdemeYap")
    GibResponse<NakitOdemeResponse> nakitOdemeYap(@RequestBody GibRequest<OdemeResponse> request);
}
