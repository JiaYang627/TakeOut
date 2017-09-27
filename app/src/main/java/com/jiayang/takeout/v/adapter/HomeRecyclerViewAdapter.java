package com.jiayang.takeout.v.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.jiayang.takeout.R;
import com.jiayang.takeout.common.TUApp;
import com.jiayang.takeout.m.bean.homeVo.Category;
import com.jiayang.takeout.m.bean.homeVo.Head;
import com.jiayang.takeout.m.bean.homeVo.HomeInfo;
import com.jiayang.takeout.m.bean.homeVo.HomeItem;
import com.jiayang.takeout.m.bean.homeVo.Promotion;
import com.jiayang.takeout.m.bean.homeVo.Seller;
import com.jiayang.takeout.p.activity.SellerDetailActivityPst;
import com.jiayang.takeout.v.activity.SellerDetailActivity;
import com.jiayang.takeout.widget.ShoppingCartManager;
import com.squareup.picasso.Picasso;


/**
 * 首页数据适配
 */
public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    // 分析条目的样式：三种
    private final int TYPE_HEAD = 0;        //头部
    private final int TYPE_SELLER = 1;      //商家
    private final int TYPE_RECOMMEND = 2;   //推荐


    private HomeInfo data;



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case TYPE_HEAD:
                holder = new HeadHoler(View.inflate(TUApp.getContext(), R.layout.item_title, null));
                break;
            case TYPE_SELLER:
                holder = new SellerHoler(View.inflate(TUApp.getContext(), R.layout.item_seller, null));
                break;
            case TYPE_RECOMMEND:
                holder = new RecommentHoler(View.inflate(TUApp.getContext(), R.layout.item_division, null));
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = holder.getItemViewType();
        switch (type) {
            case TYPE_HEAD:
                ((HeadHoler) holder).setData(data.head);
                break;
            case TYPE_SELLER:
                HomeItem item = data.body.get(position - 1);
                ((SellerHoler) holder).setData(item.seller);
                break;
            case TYPE_RECOMMEND:
                ((RecommentHoler) holder).setData(data.body.get(position - 1));
                break;
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (data == null) {
            count = 0;
        } else {
            count = data.body.size() + 1;
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        int type = -1;
        if (position == 0) {
            type = TYPE_HEAD;
        } else {
            HomeItem item = data.body.get(position - 1);
            type = item.type == 0 ? TYPE_SELLER : TYPE_RECOMMEND;
        }
        return type;
    }

    public void setData(HomeInfo data) {
        this.data = data;
        notifyDataSetChanged();
    }

    /**
     * 头容器管理
     */
    class HeadHoler extends RecyclerView.ViewHolder {


        private Head data;
        private SliderLayout sliderLayout;
        private LinearLayout categoryContainer;

        public HeadHoler(View itemView) {
            super(itemView);
            sliderLayout = (SliderLayout) itemView.findViewById(R.id.slider);
            categoryContainer = (LinearLayout) itemView.findViewById(R.id.catetory_container);
        }

        public void setData(Head data) {
            this.data = data;
            sliderLayout.removeAllSliders();
            if (data != null && data.promotionList.size() > 0) {
                int rollView = 0;
                for (Promotion item : data.promotionList) {
                    TextSliderView textSliderView = new TextSliderView(TUApp.getContext());
                    textSliderView.image(item.pic.replace("Takeout" , "takeout"));
                    textSliderView.description(item.info);

                    sliderLayout.addSlider(textSliderView);
                }
            }

            if (data != null && data.categorieList.size() > 0) {
                categoryContainer.removeAllViews();
                // 0、1  2、3  4、5
                // 0:创建整个条目的布局，将数据设置好后添加到条目中
                // 1:设置数据
                // ……

                View item = null;
                int pic = 0;
                for (int i = 0; i < data.categorieList.size(); i++) {
                    Category category = data.categorieList.get(i);

                    if (i % 2 == 0) {
                        // 每个条目中的第一个元素
                        item = View.inflate(TUApp.getContext(), R.layout.item_home_head_category, null);


                        Picasso
                                .with(TUApp.getContext())
                                .load(category.pic.replace("Takeout", "takeout"))
                                .into((ImageView) item.findViewById(R.id.top_iv));

                        ((TextView) item.findViewById(R.id.top_tv)).setText(category.name);

                        categoryContainer.addView(item);
                    }

                    if (i % 2 != 0) {


                        Picasso
                                .with(TUApp.getContext())
                                .load(category.pic.replace("Takeout" , "takeout"))
                                .into((ImageView) item.findViewById(R.id.bottom_iv));
                        ((TextView) item.findViewById(R.id.bottom_tv)).setText(category.name);
                    }
                }
            }


        }
    }

    /**
     * 商家容器管理
     */
    class SellerHoler extends RecyclerView.ViewHolder implements View.OnClickListener {


        private Seller data;
        private TextView tvTitle;
        private TextView tvCount;


        public SellerHoler(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvCount=(TextView) itemView.findViewById(R.id.tv_count);
        }

        public void setData(Seller data) {
            this.data = data;
            tvTitle.setText(data.name);


            // 设置已经购买数量,需要依据商家标识进行区分
            if(data.id== ShoppingCartManager.getInstance().sellerId) {
                Integer num = ShoppingCartManager.getInstance().getTotalNum();
                if (num > 0) {
                    tvCount.setVisibility(View.VISIBLE);
                    tvCount.setText(num.toString());
                } else {
                    tvCount.setVisibility(View.GONE);
                }
            }else {
                tvCount.setVisibility(View.GONE);
            }
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(TUApp.getContext(), SellerDetailActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// 由于使用了Application的上下文，需要增加该配置信息（错误日志中会提示）
            intent.putExtra(SellerDetailActivityPst.EXTRA_SELLERID_ID,data.id);
            intent.putExtra(SellerDetailActivityPst.EXTRA_SELLERNAME_ID,data.name);
//
            if(ShoppingCartManager.getInstance().sellerId!=data.id) {
                // 进入商家时更新购物车商家标识
                ShoppingCartManager.getInstance().sellerId = data.id;
                ShoppingCartManager.getInstance().name = data.name;
                ShoppingCartManager.getInstance().url = data.pic;
                ShoppingCartManager.getInstance().sendPrice=data.sendPrice;
                // 清除掉上一个商家中选择的商品
                ShoppingCartManager.getInstance().clear();
            }
//
//            // 统计商铺的浏览量
//            MobclickAgent.onEvent(v.getContext(),"seller");
//            HashMap<String, String> map = new HashMap<>();
//            map.put("seller_id",data.id+"");
//            MobclickAgent.onEvent(v.getContext(),"seller",map);
//
//
            TUApp.getContext().startActivity(intent);


        }
    }

    /**
     * 大家都在找的条目管理
     */
    class RecommentHoler extends RecyclerView.ViewHolder {
        private HomeItem data;

        public RecommentHoler(View itemView) {
            super(itemView);
        }

        public void setData(HomeItem data) {
            this.data = data;
        }
    }
}
