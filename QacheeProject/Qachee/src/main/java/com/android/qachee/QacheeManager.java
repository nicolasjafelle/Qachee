package com.android.qachee;


import android.support.v4.util.LruCache;

import java.util.ArrayList;
import java.util.List;


/**
 * QacheeManager
 * Created by nicolas on 2/11/14.
 */
public class QacheeManager {

	private LruCache<Long, Qacheeable> qachee;

	// Private constructor prevents instantiation from other classes
	private QacheeManager() {
		final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
		// Use 1/8th of the available memory for this memory cache.
		final int cacheSize = maxMemory / 8;

		qachee = new LruCache<Long, Qacheeable>(cacheSize);
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

	public LruCache<Long, Qacheeable> getQachee() {
		return qachee;
	}

	public Qacheeable get(Long key) {
		return qachee.get(key);
	}

	public <T> T get(Long key, Class<T> clazz) {
		return (T)qachee.get(key);
	}

//	public <T> List<T> get(Class<T> clazz) {
//		List<T> list = new ArrayList<T>();
//
//		for(T t: list) {
//			if(clazz.isInstance(t) ) {
//				list.add(t);
//			}
//		}
//		return list;
//	}

	public void setQachee(LruCache<Long, Qacheeable> qachee) {
		this.qachee = qachee;
	}

	public void addToQachee(List<Qacheeable> qachee) {
		for(Qacheeable qacheeable : qachee) {
			this.qachee.put(qacheeable.getKey(), qacheeable);
		}
	}

	public void addToQachee(Qacheeable qacheeable) {
		this.qachee.put(qacheeable.getKey(), qacheeable);
	}

	public <T> void addListToQachee(List<T> qachee ) {
		for(T t : qachee) {
			if(t instanceof Qacheeable) {
				Qacheeable q = (Qacheeable) t;
				this.qachee.put(((Qacheeable) t).getKey(), (Qacheeable) t);
			}
		}
	}

	public void removeFromQachee(Qacheeable qacheeable) {
		this.qachee.remove(qacheeable.getKey());
	}

	public void clearQachee() {
		this.qachee.evictAll();
	}


}
