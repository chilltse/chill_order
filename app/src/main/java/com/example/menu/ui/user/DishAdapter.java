package com.example.menu.ui.user;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.menu.R;
import com.example.menu.data.model.DishItem;

import java.util.List;

public class DishAdapter extends RecyclerView.Adapter<DishAdapter.ViewHolder> {

    private List<DishItem> dishItems; // 储存菜品数据
    private OnItemClickListener listener; // 点击监听器接口

    public DishAdapter(List<DishItem> dishItems) { // 替换 CartItem 为 Dish
        this.dishItems = dishItems;
    }

    // 点击监听器接口
    public interface OnItemClickListener {
        void onItemClick(DishItem dishItem); // 传递点击的 DishItem
    }

    // 设置点击监听器的方法
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_dish, parent, false); // 假设布局文件改为 item_dish
//        return new ViewHolder(view);
        return new ViewHolder(view, listener,this); // 将监听器传递给ViewHolder
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DishItem item = dishItems.get(position); // 替换 CartItem 为 Dish
        holder.textViewItemName.setText(item.getName());
        holder.textViewDescription.setText(item.getDescription());
    }

    @Override
    public int getItemCount() {
        return dishItems.size(); // 替换 cartItems 为 dishItems
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewItemName;
        TextView textViewDescription;

        public ViewHolder(View view, final OnItemClickListener listener, DishAdapter adapter) { // 接收 DishAdapter 的引用
            super(view);
            textViewItemName = view.findViewById(R.id.text_view_item_name);
            textViewDescription = view.findViewById(R.id.text_view_item_description);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getBindingAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(adapter.dishItems.get(position)); // 使用传入的 adapter 引用来访问 dishItems
                    }
                }
            });
        }
    }

//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        TextView textViewItemName;
//        TextView textViewDescription;
//
//        public ViewHolder(View view) {
//            super(view);
//            textViewItemName = view.findViewById(R.id.text_view_item_name);
//            textViewDescription = view.findViewById(R.id.text_view_item_description);
//
//            // 为整个项设置点击监听器
//            view.setOnClickListener(v -> {
//                int position = getAdapterPosition();
//                if (position != RecyclerView.NO_POSITION) {
//                    // 错误：试图在静态上下文中访问 DishAdapter 的非静态成员
//                    DishAdapter.listener.onItemClick(DishAdapter.dishItems.get(position)); // 错误示例
//                }
//            });
//        }
//    }
}