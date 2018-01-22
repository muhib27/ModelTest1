package app.modeltest.com.modeltest.fragment;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import app.modeltest.com.modeltest.Model.Topic;
import app.modeltest.com.modeltest.Model.Wrapper;
import app.modeltest.com.modeltest.MyApplication;
import app.modeltest.com.modeltest.R;
import app.modeltest.com.modeltest.retrofit.RetrofitWithTime;
import app.modeltest.com.modeltest.utils.GsonParser;
import app.modeltest.com.modeltest.realm.RealmTopic;
import app.modeltest.com.modeltest.retrofit.ApiInterface;
import app.modeltest.com.modeltest.retrofit.RetrofitApiClient;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopicListFragment extends Fragment {
    private ApiInterface apiInterface;

    private TopicAdapter adapter;
    private List<Topic> listTopic;
    private boolean isNew = true;

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private CircularProgressView progressView;
    private TextView txtMessage;



    public TopicListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.content_topic_root, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listTopic = new ArrayList<Topic>();
//        apiInterface = RetrofitApiClient.getClient().create(ApiInterface.class);
        apiInterface = RetrofitWithTime.createService(ApiInterface.class);
        initView(view);
        callApi();
    }
    private void initView(View view){
        //getListData();
        progressView = (CircularProgressView)view.findViewById(R.id.progressView);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        txtMessage = (TextView)view.findViewById(R.id.txtMessage);

    }

    private void callApi() {
        HashMap<String, String> params = new HashMap<>();
        progressView.setVisibility(View.VISIBLE);
        progressView.startAnimation();
        listTopic.clear();

        Call<JsonElement> call = apiInterface.getTopics(params);
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

                        JsonArray jsonArray = modelContainer.getData().get("topics").getAsJsonArray();
                        listTopic = parseTopicList(jsonArray.toString());
                        if(adapter == null){
//                            singleAdapter = new SingleAdapter(listTopic);
//                            recyclerView.setAdapter(singleAdapter);
                            adapter = new TopicAdapter(listTopic);
                            recyclerView.setAdapter(adapter);
                        }
                        if(listTopic.size() <= 0){
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
    }

    private List<Topic> parseTopicList(String object) {

        List<Topic> tags = new ArrayList<Topic>();
        Type listType = new TypeToken<List<Topic>>() {
        }.getType();
        tags = (List<Topic>) new Gson().fromJson(object, listType);
        return tags;
    }

    private void gotoExamInitialFragment(String id){
        Bundle bundle = new Bundle();
        bundle.putString("topic_id", id);
        ExamInitialFragment examInitialFragment = new ExamInitialFragment();
        examInitialFragment.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_acitivity_container, examInitialFragment).addToBackStack(null);
        transaction.commit();
    }

    public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.MyViewHolder> implements Filterable {

        private List<Topic> dataSet;
        private TopicAdapter.UserFilter userFilter;

        public TopicAdapter(List<Topic> data) {
            this.dataSet = data;
        }

        public void refresh(){
            notifyDataSetChanged();
        }


        @Override
        public TopicAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                         int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_topic_layout2, parent, false);

            TopicAdapter.MyViewHolder myViewHolder = new TopicAdapter.MyViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(final TopicAdapter.MyViewHolder holder, final int listPosition) {

            TextView txtTopicTitle = holder.txtTopicTitle;
            TextView txtTopicDetails = holder.txtTopicDetails;
            CardView cardView = holder.cardView;
            LinearLayout layoutDescriptionHolder = holder.layoutDescriptionHolder;
            LinearLayout layoutTopProceedHolder = holder.layoutTopProceedHolder;
            ImageView imgNew = holder.imgNew;

            layoutDescriptionHolder.setVisibility(View.GONE);
            layoutTopProceedHolder.setVisibility(View.VISIBLE);

//            if(currentLanguage.equals(AppConstants.LANG_EN)){
//                txtTopicTitle.setText(dataSet.get(listPosition).getEnName());
//            }else{
            txtTopicTitle.setText(dataSet.get(listPosition).getName());
//            }

            txtTopicDetails.setText(dataSet.get(listPosition).getDetails());


            Realm realm = MyApplication.getInstance().getRealm();
            realm.beginTransaction();

            RealmTopic realmTopic = null;
            realmTopic = realm.where(RealmTopic.class).equalTo("id", dataSet.get(listPosition).getId()).findFirst();
            if(realmTopic == null){
                realmTopic = realm.createObject(RealmTopic.class);
                realmTopic.setId(dataSet.get(listPosition).getId());
                realmTopic.setNew(true);
            }

            if(realmTopic.isNew()){
                imgNew.setVisibility(View.VISIBLE);
            }else{
                imgNew.setVisibility(View.INVISIBLE);
            }

            realm.commitTransaction();
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gotoExamInitialFragment(dataSet.get(listPosition).getId());
                }
            });

//            cardView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(TopicRootActivity.this, LevelRootActivity.class);
//                    intent.putExtra(AppConstants.QUIZ_TOPIC_ID, dataSet.get(listPosition).getId());
//                    if(currentLanguage.equals(AppConstants.LANG_EN)){
//                        intent.putExtra(AppConstants.QUIZ_LEVEL_NAME, dataSet.get(listPosition).getEnName());
//                    }else{
//                        intent.putExtra(AppConstants.QUIZ_LEVEL_NAME, dataSet.get(listPosition).getName());
//                    }
//
//                    startActivityForResult(intent, REQUEST_FROM_QUIZ_PAGE);
//                }
//            });


        }

        @Override
        public int getItemCount() {
            return dataSet.size();
        }

        @Override
        public Filter getFilter() {
            if(userFilter == null)
                userFilter = new TopicAdapter.UserFilter(this, dataSet);
            return userFilter;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView txtTopicTitle;
            TextView txtTopicDetails;
            CardView cardView;
            LinearLayout layoutDescriptionHolder;
            LinearLayout layoutTopProceedHolder;
            ImageView imgNew;


            public MyViewHolder(View itemView) {
                super(itemView);
                this.txtTopicTitle = (TextView) itemView.findViewById(R.id.txtTopicTitle);
                this.txtTopicDetails = (TextView)itemView.findViewById(R.id.txtTopicDetails);
                this.cardView = (CardView)itemView.findViewById(R.id.cardView);
                this.layoutDescriptionHolder = (LinearLayout)itemView.findViewById(R.id.layoutDescriptionHolder);
                this.layoutTopProceedHolder = (LinearLayout)itemView.findViewById(R.id.layoutTopProceedHolder);
                this.imgNew = (ImageView)itemView.findViewById(R.id.imgNew);
            }
        }

        private  class UserFilter extends Filter {

            private final TopicAdapter adapter;

            private final List<Topic> originalList;

            private final List<Topic> filteredList;

            private UserFilter(TopicAdapter adapter, List<Topic> originalList) {
                super();
                this.adapter = adapter;
                this.originalList = new LinkedList<>(originalList);
                this.filteredList = new ArrayList<>();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                filteredList.clear();
                final FilterResults results = new FilterResults();

                if (constraint.length() == 0) {
                    filteredList.addAll(originalList);
                } else {
                    final String filterPattern = constraint.toString().toLowerCase().trim();

                    for (final Topic user : originalList) {

//                        if(currentLanguage.equals(AppConstants.LANG_EN)){
//
//                            if (user.getEnName().toLowerCase().contains(filterPattern.toLowerCase())) {
//                                filteredList.add(user);
//                            }
//                        }else{
//                            if (user.getName().toLowerCase().contains(filterPattern.toLowerCase())) {
                        filteredList.add(user);
//                            }
//                        }

                    }
                }
                results.values = filteredList;
                results.count = filteredList.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                adapter.dataSet.clear();
                adapter.dataSet.addAll((ArrayList<Topic>) results.values);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
