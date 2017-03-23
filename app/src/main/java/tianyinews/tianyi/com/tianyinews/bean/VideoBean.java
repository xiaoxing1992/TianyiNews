package tianyinews.tianyi.com.tianyinews.bean;

import java.util.List;

/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/3/23.
 */

public class VideoBean {


    /**
     * topicImg : http://vimg1.ws.126.net/image/snapshot/2016/9/2/J/VC055SO2J.jpg
     * videosource : 新媒体
     * mp4Hd_url : http://flv2.bn.netease.com/videolib3/1703/23/ejiMc3177/HD/ejiMc3177-mobile.mp4
     * topicDesc : “一江春水向东流，爆笑乐活来了没忧愁”，一个主营内涵段子、爆笑趣图、幽默GIF；爆笑视频的乐活主题站！
     * topicSid : VC055SO2D
     * cover : http://vimg3.ws.126.net/image/snapshot/2017/3/I/H/VCF935GIH.jpg
     * title : 喵星人日常搞笑大合集，让你笑不停
     * playCount : 0
     * replyBoard : video_bbs
     * videoTopic : {"alias":"内涵段子、趣图和GIF乐活站","tname":"爆笑乐活派","ename":"T1471488127308","tid":"T1471488127308","topic_icons":"http://dingyue.nosdn.127.net/ltphGf5H9HTcrRVGwkak=Z9fEE5OrJ5SghfypZ2mREPYY1471488125396.jpg"}
     * sectiontitle :
     * replyid : CF8U9JHJ008535RB
     * description :
     * mp4_url : http://flv2.bn.netease.com/videolib3/1703/23/ejiMc3177/SD/ejiMc3177-mobile.mp4
     * length : 275
     * playersize : 1
     * m3u8Hd_url : http://flv2.bn.netease.com/videolib3/1703/23/ejiMc3177/HD/movie_index.m3u8
     * vid : VCF8U9JHJ
     * m3u8_url : http://flv2.bn.netease.com/videolib3/1703/23/ejiMc3177/SD/movie_index.m3u8
     * ptime : 2017-03-23 06:53:15
     * topicName : 爆笑乐活派
     */

    public String topicImg;
    public String videosource;
    public String mp4Hd_url;
    public String topicDesc;
    public String topicSid;
    public String cover;
    public String title;
    public int playCount;
    public String replyBoard;
    public VideoTopicBean videoTopic;
    public String sectiontitle;
    public String replyid;
    public String description;
    public String mp4_url;
    public int length;
    public int playersize;
    public String m3u8Hd_url;
    public String vid;
    public String m3u8_url;
    public String ptime;
    public String topicName;

    public static class VideoTopicBean {
        /**
         * alias : 内涵段子、趣图和GIF乐活站
         * tname : 爆笑乐活派
         * ename : T1471488127308
         * tid : T1471488127308
         * topic_icons : http://dingyue.nosdn.127.net/ltphGf5H9HTcrRVGwkak=Z9fEE5OrJ5SghfypZ2mREPYY1471488125396.jpg
         */

        public String alias;
        public String tname;
        public String ename;
        public String tid;
        public String topic_icons;
    }

}
