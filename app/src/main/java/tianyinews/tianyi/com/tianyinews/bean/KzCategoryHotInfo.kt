package tianyinews.tianyi.com.tianyinews.bean

/**
 * @Author:         renzhengwei
 * @CreateDate:     2021/7/9 5:31 下午
 * @Description:
 */
data class KzCategoryHotInfo(
    val tabList: List<CategoryHotItem>? = null,
    val defaultIdx: Int = 0
)