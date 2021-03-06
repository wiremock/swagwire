package acceptance.tobegenerated;

import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.google.gson.Gson;

public class WireMockedRemoteOperation<TYPE> implements RemoteOperation<TYPE> {
    private MappingBuilder mappingBuilder;
    private String contentType;

    public WireMockedRemoteOperation(MappingBuilder mappingBuilder, String contentType) {
        this.mappingBuilder = mappingBuilder;
        this.contentType = contentType;
    }

    @Override
    public void respondsWith(TYPE object) {
        WireMock.stubFor(mappingBuilder.willReturn(
            WireMock.aResponse()
                .withStatus(200)
                .withHeader("Content-Type", contentType)
                .withBody(bodyFor(object))
        ));
    }

    @Override
    public void succeeds() {
        WireMock.stubFor(mappingBuilder.willReturn(
                WireMock.aResponse()
                        .withStatus(200)
        ));

    }

    /**
     * This is currently hardcoded to doing just json right now - as the need
     * arises this should change to handle correctly content negotiation
     */
    private String bodyFor(TYPE object) {
        return new Gson().toJson(object);
    }
}