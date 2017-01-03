package solid.ren.skinlibrary.attr.support;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ProgressBar;

import solid.ren.skinlibrary.attr.base.ResourceType;
import solid.ren.skinlibrary.attr.base.SkinAttr;
import solid.ren.skinlibrary.loader.SkinManager;

/**
 * Created by Taurus on 2016/12/29.
 */

public class ProgressBarIndeterminateDrawableAttr extends SkinAttr {
    @Override
    public void apply(View view) {
        if(RES_TYPE_NAME_DRAWABLE.equals(attrValueTypeName)){
            Drawable bg = SkinManager.getInstance().getDrawable(ResourceType.Drawable,attrValueRefId);
            if(view instanceof ProgressBar){
                Rect bounds = ((ProgressBar)view).getIndeterminateDrawable().getBounds();
                bg.setBounds(bounds);
                ((ProgressBar)view).setIndeterminateDrawable(bg);
            }
        }
    }
}
