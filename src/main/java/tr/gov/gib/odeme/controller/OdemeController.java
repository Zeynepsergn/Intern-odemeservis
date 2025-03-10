package tr.gov.gib.odeme.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tr.gov.gib.gibcore.exception.GibExceptionHandler;
import tr.gov.gib.gibcore.object.response.GibResponse;
import tr.gov.gib.gibcore.object.request.GibRequest;
import tr.gov.gib.odeme.object.BorcSorguReponse;
import tr.gov.gib.odeme.service.OdemeService;

@RestController(value = "/")
@RequiredArgsConstructor
public class OdemeController extends GibExceptionHandler {
    private final OdemeService odemeService;

    @RequestMapping(path = "/odemeYap", method = RequestMethod.POST)
    public GibResponse odemeYap(@RequestBody GibRequest<BorcSorguReponse> request) {
        Logger logger = LoggerFactory.getLogger(OdemeController.class);
        logger.info("Response from odemeYap: {}", request.getData());
        return odemeService.odemeYap(request);
    }

    @RequestMapping(path = "/sposOdemeYap", method = RequestMethod.POST)
    public GibResponse sposOdemeYap(@RequestBody GibRequest request) {
        return odemeService.sposOdemeYap(request);
    }

    @RequestMapping(path = "/fposOdemeYap", method = RequestMethod.POST)
    public GibResponse fposOdemeYap(@RequestBody GibRequest request) {
        return odemeService.fposOdemeYap(request);
    }

    @RequestMapping(path = "/nakitOdemeYap", method = RequestMethod.POST)
    public GibResponse nakitOdemeYap(@RequestBody GibRequest request) {
        return odemeService.nakitOdemeYap(request);
    }

}
