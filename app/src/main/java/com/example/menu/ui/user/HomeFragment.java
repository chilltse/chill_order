package com.example.menu.ui.user;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menu.R;
import com.example.menu.data.database.DatabaseHelper;
import com.example.menu.ui.user.DishAdapter;
import com.example.menu.data.model.DishItem;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private DatabaseHelper dbHelper;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DishFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        dbHelper = new DatabaseHelper(requireContext()); // 替换 getContext() 为 requireContext() 确保安全
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false); // 替换 fragment_cart 为 fragment_dish
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewHome); // 替换 recyclerViewCart 为 recyclerViewDish
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        DishAdapter adapter = loadDishes(); // 替换 CartAdapter 为 HomeAdapter
        recyclerView.setAdapter(adapter);

        // 设置点击监听器
        adapter.setOnItemClickListener(new DishAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DishItem dishItem) {
                // 处理点击事件，如显示Toast消息
                Toast.makeText(getContext(), "Clicked: " + dishItem.getName(), Toast.LENGTH_SHORT).show();

                // 传递菜品名称到 OrderDetailActivity
                // 检查上下文是否可用
                if (getContext() == null) {
                    return;
                }

                // 创建 Intent 以启动新的 Activity
                Intent intent = new Intent(getContext(), OrderDetailActivity.class);
                intent.putExtra("DishName", dishItem.getName());

                // 获取从前一个 Activity 传递过来的额外数据
                if (getActivity() != null && getActivity().getIntent() != null && getActivity().getIntent().hasExtra("UserName")) {
                    String userName = getActivity().getIntent().getStringExtra("UserName");
                    intent.putExtra("UserName", userName);
                }

                // 启动目标 Activity
                startActivity(intent);
            }
        });

        return view;
//        return null;
    }

    private DishAdapter loadDishes() { // 替换 CartAdapter 为 HomeAdapter
        Cursor cursor = dbHelper.getAllDishes();
        List<DishItem> dishItems = new ArrayList<>(); // 替换 CartItem 为 DishItem
        if (cursor.moveToFirst()) {
            do {
                int columnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_DISH_NAME);
                if (columnIndex >= 0) {
                    String dishName = cursor.getString(columnIndex);
                    dishItems.add(new DishItem(dishName, "Fresh dish")); // 替换 CartItem 为 DishItem
                } else {
                    // Handle the error or log it
                }
            } while (cursor.moveToNext());
        }
        cursor.close();

        DishAdapter adapter = new DishAdapter(dishItems);
        return adapter;
    }
}