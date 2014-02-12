package com.android.qachee;


import android.support.v4.util.LruCache;

/**
 * QacheeManager
 * Created by nicolas on 2/11/14.
 */
public class QacheeManager {

	private LruCache<Long, QacheeObject> qachee;

	// Private constructor prevents instantiation from other classes
	private QacheeManager() {
		final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
		// Use 1/8th of the available memory for this memory cache.
		final int cacheSize = maxMemory / 8;

		qachee = new LruCache<Long, QacheeObject>(cacheSize);


	}

	/**
	 * SingletonHolder is loaded on the first execution of Singleton.getInstance()
	 * or the first access to SingletonHolder.INSTANCE, not before.
	 */
	private static class SingletonHolder {
		public static final QacheeManager instance = new QacheeManager();
	}

	public static QacheeManager getInstance() {
		return SingletonHolder.instance;
	}

	public LruCache<Long, QacheeObject> getQachee() {
		return qachee;
	}

	public void setQachee(LruCache<Long, QacheeObject> qachee) {
		this.qachee = qachee;
	}

	public void addToQachee(Long key, QacheeObject value) {
		this.qachee.put(key, value);
	}

	public void removeFromQachee(Long key) {
		this.qachee.remove(key);
	}

	public void clearQachee() {
		this.qachee.evictAll();
	}

}
