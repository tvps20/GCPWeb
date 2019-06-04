package web.santiago.gcp.controllers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

@SpringBootTest
public class HomeControllerTest {

    @InjectMocks
    private HomeController homeController;

    private Model model;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        this.model = new ConcurrentModel();
    }

    @Test
    public void homepage() {

        Assert.assertEquals(this.homeController.homePage(this.model), "home");
        Assert.assertEquals(this.model.containsAttribute("appName"), true);
    }
}
