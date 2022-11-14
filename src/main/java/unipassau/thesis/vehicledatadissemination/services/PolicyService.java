package unipassau.thesis.vehicledatadissemination.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import unipassau.thesis.vehicledatadissemination.controller.DataAccessController;
import unipassau.thesis.vehicledatadissemination.util.Encoder;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.util.HashMap;

@Service
public class PolicyService {

    Logger LOG = LoggerFactory.getLogger(PolicyService.class);

    @Autowired
    private HashMap<String, String> policyMap;
    @Autowired
    org.springframework.core.env.Environment env;

    public void updatePdpConfigFile(File file, String policy) throws UnsupportedEncodingException {

        byte[] data = null;
        try (FileInputStream fis = new FileInputStream(file)) {
            data = new byte[(int) file.length()];
            fis.read(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String input = new String(data, "UTF-8");
        String tag = "<policyLocation>";
        String closetag = "</policyLocation>";
        StringBuffer sbf = new StringBuffer(input);
        sbf.delete(input.indexOf(tag) + tag.length() , input.indexOf(closetag));
        input= sbf.toString();
        String newXML = input.substring(0, input.indexOf(tag) + tag.length()) + "policies/" + policy + input.substring(input.indexOf(tag) + tag.length(), input.length());
        try (FileWriter fw = new FileWriter(file)) {
            fw.write(newXML);
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOG.info("PDP config file updated with policy : " + policy);
    }

    public void setPdpConfigFile(byte[] hash){
        try {
            String policy = policyMap.get(Encoder.bytesToHex(hash));
            File inputFile = new File(env.getProperty("pdp.config.path"));
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            dbFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);

            XPath xPath = XPathFactory.newInstance().newXPath();
            Node policyLocationNode = (Node) xPath.compile("/pdp/policyProvider/policyLocation").evaluate(doc, XPathConstants.NODE);

            boolean checkForPolicy = (policyLocationNode.getTextContent().equals("policies/"+policy));
            if (!checkForPolicy){
                LOG.info("Updating PDP config file with policy : " + policy);
                updatePdpConfigFile(inputFile, policy);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
