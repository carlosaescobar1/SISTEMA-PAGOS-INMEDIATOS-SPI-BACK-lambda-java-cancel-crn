package co.com.avc.models.parameter;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ArnSnsOpenSearchTest {

    @Test
    void shouldSetAndGetArnSnsOpenSearchBAVV() {
        ArnSnsOpenSearch arnSnsOpenSearch = new ArnSnsOpenSearch();
        arnSnsOpenSearch.setArnSnsOpenSearchBAVV("arn:bavv:test");
        assertEquals("arn:bavv:test", arnSnsOpenSearch.getArnSnsOpenSearchBAVV());
    }

    @Test
    void shouldSetAndGetArnSnsOpenSearchBBOG() {
        ArnSnsOpenSearch arnSnsOpenSearch = new ArnSnsOpenSearch();
        arnSnsOpenSearch.setArnSnsOpenSearchBBOG("arn:bbog:test");
        assertEquals("arn:bbog:test", arnSnsOpenSearch.getArnSnsOpenSearchBBOG());
    }

    @Test
    void shouldSetAndGetArnSnsOpenSearchBOCC() {
        ArnSnsOpenSearch arnSnsOpenSearch = new ArnSnsOpenSearch();
        arnSnsOpenSearch.setArnSnsOpenSearchBOCC("arn:bocc:test");
        assertEquals("arn:bocc:test", arnSnsOpenSearch.getArnSnsOpenSearchBOCC());
    }

    @Test
    void shouldSetAndGetArnSnsOpenSearchBPOP() {
        ArnSnsOpenSearch arnSnsOpenSearch = new ArnSnsOpenSearch();
        arnSnsOpenSearch.setArnSnsOpenSearchBPOP("arn:bpop:test");
        assertEquals("arn:bpop:test", arnSnsOpenSearch.getArnSnsOpenSearchBPOP());
    }

    @Test
    void shouldSetAndGetArnSnsOpenSearchDALE() {
        ArnSnsOpenSearch arnSnsOpenSearch = new ArnSnsOpenSearch();
        arnSnsOpenSearch.setArnSnsOpenSearchDALE("arn:dale:test");
        assertEquals("arn:dale:test", arnSnsOpenSearch.getArnSnsOpenSearchDALE());
    }
}