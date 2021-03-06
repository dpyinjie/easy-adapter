package dpyinjie.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import dpyinjie.adapter.holder.RecHolder;
import dpyinjie.adapter.multitype.RecMultiItemSupport;


public abstract class BaseRecAdapter<D> extends RecyclerView.Adapter<RecHolder> implements DataManager<D> {

    private final Object mLock = new Object();
    private List<D> mDataSet;
    private boolean mNotifyOnChange = true;
    private RecMultiItemSupport<D> mMultiItemSupport;
    private int mItemLayoutId;
    private Context mContext;

    /**
     * @param context
     */
    public BaseRecAdapter(Context context) {
        init(context, 0, new ArrayList<D>());
    }

    /**
     * @param context
     * @param itemLayoutRes
     */
    public BaseRecAdapter(Context context, @LayoutRes int itemLayoutRes) {
        init(context, itemLayoutRes, new ArrayList<D>());
    }

    /**
     * @param context
     * @param dataSet
     */
    public BaseRecAdapter(Context context, @Nullable D[] dataSet) {
        init(context, 0, dataSet == null ? null : Arrays.asList(dataSet));
    }

    /**
     * @param context
     * @param dataSet
     * @param itemLayoutRes
     */
    public BaseRecAdapter(Context context, @Nullable D[] dataSet, @LayoutRes int itemLayoutRes) {
        init(context, itemLayoutRes, dataSet == null ? null : Arrays.asList(dataSet));
    }

    /**
     * @param context
     * @param dataSet
     */
    public BaseRecAdapter(Context context, @Nullable List<D> dataSet) {
        init(context, 0, dataSet);
    }

    /**
     * @param context
     * @param dataSet
     * @param itemLayoutRes
     */
    public BaseRecAdapter(Context context, @Nullable List<D> dataSet, @LayoutRes int itemLayoutRes) {
        init(context, itemLayoutRes, dataSet);
    }

    /**
     * @param context
     * @param itemLayoutRes
     * @param dataSet
     */
    private void init(Context context, @LayoutRes int itemLayoutRes, @Nullable List<D> dataSet) {
        if (dataSet == null) {
            dataSet = new ArrayList<>();
        }
        mContext = context;
        mDataSet = dataSet;
        mItemLayoutId = itemLayoutRes;
    }

    /**
     * @return
     */
    public RecMultiItemSupport<D> getMultiItemSupport() {
        return mMultiItemSupport;
    }

    /**
     * @param multiItemSupport
     */
    public void setMultiItemSupport(RecMultiItemSupport<D> multiItemSupport) {
        mMultiItemSupport = multiItemSupport;
    }

    @Override
    public RecHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mMultiItemSupport != null) {
            mItemLayoutId = mMultiItemSupport.getItemLayoutId(viewType);
        }
        RecHolder holder = RecHolder.createHolder(mContext, parent, mItemLayoutId);
        return holder;
    }


    @Override
    public void onBindViewHolder(RecHolder holder, int position) {
        onBindViews(getItemViewType(position), holder, position, getItem(position));
    }

    /**
     * @param itemViewType Item 类型
     * @param holder       Item holder
     * @param position     当前位置
     * @param data         当前位置的数据
     */
    protected abstract void onBindViews(int itemViewType, RecHolder holder, int position, D data);


    @Override
    public int getItemViewType(int position) {
        if (mMultiItemSupport != null) {
            return mMultiItemSupport.getItemViewType(position, mDataSet.get(position));
        }
        return super.getItemViewType(position);
    }

    @Override
    public void add(D data) {
        if (data == null) {
            return;
        }
        synchronized (mLock) {
            mDataSet.add(data);
        }
        if (mNotifyOnChange)
            notifyDataSetChanged();
    }

    @Override
    public void add(Collection<D> collection) {
        if (Utils.isEmptyOrNull(collection)) {
            return;
        }
        synchronized (mLock) {
            mDataSet.addAll(collection);
        }
        if (mNotifyOnChange)
            notifyDataSetChanged();
    }

    @Override
    public void add(D... items) {
        if (Utils.isEmptyOrNull(items)) {
            return;
        }
        add(Arrays.asList(items));
    }

    @Override
    public void insert(int position, D data) {
        if (data == null) {
            return;
        }
        synchronized (mLock) {
            mDataSet.add(position, data);
        }
        if (mNotifyOnChange) {
            notifyDataSetChanged();
        }
    }

    @Override
    public void insert(int position, Collection<D> collection) {
        if (Utils.isEmptyOrNull(collection)) {
            return;
        }
        synchronized (mLock) {
            mDataSet.addAll(position, collection);
        }
        if (mNotifyOnChange)
            notifyDataSetChanged();
    }

    @Override
    public void insert(int position, D... items) {
        if (Utils.isEmptyOrNull(items)) {
            return;
        }
        synchronized (mLock) {
            mDataSet.addAll(position, Arrays.asList(items));
        }
        if (mNotifyOnChange)
            notifyDataSetChanged();
    }

    @Override
    public void remove(D data) {
        if (data == null) {
            return;
        }
        synchronized (mLock) {
            mDataSet.remove(data);
        }
        if (mNotifyOnChange)
            notifyDataSetChanged();
    }

    @Override
    public void remove(int position) {
        synchronized (mLock) {
            if (mDataSet.size() > position) {
                mDataSet.remove(position);
            }
        }
        if (mNotifyOnChange)
            notifyDataSetChanged();
    }

    @Override
    public void sort(Comparator<D> comparator) {
        if (comparator == null) {
            return;
        }
        synchronized (mLock) {
            Collections.sort(mDataSet, comparator);
        }
        if (mNotifyOnChange)
            notifyDataSetChanged();
    }

    @Override
    public Collection<D> getDataSet() {
        return mDataSet;
    }

    @Override
    public void setNotifyOnChange(boolean notifyOnChange) {
        mNotifyOnChange = notifyOnChange;
    }

    @Override
    public int getItemCount() {
        return getCount();
    }

    @Override
    public int getCount() {
        return mDataSet.size();
    }

    @Override
    public D getItem(int position) {
        return mDataSet.get(position);
    }

    @Override
    public int getPosition(D item) {
        return item == null ? -1 : mDataSet.indexOf(item);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void update(Collection<D> dataSet) {
        if (dataSet == null) {
            return;
        }
        synchronized (mLock) {
            mDataSet.clear();
            mDataSet.addAll(dataSet);
        }
        if (mNotifyOnChange)
            notifyDataSetChanged();
    }

    @Override
    public void clear() {
        synchronized (mLock) {
            mDataSet.clear();
        }
        if (mNotifyOnChange)
            notifyDataSetChanged();
    }

    @Override
    public void reverse() {
        synchronized (mLock) {
            Collections.reverse(mDataSet);
        }
        if (mNotifyOnChange)
            notifyDataSetChanged();
    }

    @Override
    public void filter(Filter filter) {
        ArrayList<D> dataSet = new ArrayList<>(getCount());
        synchronized (mLock) {
            for (D d : mDataSet) {
                if (filter.accept(d)) {
                    dataSet.add(d);
                }
            }
            mDataSet = dataSet;
        }
        if (mNotifyOnChange)
            notifyDataSetChanged();
    }

    @Override
    public boolean isEmpty() {
        return mDataSet.isEmpty();
    }


    @Override
    public void refresh(Collection<D> dataSet) {
        synchronized (mLock) {
            mDataSet.clear();
            mDataSet.addAll(dataSet);
        }
        if (mNotifyOnChange) {
            notifyDataSetChanged();
        }
    }

    @Override
    public void refresh(D... items) {
        refresh(Arrays.asList(items));
    }
}
