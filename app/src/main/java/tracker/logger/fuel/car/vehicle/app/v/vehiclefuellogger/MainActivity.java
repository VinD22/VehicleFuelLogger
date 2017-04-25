package tracker.logger.fuel.car.vehicle.app.v.vehiclefuellogger;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;

import adapter.CarAdapter;
import io.realm.Realm;
import io.realm.RealmResults;
import model.Car;

/**
 * Main Activity
 * List of cars
 * Clicking on each cars goes to log of that car, where users can add new logs for each time they fill in petrol!
 */



public class MainActivity extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;

    Realm realm;

    ArrayList<Car> listOfCars = new ArrayList<>();

    private Toolbar mToolbar;
    FloatingActionButton mAddCar;

    private RecyclerView recList;
    private CarAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm = Realm.getDefaultInstance();

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        recList = (RecyclerView) findViewById(R.id.car_list_recyclerview);

        mAddCar = (FloatingActionButton) findViewById(R.id.fab_add_car);

        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setTitle(R.string.app_name);
            mToolbar.setTitle(R.string.app_name);
            mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
            // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "Main Activity");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "List of cars added!");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Home of Fuel Logger App");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        mAddCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddCar.class);
                startActivity(intent);
            }
        });

        final LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(layoutManager);
        recList.setHasFixedSize(true);

        recList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0)
                    mAddCar.hide();
                else if (dy < 0)
                    mAddCar.show();
            }
        });

    }

    public void getCarsList() {

        listOfCars.clear();
        RealmResults<Car> carResults =
                realm.where(Car.class).findAll();

        carResults = carResults.sort("name"); // Default Alphabetically Sorting!

        for (Car t : carResults) {
            final Car tempCar = new Car();
            tempCar.setId(t.getId());
            tempCar.setName(t.getName());
            tempCar.setAdditionalInformation(t.getAdditionalInformation());
            tempCar.setDistanceTravelled(t.getDistanceTravelled());
            listOfCars.add(tempCar);
        }

        mAdapter = new CarAdapter(MainActivity.this, listOfCars);
        recList.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        Log.i("totalCars", " " + listOfCars.size());
        // Toast.makeText(this, "Cars Size : " + listOfCars.size(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem myActionMenuItem = menu.findItem( R.id.action_search);
        final SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        EditText searchEditText = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.white));
        searchEditText.setHintTextColor(getResources().getColor(R.color.white));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                if (TextUtils.isEmpty(newText)) {
//                    adapter.filter("");
//                    listView.clearTextFilter();
//                } else {
//                    adapter.filter(newText);
//                }
                mAdapter.filter(newText + "");

                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
//            case R.id.search:
//
//                final Dialog dialog = new Dialog(MainActivity.this);
//                dialog.setContentView(R.layout.custom_product_search);
//
//                // set the custom dialog components - text, image and button
//                EditText mItemName = (EditText) dialog.findViewById(R.id.product_name);
//
//                mItemName.addTextChangedListener(new TextWatcher() {
//                    @Override
//                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                    }
//
//                    @Override
//                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                        mAdapter.filter(charSequence.toString() + "");
//
//
////                        private List<Thing> data;
////                        private List<Thing> dataCopy;
////
////                        public void filter(String text) {
////                            // Toast.makeText(mContext, "" + text + " /// " + dataCopy.size()  , Toast.LENGTH_SHORT).show();
////                            data.clear();
////                            if(text.isEmpty()){
////                                data.addAll(dataCopy);
////                            } else {
////                                data.clear();
////                                text = text.toLowerCase();
////                                for(Thing item: dataCopy){
////                                    if(item.getName().toLowerCase().contains(text)){
////                                        data.add(item);
////                                    }
////                                }
////                            }
////                            notifyDataSetChanged();
////                        }
//
//                    }
//
//                    @Override
//                    public void afterTextChanged(Editable editable) {
//
//
//
//                    }
//                });
//
//                dialog.setCancelable(true);
//                dialog.show();

//                return true;
            case R.id.settings:
                // Toast.makeText(this, "Show Settings!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public String capitalizeFirstLetter(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCarsList();
    }
    
}
