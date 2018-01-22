package app.modeltest.com.modeltest.fragment;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import app.modeltest.com.modeltest.R;
import app.modeltest.com.modeltest.activity.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExamCategoryFragment extends Fragment implements View.OnClickListener{
    private LinearLayout modelTest, topics;


    public ExamCategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exam_category, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //setHasOptionsMenu(true);
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            MainActivity.toggle.setDrawerIndicatorEnabled(false);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        modelTest = (LinearLayout)view.findViewById(R.id.modelTest);
        topics = (LinearLayout)view.findViewById(R.id.layoutTopic);
        modelTest.setOnClickListener(this);
        topics.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.modelTest:
                gotoTopicListFragment();
                break;
            case R.id.layoutTopic:
                gotoTopicListFragment();
                break;
        }
    }
    private void gotoTopicListFragment() {
        TopicListFragment topicListFragment = new TopicListFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_acitivity_container, topicListFragment).addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                getFragmentManager().popBackStack();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
