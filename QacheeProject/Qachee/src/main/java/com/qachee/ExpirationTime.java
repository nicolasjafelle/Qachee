package com.qachee;

/**
 * Enum class that is used to set the Expiration Time policy in Qachee.
 */
public enum ExpirationTime {

	TEN_SECONDS(10000),
	THIRTY_SECONDS(30000),
	ONE_MINUTE(60000),
	TWO_MINUTES(120000),
	TEN_MINUTES(600000),
	TWENTY_MINUTES(1200000);

	private long time;

	private ExpirationTime(long time) {
		this.time = time;
	}

	public long getValue() {
		return this.time;
	}

}
