package nl.clockwork.ebms.signing;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.List;

import nl.clockwork.common.util.SecurityUtils;
import nl.clockwork.ebms.model.EbMSAttachment;
import nl.clockwork.ebms.xml.dsig.EbMSAttachmentResolver;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.signature.XMLSignatureException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class XMLSecSignatureValidator implements SignatureValidator
{
  protected transient Log logger = LogFactory.getLog(getClass());
  private String keyStorePath;
	private String keyStorePassword;

	public XMLSecSignatureValidator()
	{
		org.apache.xml.security.Init.init();
	}
	
	public XMLSecSignatureValidator(String keyStorePath, String keyStorePassword)
	{
		this.keyStorePath = keyStorePath;
		this.keyStorePassword = keyStorePassword;
	}

	@Override
	public boolean validateMessage(Document document, List<EbMSAttachment> attachments) throws Exception
	{
		KeyStore keyStore = SecurityUtils.loadKeyStore(keyStorePath,keyStorePassword);
		return verify(keyStore,document,attachments);
	}

	private boolean verify(KeyStore keyStore, Document document, List<EbMSAttachment> attachments) throws XMLSignatureException, XMLSecurityException, CertificateExpiredException, CertificateNotYetValidException, KeyStoreException
	{
		NodeList nodeList = document.getElementsByTagNameNS(org.apache.xml.security.utils.Constants.SignatureSpecNS,org.apache.xml.security.utils.Constants._TAG_SIGNATURE);
		if (nodeList.getLength() > 0)
		{
			XMLSignature signature = new XMLSignature((Element)nodeList.item(0),org.apache.xml.security.utils.Constants.SignatureSpecNS);
	
			EbMSAttachmentResolver resolver = new EbMSAttachmentResolver(attachments);
			signature.addResourceResolver(resolver);
	
			X509Certificate certificate = signature.getKeyInfo().getX509Certificate();
			if (certificate != null)
			{
				certificate.checkValidity();
				Enumeration<String> aliases = keyStore.aliases();
				while (aliases.hasMoreElements())
				{
					try
					{
						Certificate c = keyStore.getCertificate(aliases.nextElement());
						certificate.verify(c.getPublicKey());
						return signature.checkSignatureValue(certificate);
					}
					catch (KeyStoreException e)
					{
						throw e;
					}
					catch (Exception e)
					{
					}
				}
			}
			else
			{
				PublicKey publicKey = signature.getKeyInfo().getPublicKey();
				if (publicKey != null)
					return signature.checkSignatureValue(publicKey);
			}
			return false;
		}
		return true;
	}

	public void setKeyStorePath(String keyStorePath)
	{
		this.keyStorePath = keyStorePath;
	}
	
	public void setKeyStorePassword(String keyStorePassword)
	{
		this.keyStorePassword = keyStorePassword;
	}
}