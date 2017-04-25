package tracker.logger.fuel.car.vehicle.app.v.vehiclefuellogger;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.realm.Realm;
import model.Car;

/**
 * Created by apple on 24/04/17.
 */

public class AddCar extends AppCompatActivity {

    Realm realm;

    private Toolbar mToolbar;
    private Button mAddCar;
    private EditText mCarName, mCarDistanceTravelled, mCarAdditionalInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_car);

        realm = Realm.getDefaultInstance();

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mAddCar = (Button) findViewById(R.id.add_car);

        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            mToolbar.setTitle(R.string.add_car);
            getSupportActionBar().setTitle(R.string.add_car);
            // getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mCarName = (EditText) findViewById(R.id.car_name);
        mCarDistanceTravelled = (EditText) findViewById(R.id.car_distance_travelled);
        mCarAdditionalInfo = (EditText) findViewById(R.id.car_additional_info);

        mAddCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String carName = mCarName.getText().toString();
                String distanceTravelledInString = mCarDistanceTravelled.getText().toString();
                String additionalInformation = mCarAdditionalInfo.getText().toString();

                if(carName.isEmpty() || distanceTravelledInString.isEmpty()) {
                    Toast.makeText(AddCar.this, R.string.empty_fields, Toast.LENGTH_SHORT).show();
                } else {

                    double distanceTravelled = Double.parseDouble(distanceTravelledInString);

                    realm.beginTransaction();

                    Car newCar = realm.createObject(Car.class);
                    int nextKey = getNextKey();
                    newCar.setId(nextKey);
                    newCar.setName(carName);
                    newCar.setDistanceTravelled(distanceTravelled);
                    newCar.setAdditionalInformation(additionalInformation);

                    realm.commitTransaction();

                    Toast.makeText(AddCar.this, R.string.add_car, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddCar.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }


            }
        });



    }

    public String capitalizeFirstLetter(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }

    public int getNextKey()
    {
        try {
            return realm.where(Car.class).max("id").intValue() + 1;
        } catch (ArrayIndexOutOfBoundsException e)
        { return 0; }
    }

}
