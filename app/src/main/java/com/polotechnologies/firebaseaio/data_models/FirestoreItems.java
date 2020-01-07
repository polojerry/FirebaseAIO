package com.polotechnologies.firebaseaio.data_models;

public class FirestoreItems {

    String firestoreImageName;
    String firestoreImageUrl;

    public FirestoreItems() {
    }

    public FirestoreItems(String firestoreImageName, String firestoreImageUrl) {
        this.firestoreImageName = firestoreImageName;
        this.firestoreImageUrl = firestoreImageUrl;
    }

    public String getFirestoreImageName() {
        return firestoreImageName;
    }

    public void setFirestoreImageName(String firestoreImageName) {
        this.firestoreImageName = firestoreImageName;
    }

    public String getFirestoreImageUrl() {
        return firestoreImageUrl;
    }

    public void setFirestoreImageUrl(String firestoreImageUrl) {
        this.firestoreImageUrl = firestoreImageUrl;
    }
}

