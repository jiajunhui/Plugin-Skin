package solid.ren.skinlibrary.attr;

import android.content.res.ColorStateList;
import android.view.View;
import android.widget.TextView;
import solid.ren.skinlibrary.attr.base.SkinAttr;
import solid.ren.skinlibrary.attr.base.TextColorAttrSign;
import solid.ren.skinlibrary.loader.SkinManager;

/**
 * Created by _SOLID
 * Date:2016/4/13
 * Time:22:53
 */
public class TextColorAttr extends SkinAttr {
    @Override
    public void apply(View view) {
        ColorStateList colorStateList = null;
        if (RES_TYPE_NAME_COLOR.equals(attrValueTypeName)) {
            colorStateList = SkinManager.getInstance().getColorStateList(ResourceType.Color,attrValueRefId);
        }else if(RES_TYPE_NAME_DRAWABLE.equals(attrValueTypeName)){
            colorStateList = SkinManager.getInstance().getColorStateList(ResourceType.Drawable,attrValueRefId);
        }
        if(colorStateList==null)
            return;
        if (view instanceof TextView) {
            TextView tv = (TextView) view;
            tv.setTextColor(colorStateList);
        }else if(view instanceof TextColorAttrSign){
            ((TextColorAttrSign)view).setTextColor(colorStateList);
        }
    }
}
