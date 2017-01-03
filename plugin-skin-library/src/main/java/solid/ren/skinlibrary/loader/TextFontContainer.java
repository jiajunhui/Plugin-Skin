package solid.ren.skinlibrary.loader;

import android.graphics.Typeface;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import solid.ren.skinlibrary.attr.base.TextFontAttrSign;
import solid.ren.skinlibrary.utils.TypefaceUtils;

/**
 * Created by Taurus on 2017/1/2.
 */

public class TextFontContainer {

    private List<TextView> mTextViews = new ArrayList<>();

    private List<TextFontAttrSign> mTextFonts = new ArrayList<>();

    public TextFontContainer(){

    }

    public void add(TextView textView){
        mTextViews.add(textView);
        textView.setTypeface(TypefaceUtils.CURRENT_TYPEFACE);
    }

    public void add(TextFontAttrSign textFontView){
        mTextFonts.add(textFontView);
        textFontView.setTypeface(TypefaceUtils.CURRENT_TYPEFACE);
    }

    public void applyFont(Typeface typeface){
        if(mTextViews!=null){
            for(TextView textView : mTextViews){
                textView.setTypeface(typeface);
            }
        }
        if(mTextFonts!=null){
            for(TextFontAttrSign textFontAttrSign : mTextFonts){
                textFontAttrSign.setTypeface(typeface);
            }
        }
    }

}
