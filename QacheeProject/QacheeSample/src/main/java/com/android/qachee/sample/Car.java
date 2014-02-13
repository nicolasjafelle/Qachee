package com.android.qachee.sample;

import com.android.qachee.Qacheeable;

/**
 * Created by nicolas on 2/13/14.
 */
public class Car implements Qacheeable{

	private String model;

	@Override
	public Long getKey() {
		return (long) hashCode();
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}
}
