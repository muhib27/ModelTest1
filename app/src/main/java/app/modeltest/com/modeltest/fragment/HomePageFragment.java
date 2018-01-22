package app.modeltest.com.modeltest.fragment;


import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import app.modeltest.com.modeltest.R;
import app.modeltest.com.modeltest.activity.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomePageFragment extends Fragment {

    private static final int REQUST_TOPIC_ACTIVITY = 10;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    private List<String> listData;
    private GridLayoutManager layoutManager;
    private boolean isPermissionGranted = false;
    private ImageView imgMainLogo;

    private ScrollView scrollView;
    //private NavigationView navigationView;
    private boolean isSoundOff = false;

    public HomePageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.content_home_page_grid, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
//            MainActivity.toggle.setDrawerIndicatorEnabled(false);
//            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//        }
        listData = new ArrayList<String>();
        listData.add("hhh");
        listData.add("hhh");
        listData.add("hhh");
        listData.add("hhh");
        listData.add("hhh");
        listData.add("hhh");
        initView(view);
        initAction();
    }

    private void initView(View view){
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        imgMainLogo = (ImageView)view.findViewById(R.id.imgMainLogo);

        scrollView = (ScrollView)view.findViewById(R.id.scrollView);
        //MainActivity.toggle.setDrawerIndicatorEnabled(true);

    }
    private void initAction(){
        layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CustomAdapter(listData);
        recyclerView.setAdapter(adapter);

        scrollView.fullScroll(ScrollView.FOCUS_UP);
    }

    private void gotoCategoryFragment() {
        ExamCategoryFragment examCategoryFragment = new ExamCategoryFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_acitivity_container, examCategoryFragment).addToBackStack(null);
        transaction.commit();
    }

    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

        private List<String> dataSet;
        private int lastPosition = -1;

        private AnimatorSet mSetRightOut;
        private AnimatorSet mSetLeftIn;
        private AnimatorSet setRightOut;
        private AnimatorSet setLeftIn;
        private boolean isBackVisible = false;

        public CustomAdapter(List<String> data) {
            this.dataSet = data;
            this.setRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.flight_right_out);
            this.setLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.flight_left_in);
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {

            MyViewHolder myViewHolder = null;
//            if(viewType==0){
//                View view = LayoutInflater.from(parent.getContext())
//                        .inflate(R.layout.row_homepage_grid_daily_doze, parent, false);
//
//                myViewHolder = new MyViewHolder(view);
//
//            }
//            else{
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.row_homepage_grid, parent, false);

                myViewHolder = new MyViewHolder(view);
            //}
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

            TextView txtTitle = holder.txtTitle;
            LinearLayout layoutTop = holder.layoutTop;
            CardView cardView = holder.cardView;

            txtTitle.setText(dataSet.get(listPosition));

            switch (listPosition){
                case 0:
                    layoutTop.setBackgroundResource(R.drawable.grid_sample);
                    break;
                case 1:
                    layoutTop.setBackgroundResource(R.drawable.grid_sample);
                    break;
                case 2:
                    layoutTop.setBackgroundResource(R.drawable.grid_sample);
                    break;
                case 3:
                    layoutTop.setBackgroundResource(R.drawable.grid_sample);
                    break;
            }


            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Handler h = new Handler();
                    final View v = view;
                    v.setEnabled(false);
                    gotoCategoryFragment();

                    //quiz
//                    if(listPosition == 0){
//
//                        if(ApplicationSingleton.getInstance().isNetworkConnected() == true) {
//                            //Intent intent = new Intent(HomePageGridActivity.this, DailyDozeActivity.class);
//
//                            h.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Intent intent = new Intent(HomePageGridActivity.this, PlayListItemsActivity.class);
//                                    intent.putExtra(AppConstants.KEY_FUNNY_VIDEOS, AppConstants.PALYLIST_ID_ROCKING_EXP);
//                                    intent.putExtra(AppConstants.PLAY_LIST_NAME, getString(R.string.title_activity_play_list));
//                                    startActivity(intent);
//                                }
//                            }, 500);
//
//
//                        }
//                        else {
//                            Toast.makeText(HomePageGridActivity.this, R.string.toast_no_internet, Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//                    //episodes
//                    else if(listPosition == 1){
//                        if(ApplicationSingleton.getInstance().isNetworkConnected() == true) {
//
//                            h.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Intent intent = new Intent(HomePageGridActivity.this, PlayListItemsActivity.class);
//                                    intent.putExtra(AppConstants.KEY_FUNNY_VIDEOS, AppConstants.PALYLIST_ID_FUNNY_VIDEOS);
//                                    intent.putExtra(AppConstants.PLAY_LIST_NAME, getString(R.string.home_page_grid_funny_videos));
//                                    startActivity(intent);
//                                    v.setEnabled(true);
//                                }
//                            }, 500);
//
//
//                        }
//                        else {
//                            Toast.makeText(HomePageGridActivity.this, R.string.toast_no_internet, Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    //daily doze
//                    else if(listPosition == 2){
//
//                        if(ApplicationSingleton.getInstance().isNetworkConnected() == true){
//
//                            h.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Intent intent = new Intent(HomePageGridActivity.this, TopicRootActivity.class);
//                                    startActivityForResult(intent, REQUST_TOPIC_ACTIVITY);
//                                    v.setEnabled(true);
//                                }
//                            }, 500);
//
//                        }
//                        else {
//                            Toast.makeText(HomePageGridActivity.this, R.string.toast_no_internet, Toast.LENGTH_SHORT).show();
//                        }
//
//
//
//                    }
//                    //meet the anchors
//                    else if(listPosition == 3){
//
//                        if(ApplicationSingleton.getInstance().isNetworkConnected() == true) {
//                            h.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Intent intent = new Intent(HomePageGridActivity.this, DailyDozeNewActivity.class);
//                                    startActivity(intent);
//                                    v.setEnabled(true);
//                                }
//                            }, 500);
//                        }
//                        else {
//                            Toast.makeText(HomePageGridActivity.this, R.string.toast_no_internet, Toast.LENGTH_SHORT).show();
//                        }
//
//
//                    }

                    showFlipAnimation(view);

                }
            });

            //setAnimation(holder.cardView, listPosition);
        }

        @Override
        public int getItemCount() {
            return dataSet.size();
        }

        private void setAnimation(View viewToAnimate, int position) {
            if (position > lastPosition) {
                Animation animation = AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_in_left);
                viewToAnimate.startAnimation(animation);
                lastPosition = position;
            }
        }

        private void showFlipAnimation(View view){
            if(!isBackVisible){
                setRightOut.setTarget(view);
                setLeftIn.setTarget(view);
                setRightOut.start();
                setLeftIn.start();
                isBackVisible = true;
            }
            else{
                setRightOut.setTarget(view);
                setLeftIn.setTarget(view);
                setRightOut.start();
                setLeftIn.start();
                isBackVisible = false;
            }
            view.setEnabled(true);
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView txtTitle;
            LinearLayout layoutTop;
            CardView cardView;


            public MyViewHolder(View itemView) {
                super(itemView);
                this.txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
                this.layoutTop = (LinearLayout)itemView.findViewById(R.id.layoutTop);
                this.cardView = (CardView)itemView.findViewById(R.id.cardView);

            }
        }
    }

}
