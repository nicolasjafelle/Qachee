package com.android.qachee;


import android.support.v4.util.LruCache;

/**
 * QacheeManager
 * Created by nicolas on 2/11/14.
 */
public class QacheeManager {

	private LruCache<Long, QacheeObject> cachee;

	// Private constructor prevents instantiation from other classes
	private QacheeManager() { }

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

	public LruCache<Long, QacheeObject> getCachee() {
		return cachee;
	}

	public void setCachee(LruCache<Long, QacheeObject> cachee) {
		this.cachee = cachee;
	}

	public void addToCache(Long key, QacheeObject value) {
		this.cachee.put(key, value);
	}

	public void removeToCache(Long key) {
		this.cachee.remove(key);
	}

	public void clearQachee() {
		this.cachee.evictAll();
	}

}
