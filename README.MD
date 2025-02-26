## 项目结构
app/
├── java/
│   └── com.example.menu/
│       ├── data/                    // 数据层：Model 和数据源
│       │   ├── model/              // 实体类（如 CartItem、User 等）
│       │   │   └── CartItem.java
│       │   ├── repository/         // 数据仓库，处理数据库和网络请求
│       │   │   └── CartRepository.java
│       │   └── database/           // 数据库相关
│       │       └── DatabaseHelper.java
│       ├── ui/                     // UI 层：Activity、Fragment 和 Adapter
│       │   ├── activity/           // Activity
│       │   │   ├── MainActivity.java
│       │   │   ├── AdminActivity.java
│       │   │   └── OrderDetailActivity.java
│       │   ├── fragment/           // Fragment
│       │   │   ├── CartFragment.java
│       │   │   ├── HomeFragment.java
│       │   │   ├── MineFragment.java
│       │   │   └── OrderFragment.java
│       │   └── adapter/            // Adapter
│       │       └── CartAdapter.java
│       ├── viewmodel/              // ViewModel 层：业务逻辑
│       │   ├── CartViewModel.java
│       │   ├── HomeViewModel.java
│       │   └── OrderViewModel.java
│       └── service/                // 服务类：网络或其他辅助逻辑
│           └── EmailService.java
└── res/
└── layout/                     // 布局文件