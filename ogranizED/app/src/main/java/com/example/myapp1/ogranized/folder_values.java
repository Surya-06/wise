package com.example.myapp1.ogranized;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Sireesha on 2/21/2018.
 */



public class folder_values implements Serializable {

    public String subject_name ;
    public ArrayList<File> data;

    public folder_values () {
        subject_name="";
        data = new ArrayList<>();
    }
};
