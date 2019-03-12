package com.tubitv.tubitv.Enomus

/**
 * Created by vburian on 3/6/19.
 */
enum class DeepLinks (val value:String) {
    REQUIREMENTS("https://tubitv.atlassian.net/wiki/spaces/EC/pages/770113559/Testing+deep+link+on+mobile"),
    COMAND_FOR_DEEPLINK(" am start -W -a android.intent.action.VIEW -d "),
    ACTION_CATEGORY("http://tubitv.com/category/action?utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"),
    FOR_SERIAL("https://tubitv.com/series/2076?utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"),
    FOR_COMEDY("https://tubitv.com/category/comedy?utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"),
    FOR_DRAMA(" tubitv://media-browse?categoryId=1291\\&utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content "),
    FOR_MOVIE_OLDBOY ("http://tubitv.com/movies/294111?utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"),
    FOR_MOVIE_IGOR(" https://link.tubi.tv/t1XXhN28RR "),
    SERIAL_DEAD_LIKE_ME("https://link.tubi.tv/kCXwWs68RR"),
    MOVIE_WOMAN_THOU_ART_LOOSED("https://link.tubi.tv/bLLzoR10nT"),
    MOVIE_THE_HOLLOW ("http://tubitv.com/movies/435625?utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"),
    MOVIE_OLDBOY_UTM("https://tubitv.com/video/294111?utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"),
    WILD_AT_HEART_UTM_SERIAL("http://tubitv.com/tv-shows/203?utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"),
    SERIAL_MC_LEODS_DAUGHTERS_UTM("https://link.tubi.tv/vInFMDv8hT"),
    SERIAL_MC_LEODS_DAUGHTERS("http://tubitv.com/series/337?utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"),
    SERIAL_DOG_THE_BOUNTY_HUNTER_UTM("https://tubitv.com/series/2076?utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"),
    FOR_CATEGORY_ANIME_UTM(" tubitv://media-browse?categoryId=anime\\&utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"),
    FOR_CATEGORY_ANIME_UTM2("https://tubitv.com/category/anime?utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"),
    FOR_PLAYBACK_UTM("tubitv://media-playback?contentId=294111\\&contentType=video\\&campaign=holloween\\&utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content "),
    ANOTHER_DEEPLINK_WITH_UTM("http://tubitv.com/movies/435625?utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"),
    FOR_MOVIE_WOMAN_THOU_ART_LOOSED("http://tubitv.com/movies/456403?utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content"),
    FOR_MOVIE_WOMAN_THOU_AER_LOOSED_UTM("https://link.tubi.tv/bLLzoR10nT")
    //val deepLinkForPlayback="tubitv://media-playback?contentId=294111\\&contentType=movie\\&campaign=holloween\\&utm_campaign=cam\\&utm_source=sour\\&utm_medium=med\\&utm_content=content "


}