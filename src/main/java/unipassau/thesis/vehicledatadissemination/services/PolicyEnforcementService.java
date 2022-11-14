package unipassau.thesis.vehicledatadissemination.services;

import org.ow2.authzforce.core.pdp.impl.BasePdpEngine;
import org.ow2.authzforce.core.pdp.impl.PdpEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unipassau.thesis.vehicledatadissemination.pep.EmbeddedPdpInterceptor;

import java.io.IOException;
import java.security.Principal;

@Service
public class PolicyEnforcementService {
    @Autowired
    org.springframework.core.env.Environment env;

    private PdpEngineConfiguration pdpEngineConfiguration;

    public boolean authorize(Principal principal, String requestURI, String action) throws Exception {

        pdpEngineConfiguration = PdpEngineConfiguration.getInstance(env.getProperty("pdp.config.path"));
        final BasePdpEngine pdp = new BasePdpEngine(pdpEngineConfiguration);
        final EmbeddedPdpInterceptor pep = new EmbeddedPdpInterceptor(pdp);
        boolean res = pep.authorize(principal, requestURI, action);

        return res;
    }

}
