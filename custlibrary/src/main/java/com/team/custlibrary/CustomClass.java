package com.team.custlibrary;

import android.content.Context;
import android.widget.Toast;

public class CustomClass {
    public static void s(Context c, String message){

        Toast.makeText(c,message,Toast.LENGTH_SHORT).show();

    }
}
