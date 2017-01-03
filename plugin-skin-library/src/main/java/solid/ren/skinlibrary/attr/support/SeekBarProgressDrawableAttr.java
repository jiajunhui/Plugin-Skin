package solid.ren.skinlibrary.attr.support;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.SeekBar;
import solid.ren.skinlibrary.attr.base.ResourceType;
import solid.ren.skinlibrary.attr.base.SkinAttr;
import solid.ren.skinlibrary.loader.SkinManager;

/**
 * Created by Taurus on 2017/1/3.
 */

public class SeekBarProgressDrawableAttr extends SkinAttr {
    @Override
    public void apply(View view) {
        if(RES_TYPE_NAME_DRAWABLE.equals(attrValueTypeName)){
            Drawable drawable = SkinManager.getInstance().getDrawable(ResourceType.Drawable,attrValueRefId);
            if(view instanceof SeekBar){
                ((SeekBar)view).setProgressDrawable(drawable);
            }
        }
    }
}
