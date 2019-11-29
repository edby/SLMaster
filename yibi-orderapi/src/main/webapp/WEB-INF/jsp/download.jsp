<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head lang='zh-cmn'>
    <meta charset='utf-8'>
    <meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0' name='viewport'>
    <meta content='portrait' name='x5-orientation'>
    <meta content='true' name='x5-fullscreen'>
    <meta content='portrait' name='screen-orientation'>
    <meta content='yes' name='full-screen'>
    <meta content='webkit' name='renderer'>
    <meta property="wb:webmaster" content="29a506845615ac30">
    <meta content='IE=Edge' http-equiv='X-UA-Compatible'>
    <meta content='no-siteapp' http-equiv='Cache-Control'>
    <title>Download</title>
    <link href='${pageContext.request.contextPath}/css/index.css' rel='stylesheet'>
    <link href='${pageContext.request.contextPath}/css/index-mobile.css' id='mobileStyle' rel='stylesheet'>
    <!--[if gte IE 6]>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index-IE.css" />
    <![endif]-->
</head>

<body scroll="no">
    <div id='view'>
        <div id='background'></div>
        <div id='page'>
            <div class='page' id='page_download'>
                <div class='img' id='page_download_hand'></div>
                <div id='page_download_show'>
                    <div id='page_download_show_top'>
                        <div id='page_download_text'> 微信读书
                            <div>让阅读不再孤独</div>
                        </div>
                    </div>
                </div>
                <a href="#" id='page_download_button'>
                    <div id='page_download_button_top'>
                        <div id='page_download_mobile_iPhone'></div>
                        <div id='page_download_mobile_android'></div>
                    </div>
                </a>
                <div id="page_download_pc_button">
                    <a href="${downloadUrl}" id="page_download_button_android">
                        <div id="page_download_pc_android"></div>
                    </a>
                </div>
            </div>
            <div class='page' id='page_function1'>
                <div id='page_function1_text'>
                    <h1 id='page_function1_text_title'>精心打磨的阅读体验</h1>
                    <h2 id='page_function1_text_sub'>支持EPUB和TXT格式，随心个性化专属阅读风格。精心打磨，只为给你极致体验。</h2>
                </div>
                <div id='page_function1_show'>
                    <div id='page_function1_show_top'>
                        <div id='page_function1_book'>
                            <div id='page_function1_book_page3'></div>
                            <div id='page_function1_book_page2'></div>
                            <div id='page_function1_book_page1'></div>
                        </div>
                        <div id='page_function1_skin'>
                            <div id='page_function1_skin_icon'></div>
                            <div id='page_function1_skin_block1'></div>
                            <div id='page_function1_skin_block2'></div>
                            <div id='page_function1_skin_block3'></div>
                            <div id='page_function1_skin_block4'></div>
                        </div>
                        <div id='page_function1_font'></div>
                    </div>
                </div>
            </div>
            <div class='page' id='page_function2'>
                <div id='page_function2_text'>
                    <h1 id='page_function2_text_title'>和好友发现优质好书</h1>
                    <h2 id='page_function2_text_sub'>书海茫茫，不妨让微信好友来帮你完成筛选，发现下一本适合你的好书。</h2>
                </div>
                <div id='page_function2_show'>
                    <div id='page_function2_show_top'>
                        <div id='page_function2_face'>
                            <div id='page_function2_face_left'></div>
                            <div id='page_function2_face_right'></div>
                            <div id='page_function2_face_main'></div>
                            <div id='page_function2_face_text'></div>
                        </div>
                        <div id='page_function2_book'>
                            <div id='page_function2_book_07'></div>
                            <div id='page_function2_book_06'></div>
                            <div id='page_function2_book_05'></div>
                            <div id='page_function2_book_04'></div>
                            <div id='page_function2_book_03'></div>
                            <div id='page_function2_book_02'></div>
                            <div id='page_function2_book_01'></div>
                        </div>
                    </div>
                </div>
            </div>
            <div class='page' id='page_function3'>
                <div id='page_function3_text'>
                    <h1 id='page_function3_text_title'>和好友交流阅读感想</h1>
                    <h2 id='page_function3_text_sub'>在阅读中与好友的想法邂逅，跟好友交流你的阅读感想，碰撞出更多火花。</h2>
                </div>
                <div id='page_function3_show'>
                    <div id='page_function3_show_top'>
                        <div id='page_function3_talk'>
                            <div id='page_function3_talk_01'></div>
                            <div id='page_function3_talk_02'></div>
                            <div id='page_function3_talk_03'></div>
                            <div id='page_function3_talk_04'></div>
                            <div id='page_function3_talk_05'></div>
                            <div id='page_function3_talk_06'></div>
                            <div id='page_function3_book'></div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="copyrights">Collect from <a href="http://www.cssmoban.com/" >企业网站模板</a></div>
            <div class='page' id='page_function4'>
                <div id='page_function4_text'>
                    <h1 id='page_function4_text_title'>和好友比拼读书排行</h1>
                    <h2 id='page_function4_text_sub'>严肃的阅读游戏，在阅读中不仅收获知识，更收获与好友比拼的成就感。</h2>
                </div>
                <div id='page_function4_table'>
                    <div id='page_function4_table_top'>
                        <div id='page_function4_table_background'></div>
                        <div id='page_function4_table_face_01'></div>
                        <div id='page_function4_table_face_02'></div>
                        <div id='page_function4_table_face_03'></div>
                        <div id='page_function4_table_face_04'></div>
                        <div id='page_function4_table_face_05'></div>
                        <div id='page_function4_table_column_01'></div>
                        <div id='page_function4_table_column_02'></div>
                        <div id='page_function4_table_column_03'></div>
                        <div id='page_function4_table_column_04'></div>
                        <div id='page_function4_table_column_05'></div>
                        <div id='page_function4_table_time_01'></div>
                        <div id='page_function4_table_time_02'></div>
                        <div id='page_function4_table_time_03'></div>
                        <div id='page_function4_table_time_04'></div>
                        <div id='page_function4_table_time_05'></div>
                    </div>
                </div>
            </div>
        </div>
        <div id='pageControl'>
            <div class='pageControl' id='pageControl_button1'></div>
            <div class='pageControl' id='pageControl_button2'></div>
            <div class='pageControl' id='pageControl_button3'></div>
            <div class='pageControl' id='pageControl_button4'></div>
            <div class='pageControl' id='pageControl_button5'></div>
        </div>
    </div>
    <div id='logo'></div>
    <div id='arrow_right'>
        <div id='arrow_right_height'></div>
    </div>
    <div id='arrow_left'>
        <div id='arrow_left_height'></div>
    </div>
    <div class='animateArrowDown' id='arrow_down'></div>
    <div id='nav'>
        <a href='https://i.weread.qq.com/download?from=Website&type=iOS' id='nav_iPhone'>
            <div id='nav_iPhone_img'></div>
        </a>
        <a href='https://i.weread.qq.com/download?from=Website&type=Android' id='nav_android'>
            <div id='nav_android_img'></div>
        </a>
        <div id='nav_QRCode'>
            <div id='nav_QRCode_img'></div>
            <div id='nav_QRCode_hover'>
                <div id='nav_QRCode_hover_border'>
                    <div id='nav_QRCode_hover_img'></div>
                </div>
            </div>
        </div>
    </div>
    <div id='load'>
        <div class='load' id='load_function'></div>
        <div class='load' id='load_function1_page1'></div>
        <div class='load' id='load_function1_page2'></div>
        <div class='load' id='load_function2_book1'></div>
        <div class='load' id='load_function2_book2'></div>
        <div class='load' id='load_function2_book3'></div>
        <div class='load' id='load_function2_book4'></div>
        <div class='load' id='load_function2_book5'></div>
        <div class='load' id='load_function2_book6'></div>
        <div class='load' id='load_function2_book7'></div>
        <div class='load' id='load_function3_book'></div>
        <div class='load' id='load_function4_background'></div>
    </div>
    <script src='${pageContext.request.contextPath}/js/jquery.min.js'></script>
    <script src='${pageContext.request.contextPath}/js/index.js'></script>
</body>

</html>