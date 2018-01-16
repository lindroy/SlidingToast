Toast大家都不会陌生，就是经常在界面上弹出的带有提示信息的半透明文字框，它短暂出现后就会慢慢变淡消失。与Toast具有相同作用的还有Crouton，这是一个外国人写的第三方库，跟Toast相同，一个界面一次只能出现一个Crouton。但与Toast淡出淡入的动画效果不同，
它一般是从布局的顶部滑入和滑出的，而且与灰不溜秋的Toast相比，它有多种色彩可供选择。Toast是“吐司”的英文，Crouton则是“油煎面包块”的意思，听名字就知道这两个是哥们了。

言归正传，我们现在就来学习一下Crouton的使用。

# 1、准备工作
要想使用Crouton，只需在用Android Studio创建好工程之后，打开build.gradle文件，添加以下的依赖库：

```
    compile('de.keyboardsurfer.android.widget:crouton:1.8.5@aar') {
        // exclusion is not necessary, but generally a good idea.
        exclude group: 'com.google.android', module: 'support-v4'
    }
```
# 2、创建Crouton的最简方法（在根布局弹出Crouton）
跟它的兄弟一样，Crouton也需要调用`makeText`方法来输入内容，然后再调用show方法显示到界面上：
```java
           /**1、根布局弹出Crouton**/
                Crouton.makeText(this, //上下文
                        "根布局的Crouton", //Crouton要显示的文字
                        Style.INFO, //Crouton的样式
                        R.id.rl_root) //显示Crouton的布局ID，不写时默认为根布局
                        .show();
```
上面的每个参数我都写了注释了。其中第三个参数使用了Crouton提供的三种样式中的一种，三种样式如下图所示：

![三种默认样式](http://img.blog.csdn.net/20170612163930715?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvTGluZHJvaWQyMA==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

第四个参数可以不写，不写时默认显示在根布局。

运行后的效果如下：

![根布局的Crouton](http://img.blog.csdn.net/20170610173317406?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvTGluZHJvaWQyMA==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

# 3、在子布局上弹出Crouton
由2可知，通过充分利用`makeText`方法中的参数4，我们可以在任意子布局上显示Crouton，就像下面这样（灰色区域为一个RelativeLayout）：
```java
        Crouton.makeText(this, "子布局的Crouton", Style.ALERT, R.id.rl_child).show();
```

![子布局的Crouton](http://img.blog.csdn.net/20170610173337344?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvTGluZHJvaWQyMA==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

# 4、自定义Crouton
上面区区三种样式怎么能满足我们呢？假如我们需要制作一个图文并茂的Crouton的话，大家首先想到可能是自己写一个布局。Crouton确实是支持自定义布局的，我们可以先写一个布局：
**custom_crouton.xml**
```java
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <LinearLayout
        android:gravity="center"
        android:background="@android:color/holo_blue_dark"
        android:orientation="horizontal"
        android:layout_width="match_parent"
        android:layout_height="50dp">

        <ImageView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center_vertical"
            android:layout_marginLeft="20dp"
            android:src="@mipmap/clock" />

        <TextView
            android:layout_marginLeft="5dp"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center_vertical"
            android:text="快起床写代码了！"
            android:textColor="@android:color/white"
            android:textSize="20sp" />
    </LinearLayout>

</RelativeLayout>
```
大家也许会觉得最外层的RelativeLayout有点多余，但是我试过去掉它之后，LinearLayout的高度值就不起作用了，具体原因我也不明白……

接下来就要用到Crouton类中的另一个静态方法`make`来构造Crouton，它的用法也很简单，只要把自定义View的对象传入即可：

```java
            View view = View.inflate(context, R.layout.custom_crouton, null);
            Crouton.make(this, view, R.id.rl_root).show();
```

![自定义Crouton](http://img.blog.csdn.net/20170610173404267?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvTGluZHJvaWQyMA==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

# 5、通过Style.Builder修改背景颜色和字体样式
也许有时候我们就只是想给Crouton的背景或者字体换个颜色，为了这么个小需求就写一个布局那也忒麻烦了。难道就没有接口可以直接修改颜色的吗？

Crouton当然不会这么傻，它给我们提供了两个构造器：**Style.Builder**和**Configuration.Builder**。前者用来构造Crouton的外观，如背景、字体等；后者用来构造Crouton的配置：停留时间和进出动画。

Style.Builder提供了很多方法，这里我不可能一个个地示范了，只是写了一个小例子，比如我们要实现一个黑底金字带有阴影的的Crouton（我承认是有点土）时就可以这样写：
```java
                //创建Style构造器对象
                Style.Builder sb = new Style.Builder();
                sb.setBackgroundColor(android.R.color.black) //背景颜色
                        .setTextColor(android.R.color.holo_orange_light)//字体颜色
                        .setTextSize(18) //字体大小
                        .setTextShadowColor(android.R.color.white) //字体阴影颜色
                        .setTextShadowRadius(12) //字体阴影半径
                        .setHeight(120) //Crouton高度
                        .setGravity(Gravity.CENTER) //设置文字居中
                        .setFontName("Ponsi-Regular.otf")//设置字体，直接输入字体名称的字符串
                ;
                Crouton.showText(this, "This is a Crouton", sb.build());
```
这里需要用到`showText`方法，它的第三个参数就是Style对象。字体文件要提前放在assets文件夹下，然后直接在`setFontName`方法中直接输入字体名称字符串就可以了。但要注意的是`setGravity`方法只能设置文字内容的位置，对图片是不起作用的。

![自定义背景颜色和字体样式](http://img.blog.csdn.net/20170612165133261?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvTGluZHJvaWQyMA==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

# 6、自定义背景图片
如果UI给你设计好了Crouton的背景图片或者整个Crouton就是一张图片的话，那么你也可以给Crouton设置图片：
```java
                sb.setBackgroundDrawable(R.drawable.crouton_pic)
                  .setImageScaleType(ImageView.ScaleType.CENTER_CROP);
                Crouton.showText(this,"",sb.build()); //展示图片，文本内容为空字符串即可
```
![自定义背景图片](http://img.blog.csdn.net/20170613101309079?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvTGluZHJvaWQyMA==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

# 7、通过修改Configuration.Builder修改Crouton配置
前面我们已经成功修改了Crouton的样式，现在就来修改一下它的配置吧。它配置包含两个方面：
- 动画效果，包含进入的动画和消失的动画；
- 持续时间，即进入动画执行完毕后Crouton停留在界面直至消失动画开始执行的时间。

首先创建两个动画文件：
**crouton_in.xml**
```xml
<?xml version="1.0" encoding="utf-8"?>
<translate xmlns:android="http://schemas.android.com/apk/res/android"
    android:duration="1500"
    android:fromXDelta="-100%p"
    android:toXDelta="0">
</translate>
```
**crouton_out.xml**
```xml
<?xml version="1.0" encoding="utf-8"?>
<translate xmlns:android="http://schemas.android.com/apk/res/android"
    android:duration="1500"
    android:fromXDelta="0"
    android:toXDelta="100%p">
</translate>
```
代码的写法跟Style.Builder的类似：
```java
        //创建Configuration构造器对象
        Configuration.Builder cfg = new Configuration.Builder();
        cfg.setInAnimation(R.anim.crouton_in)
                .setOutAnimation(R.anim.crouton_out)
                .setDuration(1500);
        Crouton.showText(this, "修改配置后的Crouton", Style.CONFIRM, R.id.rl_root, cfg.build());
```
![修改配置](http://img.blog.csdn.net/20170610174509172?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvTGluZHJvaWQyMA==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

# 8、同时修改外观和配置
如果我们需要同时改动外观和配置怎么弄呢？这个也很简单，在完成了外观和配置的设置之后，调用Style构造器中的`setConfiguration`方法传入一个Configuration实例即可：
```java
sb.setConfiguration(cfg.build());
```
大体效果如下：

![同时修改外观和配置](http://img.blog.csdn.net/20170613103227176?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvTGluZHJvaWQyMA==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

# 9、Crouton的销毁
Crouton的销毁可以调用两个方法（下面是我的理解）：
- `cancelAllCroutons`:销毁队列中的Crouton，如果当前有Crouton在展示，那么它会立即消失，后面的Crouton也不会再显示；
- `clearCroutonsForActivity`：销毁某个Activity中所有的Crouton。

官方的建议是在Activity销毁，也就是onDestroy方法执行时调用`cancelAllCroutons`方法，确保Crouton不再占用系统资源。

# 10、总结
大家应该都知道Crouton的用法了。我们学习了如何让Crouton在根布局和子布局上显示，也学习了如何修改它的外观和配置，相信这些可以满足我们大部分需求了。如果你有更好玩的用法，欢迎给我留言。
源码的GitHub地址：
[SlidingToast](https://github.com/Lindroy/SlidingToast "SlidingToast")


# 参考文章
[Crouton的GitHub地址](https://github.com/keyboardsurfer/Crouton "Crouton的GitHub地址")

[Crouton介绍文档](https://speakerdeck.com/keyboardsurfer/crouton-devfest-berlin-2012 "Crouton介绍文档")

[Android自定义滑动Toast](https://segmentfault.com/a/1190000009748693?share_user=1030000006707723 "Android自定义滑动Toast")
