package com.example.android.vitrinedefilmes.Negocios;

import android.content.Context;

import com.example.android.vitrinedefilmes.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class InputStreamNegocios {

    private static Context context;

    public InputStreamNegocios(Context context) {
        this.context = context;
    }

    public static String parserObjetoString (InputStream inputStream) throws IOException {

        StringBuilder stringBuilder = new StringBuilder();


        if(inputStream!=null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream,Charset.forName(context.getResources().getString(R.string.utf_8)));
            BufferedReader reader = new BufferedReader(inputStreamReader);

            String line = reader.readLine();


            while(line!=null){
                stringBuilder.append(line);
                line = reader.readLine();
            }
        }
        return stringBuilder.toString();
    }
}
