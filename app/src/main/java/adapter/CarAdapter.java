package adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import model.Car;
import tracker.logger.fuel.car.vehicle.app.v.vehiclefuellogger.FuelLoggerActivity;
import tracker.logger.fuel.car.vehicle.app.v.vehiclefuellogger.R;

/**
 * Car Adapter class
 */

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.RecyclerViewHolder> {

    private static final String INVENTORY_UPDATE = "inventory_update";
    private List<Car> data;
    private List<Car> dataCopy;
    private Context mContext;
    Realm realm;

    public CarAdapter(Context context, ArrayList<Car> data) {
        this.mContext = context;
        this.data = data;
        dataCopy = new ArrayList<Car>();
        dataCopy.addAll(data);
        realm = Realm.getDefaultInstance();
        // setHasStableIds(true);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.car_list_item, parent, false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder viewHolder, final int position) {

        final Car tempCar = data.get(viewHolder.getAdapterPosition());
        viewHolder.mCarName.setText(capitalizeFirstLetter(tempCar.getName()));

        viewHolder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, FuelLoggerActivity.class);
                intent.putExtra("id", tempCar.getId());
                intent.putExtra("name", tempCar.getName());
                mContext.startActivity(intent);

            }
        });

    }


    public void filter(String text) {
        // Toast.makeText(mContext, "" + text + " /// " + dataCopy.size()  , Toast.LENGTH_SHORT).show();
        data.clear();
        if(text.isEmpty()){
            data.addAll(dataCopy);
        } else {
            data.clear();
            text = text.toLowerCase();
            for(Car item: dataCopy){
                if(item.getName().toLowerCase().contains(text)){
                    data.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    public String capitalizeFirstLetter(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout mLinearLayout;
        protected TextView mCarName;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            mLinearLayout = (LinearLayout) itemView.findViewById(R.id.lin);
            mCarName = (TextView) itemView.findViewById(R.id.name);
        }

    }


}
