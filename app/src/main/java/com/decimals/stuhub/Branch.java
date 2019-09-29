package com.decimals.stuhub;

/**
 * Created by ADARSH ANURAG on 4/2/2018.
 */

public class Branch {
    private String mSubjectName;
    private int mImageId;

    public Branch(String SubjectName, int ImageId) {
        mSubjectName = SubjectName;
        mImageId = ImageId;
    }

    /**
     * method to get subject Name
     */
    public String getSubjectName() {
        return mSubjectName;
    }

    /**
     * method to return image id
     */
    public int getImageId() {
        return mImageId;
    }
}
