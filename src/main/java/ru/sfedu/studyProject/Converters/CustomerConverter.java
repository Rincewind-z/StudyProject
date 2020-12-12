package ru.sfedu.studyProject.Converters;

import com.opencsv.bean.AbstractBeanField;
import ru.sfedu.studyProject.model.Customer;

public class CustomerConverter extends AbstractBeanField {

    @Override
    protected Object convert(String s) {
        Customer customer = new Customer();
        customer.setId(Long.parseLong(s));
        return customer;
    }

    @Override
    protected String convertToWrite(Object value) {
        Customer customer = (Customer) value;
        return String.valueOf(customer.getId());
    }

}
