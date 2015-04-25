package semunilag.autonov.threads;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import android.os.Handler;

import semunilag.autonov.Constants;

/**
 * Created by teliov on 1/18/00.
 */
public class BluetoothConnectThread extends Thread{
    private static String LOG_TAG = BluetoothConnectThread.class.getSimpleName();

    private final Handler mHandler;
    private final BluetoothSocket mSocket;
    private final BluetoothDevice mDevice;
    private static final UUID H606_UUID = UUID.fromString("");


    public BluetoothConnectThread(BluetoothDevice device, Handler handler){
        BluetoothSocket tmpSocket = null;
        mDevice = device;
        mHandler = handler;

        try{
            tmpSocket = mDevice.createRfcommSocketToServiceRecord(H606_UUID);
        }catch (IOException e){
            Log.e(LOG_TAG, "Unable to obtain bluetooth socket");
            Log.e(LOG_TAG, e.toString());
        }
        mSocket = tmpSocket;
    }

    public void run(){
        Log.i(LOG_TAG, "Attempting to Connect to Device");

        try{
            mSocket.connect();
            //send connected message to the UI
            Message msg = mHandler.obtainMessage(Constants.DEVICE_SOCKET_OPENED, mSocket);
        }catch (IOException e){
            Log.e(LOG_TAG, "Unable to connect to device");
            Log.e(LOG_TAG, e.toString());
        }
        return;
    }

    public void cancel(){
        try {
            mSocket.close();
        }catch (IOException e){
            Log.e(LOG_TAG, "Unable to close Bluetooth Socket");
            Log.e(LOG_TAG, e.toString());
        }
    }

    public void connected(){
    }

}
