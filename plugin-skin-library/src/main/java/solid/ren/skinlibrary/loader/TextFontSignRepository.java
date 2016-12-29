package solid.ren.skinlibrary.loader;

import android.graphics.Typeface;
import java.util.ArrayList;
import java.util.List;
import solid.ren.skinlibrary.attr.base.TextFontAttrSign;
import solid.ren.skinlibrary.utils.TypefaceUtils;

/**
 * Created by _SOLID
 * Date:2016/7/12
 * Time:17:58
 */
public class TextFontSignRepository {

    private List<TextFontAttrSign> mTextViews = new ArrayList<>();

    public void add(TextFontAttrSign textView) {
        mTextViews.add(textView);
        textView.setTypeface(TypefaceUtils.CURRENT_TYPEFACE);
    }

    public void clear() {
        mTextViews.clear();
    }

    public void applyFont(Typeface tf) {
        for (TextFontAttrSign textView : mTextViews) {
            textView.setTypeface(tf);
        }
    }
}
