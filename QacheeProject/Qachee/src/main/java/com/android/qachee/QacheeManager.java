package com.android.qachee;


import android.support.v4.util.LruCache;

import java.util.ArrayList;
import java.util.List;


/**
 * QacheeManager. This singleton class is the responsable to save, restore, remove and return your cached objects.
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

	/**
	 * Singleton pattern. Returns the same instance everytime. This singleton is thread safe.
	 * @return
	 */
	public static QacheeManager getInstance() {
		return SingletonHolder.instance;
	}

	/**
	 * Returns the LruCache object.
	 * @return the LruCache object.
	 */
	private LruCache<Long, Qacheeable> getQachee() {
		return qachee;
	}

	/**
	 * Returns the Qacheeable object stored in the cache. If the Qacheeable Object is not in the cache,
	 * it will be saved before return it.
	 * @param qacheeable The Qacheeable object that you get from somewhere.
	 * @return The Qacheeable object stored in the cache.
	 */
	public Qacheeable get(Qacheeable qacheeable) {
		Qacheeable qacheed = qachee.get(qacheeable.getKey());

		if(qacheed == null) {
			add(qacheeable);
			qacheed = get(qacheeable.getKey());
		}
		return qacheed;
	}

	/**
	 * Returns the Qacheeable object which has the same key value.
	 * @param key The key value
	 * @return The Qacheeable object stored in the cache
	 */
	public Qacheeable get(Long key) {
		return qachee.get(key);
	}

	/**
	 * Returns the Object which has the same key value and is stored in the cache.
	 * @param key The key value
	 * @param clazz The Object's class that you are looking for.
	 * @return The Object that which is stored in the cache.
	 */
	public <T> T get(Long key, Class<T> clazz) {
		return (T)qachee.get(key);
	}


	/**
	 * Returns the Qacheeable Object that is stored in the cache. If not, it previously will be saved in the cache.
	 * @param qacheeable The Qacheeable Object that you get from somewhere.
	 * @param clazz The Object's class that you are looking for.
	 * @return The Qacheeable Object that which is stored in the cache.
	 */
	public <T> T get(Qacheeable qacheeable, Class<T> clazz) {
		T qacheed = (T) qachee.get(qacheeable.getKey());

		if(qacheed == null) {
			add(qacheeable);
			qacheed = get(qacheeable.getKey(), clazz);
		}
		return qacheed;
	}

	/**
	 * Sets the LruCache
	 * @param qachee The new LruCache
	 */
	private void setQachee(LruCache<Long, Qacheeable> qachee) {
		this.qachee = qachee;
	}

	/**
	 * Adds a List of Qacheeables into cache.
	 * @param qachee The Qacheeable List
	 */
	public void add(List<Qacheeable> qachee) {
		for(Qacheeable qacheeable : qachee) {
			this.qachee.put(qacheeable.getKey(), qacheeable);
		}
	}

	/**
	 * Adds a Qacheeable Object into cache.
	 * @param qacheeable
	 */
	public void add(Qacheeable qacheeable) {
		this.qachee.put(qacheeable.getKey(), qacheeable);
	}

	/**
	 * Adds a List of some class that you need to store. Typically the class will be your Pojo Class.
	 * @param qachee The List to store in the cache.
	 */
	public <T> void addList(List<T> qachee) {
		for(T t : qachee) {
			if(t instanceof Qacheeable) {
				this.qachee.put(((Qacheeable) t).getKey(), (Qacheeable) t);
			}
		}
	}

	/**
	 * Adds a List of some class that you need to store. Typically the class will be your Pojo Class.
	 * @param qachee The List to store into cache.
	 * @return List<T> The List that you previously stored in the cache.
	 */
	public <T> List<T> addAndReturnList(List<T> qachee) {
		for(T t : qachee) {
			if(t instanceof Qacheeable) {
				this.qachee.put(((Qacheeable) t).getKey(), (Qacheeable) t);
			}
		}
		return qachee;
	}

	/**
	 * Removes the Qacheeable Object previously stored in the cache.
	 * @param qacheeable The Qacheeable Object.
	 */
	public void removeFromQachee(Qacheeable qacheeable) {
		this.qachee.remove(qacheeable.getKey());
	}

	/**
	 * Returns a COPY of all the stored Qacheeable objects.
	 * @return The List of Qacheeable objects.
	 */
	public List<Qacheeable> toArray() {
		return (List<Qacheeable>)this.qachee.snapshot().values();
	}

	/**
	 * Returns a COPY of all the stored objects which are instances of the given class.
	 * @param clazz The Object's Class instance that you are looking for.
	 * @return The List of stored objects.
	 */
	public <T> List<T> toArray(Class<T> clazz) {
		List<T> list = new ArrayList<T>();

		for(Qacheeable qacheeable: this.qachee.snapshot().values()) {
			if(clazz.isInstance(qacheeable)) {
				list.add((T)qacheeable);
			}
		}
		return list;
	}

	/**
	 * Returns a COPY of all the stored Qacheeable objects in primitive array.
	 * @return The array of stored objects.
	 */
	public Qacheeable[] toPrimitiveArray() {
		return (Qacheeable[])this.qachee.snapshot().values().toArray();
	}

	/**
	 * Clears all the cache.
	 */
	public void clearQachee() {
		this.qachee.evictAll();
	}

}
