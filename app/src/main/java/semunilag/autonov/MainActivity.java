package semunilag.autonov;

import android.app.FragmentTransaction;
import android.bluetooth.BluetoothDevice;
import android.support.v7.app.ActionBarActivity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import semunilag.autonov.fragments.BluetoothDiscoverFragment;
import semunilag.autonov.fragments.BluetoothConnectFragment;


public class MainActivity extends ActionBarActivity implements BluetoothDiscoverFragment.onBluetoothDeviceSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            BluetoothDiscoverFragment bluetoothFragment = new BluetoothDiscoverFragment();
            getFragmentManager().beginTransaction()
                    .add(R.id.container1, bluetoothFragment)
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBluetoothDeviceSelected(BluetoothDevice device) {
        /*
            Once a device has been picked switch fragments to the one that will handle connection from here onwards
         */
        Bundle mBundle = new Bundle();
        mBundle.putParcelable(Constants.DEVICE_TO_CONNECT, device);

        BluetoothConnectFragment bluetoothConnectFragment = new BluetoothConnectFragment();
        bluetoothConnectFragment.setArguments(mBundle);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container1, bluetoothConnectFragment);
        transaction.commit();
    }
}
