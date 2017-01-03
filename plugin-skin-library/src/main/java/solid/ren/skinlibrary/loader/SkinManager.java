package solid.ren.skinlibrary.loader;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import com.thin.downloadmanager.DefaultRetryPolicy;
import com.thin.downloadmanager.DownloadRequest;
import com.thin.downloadmanager.DownloadStatusListenerV1;
import com.thin.downloadmanager.ThinDownloadManager;
import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import solid.ren.skinlibrary.attr.ResourceType;
import solid.ren.skinlibrary.config.SkinConfig;
import solid.ren.skinlibrary.config.StatusConfig;
import solid.ren.skinlibrary.listener.ILoaderListener;
import solid.ren.skinlibrary.listener.ISkinLoader;
import solid.ren.skinlibrary.listener.ISkinUpdate;
import solid.ren.skinlibrary.utils.TypefaceUtils;
import solid.ren.skinlibrary.utils.SkinL;
import solid.ren.skinlibrary.utils.SkinFileUtils;


/**
 * Created by _SOLID
 * Date:2016/4/13
 * Time:21:07
 */
public class SkinManager implements ISkinLoader {

    private List<ISkinUpdate> mSkinObservers;
    private static volatile SkinManager mInstance;
    private Context context;
    private Resources mResources;
    //is default skin
    private boolean isDefaultSkin = false;
    //the skin package name
    private String skinPackageName;
    //the skin plugin path
    private String skinPath;
    //will be operation change skin ?
    private boolean intentChangeSkin;

    private SkinManager() {

    }

    public void init(Context ctx) {
        context = ctx.getApplicationContext();
        TypefaceUtils.CURRENT_TYPEFACE = TypefaceUtils.getTypeface(context);
    }

    public boolean isIntentChangeSkin() {
        return intentChangeSkin;
    }

    public Context getContext() {
        return context;
    }

    /**
     * get the status bar color
     * @return
     */
    public int getColorPrimaryDark() {
        if (mResources != null) {
            int identify = mResources.getIdentifier(StatusConfig.KEY_STATUS_BAR_COLOR, ResourceType.Color.getStr(), skinPackageName);
            if (!(identify <= 0)){
                return mResources.getColor(identify);
            }else{
                return getDefaultColor(StatusConfig.KEY_STATUS_BAR_COLOR);
            }
        }
        return -1;
    }

    /**
     * get default resource color by resource name.
     * @param resName
     * @return
     */
    public int getDefaultColor(String resName){
        Resources resources = context.getResources();
        int identify = resources.getIdentifier(resName,ResourceType.Color.getStr(),context.getPackageName());
        try {
            return resources.getColor(identify);
        }catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * is use dark status on status bar for Android M.
     * @return
     */
    public boolean getDarkStatus(){
        return getBool(StatusConfig.KEY_DARK_STATUS);
    }

    /**
     * //To determine whether the current skin from outside
     *
     * @return
     */
    public boolean isExternalSkin() {
        return !isDefaultSkin && mResources != null;
    }

    /**
     * get the  current skin path
     *
     * @return
     */
    public String getSkinPath() {
        return skinPath;
    }

    /**
     * get the current skin package name.
     *
     * @return
     */
    public String getSkinPackageName() {
        return skinPackageName;
    }

    public Resources getResources() {
        return mResources;
    }

    /**
     * Revert to the default theme
     */
    public void restoreDefaultTheme() {
        SkinConfig.saveSkinPath(context, SkinConfig.DEFAULT_SKIN);
        isDefaultSkin = true;
        mResources = context.getResources();
        skinPackageName = context.getPackageName();
        notifySkinUpdate();
    }

    public static SkinManager getInstance() {
        if (mInstance == null) {
            synchronized (SkinManager.class) {
                if (mInstance == null) {
                    mInstance = new SkinManager();
                }
            }
        }
        return mInstance;
    }

    @Override
    public void attach(ISkinUpdate observer) {
        if (mSkinObservers == null) {
            mSkinObservers = new ArrayList<>();
        }
        if (!mSkinObservers.contains(mSkinObservers)) {
            mSkinObservers.add(observer);
        }
    }

    @Override
    public void detach(ISkinUpdate observer) {
        if (mSkinObservers == null) return;
        if (mSkinObservers.contains(observer)) {
            mSkinObservers.remove(observer);
        }
    }

    @Override
    public void notifySkinUpdate() {
        if (mSkinObservers == null) return;
        intentChangeSkin = true;
        for (ISkinUpdate observer : mSkinObservers) {
            observer.onThemeUpdate();
        }
        intentChangeSkin = false;
    }

    public void loadSkin() {
        String skin = SkinConfig.getCustomSkinPath(context);
        if(SkinConfig.DEFAULT_SKIN.equals(skin)){
            skinPackageName = context.getPackageName();
            mResources = context.getResources();
        }
        loadSkin(skin, null);
    }

    public void loadSkin(ILoaderListener callback) {
        String skin = SkinConfig.getCustomSkinPath(context);
        if (SkinConfig.isDefaultSkin(context)) {
            return;
        }
        loadSkin(skin, callback);
    }

    /**
     * load skin form local
     * <p>
     * eg:theme.skin
     * </p>
     *
     * @param skinName the name of skin(in assets/skin)
     * @param callback load Callback
     */
    public void loadSkin(String skinName, final ILoaderListener callback) {
        if (callback != null) {
            callback.onStart();
        }
        try {
            if (!TextUtils.isEmpty(skinName)) {
                String skinPkgPath = SkinFileUtils.getSkinDir(context) + File.separator + skinName;

                String _skinPackageName = getSkinPackageName(skinName);

                if(_skinPackageName==null){
                    isDefaultSkin = true;
                    if (callback != null) callback.onFailed("没有获取到资源");
                    return;
                }

                skinPackageName = _skinPackageName;

                AssetManager assetManager = AssetManager.class.newInstance();
                Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
                addAssetPath.invoke(assetManager, skinPkgPath);

                Resources superRes = context.getResources();
                Resources skinResource = new Resources(assetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());

                SkinConfig.saveSkinPath(context, skinName);

                skinPath = skinPkgPath;
                isDefaultSkin = false;
                mResources = skinResource;

                if (mResources != null) {
                    if (callback != null) callback.onSuccess();
                    notifySkinUpdate();
                } else {
                    isDefaultSkin = true;
                    if (callback != null) callback.onFailed("没有获取到资源");
                }
            }else{
                isDefaultSkin = true;
                if (callback != null) callback.onFailed("没有获取到资源");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * get the skin resource package name.
     * @param skinName the skin file name.
     * @return
     */
    public String getSkinPackageName(String skinName){
        if(TextUtils.isEmpty(skinName)) return null;
        String skinPkgPath = SkinFileUtils.getSkinDir(context) + File.separator + skinName;
        SkinL.i("skinPkgPath", skinPkgPath);
        File file = new File(skinPkgPath);
        if (file == null || !file.exists()) {
            return null;
        }
        PackageManager mPm = context.getPackageManager();
        PackageInfo mInfo = mPm.getPackageArchiveInfo(skinPkgPath, PackageManager.GET_ACTIVITIES);
        return mInfo.packageName;
    }

    /**
     * load skin form internet
     * <p>
     * eg:https://raw.githubusercontent.com/burgessjp/ThemeSkinning/master/app/src/main/assets/skin/theme.skin
     * </p>
     *
     * @param skinUrl  the url of skin
     * @param callback load Callback
     */
    public void loadSkinFromUrl(String skinUrl, final ILoaderListener callback) {
        String skinPath = SkinFileUtils.getSkinDir(context);
        final String skinName = skinUrl.substring(skinUrl.lastIndexOf("/") + 1);
        String skinFullName = skinPath + File.separator + skinName;
        File skinFile = new File(skinFullName);
        if (skinFile.exists()) {
            loadSkin(skinName, callback);
            return;
        }

        Uri downloadUri = Uri.parse(skinUrl);
        Uri destinationUri = Uri.parse(skinFullName);

        DownloadRequest downloadRequest = new DownloadRequest(downloadUri)
                .setRetryPolicy(new DefaultRetryPolicy())
                .setDestinationURI(destinationUri)
                .setPriority(DownloadRequest.Priority.HIGH);
        callback.onStart();
        downloadRequest.setStatusListener(new DownloadStatusListenerV1() {
            @Override
            public void onDownloadComplete(DownloadRequest downloadRequest) {
                loadSkin(skinName, callback);
            }

            @Override
            public void onDownloadFailed(DownloadRequest downloadRequest, int errorCode, String errorMessage) {
                callback.onFailed(errorMessage);
            }

            @Override
            public void onProgress(DownloadRequest downloadRequest, long totalBytes, long downloadedBytes, int progress) {
                callback.onProgress(progress);
            }
        });

        ThinDownloadManager manager = new ThinDownloadManager();
        manager.add(downloadRequest);


    }

    public void loadFont(String fontName) {
        Typeface tf = TypefaceUtils.createTypeface(context, fontName);
        FontManager.getInstance().notifyFontUpdate(tf);
    }

    /**
     * in current resource , get color by resource name.
     * @param resName
     * @return if resource null , return default resource color.
     */
    public int getColorByResName(String resName){
        if(mResources == null || isDefaultSkin){
            int originColor = context.getResources().getIdentifier(resName,ResourceType.Color.getStr(),skinPackageName);
            return originColor;
        }
        int trueResId = mResources.getIdentifier(resName, ResourceType.Color.getStr(), skinPackageName);
        int trueColor = 0;
        try {
            trueColor = mResources.getColor(trueResId);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        return trueColor;
    }

    /**
     * in current resource , get color by resource id.
     * @param resId
     * @return if resource null , return default resource color.
     */
    public int getColor(int resId) {
        int originColor = context.getResources().getColor(resId);
        if (mResources == null || isDefaultSkin) {
            return originColor;
        }

        String resName = context.getResources().getResourceEntryName(resId);

        return getColorByResName(resName);
    }

    /**
     * in current resource , get bool value by resource name.
     * @param resName
     * @return if resource null , return false.
     */
    public boolean getBool(String resName) {
        if(mResources == null || skinPackageName == null){
            return false;
        }
        int id = mResources.getIdentifier(resName,ResourceType.Bool.getStr(),skinPackageName);
        boolean result = false;
        try{
            result = mResources.getBoolean(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * in current resource , get drawable by resource id.
     * @param resId
     * @return if resource null , return default resource drawable.
     */
    public Drawable getDrawable(ResourceType resourceType,int resId) {
        Drawable originDrawable = context.getResources().getDrawable(resId);
        if (mResources == null || isDefaultSkin) {
            return originDrawable;
        }
        String resName = context.getResources().getResourceEntryName(resId);

        int trueResId = mResources.getIdentifier(resName, resourceType.getStr(), skinPackageName);

        Drawable trueDrawable = null;
        try {
            SkinL.i("SkinManager getDrawable", "SDK_INT = " + android.os.Build.VERSION.SDK_INT);
            if (android.os.Build.VERSION.SDK_INT < 22) {
                trueDrawable = mResources.getDrawable(trueResId);
            } else {
                trueDrawable = mResources.getDrawable(trueResId, null);
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            trueDrawable = originDrawable;
        }

        return trueDrawable;
    }

    /**
     *
     * Load the specified resource color drawable, into ColorStateList,
     * to ensure that the selector type of Color can also be converted.
     *
     * No skin pack resources return the default theme colors
     *
     * @param resId
     * @return
     * @author pinotao
     */
    public ColorStateList getColorStateList(ResourceType resourceType, int resId) {
        boolean isExtendSkin = true;
        if (mResources == null || isDefaultSkin) {
            isExtendSkin = false;
        }

        String resName = context.getResources().getResourceEntryName(resId);
        if (isExtendSkin) {
            int trueResId = mResources.getIdentifier(resName, resourceType.getStr(), skinPackageName);
            ColorStateList trueColorList = null;
            //If the package is not the skin of carbon resources,
            // but the need to determine whether it is ColorStateList
            if (trueResId == 0) {
                try {
                    ColorStateList originColorList = context.getResources().getColorStateList(resId);
                    return originColorList;
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                    SkinL.e("resName = " + resName + " NotFoundException : " + e.getMessage());
                }
            } else {
                try {
                    trueColorList = mResources.getColorStateList(trueResId);
                    return trueColorList;
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                    SkinL.e("resName = " + resName + " NotFoundException :" + e.getMessage());
                }
            }
        } else {
            try {
                ColorStateList originColorList = context.getResources().getColorStateList(resId);
                return originColorList;
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
                SkinL.e("resName = " + resName + " NotFoundException :" + e.getMessage());
            }

        }

        int[][] states = new int[1][1];
        return new ColorStateList(states, new int[]{context.getResources().getColor(resId)});
    }
}
