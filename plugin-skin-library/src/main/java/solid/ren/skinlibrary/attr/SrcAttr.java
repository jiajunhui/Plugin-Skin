package solid.ren.skinlibrary.attr;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import solid.ren.skinlibrary.attr.base.SkinAttr;
import solid.ren.skinlibrary.loader.SkinManager;

/**
 * Created by Taurus on 16/12/4.
 *
 * this SrcAttr for attr name src , such as ImageView or ImageButton.
 *
 */
public class SrcAttr extends SkinAttr {
    @Override
    public void apply(View view) {
        if(RES_TYPE_NAME_DRAWABLE.equals(attrValueTypeName)
                || RES_TYPE_NAME_DRAWABLE.equals(attrValueTypeName)){
            Drawable bg = SkinManager.getInstance().getDrawable(attrValueRefId);
            if(view instanceof ImageView){
                ((ImageView)view).setImageDrawable(bg);
            }
            if(view instanceof ImageButton){
                ((ImageButton)view).setImageDrawable(bg);
            }
        }
    }
}
