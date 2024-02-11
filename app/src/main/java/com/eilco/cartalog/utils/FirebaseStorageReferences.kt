package com.vitor238.cartalog.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

object FirebaseStorageReferences {
    private val firebaseStorage: StorageReference = FirebaseStorage.getInstance().reference

    val myProfileRef: StorageReference
        get() {
            return firebaseStorage.child("users")
                .child(FirebaseAuth.getInstance().currentUser?.uid!!)
                .child("profile")
        }
}