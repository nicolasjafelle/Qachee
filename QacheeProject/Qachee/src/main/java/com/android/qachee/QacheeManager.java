package com.android.qachee;


import android.support.v4.util.LruCache;

/**
 * QacheeManager
 * Created by nicolas on 2/11/14.
 */
public class QacheeManager {

	private LruCache<Long, QacheeObject> qachee;

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

	public LruCache<Long, QacheeObject> getQachee() {
		return qachee;
	}

	public void setQachee(LruCache<Long, QacheeObject> qachee) {
		this.qachee = qachee;
	}

	public void addToQachee(Long key, QacheeObject value) {
		this.qachee.put(key, value);
	}

	public void removeToQachee(Long key) {
		this.qachee.remove(key);
	}

	public void clearQachee() {
		this.qachee.evictAll();
	}

}
