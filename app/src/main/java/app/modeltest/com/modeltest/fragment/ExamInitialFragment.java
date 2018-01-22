package app.modeltest.com.modeltest.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import app.modeltest.com.modeltest.R;
import app.modeltest.com.modeltest.activity.QuizActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExamInitialFragment extends Fragment implements View.OnClickListener{
    LinearLayout examStart;
    String topic_id = "";


    public ExamInitialFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exam_initial, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            topic_id = bundle.getString("topic_id", "");
        }
        examStart = (LinearLayout)view.findViewById(R.id.layoutStart);
        examStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layoutStart:
                //Toast.makeText(getActivity(), "clicked", Toast.LENGTH_LONG).show();
                gotoQuizActivity();
                break;
        }
    }

    private void gotoQuizActivity(){
        Intent intent = new Intent(getActivity(), QuizActivity.class);
        intent.putExtra("topic_id", topic_id);
        startActivity(intent);
    }
}
