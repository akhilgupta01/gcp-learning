import com.examples.beam.tx.model.Transaction;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

import java.io.FileWriter;

public class TxTestDataGenerator {
    public static void main(String[] args) throws Exception {

        FileWriter writer = new FileWriter("C:\\Users\\Akhil\\IdeaProjects\\gcp-learning\\word-count\\src\\main\\resources\\transactions.csv");

        ColumnPositionMappingStrategy<Transaction> mappingStrategy= new ColumnPositionMappingStrategy<>();
        mappingStrategy.setType(Transaction.class);
        mappingStrategy.setColumnMapping("txId", "isin", "quantity", "customerId");

        // Creating StatefulBeanToCsv object
        StatefulBeanToCsv<Transaction> beanWriter = new StatefulBeanToCsvBuilder<Transaction>(writer)
                .withMappingStrategy(mappingStrategy)
                .withApplyQuotesToAll(true)
                .build();
        writer.append("\"txId\", \"isin\", \"quantity\", \"customerId\"\n");
        for(int i=0; i<10000; i++){
            Transaction tx = Transaction.builder()
                    .txId(String.format("ID%05d", i))
                    .customerId("CUS"+random(1000, 1500))
                    .isin("ISIN" +random(1000, 1150))
                    .quantity(random(1000, 10000))
                    .build();

            beanWriter.write(tx);
        }
        // closing the writer object
        writer.close();
    }

    private static int random(int min, int max){
        double a = Math.random()*(max-min+1)+min;
        return (int)a;
    }
}
