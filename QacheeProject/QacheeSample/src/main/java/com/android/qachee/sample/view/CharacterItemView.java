package com.android.qachee.sample.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.qachee.sample.R;


/**
 * CharacterItemView
 */
public class CharacterItemView extends LinearLayout {

	private ImageView image;
	private TextView title;

	public CharacterItemView(Context context) {
		super(context);
		init();
	}

	public CharacterItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public CharacterItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		inflate(getContext(), R.layout.character_item_view, this);

		image = (ImageView) findViewById(R.id.character_item_view_image);
		title = (TextView) findViewById(R.id.character_item_view_title);
	}

	public void loadData(com.android.qachee.sample.domain.Character character) {
		image.setImageResource(character.getImageResId());
		title.setText(character.getName());
	}



}
