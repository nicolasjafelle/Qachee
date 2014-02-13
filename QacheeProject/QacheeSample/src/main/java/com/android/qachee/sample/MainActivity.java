package com.android.qachee.sample;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.qachee.QacheeManager;
import com.android.qachee.Qacheeable;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

	    List<Qacheeable> users = new ArrayList<Qacheeable>();
	    User user1 = new User();
	    user1.setId(1l); user1.setName("Nicolas");
	    User user2 = new User();
	    user2.setId(2l); user2.setName("Jhon");
	    User user3 = new User();
	    user3.setId(3l); user3.setName("Emma");
	    User user4 = new User();
	    user4.setId(4l); user4.setName("David");
	    users.add(user1); users.add(user2); users.add(user3); users.add(user4);


	    QacheeManager.getInstance().add(users);

	    List<Qacheeable> cars = new ArrayList<Qacheeable>();
	    Car car1 = new Car(); car1.setModel("Toyota");
	    Car car2 = new Car(); car2.setModel("Ford");
	    Car car3 = new Car(); car3.setModel("Ferrari");
	    Car car4 = new Car(); car4.setModel("McLaren");
	    cars.add(car1);cars.add(car2);cars.add(car3);cars.add(car4);

	    QacheeManager.getInstance().add(cars);
		QacheeManager.getInstance().clearQachee();


	    List<User> userList = new ArrayList<User>();
	    userList.add(user1); userList.add(user2); userList.add(user3); userList.add(user4);


	    QacheeManager.getInstance().addList(userList);

	    List<Car> carList = new ArrayList<Car>();
	    carList.add(car1);carList.add(car2);carList.add(car3);carList.add(car4);

	    QacheeManager.getInstance().addList(carList);

	    Qacheeable q = QacheeManager.getInstance().get(1l, User.class);

	    User userFromCache = QacheeManager.getInstance().get(1l, User.class);
	    userFromCache.setName("pruebaaaa");


	    User userNew = new User(); userNew.setId(7l); userNew.setName("Michael");
	    User sameUser = QacheeManager.getInstance().get(userNew, User.class);
	    sameUser.setName("Michael Reborn");

	    Log.i("TAG", q.getKey() + "");
	    Log.i("TAG", userFromCache.getId() + " " + userFromCache.getName());

	    List<User> cachedUsers = QacheeManager.getInstance().toArray(User.class);
	    Log.i("TAG", "Cached List size for Users:" + cachedUsers.size());

	    List<Car> cachedCars = QacheeManager.getInstance().toArray(Car.class);
	    Log.i("TAG", "Cached List size for Cars:" + cachedCars.size());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

}
