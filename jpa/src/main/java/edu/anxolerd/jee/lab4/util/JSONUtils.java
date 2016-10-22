package edu.anxolerd.jee.lab4.util;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.annotation.Annotation;


public class JSONUtils {
    public static Gson getGson() {
        Gson gson = new GsonBuilder()
            .setExclusionStrategies(new ExclusionStrategy() {

                public boolean shouldSkipClass(Class<?> clazz) {
                    return false;
                }


                public boolean shouldSkipField(FieldAttributes f) {
                    Annotation a = f.getAnnotation(GsonExclude.class);
                    return a != null;
                }

            })
            /**
             * Use serializeNulls method if you want To serialize null values
             * By default, Gson does not serialize null values
             */
            .serializeNulls()
            .create();
        return gson;
    }
}
