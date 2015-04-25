package semunilag.autonov.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.os.Handler;
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
    private BluetoothDevice mDevice;
    private BluetoothConnectThread mConnectThread;
    private BluetoothConnectedThread mConnectedThread;


    public BluetoothConnectFragment() {
        super();
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
        }
    }

    private static final Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case Constants.DEVICE_CONNECTED:
                    //TODO: start BluetoothConnect Thread
                    break;

                case Constants.DEVICE_SOCKET_OPENED:
                    //TODO: start BluetoothConnected Thread
                    break;
            }
        }
    };
}
