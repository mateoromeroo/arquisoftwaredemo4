package com.example.jhordanvega.app3;

public class WoocommerceApi {

    public class WooCommerceApi extends org.scribe.builder.api.DefaultApi10a {

        @Override
        public org.scribe.model.Verb getRequestTokenVerb()
        {
            return org.scribe.model.Verb.POST;
        }

        @Override
        public String getRequestTokenEndpoint() {
            return "https://demo4-manga.herokuapp.com/api/v1/manga";
        }

        @Override
        public String getAccessTokenEndpoint() {
            return "none";
        }

        @Override
        public String getAuthorizationUrl(org.scribe.model.Token requestToken) {
            return "none";
        }
    }

}
