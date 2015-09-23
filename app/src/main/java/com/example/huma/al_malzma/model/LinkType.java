package com.example.huma.al_malzma.model;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.huma.al_malzma.R;
import com.example.huma.al_malzma.helper.Utility;
import com.example.huma.al_malzma.parse.ParseConstants;
import com.rey.material.widget.EditText;


public class LinkType extends DataItem {
    public static final String TAG = LinkType.class.getSimpleName();


    private static EditText mLinkEditText;
    private static EditText mLinkDescriptionEditText;

    private static boolean linkFlag, descriptionFlag;

    public LinkType() { /*Default constructor required by parse */ }

    public LinkType(@ParseConstants.FragmentSource String fragmentSource) {
        super(fragmentSource, ParseConstants.KEY_TYPE_LINK);
    }


    // TODO: 9/9/2015 it's very long method and will be use a lot so try to make dialogFragment and but them in.
    public static void showLinkDialog(final Context context, @ParseConstants.FragmentSource final String fragmentSource) {
        MaterialDialog dialog = new MaterialDialog.Builder(context)
                .title(R.string.important_link_dialog_title)
                .customView(R.layout.add_link_dialog, true)
                .positiveText(R.string.add)
                .negativeText(android.R.string.cancel)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        //save link and description to parse.
                        LinkType link = new LinkType(fragmentSource);
                        link.setLink(mLinkEditText.getText().toString());
                        link.setDescription(mLinkDescriptionEditText.getText().toString());
                        link.saveToParse(context);
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                    }
                }).build();

        final View positiveAction = dialog.getActionButton(DialogAction.POSITIVE);
        mLinkEditText = (EditText) dialog.findViewById(R.id.link_edit_text);
        mLinkDescriptionEditText = (EditText) dialog.findViewById(R.id.link_description_edit_text);

        //it's only faction is to enable and disable the input Button.
        mLinkEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //check the link.
                if (Utility.validateLink(mLinkEditText.getText().toString()) == null) {
                    linkFlag = false;
                    mLinkEditText.setError(context.getString(R.string.wrong_link_error_message));
                } else {
                    linkFlag = true;
                    mLinkEditText.setError(null);
                }

                //check if the link is correct and there is description.
                if (linkFlag && descriptionFlag) positiveAction.setEnabled(true);
                else positiveAction.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

        });

        mLinkDescriptionEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //check if the text field is Empty or not.
                descriptionFlag = (s.toString().trim().length() > 0);
                //check if the link is correct and there is description.
                if (linkFlag && descriptionFlag) positiveAction.setEnabled(true);
                else positiveAction.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        dialog.show();
        positiveAction.setEnabled(false); // disabled by default
    }


    @Override
    public void saveToParse(Context context) {
        saveInBackgroundWithAlertDialog(context);
    }


    public String getDescription() {
        return getString(ParseConstants.KEY_LINK_DESCRIPTION);
    }

    public void setDescription(String description) {
        put(ParseConstants.KEY_LINK_DESCRIPTION, description);
    }

    public String getLink() {
        return getString(ParseConstants.KEY_LINK);
    }

    public void setLink(String link) {
        link = Utility.validateLink(link);
        put(ParseConstants.KEY_LINK, link);
    }
}
