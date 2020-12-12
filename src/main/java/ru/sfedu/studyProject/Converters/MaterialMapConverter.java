package ru.sfedu.studyProject.Converters;

import com.opencsv.bean.AbstractBeanField;
import ru.sfedu.studyProject.model.Material;

import java.util.HashMap;
import java.util.Map;

public class MaterialMapConverter extends AbstractBeanField {

    @Override
    protected Object convert(String s) {
        if (s.isEmpty()) {
            return new HashMap<>();
        }
        Map<Material, Double> mapMaterial = new HashMap<>();
        String mapString = s.substring(1, s.length() - 1);
        if (mapString.isEmpty()) {
            return new HashMap<>();
        }
        String[] unparsedKeyValueArray = mapString.split(",");
        for (String keyValue: unparsedKeyValueArray) {
            String[] keyValueArray = keyValue.split(":");
            Material material = new Material();
            material.setId(Long.parseLong(keyValueArray[0]));
            mapMaterial.put(material, Double.parseDouble(keyValueArray[1]));
        }
       return mapMaterial;
    }

    @Override
    protected String convertToWrite(Object value) {
        Map<Material, Double> materialMap = (Map<Material, Double>) value;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        if (!materialMap.isEmpty()) {
            materialMap.forEach((material, aDouble) -> {
                stringBuilder.append(material.getId());
                stringBuilder.append(":");
                stringBuilder.append(aDouble);
                stringBuilder.append(",");
            });
            stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
