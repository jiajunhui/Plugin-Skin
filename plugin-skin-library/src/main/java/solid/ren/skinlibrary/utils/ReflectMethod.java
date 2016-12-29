package solid.ren.skinlibrary.utils;

import android.graphics.drawable.Drawable;
import android.view.View;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Taurus on 2016/12/29.
 */

public class ReflectMethod {

    public static final String REFLECT_IMAGE_DRAWABLE_SET_METHOD_NAME = "setImageDrawable";
    public static final Class REFLECT_IMAGE_DRAWABLE_SET_METHOD_PARAMETER_NAME = Drawable.class;

    public static final String REFLECT_BACKGROUND_DRAWABLE_SET_METHOD_NAME = "setBackgroundDrawable";
    public static final Class REFLECT_BACKGROUND_DRAWABLE_SET_METHOD_PARAMETER_NAME = Drawable.class;

    public static void reflectSetImageDrawable(View view, Drawable drawable){
        Method method = reflectMethod(view, REFLECT_IMAGE_DRAWABLE_SET_METHOD_NAME, REFLECT_IMAGE_DRAWABLE_SET_METHOD_PARAMETER_NAME);
        try {
            if(method!=null){
                method.invoke(view,drawable);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static Method reflectMethod(View view,String methodName, Class...parameters){
        Class cls = view.getClass();
        Method method = null;
        try {
            method = cls.getMethod(methodName,parameters);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return method;
    }

}
