package cn.dkm.gamehelper.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.dkm.gamehelper.R;


/**
 * Created by Administrator on 2018/1/17.
 */

public class UserItemView extends RelativeLayout {

    private static final String NAMESPACE = "http://schemas.android.com/apk/res/cn.dkm.gamehelper";

    private TextView mName;

    private ImageView mIcon;

    private String name;

    public UserItemView(Context context) {
        this(context,null);
    }

    public UserItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UserItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        View view = View.inflate(context, R.layout.item_setting_view, this);
        mName = view.findViewById(R.id.tv_name);
        mIcon = view.findViewById(R.id.iv_icon);

        name = attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "text");
        int src_resource = attrs.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "src", 0);

        mIcon.setImageBitmap(getDrawable(getResources(),src_resource));
        mName.setText(name);


    }

    public static Bitmap getDrawable(Resources res, int id){
        return BitmapFactory.decodeStream(res.openRawResource(id));
    }

}
