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
package nl.clockwork.mule.ebms.transformer;

import nl.clockwork.ebms.Constants;
import nl.clockwork.ebms.dao.EbMSDAO;
import nl.clockwork.ebms.model.EbMSMessage;
import nl.clockwork.ebms.util.EbMSMessageUtils;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageAwareTransformer;

public class EbMSMessageIdPropertyToEbMSMessageContent extends AbstractMessageAwareTransformer
{
	private EbMSDAO ebMSDAO;

	public EbMSMessageIdPropertyToEbMSMessageContent()
	{
		//registerSourceType(EbMSMessage.class);
	}

	@Override
	public Object transform(MuleMessage message, String outputEncoding) throws TransformerException
	{
		try
		{
			long id = message.getLongProperty(Constants.EBMS_MESSAGE_ID,0);
			EbMSMessage msg = (EbMSMessage)ebMSDAO.getMessage(id);
			message.setPayload(EbMSMessageUtils.EbMSMessageToEbMSMessageContent(msg));

			return message;
		}
		catch (Exception e)
		{
			throw new TransformerException(this,e);
		}
	}

	public void setEbMSDAO(EbMSDAO ebMSDAO)
	{
		this.ebMSDAO = ebMSDAO;
	}
	
}
