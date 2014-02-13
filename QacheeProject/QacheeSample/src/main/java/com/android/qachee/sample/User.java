package com.android.qachee.sample;

import com.android.qachee.Qacheeable;

/**
 * Created by nicolas on 2/13/14.
 */
public class User implements Qacheeable{

	public Long id;

	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Long getKey() {
		return id;
	}
}
