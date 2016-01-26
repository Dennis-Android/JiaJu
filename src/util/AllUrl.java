package util;

public class AllUrl {
   public static String URL_GBase = "http://www.jumeimiao.com";     //主页
   public static String URL_Base = URL_GBase + "/api";  //www.jumeimiao.com/api
   //1.轮播条	
   public static String	URL_Shouye_DiBu = URL_Base + "/s.ashx?m=ad";   
   //1.1点击轮播条
   public static String URL_Shouye_DiBu_1 = URL_Base + "/p.ashx?m=activedetail&activeId=";        //进入轮播条详情页1  上面列表 
   public static String URL_Shouye_DiBu_2 = URL_Base+"/p.ashx?m=getProductList&id="+"activeId"+"&sort=0&pageNum=1&pageSize=20";  //下面的列表
   //默认,最新，热销，价格，高价格
   public static String URL_TAB_1=URL_Base+"/p.ashx?m=getProductList&id="+"activeId"+"&sort=0&pageNum=1&pageSize=20";
   public static String URL_TAB_2=URL_Base+"/p.ashx?m=getProductList&id="+"activeId"+"&sort=1&pageNum=1&pageSize=20";
   public static String URL_TAB_3=URL_Base+"/p.ashx?m=getProductList&id="+"activeId"+"&sort=2&pageNum=1&pageSize=20";
   public static String URL_TAB_4=URL_Base+"/p.ashx?m=getProductList&id="+"activeId"+"&sort=3&pageNum=1&pageSize=20";
   public static String URL_TAB_5=URL_Base+"/p.ashx?m=getProductList&id="+"activeId"+"&sort=4&pageNum=1&pageSize=20";
   
   //2.小横条 0元购物，每日爆款，止痛贴

   public static String URL_qiang=URL_Base+"/p.ashx?m=activedetail&activeId=61";  // 0元购物
   public static String URL_Find = URL_GBase + "/wap/pb.html";    //每日精选
   public static String URL_BannerList = URL_Base + "/u.ashx?m=BannerList&Position=1"; //止痛贴tab图片
   public static String URL_BaskOrderList = URL_Base + "/u.ashx?m=BaskOrderList";    //止痛贴的推荐listview
   
   //3.轮播下面的图   
   public static String URL_FirstBannerList = URL_Base + "/u.ashx?m=BannerList&Position=2";  //轮播下面的图 
   public static String URL_haiwai=URL_Base+"/p.ashx?a=gfeng&m=activelist&brandId=4";  //海外家居
   
   //4.下面的listview，  
   public static String URL_Shouye_list = URL_Base + "/p.ashx?a=gfeng&m=activelist&brandId=0"; 

   //5.分类界面的Listview和Gridview的数据
   public static String URL_fenlei="http://www.jumeimiao.com/api/p.ashx?m=category";    
   
   public static String  URL_GetCode = URL_Base + "/u.ashx?a=gfeng&m=getSafetyCode&tel=";      //接受手机短信验证码，后面接手机号
   
   public static String  URL_AboutUs = URL_GBase + "/wap/about.html";
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
}
