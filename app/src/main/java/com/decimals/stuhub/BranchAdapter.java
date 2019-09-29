package com.decimals.stuhub;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ADARSH ANURAG on 4/2/2018.
 */

public class BranchAdapter extends ArrayAdapter<Branch> {
    public BranchAdapter(Activity context, ArrayList<Branch> branch) {
        super(context, 0, branch);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Branch currentBranch = getItem(position);
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_layout_main, parent, false);
        }
        TextView defaultText = (TextView) listItemView.findViewById(R.id.branch_name);
        defaultText.setText(currentBranch.getSubjectName());
        RelativeLayout image = (RelativeLayout) listItemView.findViewById(R.id.image_background);
        if (currentBranch.getImageId() == -1)
            image.setVisibility(View.GONE);
        else
            image.setBackgroundResource(currentBranch.getImageId());
        return listItemView;
    }
}
