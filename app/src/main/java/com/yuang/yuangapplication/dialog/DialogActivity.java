package com.yuang.yuangapplication.dialog;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yalantis.ucrop.UCrop;
import com.yuang.library.base.BaseActivity;
import com.yuang.library.glide.GlideCircleTransform;
import com.yuang.library.utils.Logg;
import com.yuang.library.utils.YPhotoUtils;
import com.yuang.library.utils.YUtils;
import com.yuang.library.widget.dialog.YDialogChooseImage;
import com.yuang.yuangapplication.R;
import com.yuang.yuangapplication.utils.GlideApp;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.yuang.library.widget.dialog.YDialogChooseImage.LayoutType.TITLE;

public class DialogActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.choose_photo)
    CardView choosePhoto;
    @BindView(R.id.choose_image)
    ImageView chooseImage;

    @Override
    public int getLayoutId() {
        return R.layout.activity_dialog;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setToolBar(toolbar, "Dialog", false);
    }

    @OnClick(R.id.choose_photo)
    public void onViewClicked() {
        YDialogChooseImage dialogChooseImage = new YDialogChooseImage(this, TITLE);
        dialogChooseImage.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case YPhotoUtils.GET_IMAGE_FROM_PHONE://选择相册之后的处理
                if (resultCode == RESULT_OK) {
                    Logg.e("选择相册后回调");
                    YUtils.getInstance().initUCrop(this, data.getData());
                }

                break;
            case YPhotoUtils.GET_IMAGE_BY_CAMERA://选择照相机之后的处理
                if (resultCode == RESULT_OK) {
                    YUtils.getInstance().initUCrop(this, YPhotoUtils.imageUriFromCamera);
                }

                break;
            case YPhotoUtils.CROP_IMAGE://普通裁剪后的处理
                GlideApp.with(mContext).
                        load(YPhotoUtils.cropImageUri).
                        diskCacheStrategy(DiskCacheStrategy.RESOURCE).
                        transform(new GlideCircleTransform(mContext)).
                        thumbnail(0.5f).
                        priority(Priority.LOW).
                        dontAnimate().
                        into(chooseImage);
                break;

            case UCrop.REQUEST_CROP://UCrop裁剪之后的处理
                if (resultCode == RESULT_OK) {
                    Uri resultUri = UCrop.getOutput(data);
                    File my_avatar = new File(YPhotoUtils.getImageAbsolutePath(this, resultUri));
                    GlideApp.with(this).load(my_avatar).into(chooseImage);
                    RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/*"), my_avatar);
//                    MultipartBody.Part photoParts[] = {MultipartBody.Part.createFormData("upload", my_avatar.getName(),
//                            photoRequestBody)};
                    MultipartBody.Part photoPart = MultipartBody.Part.createFormData("upload[]", my_avatar.getName(),
                            photoRequestBody);
                    //上传到服务器
//                    mPresenter.uploadFile(photoPart);
                } else if (resultCode == UCrop.RESULT_ERROR) {
                    final Throwable cropError = UCrop.getError(data);
                }
                break;
            case UCrop.RESULT_ERROR://UCrop裁剪错误之后的处理
                final Throwable cropError = UCrop.getError(data);
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
