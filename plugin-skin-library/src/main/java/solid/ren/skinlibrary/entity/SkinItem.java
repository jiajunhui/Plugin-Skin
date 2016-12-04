package solid.ren.skinlibrary.entity;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

import solid.ren.skinlibrary.attr.base.SkinAttr;
import solid.ren.skinlibrary.utils.SkinListUtils;


/**
 * Created by _SOLID
 * Date:2016/4/14
 * Time:9:21
 * <p></p>
 *
 * Used to store the View and its some attrs for the skin to change.
 *
 */
public class SkinItem {

    public View view;

    public List<SkinAttr> attrs;

    public SkinItem() {
        attrs = new ArrayList<SkinAttr>();
    }

    public void apply() {
        if (SkinListUtils.isEmpty(attrs)) {
            return;
        }
        for (SkinAttr at : attrs) {
            at.apply(view);
        }
    }

    public void clean() {
        if (SkinListUtils.isEmpty(attrs)) {
            return;
        }
        for (SkinAttr at : attrs) {
            at = null;
        }
    }

    @Override
    public String toString() {
        return "SkinItem [view=" + view.getClass().getSimpleName() + ", attrs=" + attrs + "]";
    }
}
