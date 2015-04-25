package semunilag.autonov.adapters;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import semunilag.autonov.R;

/**
 * Created by teliov on 4/18/15.
 */
public class BlueToothListAdapter extends ArrayAdapter <BluetoothDevice>{

    private Context mContext;
    private ArrayList<BluetoothDevice> mDevices;

    public BlueToothListAdapter(Context context, ArrayList<BluetoothDevice> devices){
        super(context, R.layout.bluetooth_list_item);
        this.mContext = context;
        this.mDevices = devices;
    }

    @Override
    public int getCount() {
        return mDevices.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        BluetoothDevice device = mDevices.get(position);
        BluetoothViewHolder mViewHolder = null;
        View rowView;

        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null){
            rowView = inflater.inflate(R.layout.bluetooth_list_item, parent, false);
            mViewHolder = new BluetoothViewHolder(rowView);
            rowView.setTag(mViewHolder);
        }else{
            rowView = convertView;
            mViewHolder = (BluetoothViewHolder)rowView.getTag();
        }

        mViewHolder.bImageView.setImageResource(R.drawable.ic_list_icon);
        mViewHolder.bNameTextView.setText(device.getName());
        mViewHolder.bMacIdTextView.setText(device.getAddress());

        return rowView;
    }

    @Override
    public void add(BluetoothDevice object) {
        mDevices.add(object);
        notifyDataSetChanged();
    }

    public static class BluetoothViewHolder{

        public final ImageView bImageView;
        public final TextView bNameTextView;
        public final TextView bMacIdTextView;


        public BluetoothViewHolder(View view){
            bImageView = (ImageView)view.findViewById(R.id.bluelist_img);
            bNameTextView = (TextView)view.findViewById(R.id.bluelist_device_name);
            bMacIdTextView = (TextView)view.findViewById(R.id.bluelist_mac_id);
        }
    }
}
