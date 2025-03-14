package db.migration

import org.flywaydb.core.api.migration.BaseJavaMigration
import org.flywaydb.core.api.migration.Context

@Suppress("ClassName")
class V1__CreateBackendTables : BaseJavaMigration() {
    override fun migrate(context: Context?) {
        // enum tables
        context!!.connection.createStatement().execute(
            "create table approval_status (" +
                "status text primary key " +
                ")",
        )
        context.connection.createStatement().execute(
            "insert into approval_status (status) values " +
                "('NONE' ), " +
                "('APPROVED' ), " +
                "('DENIED' ) ",
        )
        context.connection.createStatement().execute(
            "create table transaction_status (" +
                "status text primary key " +
                ")",
        )
        context.connection.createStatement().execute(
            "insert into transaction_status (status) values " +
                "('NONE' ), " +
                "('STARTED' ), " +
                "('FAILED' ), " +
                "('SUCCEEDED' ) ",
        )

        // hub tables
        context.connection.createStatement().execute(
            "create table hub_user (" +
                "id uuid primary key, " +
                "username text not null " +
                ")",
        )
        context.connection.createStatement().execute(
            "create table hub_research_data (" +
                "id uuid primary key, " +
                "user_id uuid null ," +
                "constraint fk_hub_research_data_user " +
                "foreign key (user_id) " +
                "references hub_user(id) " +
                "on delete cascade " +
                ")",
        )
        context.connection.createStatement().execute(
            "create table hub_transaction (" +
                "id uuid primary key, " +
                "research_data_id uuid not null ," +
                "constraint fk_hub_transaction_research_data " +
                "foreign key (research_data_id) " +
                "references hub_research_data(id) " +
                "on delete cascade " +
                ")",
        )

        // satellite tables
        context.connection.createStatement().execute(
            "create table sat_user_meta_data (" +
                "user_id uuid not null, " +
                "valid_from timestamp with time zone not null, " +
                "valid_to timestamp with time zone default null, " +
                "first_name text, " +
                "surname text, " +
                "constraint pk_sat_user_meta_data " +
                "primary key (user_id, valid_from), " +
                "constraint fk_sat_user_meta_data_user " +
                "foreign key (user_id) " +
                "references hub_user(id) " +
                "on delete cascade " +
                ")",
        )
        context.connection.createStatement().execute(
            "create table sat_transaction_meta_data (" +
                "transaction_id uuid not null, " +
                "valid_from timestamp with time zone not null, " +
                "valid_to timestamp with time zone default null, " +
                "status text references transaction_status (status) on update cascade, " +
                "constraint pk_sat_transaction_meta_data " +
                "primary key (transaction_id, valid_from) " +
                ")",
        )
        context.connection.createStatement().execute(
            "create table sat_research_data_attributes (" +
                "research_data_id uuid not null, " +
                "valid_from timestamp with time zone not null, " +
                "valid_to timestamp with time zone default null, " +
                "name text, " +
                "description text, " +
                "file bytea, " +
                "status text references approval_status (status) on update cascade, " +
                "modifying_user_id uuid not null, " +
                "constraint pk_sat_research_data_attributes " +
                "primary key (research_data_id, valid_from) " +
                ")",
        )
    }
}
