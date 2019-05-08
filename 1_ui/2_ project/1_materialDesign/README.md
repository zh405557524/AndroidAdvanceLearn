# Material Design

## Material Mesign 概念

* 是将经典的设计 原则和科技、创新想结合而创造的设计语言
* 是一个能在不同平台、不同设备上提供一致的体验的底层系统，它代表了一种设计规范。

## Material Design主题

* android:style/Theme.Material            
* android:style/Theme.Material.Light
* android:style/Theme.Material.Light.DarkActionBar
* 主题常用属性
  - colorPrimary          		标题栏颜色
  
  - colorPrimaryDark          状态栏颜色
  
  - colorAccent                    强调色
  
  - textColorPrimary           标题栏字体颜色
  
  - windowBackground      窗口的背景色
  
  - navigationBarColor       虚拟导航栏的颜色
  
    ![主题颜色](https://raw.githubusercontent.com/loonggg/MaterialDesignDemo/master/image/style.png)

## Material Design 兼容主题

* Theme.AppCompat.Light
* Theme.AppCompat.Light.DarkActionBar



## Material Design交互

* 常用控件
  - Toolbar
  
    ~~~xml
        <android.support.v7.widget.Toolbar
                    android:id="@+id/tb_at_toolbar"
                    android:layout_width="match_parent"
                    android:layout_height="?attr/actionBarSize"
                    android:background="@color/colorPrimary"
                    android:theme="@style/ThemeOverlay.AppCompat.Dark.ActionBar"
                    app:layout_scrollFlags="scroll|enterAlways"
                    app:logo="@mipmap/ic_launcher"
                    app:popupTheme="@style/ThemeOverlay.AppCompat.Light"
                    app:subtitle="副标题"
                    app:subtitleTextColor="#fff"
                    app:title="标题"
                    app:titleTextColor="#fff">
    
                    <TextView
                        android:layout_width="match_parent"
                        android:layout_height="?attr/actionBarSize"
                        android:gravity="center"
                        android:text="View"
                        android:textColor="@color/white"/>
    
                </android.support.v7.widget.Toolbar>      
    ~~~
  
  ~~~java
    final Toolbar toolBar = findViewById(R.id.tb_at_toolbar);
          setSupportActionBar(toolBar);
          final ActionBar actionBar = getSupportActionBar();
          if (actionBar != null) {
              //设置Toolbar home 键可点击
              actionBar.setDisplayHomeAsUpEnabled(true);
              //设置Toolbar home键图标
              actionBar.setHomeAsUpIndicator(R.drawable.ic_drawer_am);
          }
  ~~~
  
  ~~~java
   @Override
      public boolean onCreateOptionsMenu(Menu menu) {
          getMenuInflater().inflate(R.menu.toobalr, menu);
          return true;
      }
  
      @Override
      public boolean onOptionsItemSelected(MenuItem item) {
          switch (item.getItemId()) {
          }
          return true;
      }
  ~~~
  
  
  
  - DrawerLayout
  
    ~~~java
    //Toolbar关联侧滑菜单    
    DrawerLayout drawer = findViewById(R.id.drawer_layout);
            final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                    R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();//关联
    ~~~
  
    
  
    
  
  - NavigationView/BottomNavigationView
  
    ~~~java
            //侧滑菜单中使用navigationView
            NavigationView navigationView = findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);//设置监听
    ~~~
  
    
  
  - FloatingActionButton
  
    ~~~xml
    
        <android.support.design.widget.FloatingActionButton
            android:id="@+id/fab"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="bottom|end"  //摆放的位置
            android:layout_margin="@dimen/fab_margin"
            app:srcCompat="@android:drawable/ic_dialog_email"//显示的图片
            />
    ~~~
  
  - Snackbar
  
    ~~~java
    Snackbar.make(v, "snack Action", 1000)//snack 显示的文字
                            .setAction("Toast", new View.OnClickListener() {// snack 的点击事件
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(MainActivity.this, " to do ", Toast.LENGTH_SHORT).show();}}).show();
    ~~~
  
    
  
  - CardView
  
    ~~~xml
       <!-- cardBackgroundColor 卡片背景色 -->
        <!-- cardCornerRadius 卡片圆角效果 -->
        <!-- cardElevation 卡片阴影效果 -->
        <!-- contentPadding 卡片内容四周间距 -->
        <android.support.v7.widget.CardView
            android:layout_width="match_parent"
            android:layout_height="180dp"
            android:foreground="?android:attr/selectableItemBackground"
            app:cardBackgroundColor="#D0D2DE"
            app:cardCornerRadius="8dp"
            app:cardElevation="0dp"
            app:contentPadding="8dp">
    ~~~
  
    
  
  - CoordinatorLayout
  
  - AppBarLayout
  
  - CollapsingToolbarLayout
  
  - NestedSrollView
  
*  常用动画
  * Fade淡入
  
  * Slide滑动
  
  * Explode分解
  
  * 共享元素
  
    ~~~java
                    //Activity共享元素转场动画
                    ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                            ToolbarActivity.this,
                            view.findViewById(R.id.iv_icon),
                            "basic" //共享元素的名称
                    );
    
                    intent = new Intent(ToolbarActivity.this, MovieDetailActivity.class);
                    intent.putExtra("URL", movie.get(position).getImages().getMedium());
                    intent.putExtra("NAME", movie.get(position).getTitle());
                    startActivity(intent, optionsCompat.toBundle());
    ~~~
  
    ~~~xml
    
                    <ImageView
                        android:id="@+id/iv_movie_icon"
                        android:layout_width="match_parent"
                        android:layout_height="match_parent"
                        android:fitsSystemWindows="true"
                        android:scaleType="centerCrop"
                        android:src="@drawable/ic"
                        android:transitionName="basic"/>//共享元素的名称
    ~~~
  
    





























 





