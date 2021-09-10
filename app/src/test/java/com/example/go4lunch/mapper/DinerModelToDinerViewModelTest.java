package com.example.go4lunch.mapper;

import com.example.go4lunch.model.DinerModel;
import com.example.go4lunch.model.WorkmateModel;
import com.example.go4lunch.ui.viewModel.DinerViewModel;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DinerModelToDinerViewModelTest {

    DinerModelToDinerViewModel mapper = new DinerModelToDinerViewModel();

    @Test
    public void map() {

        // GIVEN
        DinerModel model = new DinerModel("workmateId", "restaurantId", new Date(), true, null);
        model.setWorkmateModel(new WorkmateModel("username", "mail", "url"));

        // WHEN
        DinerViewModel viewModel = mapper.map(model);

        // THEN
        assertNotNull(viewModel);
        String USERNAME = "username";
        assertEquals(viewModel.getWorkmate(), USERNAME);
        String URL_PICTURE = "url";
        assertEquals(viewModel.getWorkmatePictureUrl(), URL_PICTURE);
    }

    @Test
    public void maps() {
        // GIVEN
        DinerModel model1 = new DinerModel("workmateId", "restaurantId", new Date(), false, null);
        model1.setWorkmateModel(new WorkmateModel("username1", "mail1", "url1"));
        DinerModel model2 = new DinerModel("workmateId", "restaurantId", new Date(), false, null);
        model2.setWorkmateModel(new WorkmateModel("username2", "mail2", "url2"));
        List<DinerModel> models = new ArrayList<>();
        models.add(model1);
        models.add(model2);

        // WHEN
        List<DinerViewModel> viewModels = mapper.maps(models);

        // THEN
        assertNotNull(viewModels.get(0));
        assertEquals(viewModels.get(0).getWorkmate(), "username1");
        assertEquals(viewModels.get(0).getWorkmatePictureUrl(), "url1");
        assertNotNull(viewModels.get(1));
        assertEquals(viewModels.get(1).getWorkmate(), "username2");
        assertEquals(viewModels.get(1).getWorkmatePictureUrl(), "url2");
    }
}
