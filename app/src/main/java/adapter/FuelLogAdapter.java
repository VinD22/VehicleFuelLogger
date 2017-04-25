package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import model.FuelLog;
import tracker.logger.fuel.car.vehicle.app.v.vehiclefuellogger.R;

/**
 * Adapter to show logs of fuel added in a list.
 */

public class FuelLogAdapter extends RecyclerView.Adapter<FuelLogAdapter.RecyclerViewHolder> {

    private static final String INVENTORY_UPDATE = "inventory_update";
    private List<FuelLog> data;
    private Context mContext;
    Realm realm;

    String fuelUnits, fuelPrice, odometer, dateString;

    public FuelLogAdapter(Context context, ArrayList<FuelLog> data) {
        this.mContext = context;
        this.data = data;

        fuelUnits = mContext.getString(R.string.fuel_units);
        fuelPrice = mContext.getString(R.string.fuel_price);
        odometer = mContext.getString(R.string.odometer_value);
        dateString = mContext.getString(R.string.date);

        realm = Realm.getDefaultInstance();
        // setHasStableIds(true);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.fuel_log_list_item, parent, false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder viewHolder, final int position) {
        final FuelLog tempFuelLog = data.get(viewHolder.getAdapterPosition());

        String formattedDate = convertDateToString(tempFuelLog.getDate(), "yyyy-MM-dd");

        viewHolder.mFuelUnits.setText(fuelUnits + " - " + tempFuelLog.getFuelUnits());
        viewHolder.mFuelPrice.setText(fuelPrice + " - " + tempFuelLog.getFuelPrice());
        viewHolder.mOdometerValue.setText(odometer + " - "  + tempFuelLog.getOdometerValue());
        viewHolder.mDate.setText(dateString + " - " + formattedDate);

    }

    public String convertDateToString(Date date, String format) {
        String dateStr = null;
        DateFormat df = new SimpleDateFormat(format);
        try {
            dateStr = df.format(date);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return dateStr;
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout mLinearLayout;
        protected TextView mFuelUnits, mFuelPrice, mOdometerValue, mDate;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            mLinearLayout = (LinearLayout) itemView.findViewById(R.id.lin);
            mFuelUnits = (TextView) itemView.findViewById(R.id.fuel_units);
            mFuelPrice = (TextView) itemView.findViewById(R.id.fuel_price);
            mOdometerValue = (TextView) itemView.findViewById(R.id.odometer_value);
            mDate = (TextView) itemView.findViewById(R.id.date);
        }

    }


}

