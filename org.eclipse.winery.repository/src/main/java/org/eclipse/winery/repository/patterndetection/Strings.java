package org.eclipse.winery.repository.patterndetection;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Created by marvin on 24.05.2017.
 */
public class Strings {

	private static final String BUNDLE_NAME = "patterndetection"; //$NON-NLS-1$


	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
