package com.jiayang.takeout.v.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jiayang.takeout.R;
import com.jiayang.takeout.m.bean.goodsFrgVo.GoodsInfoVo;
import com.jiayang.takeout.m.bean.goodsFrgVo.GoodsTypeVo;
import com.jiayang.takeout.m.component.ApiComponent;
import com.jiayang.takeout.p.fragment.GoodsFragmentPst;
import com.jiayang.takeout.utils.LogUtils;
import com.jiayang.takeout.v.adapter.MyGroupAdapter;
import com.jiayang.takeout.v.adapter.MyHeadAdapter;
import com.jiayang.takeout.v.base.LazyFragment;
import com.jiayang.takeout.v.iview.IgoodsFragmentView;
import com.jiayang.takeout.widget.ShoppingCartManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;


/**
 * 懒加载Frg未做细致处理，
 */

public class GoodsFragment extends LazyFragment<GoodsFragmentPst> implements IgoodsFragmentView, AdapterView.OnItemClickListener, AbsListView.OnScrollListener {

    @BindView(R.id.goodsLeftListView)
    ListView mGoodsLeftListView;
    @BindView(R.id.goodsRightSHLView)
    StickyListHeadersListView mGoodsRightSHLView;
    @BindView(R.id.iv_cart)
    ImageView mIvCart;
    @BindView(R.id.fragment_goods_tv_count)
    TextView mFragmentGoodsTvCount;
    private Unbinder mUnbinder;
    private List<GoodsTypeVo> headDatas;
    private List<GoodsInfoVo> datas = new ArrayList<GoodsInfoVo>() {
    };
    private MyHeadAdapter headAdapter;
    private MyGroupAdapter groupAdapter;


    @Override
    protected void inject(ApiComponent apiComponent) {
        apiComponent.inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.goods_frg, null);
        mUnbinder = ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 判断购物车中是否有商品，如果有需要对购物车的气泡进行修改
        Integer totalNum = ShoppingCartManager.getInstance().getTotalNum();
        if(totalNum>0){
            mFragmentGoodsTvCount.setVisibility(View.VISIBLE);
            mFragmentGoodsTvCount.setText(totalNum.toString());
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }


    @Override
    public void fillData(List<GoodsTypeVo> goodsTypeVos) {
        // 安装数据结构处理goodsInfo容器
        headDatas = goodsTypeVos;

        for (int hi = 0; hi < headDatas.size(); hi++) {

            GoodsTypeVo head = headDatas.get(hi);


            // 普通条目
            for (int di = 0; di < head.list.size(); di++) {
                GoodsInfoVo data = head.list.get(di);
                data.headId = head.id;
                data.headIndex = hi;

                if (di == 0)
                    head.groupFirstIndex = datas.size();
                datas.add(data);
            }
        }

        headAdapter = new MyHeadAdapter(headDatas);
        mGoodsLeftListView.setAdapter(headAdapter);

        groupAdapter = new MyGroupAdapter(headDatas, datas);
        mGoodsRightSHLView.setAdapter(groupAdapter);


        mGoodsLeftListView.setOnItemClickListener(this);
        mGoodsRightSHLView.setOnScrollListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // 在头容器中点击某个条目的时候，让该组信息在条目容器中置顶
        // 1.高亮点击条目
        headAdapter.setSelectedPositon(position);
        GoodsTypeVo head = headDatas.get(position);
        mGoodsRightSHLView.setSelection(head.groupFirstIndex);
        isScroll = false;
    }

    private boolean isScroll = false;

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        System.out.println("scrollState:" + scrollState);
        // 用户在滚动
        isScroll = true;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // 用户的滚动
        if (isScroll) {
            System.out.println("firstVisibleItem:" + firstVisibleItem);

            GoodsInfoVo data = datas.get(firstVisibleItem);
            System.out.println("data.headIndex:" + data.headIndex);
            // 当前正在置顶显示的头高亮处理
            headAdapter.setSelectedPositon(data.headIndex);

            // 判断头容器对应的条目是否处于可见状态
            // 获取到第一个可见，和最后一个可见的。比第一个小的，或者比最后一个大的均为不可见
            int firstVisiblePosition = mGoodsLeftListView.getFirstVisiblePosition();
            int lastVisiblePosition = mGoodsLeftListView.getLastVisiblePosition();
            if (data.headIndex <= firstVisiblePosition || data.headIndex >= lastVisiblePosition) {
                mGoodsLeftListView.setSelection(data.headIndex);// 可见处理
            }
        }


    }



    @OnClick(R.id.iv_cart)
    public void onViewClicked() {

        mPresenter.goToShopCar();
    }
}
