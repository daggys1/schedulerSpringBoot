package us.ne.state.services.service

import com.google.common.collect.ImmutableMap
import groovy.sql.Sql
import org.springframework.jdbc.core.JdbcTemplate


class ProbationerDemographicsService {

    private JdbcTemplate jdbc
    private Sql sql

    ProbationerDemographicsService(JdbcTemplate jdbc, Sql sql) {
        this.jdbc = jdbc
        this.sql = sql
    }

    static final dbKeyToObjectKeyMapping = new ImmutableMap.Builder<String, String>()
            .put('pbId', 'PROBATIONER_ID')
            .put('lastName', 'LAST_NAME')
            .put('firstName', 'FIRST_NAME')
            .put('ssn', 'SSN')
            .put('dob', 'DOB')
            .build()

    Map<String, String> demographics(def firstName, def lastName, def ssn) {
        def map = new HashMap<String, String>(10)
        println(getSorUri())
        try {
            def query = "SELECT pk_Probationer_Id pbId, last_Name lastName, first_Name firstName, SSN ssn,birth_Date dob FROM NSC_Npacs.dbo.probationer where first_Name = ${firstName}"
            sql.query(query) { p ->
                while (p.next()) {
                    dbKeyToObjectKeyMapping.each { k, v ->
                        map.put(v, p.getString(k))
                    }
                }
            }
        } catch (final Throwable t) {
            println(getSorUri())
            throw t
        }
        map
    }

    def getSorUri() {
        sql.getDataSource().jdbcUrl
    }
}
