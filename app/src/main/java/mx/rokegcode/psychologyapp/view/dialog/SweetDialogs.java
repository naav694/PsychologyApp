package mx.rokegcode.psychologyapp.view.dialog;

import android.app.Activity;
import android.content.Context;

import cn.pedant.SweetAlert.SweetAlertDialog;
import mx.rokegcode.psychologyapp.R;

public abstract class SweetDialogs {

    public static SweetAlertDialog sweetLoading(Context context, String text) {
        SweetAlertDialog sweetAD = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        sweetAD.setTitleText(text);
        sweetAD.getProgressHelper().setBarColor(context.getColor(R.color.colorPrimary));
        sweetAD.setCanceledOnTouchOutside(false);
        sweetAD.setCancelable(false);
        return sweetAD;
    }

    public static SweetAlertDialog sweetError(Context context, String text) {
        SweetAlertDialog sweetAD = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
        sweetAD.setTitleText(text);
        sweetAD.setCanceledOnTouchOutside(false);
        sweetAD.setConfirmText("OK");
        sweetAD.setConfirmClickListener(SweetAlertDialog::dismissWithAnimation);
        return sweetAD;
    }

    public static SweetAlertDialog sweetWarning(Context context, String text) {
        SweetAlertDialog sweetAD = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
        sweetAD.setTitleText(text);
        sweetAD.setCanceledOnTouchOutside(false);
        sweetAD.setConfirmText("OK");
        sweetAD.setConfirmClickListener(sweetAlertDialog -> sweetAD.dismissWithAnimation());
        return sweetAD;
    }

    public static SweetAlertDialog sweetSuccess(Context context, String text) {
        SweetAlertDialog sweetAD = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
        sweetAD.setTitleText(text);
        sweetAD.setCanceledOnTouchOutside(false);
        sweetAD.setConfirmText("OK");
        sweetAD.setConfirmClickListener(sweetAlertDialog -> {
            sweetAD.dismiss();
        });
        return sweetAD;
    }

    public static SweetAlertDialog sweetSuccessCloseActivity(Context context, String text, Activity activity) {
        SweetAlertDialog sweetAD = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
        sweetAD.setTitleText(text);
        sweetAD.setCanceledOnTouchOutside(false);
        sweetAD.setConfirmText("OK");
        sweetAD.setConfirmClickListener(sweetAlertDialog -> {
            sweetAD.dismiss();
            activity.finish();
        });
        return sweetAD;
    }

}
