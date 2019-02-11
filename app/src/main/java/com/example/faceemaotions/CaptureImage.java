package com.example.faceemaotions;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CaptureImage extends AppCompatActivity {
    String mCurrentPhotoPath;
    ImageView image;

    Intent intent ;
Context context;
    CaptureImage( ImageView image,Context context){
        image=this.image;
        intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        this.context=context;
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            File photoFile=null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(context,
                        "com.example.android.fileprovider",
                        photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(intent, 1);
            }


        };
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap =(Bitmap)data.getExtras().get("data");
        if(bitmap!=null)
        {

            image.setImageBitmap(sendImage(bitmap));
            Uploader uploader = new Uploader(bitmap,"http://192.168.1.101");
        }
        else
        {
            Toast.makeText(this, "No image Tacken", Toast.LENGTH_SHORT).show();
        }

    }

    protected Bitmap sendImage(Bitmap bitmap)
    {
        return  bitmap;

    }
    //Create A file image to save the taken Image in to get better Quality
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File storageDir = new File("/storage/0000-208A/", "Neger");
        System.out.println(storageDir.getAbsolutePath());
        storageDir.mkdir();
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();

        System.out.println(image.getAbsolutePath());
        if(image.exists())
        {
            System.out.println("yay\n\r");
        }

        return image;
    }
}