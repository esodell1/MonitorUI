package edu.uw.tcss450.monitorui.services;

import java.util.ArrayList;
import java.util.List;

public class MedianFilter {

    private List<Integer> mList;
    private List<Integer> mData;
    private int windowSize;

    public MedianFilter() {
        // Default constructor
        mList = mData = null;
        windowSize = 1;
    }

    public MedianFilter(List<Integer> data, int windowSize) {
        mList = data;
        this.windowSize = windowSize;
    }

    public List<Integer> getMedianData() {
        mData = new ArrayList<Integer>();

        return mData;
    }

}
