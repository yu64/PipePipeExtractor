package org.schabi.newpipe.extractor.channel;

import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.ListExtractor;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;
import org.schabi.newpipe.extractor.stream.StreamInfoItem;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

/*
 * Created by Christian Schabesberger on 25.07.16.
 *
 * Copyright (C) Christian Schabesberger 2016 <chris.schabesberger@mailbox.org>
 * ChannelExtractor.java is part of NewPipe.
 *
 * NewPipe is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * NewPipe is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with NewPipe.  If not, see <http://www.gnu.org/licenses/>.
 */

public abstract class ChannelExtractor extends ListExtractor<StreamInfoItem> {

    public static final long UNKNOWN_SUBSCRIBER_COUNT = -1;

    public ChannelExtractor(final StreamingService service, final ListLinkHandler linkHandler) {
        super(service, linkHandler);
    }


    @Nonnull
    public List<Image> getAvatars() throws ParsingException {
        String avatarUrl = getAvatarUrl();
        if (avatarUrl != null && !avatarUrl.isEmpty()) {
            Image image = new Image(avatarUrl, -1, -1, Image.ResolutionLevel.MEDIUM);
            List<Image> list = new java.util.ArrayList<>();
            list.add(image);
            return list;
        } else {
            return Collections.emptyList();
        }
    }

    @Nonnull
    public List<Image> getBanners() throws ParsingException {
        String avatarUrl = getBannerUrl();
        if (avatarUrl != null && !avatarUrl.isEmpty()) {
            Image image = new Image(avatarUrl, -1, -1, Image.ResolutionLevel.MEDIUM);
            List<Image> list = new java.util.ArrayList<>();
            list.add(image);
            return list;
        } else {
            return Collections.emptyList();
        }
    }

    public abstract String getAvatarUrl() throws ParsingException;

    public String getBannerUrl() throws ParsingException {
        return null;
    }

    public String getFeedUrl() throws ParsingException {
        return null;
    }

    public abstract long getSubscriberCount() throws ParsingException;

    public abstract String getDescription() throws ParsingException;

    public String getParentChannelName() throws ParsingException {
        return null;
    }

    public String getParentChannelUrl() throws ParsingException {
        return null;
    }

    public String getParentChannelAvatarUrl() throws ParsingException {
        return null;
    }

    public boolean isVerified() throws ParsingException {
        return false;
    }

    @Nonnull
    public List<ListLinkHandler> getTabs() throws ParsingException {
        return Collections.emptyList();
    }

    @Nonnull
    public List<String> getTags() throws ParsingException {
        return Collections.emptyList();
    }
}
