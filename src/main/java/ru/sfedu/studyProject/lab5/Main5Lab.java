package ru.sfedu.studyProject.lab5;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.studyProject.core.Main;
import ru.sfedu.studyProject.lab1.HibernateDataProvider;

public class Main5Lab {
    private static final Logger log = LogManager.getLogger(Main5Lab.class);

    public static void main(String[] args){
        switch (args[0].toLowerCase()) {
            case "lab1":
                lab1(args);
                break;
            case "lab2":
                break;
            case "lab3":
                break;
            case "lab4":
                break;
            case "lab5":
                break;
        }
    }

    private static void lab1(String[] args){
        switch (args[1]) {
            case "getHelp":
                getHelp();
        }
    }

    private static void getHelp(){
        HibernateDataProvider.getHelp().forEach(s -> System.out.println(s));
    }
}
