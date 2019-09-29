package com.decimals.stuhub;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.Context.DOWNLOAD_SERVICE;
import static android.os.Environment.DIRECTORY_DOWNLOADS;

/**
 * Created by Shubham on 08-04-2018.
 */

public class filesViewHolder extends RecyclerView.ViewHolder {
    private final Context context;
    View mView;
    DownloadManager downloadManager;
    long downloadId;

    public filesViewHolder(final View itemView) {
        super(itemView);
        context = itemView.getContext();

        mView = itemView;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemPosition = getLayoutPosition();
                Log.d("position", Integer.toString(itemPosition));

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(false);
                builder.setTitle("Download");
                builder.setMessage("Do you want to download the file?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context, "Downloading", Toast.LENGTH_SHORT).show();

                        TextView download_link = (TextView) mView.findViewById(R.id.download_text);
                        TextView file_name = (TextView) mView.findViewById(R.id.name_text);
                        String url = download_link.getText().toString();
                        String fileName = file_name.getText().toString();
                        Uri uri = Uri.parse(url);
                        downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
                        DownloadManager.Request request = new DownloadManager.Request(uri);
                        request.setTitle(fileName);
                        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                        request.setDestinationInExternalFilesDir(context, DIRECTORY_DOWNLOADS, fileName);
                        request.setVisibleInDownloadsUi(true);
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                        downloadId = downloadManager.enqueue(request);

                        Log.d("URL", url);

                    }
                })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(context, "Download task Canceled", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        });
                builder.create().show();
            }
        });

    }

    public void setDetails(Context context, String displayName, String downloadUrl, String description, String uploaderName) {

        TextView file_name = (TextView) mView.findViewById(R.id.name_text);
        TextView download_link = (TextView) mView.findViewById(R.id.download_text);
        TextView description_text = (TextView) mView.findViewById(R.id.description_text);
        TextView uploader_name = (TextView) mView.findViewById(R.id.uploader_name);


        file_name.setText(displayName);
        download_link.setText(downloadUrl);
        description_text.setText(description);
        uploader_name.setText(uploaderName);


    }
}
