package tianyinews.tianyi.com.tianyinews.bean

import com.trs.channellib.channel.channel.ChannelEntity
import com.trs.channellib.channel.channel.ChannelEntity.ChannelEntityCreater
import java.io.Serializable

/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/3/22.
 */
data class MyChannel(
    var title: String = "",
    var requestTitle: String = "",
    var channelType: Int = 0,
    var type: String = "",
    var isFix: Int = 0,
    var url: String = "",
    var url_footer: String = "",
    var isSubscrible: Int = 0
) : ChannelEntityCreater, Serializable {

    override fun createChannelEntity(): ChannelEntity {
        val entity = ChannelEntity()
        entity.isFixed = isFix == 1
        entity.name = title
        return entity
    }
}