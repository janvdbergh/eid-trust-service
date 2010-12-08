/*
 * eID Trust Service Project.
 * Copyright (C) 2009-2010 FedICT.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License version
 * 3.0 as published by the Free Software Foundation.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, see 
 * http://www.gnu.org/licenses/.
 */

package be.fedict.trust.xkms2;

import org.w3._2002._03.xkms.XKMSService;

import javax.xml.namespace.QName;
import java.net.URL;

/**
 * Factory for the JAX-WS based XKMS2 Service.
 *
 * @author fcorneli
 */
public class XKMSServiceFactory {

    public static final String WSDL_RESOURCE = "/xkms.wsdl";

    private XKMSServiceFactory() {
        super();
    }

    public static XKMSService getInstance() {
        URL wsdlLocation = XKMSServiceFactory.class.getResource(WSDL_RESOURCE);
        if (null == wsdlLocation) {
            throw new RuntimeException("WSDL location not valid: "
                    + WSDL_RESOURCE);
        }
        QName serviceName = new QName("http://www.w3.org/2002/03/xkms#wsdl",
                "XKMSService");
        return new XKMSService(wsdlLocation, serviceName);
    }
}
