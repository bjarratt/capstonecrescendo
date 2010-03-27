package network.OODSS;

import ecologylab.services.messages.DefaultServicesTranslations;
import ecologylab.xml.TranslationScope;

/**
 *
 * @author Brandon Kaster
 * 
 */
public class ServerTranslations {

	public final static String TRANSLATION_SPACE_NAME = "ServerTranslations";
	
	public static TranslationScope get() {
		return TranslationScope.get(TRANSLATION_SPACE_NAME,
				DefaultServicesTranslations.get(), ServerRequest.class,
				ServerUpdate.class);
	}
	
}
