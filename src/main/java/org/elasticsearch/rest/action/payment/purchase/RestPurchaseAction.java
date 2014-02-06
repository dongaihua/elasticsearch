package org.elasticsearch.rest.action.payment.purchase;

import org.elasticsearch.ElasticSearchException;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.bytes.BytesReference;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.payment.Gateway;
import org.elasticsearch.rest.*;

import java.io.IOException;
import java.util.Map;

import static org.elasticsearch.rest.RestRequest.Method.POST;
import static org.elasticsearch.rest.RestStatus.OK;
import static org.elasticsearch.rest.action.support.RestXContentBuilder.restContentBuilder;

/**
 * Created by jackiedong168 on 14-1-20.
 */
public class RestPurchaseAction extends BaseRestHandler {

    @Inject
    public RestPurchaseAction(Settings settings, Client client, RestController controller) {
        super(settings, client);
        controller.registerHandler(POST, "/v1/purchases", this);
    }

    @Override
    public void handleRequest(final RestRequest request, final RestChannel channel) {
        Map<String, Object> state = parsePurchaseRequest(request);
        try {
            Map newState = Gateway.purchase(state);
            XContentBuilder builder = null;
            builder = restContentBuilder(request);
            builder.startObject()
                    .field("projectName", "Test")
                    .field("projectVersion", "1.0")
                    .field("logType", "Apache")
                    .field("logSource", "access.log")
                    .field("logLevel", "1")
                    .field("logTime", "2012-1-2T12:6:6")
                    .field("host", "127.0.0.1")
                    .field("body", "GET /test/nelo/")
                    .endObject();
            channel.sendResponse(new XContentRestResponse(request, OK, builder));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private Map parsePurchaseRequest(RestRequest request) {
        if (request.hasContent()) {
            BytesReference content = request.content();
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