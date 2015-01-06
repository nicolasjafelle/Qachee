package com.qachee;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.LruCache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * QacheeManager. This singleton class is the responsible to save, restore, remove and return your cached objects.
 */
public class QacheeManager {

	private Map<Class, LruCache<Long, Qacheeable>> qachee;
	private long expirationTime;
	private int mCacheSize;


	// Private constructor prevents instantiation from other classes
	private QacheeManager() {
		final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
		// Use 1/8th of the available memory for this memory cache.
		mCacheSize = maxMemory / 8;

		qachee = new HashMap<>();
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
	 * Returns the LruCache object.
	 *
	 * @return the LruCache object.
	 */
	@Nullable
	private LruCache<Long, Qacheeable> getQachee(@NonNull Class clazz) {
		return qachee.get(clazz);
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
		LruCache<Long, Qacheeable> cache = qachee.get(qacheeable.getClass());

		Qacheeable qacheed = null;
		if (cache != null) {
			qacheed = cache.get(qacheeable.getKey());

			if (qacheed != null) {
				if (checkLastUpdate && qacheed.lastUpdate() > expirationTime) {
					qacheed = null;
				} else {
					add(qacheeable);
				}
			} else {
				add(qacheeable);
			}
		}

		return qacheed;
	}

	/**
	 * @deprecated This method could not reliably return the intended object if there are multiple
	 * class type that returns the same Qacheeable#getKey. Use {@link #get(Long, Class, boolean)} instead
	 */
	@Deprecated
	@Nullable
	public Qacheeable get(Long key, boolean checkLastUpdate) {

		Qacheeable qacheed = search(key);

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
	 * Searches all entries matching the key
	 *
	 * @param key The key to search for
	 * @return Qacheeable object, otherwise null
	 */
	@Nullable
	private Qacheeable search(Long key) {
		Qacheeable qacheed = null;
		for (LruCache<Long, Qacheeable> entry : qachee.values()) {
			qacheed = entry.get(key);
			if (qacheed != null) break;
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
	public <T> T get(@NonNull Long key, @NonNull Class<T> clazz, boolean checkLastUpdate) {
		LruCache<Long, Qacheeable> cache = qachee.get(clazz);

		T qacheed = null;
		if (cache != null) {
			qacheed = (T) cache.get(key);

			if (qacheed != null) {
				if (checkLastUpdate && ((QacheeableObject) qacheed).lastUpdate() > expirationTime) {
					qacheed = null;
				} else {
					add((QacheeableObject) qacheed);
				}
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
		LruCache<Long, Qacheeable> cache = qachee.get(clazz);

		T qacheed = null;
		if (cache != null) {
			qacheed = (T) qachee.get(qacheeable.getKey());

			if (qacheed == null) {
				add(qacheeable);
				qacheed = get(qacheeable.getKey(), clazz, checkLastUpdate);
			}
		}
		return qacheed;
	}

	/**
	 * Adds a List of Qacheeables into cache.
	 *
	 * @param qachee The Qacheeable List
	 */
	public void add(@NonNull List<Qacheeable> qachee) {
		for (Qacheeable qacheeable : qachee) {
			LruCache<Long, Qacheeable> cache = this.qachee.get(qacheeable.getClass());
			if (cache == null) {
				cache = new LruCache<>(mCacheSize);
				this.qachee.put(qacheeable.getClass(), cache);
			}
			cache.put(qacheeable.getKey(), qacheeable);
		}
	}

	/**
	 * Adds a Qacheeable Object into cache.
	 *
	 * @param qacheeable
	 * @return The Qacheeable's key
	 */
	public long add(@NonNull Qacheeable qacheeable) {
		LruCache<Long, Qacheeable> cache = this.qachee.get(qacheeable.getClass());
		if (cache == null) {
			cache = new LruCache<>(mCacheSize);
			this.qachee.put(qacheeable.getClass(), cache);
		}
		cache.put(qacheeable.getKey(), qacheeable);
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
				add((Qacheeable) t);
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
				add((Qacheeable) t);
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
		LruCache<Long, Qacheeable> cache = this.qachee.get(qacheeable.getClass());
		if (cache != null) {
			cache.remove(qacheeable.getKey());
		}
	}

	/**
	 * Returns a COPY of all the stored objects which are instances of the given class. It also
	 * check or not for the last updated time.
	 *
	 * @param clazz           The Object's Class instance that you are looking for.
	 * @param checkLastUpdate true if check for last updated time, false otherwise.
	 * @return The List of stored objects or null if one of its items has expired.
	 */
	@Nullable
	private <T> List<T> toArray(@NonNull Class<T> clazz, boolean checkLastUpdate) {
		LruCache<Long, Qacheeable> cache = this.qachee.get(clazz);
		if (cache == null) {
			return null;
		}

		Collection<Qacheeable> list = cache.snapshot().values();
		List<T> resultList = new ArrayList<T>(list.size());
		Iterator<Qacheeable> it = list.iterator();

		Qacheeable qacheeable;
		while (it.hasNext()) {
			qacheeable = it.next();

			if (checkLastUpdate && (qacheeable.lastUpdate() > expirationTime)) {
				resultList.clear();
				resultList = null;
				break;

			} else if (clazz.isInstance(qacheeable)) {
				resultList.add((T) qacheeable);
			}
		}
		return resultList;
	}

	/**
	 * Removes all the Qacheeable Objects which are instances of clazz.
	 *
	 * @param clazz The class which instances should be removed.
	 */
	public <T> void removeAll(@NonNull Class<T> clazz) {
		LruCache<Long, Qacheeable> cache = this.qachee.get(clazz);
		if (cache != null) {
			cache.evictAll();
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
		for (LruCache<Long, Qacheeable> cache : qachee.values()) {
			cache.evictAll();
		}
	}

}
