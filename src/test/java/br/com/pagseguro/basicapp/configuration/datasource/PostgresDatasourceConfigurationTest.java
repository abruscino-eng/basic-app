package br.com.pagseguro.basicapp.configuration.datasource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PostgresDatasourceConfigurationTest {


    @Test
    public void getJDBCConnectionUrlValid() {
        final PostgresDatasourceConfiguration dsConfig =
                new PostgresDatasourceConfiguration("URL", "PORT", "DB_NAME", null, null);

        Assertions.assertEquals("jdbc:postgresql://URL:PORT/DB_NAME", dsConfig.getDBConnection());
    }
}
