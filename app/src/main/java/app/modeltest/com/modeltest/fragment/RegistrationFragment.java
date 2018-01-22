package app.modeltest.com.modeltest.fragment;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import app.modeltest.com.modeltest.R;
import app.modeltest.com.modeltest.utils.AppConstants;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrationFragment extends Fragment implements View.OnClickListener{
    private Button submitBtn;
    private EditText fullName;
    private EditText mobileNo;
    private EditText email;

    public RegistrationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        submitBtn = (Button)view.findViewById(R.id.submitBtn);
        fullName = (EditText)view.findViewById(R.id.name);
        mobileNo = (EditText)view.findViewById(R.id.mobileNo);
        email = (EditText)view.findViewById(R.id.email);
        InputMethodManager imgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        fullName.requestFocus();

        //getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        submitBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submitBtn:
                attemptLogin();
                break;
            case android.R.id.home:
                getFragmentManager().popBackStack();
                break;
        }
    }
    private void gotoRegistrationComplete(){
        RegistrationCompleteFragment registrationCompleteFragment = new RegistrationCompleteFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, registrationCompleteFragment, AppConstants.REG_COM_FRAG_TAG).addToBackStack(null);
        transaction.commit();
    }

    private void attemptLogin() {

        // Reset errors.
        mobileNo.setError(null);
        fullName.setError(null);

        // Store values at the time of the login attempt.
        String mobile_no = mobileNo.getText().toString();
        String name = fullName.getText().toString();
        String emailAddress = email.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
//        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
//            mPasswordView.setError(getString(R.string.error_invalid_password));
//            focusView = mPasswordView;
//            cancel = true;
//        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(name)) {
            fullName.setError(getString(R.string.error_field_required));
            focusView = fullName;
            cancel = true;
        }
        else if (TextUtils.isEmpty(mobile_no)) {
            mobileNo.setError(getString(R.string.error_field_required));
            focusView = mobileNo;
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
            gotoRegistrationComplete();
        }
    }
}
