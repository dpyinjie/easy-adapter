package dpyinjie.adapter.holder;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.FloatRange;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TextView;

import dpyinjie.adapter.Utils;

public class RecHolder extends RecyclerView.ViewHolder implements HoldAble {

    private Context mContext;
    private View mConvertView;
    private SparseArray<View> mViewArray;

    /**
     * @param context
     * @param parent
     * @param layoutId
     * @return
     */
    public static RecHolder createHolder(Context context, ViewGroup parent, @LayoutRes int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        RecHolder holder = new RecHolder(context, itemView);
        return holder;
    }


    private RecHolder(Context context, View itemView) {
        super(itemView);
        mContext = context;
        mConvertView = itemView;
        mViewArray = new SparseArray<>();

        mConvertView.setTag(this);
    }

    @Override
    public <T extends View> T getView(int viewId) {
        View view = mViewArray.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViewArray.put(viewId, view);
        }
        return (T) view;
    }

    @Override
    public RecHolder setText(int viewId, CharSequence text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    @Override
    public RecHolder setImageResource(int viewId, int drawableId) {
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);
        return this;
    }

    @Override
    public HoldAble setImageURI(@IdRes int viewId, @Nullable Uri uri) {
        ImageView imageView = getView(viewId);
        imageView.setImageURI(uri);
        return this;
    }


    @Override
    public HoldAble setImageUrl(@IdRes int viewId, @Nullable String url) {
        // TODO: 2017/2/20 加载网络图片
        return this;
    }

    @Override
    public HoldAble setScaleType(@IdRes int viewId, ImageView.ScaleType scaleType) {
        ImageView imageView = getView(viewId);
        imageView.setScaleType(scaleType);
        return this;
    }

    @Override
    public RecHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    @Override
    public RecHolder setVisibility(int viewId, int visibility) {
        View view = getView(viewId);
        view.setVisibility(visibility);
        return this;
    }

    @Override
    public RecHolder setOnClickListener(int viewId, OnClickListener clickListener) {
        View view = getView(viewId);
        view.setOnClickListener(clickListener);
        return this;
    }

    @Override
    public View getConvertView() {
        return mConvertView;
    }

    @Override
    public HoldAble setText(int viewId, int textResId) {
        TextView view = getView(viewId);
        view.setText(textResId);
        return this;
    }

    @Override
    public HoldAble setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    @Override
    public HoldAble setEnabled(int viewId, boolean enable) {
        getView(viewId).setEnabled(enable);
        return this;
    }

    @Override
    public HoldAble setBackgroundColorRes(int viewId, int colorRes) {
        View view = getView(viewId);
        view.setBackgroundColor(ContextCompat.getColor(mContext, colorRes));
        return this;
    }

    @Override
    public HoldAble setBackgroundColorInt(@IdRes int viewId, @ColorInt int colorInt) {
        View view = getView(viewId);
        view.setBackgroundColor(colorInt);
        return this;
    }

    @Override
    public HoldAble setBackgroundResource(int viewId, int drawable) {
        getView(viewId).setBackgroundResource(drawable);
        return this;
    }

    @SuppressWarnings("deprecation")
    @Override
    public HoldAble setBackgroundDrawable(int viewId, Drawable drawable) {
        getView(viewId).setBackgroundDrawable(drawable);
        return this;
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    @Override
    public HoldAble setBackground(int viewId, Drawable drawable) {
        View view = getView(viewId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
        return this;
    }

    @Override
    public HoldAble setViewTag(int viewId, Object tag) {
        getView(viewId).setTag(tag);
        return this;
    }

    @Override
    public HoldAble setViewTag(int viewId, int key, Object tag) {
        getView(viewId).setTag(key, tag);
        return this;
    }

    @Override
    public HoldAble setChecked(int viewId, boolean checked) {
        View view = getView(viewId);
        if (Checkable.class.isInstance(view)) {
            ((Checkable) view).setChecked(checked);
        }
        return this;
    }

    @Override
    public boolean isChecked(int viewId) {
        View view = getView(viewId);
        if (Checkable.class.isInstance(view)) {
            return ((Checkable) view).isChecked();
        }
        return false;
    }

    @Override
    public HoldAble toggleCheckState(int viewId) {
        View view = getView(viewId);
        if (Checkable.class.isInstance(view)) {
            setChecked(viewId, !isChecked(viewId));
        }
        return this;
    }

    @Override
    public HoldAble setTextColorRes(int viewId, int colorRes) {
        TextView view = getView(viewId);
        view.setTextColor(ContextCompat.getColor(mContext, colorRes));
        return this;
    }

    @Override
    public HoldAble setTextSizePx(@IdRes int viewId, float pxSize) {
        TextView view = getView(viewId);
        view.setTextSize(pxSize);
        return this;
    }

    @Override
    public HoldAble setCompoundDrawablesWithIntrinsicBounds(@IdRes int viewId, @DrawableRes int left, @DrawableRes int top, @DrawableRes int right, @DrawableRes int bottom) {
        TextView view = getView(viewId);
        view.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        return null;
    }

    @Override
    public HoldAble setCompoundDrawablePaddingPx(@IdRes int viewId, int pxValue) {
        TextView view = getView(viewId);
        view.setCompoundDrawablePadding(pxValue);
        return this;
    }

    @Override
    public HoldAble setCompoundDrawablePaddingDp(@IdRes int viewId, int dpValue) {
        return setCompoundDrawablePaddingPx(viewId, Utils.dp2px(mContext, dpValue));
    }

    @Override
    public HoldAble setTextSizeSp(@IdRes int viewId, float spSize) {
        return setTextSizePx(viewId, Utils.sp2px(mContext, spSize));
    }

    @Override
    public HoldAble setHintId(@IdRes int viewId, @StringRes int strResid) {
        return setHintStr(viewId, mContext.getString(strResid));
    }

    @Override
    public HoldAble setHintStr(@IdRes int viewId, CharSequence hint) {
        TextView view = getView(viewId);
        view.setHint(hint);
        return this;
    }

    @Override
    public HoldAble setTextColorInt(@IdRes int viewId, @ColorInt int colorInt) {
        TextView view = getView(viewId);
        view.setTextColor(colorInt);
        return this;
    }

    @Override
    public HoldAble setAlpha(@IdRes int viewId, @FloatRange(from = 0.0, to = 1.0) float alpha) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getView(viewId).setAlpha(alpha);
        }
        return this;
    }

    @Override
    public HoldAble setClickable(@IdRes int viewId, boolean clickable) {
        getView(viewId).setClickable(clickable);
        return this;
    }

    @Override
    public HoldAble setLongClickable(@IdRes int viewId, boolean longClickable) {
        getView(viewId).setLongClickable(longClickable);
        return this;
    }

    @Override
    public HoldAble setActivated(@IdRes int viewId, boolean activated) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getView(viewId).setActivated(activated);
        }
        return this;
    }

    @Override
    public HoldAble setScaleX(@IdRes int viewId, float scaleX) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getView(viewId).setScaleX(scaleX);
        }
        return this;
    }

    @Override
    public HoldAble setScaleY(@IdRes int viewId, float scaleY) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getView(viewId).setScaleY(scaleY);
        }
        return this;
    }

    @Override
    public HoldAble setRotation(@IdRes int viewId, float rotation) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getView(viewId).setRotation(rotation);
        }
        return this;
    }

    @Override
    public HoldAble setRotationX(@IdRes int viewId, float rotationX) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getView(viewId).setRotationX(rotationX);
        }
        return this;
    }

    @Override
    public HoldAble setRotationY(@IdRes int viewId, float rotationY) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getView(viewId).setRotationY(rotationY);
        }
        return this;
    }

    @Override
    public HoldAble setId(@IdRes int viewId, @IdRes int id) {
        getView(viewId).setId(id);
        return this;
    }

}
