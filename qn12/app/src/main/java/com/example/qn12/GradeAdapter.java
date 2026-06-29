package com.example.qn12;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

// Custom Adapter to display students in the ListView
public class GradeAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Student> studentList;

    public GradeAdapter(Context context, ArrayList<Student> studentList) {
        this.context = context;
        this.studentList = studentList;
    }

    @Override
    public int getCount() {
        return studentList.size();
    }

    @Override
    public Object getItem(int position) {
        return studentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate the custom row layout if it doesn't exist yet
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_grade, parent, false);
        }

        // Get the current student object
        Student student = studentList.get(position);

        // Find views in the custom layout
        TextView tvName = convertView.findViewById(R.id.tv_name);
        TextView tvSubject = convertView.findViewById(R.id.tv_subject);
        TextView tvScore = convertView.findViewById(R.id.tv_score);
        LinearLayout itemLayout = convertView.findViewById(R.id.item_layout);

        // Set student data to views
        tvName.setText(student.getName());
        tvSubject.setText(student.getSubject());
        tvScore.setText("Score: " + student.getScore());

        // Color-coded feedback: Green for > 50, Red for < 50
        if (student.getScore() >= 50) {
            itemLayout.setBackgroundColor(Color.parseColor("#C8E6C9")); // Light Green
        } else {
            itemLayout.setBackgroundColor(Color.parseColor("#FFCDD2")); // Light Red
        }

        return convertView;
    }
}
