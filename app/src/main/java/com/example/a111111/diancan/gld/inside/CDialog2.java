package com.example.a111111.diancan.gld.inside;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a111111.diancan.CustomDialog;
import com.example.a111111.diancan.R;
import com.example.a111111.diancan.gld.manager;
import static com.example.a111111.diancan.gld.inside.cdmanager.wait;
import static com.example.a111111.diancan.gld.inside.cdmanager.bitlog;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class CDialog2 extends Dialog {

    public CDialog2(Context context) {
        super(context);
    }

    public CDialog2(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        final Handler handed = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                Log.d("ooook", "handleMessage: ");
                    if(wait==1){
                        c1=bitlog;
                        ((ImageView)layout.findViewById(R.id.lv)).setImageBitmap(bitlog);
                    }

            }
        };
        Dialog mCameraDialog;
        private Context context;
        private String title,name,price,desc;
        private String message;
        private String positiveButtonText;
        private String negativeButtonText;
        private View contentView;
        private Bitmap c1;
        String file_str = Environment.getExternalStorageDirectory().getPath();
        File mars_file = new File(file_str + "/my_camera");
        File file_go= new File(file_str + "/my_camera/file.jpg");
        private View layout;
        private OnClickListener positiveButtonClickListener;
        private OnClickListener negativeButtonClickListener;

        public Builder(Context context) {
            this.context = context;
        }






        /**
         * Set the Dialog title from resource
         *
         * @param title
         * @return
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        /**
         * Set the Dialog title from String
         *
         * @param title
         * @return
         */

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }
        public Builder setpp(Bitmap c1) {
            this.c1 = c1;
            return this;
        }
        public Builder setname(String name) {
            this.name = name;
            return this;
        }
        public Builder setprice(String price) {
            this.price = price;
            return this;
        }
        public Builder setdesc(String desc) {
            this.desc = desc;
            return this;
        }
        /**
         * Set the positive button resource and it's listener
         *
         * @param positiveButtonText
         * @return
         */
        public Builder setPositiveButton(int positiveButtonText,
                                         OnClickListener listener) {
            this.positiveButtonText = (String) context
                    .getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText,
                                         OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }
        public String getname()
        {
            return ((EditText)layout.findViewById(R.id.name)).getText().toString();
        }
        public String getprice()
        {
            return ((EditText)layout.findViewById(R.id.price)).getText().toString();
        }
        public String getdesc()
        {
            return ((EditText)layout.findViewById(R.id.desc)).getText().toString();
        }
        public String Bitmap2StrByBase64(Bitmap bit){
            ByteArrayOutputStream bos=new ByteArrayOutputStream();
            bit.compress(Bitmap.CompressFormat.JPEG, 40, bos);//参数100表示不压缩
            byte[] bytes=bos.toByteArray();
            return Base64.encodeToString(bytes, Base64.NO_WRAP);
        }

        public String getbit()
        {
            return Bitmap2StrByBase64(c1);
        }

        public Bitmap getC1() {
            return c1;
        }

        public Builder setNegativeButton(int negativeButtonText,
                                         OnClickListener listener) {
            this.negativeButtonText = (String) context
                    .getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText,
                                         OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }
        private void delay(int ms){
            try {
                Thread.currentThread();
                Thread.sleep(ms);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        public void downcd(){
            mCameraDialog = new Dialog(context, R.style.my_dialog);
            LinearLayout root = (LinearLayout) LayoutInflater.from(context).inflate(
                    R.layout.layout_camera_control, null);
            root.findViewById(R.id.btn_open_camera).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
// TODO Auto-generated method stub

// 验证sd卡是否正确安装：
                    if (Environment.MEDIA_MOUNTED.equals(Environment
                            .getExternalStorageState())) {
// 先创建父目录，如果新创建一个文件的时候，父目录没有存在，那么必须先创建父目录，再新建文件。
                        if (!mars_file.exists()) {
                            mars_file.mkdirs();
                        }

                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
// 并设置拍照的存在方式为外部存储和存储的路径；
                        file_go= new File(file_str + "/my_camera/file.jpg");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(file_go));
//跳转到拍照界面;
                        wait=0;
                        ((cdmanager)context).startActivityForResult(intent, 0x1);

                        new Thread() {
                            public void run() {
                                try {
                                    while (wait == 0) {
                                        sleep(1000);
                                    }
                                    handed.sendEmptyMessage(1);
                                } catch (Exception e) {
                                }
                            }
                        }.start();
                    } else {
                        Toast.makeText(context, "请先安装好sd卡",
                                Toast.LENGTH_LONG).show();
                    }
                    mCameraDialog.dismiss();
                }
            });

            root.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
// TODO Auto-generated method stub

// 验证sd卡是否正确安装：
                    mCameraDialog.dismiss();
                }
            });
            mCameraDialog.setContentView(root);
            Window dialogWindow = mCameraDialog.getWindow();
            dialogWindow.setGravity(Gravity.BOTTOM);
            dialogWindow.setWindowAnimations(R.style.dialogstyle); // 添加动画
            WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
            lp.x = 0; // 新位置X坐标
            lp.y = -40; // 新位置Y坐标
            lp.width = (int) ((cdmanager)context).getResources().getDisplayMetrics().widthPixels; // 宽度
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT; // 高度
            root.measure(0, 0);
            lp.alpha = 9f; // 透明度
            dialogWindow.setAttributes(lp);
            mCameraDialog.show();
        }
        public CustomDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final CustomDialog dialog = new CustomDialog(context,R.style.Dialog);
            layout = inflater.inflate(R.layout.dnlayout2, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            // set the dialog title
            ((TextView) layout.findViewById(R.id.title)).setText(title);
            ((EditText)layout.findViewById(R.id.name)).setText(name);
            ((EditText)layout.findViewById(R.id.price)).setText(price);
            ((EditText)layout.findViewById(R.id.desc)).setText(desc);
            ((ImageView)layout.findViewById(R.id.lv)).setImageBitmap(c1);
            ((ImageView)layout.findViewById(R.id.lv)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    downcd();
                }
            });
            // set the confirm button
            if (positiveButtonText != null) {
                ((Button) layout.findViewById(R.id.positiveButton))
                        .setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.positiveButton))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    positiveButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_POSITIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.positiveButton).setVisibility(
                        View.GONE);
            }
            // set the cancel button
            if (negativeButtonText != null) {
                ((Button) layout.findViewById(R.id.negativeButton))
                        .setText(negativeButtonText);
                if (negativeButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.negativeButton))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    negativeButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_NEGATIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.negativeButton).setVisibility(
                        View.GONE);
            }
            // set the content message

            dialog.setContentView(layout);
            return dialog;
        }
    }
}

