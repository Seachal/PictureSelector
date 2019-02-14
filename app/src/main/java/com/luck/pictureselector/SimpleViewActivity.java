package com.luck.pictureselector;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

/**
 * 条码扫描
 */
public class SimpleViewActivity extends Activity implements QRCodeView.Delegate, View.OnClickListener {
    private static final String TAG = SimpleViewActivity.class.getSimpleName();



//    ImageButton bt_switch_flash_light;
    //闪光灯是否打开 默认不打开
    private boolean is_light_on = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_view);
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
        Log.i(TAG, "onStart"+System.currentTimeMillis());


    }

    @Override
    protected void onResume() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    Thread.sleep(3000);

                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                Log.i(TAG, "Resume3000"+System.currentTimeMillis());
            }
        }).start();
        super.onResume();
        Log.i(TAG, "onResume"+System.currentTimeMillis());
    }

    private void  resume(){
        super.onResume();
    }

    @Override
    public void onStop() {

        Log.i(TAG, "onStop"+System.currentTimeMillis());
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy"+System.currentTimeMillis());

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



    /**
     * 从相册选择图片
     */
    private void selectPhotoFromGallery() {
        PictureSelector.create(SimpleViewActivity.this)
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