package chu.kevin.googlemapstest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends Activity implements OnMapClickListener{
  static final LatLng USC_TC = new LatLng(10.35410, 123.91145);
  static final LatLng USC_MAIN = new LatLng(10.30046, 123.88822);
  static final LatLng MY_PLACE = new LatLng(10.0, 123.0);
  
  private GoogleMap map;
  private int locationIndicator = 0;
  private int locationMax = 3;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    if(GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext())==ConnectionResult.SUCCESS)
    {
    	map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
    	        .getMap();
    	    if(map!=null)
    	    {
    	    	Marker usc_tc = map.addMarker(new MarkerOptions().position(USC_TC).title("USC TC"));
    	    	Marker usc_main = map.addMarker(new MarkerOptions().position(USC_MAIN).title("USC MAIN"));
    	    	Marker my_place = map.addMarker(new MarkerOptions().position(MY_PLACE).title("MY PLACE"));

    	    	map.setOnMapClickListener(this);

    	    	map.animateCamera(CameraUpdateFactory.newLatLngZoom(USC_TC, 15), 2000, null);
    	    	
    	    }
    }
    
    
    
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.activity_main, menu);
    return true;
  }

@Override
public void onMapClick(LatLng point) {
	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
	    @Override
	    public void onClick(DialogInterface dialog, int which) {
	        switch (which){
	        case DialogInterface.BUTTON_POSITIVE:
	            switch(locationIndicator)
	            {
	            case 0:	map.animateCamera(CameraUpdateFactory.newLatLngZoom(USC_TC, 15), 2000, null); break;
	            case 1:	map.animateCamera(CameraUpdateFactory.newLatLngZoom(USC_MAIN, 15), 2000, null); break;
	            case 2:	map.animateCamera(CameraUpdateFactory.newLatLngZoom(MY_PLACE, 15), 2000, null); break;
	            }
	            locationIndicator = (locationIndicator + 1) % locationMax;
	            break;

	        case DialogInterface.BUTTON_NEGATIVE:
	            //No button clicked
	            break;
	        }
	    }
	};

	AlertDialog.Builder builder = new AlertDialog.Builder(this);
	builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
	    .setNegativeButton("No", dialogClickListener).show();
	
}

} 
