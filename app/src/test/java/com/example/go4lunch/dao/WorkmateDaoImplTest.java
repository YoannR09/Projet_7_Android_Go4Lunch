package com.example.go4lunch.dao;

import com.example.go4lunch.entity.WorkmateEntity;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WorkmateDaoImplTest{

    WorkmateDaoImpl dao = new WorkmateDaoImplFake();
    CollectionReference collectionMock = mock(CollectionReference.class);
    DocumentReference documentReferenceMock = mock(DocumentReference.class);
    Task<DocumentSnapshot> documentSnapshotMock = Tasks.forResult(new DocumentSnapshot());



    @Test
    public void getUser() {
        // GIVEN
        WorkmateEntity workmateEntity = new WorkmateEntity();
        workmateEntity.setId("22");
        when(collectionMock.document(anyString())).thenReturn(documentReferenceMock);
        when(documentReferenceMock.get()).thenReturn(documentSnapshotMock);
        when(documentSnapshotMock.continueWith(any())).thenCallRealMethod();
        when(documentSnapshotMock)
        when(taskMock.getResult(any())).thenReturn();
        // WHEN
        dao.getUser("id", data -> {

            // THEN

        });
    }

    public class WorkmateDaoImplFake extends WorkmateDaoImpl{
        @Override
        protected CollectionReference getUsersCollection() {
            return collectionMock;
        }
    }
}
