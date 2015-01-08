package com.qachee;


/**
 * This abstract class is the responsible to check and update the last updated time. Typically POJOs
 * should be subclasses of QacheeableObject.
 */
public abstract class QacheeableObject implements Qacheeable {

	private long lastUpdate;

	public QacheeableObject() {
		this.lastUpdate = System.currentTimeMillis();
	}

	@Override
	public long lastUpdate() {
		long result = System.currentTimeMillis() - this.lastUpdate;
		update();
		return result;
	}

	@Override
	public void update() {
		this.lastUpdate = System.currentTimeMillis();
	}
}
