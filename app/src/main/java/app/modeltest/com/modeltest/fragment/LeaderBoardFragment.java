package app.modeltest.com.modeltest.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import app.modeltest.com.modeltest.Model.LeaderList;
import app.modeltest.com.modeltest.Model.Wrapper;
import app.modeltest.com.modeltest.R;
import app.modeltest.com.modeltest.activity.MainActivity;
import app.modeltest.com.modeltest.retrofit.ApiInterface;
import app.modeltest.com.modeltest.retrofit.RetrofitApiClient;
import app.modeltest.com.modeltest.utils.GsonParser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class LeaderBoardFragment extends Fragment {


    private RecyclerView recyclerView;
    private LeaderBoardAdapter adapter;
    private LinearLayoutManager layoutManager;
    private List<LeaderList> listItems;
    private CircularProgressView progressView;
    private TextView txtMessage;
    private ApiInterface apiInterface;

    public LeaderBoardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_leader_board, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            MainActivity.toggle.setDrawerIndicatorEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        listItems = new ArrayList<LeaderList>();
        listItems.clear();

        initView(view);
        initApiCall();
        initAction();
    }

    private void initView(View v){

        progressView = (CircularProgressView)v.findViewById(R.id.progressView);
        progressView.setVisibility(View.GONE);
        recyclerView = (RecyclerView)v.findViewById(R.id.recyclerView);
        recyclerView.setNestedScrollingEnabled(false);
        txtMessage = (TextView)v.findViewById(R.id.txtMessage);
    }

    private void initAction(){
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }


    private void initApiCall(){

        progressView.setVisibility(View.VISIBLE);
        progressView.startAnimation();
        listItems.clear();
        Map<String,String> para = new HashMap();
        //para.put("level_id", levelId);
//        QuestionApiCall questionApiCall = new QuestionApiCall();
//        questionApiCall.setLevel_id("39");
        apiInterface = RetrofitApiClient.getClient().create(ApiInterface.class);

        Call<JsonElement> call = apiInterface.getLeaderBoard(para);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                //ServerResponse serverResponse = response.body();
                Log.e("*** RESPONSE ***", "is: "+response.toString());
                if(progressView!=null)
                    progressView.setVisibility(View.GONE);
                //ModelBase mb = ModelBase.getInstance().setResponse(response);

                if(response.body()!=null){
                    Wrapper modelContainer = GsonParser.getInstance()
                            .parseServerResponse2(response.body());
                    if(modelContainer.getStatus().getCode()==200){

                        JsonArray jsonArray = modelContainer.getData().get("list").getAsJsonArray();
                        listItems = parseLeaderList(jsonArray.toString());
                        //populateData(currentPosition);
                        if(adapter == null){
                            adapter = new LeaderBoardAdapter(listItems);
                            recyclerView.setAdapter(adapter);
                        }
                        if(listItems.size() <= 0){
                            txtMessage.setVisibility(View.VISIBLE);
                        }
                        else{
                            txtMessage.setVisibility(View.GONE);
                        }
                        //
                        adapter.notifyDataSetChanged();
                        recyclerView.setAdapter(adapter);
                    }



                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });

//        MultiPartStringRequest mpr = new MultiPartStringRequest(Request.Method.GET, UrlHelper.newUrl(UrlHelper.URL_GET_LEADER_BOARD), new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//
//                Log.e("*** RESPONSE ****", "is: "+response);
//                ModelBase mb = ModelBase.getInstance().setResponse(response);
//                if(mb.getStatus().getCode() == 200){
//                    listItems.addAll(mb.getData().getLeaderList());
//                }
//
//                if(listItems.size()<=0){
//                    txtMessage.setVisibility(View.VISIBLE);
//                }
//                else{
//                    txtMessage.setVisibility(View.GONE);
//                }
//
//                if(adapter==null){
//                    adapter = new LeaderBoardAdapter(listItems);
//                    recyclerView.setAdapter(adapter);
//                }
//                adapter.notifyDataSetChanged();
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//
//        RequestQueue rq = Volley.newRequestQueue(LeaderBoardActivity.this, new MultiPartStack());
//        rq.add(mpr);
    }

    private List<LeaderList> parseLeaderList(String object) {

        List<LeaderList> tags = new ArrayList<LeaderList>();
        Type listType = new TypeToken<List<LeaderList>>() {
        }.getType();
        tags = (List<LeaderList>) new Gson().fromJson(object, listType);
        return tags;
    }

    private String getDurationBreakdown(long millis)
    {
        if(millis < 0)
        {
            throw new IllegalArgumentException("Duration must be greater than zero!");
        }

        long days = TimeUnit.MILLISECONDS.toDays(millis);
        millis -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

        StringBuilder sb = new StringBuilder(64);
        sb.append(days);
        sb.append(" Days ");
        sb.append(hours);
        sb.append(" Hours ");
        sb.append(minutes);
        sb.append(" Minutes ");
        sb.append(seconds);
        sb.append(" Seconds");

        String output = String.valueOf(hours)+":"+String.valueOf(minutes)+":"+String.valueOf(seconds);

        //return(sb.toString());
        return output;
    }

    public class LeaderBoardAdapter extends RecyclerView.Adapter<LeaderBoardAdapter.MyViewHolder> {

        private List<LeaderList> dataSet;
        //private ImageLoader mImageLoader;

        public LeaderBoardAdapter(List<LeaderList> data) {
            this.dataSet = data;
            //this.mImageLoader = CustomVolleyRequestQueue.getInstance(LeaderBoardActivity.this).getImageLoader();

        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_leaderboard_list, parent, false);

            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

            //NetworkImageView imgViewNetwork = holder.imgViewNetwork;
            TextView txtPosition = holder.txtPosition;
            TextView txtName = holder.txtName;
            TextView txtScore = holder.txtScore;
            TextView txtTime = holder.txtTime;
            CardView cardView = holder.cardView;

            /*mImageLoader.get("", ImageLoader.getImageListener(imgViewNetwork, R.drawable.avatar, R.drawable.avatar));
            imgViewNetwork.setImageUrl("", mImageLoader);*/

            int pos = listPosition;
            txtPosition.setText(String.valueOf(pos+1));
            txtName.setText(dataSet.get(listPosition).getName());
            txtScore.setText(dataSet.get(listPosition).getScore());

            Long time = Long.parseLong(dataSet.get(listPosition).getTime());
            txtTime.setText(getDurationBreakdown(time*1000));


            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });


        }

        @Override
        public int getItemCount() {
            return dataSet.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            //NetworkImageView imgViewNetwork;
            TextView txtPosition;
            TextView txtName;
            TextView txtScore;
            TextView txtTime;
            CardView cardView;


            public MyViewHolder(View itemView) {
                super(itemView);
                //this.imgViewNetwork = (NetworkImageView)itemView.findViewById(R.id.imgViewNetwork);
                this.txtPosition = (TextView) itemView.findViewById(R.id.txtPosition);
                this.txtName = (TextView)itemView.findViewById(R.id.txtName);
                this.txtScore = (TextView) itemView.findViewById(R.id.txtScore);
                this.txtTime = (TextView) itemView.findViewById(R.id.txtTime);
                this.cardView = (CardView)itemView.findViewById(R.id.cardView);
            }
        }

    }

}
