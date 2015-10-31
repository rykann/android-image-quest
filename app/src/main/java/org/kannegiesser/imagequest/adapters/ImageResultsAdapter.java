package org.kannegiesser.imagequest.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.kannegiesser.imagequest.R;
import org.kannegiesser.imagequest.models.ImageResult;

import java.util.List;

public class ImageResultsAdapter extends ArrayAdapter<ImageResult> {

    public ImageResultsAdapter(Context context, List<ImageResult> imageResults) {
        super(context, 0, imageResults);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageResult result = getItem(position);

        // if a recycled view wasn't given, inflate a new one
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image_result, parent, false);
        }

        ImageView ivThumbnail = (ImageView) convertView.findViewById(R.id.ivThumbnail);
        ivThumbnail.setImageResource(android.R.color.transparent);
        Picasso.with(getContext()).load(result.thumbUrl).noFade().into(ivThumbnail);

        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        tvTitle.setText(Html.fromHtml(result.title));

        return convertView;
    }
}
