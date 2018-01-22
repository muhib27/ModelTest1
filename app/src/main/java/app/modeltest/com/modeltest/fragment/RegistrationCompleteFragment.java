package app.modeltest.com.modeltest.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import app.modeltest.com.modeltest.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrationCompleteFragment extends Fragment implements View.OnClickListener{
    private Button createProfileBtn;
    private TextView resendTextBtn;
    private EditText verificationCode;
    private EditText setPasswoed;

    public RegistrationCompleteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration_complete, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        createProfileBtn = (Button)view.findViewById(R.id.createProfile);
        createProfileBtn.setOnClickListener(this);
        resendTextBtn = (TextView)view.findViewById(R.id.resend_code);
        String text = "<font color=#cc0029>Don\'t get code?</font> <font color=#239cdc>  Tap here to send again</font>";
        resendTextBtn.setText(Html.fromHtml(text));
        verificationCode = (EditText)view.findViewById(R.id.verificationCode);
        setPasswoed = (EditText)view.findViewById(R.id.password);
    }


    private void attemptLogin() {

        // Reset errors.
        verificationCode.setError(null);
        setPasswoed.setError(null);

        // Store values at the time of the login attempt.
        String Vcode = verificationCode.getText().toString();
        String password = setPasswoed.getText().toString();


        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
//        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
//            mPasswordView.setError(getString(R.string.error_invalid_password));
//            focusView = mPasswordView;
//            cancel = true;
//        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(Vcode)) {
            verificationCode.setError(getString(R.string.error_field_required));
            focusView = verificationCode;
            cancel = true;
        }
        else if (TextUtils.isEmpty(password)) {
            setPasswoed.setError(getString(R.string.error_field_required));
            focusView = setPasswoed;
            cancel = true;
        }
//        else if (!isEmailValid(email)) {
//            mEmailView.setError(getString(R.string.error_invalid_email));
//            focusView = mEmailView;
//            cancel = true;
//        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            //showProgress(true);
            //gotoRegistrationComplete();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.createProfile:
                attemptLogin();
                break;
        }
    }
}
