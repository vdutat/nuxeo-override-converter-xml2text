package com.acme.ecm.core.convert.plugins.text.extractors;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.nuxeo.ecm.core.api.Blob;
import org.nuxeo.ecm.core.api.blobholder.BlobHolder;
import org.nuxeo.ecm.core.api.blobholder.SimpleBlobHolder;
import org.nuxeo.ecm.core.api.impl.blob.StringBlob;
import org.nuxeo.ecm.core.convert.api.ConversionException;
import org.nuxeo.ecm.core.convert.plugins.text.extractors.XML2TextConverter;
import org.xml.sax.SAXException;

public class CustomXML2TextConverter extends XML2TextConverter {
    
    private static final Logger log = LogManager.getLogger(CustomXML2TextConverter.class);

    @Override
    public BlobHolder convert(BlobHolder holder, Map<String, Serializable> parameters) throws ConversionException {
        log.warn("<convert> " + holder);
        return new SimpleBlobHolder(new StringBlob(convert(holder.getBlob(), parameters)));
    }

    protected String convert(Blob blob, Map<String, Serializable> parameters) {
        log.warn("<convert> " + blob);
        if (blob.getLength() == 0L) {
            return "";
        }
        try (InputStream stream = blob.getStream()) {
            CustomXml2TextHandler xml2text = new CustomXml2TextHandler();
            return xml2text.parse(stream);
        } catch (IOException | SAXException | ParserConfigurationException e) {
            throw new ConversionException("Error during XML2Text conversion", e);
        }
    }

}
