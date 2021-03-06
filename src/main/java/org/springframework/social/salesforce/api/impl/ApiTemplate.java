package org.springframework.social.salesforce.api.impl;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.springframework.social.salesforce.api.ApiOperations;
import org.springframework.social.salesforce.api.ApiVersion;
import org.springframework.social.salesforce.api.Salesforce;
import org.springframework.social.salesforce.api.SalesforceIdentity;
import org.springframework.social.support.URIBuilder;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Default implementation of ApiOperations.
 *
 * @author Umut Utkan
 */
public class ApiTemplate extends AbstractSalesForceOperations<Salesforce> implements ApiOperations
{

    private RestTemplate restTemplate;

    public ApiTemplate(Salesforce api,
                       RestTemplate restTemplate)
    {
        super(api);
        this.restTemplate = restTemplate;
    }

    @Override
    public List<ApiVersion> getVersions()
    {
        URI uri = URIBuilder.fromUri(api.getBaseUrl()).build();
        JsonNode dataNode = restTemplate.getForObject(uri, JsonNode.class);
        return api.readList(dataNode, ApiVersion.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, String> getServices(String version)
    {
        requireAuthorization();
        return restTemplate.getForObject(api.getBaseUrl() + "/v{version}", Map.class, version);
    }

    @Override
    public SalesforceIdentity getIdentity(String identityUrl)
    {
        requireAuthorization();
        return restTemplate.getForObject(identityUrl, SalesforceIdentity.class);
    }

}
