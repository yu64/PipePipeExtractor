package org.schabi.newpipe.extractor.services.niconico.linkHandler;

import static org.schabi.newpipe.extractor.utils.Utils.UTF_8;

import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.SearchQueryHandlerFactory;
import org.schabi.newpipe.extractor.search.filter.Filter;
import org.schabi.newpipe.extractor.search.filter.FilterItem;
import org.schabi.newpipe.extractor.services.niconico.NiconicoService;
import org.schabi.newpipe.extractor.services.niconico.search.filter.NiconicoFilters;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class NiconicoSearchQueryHandlerFactory extends SearchQueryHandlerFactory {
    public static final int ITEMS_PER_PAGE = 10;
    private static final String SEARCH_URL
            = "https://api.search.nicovideo.jp/api/v2/snapshot/video/contents/search";

    private final NiconicoFilters searchFilters = new NiconicoFilters();

    @Override
    public String getUrl(final String id,
                         final List<FilterItem> selectedContentFilter,
                         final List<FilterItem> selectedSortFilter) throws ParsingException {

        searchFilters.setSelectedSortFilter(selectedSortFilter);
        searchFilters.setSelectedContentFilter(selectedContentFilter);

        final String filterQuery = searchFilters.evaluateSelectedFilters(null);

        try {
            return SEARCH_URL + "?q=" + URLEncoder.encode(id, UTF_8) + filterQuery
                    + "&fields=contentId,title,userId,channelId"
                    + ",viewCounter,lengthSeconds,thumbnailUrl,startTime"
                    + "&_offset=0"
                    + "&_limit=" + ITEMS_PER_PAGE
                    + "&_context=" + URLEncoder.encode(NiconicoService.APP_NAME, UTF_8);
        } catch (final UnsupportedEncodingException e) {
            throw new ParsingException("could not encode query.");
        }
    }

    @Override
    public Filter getAvailableContentFilter() {
        return searchFilters.getContentFilters();
    }

    @Override
    public FilterItem getFilterItem(final int filterId) {
        return searchFilters.getFilterItem(filterId);
    }
    @Override
    public Filter getAvailableSortFilter() {
        return searchFilters.getSortFilters();
    }

    @Override
    public Filter getContentFilterSortFilterVariant(final int contentFilterId) {
        return searchFilters.getContentFilterSortFilterVariant(contentFilterId);
    }
}