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
package nl.clockwork.ebms.client;

import java.io.IOException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

import nl.clockwork.ebms.common.util.DOMUtils;
import nl.clockwork.ebms.model.EbMSDocument;
import nl.clockwork.ebms.processor.EbMSProcessorException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EbMSHttpClientN implements EbMSClient
{
  protected transient Log logger = LogFactory.getLog(getClass());
	private SSLFactoryManager sslFactoryManager;
	protected boolean chunkedStreamingMode = true;
	
	public EbMSHttpClientN()
	{
		//System.setProperty("http.keepAlive","false");
	}

	public EbMSDocument sendMessage(String uri, EbMSDocument document) throws EbMSProcessorException
	{
		HttpURLConnection connection = null;
		try
		{
			connection = (HttpURLConnection)openConnection(uri);
			//connection.setConnectTimeout(connectTimeout);
			if (logger.isInfoEnabled())
			{
				logger.info("Connection to: " + uri);
				logger.info("OUT:\n" + DOMUtils.toString(document.getMessage()));
			}
			if (chunkedStreaming(uri))
				connection.setChunkedStreamingMode(0);
			EbMSMessageWriter writer = new EbMSMessageWriter(connection);
			writer.write(document);
			connection.connect();
			EbMSMessageReader reader = new EbMSMessageReader(connection);
			EbMSDocument in = reader.read();
			if (logger.isInfoEnabled())
				logger.info("IN:\n" + (in == null || in.getMessage() == null ? "" : DOMUtils.toString(in.getMessage())));
			return in;
		}
		catch (ConnectException e)
		{
			throw new EbMSProcessorException("Error connecting to: " + uri,e);
		}
		catch (Exception e)
		{
			throw new EbMSProcessorException(e);
		}
		finally
		{
			if (connection != null)
				connection.disconnect();
		}
	}
	
	public boolean chunkedStreaming(String uri)
	{
		return chunkedStreamingMode;
	}

	@SuppressWarnings({"restriction","deprecation"})
	private URLConnection openConnection(String uri) throws IOException
	{
		URL url = new URL(uri);
		URLConnection connection = url.openConnection();
		connection.setDoOutput(true);
		//connection.setMethod("POST");
		if (connection instanceof HttpsURLConnection)
			((HttpsURLConnection)connection).setSSLSocketFactory(sslFactoryManager.getSslFactory());
		else if (connection instanceof com.sun.net.ssl.HttpsURLConnection)
			((com.sun.net.ssl.HttpsURLConnection)connection).setSSLSocketFactory(sslFactoryManager.getSslFactory());
		return connection;
	}

	public void setSslFactoryManager(SSLFactoryManager sslFactoryManager)
	{
		this.sslFactoryManager = sslFactoryManager;
	}
	
	public void setChunkedStreamingMode(boolean chunkedStreamingMode)
	{
		this.chunkedStreamingMode = chunkedStreamingMode;
	}
}