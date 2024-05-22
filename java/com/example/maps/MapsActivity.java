package com.example.maps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.libraries.places.api.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.maps.databinding.ActivityMapsBinding;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private final int FINE_PERMISSION_CODE = 1;
    Location currentLocation;
    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient fusedLocationProviderClient;
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private SeekBar radiusSlider;
    private boolean isFirstSliderChange = false;
    private TextView textStreetName;
    private SharedPreferences prefs;
    private static final String PREFS_NAME = "AppPrefs";
    private static final String TOKEN_DATE_KEY = "token_date";
    private static final String TOKEN_USED_KEY = "token_used";
    private String randomUlica;
    private String randomZnamenitost;
    private String randomObicanZadatak;
    private String randomZanimljivZadatak;
    private String kvart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //checkLocationPermission();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        checkTokenAndResetIfNeeded();

        textStreetName = findViewById(R.id.ulicaText);

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        fusedLocationProviderClient = (FusedLocationProviderClient) LocationServices.getFusedLocationProviderClient(this);

        radiusSlider = findViewById(R.id.slider);
        radiusSlider.setMax(10000);
        radiusSlider.setProgress(100);
        radiusSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mMap != null && fromUser) {
                    Button generisiLokaciju = findViewById(R.id.generisiLokaciju);
                    generisiLokaciju.setOnClickListener(v -> updateMapWithChosenLocation(progress));
                    //generisiLokaciju.setOnClickListener(v -> updateMapWithRandomLocation(progress));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        supportMapFragment.getMapAsync(this);

        //checklist:
        CheckBox checkBox = findViewById(R.id.checkBox);
        checkBox.setVisibility(View.INVISIBLE);
        CheckBox checkBox2 = findViewById(R.id.checkBox2);
        checkBox2.setVisibility(View.INVISIBLE);
        CheckBox checkBox3 = findViewById(R.id.checkBox3);
        checkBox3.setVisibility(View.INVISIBLE);
        CheckBox checkBox4 = findViewById(R.id.checkBox4);
        checkBox4.setVisibility(View.INVISIBLE);
        CheckBox checkBox5 = findViewById(R.id.checkBox5);
        checkBox5.setVisibility(View.INVISIBLE);

        Button hideButton = findViewById(R.id.hideButton);
        hideButton.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();
        if (intent != null) {
            randomUlica = intent.getStringExtra("random_street");
            randomZnamenitost = intent.getStringExtra("random_landmark");
            randomObicanZadatak = intent.getStringExtra("random_obican");
            randomZanimljivZadatak = intent.getStringExtra("random_zanimljiv");
            kvart = intent.getStringExtra("kvart");
        }

        if (randomUlica != null && randomZnamenitost != null && randomObicanZadatak != null && randomZanimljivZadatak != null) {
            hideButton.setVisibility(View.VISIBLE);

            checkBox.setVisibility(View.VISIBLE);
            checkBox.setText("Poseti ulicu: " + randomUlica);

            checkBox2.setVisibility(View.VISIBLE);
            checkBox2.setText("Poseti znamenitost: " + randomZnamenitost);

            checkBox3.setVisibility(View.VISIBLE);
            checkBox3.setText(randomObicanZadatak);

            checkBox4.setVisibility(View.VISIBLE);
            checkBox4.setText(randomZanimljivZadatak);

            checkBox5.setVisibility(View.VISIBLE);
            checkBox5.setText("Poseti nasumično generisanu lokaciju");
        }

        hideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.getVisibility() == View.VISIBLE) {
                    hideButton.setText("Otkrij");

                    checkBox.setVisibility(View.INVISIBLE);
                    checkBox2.setVisibility(View.INVISIBLE);
                    checkBox3.setVisibility(View.INVISIBLE);
                    checkBox4.setVisibility(View.INVISIBLE);
                    checkBox5.setVisibility(View.INVISIBLE);
                } else {
                    hideButton.setText("Sakrij");

                    checkBox.setVisibility(View.VISIBLE);
                    checkBox2.setVisibility(View.VISIBLE);
                    checkBox3.setVisibility(View.VISIBLE);
                    checkBox4.setVisibility(View.VISIBLE);
                    checkBox5.setVisibility(View.VISIBLE);
                }
            }
        });

        Button checkButton = findViewById(R.id.checkButton);
        checkButton.setOnClickListener(v -> checkAndSubmit(kvart));

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getCurrentLocation();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        if (item.getItemId() == R.id.itemOne) {
            //Intent intent1 = new Intent(this, KvartoviView.class);
            return true;
        } else if (item.getItemId() == R.id.itemTwo) {
            Intent intent2 = new Intent(this, KvartoviActivity.class);
            startActivity(intent2);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    if (location != null) {
                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        //mMap.addMarker(new MarkerOptions().position(latLng).title("Moja lokacija"));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                        updateMapWithRandomLocation(radiusSlider.getProgress());
                    } else {
                        Toast.makeText(this, "Greska prilikom dobavljanja trenutne lokacije!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateMapWithChosenLocation(int radius) {
        if (!isFirstSliderChange) {
            isFirstSliderChange = true;
            return;
        }
        LatLng chosenLocation = new LatLng(45.26411, 19.83036);
        LatLng randomLocation = generateRandomLocation(chosenLocation, radius);
        mMap.clear();
        if (randomLocation != null) {
            mMap.addMarker(new MarkerOptions().position(randomLocation).title("Odabrana lokacija"));
            fetchStreetName(randomLocation);
        }

    }

    private void updateMapWithRandomLocation(int radius) {
        if (!isFirstSliderChange) {
            isFirstSliderChange = true;
            return;
        }
        LatLng currentUserLocation = mMap.getCameraPosition().target;
        LatLng randomLocation = generateRandomLocation(currentUserLocation, radius);

        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(randomLocation).title("Nasumicna lokacija"));

//        fetchImages(randomLocation.latitude, randomLocation.longitude);
    }

    private LatLng generateRandomLocation(LatLng center, double radius) {
        if (!prefs.getBoolean(TOKEN_USED_KEY, true)) {
            double radiusInDegrees = radius / 111000f;
            double randomAngle = Math.toRadians(new Random().nextDouble() * 360);
            double newLatitude = center.latitude + (radiusInDegrees * Math.sin(randomAngle));
            double newLongitude = center.longitude + (radiusInDegrees * Math.cos(randomAngle));
            return new LatLng(newLatitude, newLongitude);
        } else {
            Toast.makeText(this, "Nemate dostupnih tokena! Pokusajte ponovo sutra.", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    private void fetchStreetName(LatLng location) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                String streetName = address.getThoroughfare();
                String streetNumber = address.getSubThoroughfare();

                String fullAddress = (streetName != null ? streetName : "Nepoznata") + (streetNumber != null ? " " + streetNumber : "");
                textStreetName.setText("Ulica: " + fullAddress);
                if (!fullAddress.contains("Nepoznata")) {
                    useToken();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            textStreetName.setText("Nije moguce dobaviti naziv ulice!");
        }
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_PERMISSION_CODE);
        } else {
            supportMapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == FINE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                supportMapFragment.getMapAsync(this);
            } else {
                Toast.makeText(this, "Dozvola za pristup lokaciji je odbijena!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void checkTokenAndResetIfNeeded() {
        String lastUsedDate = prefs.getString(TOKEN_DATE_KEY, "");
        boolean tokenUsed = prefs.getBoolean(TOKEN_USED_KEY, false);
        String todayDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        if (!lastUsedDate.equals(todayDate)) {
            prefs.edit().putString(TOKEN_DATE_KEY, todayDate).apply();
            prefs.edit().putBoolean(TOKEN_USED_KEY, false).apply();
            tokenUsed = false;
        }

        updateTokenUI(!tokenUsed);
    }

    private void updateTokenUI(boolean tokenAvailable) {
        TextView numberText = findViewById(R.id.editTextNumber);
        if (tokenAvailable) {
            numberText.setText("1");
            numberText.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
        } else {
            numberText.setText("0");
            numberText.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        }
    }

    private void useToken() {
        //Promeniti ovaj line ispod na true da aplikacija radi kako treba
        prefs.edit().putBoolean(TOKEN_USED_KEY, false).apply();
        updateTokenUI(false);
    }

    private void checkAndSubmit(String kvart) {

        CheckBox checkBox1 = findViewById(R.id.checkBox);
        CheckBox checkBox2 = findViewById(R.id.checkBox2);
        CheckBox checkBox3 = findViewById(R.id.checkBox3);
        CheckBox checkBox4 = findViewById(R.id.checkBox4);
        CheckBox checkBox5 = findViewById(R.id.checkBox5);

        if (checkBox1.isChecked() && checkBox2.isChecked() && checkBox3.isChecked() && checkBox4.isChecked() && checkBox5.isChecked()) {
            SharedPreferences prefs = getSharedPreferences("DistrictPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean(kvart, true);
            editor.apply();

            Intent intent = new Intent(this, KvartoviActivity.class);
            intent.putExtra("resen_kvart", kvart);
            startActivity(intent);
        }
    }

//    private void loadPhotoForLocation(LatLng location) {
//        PlacesClient placesClient = Places.createClient(this);
//
//        List<Place.Field> fields = Collections.singletonList(Place.Field.PHOTO_METADATAS);
//        FetchPlaceRequest request = FetchPlaceRequest.builder(location.toString(), fields).build();
//
//        placesClient.fetchPlace(request).addOnSuccessListener((response) -> {
//            Place place = response.getPlace();
//            if (place != null && place.getPhotoMetadatas() != null && !place.getPhotoMetadatas().isEmpty()) {
//                PhotoMetadata photoMetadata = place.getPhotoMetadatas().get(0);
//                FetchPhotoRequest photoRequest = FetchPhotoRequest.builder(photoMetadata).build();
//                placesClient.fetchPhoto(photoRequest).addOnSuccessListener((fetchPhotoResponse -> {
//                    Bitmap bitmap = fetchPhotoResponse.getBitmap();
//                    if (bitmap != null) {
//                        ImageView mapsImage = (ImageView) findViewById(R.id.mapsImage);
//                        mapsImage.setImageBitmap(bitmap);
//                    }
//                }));
//            }
//        });
//    }
//
//    public void fetchImages(double lat, double lng) {
//        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
//        Call<ImageResponse> call = apiService.getGeotaggedImages(lat, lng);
//        call.enqueue(new Callback<ImageResponse>() {
//            @Override
//            public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
//                List<Image> images = (List<Image>) response.body();
//                if (images != null && !images.isEmpty()) {
//                    displayImage(images.get(0));
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ImageResponse> call, Throwable t) {
//
//            }
//        });
//    }
//
//    private void displayImage(Image image) {
//        ImageView imageView = findViewById(R.id.mapsImage);
//        if (image != null && image.getUrl() != null && !image.getUrl().isEmpty()) {
//            Picasso.get().load(image.getUrl()).into(imageView);
//        } else {
//            // Handle the case where image is null or URL is empty
//            Toast.makeText(this, "No image to display", Toast.LENGTH_SHORT).show();
//        }
//    }

//
//    private void getCurrentLocation() {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        Task<Location> task = fusedLocationProviderClient.getLastLocation();
//        task.addOnSuccessListener(new OnSuccessListener<Location>() {
//            @Override
//            public void onSuccess(Location location) {
//                supportMapFragment.getMapAsync(new OnMapReadyCallback() {
//                    @Override
//                    public void onMapReady(@NonNull GoogleMap googleMap) {
//                        if (location != null) {
//                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//                            System.out.println(latLng.toString());
//                            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("MyMap");
//                            googleMap.addMarker(markerOptions);
//                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 1));
//                        } else {
//                            Toast.makeText(MapsActivity.this, "Molim vas aktivirajte prava za lokaciju!", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//            }
//        });
//
//    }

}
