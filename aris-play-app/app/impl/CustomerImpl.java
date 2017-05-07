package impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

import models.Customer;

public class CustomerImpl implements ICustomer {

	@Override
	public List<Customer> sortCustomer(List<Customer> customerList) {
		// TODO Auto-generated method stub
		Collections.sort(customerList, new Comparator<Customer>() {

			@Override
			public int compare(Customer o1, Customer o2) {

				//DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss Z");
				DateTime dt1 = ISODateTimeFormat.dateTimeParser().parseDateTime(o1.getDuetime());
				DateTime dt2 = ISODateTimeFormat.dateTimeParser().parseDateTime(o2.getDuetime());
				
				return dt1.compareTo(dt2);
			}
			
		});
		return customerList;
	}

	
}
