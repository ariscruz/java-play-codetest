package controllers;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import impl.CustomerImpl;
import models.Customer;
import models.CustomerFile;
import play.api.mvc.MultipartFormData;
import play.api.mvc.MultipartFormData.FilePart;
import play.core.j.HttpExecutionContext;
import play.data.Form;
import play.libs.Json;
import play.mvc.*;

public class Application extends Controller {

    public static Result index() {
		return redirect(routes.Application.loadFile());
    }
	
	public static CompletionStage<Result> sortCustomers() {
		
		JsonNode json = request().body().asJson();
		/*if (json == null) {
	        return badRequest("Expecting Json data");
	    }

		List<Customer> customersList = process(json.toString(), false);
        JsonNode jsonParsed = Json.toJson(customersList);
        return created(jsonParsed); */
		
		return CompletableFuture.supplyAsync(() -> process(json.toString(), false))
                .thenApply(customerList -> created(Json.toJson(customerList)));
	}

	public static Result loadFile() {
		Form<CustomerFile> customerForm = Form.form(CustomerFile.class);
		 return ok(views.html.index.render(customerForm));
	}
	

    public static CompletionStage<Result> upload() throws IOException {
		play.mvc.Http.MultipartFormData body = request().body().asMultipartFormData();
	    play.mvc.Http.MultipartFormData.FilePart filePart = body.getFile("file");
        final File file = filePart.getFile();
        /*List<Customer> customersList = process(file, true);
        JsonNode jsonParsed = Json.toJson(customersList);
	    return created(jsonParsed);*/
	    
	    return CompletableFuture.supplyAsync(() -> process(file, true))
                .thenApply(customerList -> created(Json.toJson(customerList)));
		
    }
	
    private static List<Customer> process(Object o, boolean isFromFile) {
    
		try {
			
		    ObjectMapper mapper = new ObjectMapper();
		    JsonFactory f = new JsonFactory();
		    List<Customer> customersList = null;
		    JsonParser jp = null;
		    if (isFromFile) {
		    	jp = f.createJsonParser((File) o);
		    } else {
		    	jp = f.createJsonParser((String) o);
		    }
		    
	    	TypeReference<List<Customer>> tRef = new TypeReference<List<Customer>>() {};
		    customersList = mapper.readValue(jp, tRef);
		    CustomerImpl customerImpl = new CustomerImpl();
		    customersList = customerImpl.sortCustomer(customersList);
		    return customersList;
		    
		} catch (JsonGenerationException e) {
		    e.printStackTrace();
		} catch (JsonMappingException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		return null;
    }
    
	
}
