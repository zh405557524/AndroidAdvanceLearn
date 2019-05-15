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
  
    
  
    
  
  - NavigationView
  
    ~~~java
    //侧滑菜单中使用navigationView
            NavigationView navigationView = findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);//设置监听
    ~~~
  
    
  
  - BottomNavigationView
  
       ~~~xml
    <android.support.design.widget.BottomNavigationView
            android:id="@+id/navigation"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_marginStart="0dp"
            android:layout_marginEnd="0dp"
            android:background="?android:attr/windowBackground"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintLeft_toLeftOf="parent"
            app:layout_constraintRight_toRightOf="parent"
            app:menu="@menu/navigation"/>
    ~~~
  
    ~~~xml
    <?xml version="1.0" encoding="utf-8"?>
    <menu xmlns:android="http://schemas.android.com/apk/res/android">
    
        <item
            android:id="@+id/navigation_home"
            android:icon="@drawable/ic_home_black_24dp"
            android:title="@string/title_home"/>
    
        <item
            android:id="@+id/navigation_dashboard"
            android:icon="@drawable/ic_dashboard_black_24dp"
            android:title="@string/title_dashboard"/>
    
        <item
            android:id="@+id/navigation_notifications"
            android:icon="@drawable/ic_notifications_black_24dp"
            android:title="@string/title_notifications"/>
    
    </menu>
    
    ~~~
  
    ~~~java
    BottomNavigationView navigation = findViewById(R.id.navigation);
    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
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
  
       ~~~xml
           <android.support.design.widget.CoordinatorLayout
               android:layout_width="match_parent"
               android:layout_height="match_parent">
       
               <android.support.design.widget.AppBarLayout
                   android:layout_width="match_parent"
                   android:layout_height="wrap_content">
       
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
               </android.support.design.widget.AppBarLayout>
       
       
               <android.support.v4.widget.SwipeRefreshLayout
                   android:id="@+id/srl_refresh"
                   android:layout_width="match_parent"
                   android:layout_height="match_parent"
                   app:layout_behavior="@string/appbar_scrolling_view_behavior">
       
                   <android.support.v7.widget.RecyclerView
                       android:id="@+id/rv_at_list"
                       android:layout_width="match_parent"
                       android:layout_height="match_parent"/>
       
               </android.support.v4.widget.SwipeRefreshLayout>
       
               <android.support.design.widget.FloatingActionButton
                   android:id="@+id/fab_at_action"
                   android:layout_width="wrap_content"
                   android:layout_height="wrap_content"
                   android:layout_gravity="bottom|end"
                   android:layout_margin="16dp"
                   android:src="@drawable/add"
                   app:elevation="8dp"/>
       
               <!-- 使用popupTheme是为了兼容5.0以下的系统-->
           </android.support.design.widget.CoordinatorLayout>
       
       ~~~
  
       
  
  - AppBarLayout
  
    ~~~java
     appBarLayout.addOnOffsetChangedListener()
    ~~~
  
    ~~~xml
    
            <android.support.design.widget.AppBarLayout
                android:id="@+id/appBar"
                android:layout_width="match_parent"
                android:layout_height="450dp"
                android:fitsSystemWindows="true">//是否不延伸至状态栏
    
                <!-- AppBarLayout的直接子控件可以设置的属性:layout_scrollFlags(滚动模式) -->
                <!-- 1.scroll|exitUntilCollapsed:如果AppBarLayout的直接子控件设置该属性,该子控件可以滚动,向上滚动NestedScrollView出父布局(一般为CoordinatorLayout)时,会折叠到顶端,向下滚动时NestedScrollView必须滚动到最上面的时候才能拉出该布局
                     2.scroll|enterAlways:只要向下滚动该布局就会显示出来,只要向上滑动该布局就会向上收缩
                     3.scroll|enterAlwaysCollapsed:向下滚动NestedScrollView到最底端时该布局才会显示出来
                     4.scroll|snap:表示一个吸附效果，可以确保childView不会滑动停止在中间的状态
                     5.如果不设置该属性,则该布局不能滑动 -->
                <android.support.design.widget.CollapsingToolbarLayout
                    android:id="@+id/collapsingToolbarLayout"
                    android:layout_width="match_parent"
                    android:layout_height="match_parent"
                    android:fitsSystemWindows="true"
                    app:contentScrim="?attr/colorPrimary"
                    app:layout_scrollFlags="scroll|exitUntilCollapsed">
    ~~~
  
    
  
  - CollapsingToolbarLayout
  
    ~~~java
      //通过collapsingToolbarLayout修改字体颜色
            collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);//设置还没有收缩时状下字体颜色
            collapsingToolbarLayout.setCollapsedTitleTextColor(Color.BLACK);//设置收缩后Toolbar上字体的颜色
    
    ~~~
  
    ~~~xml
            <android.support.design.widget.CollapsingToolbarLayout
                    android:id="@+id/collapsingToolbarLayout"
                    android:layout_width="match_parent"
                    android:layout_height="match_parent"
                    android:fitsSystemWindows="true"
                    app:contentScrim="?attr/colorPrimary"
                    app:layout_scrollFlags="scroll|exitUntilCollapsed">
    
                    <!-- CollapsingToolbarLayout的直接子布局可以使用的属性:layout_collapseMode(折叠模式) -->
                    <!-- 1.pin:在滑动过程中,此自布局会固定在它所在的位置不动,直到CollapsingToolbarLayout全部折叠或者全部展开
                         2.parallax:视差效果,在滑动过程中,不管上滑还是下滑都会有视察效果,不知道什么事视察效果自己看gif图(layout_collapseParallaxMultiplier视差因子 0~1之间取值,当设置了parallax时可以配合这个属性使用,调节自己想要的视差效果)
                         3.不设置:跟随NestedScrollView的滑动一起滑动,NestedScrollView滑动多少距离他就会跟着走多少距离 -->
                    <ImageView
                        android:id="@+id/iv_movie_icon"
                        android:layout_width="match_parent"
                        android:layout_height="match_parent"
                        android:fitsSystemWindows="true"
                        android:scaleType="centerCrop"
                        android:src="@drawable/ic"
                        android:transitionName="basic"
                        app:layout_collapseMode="parallax"/>
    
                    <android.support.v7.widget.Toolbar
                        android:id="@+id/tb_amd_toolbar"
                        android:layout_width="match_parent"
                        android:layout_height="?attr/actionBarSize"
                        app:layout_collapseMode="pin"
                        app:subtitleTextColor="#ff4081"
                        app:titleTextColor="#ff4081"/>
    
                </android.support.design.widget.CollapsingToolbarLayout>
    ~~~
  
    
  
  - NestedSrollView
  
*  常用动画
  * Fade淡入
  
    ~~~java
    final Intent intent = new Intent(this, VipActivity.class);
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    
    ~~~
  
    ~~~java
    //允许transitions
       getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
     //淡入动画
       getWindow().setEnterTransition(new Fade());
    ~~~
  
    
  
  * Slide滑动
  
    ~~~java
    getWindow().setEnterTransition(new Slide());
    ~~~
  
    
  
  * Explode分解
  
    ~~~java
    getWindow().setEnterTransition(new Explode());
    ~~~
  
    
  
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
  
    

* Behavior 子父view滑动联动

  ~~~ java
  
  public class ScaleBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {
  
      private FastOutLinearInInterpolator mFastOutLinearInInterpolator = new FastOutLinearInInterpolator();
      private LinearOutSlowInInterpolator mLinearOutSlowInInterpolator = new LinearOutSlowInInterpolator();
  
      public ScaleBehavior(Context context, AttributeSet attrs) {
          super(context, attrs);
      }
  
      @Override
      public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
          return axes == ViewCompat.SCROLL_AXIS_VERTICAL; //垂直滚动
      }
  
      @Override
      public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
          super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
          if (dyConsumed > 0 && !isRunning && child.getVisibility() == View.VISIBLE){//向下滑动，缩放隐藏控件
              scaleHide(child);
          }else if (dyConsumed < 0 && !isRunning && child.getVisibility() == View.INVISIBLE){ //向上滑动，缩放显示控件
              scaleShow(child);
          }
      }
      private boolean isRunning;
  }
  
  
  ~~~

  

## 沉浸式体验

* 修改styles文件

  ~~~xml
   <style name="AppTheme" parent="Theme.AppCompat.Light.NoActionBar">
          <!-- Customize your theme here. -->
          <item name="colorPrimary">@color/colorPrimary</item>
          <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
          <item name="colorAccent">@color/colorAccent</item>
          <item name="android:statusBarColor">@android:color/transparent</item>
          <item name="android:windowTranslucentStatus">true</item>
          <item name="android:windowTranslucentNavigation">true</item>
      </style>
  ~~~

* 修改代码

~~~java
 if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT){
            return;
        }   
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏颜色透明
            window.setStatusBarColor(Color.TRANSPARENT);

            int visibility = window.getDecorView().getSystemUiVisibility();
            //布局内容全屏展示
            visibility |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            //隐藏虚拟导航栏
            visibility |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            //防止内容区域大小发生变化
            visibility |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

            window.getDecorView().setSystemUiVisibility(visibility);
        }else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
~~~

~~~java
   public int getStatusBarHeight(Context context){
        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0){
            return context.getResources().getDimensionPixelSize(resId);
        }
        return 0;
    }
~~~



## CaredView

~~~xml
    <!--app:cardBackgroundColor="@color/colorPrimary"  设置cardView背景色 -->
    <!--app:cardPreventCornerOverlap="false" 取消Lollipop以下版本的padding -->
    <!--app:cardUseCompatPadding="true" 为 Lollipop 及其以上版本增加一个阴影padding内边距-->
    <!--app:cardCornerRadius="8dp" 设置cardView圆角效果-->
    <!--app:cardElevation="10dp" 设置cardView Z轴阴影大小-->
    <!--app:cardMaxElevation="6dp" 设置cardView Z轴最大阴影-->
    <!--app:contentPadding="10dp" 设置内容的内边距-->
    <!--app:contentPaddingBottom="12dp" 设置内容的底部内边距-->
    <!--app:contentPaddingLeft="12dp" 设置内容的左边内边距-->
    <!--app:contentPaddingRight="12dp" 设置内容的右边内边距-->
    <!--app:contentPaddingTop="12dp" 设置内容的顶部内边距-->
    <android.support.v7.widget.CardView android:layout_width="200dp"
        android:layout_height="200dp"
        android:layout_marginTop="20dp"
        app:cardBackgroundColor="@color/colorPrimary"
        app:cardPreventCornerOverlap="false"
        app:cardUseCompatPadding="true"
        app:cardCornerRadius="8dp"
        app:contentPadding="10dp"
        android:clickable="true"
        android:foreground="?attr/selectableItemBackground"
        app:cardElevation="10dp">

        <TextView android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:text="cardView"
            android:background="#ffff00"
            android:gravity="center"/>

    </android.support.v7.widget.CardView>
~~~





















 





