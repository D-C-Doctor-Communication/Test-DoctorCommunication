package com.example.doctorcommunication;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.format.DateFormatTitleFormatter;
import com.prolificinteractive.materialcalendarview.format.TitleFormatter;

import java.text.SimpleDateFormat;
import java.util.List;

public class Fragment_medicalChart extends Fragment {

    private static final String CATEGORY_SAMPLE =
            "com.prolificinteractive.materialcalendarview.sample.SAMPLE";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        Log.d("myapp","진료기록탭 열림");
        //View view =  inflater.inflate(R.layout.fragment_medical_chart,container,false);

        View view =  inflater.inflate(R.layout.calendar_sample,container,false);
        RecyclerView list = view.findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setAdapter(new ResolveInfoAdapter(getContext(), getAllSampleActivities()));

        return view;
    }

    private List<ResolveInfo> getAllSampleActivities() {
        Intent filter = new Intent();
        filter.setAction(Intent.ACTION_RUN);
        filter.addCategory(CATEGORY_SAMPLE);
        return getActivity().getPackageManager().queryIntentActivities(filter, 0);
    }

    private void onRouteClicked(ResolveInfo route) {
        ActivityInfo activity = route.activityInfo;
        ComponentName name = new ComponentName(activity.applicationInfo.packageName, activity.name);
        startActivity(new Intent(Intent.ACTION_VIEW).setComponent(name));
    }

    class ResolveInfoAdapter extends RecyclerView.Adapter<ResolveInfoAdapter.ResolveInfoViewHolder> {

        private final PackageManager pm;
        private final LayoutInflater inflater;
        private final List<ResolveInfo> samples;

        private ResolveInfoAdapter(Context context, List<ResolveInfo> resolveInfos) {
            this.samples = resolveInfos;
            this.inflater = LayoutInflater.from(context);
            this.pm = context.getPackageManager();
        }

        @Override
        public ResolveInfoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = inflater.inflate(R.layout.item_route, viewGroup, false);
            return new ResolveInfoViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ResolveInfoViewHolder viewHolder, int i) {
            ResolveInfo item = samples.get(i);
            viewHolder.textView.setText(item.loadLabel(pm));
        }

        @Override
        public int getItemCount() {
            return samples.size();
        }

        class ResolveInfoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public final TextView textView;

            public ResolveInfoViewHolder(View view) {
                super(view);
                this.textView = view.findViewById(android.R.id.text1);
                view.setOnClickListener(this);
            }

            @Override
            public void onClick(@NonNull View v) {
                onRouteClicked(samples.get(getAdapterPosition()));
            }
        }
    }
}