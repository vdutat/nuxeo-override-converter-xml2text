package com.acme.ecm.core.convert.plugins.text.extractors;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.nuxeo.ecm.core.convert.plugins.text.extractors.Xml2TextHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class CustomXml2TextHandler extends Xml2TextHandler {
    
    private static final Logger log = LogManager.getLogger(CustomXml2TextHandler.class);

    public CustomXml2TextHandler() throws SAXException, ParserConfigurationException {
        super();
    }

    @Override
    public void startDocument() throws SAXException {
        log.warn("<startDocument> ");
        trim = false;
        buf = new StringBuffer();
    }

    @Override
    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
        log.warn("<startElement> Qname: " + name + " / " + attributes);
        trim = true;
    }

    @Override
    public void endElement(String uri, String localName, String name) throws SAXException {
        log.warn("<endElement> Qname: " + name + " / " + buf);
        trim = true;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        log.warn(String.format("<characters> %s / %d / %d", ch, start, length));
        // buf.append(ch, start, length); if (true) return;
        if (trim) {
            int i = start;
            int end = start + length;
            while (i < end && Character.isWhitespace(ch[i])) {
                i++;
            }
            buf.append(" ").append(ch, i, length - i + start);
            trim = false;
            log.warn("["+new String(ch, i, length - i + start)+"]");
        } else {
            buf.append(ch, start, length);
            log.warn("{"+new String(ch, start, length)+"}");
        }
    }

}
