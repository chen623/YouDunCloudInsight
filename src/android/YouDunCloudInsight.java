package com.longyang.cordova.youdun.cloudinsight;

import com.authreal.api.AuthBuilder;
import com.authreal.api.FVSdk;
import com.authreal.api.OnResultListener;
import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * 有盾云慧眼
 */
public class YouDunCloudInsight extends CordovaPlugin {

  private static boolean IS_SHOW_CONFIRM = true;
  private static FVSdk.FVSafeMode SAFE_MODE = FVSdk.FVSafeMode.FVSafeMediumMode;
  private static boolean IS_MANUAL_OCR = true;

  @Override
  public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    LOG.d("", "有盾云慧眼插件初始化");
  }

  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

    if ("faceAuth".equals(action)) {
      LOG.d("", "启动认证流程");
      JSONObject jsonObject = args.getJSONObject(0);
      this.faceAuth(jsonObject, callbackContext);
      return true;
    }
    if ("isManualOcr".equals(action)) {
      LOG.d("", "设置手动拍照功能是否关闭，默认为打开");
      JSONObject jsonObject = args.getJSONObject(0);
      this.isManualOcr(jsonObject, callbackContext);
      return true;
    }
    if ("setFVSafeMode".equals(action)) {
      LOG.d("", "设置活体难度");
      JSONObject jsonObject = args.getJSONObject(0);
      this.setFVSafeMode(jsonObject, callbackContext);
      return true;
    }
    if ("isShowConfirm".equals(action)) {
      LOG.d("", "设置是否展示姓名核验详情页");
      JSONObject jsonObject = args.getJSONObject(0);
      this.isShowConfirm(jsonObject, callbackContext);
      return true;
    }
    return false;
  }

  /**
   * 启动认证流程
   *
   * @param jsonObject      jsonObject
   * @param callbackContext callbackContext
   */
  private void faceAuth(JSONObject jsonObject, CallbackContext callbackContext) {
    String outOrderId = jsonObject.optString("outOrderId", System.currentTimeMillis() + "");
    String authKey = jsonObject.optString("authKey");
    String urlNotify = jsonObject.optString("urlNotify", "https://www.baidun.com");

    AuthBuilder authBuilder = new AuthBuilder(outOrderId, authKey, urlNotify, new OnResultListener() {
      @Override
      public void onResult(String s) {
        try {
          callbackContext.success(new JSONObject(s));
        } catch (JSONException e) {
          callbackContext.success();
        }
      }
    });

//    重新设置
    IS_MANUAL_OCR = jsonObject.optBoolean("isManualOCR", IS_MANUAL_OCR);
    String fvSafeModeStr = jsonObject.optString("FVSafeMode", "FVSafeMediumMode");
    FVSdk.FVSafeMode fvSafeMode = null;
    try {
      fvSafeMode = FVSdk.FVSafeMode.valueOf(fvSafeModeStr);
    } catch (IllegalArgumentException e) {
      LOG.d("", "活体难度参数错误", e);
      callbackContext.error(fvSafeModeStr + "没有这个枚举值");
    }
    if (fvSafeMode != null) {
      SAFE_MODE = fvSafeMode;
    }
    IS_SHOW_CONFIRM = jsonObject.optBoolean("isShowConfirm", IS_SHOW_CONFIRM);

    authBuilder.isManualOCR(IS_MANUAL_OCR);
    authBuilder.setFVSafeMode(SAFE_MODE);
    authBuilder.isShowConfirm(IS_SHOW_CONFIRM);

    //下文调用方法做为范例，请以对接文档中的调用方法为准
    authBuilder.faceAuth(this.cordova.getContext());
  }

  /**
   * 设置手动拍照功能是否关闭，默认为打开
   *
   * @param jsonObject      jsonObject
   * @param callbackContext callbackContext
   */
  private void isManualOcr(JSONObject jsonObject, CallbackContext callbackContext) {
    IS_MANUAL_OCR = jsonObject.optBoolean("isManualOCR", IS_MANUAL_OCR);
    callbackContext.success();
  }

  /**
   * 设置活体难度
   *
   * @param jsonObject      jsonObject
   * @param callbackContext callbackContext
   */
  private void setFVSafeMode(JSONObject jsonObject, CallbackContext callbackContext) {
    String fvSafeModeStr = jsonObject.optString("FVSafeMode", "FVSafeMediumMode");
    FVSdk.FVSafeMode fvSafeMode = null;
    try {
      fvSafeMode = FVSdk.FVSafeMode.valueOf(fvSafeModeStr);
    } catch (IllegalArgumentException e) {
      LOG.d("", "活体难度参数错误", e);
      callbackContext.error(fvSafeModeStr + "没有这个枚举值");
    }
    if (fvSafeMode != null) {
      SAFE_MODE = fvSafeMode;
    }
    callbackContext.success();
  }

  /**
   * 设置是否展示姓名核验详情页
   *
   * @param jsonObject      jsonObject
   * @param callbackContext callbackContext
   */
  private void isShowConfirm(JSONObject jsonObject, CallbackContext callbackContext) {
    IS_SHOW_CONFIRM = jsonObject.optBoolean("isShowConfirm", IS_SHOW_CONFIRM);
    callbackContext.success();
  }

}
