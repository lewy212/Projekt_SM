package com.example.projektsm;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.bumptech.glide.Glide;


public class EditJedzenieActivity extends AppCompatActivity {
    public static final String EXTRA_EDIT_JEDZENIE_NAZWA = "EDIT_JEDZENIE_NAZWA";
    public static final String EXTRA_EDIT_JEDZENIE_TLUSZCZE = "EDIT_JEDZENIE_TLUSZCZE";
    public static final String EXTRA_EDIT_JEDZENIE_BIALKA = "EDIT_JEDZENIE_BIALKA";
    public static final String EXTRA_EDIT_JEDZENIE_WEGLOWODANY = "EDIT_JEDZENIE_WEGLOWODANY";
    public static final String EXTRA_EDIT_JEDZENIE_KALORIE = "EDIT_JEDZENIE_KALORIE";
    public static final String EXTRA_EDIT_ZDJECIE_SCIEZKA = "EDIT_ZDJECIE_SCIEZKA";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private EditText editNazwaEditText;
    private EditText editTluszczeEditText;
    private EditText editBialkaEditText;
    private EditText editWeglowodanyEditText;
    private EditText editKalorieEditText;
    private String sciezka;
    private ImageView zdjecie_aparatu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sciezka="";
        setContentView(R.layout.activity_edit_jedzenie);
        zdjecie_aparatu = findViewById(R.id.aparatZdjecie);
        zdjecie_aparatu.setImageResource(R.drawable.ic_camera);
        editNazwaEditText = findViewById(R.id.edit_jedzenie_nazwa);
        editTluszczeEditText = findViewById(R.id.edit_jedzenie_tluszcze);
        editBialkaEditText = findViewById(R.id.edit_jedzenie_bialka);
        editWeglowodanyEditText = findViewById(R.id.edit_jedzenie_weglowodany);
        editKalorieEditText = findViewById(R.id.edit_jedzenie_kalorie);
        Intent starter = getIntent();
        if (starter.hasExtra(EXTRA_EDIT_JEDZENIE_NAZWA))
            editNazwaEditText.setText(starter.getStringExtra(EXTRA_EDIT_JEDZENIE_NAZWA));
        if (starter.hasExtra(EXTRA_EDIT_JEDZENIE_KALORIE))
            editKalorieEditText.setText(starter.getStringExtra(EXTRA_EDIT_JEDZENIE_KALORIE));
        if (starter.hasExtra(EXTRA_EDIT_JEDZENIE_TLUSZCZE))
            editTluszczeEditText.setText(starter.getStringExtra(EXTRA_EDIT_JEDZENIE_TLUSZCZE));
        if (starter.hasExtra(EXTRA_EDIT_JEDZENIE_WEGLOWODANY))
        {
            editWeglowodanyEditText.setText(starter.getStringExtra(EXTRA_EDIT_JEDZENIE_WEGLOWODANY));
            Log.d("wegle", starter.getStringExtra(EXTRA_EDIT_JEDZENIE_WEGLOWODANY));
        }

        if (starter.hasExtra(EXTRA_EDIT_JEDZENIE_BIALKA))
            editBialkaEditText.setText(starter.getStringExtra(EXTRA_EDIT_JEDZENIE_BIALKA));
        if (starter.hasExtra(EXTRA_EDIT_ZDJECIE_SCIEZKA))
        {
            if(!starter.getStringExtra(EXTRA_EDIT_ZDJECIE_SCIEZKA).equals(""))
                loadImageWithGlide(starter.getStringExtra(EXTRA_EDIT_ZDJECIE_SCIEZKA));
        }

        Button buttonTakePhoto = findViewById(R.id.button_take_photo);
        buttonTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                try{
//                    Intent intent = new Intent();
//                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivity(intent);
//                }
//                catch(Exception e){
//                    e.printStackTrace();
//                }
                dispatchTakePictureIntent();
            }
        });



        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(editNazwaEditText.getText())
                        || TextUtils.isEmpty(editBialkaEditText.getText())
                        || TextUtils.isEmpty(editTluszczeEditText.getText())
                        || TextUtils.isEmpty(editWeglowodanyEditText.getText()))
                    setResult(RESULT_CANCELED, replyIntent);
                else {
                    String nazwa = editNazwaEditText.getText().toString();
                    replyIntent.putExtra(EXTRA_EDIT_JEDZENIE_NAZWA, nazwa);

                    String kalorie = editKalorieEditText.getText().toString();
                    replyIntent.putExtra(EXTRA_EDIT_JEDZENIE_KALORIE, kalorie);

                    String tluszcze = editTluszczeEditText.getText().toString();
                    replyIntent.putExtra(EXTRA_EDIT_JEDZENIE_TLUSZCZE, tluszcze);

                    String bialka = editBialkaEditText.getText().toString();
                    replyIntent.putExtra(EXTRA_EDIT_JEDZENIE_BIALKA,bialka );

                    String weglowodany = editWeglowodanyEditText.getText().toString();
                    replyIntent.putExtra(EXTRA_EDIT_JEDZENIE_WEGLOWODANY, weglowodany);
                    replyIntent.putExtra(EXTRA_EDIT_ZDJECIE_SCIEZKA, sciezka);
                    Log.d("Sciezka",EXTRA_EDIT_ZDJECIE_SCIEZKA);
                    Log.d("Sciezka v2",sciezka);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent();
        takePictureIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        try{
            Intent intent = new Intent();
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
        catch(Exception e){
            e.printStackTrace();
        }
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//        } else {
//            Toast.makeText(this, "Nie można uruchomić aparatu", Toast.LENGTH_SHORT).show();
//            Log.e("Camera", "Aparat nie został uruchomiony, brak aktywności do obsługi");
//        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            zdjecie_aparatu.setImageBitmap(imageBitmap);

            // Zapisz zdjęcie do pliku
            sciezka = saveImageToFile(imageBitmap);


        }
    }

    // Metoda do zapisu zdjęcia do pliku
    private String saveImageToFile(Bitmap imageBitmap) {
        // Możesz użyć np. nazwy unikalnej opartej na dacie i czasie dla pliku
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + ".jpg";

        // Uzyskaj dostęp do katalogu, w którym chcesz zapisać zdjęcie
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        // Stwórz obiekt File dla nowego pliku
        File imageFile = new File(storageDir, imageFileName);

        // Zapisz bitmapę do pliku
        try (FileOutputStream fos = new FileOutputStream(imageFile)) {
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Zwróć pełną ścieżkę do zapisanego pliku
        return imageFile.getAbsolutePath();
    }
    private void loadImageWithGlide(String imagePath) {
        ImageView imageView = findViewById(R.id.aparatZdjecie);

        Glide.with(this)
                .load(new File(imagePath))
                .into(imageView);
    }

}
