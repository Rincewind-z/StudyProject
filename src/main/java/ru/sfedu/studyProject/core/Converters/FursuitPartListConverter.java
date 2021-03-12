package ru.sfedu.studyProject.core.Converters;

import com.opencsv.bean.AbstractBeanField;
import ru.sfedu.studyProject.core.model.FursuitPart;

import java.util.ArrayList;
import java.util.List;

public class FursuitPartListConverter extends AbstractBeanField {

    @Override
    protected Object convert(String s) {
        if (s.isEmpty()) {
            return new  ArrayList<>();
        }
        List<FursuitPart> fursuitPartList = new ArrayList<>();
        String listString = s.substring(1, s.length() - 1);
        if (listString.isEmpty()) {
            return new  ArrayList<>();
        }
        String[] fursuitPartStringArray = listString.split(",");
        for (String fursuitPartString: fursuitPartStringArray) {
            FursuitPart fursuitPart = new FursuitPart();
            fursuitPart.setId(Long.parseLong(fursuitPartString));
            fursuitPartList.add(fursuitPart);
        }
        return fursuitPartList;
    }

    @Override
    protected String convertToWrite(Object value) {
        List<FursuitPart> fursuitPartList = (List<FursuitPart>) value;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        if (!fursuitPartList.isEmpty()) {
            fursuitPartList.forEach(fursuitPart -> {
                stringBuilder.append(fursuitPart.getId());
                stringBuilder.append(",");
            });
            stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
