package com.polotechnologies.firebaseaio.data_models;

public class RealtimeItems {

    String realtimeImageName;
    String realtimeImageUrl;

    public RealtimeItems() {
    }

    public RealtimeItems(String realtimeImageName, String realtimeImageUrl) {
        this.realtimeImageName = realtimeImageName;
        this.realtimeImageUrl = realtimeImageUrl;
    }

    public String getRealtimeImageName() {
        return realtimeImageName;
    }

    public String getRealtimeImageUrl() {
        return realtimeImageUrl;
    }
}

