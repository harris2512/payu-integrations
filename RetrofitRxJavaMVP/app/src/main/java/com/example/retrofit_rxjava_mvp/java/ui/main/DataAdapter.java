package com.example.retrofit_rxjava_mvp.java.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofit_rxjava_mvp.R;
import com.example.retrofit_rxjava_mvp.java.models.ClientServices;
import com.example.retrofit_rxjava_mvp.java.models.ResponseModel;
import com.example.retrofit_rxjava_mvp.java.models.VendorList;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

//    private List<ResponseModel> mAndroidList;
    //private List<VendorList> mAndroidList;
    private List<ClientServices> mAndroidList;

    public DataAdapter(List<ClientServices> androidList) {
        mAndroidList = androidList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.mTvName.setText(mAndroidList.get(position).name);
       // holder.mTvVersion.setText(mAndroidList.get(position).addressText);
       // holder.mTvApi.setText(mAndroidList.get(position).email);
    }

    @Override
    public int getItemCount() {
        return mAndroidList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvName, mTvVersion, mTvApi;

        public ViewHolder(View view) {
            super(view);

            mTvName = (TextView) view.findViewById(R.id.tv_name);
            mTvVersion = (TextView) view.findViewById(R.id.tv_version);
            mTvApi = (TextView) view.findViewById(R.id.tv_api_level);
        }
    }
}
