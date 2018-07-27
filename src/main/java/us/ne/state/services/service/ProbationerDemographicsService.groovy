package us.ne.state.services.service

import com.google.common.collect.ImmutableMap
import groovy.sql.Sql
import org.springframework.jdbc.core.JdbcTemplate
import us.ne.state.services.Exceptions.SorCommunicationException

import java.sql.SQLException


class ProbationerDemographicsService {

    private JdbcTemplate jdbc
    private Sql sql

    ProbationerDemographicsService(JdbcTemplate jdbc, Sql sql) {
        this.jdbc = jdbc
        this.sql = sql
    }

    static final dbKeyToObjectKeyMapping = new ImmutableMap.Builder<String, String>(49)
            .put('pk_Probationer_Id', 'pk_Probationer_Id')
            .put('last_Name', 'last_Name')
            .put('first_Name', 'first_Name')
            .put('MI', 'MI')
            .put('seniority_Term', 'seniority_Term')
            .put('SSN', 'SSN')
            .put('SID', 'SID')
            .put('fk_NCIC_State_Cd', 'fk_NCIC_State_Cd')
            .put('birth_Date', 'birth_Date')
            .put('fk_Citizenship_Cd', 'fk_Citizenship_Cd')
            .put('fk_Gender_Cd', 'fk_Gender_Cd')
            .put('fk_Race_Cd', 'fk_Race_Cd')
            .put('fk_Ethnicity_Cd', 'fk_Ethnicity_Cd')
            .put('fk_Marital_Status_Cd', 'fk_Marital_Status_Cd')
            .put('fk_Hair_Cd', 'fk_Hair_Cd')
            .put('fk_Eye_Color_Cd', 'fk_Eye_Color_Cd')
            .put('height_Ft', 'height_Ft')
            .put('height_In', 'height_In')
            .put('weight', 'weight')
            .put('active', 'active')
            .put('RSO', 'RSO')
            .put('data_Number', 'data_Number')
            .put('age_First_Arrest', 'age_First_Arrest')
            .put('first_Name_Soundex', 'first_Name_Soundex')
            .put('last_Name_Soundex', 'last_Name_Soundex')
            .put('fk_Ownership_Cd', 'fk_Ownership_Cd')
            .put('driver_License', 'driver_License')
            .put('fk_Driver_License_Issued_State_Cd', 'fk_Driver_License_Issued_State_Cd')
            .put('driver_License_Expiration_Date', 'driver_License_Expiration_Date')
            .put('fk_Driver_License_Status_Cd', 'fk_Driver_License_Status_Cd')
            .put('fk_Interpreter_Language_Cd', 'fk_Interpreter_Language_Cd')
            .put('fk_Probationer_Type_Cd', 'fk_Probationer_Type_Cd')
            .put('last_Updated_Date', 'last_Updated_Date')
            .put('last_Updated_By', 'last_Updated_By')
            .put('created_Date', 'created_Date')
            .put('created_By', 'created_By')
            .put('version', 'version')
            .put('identifying_Marks', 'identifying_Marks')
            .put('fbi_Number', 'fbi_Number')
            .put('resident_Alien_Number', 'resident_Alien_Number')
            .put('fk_Driver_License_Type_Cd', 'fk_Driver_License_Type_Cd')
            .put('siblings', 'siblings')
            .put('fk_Gang_Affiliation_Cd', 'fk_Gang_Affiliation_Cd')
            .put('law_Enforcement_Verified_Gang', 'law_Enforcement_Verified_Gang')
            .put('juvenile', 'juvenile')
            .put('dhhs_System_Partner', 'dhhs_System_Partner')
            .put('military_Status', 'military_Status')
            .put('driver_License_Eligibility_Date', 'driver_License_Eligibility_Date')
            .put('psc_Identifier', 'psc_Identifier')
            .build()

    List<Map<String, String>> search(def firstName, def lastName, def ssn) {
        def map = new HashMap<String, String>()
        println(getSorUri())
        def list = new ArrayList()
        try {
            def query = "SELECT * FROM NSC_Npacs.dbo.probationer where last_Name=${lastName} or ssn=${ssn} or first_Name=${firstName}"
            println(query)
            sql.query(query) { p ->
                while (p.next()) {
                    dbKeyToObjectKeyMapping.each { k, v ->
                        map.put(v, p.getString(k))
                    }
                    list.add(map)
                }
            }
            list
        } catch (final SQLException sql) {
            throw new SorCommunicationException(getSorUri().toString(),sql)
        }
    }

    def getSorUri() {
        sql.getDataSource().jdbcUrl
    }
}
