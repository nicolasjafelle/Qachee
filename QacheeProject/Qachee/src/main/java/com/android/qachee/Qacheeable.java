package com.android.qachee;

/**
 * Qacheeable
 */
public interface Qacheeable {

	/**
	 * Returns the key value that will be the key that reference the value.
	 * Typically you will use the "id" of the Database but you can also use
	 * the hashcode value.
	 * @return the key
	 */
	Long getKey();
}
