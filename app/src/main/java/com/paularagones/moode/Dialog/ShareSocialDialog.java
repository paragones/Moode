package com.paularagones.moode.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.paularagones.moode.R;

/**
 * Created by Paul.Aragones on 2/17/2016.
 */
public class ShareSocialDialog extends DialogFragment {


    public static ShareSocialDialog newInstance() {
        ShareSocialDialog helpDialog = new ShareSocialDialog();
        return helpDialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_share_social, null);

        builder.setView(view);
        return builder.create();
    }

}
