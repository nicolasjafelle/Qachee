package com.android.qachee.sample.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.qachee.QacheeManager;
import com.android.qachee.sample.domain.Character;
import com.android.qachee.sample.view.CharacterItemView;

import java.util.List;

/**
 * CharacterAdapter
 */
public class CharacterAdapter extends BaseAdapter {

	private List<com.android.qachee.sample.domain.Character> list;

	public CharacterAdapter(List<Character> list) {
		super();
		this.list = list;
	}

	@Override
	public int getCount() {
		return this.list.size();
	}

	@Override
	public Character getItem(int i) {
		return this.list.get(i);
	}

	@Override
	public long getItemId(int i) {
		return this.list.get(i).getKey();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		CharacterItemView itemView;
		Character character = QacheeManager.getInstance().get(getItem(position), Character.class);

		if(convertView != null) {
			itemView = (CharacterItemView)convertView;
		}else {
			itemView = new CharacterItemView(parent.getContext());
		}

		itemView.loadData(character);
		return itemView;
	}
}
