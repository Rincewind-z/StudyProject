package ru.sfedu.studyProject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.sfedu.studyProject.DataProviders.DataProviderCsv;
import ru.sfedu.studyProject.DataProviders.DataProviderJdbc;
import ru.sfedu.studyProject.DataProviders.DataProviderXml;

public class MainTest {
    private static final Logger log = LogManager.getLogger(Main.class);

    @BeforeAll
    static void deleteAll() {
        DataProviderJdbc dataProviderJdbc = DataProviderJdbc.getInstance();
        dataProviderJdbc.dropDB();
        DataProviderXml.deleteAll();
        DataProviderCsv.deleteAll();
    }

    @Test
    void testCreateCustomer() {
        Main.main(new String[]{"DataProviderCsv", "createCustomer", "0", " Ivan vk.com/**", "88005553535"});
        Main.main(new String[]{"DataProviderXml", "createCustomer", "0", " Ivan vk.com/**", "88005553535"});
        Main.main(new String[]{"DataProviderJdbc", "createCustomer", "0", " Ivan vk.com/**", "88005553535"});
    }

    @Test
    void testCreateCustomerFailed() {
        Main.main(new String[]{"fd", "createCustomer", "0", " Ivan vk.com/**", "88005553535"});
        Main.main(new String[]{"DataProviderXml", "0", "0", " Ivan vk.com/**", "88005553535"});
        Main.main(new String[]{"DataProviderJdbc", "createCustomer", "9299", " Ivan vk.com/**", "88005553535"});
    }

    @Test
    void testGetCustomerList() {
        Main.main(new String[]{"DataProviderCsv", "getCustomer", "0"});
        Main.main(new String[]{"DataProviderXml", "getCustomer", "0"});
        Main.main(new String[]{"DataProviderJdbc", "getCustomer", "0"});
    }

    @Test
    void testGetCustomerListFailed() {
        Main.main(new String[]{"DataProviderCsv", "getCustomer", "515151"});
        Main.main(new String[]{"DataProviderXml", "getCustomer", "afd"});
    }

    @Test
    void testGetCustomer() {
        Main.main(new String[]{"DataProviderCsv", "getCustomer", "0", "1"});
        Main.main(new String[]{"DataProviderXml", "getCustomer", "0", "1"});
        Main.main(new String[]{"DataProviderJdbc", "getCustomer", "0", "1"});
    }

    @Test
    void testGetCustomerFailed() {
        Main.main(new String[]{"DataProviderCsv", "getCustomer", "95662626", "1"});
        Main.main(new String[]{"DataProviderXml", "getCustomer", "fsfgsg", "1"});
        Main.main(new String[]{"DataProviderJdbc", "getCustomer", "0", "51511151"});
        Main.main(new String[]{"DataProviderJdbc", "getCustomer", "0", "sfsdfds"});
    }

    @Test
    void testGetProjectEstimate() {
        Main.main(new String[]{"DataProviderCsv", "getProjectEstimate", "0"});
        Main.main(new String[]{"DataProviderXml", "getProjectEstimate", "0"});
        Main.main(new String[]{"DataProviderJdbc", "getProjectEstimate", "0"});
    }

    @Test
    void testGetProjectEstimateFailed() {
        Main.main(new String[]{"DataProviderCsv", "getProjectEstimate", "fdgsdfgdfg"});
        Main.main(new String[]{"DataProviderXml", "getProjectEstimate", "25515161"});
        Main.main(new String[]{"DataProviderXml", "getProjectEstimate", ""});
    }
}