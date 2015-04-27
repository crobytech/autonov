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
    private BluetoothSocket mSocket;
    private final BluetoothDevice mDevice;
    private static final UUID H606_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private BluetoothConnectedThread mConnectedThread;


    public BluetoothConnectThread(BluetoothDevice device, Handler handler){
        BluetoothSocket tmpSocket = null;
        mDevice = device;
        mHandler = handler;

        /*try{
            tmpSocket = mDevice.createRfcommSocketToServiceRecord(H606_UUID);
        }catch (IOException e){
            Log.e(LOG_TAG, "Unable to obtain bluetooth socket");
            Log.e(LOG_TAG, e.toString());
        }
        mSocket = tmpSocket;*/
    }

    public void run(){
        Log.i(LOG_TAG, "Attempting to Connect to Device");

        try{
            mSocket = mDevice.createInsecureRfcommSocketToServiceRecord(H606_UUID);
            mSocket.connect();
            //send connected message to the UI
            String deviceName = mDevice.getName();
            mHandler.obtainMessage(Constants.DEVICE_SOCKET_OPENED, deviceName).sendToTarget();

            mConnectedThread = new BluetoothConnectedThread(mSocket, mHandler);
            mConnectedThread.run();

        }catch (IOException e){
            Log.e(LOG_TAG, "Unable to connect to device");
            Log.e(LOG_TAG, e.toString());
        }
        return;
    }

    public void cancel(){
        if (mConnectedThread != null){
            mConnectedThread.cancel();
        }
        try {
            mSocket.close();
        }catch (IOException e){
            Log.e(LOG_TAG, "Unable to close Bluetooth Socket");
            Log.e(LOG_TAG, e.toString());
        }
    }
}
