package com.kushyk.paint.ui;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.jrummyapps.android.colorpicker.ColorPickerDialog;
import com.jrummyapps.android.colorpicker.ColorPickerDialogListener;
import com.kushyk.paint.App;
import com.kushyk.paint.R;
import com.kushyk.paint.manager.path.LineManager;
import com.kushyk.paint.manager.path.OvalManager;
import com.kushyk.paint.manager.path.PencilManager;
import com.kushyk.paint.manager.path.RectangleManager;
import com.kushyk.paint.manager.path.StratchManager;
import com.kushyk.paint.ui.view.PaintView;
import com.kushyk.paint.util.IntentUtil;
import com.kushyk.paint.util.PermissionUtil;
import com.kushyk.paint.util.file.FileUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements ColorPickerDialogListener {
    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    public static final int PICK_IMAGE_REQUEST = 7;
    public static final int PERMISSION_REQUEST_CODE = 11;
    @BindView(R.id.canvasView)
    PaintView canvasView;

    @BindView(R.id.sizeAction)
    Button sizeAction;

    @BindView(R.id.chooseColorAction)
    ImageView chooseColorAction;

    private PopupMenu sizePopUpMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUnbinder(ButterKnife.bind(this));

        initPainManager();
        initSizeView();
    }

    private void initPainManager() {
        canvasView.setManager(App.getManager());
        chooseColorAction.setColorFilter(App.getManager().getCurrentColor());
    }

    private void initSizeView() {
        sizePopUpMenu = getSizePopupMenu(sizeAction);
        sizeAction.setText(R.string.size_4);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            loadImage(uri);
        }
    }

    private void loadImage(Uri uri) {
        Observable.fromCallable(()->  Glide.with(this).load(uri).asBitmap().into(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL).get())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::onSuccessLoadImage, this::onErrorLoadImage) ;
    }

    private void onSuccessLoadImage(Bitmap bitmap) {
        canvasView.drawBitmap(bitmap);
    }

    private void onErrorLoadImage(Throwable throwable) {
        Log.e(LOG_TAG, "onErrorSaveImage() " + throwable.getMessage());
        showToast(R.string.cant_load_image);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE
                && grantResults.length> 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            saveImage();
        }
    }

    @OnClick(R.id.pencilAction)
    public void drawPencil() {
        App.getManager().setColor(App.getManager().getCurrentColor());
        App.getManager().setManager(new PencilManager());
    }

    @OnClick(R.id.squareAction)
    public void drawSquare() {
        App.getManager().setColor(App.getManager().getCurrentColor());
        App.getManager().setManager(new RectangleManager());
    }

    @OnClick(R.id.circleAction)
    public void circleAction() {
        App.getManager().setColor(App.getManager().getCurrentColor());
        App.getManager().setManager(new OvalManager());
    }

    @OnClick(R.id.lineAction)
    public void lineAction() {
        App.getManager().setColor(App.getManager().getCurrentColor());
        App.getManager().setManager(new LineManager());
    }

    @OnClick(R.id.stretchAction)
    public void stretchAction() {
        App.getManager().setStretchColor(PaintView.BACKGROUND_COLOR);
        App.getManager().setManager(new StratchManager());
    }
    @OnClick(R.id.clearAction)
    public void clearCanvas() {
        canvasView.clearCanvas();
    }

    @OnClick(R.id.previousAction)
    public void previousAction() {
        canvasView.revertTo();
    }

    @OnClick(R.id.chooseColorAction)
    public void chooseColorAction() {
        ColorPickerDialog.newBuilder()
                .show(this);
    }

    @OnClick(R.id.sizeAction)
    public void sizeAction(View v) {
        sizePopUpMenu.show();
    }

    @OnClick(R.id.getImageAction)
    public void getImageAction() {
        getImage();
    }

    @OnClick(R.id.saveImageAction)
    public void saveImageAction() {
        saveImage();
    }

    private void saveImage() {
        if (!PermissionUtil.checkWritePermission(this, PERMISSION_REQUEST_CODE)) {
            return;
        }
        canvasView.setDrawingCacheEnabled(true);
        canvasView.buildDrawingCache();
        Bitmap bitmap = canvasView.getDrawingCache();
        FileUtil.saveImage(bitmap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccessSaveImage, this::onErrorSaveImage);
    }

    private void onSuccessSaveImage(Boolean saved) {
        showToast(R.string.success_save_image);
    }

    private void onErrorSaveImage(Throwable throwable) {
        Log.e(LOG_TAG, "onErrorSaveImage() " + throwable.getMessage());
        showToast(R.string.cant_save_image);
    }

    @Override
    public void onColorSelected(int dialogId, @ColorInt int color) {
        chooseColorAction.setColorFilter(color);
        App.getManager().setColor(color);
    }

    @Override
    public void onDialogDismissed(int dialogId) {

    }

    private void getImage() {
        startActivityForResult(
                Intent.createChooser(
                        IntentUtil.getImageIntent(),
                        getString(R.string.select_picture)),
                PICK_IMAGE_REQUEST);
    }

    public PopupMenu getSizePopupMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
        popupMenu.inflate(R.menu.size_menu);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_2:
                        App.getManager().setSize(R.dimen.size_2);
                        sizeAction.setText(R.string.size_2);
                        return true;
                    case R.id.item_4:
                        App.getManager().setSize(R.dimen.size_4);
                        sizeAction.setText(R.string.size_4);
                    case R.id.item_8:
                        App.getManager().setSize(R.dimen.size_8);
                        sizeAction.setText(R.string.size_8);
                        return true;
                    case R.id.item_16:
                        App.getManager().setSize(R.dimen.size_16);
                        sizeAction.setText(R.string.size_16);
                        return true;
                    case R.id.item_24:
                        App.getManager().setSize(R.dimen.size_24);
                        sizeAction.setText(R.string.size_24);
                        return true;
                    default:
                        return false;
                }
            }
        });
        return popupMenu;
    }

    private void showToast(@StringRes int stringRes) {
        Toast.makeText(this, stringRes, Toast.LENGTH_LONG).show();
    }
}
