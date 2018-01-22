package app.modeltest.com.modeltest.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import app.modeltest.com.modeltest.R;
import app.modeltest.com.modeltest.fragment.LoginFragment;
import app.modeltest.com.modeltest.fragment.RegistrationFragment;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    RegistrationFragment registrationFragment;
    // UI references.
    private EditText mobileNo;
    private EditText mPasswordView;
    private View mProgressView;
    private TextView notRegistered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        LoginFragment loginFragment = new LoginFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, loginFragment);
        transaction.commit();

//        mobileNo = (EditText) findViewById(R.id.mobileNo);
//
//        mPasswordView = (EditText) findViewById(R.id.password);
//        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
//                if (id == R.id.login || id == EditorInfo.IME_NULL) {
//                    attemptLogin();
//                    return true;
//                }
//                return false;
//            }
//        });
//        Button mSignInButton = (Button) findViewById(R.id.email_sign_in_button);
//        mSignInButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                attemptLogin();
//            }
//        });
//        notRegistered = (TextView)findViewById(R.id.not_registered);
//        String text = "<font color=#cc0029>Not Registered Yet?</font> <font color=#239cdc> Register Now</font>";
//        notRegistered.setText(Html.fromHtml(text));

    }

    private void attemptLogin() {
//        if (mAuthTask != null) {
//            return;
//        }

        // Reset errors.
        mobileNo.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String mobile_no = mobileNo.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
//        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
//            mPasswordView.setError(getString(R.string.error_invalid_password));
//            focusView = mPasswordView;
//            cancel = true;
//        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(mobile_no)) {
            mobileNo.setError(getString(R.string.error_field_required));
            focusView = mobileNo;
            cancel = true;
        }
        else if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
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
        }
    }
    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount()>0) {
//            registrationFragment = (RegistrationFragment) getFragmentManager().findFragmentByTag(AppConstants.REG_FRAG_TAG);
//            if (registrationFragment != null && registrationFragment.isVisible()) {
                getFragmentManager().popBackStack();
//            }
        }
        else {
            finish();
        }

        //super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if(getFragmentManager().getBackStackEntryCount()>0)
                    getFragmentManager().popBackStack();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case android.R.id.home:
//                if(getFragmentManager().getBackStackEntryCount()>0)
//                getFragmentManager().popBackStack();
//                break;
        }
    }
}
