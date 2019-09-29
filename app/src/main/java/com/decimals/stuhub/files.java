package com.decimals.stuhub;

/**
 * Created by Shubham on 03-04-2018.
 */

public class files {

    public String displayName, downloadUrl, description, uploaderUid, uploaderName;

    public files() {

    }

    public files(String displayName, String downloadUrl, String description, String uploaderUid, String uploaderName) {
        this.displayName = displayName;
        this.downloadUrl = downloadUrl;
        this.description = description;
        this.uploaderUid = uploaderUid;
        this.uploaderName = uploaderName;

    }

    public String getdisplayName() {
        return displayName;
    }

    public void setdisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getdownloadUrl() {
        return downloadUrl;
    }

    public void setdownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getdescription() {
        return description;
    }

    public void setdescription(String description) {
        this.description = description;
    }

    public String getuploaderUid() {
        return uploaderUid;
    }

    public void setuploaderUid(String uploaderUid) {
        this.uploaderUid = uploaderUid;
    }

    public String getuploaderName() {
        return uploaderName;
    }

    public void setuploaderName(String uploaderName) {
        this.uploaderName = uploaderName;
    }

}
