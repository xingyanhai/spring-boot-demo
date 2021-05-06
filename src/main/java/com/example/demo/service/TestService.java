package com.example.demo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pugwoo.wooutils.json.JSON;
import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TestService {

    /**
     * @param topicId
     * @return
     */
    public String path = "D:\\egg-example-xyh-output\\zhihu-output\\essenceAnswer\\";

    public String getTopic(Integer topicId) throws IOException, JSONException {

        if (topicId == null) {
            throw new RuntimeException("topicId不能为空");
        }


//        FileInputStream fileInputStream = new FileInputStream(file);
//        InputStreamReader treader = new InputStreamReader(fileInputStream);
//        BufferedReader tBff = new BufferedReader(treader);
//
//        StringBuilder strAll = new StringBuilder();
//        String lineText = null;
//        //读取内容为null则表示读到了文件末尾
//        while ((lineText = tBff.readLine()) != null) {
//            strAll.append(lineText);
//        }
//        tBff.close();

        String path = this.path + "essenceAnswer_" + topicId + ".json";
        String fileJsonStr = this.getFileContent(path);
        StringBuilder sb = new StringBuilder();
        List<Map<String, Object>> list = (List<Map<String, Object>>) JSON.parse(fileJsonStr);
        for (Map<String, Object> item : list) {
            Map<String, Object> target = (Map<String, Object>) item.get("target");
            String content = target.get("content").toString();
            sb.append(content);
        }
        String css = "<style>body{width:800px;margin:0 auto!important;}</style><link href=\"https://static.zhihu.com/heifetz/column.app.216a26f4.e6262f57189fab22e132.css\" crossorigin=\"\" rel=\"stylesheet\"><link rel=\"stylesheet\" type=\"text/css\" href=\"https://static.zhihu.com/heifetz/column.user-hover-card.216a26f4.a42bace18886d2c57d1d.css\" crossorigin=\"anonymous\"><link rel=\"stylesheet\" type=\"text/css\" href=\"https://static.zhihu.com/heifetz/column.Labels.216a26f4.7d19d2afdc588e36471f.css\" crossorigin=\"anonymous\"><link rel=\"stylesheet\" type=\"text/css\" href=\"https://static.zhihu.com/heifetz/column.GoodsRecommendGoodsCardList.216a26f4.3a73063149de8a52c56f.css\" crossorigin=\"anonymous\"><link rel=\"stylesheet\" type=\"text/css\" href=\"https://static.zhihu.com/heifetz/column.modals.216a26f4.30817b7d14bafaeba013.css\" crossorigin=\"anonymous\"><link rel=\"stylesheet\" type=\"text/css\" href=\"https://static.zhihu.com/heifetz/column.comments-modals.216a26f4.39d2b57c6fd2cae24672.css\" crossorigin=\"anonymous\"><link rel=\"stylesheet\" type=\"text/css\" href=\"https://static.zhihu.com/heifetz/column.richinput.216a26f4.ebdeb881e8ab7e59ca0d.css\" crossorigin=\"anonymous\">";
        return css + sb.toString();


//        return this.getAnswerContent(root);

    }

    private String getFileContent(String path) throws IOException {

        File file = new File(path);
        if (!file.exists()) {
            return "文件不存在！";
        }
        ObjectMapper objMap = new ObjectMapper();
        JsonNode root = objMap.readTree(file);
        return root.toString();
    }

    private String getAnswerContent(JsonNode node) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < node.size(); i++) {
            String item = node.get(i).path("target").path("content").asText();
            str.append(item);
        }
        return str.toString();
    }

    private String getAnswerTitle(JsonNode node) {
        ArrayList<JsonNode> list = new ArrayList<>();
        for (int i = 0; i < node.size(); i++) {
            JsonNode item = node.get(i).path("target").path("question").path("title");
            list.add(item);
        }
        return list.toString();
    }

    public String searchQuestion(String search) {
        System.out.println(search);
        List<String> fileList = this.getAllFile(this.path, true);
        StringBuilder sb = new StringBuilder();
        boolean searched = false;
        for (String e : fileList) {
            try {
                String fileJsonStr = this.getFileContent(e);
                List<Map<String, Object>> list = (List<Map<String, Object>>) JSON.parse(fileJsonStr);
                System.out.println(list.size());
                for (Map<String, Object> item : list) {
                    Map<String, Object> target = (Map<String, Object>) item.get("target");
                    Map<String, Object> question = (Map<String, Object>) target.get("question");

                    if (question == null) {
                        continue;
                    }
                    String title = question.get("title").toString();
                    System.out.println(title);
                    if (title.contains(search)) {
                        sb.append(title);
                        sb.append("<hr>");
                        sb.append(target.get("content").toString());
                        searched = true;
                        break;
                    }
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            if (searched) {
                break;
            }
        }

        return sb.toString();
    }


    /**
     * 获取路径下的所有文件/文件夹
     *
     * @param directoryPath  需要遍历的文件夹路径
     * @param isAddDirectory 是否将子文件夹的路径也添加到list集合中
     * @return
     */
    public static List<String> getAllFile(String directoryPath, boolean isAddDirectory) {
        List<String> list = new ArrayList<String>();
        File baseFile = new File(directoryPath);
        if (baseFile.isFile() || !baseFile.exists()) {
            return list;
        }
        File[] files = baseFile.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                if (isAddDirectory) {
                    list.add(file.getAbsolutePath());
                }
                list.addAll(getAllFile(file.getAbsolutePath(), isAddDirectory));
            } else {
                list.add(file.getAbsolutePath());
            }
        }
        return list;
    }

    public String sqlTest() {
        return "xxx";
    }

}


