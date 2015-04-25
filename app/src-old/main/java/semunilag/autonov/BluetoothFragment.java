package semunilag.autonov;

import android.app.Activity;
import android.app.ListFragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import semunilag.autonov.R;
import semunilag.autonov.adapters.BlueToothListAdapter;


public class BluetoothFragment extends ListFragment {

    BluetoothAdapter mAdapter;
    BlueToothListAdapter mListAdapter = null;
    private final static String LOG_TAG = BluetoothFragment.class.getSimpleName();
    private final static int REQUEST_ENABLE_BT = 101;

    onBluetoothDeviceSelectedListener mListener;

    public BluetoothFragment(){

    }

    private ArrayList<BluetoothDevice> deviceArrayList = new ArrayList<BluetoothDevice>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAdapter.isDiscovering()){
            mAdapter.cancelDiscovery();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mAdapter.isDiscovering()){
            mAdapter.cancelDiscovery();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bluetooth, container, false);
        return rootView;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //super.onListItemClick(l, v, position, id);
        if (mAdapter.isDiscovering()){
            mAdapter.cancelDiscovery();
        }

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void startDiscovery(){
        mAdapter.startDiscovery();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (onBluetoothDeviceSelectedListener)activity;
            IntentFilter deviceFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            getActivity().registerReceiver(mReceiver, deviceFilter);

            mAdapter = BluetoothAdapter.getDefaultAdapter();
            if (mAdapter == null){
                Log.d(LOG_TAG, " Device does not support Bluetooth");
                Toast.makeText(getActivity(), "Device does not support Bluetooth", Toast.LENGTH_LONG).show();
            }else{
                if (!mAdapter.isEnabled()){
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                }else{
                    //TODO start discovery for devices here
                    startDiscovery();
                }
            }

            mListAdapter = new BlueToothListAdapter(getActivity(), deviceArrayList);
            setListAdapter(mListAdapter);
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString() + " must implement " + onBluetoothDeviceSelectedListener.class.getSimpleName());
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case REQUEST_ENABLE_BT:
                if (resultCode == Activity.RESULT_OK){
                    startDiscovery();
                }
                if (resultCode == Activity.RESULT_CANCELED){
                    //TODO close the application at this point
                    Toast.makeText(getActivity(), "Enable Bluetooh Failed", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Toast.makeText(getActivity(), "Device Found", Toast.LENGTH_SHORT).show();
                // If it's already paired, skip it, because it's been listed already
                mListAdapter.add(device);
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                Toast.makeText(getActivity(), "Discovery Complete", Toast.LENGTH_SHORT).show();
            }
        }
    };

    public interface onBluetoothDeviceSelectedListener{
        public void onBluetoothDeviceSelected(BluetoothDevice device);
    }
}
