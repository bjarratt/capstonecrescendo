package network.OODSS.base;

import java.util.ArrayList;

import keys.OODSS;

import network.OODSS.messages.ConnectionRequest;
import network.OODSS.messages.ConnectionUpdate;
import network.OODSS.messages.DisconnectRequest;
import network.OODSS.messages.GameTypeRequest;
import network.OODSS.messages.PlayNoteRequest;
import ecologylab.services.messages.DefaultServicesTranslations;
import ecologylab.xml.ElementState;
import ecologylab.xml.TranslationScope;

public class CrescendoTranslation 
{
	public static TranslationScope get()
	{
		TranslationScope scope = DefaultServicesTranslations.get();
		
		ArrayList<Class<? extends ElementState>> elements = scope.getAllClasses();

		ArrayList<Class<? extends ElementState>> pruned = new ArrayList<Class<? extends ElementState>>();
		
		for (Class<? extends ElementState> element : elements)
		{
			if (!element.equals(ecologylab.services.messages.DisconnectRequest.class))
			{
				pruned.add(element);
			}
		}
		
		Class[] elem = new Class[pruned.size()];
		
		return TranslationScope.get(OODSS.TRANSLATION_SPACE_NAME,
									TranslationScope.get("PRUNED_DEFAULT", pruned.toArray(elem)),
									ConnectionRequest.class,
									ConnectionUpdate.class,
									DisconnectRequest.class,
									PlayNoteRequest.class,
									GameTypeRequest.class);
	}
}
