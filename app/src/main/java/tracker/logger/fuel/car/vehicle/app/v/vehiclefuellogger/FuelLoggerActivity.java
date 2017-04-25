package tracker.logger.fuel.car.vehicle.app.v.vehiclefuellogger;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import adapter.FuelLogAdapter;
import io.realm.Realm;
import io.realm.RealmResults;
import model.FuelLog;

public class FuelLoggerActivity extends AppCompatActivity {

    Realm realm;

    ArrayList<FuelLog> listOfFuelLogs = new ArrayList<>();

    private Toolbar mToolbar;
    FloatingActionButton mAddLog;

    private RecyclerView recList;
    private FuelLogAdapter mAdapter;

    int carId = -1;
    String carName = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fuel_logger_activity);

        // Initialize Realm
        realm = Realm.getDefaultInstance();

        // Get FuelLog ID from Intent!
        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            // Toast.makeText(this, "Error in intents! (Contact Developer)", Toast.LENGTH_SHORT).show();
        } else {
            carId = extras.getInt("id");
            carName = extras.getString("name", "");
        }

        // Link Widgets to UI
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mAddLog = (FloatingActionButton) findViewById(R.id.fab_add_log);

        recList = (RecyclerView) findViewById(R.id.fuel_log_list_recyclerview);

        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setTitle(carName);
            mToolbar.setTitle(carName);
            mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
            // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mAddLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FuelLoggerActivity.this, AddFuelLog.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("id", carId);
                intent.putExtra("name", carName);
                startActivity(intent);
            }
        });

        final LinearLayoutManager layoutManager = new LinearLayoutManager(FuelLoggerActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(layoutManager);
        recList.setHasFixedSize(true);

        recList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0)
                    mAddLog.hide();
                else if (dy < 0)
                    mAddLog.show();
            }
        });

        getFuelLog();

    }

    public void getFuelLog() {

        listOfFuelLogs.clear();
        RealmResults<FuelLog> fuelLogResults = realm.where(FuelLog.class).equalTo("carId", carId).findAll();

        for (FuelLog t : fuelLogResults) {
            final FuelLog tempFuelLog = new FuelLog();
            tempFuelLog.setId(t.getId());
            tempFuelLog.setCarId(t.getCarId());
            tempFuelLog.setAdditionalInformation(t.getAdditionalInformation());
            tempFuelLog.setOdometerValue(t.getOdometerValue());
            tempFuelLog.setDate(t.getDate());
            tempFuelLog.setFuelUnits(t.getFuelUnits());
            tempFuelLog.setFuelPrice(t.getFuelPrice());
            listOfFuelLogs.add(tempFuelLog);
        }

        mAdapter = new FuelLogAdapter(FuelLoggerActivity.this, listOfFuelLogs);
        recList.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        Log.i("totalFuelLogs", " " + listOfFuelLogs.size());
        // Toast.makeText(this, "FuelLogs Size : " + listOfFuelLogs.size(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(FuelLoggerActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }
}
