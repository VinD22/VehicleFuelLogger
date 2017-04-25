package tracker.logger.fuel.car.vehicle.app.v.vehiclefuellogger;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import model.FuelLog;

/**
 * Adding Fuel Log
 */

public class AddFuelLog extends AppCompatActivity {

    Realm realm;

    private Toolbar mToolbar;
    private Button mAddFuelLog;
    private static Button mSelectDate;
    private EditText mFuelUnits, mFuelPrice, mAdditionalInformation, mOdometerValue;

    int carId = -1;
    String carName = "";

    static Date date;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_fuel_log);

        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            Toast.makeText(this, "Error in intents! (Contact Developer)", Toast.LENGTH_SHORT).show();
        } else {
            carId = extras.getInt("id");
            carName = extras.getString("name", "");
        }

        realm = Realm.getDefaultInstance();
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mAddFuelLog = (Button) findViewById(R.id.add_fuel_log);
        mSelectDate = (Button) findViewById(R.id.select_date);

        mFuelUnits = (EditText) findViewById(R.id.enter_fuel_units);
        mFuelPrice = (EditText) findViewById(R.id.enter_fuel_price);
        mAdditionalInformation = (EditText) findViewById(R.id.additional_info);
        mOdometerValue = (EditText) findViewById(R.id.odometer_value);

        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            mToolbar.setTitle(R.string.add_fuel_log);
            getSupportActionBar().setTitle(R.string.add_fuel_log);
            // getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogFragment dialogfragment = new DatePickerDialogTheme1();

                dialogfragment.show(getFragmentManager(), "Theme 1");

            }
        });

        mAddFuelLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fuelUnits = mFuelUnits.getText().toString();
                String fuelPrice = mFuelPrice.getText().toString();
                String additionalInformation = mAdditionalInformation.getText().toString();
                String odometerValue = mOdometerValue.getText().toString();

                if(fuelUnits.isEmpty() || fuelPrice.isEmpty() || odometerValue.isEmpty()) {
                    Toast.makeText(AddFuelLog.this, R.string.empty_fields, Toast.LENGTH_SHORT).show();
                } else if(date == null) {
                    Toast.makeText(AddFuelLog.this, R.string.select_date, Toast.LENGTH_SHORT).show();
                } else {

                    double fuelUnitsInDouble = Double.parseDouble(fuelUnits);
                    double fuelPriceInDouble = Double.parseDouble(fuelPrice);
                    double odometerValueInDouble = Double.parseDouble(odometerValue);

                    realm.beginTransaction();

                    FuelLog newLog = realm.createObject(FuelLog.class);
                    int nextKey = getNextKey();
                    newLog.setId(nextKey);
                    newLog.setFuelUnits(fuelUnitsInDouble);
                    newLog.setFuelPrice(fuelPriceInDouble);
                    newLog.setAdditionalInformation(additionalInformation);
                    newLog.setDate(date);
                    newLog.setOdometerValue(odometerValueInDouble);
                    newLog.setCarId(carId);

                    realm.commitTransaction();

//                    Toast.makeText(AddFuelLog.this, "Log Added!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddFuelLog.this, FuelLoggerActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("id", carId);
                    intent.putExtra("name", carName);
                    startActivity(intent);
                    finish();

                }
            }
        });

    }

    public static class DatePickerDialogTheme1 extends DialogFragment implements DatePickerDialog.OnDateSetListener{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datepickerdialog = new DatePickerDialog(getActivity(),
                    AlertDialog.THEME_DEVICE_DEFAULT_DARK,this,year,month,day);

//            DatePickerDialog datepickerdialog = new DatePickerDialog(getActivity(),
//                    AlertDialog.THEME_DEVICE_DEFAULT_LIGHT,this,year,month,day);

            return datepickerdialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day){

            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            try {
                int correctMonth = month+1;
                date = fmt.parse(year + "-" + correctMonth + "-" +day);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            mSelectDate.setText(day + " - " + (month+1) + " - " + year);

        }
    }

    public int getNextKey()
    {
        try {
            return realm.where(FuelLog.class).max("id").intValue() + 1;
        } catch (ArrayIndexOutOfBoundsException e)
        { return 0; }
    }

}
