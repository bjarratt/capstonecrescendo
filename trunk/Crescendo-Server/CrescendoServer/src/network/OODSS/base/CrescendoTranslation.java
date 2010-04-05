package network.OODSS.base;

import network.OODSS.messages.ConnectionRequest;
import network.OODSS.messages.ConnectionUpdate;
import ecologylab.services.messages.DefaultServicesTranslations;
import ecologylab.xml.TranslationScope;

public class CrescendoTranslation 
{
	public static final String TRANSLATION_SPACE_NAME = "CrescendoTranslation";
	
	public static TranslationScope get()
	{
		return TranslationScope.get(TRANSLATION_SPACE_NAME,
									DefaultServicesTranslations.get(),
									ConnectionRequest.class,
									ConnectionUpdate.class);
	}
}
