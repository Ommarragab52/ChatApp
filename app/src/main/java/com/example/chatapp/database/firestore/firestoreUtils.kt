package com.example.chatapp.database.firestore

import com.example.chatapp.database.model.Message
import com.example.chatapp.database.model.Room
import com.example.chatapp.database.model.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

fun getCollectionRef(collectionName: String): CollectionReference {
    val db = Firebase.firestore
    return db.collection(collectionName)
}

fun addUserToFirestoreDB(
    user: User,
    onCompleteListener: OnCompleteListener<Void>,
    onFailureListener: OnFailureListener
) {
    getCollectionRef(User.COLLECTION_NAME)
        .document(user.id ?: "")
        .set(user)
        .addOnCompleteListener(onCompleteListener)
        .addOnFailureListener(onFailureListener)
}

fun getUserFromFirestoreDB(
    uid: String,
    onSuccessListener: OnSuccessListener<DocumentSnapshot>,
    onFailureListener: OnFailureListener
) {
    getCollectionRef(User.COLLECTION_NAME)
        .document(uid)
        .get()
        .addOnSuccessListener(onSuccessListener)
        .addOnFailureListener(onFailureListener)
}

fun addRoomToFirestoreDB(
    room: Room, onSuccessListener: OnSuccessListener<Void>,
    onFailureListener: OnFailureListener
) {
    val documentRef = getCollectionRef(Room.COLLECTION_NAME).document()
    room.roomId = documentRef.id
    documentRef.set(room)
        .addOnSuccessListener(onSuccessListener)
        .addOnFailureListener(onFailureListener)
}

fun getRoomsFromFirestoreDB(
    onSuccessListener: OnSuccessListener<QuerySnapshot>,
    onFailureListener: OnFailureListener
) {
    getCollectionRef(Room.COLLECTION_NAME)
        .get()
        .addOnSuccessListener(onSuccessListener)
        .addOnFailureListener(onFailureListener)
}

fun getMessagesRef(roomId: String): CollectionReference {
    val roomCollection = getCollectionRef(Room.COLLECTION_NAME)
    val roomDoc = roomCollection.document(roomId)
    return roomDoc.collection(Message.COLLECTION_NAME)
}

fun addMessageToFirestoreDB(
    roomId: String,
    message: Message,
    onSuccessListener: OnSuccessListener<Void>,
    onFailureListener: OnFailureListener
) {
    val messageCollection = getMessagesRef(roomId)
    val messageDoc = messageCollection.document()
    message.id = messageDoc.id
    messageDoc.set(message)
        .addOnSuccessListener(onSuccessListener)
        .addOnFailureListener(onFailureListener)
}

fun getMessagesFromFirestoreDB(roomId: String, listener: EventListener<QuerySnapshot>) {
    val messageCollection = getMessagesRef(roomId)
    messageCollection.orderBy("timeDate", Query.Direction.DESCENDING)
        .addSnapshotListener(listener)
}