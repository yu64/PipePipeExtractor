package org.schabi.newpipe.extractor.services.bilibili.extractors;

import static org.schabi.newpipe.extractor.services.bilibili.BilibiliService.QUERY_LIVEROOM_STATUS_URL;
import static org.schabi.newpipe.extractor.services.bilibili.BilibiliService.QUERY_USER_INFO_URL;
import static org.schabi.newpipe.extractor.services.bilibili.BilibiliService.getUpToDateHeaders;
import static org.schabi.newpipe.extractor.services.bilibili.utils.buildUserVideosUrl;
import static org.schabi.newpipe.extractor.services.bilibili.utils.getNextPageFromCurrentUrl;
import static org.schabi.newpipe.extractor.services.bilibili.utils.requestUserSpaceResponse;

import com.grack.nanojson.JsonArray;
import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonParserException;

import org.schabi.newpipe.extractor.Page;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.channel.ChannelExtractor;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.ChannelTabs;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;
import org.schabi.newpipe.extractor.search.filter.Filter;
import org.schabi.newpipe.extractor.search.filter.FilterItem;
import org.schabi.newpipe.extractor.stream.StreamInfoItem;
import org.schabi.newpipe.extractor.stream.StreamInfoItemsCollector;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

public class BilibiliChannelExtractor extends ChannelExtractor {
    JsonObject userVideoData = new JsonObject();
    JsonObject userInfoData = new JsonObject();
    JsonObject userLiveData = new JsonObject();

    public BilibiliChannelExtractor(StreamingService service, ListLinkHandler linkHandler) {
        super(service, linkHandler);
    }

    @Override
    public void onFetchPage(@Nonnull Downloader downloader) throws IOException, ExtractionException {
        try {
            Map<String, List<String>> headers = getUpToDateHeaders();
            String id = getId();

            userVideoData = requestUserSpaceResponse(downloader, buildUserVideosUrl(getUrl(), id), headers);

            userInfoData = requestUserSpaceResponse(downloader, QUERY_USER_INFO_URL + id, headers);

            userLiveData = requestUserSpaceResponse(downloader, QUERY_LIVEROOM_STATUS_URL + id, null);

        } catch (JsonParserException e) {
            e.printStackTrace(); // ignore because liveResponse may not exist
        }
    }

    @Nonnull
    @Override
    public String getName() throws ParsingException {
        return userInfoData.getObject("data").getObject("card").getString("name");
    }

    @Nonnull
    @Override
    public InfoItemsPage<StreamInfoItem> getInitialPage() throws IOException, ExtractionException {
        JsonArray results;
        results = userVideoData.getObject("data").getObject("list").getArray("vlist");

        final StreamInfoItemsCollector collector = new StreamInfoItemsCollector(getServiceId());
        if (results.size() == 0) {
            return new InfoItemsPage<>(collector, null);
        }
        if (userLiveData.getObject("data").getObject(getId()).getInt("live_status") != 0) {
            collector.commit(new BilibiliLiveInfoItemExtractor(userLiveData.getObject("data").getObject(getId()), 1));
        }
        for (int i = 0; i < results.size(); i++) {
            collector.commit(new BilibiliChannelInfoItemExtractor(results.getObject(i), getName(), getAvatarUrl()));
        }
        return new InfoItemsPage<>(collector, new Page(getNextPageFromCurrentUrl
                (getUrl(), "pn", 1, true, "1", "?")));
    }

    @Override
    public InfoItemsPage<StreamInfoItem> getPage(Page page) throws IOException, ExtractionException {
        try {
            Map<String, List<String>> headers = getUpToDateHeaders();
            String id = getId();
            Downloader downloader = getDownloader();

            userVideoData = requestUserSpaceResponse(downloader, buildUserVideosUrl(page.getUrl(), id), headers);

            userInfoData = requestUserSpaceResponse(downloader, QUERY_USER_INFO_URL + id, headers);

        } catch (JsonParserException e) {
            e.printStackTrace();  // ignore
        }

        JsonArray results;
        results = userVideoData.getObject("data").getObject("list").getArray("vlist");

        if (results.size() == 0) {
            return new InfoItemsPage<>(new StreamInfoItemsCollector(getServiceId()), null);
        }
        final StreamInfoItemsCollector collector = new StreamInfoItemsCollector(getServiceId());
        for (int i = 0; i < results.size(); i++) {
            collector.commit(new BilibiliChannelInfoItemExtractor(results.getObject(i), getName(), getAvatarUrl()));
        }
        return new InfoItemsPage<>(collector, new Page(getNextPageFromCurrentUrl(page.getUrl(), "pn", 1)));
    }

    @Override
    public String getAvatarUrl() throws ParsingException {
        return userInfoData.getObject("data").getObject("card").getString("face").replace("http:", "https:");
    }

    @Override
    public String getBannerUrl() throws ParsingException {
        return userInfoData.getObject("data").getObject("space").getString("l_img").replace("http:", "https:");
    }

    @Override
    public long getSubscriberCount() throws ParsingException {
        return userInfoData.getObject("data").getObject("card").getLong("fans");
    }

    @Override
    public String getDescription() throws ParsingException {
        return userInfoData.getObject("data").getObject("card").getString("sign");
    }

    @Nonnull
    @Override
    public String getUrl() throws ParsingException {
        return super.getUrl();
    }

    @Nonnull
    @Override
    public List<ListLinkHandler> getTabs() throws ParsingException {
        String url = "https://api.bilibili.com/x/polymer/space/seasons_series_list?mid=" + getLinkHandler().getId() + "&page_num=1&page_size=10";
        return Collections.singletonList(
                new ListLinkHandler(url, url, getLinkHandler().getId(),
                        Collections.singletonList(new FilterItem(Filter.ITEM_IDENTIFIER_UNKNOWN, ChannelTabs.PLAYLISTS)), null)
        );
    }
}
