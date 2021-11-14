package com.example.go4lunch.dao;

import com.example.go4lunch.entity.WorkmateEntity;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WorkmateDaoImplTest{

    WorkmateDaoImpl dao = new WorkmateDaoImplFake();
    CollectionReference collectionMock = mock(CollectionReference.class);
    DocumentReference documentReferenceMock = mock(DocumentReference.class);
    DocumentSnapshot mock = mock(DocumentSnapshot.class);
    Task<DocumentSnapshot> documentSnapshotMock = Tasks.forResult(mock);

    @Test
    public void getUser() {
        // GIVEN
        WorkmateEntity workmateEntity = new WorkmateEntity();
        workmateEntity.setId("22");
        when(mock.getDocumentReference(anyString())).thenCallRealMethod();
        when(mock.get(anyString())).thenReturn(workmateEntity);
        when(documentReferenceMock.get()).thenReturn(documentSnapshotMock);
        when(collectionMock.document(anyString())).thenReturn(documentReferenceMock);
        // WHEN - THEN
        dao.getUser("id", data -> assertEquals(data.getId(), 21));
    }

    public class WorkmateDaoImplFake extends WorkmateDaoImpl{
        @Override
        protected CollectionReference getUsersCollection() {
            return collectionMock;
        }
    }
}
