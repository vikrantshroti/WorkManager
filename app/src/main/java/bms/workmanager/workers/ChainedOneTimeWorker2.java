package bms.workmanager.workers;

import android.support.annotation.NonNull;
import android.util.Log;
import androidx.work.Worker;

public class ChainedOneTimeWorker2 extends Worker{

  private static final String TAG = "ChainedOneTimeWorker2";

  @NonNull
  @Override
  public Result doWork() {
    if (1 == 1) {
      //dummy condition to evaluate success
      Log.d(TAG, "doWork: success");
      return Result.SUCCESS;
    } else {
      //dummy condition to evaluate failure
      Log.d(TAG, "doWork: failure");
    }

    return Result.FAILURE;
  }
}
