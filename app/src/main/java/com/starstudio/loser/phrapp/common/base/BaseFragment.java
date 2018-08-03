package com.starstudio.loser.phrapp.common.base;

/*
    create by:loser
    date:2018/7/31 14:34
*/

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;
import com.starstudio.loser.phrapp.common.view.PHRProgressDialog;
import com.trello.rxlifecycle2.components.support.RxFragment;
import java.util.Objects;
import es.dmoral.toasty.Toasty;

import static android.support.constraint.Constraints.TAG;

public class BaseFragment<E extends BaseEventListener> extends RxFragment implements BaseView<E>{
    private E mListener;
    private PHRProgressDialog mDialog;

    public Fragment getFragment(){
        return this;
    }

    @Override
    public void setEventListener(E eventListener) {
        this.mListener = eventListener;
    }

    public E getListener() {
        return mListener;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void showProgressDialog() {
        if (mDialog == null) {
            mDialog = new PHRProgressDialog(Objects.requireNonNull(getFragment().getActivity()));
            mDialog.showProgressDialog();
            Log.d(TAG, "showProgressDialog: show show show show show show show hahahahahaha  " + mDialog.isShowing());
        }
        mDialog.showProgressDialog();
    }


    @Override
    public void dismissProgressDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void showErrorToast(String message) {
        Toasty.error(Objects.requireNonNull(getActivity()), message, Toast.LENGTH_SHORT, true).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void showWarningToast(String message) {
        Toasty.warning(Objects.requireNonNull(getActivity()), message, Toast.LENGTH_SHORT, true).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void showSuccessToast(String message) {
        Toasty.success(Objects.requireNonNull(getActivity()), message, Toast.LENGTH_SHORT, true).show();
    }
}
