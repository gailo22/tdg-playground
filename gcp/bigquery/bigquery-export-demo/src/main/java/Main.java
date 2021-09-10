import com.google.cloud.bigquery.*;

public class Main {
    public static void main(String[] args) {
        String projectId = "MY_PROJECT_ID";
        String datasetName = "MY_DATASET_NAME";
        String tableName = "MY_TABLE_NAME";
//        String query = "SELECT * FROM `bigquery-public-data.samples.shakespeare` LIMIT 10";
        String query = "SELECT * FROM `bigquery-public-data.samples.shakespeare` LIMIT 16010";
        query(query);
    }

    public static void query(String query) {
        try {
            // Initialize client that will be used to send requests. This client only needs to be created
            // once, and can be reused for multiple requests.
            BigQuery bigquery = BigQueryOptions.getDefaultInstance().getService();
            QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query).build();
            TableResult results = bigquery.query(queryConfig);
            results.iterateAll()
                    .forEach(row -> row.forEach(val -> System.out.printf("%s,", val.toString())));

            System.out.println();
            System.out.printf("Total records: %s\n", results.getTotalRows());
            System.out.println("Query performed successfully.");
        } catch (BigQueryException | InterruptedException e) {
            System.out.println("Query not performed \n" + e.toString());
        }
    }
}
