package io.dtective.quality.framework;

import com.google.gson.Gson;
import cucumber.api.java.en.When;
import io.dtective.quality.framework.data.DataTestCore;
import io.dtective.test.TestDataCore;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CustomSteps {
    @When("I use response and save random {int} currencies in CSV file {string}")
    public void iUseResponseAndSaveRandomCurrenciesInCSVFile(int numCurrencies, String file) throws IOException {

        /*
            Getting the rates from the latest HTTP response.
            'resbody' is a special data-store that the framework uses to store the body of last performed HTTP call.
            JSONPath is used to retrieve the rates.
            The value of $.rates is then saved to another data-store named rates.
        */

        DataTestCore dataTestCore = new DataTestCore();
        dataTestCore.iGetValueOfKeyFromJSONInLocalParamAndStoreItInLocalParam("$.rates", "resbody", "rates");

        /*
            Converting the value for the key 'rates' containing JSON into a Map using Gson library.
            Then converting the Map to an Object[] to be able to select any index from the Array.
        */

        Map ratesMap = new Gson().fromJson(TestDataCore.getDataStore("rates").toString(), Map.class);
        Object[] ratesObj = ratesMap.entrySet().toArray();

       /*
        Getting x random key value pairs from the rates and storing them in CSV.
        */

        Map<String, String> output = new HashMap<String, String>();

        Random rnd = new Random();
        int pos = 0;

        for (int i = 0; i <= numCurrencies; i++) {
            pos = rnd.nextInt(ratesObj.length);
            Map.Entry value = (Map.Entry) ratesObj[pos];
            output.put(value.getKey().toString(), value.getValue().toString());
        }

        FileWriter csvWriter = new FileWriter(file);

        for (Map.Entry<String, String> rowData : output.entrySet()) {
            csvWriter.append(rowData.getKey() + "," + rowData.getValue() + "\n");
        }
        csvWriter.close();

    }
}
