package solid.ren.skinlibrary.listener;

/**
 * <p>
 * Created by _SOLID
 * Date:2016/4/13
 * Time:21:07
 * </p>
 *
 * Callback interface when loading the skin
 *
 */
public interface ILoaderListener {
    /**
     * on start change skin
     */
    void onStart();

    /**
     * on success change skin
     */
    void onSuccess();

    /**
     * on failure change skin
     * @param errMsg
     */
    void onFailed(String errMsg);

    /**
     * When loading the skin file from the network, this method will be called
     *
     * @param progress
     */
    void onProgress(int progress);
}
