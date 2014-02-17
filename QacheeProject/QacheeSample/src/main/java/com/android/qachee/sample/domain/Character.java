package com.android.qachee.sample.domain;

import android.util.Log;

import com.android.qachee.Qacheeable;

/**
 * Created by nicolas on 2/17/14.
 */
public class Character implements Qacheeable {

	private String name;
	private String description;
	private int imageResId;

	public Character(){}

	public Character(String name, String description, int imageResId) {
		this.name = name;
		this.description = description;
		this.imageResId = imageResId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getImageResId() {
		return imageResId;
	}

	public void setImageResId(int imageResId) {
		this.imageResId = imageResId;
	}

	@Override
	public Long getKey() {
		Log.i("HASHCHODE", "KEY VALUE FOR " + getName() + "= " + hashCode());
		return (long)hashCode();
	}
}
