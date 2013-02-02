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
package nl.clockwork.mule.ebms.stub.enricher;

import java.io.IOException;
import java.util.Arrays;

import nl.clockwork.ebms.model.EbMSDataSource;
import nl.clockwork.mule.ebms.stub.util.Utils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageAwareTransformer;

public class XMLToEbMSDataSourceEnricher extends AbstractMessageAwareTransformer
{
  protected transient Log logger = LogFactory.getLog(getClass());

	public XMLToEbMSDataSourceEnricher()
	{
		registerSourceType(String.class);
		//FIXME
		//setReturnClass(String.class);
	}
	
	@Override
	public Object transform(final MuleMessage message, String outputEncoding) throws TransformerException
	{
		try
		{
			String fileName = message.getStringProperty("originalFilename","message.xml");
			EbMSDataSource dataSource = new EbMSDataSource(fileName,StringUtils.defaultIfEmpty(Utils.getMimeType(fileName),"application/octet-stream"),((String)message.getPayload()).getBytes()); //application/xml
			message.setPayload(Arrays.asList(dataSource));
			return message;
		}
		catch (IOException e)
		{
			throw new TransformerException(this,e);
		}
	}
	
}
