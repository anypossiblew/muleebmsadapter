/**
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
 */
package nl.clockwork.ebms.event;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;


@WebService(targetNamespace="http://www.clockwork.nl/ebms/event/2.0")
public interface EbMSEventListenerService extends EventListener
{

	@Override
	@WebMethod(operationName="MessageReceived")
	public void onMessageReceived(@WebParam(name="MessageId") String messageId) throws EbMSEventListenerServiceException;

	@Override
	@WebMethod(operationName="MessageAcknowledged")
	public void onMessageAcknowledged(@WebParam(name="MessageId") String messageId) throws EbMSEventListenerServiceException;

	@Override
	@WebMethod(operationName="MessageDeliveryFailed")
	public void onMessageDeliveryFailed(@WebParam(name="MessageId") String messageId) throws EbMSEventListenerServiceException;

	@Override
	@WebMethod(operationName="MessageNotAcknowledged")
	public void onMessageNotAcknowledged(@WebParam(name="MessageId") String messageId) throws EbMSEventListenerServiceException;

}