package cl.inacap.kevinguerrero.afinal;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


public class Camara2 extends AppCompatActivity {



    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;

    Button Btncapturar;
    ImageView Vprevia;

    Uri imagen_uri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camara2);


        Vprevia = findViewById(R.id.Imagen);
        Btncapturar = findViewById(R.id.btnfoto);

        Btncapturar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) ==
                            PackageManager.PERMISSION_DENIED ||
                            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                                    PackageManager.PERMISSION_DENIED) {
                        String[] Permiso = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(Permiso, PERMISSION_CODE);
                    } else {
                        openCamera();
                    }
                } else {
                    openCamera();
                }
            }
        });
    }

    private void openCamera() {

        ContentValues valor = new ContentValues();
        valor.put(MediaStore.Images.Media.TITLE, "Imagen nueva");
        valor.put(MediaStore.Images.Media.DESCRIPTION, "Desde la Camara");
        imagen_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, valor);

        Intent Camaras = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Camaras.putExtra(MediaStore.EXTRA_OUTPUT, imagen_uri);
        startActivityForResult(Camaras, IMAGE_CAPTURE_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    Toast.makeText(this, "Permiso denegado", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Vprevia.setImageURI(imagen_uri);
        }
    }
}


