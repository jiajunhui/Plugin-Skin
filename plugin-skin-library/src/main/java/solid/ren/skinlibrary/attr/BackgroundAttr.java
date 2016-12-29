package solid.ren.skinlibrary.attr;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.view.View;
import solid.ren.skinlibrary.attr.base.SkinAttr;
import solid.ren.skinlibrary.config.SkinConfig;
import solid.ren.skinlibrary.loader.SkinManager;
import solid.ren.skinlibrary.utils.AnimatorUtils;

/**
 * Created by _SOLID
 * Date:2016/4/13
 * Time:21:46
 */
public class BackgroundAttr extends SkinAttr {

    @Override
    public void apply(View view) {

        if (RES_TYPE_NAME_COLOR.equals(attrValueTypeName)) {
            int color = SkinManager.getInstance().getColor(attrValueRefId);
            //this handle for cardview
            if (view instanceof CardView) {
                CardView cardView = (CardView) view;
                //when the view instance of cardview , set background color should use this method.
                cardView.setCardBackgroundColor(color);
            } else {
                //original author use this method set background color .
                //view.setBackgroundColor(color);

                //this method change color animation.
                //AnimatorUtils.showBackgroundColorAnimation(view, getPreColor(view),color,500);
                if(SkinManager.getInstance().isIntentChangeSkin() && SkinConfig.isGradientChangeColor()){
                    AnimatorUtils.showBackgroundColorAnimation(view, getPreColor(view),color,500);
                }else{
                    view.setBackgroundColor(color);
                }
            }
        } else if (RES_TYPE_NAME_DRAWABLE.equals(attrValueTypeName)) {
            Drawable bg = SkinManager.getInstance().getDrawable(ResourceType.Drawable,attrValueRefId);
            view.setBackgroundDrawable(bg);
        } else if (RES_TYPE_NAME_MIPMAP.equals(attrValueTypeName)) {
            Drawable bg = SkinManager.getInstance().getDrawable(ResourceType.Mipmap,attrValueRefId);
            view.setBackgroundDrawable(bg);
        }
    }

    private int getPreColor(View view){
        Drawable drawable = view.getBackground();
        if(drawable instanceof ColorDrawable){
            return ((ColorDrawable) drawable).getColor();
        }
        return Color.TRANSPARENT;
    }
}
