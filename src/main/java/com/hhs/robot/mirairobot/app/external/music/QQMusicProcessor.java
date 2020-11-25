package com.hhs.robot.mirairobot.app.external.music;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hhs.robot.mirairobot.app.dto.MusicSearchDTO;
import com.hhs.robot.mirairobot.app.dto.QQMusicCardDTO;
import com.hhs.robot.mirairobot.app.dto.QQMusicCardParaDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hhs
 * @since 2020/11/25 10:28
 * qq音乐相关
 * <p>
 * {
 * "keyword":"曹操",
 * "priority":0,
 * "qc":[
 * <p>
 * ],
 * "semantic":{
 * "curnum":0,
 * "curpage":1,
 * "list":[
 * <p>
 * ],
 * "totalnum":0
 * },
 * "song":{
 * "curnum":30,
 * "curpage":1,
 * "list":[
 * {
 * "action":Object{...},
 * "album":Object{...},
 * "chinesesinger":0,
 * "desc":"",
 * "desc_hilight":"",
 * "docid":"4062374665433232312",
 * "es":"",
 * "file":Object{...},
 * "fnote":4009,
 * "genre":1,
 * "grp":Array[5],
 * "id":101014,
 * "index_album":2,
 * "index_cd":0,
 * "interval":242,
 * "isonly":0,
 * "ksong":Object{...},
 * "language":0,
 * "lyric":"",
 * "lyric_hilight":"",
 * "mid":"0003y8uR1ZZwOI",
 * "mv":Object{...},
 * "name":"曹操",
 * "newStatus":2,
 * "nt":409611662,
 * "ov":0,
 * "pay":Object{...},
 * "pure":0,
 * "sa":0,
 * "singer":Array[1],
 * "status":0,
 * "subtitle":"",
 * "t":1,
 * "tag":11,
 * "tid":0,
 * "time_public":"2006-02-17",
 * "title":"曹操",
 * "title_hilight":"<em>曹操</em>",
 * "type":0,
 * "url":"http://stream10.qqmusic.qq.com/101014.wma",
 * "ver":0,
 * "volume":Object{...}
 * }
 * .......
 * ],
 * "totalnum":150
 * },
 * "tab":0,
 * "taglist":[
 * <p>
 * ],
 * "totaltime":0,
 * "zhida":{
 * "type":1,
 * "zhida_singer":{
 * "albumNum":50,
 * "hotalbum":[
 * {
 * "albumID":852443,
 * "albumMID":"001IV22P1RDX7p",
 * "albumName":"新地球",
 * "albumname_hilight":"新地球"
 * },
 * {
 * "albumID":36160,
 * "albumMID":"002g6zv02X7SNi",
 * "albumName":"JJ陆",
 * "albumname_hilight":"JJ陆"
 * },
 * {
 * "albumID":62342,
 * "albumMID":"000TuW8h0AH2n4",
 * "albumName":"她说 概念自选辑",
 * "albumname_hilight":"她说 概念自选辑"
 * },
 * {
 * "albumID":421264,
 * "albumMID":"001gqOnU3DTg2S",
 * "albumName":"因你而在",
 * "albumname_hilight":"因你而在"
 * },
 * {
 * "albumID":8036,
 * "albumMID":"000y5gq7449K9I",
 * "albumName":"第二天堂",
 * "albumname_hilight":"第二天堂"
 * },
 * {
 * "albumID":8440,
 * "albumMID":"002aaUOS24kcwh",
 * "albumName":"曹操",
 * "albumname_hilight":"曹操"
 * },
 * {
 * "albumID":88971,
 * "albumMID":"002PQCmo2azasb",
 * "albumName":"学不会",
 * "albumname_hilight":"学不会"
 * },
 * {
 * "albumID":8035,
 * "albumMID":"001HwwNH1inlz9",
 * "albumName":"编号89757",
 * "albumname_hilight":"编号89757"
 * },
 * {
 * "albumID":54866,
 * "albumMID":"002C0kX720gMQi",
 * "albumName":"100天",
 * "albumname_hilight":"100天"
 * },
 * {
 * "albumID":59071,
 * "albumMID":"004HNY674Nu68w",
 * "albumName":"100天·Love音乐实录",
 * "albumname_hilight":"100天·Love音乐实录"
 * }
 * ],
 * "hotsong":[
 * {
 * "f":"101555425|可惜没如果|4286|林俊杰|852443|新地球|0|298|-1|1|0|11933977|4773709|0|0|0|33930173|6619277|7220655|0|004295Et37taLD|001BLpXF2DyJe2|001IV22P1RDX7p|0|4009",
 * "songID":101555425,
 * "songMID":"004295Et37taLD",
 * "songName":"可惜没如果",
 * "songname_hilight":"可惜没如果"
 * },
 * {
 * "f":"9063002|江南|4286|林俊杰|8036|第二天堂|0|267|-1|1|0|10720847|4288455|0|0|0|31386542|6600445|6445069|0|004TXEXY2G2c7C|001BLpXF2DyJe2|000y5gq7449K9I|0|4009",
 * "songID":9063002,
 * "songMID":"004TXEXY2G2c7C",
 * "songName":"江南",
 * "songname_hilight":"江南"
 * },
 * {
 * "f":"5063375|修炼爱情|4286|林俊杰|421264|因你而在|0|287|-1|1|0|11482581|4593151|0|0|0|33112408|6544206|6943560|0|002CxSLT41D5tD|001BLpXF2DyJe2|001gqOnU3DTg2S|0|4009",
 * "songID":5063375,
 * "songMID":"002CxSLT41D5tD",
 * "songName":"修炼爱情",
 * "songname_hilight":"修炼爱情"
 * },
 * {
 * "f":"102388808|那些你很冒险的梦|4286|林俊杰|88971|学不会|0|245|-1|1|0|9822009|3929020|0|0|0|27309033|5425391|5914397|0|002kADrZ01iC2L|001BLpXF2DyJe2|002PQCmo2azasb|0|4009",
 * "songID":102388808,
 * "songMID":"002kADrZ01iC2L",
 * "songName":"那些你很冒险的梦",
 * "songname_hilight":"那些你很冒险的梦"
 * },
 * {
 * "f":"95292|美人鱼|4286|林俊杰|8036|第二天堂|0|254|-1|1|0|10162873|4065267|0|0|0|31260476|6529468|6126574|0|002ASCKm3ROw7t|001BLpXF2DyJe2|000y5gq7449K9I|0|4009",
 * "songID":95292,
 * "songMID":"002ASCKm3ROw7t",
 * "songName":"美人鱼",
 * "songname_hilight":"美人鱼"
 * },
 * {
 * "f":"648231|背对背拥抱|4286|林俊杰|54866|100天|0|238|-1|1|0|9558500|3823690|0|0|0|27033876|5405643|5733708|0|002E8ebM3TFWmA|001BLpXF2DyJe2|002C0kX720gMQi|0|4009",
 * "songID":648231,
 * "songMID":"002E8ebM3TFWmA",
 * "songName":"背对背拥抱",
 * "songname_hilight":"背对背拥抱"
 * },
 * {
 * "f":"277438634|交换余生|4286|林俊杰|14551265|交换余生|0|276|-1|1|0|11056263|4422624|0|0|0|33214612|6523885|6674216|0|000CdfvT1LMVP3|001BLpXF2DyJe2|004TvSd03uUezv|0|4009",
 * "songID":277438634,
 * "songMID":"000CdfvT1LMVP3",
 * "songName":"交换余生",
 * "songname_hilight":"交换余生"
 * },
 * {
 * "f":"101014|曹操|4286|林俊杰|8440|曹操|0|242|-1|1|0|9683259|3873417|0|0|0|29324795|5200951|5849025|0|0003y8uR1ZZwOI|001BLpXF2DyJe2|002aaUOS24kcwh|0|4009",
 * "songID":101014,
 * "songMID":"0003y8uR1ZZwOI",
 * "songName":"曹操",
 * "songname_hilight":"曹操"
 * },
 * {
 * "f":"102415346|她说|4286|林俊杰|62342|她说 概念自选辑|0|320|-1|1|0|12824234|5129815|0|0|0|31302528|6690182|7724461|0|001IpbDW34m1Gy|001BLpXF2DyJe2|000TuW8h0AH2n4|0|4009",
 * "songID":102415346,
 * "songMID":"001IpbDW34m1Gy",
 * "songName":"她说",
 * "songname_hilight":"她说"
 * },
 * {
 * "f":"105388642|醉赤壁|4286|林俊杰|36160|JJ陆|0|281|-1|1|0|11264194|4505794|0|0|0|32170188|6216865|6783259|0|003UTRfZ12wGOs|001BLpXF2DyJe2|002g6zv02X7SNi|0|4009",
 * "songID":105388642,
 * "songMID":"003UTRfZ12wGOs",
 * "songName":"醉赤壁",
 * "songname_hilight":"醉赤壁"
 * }
 * ],
 * "mvNum":735,
 * "singerID":4286,
 * "singerMID":"001BLpXF2DyJe2",
 * "singerName":"林俊杰",
 * "singerPic":"http://y.gtimg.cn/music/photo_new/T001R150x150M000001BLpXF2DyJe2.jpg",
 * "singername_hilight":"林俊杰",
 * "songNum":678
 * }
 * }
 * }
 * <p>
 * <p>
 * <p>
 * 先实现功能... 丑就丑吧 改的很烦
 */
@Slf4j
@Service
public class QQMusicProcessor implements MusicProcessor {

    private CloseableHttpClient httpClient = HttpClientBuilder.create().build();

    /**
     * 搜索url p页数 n条数 w关键字
     */
    private String searchUrl = "https://c.y.qq.com/soso/fcgi-bin/client_search_cp?new_json=1&p=1&n=20&w=";

    public String searchMusic(MusicSearchDTO searchDTO) {
        String songName = searchDTO.getSongName();
        HttpGet httpGet = new HttpGet(searchUrl + songName);
        try {

            CloseableHttpResponse execute = httpClient.execute(httpGet);
            String json = EntityUtils.toString(execute.getEntity(), "utf-8");
            // 去除callback( 和 )
            json = StringUtils.replace(StringUtils.replace(json, "callback(", ""), "})", "}");
            log.info("qq音乐搜索结果:{}", json);
            JSONObject jsonObject = JSON.parseObject(json);
            JSONObject data = jsonObject.getJSONObject("data");
            String cardJSON = parseCardDTO(data, searchDTO);
            if (cardJSON != null) {
                // 组卡片
                return cardJSON;
            }
        } catch (Exception e) {
            log.error("qq音乐搜歌异常!", e);
        }
        return null;
    }

    private String parseCardDTO(JSONObject data, MusicSearchDTO searchDTO) {

        String songName = searchDTO.getSongName();
        String singerName = searchDTO.getSingerName();
        if (data != null) {
            // 解析音乐
            JSONObject song = data.getJSONObject("song");
            JSONArray list = song.getJSONArray("list");
            for (Object o : list) {
                QQMusicCardParaDTO qqMusicCardDTO = new QQMusicCardParaDTO();
                JSONObject songInfo = (JSONObject) o;
                String title = songInfo.getString("title");

                // 查歌手
                JSONArray singer = songInfo.getJSONArray("singer");
                List<String> singerNameList = singer.stream().map(obj -> {
                    JSONObject ssObj = (JSONObject) obj;
                    return ssObj.getString("name");
                }).collect(Collectors.toList());

                if (songName.equals(title)) {

                    // 设置歌手
                    qqMusicCardDTO.setSonger(singerNameList);

                    String mid = "";
                    String pmid = "";

                    // 判断有没有歌手
                    if (singerName != null && !singerName.isEmpty()) {
                        if (singerNameList.contains(singerName)) {
                            // 就是这个
                            // 获取mid 用于音乐链接
                            mid = songInfo.getString("mid");
                            // 获取album.pmid 用于预览图
                            JSONObject album = songInfo.getJSONObject("album");
                            if (album != null) {
                                pmid = album.getString("pmid");
                            }

                            // 可以返回了
                            qqMusicCardDTO.setTitle(title);
                            qqMusicCardDTO.setMid(mid);
                            qqMusicCardDTO.setPmid(pmid);

                            // 组装卡片dto
                            String s = convertMusicCard(qqMusicCardDTO);
                            if (s == null) {
                                continue;
                            } else {
                                return s;
                            }
                        }
                    } else {
                        // 不用查歌手,也直接返回
                        // 获取mid 用于音乐链接
                        mid = songInfo.getString("mid");
                        // 获取album.pmid 用于预览图
                        JSONObject album = songInfo.getJSONObject("album");
                        if (album != null) {
                            pmid = album.getString("pmid");
                        }
                        // 可以返回了
                        qqMusicCardDTO.setTitle(title);
                        qqMusicCardDTO.setMid(mid);
                        qqMusicCardDTO.setPmid(pmid);

                        // 组装卡片dto
                        String s = convertMusicCard(qqMusicCardDTO);
                        if (s == null) {
                            continue;
                        } else {
                            return s;
                        }
                    }
                }
            }
        }

        return null;
    }


    public String convertMusicCard(QQMusicCardParaDTO qqMusicCardDTO) {
        // token c72f1976e959388d785a255c824feb5f


        // 获取歌曲播放地址
        String tokenUrl = "https://u.y.qq.com/cgi-bin/musicu.fcg?format=json&data=%7B%22req_0%22%3A%7B%22module%22%3A%22vkey.GetVkeyServer%22%2C%22method%22%3A%22CgiGetVkey%22%2C%22param%22%3A%7B%22guid%22%3A%22358840384%22%2C%22songmid%22%3A%5B%22${songMid}%22%5D%2C%22songtype%22%3A%5B0%5D%2C%22uin%22%3A%221443481947%22%2C%22loginflag%22%3A1%2C%22platform%22%3A%2220%22%7D%7D%2C%22comm%22%3A%7B%22uin%22%3A%2218585073516%22%2C%22format%22%3A%22json%22%2C%22ct%22%3A24%2C%22cv%22%3A0%7D%7D";

        String replace = tokenUrl.replace("${songMid}", qqMusicCardDTO.getMid());

        log.info("查token url={}", replace);

        String json;
        try {
            HttpGet httpGet = new HttpGet(replace);
            CloseableHttpResponse execute = httpClient.execute(httpGet);
            json = EntityUtils.toString(execute.getEntity());
        } catch (Exception e) {
            log.error("查token异常!", e);
            return null;
        }

        JSONObject jsonObject = JSON.parseObject(json);
        JSONObject req_0 = jsonObject.getJSONObject("req_0");
        JSONObject data = req_0.getJSONObject("data");

        JSONArray sip = data.getJSONArray("sip");
        String main = (String) sip.get(0);

        JSONArray midurlinfoList = data.getJSONArray("midurlinfo");
        JSONObject mindUrlInfo = (JSONObject) midurlinfoList.get(0);
        String purl = mindUrlInfo.getString("purl");
        if (purl == null || purl.equals("")) {
            return null;
        }

        // 拼接url
        String musicUrl = main + purl;


        JSONObject config = new JSONObject();
        config.put("autosize", true);
        config.put("ctime", System.currentTimeMillis());
        config.put("forward", true);
        config.put("token", "a8f1d1c8864c38d807b1b58a4f0772fe");
        config.put("type", "normal");

        JSONObject music = new JSONObject();
        music.put("action", "");
        music.put("android_pkg_name", "");
        music.put("app_type", 1);
        music.put("appid", 100497308);
        // 描述-可以填歌手
        music.put("desc", String.join("-", qqMusicCardDTO.getSonger()));
        // jumpUrl
        music.put("jumpUrl", "https://i.y.qq.com/v8/playsong.html?songtype=0&songmid=" + qqMusicCardDTO.getMid());
        // musicUrl
        music.put("musicUrl", musicUrl);
        // preview
        music.put("preview", "http://y.gtimg.cn/music/photo_new/T002R180x180M000" + qqMusicCardDTO.getPmid());
        music.put("sourceMsgId", "0");
        music.put("source_icon", "");
        music.put("source_url", "");
        music.put("tag", "QQ音乐");
        // 标题
        music.put("title", qqMusicCardDTO.getTitle());

        JSONObject meta = new JSONObject();
        meta.put("music", music);


        JSONObject extra = new JSONObject();
        extra.put("app_type", 1);
        extra.put("appid", 100497308);
        extra.put("uin", 3522521295L);

        QQMusicCardDTO cardDTO = new QQMusicCardDTO();
        cardDTO.setApp("com.tencent.structmsg");
        cardDTO.setConfig(config);
        cardDTO.setDesc("音乐");
        cardDTO.setExtra(extra);
        cardDTO.setMeta(meta);
        cardDTO.setPrompt("[分享]" + qqMusicCardDTO.getTitle());
        cardDTO.setVer("0.0.0.1");
        cardDTO.setView("music");

        return JSON.toJSONString(cardDTO);

    }

}
