package org.schabi.newpipe.extractor.services.niconico.extractors;

import org.jsoup.nodes.Element;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.localization.DateWrapper;
import org.schabi.newpipe.extractor.services.niconico.NiconicoService;
import org.schabi.newpipe.extractor.stream.StreamInfoItemExtractor;
import org.schabi.newpipe.extractor.stream.StreamType;

import javax.annotation.Nullable;

public class NiconicoLiveSearchInfoItemExtractor implements StreamInfoItemExtractor {
    Element data;
    public NiconicoLiveSearchInfoItemExtractor(Element e) {
        data = e;
    }

    @Override
    public String getName() throws ParsingException {
        return data.select("a[class*=___program-card-title-anchor___]").attr("title");
    }

    @Override
    public String getUrl() throws ParsingException {
        return data.select("a[class*=___program-card-title-anchor___]").attr("href");
    }

    @Override
    public String getThumbnailUrl() throws ParsingException {
        return data.select("img[class*=___program-card-thumbnail-image___]").attr("src");
    }

    @Override
    public StreamType getStreamType() throws ParsingException {
        return StreamType.LIVE_STREAM;
    }

    @Override
    public boolean isAd() throws ParsingException {
        return false;
    }

    @Override
    public long getDuration() throws ParsingException {
        return -1;
    }

    @Override
    public long getViewCount() throws ParsingException {
        return Long.parseLong(data.select("span[class*=___program-card-statistics-text___] > span").get(1).text());
    }

    @Override
    public String getUploaderName() throws ParsingException {
        return data.select("p[class*=___program-card-provider-name___] > a[class*=___program-card-provider-name-link___]").text();
    }

    @Override
    public String getUploaderUrl() throws ParsingException {
        return data.select("p[class*=___program-card-provider-name___] > a[class*=___program-card-provider-name-link___]")
                .attr("href").split("/live_programs")[0];
    }

    @Nullable
    @Override
    public String getUploaderAvatarUrl() throws ParsingException {
        return data.select("img[class*=___program-card-provider-icon-image___]").attr("src");
    }

    @Override
    public boolean isUploaderVerified() throws ParsingException {
        return false;
    }

    @Nullable
    @Override
    public String getTextualUploadDate() throws ParsingException {
        return data.select("span[class*=___program-card-statistics-text___] > span").first().text();
    }

    @Nullable
    @Override
    public DateWrapper getUploadDate() throws ParsingException {
        return null;
    }

    @Nullable
    @Override
    public String getShortDescription() throws ParsingException {
        return data.select("p[class*=___program-card-description___]").text();
    }
}
