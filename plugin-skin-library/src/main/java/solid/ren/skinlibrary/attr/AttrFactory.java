package solid.ren.skinlibrary.attr;

import java.util.HashMap;

import solid.ren.skinlibrary.attr.base.SkinAttr;
import solid.ren.skinlibrary.attr.support.BackgroundAttr;
import solid.ren.skinlibrary.attr.support.ProgressBarIndeterminateDrawableAttr;
import solid.ren.skinlibrary.attr.support.SeekBarProgressDrawableAttr;
import solid.ren.skinlibrary.attr.support.SrcAttr;
import solid.ren.skinlibrary.attr.support.TextColorAttr;
import solid.ren.skinlibrary.utils.SkinL;

/**
 * Created by _SOLID
 * Date:2016/4/14
 * Time:9:47
 */
public class AttrFactory {

    private static String TAG = "AttrFactory";

    public static final String SUPPORT_ATTR_NAME_BACKGROUND                 = "background";
    public static final String SUPPORT_ATTR_NAME_TEXT_COLOR                 = "textColor";
    public static final String SUPPORT_ATTR_NAME_SRC                        = "src";
    public static final String SUPPORT_ATTR_NAME_INDETERMINATE_DRAWABLE     = "indeterminateDrawable";
    public static final String SUPPORT_ATTR_NAME_PROGRESS_DRAWABLE          = "progressDrawable";

    public static HashMap<String, SkinAttr> mSupportAttr = new HashMap<>();

    static {
        mSupportAttr.put(SUPPORT_ATTR_NAME_BACKGROUND, new BackgroundAttr());
        mSupportAttr.put(SUPPORT_ATTR_NAME_TEXT_COLOR, new TextColorAttr());
        mSupportAttr.put(SUPPORT_ATTR_NAME_SRC, new SrcAttr());
        mSupportAttr.put(SUPPORT_ATTR_NAME_INDETERMINATE_DRAWABLE, new ProgressBarIndeterminateDrawableAttr());
        mSupportAttr.put(SUPPORT_ATTR_NAME_PROGRESS_DRAWABLE, new SeekBarProgressDrawableAttr());
    }


    public static SkinAttr get(String attrName, int attrValueRefId, String attrValueRefName, String typeName) {
        SkinL.i(TAG, "attrName:" + attrName);
        SkinAttr mSkinAttr = mSupportAttr.get(attrName).clone();
        if (mSkinAttr == null) return null;
        mSkinAttr.attrName = attrName;
        mSkinAttr.attrValueRefId = attrValueRefId;
        mSkinAttr.attrValueRefName = attrValueRefName;
        mSkinAttr.attrValueTypeName = typeName;
        return mSkinAttr;
    }

    /**
     *
     * Whether the test attribute is supported
     *
     * @param attrName
     * @return true : supported <br>
     * false: not supported
     */
    public static boolean isSupportedAttr(String attrName) {
        return mSupportAttr.containsKey(attrName);
    }

    /**
     * Increase the skin properties of support
     *
     * @param attrName
     * @param skinAttr custom attr.
     */
    public static void addSupportAttr(String attrName, SkinAttr skinAttr) {
        mSupportAttr.put(attrName, skinAttr);
    }
}
