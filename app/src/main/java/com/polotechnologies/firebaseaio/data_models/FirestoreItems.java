package com.polotechnologies.firebaseaio.data_models;

public class FirestoreItems {

    String FirestoreImageName;
    String FirestoreImageUrl;

    public FirestoreItems() {
    }

    public FirestoreItems(String FirestoreImageName, String FirestoreImageUrl) {
        this.FirestoreImageName = FirestoreImageName;
        this.FirestoreImageUrl = FirestoreImageUrl;
    }

    public String getFirestoreImageName() {
        return FirestoreImageName;
    }

    public String getFirestoreImageUrl() {
        return FirestoreImageUrl;
    }
}

