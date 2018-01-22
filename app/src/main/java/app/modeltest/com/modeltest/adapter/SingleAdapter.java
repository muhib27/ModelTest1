package app.modeltest.com.modeltest.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import app.modeltest.com.modeltest.Model.Topic;
import app.modeltest.com.modeltest.R;

/**
 * Created by RR on 31-Dec-17.
 */

public class SingleAdapter extends RecyclerView.Adapter<SingleAdapter.MyViewHolder>{

    private List<Topic> dataSet;

    public SingleAdapter(List<Topic> dataSet) {
        this.dataSet = dataSet;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_single, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        TextView txtTopicTitle = holder.txtTopicTitle;

        txtTopicTitle.setText(dataSet.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txtTopicTitle;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtTopicTitle = (TextView)itemView.findViewById(R.id.row);

        }
    }
}
