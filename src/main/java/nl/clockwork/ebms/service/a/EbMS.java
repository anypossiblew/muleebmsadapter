/*******************************************************************************
 * Copyright 2011 Clockwork
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
/*
 * 
 */

package nl.clockwork.ebms.service.a;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;

/**
 * This class was generated by Apache CXF 2.1.2
 * Fri Nov 12 23:51:46 CET 2010
 * Generated source version: 2.1.2
 * 
 */


@WebServiceClient(name = "EbMS", 
                  wsdlLocation = "classpath:nl/clockwork/ebms/wsdl/ebms.wsdl",
                  targetNamespace = "http://www.clockwork.nl/ebms/1.0") 
public class EbMS extends Service {

    public final static URL WSDL_LOCATION;
    public final static QName SERVICE = new QName("http://www.clockwork.nl/ebms/1.0", "EbMS");
    public final static QName EbMSPort = new QName("http://www.clockwork.nl/ebms/1.0", "EbMSPort");
    static {
        URL url = null;
        try {
            url = new URL("classpath:nl/clockwork/ebms/wsdl/ebms.wsdl");
        } catch (MalformedURLException e) {
            System.err.println("Can not initialize the default wsdl from classpath:nl/clockwork/ebms/wsdl/ebms.wsdl");
            // e.printStackTrace();
        }
        WSDL_LOCATION = url;
    }

    public EbMS(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public EbMS(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public EbMS() {
        super(WSDL_LOCATION, SERVICE);
    }

    /**
     * 
     * @return
     *     returns EbMSPortType
     */
    @WebEndpoint(name = "EbMSPort")
    public EbMSPortType getEbMSPort() {
        return super.getPort(EbMSPort, EbMSPortType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns EbMSPortType
     */
    @WebEndpoint(name = "EbMSPort")
    public EbMSPortType getEbMSPort(WebServiceFeature... features) {
        return super.getPort(EbMSPort, EbMSPortType.class, features);
    }

}
