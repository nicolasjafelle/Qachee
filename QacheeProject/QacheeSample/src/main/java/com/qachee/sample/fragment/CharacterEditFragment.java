package com.qachee.sample.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.qachee.QacheeManager;
import com.qachee.sample.R;
import com.qachee.sample.domain.Character;

/**
 * Created by nicolas on 2/17/14.
 */
public class CharacterEditFragment extends Fragment{

	private EditText editName, editDescription;
	private Button saveButton;

	private long currentSelectedCharacter;
	public static final String CURRENT_SELECTED_CHARACTER = "current_selected_character";


	public static Fragment newInstance() {
		return new CharacterEditFragment();
	}

	public static Fragment newInstance(long currentSelectedCharacter) {
		Bundle b = new Bundle();
		b.putLong(CURRENT_SELECTED_CHARACTER, currentSelectedCharacter);

		Fragment f = new CharacterEditFragment();
		f.setArguments(b);
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_edit, container, false);
		editName = (EditText) rootView.findViewById(R.id.fragment_edit_edit_text_name);
		editDescription = (EditText) rootView.findViewById(R.id.fragment_edit_edit_text_description);
		saveButton = (Button) rootView.findViewById(R.id.fragment_edit_save_button);

		saveButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				saveData();
			}
		});

		return rootView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		loadData();
	}

	private void loadData() {

		if(getArguments() != null && getArguments().getLong(CURRENT_SELECTED_CHARACTER) != 0) {
			currentSelectedCharacter = getArguments().getLong(CURRENT_SELECTED_CHARACTER);

			com.qachee.sample.domain.Character character = QacheeManager.getInstance().get(currentSelectedCharacter, Character.class);
			if(character != null) {
				editName.setText(character.getName());
				editDescription.setText(character.getDescription());
			}
		}
	}

	private void saveData() {
		if(currentSelectedCharacter != 0) {
			Character character = QacheeManager.getInstance().get(currentSelectedCharacter, Character.class);

			if(character != null) {
				character.setName(editName.getText().toString());
				character.setDescription(editDescription.getText().toString());
				Toast.makeText(getActivity(), R.string.successfully_updated, Toast.LENGTH_SHORT).show();
				goBack();
			}
		}
	}

	private void goBack() {
		getActivity().getSupportFragmentManager().popBackStack();
	}






}
