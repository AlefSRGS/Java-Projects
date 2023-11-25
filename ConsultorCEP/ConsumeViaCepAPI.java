import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public abstract class ConsumeViaCepAPI {
    public String getInfoJsonByCEP(String cepToSearch) throws IOException, InterruptedException {
        String cleanedCep = cleanCep(cepToSearch);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://viacep.com.br/ws/" + cleanedCep + "/json/"))
                .build();
        HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }
    public String cleanCep(String cep) {
        return cep.replace(" ", "").replace("-", "");
    }
    public boolean testValidInputCep(String cep){
        return (cep != null && cep.matches("[0-9]*"));
    }
    public String getInfoJsonDescoverCep(String uf, String city, String logradouro){
        return "";
    }
    public TranslateJsonViaCepAPI translateJsonToObject(String responseApi){
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting()
                .create();
        return gson.fromJson(responseApi, TranslateJsonViaCepAPI.class);
    }
    public String translateObjectToJson(Location location){
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting()
                .create();
        return gson.toJson(location);
    }
}
