package unipassau.thesis.vehicledatadissemination.controller;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.ow2.authzforce.core.pdp.impl.BasePdpEngine;
import org.ow2.authzforce.core.pdp.impl.PdpEngineConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import unipassau.thesis.vehicledatadissemination.pep.EmbeddedPdpInterceptor;
import unipassau.thesis.vehicledatadissemination.services.PolicyEnforcementService;
import unipassau.thesis.vehicledatadissemination.services.PolicyService;
import unipassau.thesis.vehicledatadissemination.services.ProxyReEncryptionService;
import unipassau.thesis.vehicledatadissemination.util.DataHandler;
import unipassau.thesis.vehicledatadissemination.util.Encoder;


import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class DataAccessController {

    Logger LOG = LoggerFactory.getLogger(DataAccessController.class);

    @Autowired
    org.springframework.core.env.Environment env;

    @Autowired
    private PolicyService policyService;

    @Autowired
    private PolicyEnforcementService policyEnforcementService;

    @Autowired
    private ProxyReEncryptionService proxyReEncryptionService;

    @RequestMapping(method = RequestMethod.POST, value = "/data/re-encrypt")
    public String authorize(InputStream dataStream) throws Exception {
        byte[] stickyDocument = dataStream.readAllBytes();
        Map<String, byte[]> stickyDoocumentMap =  DataHandler.read(stickyDocument);
        policyService.setPdpConfigFile(stickyDoocumentMap.get("hash"));

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes())
                .getRequest();

        Principal principal = request.getUserPrincipal();
        boolean res = policyEnforcementService.authorize(principal, request.getRequestURI(), request.getMethod());

        if (res){
            return proxyReEncryptionService.reEncrypt(stickyDoocumentMap.get("data"));
        }else {
            return "Access permission denied";
        }

    }
}
