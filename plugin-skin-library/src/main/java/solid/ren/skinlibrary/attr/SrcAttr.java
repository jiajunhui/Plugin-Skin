package solid.ren.skinlibrary.attr;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import solid.ren.skinlibrary.attr.base.SkinAttr;
import solid.ren.skinlibrary.loader.SkinManager;
import solid.ren.skinlibrary.utils.ReflectMethod;

/**
 * Created by Taurus on 16/12/4.
 *
 * this SrcAttr for attr name src , such as ImageView or ImageButton.
 *
 */
public class SrcAttr extends SkinAttr {
    @Override
    public void apply(View view) {
        Drawable bg = null;
        if(RES_TYPE_NAME_DRAWABLE.equals(attrValueTypeName)){
            bg = SkinManager.getInstance().getDrawable(ResourceType.Drawable,attrValueRefId);
        }else if(RES_TYPE_NAME_MIPMAP.equals(attrValueTypeName)){
            bg = SkinManager.getInstance().getDrawable(ResourceType.Mipmap,attrValueRefId);
        }
        if(bg == null)
            return;
        if(view instanceof ImageView){
            ((ImageView)view).setImageDrawable(bg);
        }else{
            ReflectMethod.reflectSetImageDrawable(view,bg);
        }

    }
}
