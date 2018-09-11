package bms.workmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import bms.workmanager.workers.ChainedOneTimeWorker1;
import bms.workmanager.workers.ChainedOneTimeWorker2;
import bms.workmanager.workers.ChainedOneTimeWorker3;
import bms.workmanager.workers.OneTimeRequestWorker;
import bms.workmanager.workers.PeriodicWorkRequestWorker;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

  public static final String TAG = "WorkManager";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    findViewById(R.id.btnOneTimeRequestWorker).setOnClickListener(
        v -> fnInvokeOneTimeRequestWorker());

    findViewById(R.id.btnPeriodicRequestWorker).setOnClickListener(
        v -> fnInvokePeriodicRequestWorker());

    findViewById(R.id.btnChainedOneTimeRequestWorker).setOnClickListener
        (v -> fnInvokeChainedOneTimeWorker());
  }


  private void fnInvokeOneTimeRequestWorker() {
    Log.d(TAG, "fnInvokeOneTimeRequestWorker: Invoked");

    //building up constraints for work request
    Constraints constraints = new Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .setRequiresBatteryNotLow(false)
        .setRequiresCharging(false)
        .build();

    //building up request, adding tag (can be used to get status and cancel work) and adding
    //constraints
    OneTimeWorkRequest oneTimeWorkRequest =
        new OneTimeWorkRequest.Builder(OneTimeRequestWorker.class)
            .setConstraints(constraints)
            .addTag("OneTimeRequestWorker")
            .build();

    //getting instance and enqueue it
    WorkManager.getInstance().enqueue(oneTimeWorkRequest);
  }

  private void fnInvokeChainedOneTimeWorker() {
    Log.d(TAG, "fnInvokeChainedOneTimeWorker: Invoked");

    //building up constraints for work request
    Constraints constraints = new Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .setRequiresBatteryNotLow(false)
        .setRequiresCharging(false)
        .build();

    //building up request 1, adding tag (can be used to get status and cancel work) and adding
    //constraints
    OneTimeWorkRequest chainedOneTimeWorkRequest1 =
        new OneTimeWorkRequest.Builder(ChainedOneTimeWorker1.class)
            .setConstraints(constraints)
            .addTag("OneTimeRequestWorker1")
            .build();

    //building up request 2, adding tag (can be used to get status and cancel work) and adding
    //constraints
    OneTimeWorkRequest chainedOneTimeWorkRequest2 =
        new OneTimeWorkRequest.Builder(ChainedOneTimeWorker2.class)
            .setConstraints(constraints)
            .addTag("OneTimeRequestWorker2")
            .build();

    //building up request 3, adding tag (can be used to get status and cancel work) and adding
    //constraints
    OneTimeWorkRequest chainedOneTimeWorkRequest3 =
        new OneTimeWorkRequest.Builder(ChainedOneTimeWorker3.class)
            .setConstraints(constraints)
            .addTag("OneTimeRequestWorker3")
            .build();

    //getting instance and chaining all 3 work request built above
    WorkManager.getInstance()
        .beginWith(chainedOneTimeWorkRequest1)
        .then(chainedOneTimeWorkRequest2)
        .then(chainedOneTimeWorkRequest3)
        .enqueue();

  }

  private void fnInvokePeriodicRequestWorker() {
    Log.d(TAG, "fnInvokePeriodicRequestWorker: Invoked");

    //building up constraints for work request
    Constraints constraints = new Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .setRequiresBatteryNotLow(false)
        .setRequiresCharging(false)
        .build();

    //builder for periodic work request
    PeriodicWorkRequest.Builder periodicWorkRequestBuilder =
        new PeriodicWorkRequest.Builder(PeriodicWorkRequestWorker.class, 15,
            TimeUnit.MINUTES);

    //building up request 1, adding tag (can be used to get status and cancel work) and adding
    //constraints
    PeriodicWorkRequest periodicWorkRequest = periodicWorkRequestBuilder
        .setConstraints(constraints)
        .addTag("PeriodicWorkRequest")
        .build();

    //Then enqueue the recurring task
    WorkManager.getInstance().enqueue(periodicWorkRequest);
  }
}
