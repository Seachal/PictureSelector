package com.luck.pictureselector;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

/**
 * 条码扫描
 */
public class ScanBarCodeActivity extends AppCompatActivity implements QRCodeView.Delegate, View.OnClickListener {
    private static final String TAG = ScanBarCodeActivity.class.getSimpleName();


    QRCodeView mQRCodeView;
//    ImageButton bt_switch_flash_light;
    //闪光灯是否打开 默认不打开
    private boolean is_light_on = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_barcode);
        mQRCodeView = (ZXingView) findViewById(R.id.zxingview);
        mQRCodeView.setDelegate(this);
//        bt_switch_flash_light = findViewById(R.id.bt_switch_flash_light);
//        bt_switch_flash_light.setOnClickListener(this);
//        findViewById(R.id.bt_back).setOnClickListener(this);
        findViewById(R.id.bt_select_picture_from_gallery).setOnClickListener(this);
        findViewById(R.id.bt_intelligent_identification).setOnClickListener(this);
//        mDeCodeBarCodePresenter = new DeCodeBarCodePresenter(this);
        Log.i(TAG, "onCreate"+System.currentTimeMillis());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart1:"+System.currentTimeMillis());
        mQRCodeView.startCamera();
        mQRCodeView.showScanRect();
        Log.i(TAG, "onStart2:"+System.currentTimeMillis());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume1:"+System.currentTimeMillis());
        new Thread(new Runnable() {
            @Override
            public void run() {
                mQRCodeView.startSpotDelay(10);
            }
        }).start();
        Log.i(TAG, "onResume2:"+System.currentTimeMillis());
    }


    @Override
    public void onStop() {
        mQRCodeView.stopCamera();
        Log.i(TAG, "onStop"+System.currentTimeMillis());
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy"+System.currentTimeMillis());
        mQRCodeView.onDestroy();
        super.onDestroy();
    }





//    private void vibrate() {
//        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            vibrator.vibrate(VibrationEffect.createOneShot(100, 100));
//        } else {
//            vibrator.vibrate(200);
//        }
//    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        Log.i(TAG, "result:" + result);
       Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        Bundle bundle = new Bundle();
//        vibrate();
       mQRCodeView.startSpot();
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e(TAG, "打开相机出错");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.bt_switch_flash_light:
//                switchFlashLigth();
//                break;
//            case R.id.bt_back:
//                mQRCodeView.onDestroy();
//                finish();
//                break;
            case R.id.bt_select_picture_from_gallery:
                selectPhotoFromGallery();
                mQRCodeView.stopCamera();
                break;
//            case R.id.bt_intelligent_identification:
//                Intent intent = new Intent(ScanBarCodeActivity.this, IntelligentIdentificationActivity.class);
//                startActivity(intent);
//                finish();
            default:
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("tagA1", System.currentTimeMillis() + "");
        super.onActivityResult(requestCode, resultCode, data);
        List<LocalMedia> selectList = new ArrayList<>();
        if (resultCode == RESULT_OK && requestCode == PictureConfig.CHOOSE_REQUEST) {
            // 图片选择结果回调
            selectList = PictureSelector.obtainMultipleResult(data);
            final String picturePath = selectList.get(0).getPath();
            Log.i("tagA2", System.currentTimeMillis() + "");
//
//       mDeCodeBarCodePresenter.getDecodeResult(picturePath);
        }
//        mQRCodeView.showScanRect();
        Log.i("tagA4", System.currentTimeMillis() + "");
    }




//    /**
//     * 从相册选择图片的回调
//     *
//     * @param callId 调用id，唯一标识业务
//     * @param result 通过网络请求的结果数据
//     * @param args   其他额外的参数
//     */
//    @Override
//    public void callView(int callId, OperateResult result, Object... args) {
//        String resultString = (String) args[0];
//        if (callId == DeCodeBarCodePresenter.DECODE_RESULT) {
//            if (StringUtils.isEmpty(resultString)) {
//                Toast.makeText(this, "未发现二维码", Toast.LENGTH_SHORT).show();
//                mQRCodeView.startCamera();
//            } else {
//                Toast.makeText(this, resultString, Toast.LENGTH_SHORT).show();
//                mQRCodeView.stopCamera();
//                Bundle bundle = new Bundle();
//                bundle.putString(DeepConfig.BAR_CODE_KEY, resultString);
//                PageInfo pageInfo = new PageInfo("详细", CigaretteDetialFragment.class);
//                JumpPage.jump(ScanBarCodeActivity.this, pageInfo, bundle);
//            }
//        }
////      mQRCodeView.startCamera();
//    }


    /**
     * 从相册选择图片
     */
    private void selectPhotoFromGallery() {
        PictureSelector.create(ScanBarCodeActivity.this)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
//               .theme(themeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                .maxSelectNum(1)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
                .previewImage(true)// 是否可预览图片
                .previewVideo(false)// 是否可预览视频
                .enablePreviewAudio(false) // 是否可播放音频
                .isCamera(false)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .enableCrop(false)// 是否裁剪
                .compress(false)// 是否压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .isGif(false)// 是否显示gif图片
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code

    }




}