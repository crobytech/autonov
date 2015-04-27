package semunilag.autonov.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.os.Handler;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.logging.LogRecord;

import semunilag.autonov.Constants;
import semunilag.autonov.R;
import semunilag.autonov.threads.BluetoothConnectThread;
import semunilag.autonov.threads.BluetoothConnectedThread;

/**
 * Created by teliov on 1/18/00.
 */
public class BluetoothConnectFragment extends Fragment{

    private static String LOG_TAG = BluetoothConnectFragment.class.getSimpleName();
    private BluetoothDevice mDevice;
    private BluetoothConnectThread mConnectThread;

    public BluetoothConnectFragment() {

        setHasOptionsMenu(true);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bluetooth_connected, container, false);
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Bundle arguments = getArguments();

        if (arguments != null){
            mDevice = arguments.getParcelable(Constants.DEVICE_TO_CONNECT);
            mConnectThread = new BluetoothConnectThread(mDevice, mHandler);
            Toast.makeText(getActivity(), "Connecting to device", Toast.LENGTH_SHORT);
            mConnectThread.start();
        }else{
            //TODO: do something if this happens
            Log.e(LOG_TAG, "No arguments for this Fragment");
        }
    }

    private final Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case Constants.DEVICE_SOCKET_OPENED:
                    //TODO: start BluetoothConnect Thread
                    String deviceName = (String)msg.obj;
                    removeProgressBar(deviceName);
                    break;
            }
        }
    };

    private void  removeProgressBar(String deviceName){
        Toast.makeText(getActivity(), "Connected to " +  deviceName, Toast.LENGTH_SHORT).show();
        ProgressBar progressBar = (ProgressBar)getActivity().findViewById(R.id.connecting_progressbar);
        LinearLayout linearLayout = (LinearLayout)getActivity().findViewById(R.id.connected);
        progressBar.setVisibility(View.GONE);
        linearLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.bluetooth_connected_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case R.id.bluetooth_stop_menu:
                //End the connection
                if (mConnectThread != null){
                    mConnectThread.cancel();
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
