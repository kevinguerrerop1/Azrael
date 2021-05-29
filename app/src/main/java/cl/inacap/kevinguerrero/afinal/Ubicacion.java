package cl.inacap.kevinguerrero.afinal;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Ubicacion extends Fragment implements OnMapReadyCallback{

    private GoogleMap mMap;
    private Marker marcador;
    double lat=0;
    double lng=0;
    String mensaje="";
    String direccion="";
    Context ctx=getActivity();

    public Ubicacion() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) myContext.getSupportFragmentManager().findFragmentById(R.id.map);
        //       mapFragment.getMapAsync(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista= inflater.inflate(R.layout.fragment_ubicacion, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return vista;
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        miUbicacion();
    }

    private void localizacionStart(){
        LocationManager mlocManager=(LocationManager)this.ctx.getSystemService(Context.LOCATION_SERVICE);
        final boolean gpsEnabled=mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled){
            Intent settingsIntent=new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(settingsIntent);
        }
        /*if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,},1000);
            return;
        }*/

    }
    public void setLocation(android.location.Location loc){
        //Obtener direccion de la calle a traves de lat y lng
        if(loc.getLatitude()!=0.0 && loc.getLongitude()!=0.0){
            try{
                Geocoder geocoder=new Geocoder(getActivity(), Locale.getDefault());
                List<Address> list=geocoder.getFromLocation(loc.getLatitude(),loc.getLongitude(),1);
                if (!list.isEmpty()){
                    Address DirCalle= list.get(0);
                    direccion=(DirCalle.getAddressLine(0));
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    //Convertir xml a imagen para mostrar como marcador
    @NonNull
    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    //Agregar marcador en el Mapa
    public void AgregarMArcador(double lat,double lng){
        LatLng coordenadas=new LatLng(lat,lng);
        CameraUpdate MiUbicacion= CameraUpdateFactory.newLatLngZoom(coordenadas,16);
        if (marcador!=null) marcador.remove();
        marcador=mMap.addMarker(new MarkerOptions().position(coordenadas).title("Dirección: "+direccion).icon(bitmapDescriptorFromVector(getActivity(), R.drawable.ic_location_on_black_24dp)));
        mMap.animateCamera(MiUbicacion);
    }
    //Actualizar la ubicacion
    private void ActualizarUbicacion(android.location.Location location){
        if (location!=null){
            lat=location.getLatitude();
            lng=location.getLongitude();
            AgregarMArcador(lat,lng);
        }
    }

    LocationListener locListener=new LocationListener() {
        @Override
        public void onLocationChanged(android.location.Location location) {
            ActualizarUbicacion(location);
            setLocation(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {
            mensaje=("GPS Activado");
            Mensaje();
        }

        @Override
        public void onProviderDisabled(String provider) {
            mensaje=("GPS Desactivado");
            localizacionStart();
            Mensaje();
        }
    };

    //Obtener mi ubicacion
    private static int PETITION_PERMISO_LOCALIZACION=101;

    private void miUbicacion(){
        if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION},PETITION_PERMISO_LOCALIZACION);
            return;
        }else{
            LocationManager locationManager=(LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
            android.location.Location location=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            ActualizarUbicacion(location);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1200,0,locListener);
            //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,1000,0,locListener);


        }
    }
    public  void Mensaje(){
        Toast.makeText(ctx,mensaje, Toast.LENGTH_LONG).show();
    }
}
