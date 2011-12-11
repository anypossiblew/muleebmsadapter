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

import java.util.Date;

import nl.clockwork.mule.ebms.Constants;
import nl.clockwork.mule.ebms.channel.Channel;
import nl.clockwork.mule.ebms.channel.ChannelManager;
import nl.clockwork.mule.ebms.dao.EbMSDAO;
import nl.clockwork.mule.ebms.model.EbMSMessageContent;
import nl.clockwork.mule.ebms.model.cpp.cpa.CollaborationProtocolAgreement;
import nl.clockwork.mule.ebms.model.ebxml.AckRequested;
import nl.clockwork.mule.ebms.model.ebxml.Manifest;
import nl.clockwork.mule.ebms.model.ebxml.MessageHeader;
import nl.clockwork.mule.ebms.model.ebxml.Reference;
import nl.clockwork.mule.ebms.util.EbMSMessageUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageAwareTransformer;

public class EbMSMessageContentToEbMSMessage extends AbstractMessageAwareTransformer
{
	protected transient Log logger = LogFactory.getLog(getClass());
	private ChannelManager channelManager;
	private EbMSDAO ebMSDAO;
	private String hostname;

  public EbMSMessageContentToEbMSMessage()
	{
		registerSourceType(EbMSMessageContent.class);
	}
  
	@Override
	public Object transform(MuleMessage message, String outputEncoding) throws TransformerException
	{
		try
		{
			EbMSMessageContent content = (EbMSMessageContent)message.getPayload();
			Channel channel = channelManager.getChannel(content.getContext(),(String)message.getProperty(Constants.EBMS_CHANNEL_ID));
			CollaborationProtocolAgreement cpa = ebMSDAO.getCPA(channel.getCpaId());
			
			MessageHeader messageHeader = EbMSMessageUtils.createMessageHeader(cpa,channel.getActionId(),content.getContext() != null ? content.getContext().getConversationId() : new Date().getTime() + message.getCorrelationId(),new Date().getTime() + message.getCorrelationId() + "@" + hostname);

			AckRequested ackRequested = EbMSMessageUtils.createAckRequested();
			
			Manifest manifest = EbMSMessageUtils.createManifest();

			Reference reference = new Reference();
			reference.setHref("cid:1");
			reference.setType("simple");
			//reference.setRole("XLinkRole");

			manifest.getReference().add(reference);
			
			message.setPayload(new Object[]{messageHeader,ackRequested,manifest});

			return message;
		}
		catch (Exception e)
		{
			throw new TransformerException(this,e);
		}
	}

	public void setChannelManager(ChannelManager channelManager)
	{
		this.channelManager = channelManager;
	}

	public void setEbMSDAO(EbMSDAO ebMSDAO)
	{
		this.ebMSDAO = ebMSDAO;
	}
	
	public void setHostname(String hostname)
	{
		this.hostname = hostname;
	}

}
