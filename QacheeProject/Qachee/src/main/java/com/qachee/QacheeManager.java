package com.qachee;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.LruCache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


/**
 * QacheeManager. This singleton class is the responsible to save, restore, remove and return your cached objects.
 */
public class QacheeManager {

	private LruCache<String, Qacheeable> qachee;
	private long expirationTime;


	// Private constructor prevents instantiation from other classes
	private QacheeManager() {
		final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
		// Use 1/8th of the available memory for this memory cache.
		final int cacheSize = maxMemory / 8;

		qachee = new LruCache<>(cacheSize);
		expirationTime = ExpirationTime.ONE_MINUTE;
	}

	/**
	 * SingletonHolder is loaded on the first execution of Singleton.getInstance()
	 * or the first access to SingletonHolder.INSTANCE, not before.
	 */
	private static class SingletonHolder {
		public static final QacheeManager instance = new QacheeManager();
	}

	/**
	 * Singleton pattern. Returns the same instance everytime. This singleton is thread safe.<br>
	 * By default the ExpirationTime is ONE_MINUTE.
	 *
	 * @return
	 */
	public static QacheeManager getInstance() {
		return SingletonHolder.instance;
	}

	/**
	 * Sets the ExpirationTime policy. By default is ONE_MINUTE.
	 *
	 * @param expirationTime
	 */
	public void setExpirationTime(@ExpirationTime.ExpirationPolicy long expirationTime) {
		this.expirationTime = expirationTime;
	}

	/**
	 * Sets the size of the cache.
	 *
	 * @param maxSize The new maximum size.
	 */
	public final void setCacheSize(int maxSize) {
		qachee.resize(maxSize);
	}

	/**
	 * Returns the LruCache object.
	 *
	 * @return the LruCache object.
	 */
	@NonNull
	private LruCache<String, Qacheeable> getQachee() {
		return qachee;
	}

	/**
	 * Returns the Qacheeable object stored in the cache. If the Qacheeable Object is not in the cache,
	 * it will be saved before return it.
	 *
	 * @param qacheeable      The Qacheeable object that you get from somewhere.
	 * @param checkLastUpdate true if check for last updated time, false otherwise.
	 * @return The Qacheeable object stored in the cache.
	 */
	@Nullable
	public Qacheeable get(@NonNull Qacheeable qacheeable, boolean checkLastUpdate) {

		Qacheeable qacheed = qachee.get(qacheeable.getKey());

		if (qacheed != null) {
			if (checkLastUpdate && qacheed.lastUpdate() > expirationTime) {
				qacheed = null;
			} else {
				add(qacheeable);
			}
		} else {
			add(qacheeable);
		}
		return qacheed;
	}


	/**
	 * Returns the Qacheeable object which has the same key value.
	 *
	 * @param key The key value
	 * @return The Qacheeable object stored in the cache
	 */
	@Nullable
	public Qacheeable get(@NonNull String key, boolean checkLastUpdate) {

		Qacheeable qacheed = qachee.get(key);

		if (qacheed != null) {
			if (checkLastUpdate && qacheed.lastUpdate() > expirationTime) {
				qacheed = null;
			} else {
				add(qacheed);
			}
		}

		return qacheed;
	}

	/**
	 * Returns the Object which has the same key value and is stored in the cache.
	 *
	 * @param key   The key value
	 * @param clazz The Object's class that you are looking for.
	 * @return The Object that which is stored in the cache.
	 */
	@Nullable
	public <T> T get(@NonNull String key, @NonNull Class<T> clazz, boolean checkLastUpdate) {
		T qacheed = (T) qachee.get(key);

		if (qacheed != null) {
			if (checkLastUpdate && ((QacheeableObject) qacheed).lastUpdate() > expirationTime) {
				qacheed = null;
			} else {
				add((QacheeableObject) qacheed);
			}
		}

		return qacheed;
	}


	/**
	 * Returns the Qacheeable Object that is stored in the cache. If not, it previously will be saved in the cache.
	 *
	 * @param qacheeable The Qacheeable Object that you get from somewhere.
	 * @param clazz      The Object's class that you are looking for.
	 * @return The Qacheeable Object that which is stored in the cache.
	 */
	@Nullable
	public <T> T get(@NonNull Qacheeable qacheeable, @NonNull Class<T> clazz, boolean checkLastUpdate) {
		T qacheed = (T) qachee.get(qacheeable.getKey());

		if (qacheed == null) {
			add(qacheeable);
			qacheed = get(qacheeable.getKey(), clazz, checkLastUpdate);
		}
		return qacheed;
	}

	/**
	 * Sets the LruCache
	 *
	 * @param qachee The new LruCache
	 */
	private void setQachee(@NonNull LruCache<String, Qacheeable> qachee) {
		this.qachee = qachee;
	}

	/**
	 * Adds a List of Qacheeables into cache.
	 *
	 * @param qachee The Qacheeable List
	 */
	public void add(@NonNull List<Qacheeable> qachee) {
		for (Qacheeable qacheeable : qachee) {
			this.qachee.put(qacheeable.getKey(), qacheeable);
		}
	}

	/**
	 * Adds a Qacheeable Object into cache.
	 *
	 * @param qacheeable
	 * @return The Qacheeable's key
	 */
	public String add(@NonNull Qacheeable qacheeable) {
		this.qachee.put(qacheeable.getKey(), qacheeable);
		return qacheeable.getKey();
	}

	/**
	 * Adds a List of some class that you need to store. Typically the class will be your Pojo Class.
	 *
	 * @param qachee The List to store in the cache.
	 */
	public <T> void addList(@NonNull List<T> qachee) {
		for (T t : qachee) {
			if (t instanceof Qacheeable) {
				this.qachee.put(((Qacheeable) t).getKey(), (Qacheeable) t);
			}
		}
	}

	/**
	 * Adds a List of some class that you need to store. Typically the class will be your Pojo Class.
	 *
	 * @param qachee The List to store into cache.
	 * @return List<T> The List that you previously stored in the cache.
	 */
	@NonNull
	public <T> List<T> addAndReturnList(@NonNull List<T> qachee) {
		for (T t : qachee) {
			if (t instanceof Qacheeable) {
				this.qachee.put(((Qacheeable) t).getKey(), (Qacheeable) t);
			}
		}
		return qachee;
	}

	/**
	 * Removes the Qacheeable Object previously stored in the cache.
	 *
	 * @param qacheeable The Qacheeable Object.
	 */
	public void remove(@NonNull Qacheeable qacheeable) {
		this.qachee.remove(qacheeable.getKey());
	}


	/**
	 * Returns a COPY of all the stored objects which are instances of the given class. It also check or not
	 * for the last updated time.
	 *
	 * @param clazz           The Object's Class instance that you are looking for.
	 * @param checkLastUpdate true if check for last updated time, false otherwise.
	 * @return The List of stored objects or null if one of its items has expired.
	 */
	@Nullable
	private <T> List<T> toArray(@NonNull Class<T> clazz, boolean checkLastUpdate) {
		Qacheeable qacheeable;
		Collection<Qacheeable> list = this.qachee.snapshot().values();
		List<T> resultList = new ArrayList<T>();
		Iterator<Qacheeable> it = list.iterator();

		while (it.hasNext() && resultList != null) {
			qacheeable = it.next();

			if (checkLastUpdate && (qacheeable.lastUpdate() > expirationTime)) {
				resultList = null;

			} else if (clazz.isInstance(qacheeable)) {
				resultList.add((T) qacheeable);
			}
		}
		return resultList;
	}

	/**
	 * Removes all the Qacheeable Objects which are instancies of clazz.
	 *
	 * @param clazz The class which instances should be removed.
	 */
	public <T> void removeAll(@NonNull Class<T> clazz) {
		List<T> list = toArray(clazz, false);

		if (list != null) {
			for (T t : list) {
				if (t instanceof Qacheeable) {
					this.qachee.remove(((Qacheeable) t).getKey());
				}
			}
		}
	}


	/**
	 * Returns a COPY of all the stored objects which are instances of the given class.
	 *
	 * @param clazz The Object's Class instance that you are looking for.
	 * @return The List of stored objects or null if one of its items has expired.
	 */
	@Nullable
	public <T> List<T> toArray(@NonNull Class<T> clazz) {
		return toArray(clazz, true);
	}

	/**
	 * Returns a COPY of all the stored Qacheeable objects.
	 *
	 * @return The List of Qacheeable objects.
	 */
	@Nullable
	public List<Qacheeable> toArray() {
		return toArray(Qacheeable.class, true);
	}

	/**
	 * Returns a COPY of all the stored Qacheeable objects in primitive array.
	 *
	 * @return The array of stored objects.
	 */
	@Nullable
	public Qacheeable[] toPrimitiveArray() {
		List<Qacheeable> list = toArray(Qacheeable.class, true);
		return (list == null) ? null : list.toArray(new Qacheeable[list.size()]);
	}

	/**
	 * Clears all the cache.
	 */
	public void clearQachee() {
		this.qachee.evictAll();
	}


}
