package solid.ren.skinlibrary.listener;

import android.graphics.Typeface;

/**
 * Created by Taurus on 2017/1/2.
 */

public interface IFontLoader {
    void attach(IFontUpdate observer);

    void detach(IFontUpdate observer);

    void notifyFontUpdate(Typeface typeface);
}
