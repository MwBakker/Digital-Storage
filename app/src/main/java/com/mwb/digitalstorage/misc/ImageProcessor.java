package com.mwb.digitalstorage.misc;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import androidx.core.content.FileProvider;

import static android.os.Environment.getExternalStorageDirectory;
import static android.os.Environment.getExternalStoragePublicDirectory;

public class ImageProcessor
{
    private boolean imgFromCamera;
    private String imgPath;

    public boolean isFromCamera() { return imgFromCamera; }
    public void setCamerabool(boolean isFromCamera) { imgFromCamera = isFromCamera; }
    public String getImgPath() { return imgPath; }


    //  creates the intent
    public Intent dispatchTakePictureIntent(Context context, File storageDir)
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null)
        {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile(storageDir);
            } catch (IOException ex)
            {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null)
            {
                Uri photoURI = FileProvider.getUriForFile(context,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            }
        }
        return takePictureIntent;
    }

    //  creates a file from taken image
    private File createImageFile(File storageDir) throws IOException
    {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        // Save a file: path for use with ACTION_VIEW intents
        imgPath = image.getAbsolutePath();
        return image;
    }

    //  returns result of browsing an image
    public String browseImage(Intent data, Application app)
    {
        if (data != null)
        {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = app.getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            imgPath = cursor.getString(columnIndex);
            cursor.close();
        }
        return imgPath;
    }

    //  decodes the imgPath to a file
    public Bitmap decodeImgPath()
    {
        if (imgPath != null)
        {
            File file = new File(imgPath);
            if (file.exists() && file.length() > 0) {
                return decode(imgPath);
            }
            else { return null; }
        } else { return null; }
    }

    //  decodes the imgPath to a file (overload)
    public Bitmap decodeImgPath(String path)
    {
        File file = new File(path);
        if (file.exists() && file.length() > 0)
        {
            return decode(path);
        } else { return null; }
    }

    // process the decoding
    private Bitmap decode(String path)
    {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inSampleSize = 2;
        Bitmap bitmap = BitmapFactory.decodeFile(path, bmOptions);
        return Bitmap.createScaledBitmap(bitmap, 800, 600, true);
    }
}