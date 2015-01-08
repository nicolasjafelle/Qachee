package com.qachee.sample.domain;

import android.util.Log;

import com.qachee.QacheeableObject;

/**
 * Created by nicolas on 2/17/14.
 */
public class Character extends QacheeableObject {

	private String name;
	private String description;
	private int imageResId;

	public Character() {
		super();
	}

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
	public String getKey() {
		Log.i("HASHCHODE", "KEY VALUE FOR " + getName() + "= " + getClass().getSimpleName() + hashCode());
		return getClass().getSimpleName() + hashCode();
	}

}
