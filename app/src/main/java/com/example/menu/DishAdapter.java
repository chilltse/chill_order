package com.example.menu;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class DishAdapter extends BaseAdapter {
    private Context context;
    private List<Dish> dishList;

    public DishAdapter(Context context, List<Dish> dishList) {
        this.context = context;
        this.dishList = dishList;
    }

    @Override
    public int getCount() {
        return dishList.size();
    }

    @Override
    public Object getItem(int position) {
        return dishList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_dish, parent, false);
        }

        ImageView imageViewDish = convertView.findViewById(R.id.imageViewDish);
        TextView textViewDishName = convertView.findViewById(R.id.textViewDishName);

        Dish dish = dishList.get(position);
        textViewDishName.setText(dish.getName());

        // 加载图片
        if (dish.getImagePath() != null && !dish.getImagePath().isEmpty()) {
            imageViewDish.setImageURI(Uri.parse(dish.getImagePath()));
        } else {
            imageViewDish.setImageResource(R.drawable.ic_launcher_foreground); // 默认占位图
        }

        return convertView;
    }
}
