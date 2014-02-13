package com.android.qachee;


import android.support.v4.util.LruCache;

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

	private LruCache<Long, Qacheeable> getQachee() {
		return qachee;
	}

	public Qacheeable get(Qacheeable qacheeable) {
		Qacheeable qacheed = qachee.get(qacheeable.getKey());

		if(qacheed == null) {
			add(qacheeable);
			qacheed = get(qacheeable.getKey());
		}
		return qacheed;
	}

	public Qacheeable get(Long key) {
		return qachee.get(key);
	}

	public <T> T get(Long key, Class<T> clazz) {
		return (T)qachee.get(key);
	}


	public <T> T get(Qacheeable qacheeable, Class<T> clazz) {
		T qacheed = (T) qachee.get(qacheeable.getKey());

		if(qacheed == null) {
			add(qacheeable);
			qacheed = get(qacheeable.getKey(), clazz);
		}
		return qacheed;
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

	private void setQachee(LruCache<Long, Qacheeable> qachee) {
		this.qachee = qachee;
	}

	public void add(List<Qacheeable> qachee) {
		for(Qacheeable qacheeable : qachee) {
			this.qachee.put(qacheeable.getKey(), qacheeable);
		}
	}

	public void add(Qacheeable qacheeable) {
		this.qachee.put(qacheeable.getKey(), qacheeable);
	}

	public <T> void addList(List<T> qachee) {
		for(T t : qachee) {
			if(t instanceof Qacheeable) {
				this.qachee.put(((Qacheeable) t).getKey(), (Qacheeable) t);
			}
		}
	}

	public <T> List<T> addAndReturnList(List<T> qachee) {
		for(T t : qachee) {
			if(t instanceof Qacheeable) {
				this.qachee.put(((Qacheeable) t).getKey(), (Qacheeable) t);
			}
		}
		return qachee;
	}

	public void removeFromQachee(Qacheeable qacheeable) {
		this.qachee.remove(qacheeable.getKey());
	}

	public void clearQachee() {
		this.qachee.evictAll();
	}


}
