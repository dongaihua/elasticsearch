package org.elasticsearch.rest.action.payment.purchase;

import org.elasticsearch.ElasticSearchException;
import org.elasticsearch.ElasticSearchIllegalArgumentException;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.search.SearchOperationThreading;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.IgnoreIndices;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.bytes.BytesReference;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.rest.*;
import org.elasticsearch.rest.action.support.RestActions;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;

import javax.swing.text.AbstractDocument;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.elasticsearch.common.unit.TimeValue.parseTimeValue;
import static org.elasticsearch.rest.RestRequest.Method.GET;
import static org.elasticsearch.rest.RestRequest.Method.POST;
import static org.elasticsearch.rest.RestStatus.BAD_REQUEST;
import static org.elasticsearch.rest.RestStatus.OK;
import static org.elasticsearch.rest.action.support.RestXContentBuilder.restContentBuilder;
import static org.elasticsearch.search.suggest.SuggestBuilder.termSuggestion;

/**
 * Created by jackiedong168 on 14-1-20.
 */
public class RestPurchaseAction extends BaseRestHandler {

    @Inject
    public RestPurchaseAction(Settings settings, Client client, RestController controller) {
        super(settings, client);
        controller.registerHandler(POST, "/_search", this);
    }

    @Override
    public void handleRequest(final RestRequest request, final RestChannel channel) {
        Map<String, String> map = parsePurchaseRequest(request);
        XContentBuilder builder = null;
        try {
            builder = restContentBuilder(request);
            String doc = builder.startObject()
                    .field("projectName", "Test")
                    .field("projectVersion", "1.0")
                    .field("logType", "Apache")
                    .field("logSource", "access.log")
                    .field("logLevel", "1")
                    .field("logTime", "2012-1-2T12:6:6")
                    .field("host", "127.0.0.1")
                    .field("body", "GET /test/nelo/")
                    .endObject().string();
            channel.sendResponse(new XContentRestResponse(request, OK, doc));
        } catch (IOException e) {
            e.printStackTrace();
        }

        }


    private Map parsePurchaseRequest(RestRequest request) {
        if (request.hasContent()) {
            BytesReference content =  request.content();
            try {
                return parseBody(content);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            throw new ElasticSearchException("missed body");
        }
        return null;
    }

    /*
    private Map pareseReqeustBody(BytesReference content) {
        Map<String, Object> map = parseBody(content);

        String [] fields = new String[]{
                "user-id", "order-id", "order-number", "order-amount", "description",
                "payment-id", "payment-method-id", "payment-amount"};
        for(String field : fields) {
            map.put(field, request.param(field));
        }

        String creditcard = request.param("creditcard");

    }
*/

    private Map<String, Object> parseBody(BytesReference content) throws IOException {
        return XContentFactory.xContent(XContentType.JSON).createParser(content).mapAndClose();
    }


}