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
package nl.clockwork.ebms.service.b;

import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 2.1.2
 * Fri Nov 12 23:51:46 CET 2010
 * Generated source version: 2.1.2
 * 
 */
 
@WebService(targetNamespace = "http://www.clockwork.nl/ebms/1.0", name = "EbMSPortType")
@XmlSeeAlso({nl.clockwork.ebms.model.soap.envelope.ObjectFactory.class,nl.clockwork.ebms.model.ebxml.ObjectFactory.class,nl.clockwork.ebms.model.xml.xlink.ObjectFactory.class,nl.clockwork.ebms.model.xml.xmldsig.ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface EbMSPortType {

	@Oneway
	@WebMethod(operationName = "MessageRequest", action = "ebXML")
	public void messageRequest(
		@WebParam(partName = "MessageHeader", name = "MessageHeader", targetNamespace = "http://www.oasis-open.org/committees/ebxml-msg/schema/msg-header-2_0.xsd", header = true)
		nl.clockwork.ebms.model.ebxml.MessageHeader messageHeader,
		@WebParam(partName = "SyncReply", name = "SyncReply", targetNamespace = "http://www.oasis-open.org/committees/ebxml-msg/schema/msg-header-2_0.xsd", header = true)
		nl.clockwork.ebms.model.ebxml.SyncReply syncReply,
		@WebParam(partName = "MessageOrder", name = "MessageOrder", targetNamespace = "http://www.oasis-open.org/committees/ebxml-msg/schema/msg-header-2_0.xsd", header = true)
		nl.clockwork.ebms.model.ebxml.MessageOrder messageOrder,
		@WebParam(partName = "AckRequested", name = "AckRequested", targetNamespace = "http://www.oasis-open.org/committees/ebxml-msg/schema/msg-header-2_0.xsd", header = true)
		nl.clockwork.ebms.model.ebxml.AckRequested ackRequested,
		@WebParam(partName = "Manifest", name = "Manifest", targetNamespace = "http://www.oasis-open.org/committees/ebxml-msg/schema/msg-header-2_0.xsd")
		nl.clockwork.ebms.model.ebxml.Manifest manifest
	);

	@Oneway
  @WebMethod(operationName = "MessageResponse", action = "ebXML")
  public void messageResponse(
      @WebParam(partName = "MessageHeader", name = "MessageHeader", targetNamespace = "http://www.oasis-open.org/committees/ebxml-msg/schema/msg-header-2_0.xsd", header = true)
      nl.clockwork.ebms.model.ebxml.MessageHeader requestMessageHeader,
      @WebParam(partName = "SyncReply", name = "SyncReply", targetNamespace = "http://www.oasis-open.org/committees/ebxml-msg/schema/msg-header-2_0.xsd", header = true)
      nl.clockwork.ebms.model.ebxml.SyncReply syncReply,
  		@WebParam(partName = "ErrorList", name = "ErrorList", targetNamespace = "http://www.oasis-open.org/committees/ebxml-msg/schema/msg-header-2_0.xsd", header = true)
  		nl.clockwork.ebms.model.ebxml.ErrorList errorList,
  		@WebParam(partName = "Acknowledgment", name = "Acknowledgment", targetNamespace = "http://www.oasis-open.org/committees/ebxml-msg/schema/msg-header-2_0.xsd", header = true)
  		nl.clockwork.ebms.model.ebxml.Acknowledgment acknowledgment
  );

	@Oneway
	@WebMethod(operationName = "StatusRequest", action = "ebXML")
	public void statusRequest(
		@WebParam(partName = "MessageHeader", name = "MessageHeader", targetNamespace = "http://www.oasis-open.org/committees/ebxml-msg/schema/msg-header-2_0.xsd", header = true)
		nl.clockwork.ebms.model.ebxml.MessageHeader messageHeader,
		@WebParam(partName = "SyncReply", name = "SyncReply", targetNamespace = "http://www.oasis-open.org/committees/ebxml-msg/schema/msg-header-2_0.xsd", header = true)
		nl.clockwork.ebms.model.ebxml.SyncReply syncReply,
		@WebParam(partName = "StatusRequest", name = "StatusRequest", targetNamespace = "http://www.oasis-open.org/committees/ebxml-msg/schema/msg-header-2_0.xsd")
		nl.clockwork.ebms.model.ebxml.StatusRequest statusRequest
	);

	@Oneway
	@WebMethod(operationName = "StatusResponse", action = "ebXML")
	public void statusResponse(
		@WebParam(partName = "MessageHeader", mode = WebParam.Mode.OUT, name = "MessageHeader", targetNamespace = "http://www.oasis-open.org/committees/ebxml-msg/schema/msg-header-2_0.xsd", header = true)
		nl.clockwork.ebms.model.ebxml.MessageHeader messageHeader,
		@WebParam(partName = "StatusResponse", mode = WebParam.Mode.OUT, name = "StatusResponse", targetNamespace = "http://www.oasis-open.org/committees/ebxml-msg/schema/msg-header-2_0.xsd")
		nl.clockwork.ebms.model.ebxml.StatusResponse statusResponse
	);

}