package apiTests;

import utils.UserProvider;
import org.testng.annotations.BeforeMethod;

import static api.spec.Specifications.*;

public class BaseTestSetups {
    public UserProvider user;

    @BeforeMethod()
    public void testPreparation(){
        user = new UserProvider();

        initSpecifications(requestSpecification(),
                responseSpecificationOkOrCreated());
    }

}
