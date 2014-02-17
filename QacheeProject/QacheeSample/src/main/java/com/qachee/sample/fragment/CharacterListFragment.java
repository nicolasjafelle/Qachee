package com.qachee.sample.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.qachee.QacheeManager;
import com.qachee.sample.DemoLoader;
import com.qachee.sample.R;
import com.qachee.sample.adapter.CharacterAdapter;
import com.qachee.sample.domain.Character;
import com.qachee.sample.task.SafeAsyncTask;

import java.util.List;

/**
 * Created by nicolas on 2/17/14.
 */
public class CharacterListFragment extends Fragment implements AdapterView.OnItemClickListener {

	private CharacterAdapter adapter;
	private ListView listView;


	public static Fragment newInstance() {
		return new CharacterListFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container, false);
		listView = (ListView) rootView.findViewById(R.id.list_view);
		listView.setOnItemClickListener(this);

		return rootView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		loadList();
	}

	private void loadList() {
		List<com.qachee.sample.domain.Character> list = QacheeManager.getInstance().toArray(Character.class);

		if(list == null || list.isEmpty()) {
			new DemoTask(getActivity()).execute();
		}else {
			adapter = new CharacterAdapter(list);
			listView.setAdapter(adapter);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {

		Character selectedCharacter = (Character) listView.getItemAtPosition(pos);

		selectedCharacter = (Character) QacheeManager.getInstance().get(selectedCharacter);
		replace(getActivity(), CharacterEditFragment.newInstance(selectedCharacter.getKey()));


		// OR...
//		long selectedItemKey = ((Character) QacheeManager.getInstance().get(selectedCharacter)).getKey();
//		replace(getActivity(), CharacterEditFragment.newInstance(selectedItemKey);
	}


	public void replace(FragmentActivity activity, Fragment newFragment) {
		FragmentManager fragmentManager = activity.getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.addToBackStack(null);

		fragmentTransaction.replace(R.id.container, newFragment).commit();
	}


	/**
	 * DemoTask
	 */
	public class DemoTask extends SafeAsyncTask<List<Character>> {

		private Context context;
		private ProgressDialog dialog;

		public DemoTask(Context context) {
			this.context = context;
		}

		@Override
		protected void onPreExecute() throws Exception {
			super.onPreExecute();
			dialog = ProgressDialog.show(context, "", context.getString(R.string.loading_data) );
		}

		@Override
		public List<Character> call() throws Exception {
			Thread.sleep(3000);
			return DemoLoader.createData();
		}

		@Override
		protected void onSuccess(List<Character> characters) throws Exception {
			super.onSuccess(characters);

			QacheeManager.getInstance().addList(characters);
			loadList();
		}

		@Override
		protected void onFinally() throws RuntimeException {
			super.onFinally();
			dialog.dismiss();
		}
	}






}
