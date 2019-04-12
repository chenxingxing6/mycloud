package com.example.controller;


import org.springframework.web.bind.annotation.*;


/**
 * 菜单
 */
@RestController
@RequestMapping("/api/")
public class MenuController {

    /**
     * 获取菜单
     * @return
     */
    @GetMapping("/getMenu")
    public String getMenu(){
        String result = "{\n" +
                "    \"code\":200,\n" +
                "    \"msg\":\"\",\n" +
                "    \"data\":[\n" +
                "        {\n" +
                "            \"id\":\"120\",\n" +
                "            \"pid\":0,\n" +
                "            \"title\":\"首页\",\n" +
                "            \"icon\":\"appstore-o\",\n" +
                "            \"url\":\"home\",\n" +
                "            \"file_path\":\"home\",\n" +
                "            \"params\":\"\",\n" +
                "            \"node\":\"#\",\n" +
                "            \"sort\":0,\n" +
                "            \"status\":1,\n" +
                "            \"create_by\":0,\n" +
                "            \"create_at\":\"2018-09-30 16:30:01\",\n" +
                "            \"is_inner\":false,\n" +
                "            \"values\":\"\",\n" +
                "            \"show_slider\":false,\n" +
                "            \"statusText\":\"使用中\",\n" +
                "            \"innerText\":\"导航\",\n" +
                "            \"fullUrl\":\"home\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\":\"121\",\n" +
                "            \"pid\":0,\n" +
                "            \"title\":\"网盘\",\n" +
                "            \"icon\":\"project\",\n" +
                "            \"url\":\"#\",\n" +
                "            \"file_path\":\"disk/files\",\n" +
                "            \"params\":\"\",\n" +
                "            \"node\":\"disk/files\",\n" +
                "            \"sort\":0,\n" +
                "            \"status\":1,\n" +
                "            \"create_by\":0,\n" +
                "            \"create_at\":\"0000-00-00 00:00:00\",\n" +
                "            \"is_inner\":true,\n" +
                "            \"values\":\"\",\n" +
                "            \"show_slider\":false,\n" +
                "            \"statusText\":\"使用中\",\n" +
                "            \"innerText\":\"导航\",\n" +
                "            \"fullUrl\":\"disk/files\",\n" +
                "            \"children\":[\n" +
                "             {\n" +
                "                    \"id\":\"126\",\n" +
                "                    \"pid\":121,\n" +
                "                    \"title\":\"文件\",\n" +
                "                    \"icon\":\"\",\n" +
                "                    \"url\":\"disk/files\",\n" +
                "                    \"file_path\":\"disk/files\",\n" +
                "                    \"params\":\"\",\n" +
                "                    \"node\":\"disk/files\",\n" +
                "                    \"sort\":0,\n" +
                "                    \"status\":1,\n" +
                "                    \"create_by\":0,\n" +
                "                    \"create_at\":\"0000-00-00 00:00:00\",\n" +
                "                    \"is_inner\":false,\n" +
                "                    \"values\":\"\",\n" +
                "                    \"show_slider\":false,\n" +
                "                    \"statusText\":\"使用中\",\n" +
                "                    \"innerText\":\"内页\",\n" +
                "                    \"fullUrl\":\"disk/files\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"id\":\"125\",\n" +
                "                    \"pid\":121,\n" +
                "                    \"title\":\"概览\",\n" +
                "                    \"icon\":\"\",\n" +
                "                    \"url\":\"disk/overview\",\n" +
                "                    \"file_path\":\"disk/overview\",\n" +
                "                    \"params\":\"\",\n" +
                "                    \"node\":\"disk/overview\",\n" +
                "                    \"sort\":0,\n" +
                "                    \"status\":1,\n" +
                "                    \"create_by\":0,\n" +
                "                    \"create_at\":\"0000-00-00 00:00:00\",\n" +
                "                    \"is_inner\":false,\n" +
                "                    \"values\":\"\",\n" +
                "                    \"show_slider\":false,\n" +
                "                    \"statusText\":\"使用中\",\n" +
                "                    \"innerText\":\"内页\",\n" +
                "                    \"fullUrl\":\"disk/overview\"\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\":\"160\",\n" +
                "            \"pid\":0,\n" +
                "            \"title\":\"分享\",\n" +
                "            \"icon\":\"team\",\n" +
                "            \"url\":\"share\",\n" +
                "            \"file_path\":\"share\",\n" +
                "            \"params\":\"\",\n" +
                "            \"node\":\"share/index\",\n" +
                "            \"sort\":0,\n" +
                "            \"status\":1,\n" +
                "            \"create_by\":0,\n" +
                "            \"create_at\":null,\n" +
                "            \"is_inner\":true,\n" +
                "            \"values\":\"\",\n" +
                "            \"show_slider\":false,\n" +
                "            \"statusText\":\"使用中\",\n" +
                "            \"innerText\":\"导航\",\n" +
                "            \"fullUrl\":\"share\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\":\"161\",\n" +
                "            \"pid\":0,\n" +
                "            \"title\":\"资源库\",\n" +
                "            \"icon\":\"setting\",\n" +
                "            \"url\":\"disk\",\n" +
                "            \"file_path\":\"disk\",\n" +
                "            \"params\":\"\",\n" +
                "            \"node\":\"disk/index\",\n" +
                "            \"sort\":0,\n" +
                "            \"status\":1,\n" +
                "            \"create_by\":0,\n" +
                "            \"create_at\":null,\n" +
                "            \"is_inner\":true,\n" +
                "            \"values\":\"\",\n" +
                "            \"show_slider\":false,\n" +
                "            \"statusText\":\"使用中\",\n" +
                "            \"innerText\":\"导航\",\n" +
                "            \"fullUrl\":\"disk\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\":\"169\",\n" +
                "            \"pid\":0,\n" +
                "            \"title\":\"关注用户\",\n" +
                "            \"icon\":\"appstore-o\",\n" +
                "            \"url\":\"followuser\",\n" +
                "            \"file_path\":\"followuser\",\n" +
                "            \"params\":\"\",\n" +
                "            \"node\":\"followuser/index\",\n" +
                "            \"sort\":0,\n" +
                "            \"status\":1,\n" +
                "            \"create_by\":0,\n" +
                "            \"create_at\":null,\n" +
                "            \"is_inner\":true,\n" +
                "            \"values\":\"\",\n" +
                "            \"show_slider\":false,\n" +
                "            \"statusText\":\"使用中\",\n" +
                "            \"innerText\":\"导航\",\n" +
                "            \"fullUrl\":\"followuser\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\":\"124\",\n" +
                "            \"pid\":0,\n" +
                "            \"title\":\"更多\",\n" +
                "            \"icon\":\"setting\",\n" +
                "            \"url\":\"#\",\n" +
                "            \"file_path\":\"#\",\n" +
                "            \"params\":\"\",\n" +
                "            \"node\":\"#\",\n" +
                "            \"sort\":100,\n" +
                "            \"status\":1,\n" +
                "            \"create_by\":0,\n" +
                "            \"create_at\":\"0000-00-00 00:00:00\",\n" +
                "            \"is_inner\":false,\n" +
                "            \"values\":\"\",\n" +
                "            \"show_slider\":1,\n" +
                "            \"statusText\":\"使用中\",\n" +
                "            \"innerText\":\"导航\",\n" +
                "            \"fullUrl\":\"#\",\n" +
                "            \"children\":[\n" +
                "                {\n" +
                "                    \"id\":\"148\",\n" +
                "                    \"pid\":124,\n" +
                "                    \"title\":\"个人管理\",\n" +
                "                    \"icon\":\"user\",\n" +
                "                    \"url\":\"#\",\n" +
                "                    \"file_path\":\"#\",\n" +
                "                    \"params\":\"\",\n" +
                "                    \"node\":\"#\",\n" +
                "                    \"sort\":0,\n" +
                "                    \"status\":1,\n" +
                "                    \"create_by\":0,\n" +
                "                    \"create_at\":\"0000-00-00 00:00:00\",\n" +
                "                    \"is_inner\":false,\n" +
                "                    \"values\":\"\",\n" +
                "                    \"show_slider\":1,\n" +
                "                    \"statusText\":\"使用中\",\n" +
                "                    \"innerText\":\"导航\",\n" +
                "                    \"fullUrl\":\"#\",\n" +
                "                    \"children\":[\n" +
                "                        {\n" +
                "                            \"id\":\"149\",\n" +
                "                            \"pid\":148,\n" +
                "                            \"title\":\"个人设置\",\n" +
                "                            \"icon\":\"\",\n" +
                "                            \"url\":\"account/setting/base\",\n" +
                "                            \"file_path\":\"account/setting/base\",\n" +
                "                            \"params\":\"\",\n" +
                "                            \"node\":\"project/index/editpersonal\",\n" +
                "                            \"sort\":0,\n" +
                "                            \"status\":1,\n" +
                "                            \"create_by\":0,\n" +
                "                            \"create_at\":\"0000-00-00 00:00:00\",\n" +
                "                            \"is_inner\":false,\n" +
                "                            \"values\":\"\",\n" +
                "                            \"show_slider\":1,\n" +
                "                            \"statusText\":\"使用中\",\n" +
                "                            \"innerText\":\"导航\",\n" +
                "                            \"fullUrl\":\"account/setting/base\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"id\":\"150\",\n" +
                "                            \"pid\":148,\n" +
                "                            \"title\":\"安全设置\",\n" +
                "                            \"icon\":\"\",\n" +
                "                            \"url\":\"account/setting/security\",\n" +
                "                            \"file_path\":\"account/setting/security\",\n" +
                "                            \"params\":\"\",\n" +
                "                            \"node\":\"project/index/editpersonal\",\n" +
                "                            \"sort\":0,\n" +
                "                            \"status\":1,\n" +
                "                            \"create_by\":0,\n" +
                "                            \"create_at\":null,\n" +
                "                            \"is_inner\":true,\n" +
                "                            \"values\":\"\",\n" +
                "                            \"show_slider\":1,\n" +
                "                            \"statusText\":\"使用中\",\n" +
                "                            \"innerText\":\"内页\",\n" +
                "                            \"fullUrl\":\"account/setting/security\"\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"id\":\"125\",\n" +
                "                    \"pid\":124,\n" +
                "                    \"title\":\"消息通知\",\n" +
                "                    \"icon\":\"unlock\",\n" +
                "                    \"url\":\"#\",\n" +
                "                    \"file_path\":\"#\",\n" +
                "                    \"params\":\"\",\n" +
                "                    \"node\":\"#\",\n" +
                "                    \"sort\":10,\n" +
                "                    \"status\":1,\n" +
                "                    \"create_by\":0,\n" +
                "                    \"create_at\":\"0000-00-00 00:00:00\",\n" +
                "                    \"is_inner\":false,\n" +
                "                    \"values\":\"\",\n" +
                "                    \"show_slider\":1,\n" +
                "                    \"statusText\":\"使用中\",\n" +
                "                    \"innerText\":\"导航\",\n" +
                "                    \"fullUrl\":\"#\",\n" +
                "                    \"children\":[\n" +
                "                        {\n" +
                "                            \"id\":\"126\",\n" +
                "                            \"pid\":125,\n" +
                "                            \"title\":\"系统公告\",\n" +
                "                            \"icon\":\"\",\n" +
                "                            \"url\":\"notify/notice\",\n" +
                "                            \"file_path\":\"notify/notice\",\n" +
                "                            \"params\":\"\",\n" +
                "                            \"node\":\"project/notify/index\",\n" +
                "                            \"sort\":0,\n" +
                "                            \"status\":1,\n" +
                "                            \"create_by\":0,\n" +
                "                            \"create_at\":\"0000-00-00 00:00:00\",\n" +
                "                            \"is_inner\":false,\n" +
                "                            \"values\":\"\",\n" +
                "                            \"show_slider\":true,\n" +
                "                            \"statusText\":\"使用中\",\n" +
                "                            \"innerText\":\"导航\",\n" +
                "                            \"fullUrl\":\"notify/notice\"\n" +
                "                        }\n" +
                "                    ]\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        return result;
    }
}
