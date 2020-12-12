package ru.sfedu.studyProject.Converters;

import com.opencsv.bean.AbstractBeanField;
import ru.sfedu.studyProject.model.FursuitPart;
import ru.sfedu.studyProject.model.Material;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
