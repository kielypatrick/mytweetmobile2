package app.myTweet.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.patrick.mytweet.R;

import java.util.UUID;

import static app.myTweet.helpers.FileIOHelper.writeBitmap;

public class MyTweetCameraActivity extends AppCompatActivity implements View.OnClickListener

{
  private static  final int     CAMERA_RESULT = 5;
  public static   final String EXTRA_PHOTO_FILENAME = "app.myTweet.photo.filename";

  private Button savePhoto;
  private Button takePhoto;
  private ImageView tweetImage;
  private Bitmap tweetPhoto;



  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.tweet_image);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    tweetImage  = (ImageView) findViewById(R.id.tweetImage);

    savePhoto       = (Button)findViewById(R.id.savePhoto);
    takePhoto       = (Button)findViewById(R.id.takePhoto);

    savePhoto.setEnabled(false);

    savePhoto.setOnClickListener(this);
    takePhoto.setOnClickListener(this);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    switch (item.getItemId())
    {
      case android.R.id.home  : onBackPressed();
        return true;
      default                 : return super.onOptionsItemSelected(item);
    }
  }

  @Override
  public void onClick(View v)
  {
    switch(v.getId())
    {
      case R.id.takePhoto     : onTakePhotoClicked(v);
        break;

      case R.id.savePhoto     : onPictureTaken(tweetPhoto);
        break;
    }
  }

  public void onTakePhotoClicked(View v)
  {
    // Check for presence of device camera. If not present advise user and quit method.
    PackageManager pm = getPackageManager();
    if (!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
      Toast.makeText(this, "Camera app not present on this device", Toast.LENGTH_SHORT).show();
      return;
    }
    // Use the device's owm camera app
    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
    startActivityForResult(cameraIntent,CAMERA_RESULT);
    savePhoto.setEnabled(true);
  }

  private void onPictureTaken(Bitmap data)
  {
    String filename = UUID.randomUUID().toString() + ".png";
    if(writeBitmap(this, filename, data) == true)
    {
      Intent intent = new Intent();
      intent.putExtra(EXTRA_PHOTO_FILENAME, filename);
      setResult(Activity.RESULT_OK, intent);
    }
    else
    {
      setResult(Activity.RESULT_CANCELED);
    }
    finish();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data)
  {
    super.onActivityResult(requestCode, resultCode, data);
    switch (requestCode)
    {
      case MyTweetCameraActivity.CAMERA_RESULT :
        if(data != null) {
          processImage(data);
        }
        else {
          Toast.makeText(this, "Camera failure: check simulated camera present emulator advanced settings",
                                Toast.LENGTH_LONG).show();
        }
        break;
    }
  }

  private void processImage(Intent data)
  {
    tweetPhoto = (Bitmap) data.getExtras().get("data");
    if(tweetPhoto == null)
    {
      Toast.makeText(this, "Attempt to take photo did not succeed", Toast.LENGTH_SHORT).show();
    }
    tweetImage.setImageBitmap(tweetPhoto);
  }
}