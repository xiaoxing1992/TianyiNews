package tianyinews.tianyi.com.tianyinews.bean;

import com.trs.channellib.channel.channel.ChannelEntity;

/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/3/22.
 */

public class MyChannel implements ChannelEntity.ChannelEntityCreater {


    public String title;

    public int channelType;

    public int isFix;

    public String url;
    public String url_footer;

    public int isSubscrible;


    @Override
    public ChannelEntity createChannelEntity() {
        ChannelEntity entity = new ChannelEntity();
        entity.setFixed(isFix == 1);
        entity.setName(title);
        return entity;
    }
}
