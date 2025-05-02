package db.migration

import org.flywaydb.core.api.migration.BaseJavaMigration
import org.flywaydb.core.api.migration.Context

enum class TransactionStatus {
  NONE,
  STARTED,
  FAILED,
  SUCCEEDED,
}

@Suppress("ClassName", "kotlin:S1192", "kotlin:S101")
class V1__CreateBackendTables : BaseJavaMigration() {
  @Suppress("LongMethod")
  override fun migrate(context: Context?) {
    // enum tables
    context!!
        .connection
        .createStatement()
        .execute(
            "create table transaction_status (" + "status text primary key " + ")",
        )
    context.connection
        .createStatement()
        .execute(
            "insert into transaction_status (status) values " +
                "('" +
                TransactionStatus.NONE.name +
                "'), " +
                "('" +
                TransactionStatus.STARTED.name +
                "'), " +
                "('" +
                TransactionStatus.FAILED.name +
                "'), " +
                "('" +
                TransactionStatus.SUCCEEDED.name +
                "') ",
        )
    context.connection
        .createStatement()
        .execute(
            "create table data_access_status (" + "status text primary key " + ")",
        )
    context.connection
        .createStatement()
        .execute(
            "insert into data_access_status (status) values " +
                "('NONE' ), " +
                "('REQUESTED' ), " +
                "('APPROVED' ), " +
                "('DENIED' ) ",
        )

    // hub tables
    context.connection
        .createStatement()
        .execute(
            "create table hub_data_consumer (" +
                "id uuid primary key, " +
                "username text not null " +
                ")",
        )
    context.connection
        .createStatement()
        .execute(
            "create table hub_data_provider (" +
                "id uuid primary key, " +
                "username text not null " +
                ")",
        )
    context.connection
        .createStatement()
        .execute(
            "create table hub_research_data (" +
                "id uuid primary key, " +
                "data_provider_id uuid not null, " +
                "constraint fk_hub_research_data_data_provider " +
                "foreign key (data_provider_id) " +
                "references hub_data_provider(id) " +
                "on delete cascade " +
                ")",
        )
    context.connection
        .createStatement()
        .execute(
            "create table hub_transaction (" + "id uuid primary key " + ")",
        )
    context.connection
        .createStatement()
        .execute(
            "create table hub_data_analysis (" +
                "id uuid primary key, " +
                "app_name text unique not null " +
                ")",
        )

    // link tables
    context.connection
        .createStatement()
        .execute(
            "create table link_data_consumer_research_data (" +
                "id uuid primary key, " +
                "data_consumer_id uuid not null, " +
                "research_data_id uuid not null, " +
                "constraint fk_link_data_consumer_research_data_data_consumer " +
                "foreign key (data_consumer_id) " +
                "references hub_data_consumer(id) " +
                "on delete cascade, " +
                "constraint fk_link_data_consumer_research_data_research_data " +
                "foreign key (research_data_id) " +
                "references hub_research_data(id) " +
                "on delete cascade " +
                ")",
        )
    context.connection
        .createStatement()
        .execute(
            "create table link_transaction_data_consumer (" +
                "transaction_id uuid not null, " +
                "data_consumer_id uuid not null, " +
                "constraint fk_link_transaction_data_consumer_transaction " +
                "foreign key (transaction_id) " +
                "references hub_transaction(id) " +
                "on delete cascade, " +
                "constraint fk_link_transaction_data_consumer_data_consumer " +
                "foreign key (data_consumer_id) " +
                "references hub_data_consumer(id) " +
                "on delete cascade " +
                ")",
        )
    context.connection
        .createStatement()
        .execute(
            "create table link_transaction_research_data (" +
                "transaction_id uuid not null, " +
                "research_data_id uuid not null, " +
                "constraint fk_link_transaction_research_data_transaction " +
                "foreign key (transaction_id) " +
                "references hub_transaction(id) " +
                "on delete cascade, " +
                "constraint fk_link_transaction_research_data_research_data " +
                "foreign key (research_data_id) " +
                "references hub_research_data(id) " +
                "on delete cascade " +
                ")",
        )

    // satellite tables
    context.connection
        .createStatement()
        .execute(
            "create table sat_data_consumer_meta_data (" +
                "data_consumer_id uuid not null, " +
                "valid_from timestamp with time zone not null, " +
                "valid_to timestamp with time zone default null, " +
                "first_name text, " +
                "surname text, " +
                "constraint pk_sat_data_consumer_meta_data " +
                "primary key (data_consumer_id, valid_from), " +
                "constraint fk_sat_data_consumer_meta_data_data_consumer " +
                "foreign key (data_consumer_id) " +
                "references hub_data_consumer(id) " +
                "on delete cascade " +
                ")",
        )
    context.connection
        .createStatement()
        .execute(
            "create table sat_data_consumer_data_access (" +
                "data_consumer_research_data_id uuid not null, " +
                "valid_from timestamp with time zone not null, " +
                "valid_to timestamp with time zone default null, " +
                "status text references data_access_status (status) on update cascade, " +
                "constraint pk_sat_data_consumer_data_access " +
                "primary key (data_consumer_research_data_id, valid_from), " +
                "constraint fk_sat_data_consumer_data_access_data_consumer_research_data " +
                "foreign key (data_consumer_research_data_id) " +
                "references link_data_consumer_research_data(id) " +
                "on delete cascade " +
                ")",
        )
    context.connection
        .createStatement()
        .execute(
            "create table sat_data_provider_meta_data (" +
                "data_provider_id uuid not null, " +
                "valid_from timestamp with time zone not null, " +
                "valid_to timestamp with time zone default null, " +
                "institution_name text, " +
                "contact_person_first_name text, " +
                "contact_person_surname text, " +
                "constraint pk_sat_data_provider_meta_data " +
                "primary key (data_provider_id, valid_from), " +
                "constraint fk_sat_data_provider_meta_data_data_provider " +
                "foreign key (data_provider_id) " +
                "references hub_data_provider(id) " +
                "on delete cascade " +
                ")",
        )
    context.connection
        .createStatement()
        .execute(
            "create table sat_research_data_meta_data (" +
                "research_data_id uuid not null, " +
                "valid_from timestamp with time zone not null, " +
                "valid_to timestamp with time zone default null, " +
                "attribute_id uuid not null, " +
                "attribute_position bigint, " +
                "attribute_name text, " +
                "attribute_type text, " +
                "constraint pk_sat_research_data_meta_data " +
                "primary key (research_data_id, valid_from, attribute_id), " +
                "constraint fk_sat_research_data_meta_data_research_data " +
                "foreign key (research_data_id) " +
                "references hub_research_data(id) " +
                "on delete cascade " +
                ")",
        )
    context.connection
        .createStatement()
        .execute(
            "create table sat_research_meta_data (" +
                "research_data_id uuid not null, " +
                "valid_from timestamp with time zone not null, " +
                "valid_to timestamp with time zone default null, " +
                "name text, " +
                "description text, " +
                "constraint pk_sat_research_meta_data " +
                "primary key (research_data_id, valid_from), " +
                "constraint fk_sat_research_meta_data_research_data " +
                "foreign key (research_data_id) " +
                "references hub_research_data(id) " +
                "on delete cascade " +
                ")",
        )
    context.connection
        .createStatement()
        .execute(
            "create table sat_transaction_meta_data (" +
                "transaction_id uuid not null, " +
                "valid_from timestamp with time zone not null, " +
                "valid_to timestamp with time zone default null, " +
                "status text references transaction_status (status) on update cascade, " +
                "data_analysis_id uuid not null, " +
                "eurodat_client_id text not null, " +
                "eurodat_workflow_name text not null, " +
                "eurodat_image_id text not null, " +
                "eurodat_transaction_id text not null, " +
                "constraint pk_sat_transaction_meta_data " +
                "primary key (transaction_id, valid_from), " +
                "constraint fk_sat_transaction_meta_data_transaction " +
                "foreign key (transaction_id) " +
                "references hub_transaction(id) " +
                "on delete cascade, " +
                "constraint fk_sat_transaction_meta_data_data_analysis " +
                "foreign key (data_analysis_id) " +
                "references hub_data_analysis(id) " +
                "on delete cascade " +
                ")",
        )
    context.connection
        .createStatement()
        .execute(
            "create table sat_data_analysis_meta_data (" +
                "data_analysis_id uuid not null, " +
                "valid_from timestamp with time zone not null, " +
                "valid_to timestamp with time zone default null, " +
                "description text, " +
                "image_location text not null, " +
                "ddl_statement text not null, " +
                "constraint pk_sat_data_analysis_meta_data " +
                "primary key (data_analysis_id, valid_from), " +
                "constraint fk_sat_data_analysis_meta_data_data_analysis " +
                "foreign key (data_analysis_id) " +
                "references hub_data_analysis(id) " +
                "on delete cascade " +
                ")",
        )
  }
}
