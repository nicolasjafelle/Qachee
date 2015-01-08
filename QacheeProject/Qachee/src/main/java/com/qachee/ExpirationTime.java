package com.qachee;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Class that is used to set the Expiration Time policy in Qachee.
 */
public final class ExpirationTime {

	@Retention(RetentionPolicy.SOURCE)
	@IntDef({TEN_SECONDS, THIRTY_SECONDS, ONE_MINUTE, TWO_MINUTES, TEN_MINUTES, TWENTY_MINUTES})
	public @interface ExpirationPolicy {
	}

	public static final long TEN_SECONDS = 10000;
	public static final long THIRTY_SECONDS = 30000;
	public static final long ONE_MINUTE = 60000;
	public static final long TWO_MINUTES = 120000;
	public static final long TEN_MINUTES = 600000;
	public static final long TWENTY_MINUTES = 1200000;

}
