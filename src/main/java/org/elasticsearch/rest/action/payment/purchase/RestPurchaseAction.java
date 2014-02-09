package org.elasticsearch.rest.action.payment.purchase;

import org.elasticsearch.ElasticSearchException;
import org.elasticsearch.action.PaymentListener;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.bytes.BytesReference;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.payment.Gateway;
import org.elasticsearch.rest.*;
import org.elasticsearch.threadpool.ThreadPool;

import java.io.IOException;
import java.util.Map;

import static org.elasticsearch.rest.RestRequest.Method.POST;
import static org.elasticsearch.rest.RestStatus.OK;
import static org.elasticsearch.rest.action.support.RestXContentBuilder.restContentBuilder;

/**
 * Created by jackiedong168 on 14-1-20.
 */
public class RestPurchaseAction extends BaseRestHandler {
    private final ThreadPool threadPool;

    @Inject
    public RestPurchaseAction(Settings settings, Client client, RestController controller, ThreadPool threadPool) {
        super(settings, client);
        this.threadPool = threadPool;
        controller.registerHandler(POST, "/v1/purchases", this);
    }

    @Override
    public void handleRequest(final RestRequest request, final RestChannel channel) {
        Map<String, Object> state = parsePurchaseRequest(request);
        purchase(state, new PaymentListener<Map>() {

            @Override
            public void onResponse(Map state) {
                try {
                    XContentBuilder builder = null;
                    builder = restContentBuilder(request);
                    builder.startObject()
                            .field("projectName", "Test2")
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
                    if (logger.isDebugEnabled()) {
                        logger.debug("failed to execute search (building response)", e);
                    }
                    onFailure(e);
                }

            }

            @Override
            public void onFailure(Throwable e) {
                try {
                    channel.sendResponse(new XContentThrowableRestResponse(request, e));
                } catch (IOException e1) {
                    logger.error("Failed to send failure response", e1);
                }
            }
        });
    }


    private void purchase(final Map<String, Object> state, final PaymentListener<Map> listener) {
        threadPool.generic().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Map newState = Gateway.purchase(state);
                    listener.onResponse(newState);
                } catch (Exception e) {
                    listener.onFailure(e);
                }
            }
        });


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

    private Map<String, Object> handleRequest(Map<String, ?> state) throws Exception {
        return (Map<String, Object>) state;
    }

    private Map<String, Object> parseBody(BytesReference content) throws IOException {
        return XContentFactory.xContent(XContentType.JSON).createParser(content).mapAndClose();
    }



}