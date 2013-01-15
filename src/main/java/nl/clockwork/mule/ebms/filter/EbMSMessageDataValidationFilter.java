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
package nl.clockwork.mule.ebms.filter;

import nl.clockwork.common.dao.DAOException;
import nl.clockwork.ebms.Constants;
import nl.clockwork.ebms.dao.EbMSDAO;
import nl.clockwork.ebms.model.EbMSBaseMessage;
import nl.clockwork.ebms.model.ebxml.MessageHeader;
import nl.clockwork.ebms.util.EbMSMessageUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.api.MuleMessage;
import org.mule.api.routing.filter.Filter;

public class EbMSMessageDataValidationFilter implements Filter
{
  protected transient Log logger = LogFactory.getLog(getClass());
	private EbMSDAO ebMSDAO;

	@Override
	public boolean accept(MuleMessage message)
	{
		if (message.getPayload() instanceof EbMSBaseMessage)
		{
			try
			{
				EbMSBaseMessage msg = (EbMSBaseMessage)message.getPayload();
				MessageHeader messageHeader = msg.getMessageHeader();

				String refToMessageId = messageHeader.getMessageData().getRefToMessageId();
				if (!StringUtils.isEmpty(refToMessageId))
					//FIXME refToMessageId does not have to refer to another message or to an Acknowledgment or ErrorMessage
					if (!ebMSDAO.existsMessage(refToMessageId))
					{
						message.setProperty(Constants.EBMS_ERROR,EbMSMessageUtils.createError("//Header/MessageHeader/MessageData/RefToMessageId",Constants.EbMSErrorCode.VALUE_NOT_RECOGNIZED.errorCode(),"Value not found."));
						return false;
					}
				return true;
			}
			catch (DAOException e)
			{
				throw new RuntimeException(e);
			}
		}
		return true;
	}

	public void setEbMSDAO(EbMSDAO ebMSDAO)
	{
		this.ebMSDAO = ebMSDAO;
	}
}
