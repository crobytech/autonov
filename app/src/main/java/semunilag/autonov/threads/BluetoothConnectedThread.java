package semunilag.autonov.threads;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.os.Handler;

/**
 * Created by teliov on 1/18/00.
 */

public class BluetoothConnectedThread extends Thread{

    private static String LOG_TAG = BluetoothConnectedThread.class.getSimpleName();
    private final BluetoothSocket mSocket;
    private InputStream mInputStream;
    private OutputStream mOutputStream;
    private final Handler mHandler;

    public BluetoothConnectedThread(BluetoothSocket socket, Handler handler){
        mHandler = handler;
        mSocket = socket;
    }

    public void run(){
        Log.i(LOG_TAG, "Beginning Connected Thread handler");

        InputStream tmpInputStream = null;
        OutputStream tmpOutputStream = null;

        try {
            tmpInputStream = mSocket.getInputStream();
            tmpOutputStream = mSocket.getOutputStream();
        }catch (IOException e){
            Log.e(LOG_TAG, "Error Obtaining Input/Outpitstream from Socket");
            Log.e(LOG_TAG, e.toString());
        }

        mInputStream = tmpInputStream;
        mOutputStream = tmpOutputStream;

        byte[] buffer = new byte[1024];
        int bytes;

        while(true){
            try{
                bytes = mInputStream.read(buffer);
            }catch (IOException e){
                Log.e(LOG_TAG, "unable to obtain data from device input stream");
                Log.e(LOG_TAG, e.toString());
                //TODO: Add Handler for Bluetooth Connection Lost
                break;
            }
        }
    }

    public void write(byte[] buffer){
        try {
            mOutputStream.write(buffer);
        }catch (IOException e){
            Log.e(LOG_TAG, "Unable to write to bluetooth device");
            Log.e(LOG_TAG, e.toString());
        }
    }

    public void cancel(){
        try{
            mSocket.close();
            mOutputStream.close();
            mInputStream.close();
        }catch (IOException e){
            Log.e(LOG_TAG, "Error Closing Device Socket/Inputstream/Outputstream");
        }
    }
}