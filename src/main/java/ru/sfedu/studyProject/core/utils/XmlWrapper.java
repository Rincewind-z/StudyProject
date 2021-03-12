package ru.sfedu.studyProject.core.utils;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root
public class XmlWrapper <T>{
    public List<T> getObjectList() {
        return objectList;
    }

    public void setObjectList(List<T> tList) {
        this.objectList = tList;
    }

    @ElementList(required = false)
    private List<T> objectList;
}
